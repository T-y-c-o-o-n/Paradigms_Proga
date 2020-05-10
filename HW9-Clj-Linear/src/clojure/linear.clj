; just delay!

; vectors
(defn sizes-eq? [& args]
  "checks if all the vector has the same length"
  {:pre [(every? vector? args)]}
  (apply = (mapv count args)))
(defn num-vec? [v]
  "checks if argument is numeric vector"
  (and (vector? v) (every? number? v)))
(defn vec-op [op]
  (fn [& vectors]
    {:pre [(every? num-vec? vectors) (apply sizes-eq? vectors)]
     :post [(num-vec? %) (= (count (vectors 0)) (count %))]}
    (apply mapv op vectors)))
(def v+ (vec-op +))
(def v- (vec-op -))
(def v* (vec-op *))
(defn scalar [v u]
  {:pre [(num-vec? v) (num-vec? u) (sizes-eq? v u)]
   :post [(number? scalar)]}
  (apply + (v* v u)))
(defn vec-mul [v u]
  {:pre [(num-vec? v) (num-vec? u) (= 3 (count v) (count u))]
   :post [(num-vec? %) (= 3 (count %))]}
  (letfn [
          (minor [i j] (- (* (v i) (u j)) (* (v j) (u i))))
          ]
    ([(minor 1 2)
      (minor 0 2)
      (minor 0 1)])))
(defn vect [v & vectors]
  {:pre [(every? num-vec? vectors) (apply sizes-eq? [0 0 0] vectors)]
   :post [(num-vec? %) (= 3 (count %))]}
  (reduce vec-mul v vectors))
(defn v*s [v & scalars]
  {:pre [(num-vec? v) (every? number? scalars)]
   :post [(num-vec? %) (= (count v) (count %))]}
  (let [prod (apply * scalars)]
   (mapv (partial * prod) v)))
; matrices
(defn num-mat? [m]
  "checks if argument is numeric matrix"
  (and (vector? m) (every? num-vec? m) (apply sizes-eq? m)))
(defn mat-op [op]
  (fn [& matrices]
    {:pre [(every? num-mat? matrices) (apply sizes-eq? matrices) (apply sizes-eq? (mapv (fn [m] (m 0)) matrices))]}
    :post [(num-mat? %) (= (count (matrices 0)) (count %)) (= (count ((matrices 0) 0)) (count (% 0)))]
    (apply mapv op matrices)))
(def m+ (mat-op v+))
(def m- (mat-op v-))
(def m* (mat-op v*))
(defn m*s [A & scalars]
  {:pre [(num-mat? A) (every? number? scalars)]}
  (let [prod (apply * scalars)] (mapv (partial * prod) A)))
(defn m*v [A v]
  {:pre [(num-mat? A) (num-vec? v) (sizes-eq? [A v])]}
  (apply v+ (mapv v*s A v)))
(defn mat-mul [A B]
  {:pre [(num-mat? A) (num-mat? B) (= (count A) (count (B 0)))]
   :post [(num-mat? %) (= (count B) (count %)) (= (count (A 0)) (count (% 0)))]}
   (mapv (fn [v] (m*v A v)) B))
(defn m*m [& matrices]
  {:pre [(every? num-mat? matrices)]
   :post [(num-mat? %)]}
  (reduce mat-mul matrices))
(defn transpose [A]
  {:pre [(num-mat? A)]
   :post [(num-mat? %)]}
  (apply mapv vector A))
; tensors
(defn consist-of-vecs-and-nums? [comp]
  (or (number? comp)
      (and (vector? comp) (every? consist-of-vecs-and-nums? comp))))
(defn get-shape [t]
  {:pre [(consist-of-vecs-and-nums? t)]}
  "Return the array with lengths of axis. If argument is scalar returns []"
  (letfn [
          (get-shape' [comp array-to-fill]
            (if (number? comp)
              array-to-fill
              (get-shape' (comp 0) (conj array-to-fill (count comp)))))
          (check-shape [comp shape level]
            (if (= level (count shape))
              (number? comp)
              (and
                (vector? comp)
                (= (shape level) (count comp))
                (every? (fn [comp] (check-shape comp shape (+ 1 level))) comp))))
          ]
    (let [res (get-shape' t [])]
      (if (check-shape t res 0) res nil))))
(defn tensor? [t]
  (and
    (consist-of-vecs-and-nums? t)
    (not (= (get-shape t) nil))))
(defn ten-op [op-end-of-rec]
  (fn [& tensors]
    {:pre [(every? tensor? tensors) (apply = (mapv get-shape tensors))]
     :post [(tensor? %)]}
    (apply
      (fn rec-fun [& comps]
        (if (every? number? comps)
          (apply op-end-of-rec comps)
          (apply mapv rec-fun comps)))
      tensors)))
(def t+ (ten-op +))
(def t- (ten-op -))
(def t* (ten-op *))


(def t [900 [-34] [[1]]])
(println t)
(println (str "tensor? " (tensor? t)))
(println (str "shape - " (get-shape t)))


(println (scalar [60 80 90] [1 2 3]))
(def vectors [[60 80 90] [1 2 3] [100 100 0]])
(println (apply v* [[60 80 90] [1 2 3] [100 100 0]]))
(println (m- [[2 0] [-3 45] [900 -12]] [[54 2] [7 -0] [+0 34]] [[43 34] [1 -1] [29 8]]))
(println (m*s [[2 0] [-3 45] [900 -12]] 1 2 -30))
(println (m*v [[2 0] [-3 45] [900 -12] [21 0]] [2 -1 0 1]))
(println (m*m [[1 0] [0 1]] [[13 -900] [0 1345]] [[1 0] [0 1]]))
(println (transpose [[1 2] [3 4] [5 6] [7 8]]))
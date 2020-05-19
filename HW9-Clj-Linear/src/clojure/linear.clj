; Solution stub
; code for passing!
; Scalars
(def s+ +)
(def s- -)
(def s* *)
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
     :post [(num-vec? %) (apply sizes-eq? % vectors)]}
    (apply mapv op vectors)))
(def v+ (vec-op +))
(def v- (vec-op -))
(def v* (vec-op *))
(defn scalar [v u]
  {:pre [(num-vec? v) (num-vec? u) (sizes-eq? v u)]
   :post [(number? %)]}
  (apply + (v* v u)))
(defn vect [& vectors]
  {:pre [(every? num-vec? vectors) (apply sizes-eq? [0 0 0] vectors)]
   :post [(num-vec? %) (= 3 (count %))]}
  (letfn [(vec-mul [v u]
            (letfn [(minor [i j] (- (* (v i) (u j)) (* (v j) (u i))))]
              [(minor 1 2)
               (minor 2 0)
               (minor 0 1)]))]
    (reduce vec-mul vectors)))
(defn v*s [v & scalars]
  {:pre [(num-vec? v) (every? number? scalars)]
   :post [(num-vec? %) (sizes-eq? v %)]}
  (let [prod (apply * scalars)]
   (mapv (partial * prod) v)))

; matrices

(defn num-mat? [m]
  "checks if argument is numeric matrix"
  (and (vector? m) (every? num-vec? m) (apply sizes-eq? m)))
(defn mat-op [op]
  (fn [& matrices]
    {:pre [(every? num-mat? matrices) (apply sizes-eq? matrices) (apply sizes-eq? (mapv #(% 0) matrices))]
    :post [(num-mat? %) (apply sizes-eq? matrices) (apply sizes-eq? (% 0) (mapv #(% 0) matrices))]}
    (apply mapv op matrices)))
(def m+ (mat-op v+))
(def m- (mat-op v-))
(def m* (mat-op v*))
(defn m*s [A & scalars]
  {:pre [(num-mat? A) (every? number? scalars)]}
  (let [prod (apply * scalars)] (mapv #(v*s % prod) A)))
(defn m*v [A v]
  {:pre [(num-mat? A) (num-vec? v) (sizes-eq? [(count (A 0)) v])]}
  (mapv (partial scalar v) A))
(defn v*m [v A]
  "multiplies numeric vector-string with numeric matrix"
  {:pre [(num-vec? v) (num-mat? A) (sizes-eq? v (A 0))]}
  (apply v+ (mapv #(v*s %2 %1) v A)))
(defn m*m [& matrices]
  {:pre [(every? num-mat? matrices)]
   :post [(num-mat? %)]}
  (letfn [(mat-mul [A B] {:pre [(sizes-eq? (A 0) B)]} (mapv #(v*m % B) A))]
   (reduce mat-mul matrices)))
(defn transpose [A]
  {:pre [(num-mat? A)]
   :post [(num-mat? %)]}
  (apply mapv vector A))

; 188 symbols for det
(defn det [A]
  "returns determinant of numeric square matrix"
  {:pre [(num-mat? A) (sizes-eq? A (A 0))]}
  (if (= 1 (count A))
    ((A 0) 0)
    (letfn [
            (cut [v i] (vec (concat (subvec v 0 i) (subvec v (+ 1 i)))))
            (minor [i j] (det (cut (mapv #(cut % j) A) i)))]
      ((fn me [j]
         (if (= (count A) j)
           0
           (- (* ((A 0) j) (minor 0 j)) (me (+ 1 j)))))
       0))))

; tensors

(defn consist-of-vecs-and-nums? [t]
  (or (number? t)
      (and (vector? t) (every? consist-of-vecs-and-nums? t))))
(defn get-shape [t]
  {:pre [(consist-of-vecs-and-nums? t)]
   :post [(or (nil? %) (num-vec? %))]}
  "Returns vector with lengths of axis. If argument is scalar returns []. If polimeric matrix t is not tensor returns nil"
  (letfn [
          (get-shape' [t vec-to-fill]
            (if (number? t)
              vec-to-fill
              (get-shape' (t 0) (conj vec-to-fill (count t)))))
          (check-shape [t shape level]
            (if (= level (count shape))
              (number? t)
              (and
                (vector? t)
                (= (shape level) (count t))
                (every? (fn [comp] (check-shape comp shape (+ 1 level))) t))))
          ]
    (let [res (get-shape' t [])]
      (if (check-shape t res 0) res nil))))
(defn tensor? [t]
  (and
    (consist-of-vecs-and-nums? t)
    (not (nil? (get-shape t)))))
(defn ten-op [op-end-of-rec]
  (fn [& tensors]
    {:pre [(every? tensor? tensors) (apply = (mapv get-shape tensors))]
     :post [(tensor? %)]}
    (apply
      (fn rec-fun [& ts]
        (if (every? number? ts)
          (apply op-end-of-rec ts)
          (apply mapv rec-fun ts)))
      tensors)))
(def t+ (ten-op +))
(def t- (ten-op -))
(def t* (ten-op *))


(def t [900 [-34] [[1]]])
(println t)
(println (str "tensor? " (tensor? t)))
(println (str "shape - " (get-shape t)))
(println (vect (vector 1 2 3) (vector 4 5 6)))
(println (m*s [[1 2] [3 4] [5 6]] 4 90 0))
(println (m*v [[2 0]
               [5 7]
               [2 9]
               [0 -1]] [1 -1]))
(println (v*m [1 2 3 4] [[2 0]
                         [5 7]
                         [2 9]
                         [0 -1]]))
(println (m*m [[1 2 3 4] [4 3 2 1] [100 100 100 0]] [[0 1]
                                                     [2 9]
                                                     [3 -10]
                                                     [100 4]]))
(def A
  [[1	-46	0	4	0]
  [0	6	7	-100	233]
  [45	10	11	12	3]
  [13	0	15	16	-1]
  [4	-46	-156	0	25]]
  )

(println (str "A = " A))
(println (str "det A = " (det A)))                          ; -911034352

;(println (scalar [60 80 90] [1 2 3]))
;(def vectors [[60 80 90] [1 2 3] [100 100 0]])
;(println (apply v* [[60 80 90] [1 2 3] [100 100 0]]))
;(println (m- [[2 0] [-3 45] [900 -12]] [[54 2] [7 -0] [+0 34]] [[43 34] [1 -1] [29 8]]))
;(println (m*s [[2 0] [-3 45] [900 -12]] 1 2 -30))
;(println (m*v [[2 0] [-3 45] [900 -12] [21 0]] [2 -1 0 1]))
;(println (m*m [[1 0] [0 1]] [[13 -900] [0 1345]] [[1 0] [0 1]]))
;(println (transpose [[1 2] [3 4] [5 6] [7 8]]))
; just delay!

; vectors

(defn get-size [n arr]
  "return the length of n-th axis. if n = 0 works as count"
  (if (= n 0) (count arr) (get-size (- n 1) (arr 0))))
(defn sizes-eq? [& args]
  "checks if all the vector has the same length"
  {:pre [(every? vector? args)]}
  (apply = (mapv count args)))
(defn num-vec? [v]
  "checks if argument is numeric vector"
  (and (vector? v) (every? number? v)))
(defn vec-op [op]
  (fn [& vectors]
    {:pre [(every? num-vec? vectors) (apply sizes-eq? vectors)]}
    (apply mapv op vectors)))
(def v+ (vec-op +))
(def v- (vec-op -))
(def v* (vec-op *))
(defn scalar [& vectors]
  {:pre [(every? num-vec? vectors) (apply sizes-eq? vectors)]}
  (reduce + 0 (apply v* vectors)))
(defn vec-mul [v u] [(- (* (v 1) (u 2)) (* (v 2) (u 1)))
                     (- (* (v 0) (u 2)) (* (v 2) (u 0)))
                     (- (* (v 0) (u 1)) (* (v 1) (u 0)))])
(defn vect [& vectors]
  {:pre [(every? num-vec? vectors) (apply sizes-eq? vectors)]}
  (reduce vec-mul vectors))
(defn v*s [v & scalars]
  {:pre [(num-vec? v) (every? number? scalars)]}
  (mapv (fn [a] (apply * a scalars)) v))

; matrices

(defn num-mat? [m]
  "checks if argument is numeric matrix"
  (and (vector? m) (every? num-vec? m) (apply sizes-eq? m)))
(defn mat-op [op]
  (fn [& matrices]
    {:pre [(every? num-mat? matrices) (apply sizes-eq? matrices) (apply sizes-eq? (mapv (fn [m] (m 0)) matrices))]}
    (apply mapv op matrices)))
(def m+ (mat-op v+))
(def m- (mat-op v-))
(def m* (mat-op v*))
(defn m*s [A & scalars]
  {:pre [(num-mat? A) (every? number? scalars)]}
  (mapv (fn [v] (apply v*s v scalars)) A))
(defn m*v [A v]
  {:pre [(num-mat? A) (num-vec? v) (sizes-eq? [A v])]}
  (apply v+ (mapv v*s A v)))
(defn mat-mul [A B]
  {:pre [(= (get-size 0 A) (get-size 1 B))]}
  (mapv (fn [v] (m*v A v)) B))
(defn m*m [& matrices]
  {:pre [(every? num-mat? matrices) (true)]}
  (reduce mat-mul matrices))
(defn transpose [A]
  {:pre [(num-mat? A)]}
  (apply mapv vector A))

; tensors

(defn check-shape [t sh]
  {:pre [(vector? t) (num-vec? sh)]}
  (letfn [
          (check-shape' [t shape level]
            (if (= level (count shape))
              (number? t)
              (and
                (vector? t)
                (= (shape level) (count t))
                (every? (fn [comp] (check-shape' comp shape (+ 1 level))) t))))
          ]
    (check-shape' t sh 0)))

(defn get-shape [t]                                         ; should return nil if it is not tensor
  {:pre [(or (number? t) (vector? t))]}
  "Return the array with lengths of axis. If argument is scalar it returns []"
  (letfn [
          (get-shape' [t array-to-fill]
            (if (number? t)
              array-to-fill
              (get-shape' (t 0) (conj (conj array-to-fill (count t))))))

          (shape-or-nil [t shape]
            (if (check-shape t shape) shape nil))
          ]
    (shape-or-nil t (get-shape' t []))))

(defn tensor? [t]
  (or
    (number? t)
    (and
      (vector? t)
      (every? tensor? t)
      (not (= nil (get-shape t)))))

(defn ten-op [op-end-of-rec]
  (fn [& tensors]
    {:pre [(every? tensor? tensors) (apply = (mapv get-shape tensors))]}
    (apply
      (fn rec-fun [& comps]
        (if (every? number? comps)
          (apply op-end-of-rec comps)
          (apply mapv rec-fun comps)))
      tensors)))
(def t+ (ten-op +))
(def t- (ten-op -))
(def t* (ten-op *))



(def ten [[[1] [4]] [[2] [6]]])
(println ten)
(println (str "tensor? " (tensor? ten)))
(def shape (get-shape ten))
(println (str "shape " shape))
(println (str "shape? " (check-shape ten shape)))


; (println (check-shape [[[1] [2] [3]] [[4] [5] [6]]] shape))
;(println (scalar [60 80 90] [1 2 3] [100 100 0]))
;(def vectors [[60 80 90] [1 2 3] [100 100 0]])
;(println (apply v* [[60 80 90] [1 2 3] [100 100 0]]))
;(println (m- [[2 0] [-3 45] [900 -12]] [[54 2] [7 -0] [+0 34]] [[43 34] [1 -1] [29 8]]))
;(println (m*s [[2 0] [-3 45] [900 -12]] 1 2 -30))
;(println (m*v [[2 0] [-3 45] [900 -12] [21 0]] [2 -1 0 1]))
;(println (m*m [[1 0] [0 1]] [[13 -900] [0 1345]] [[1 0] [0 1]]))
;(println (transpose [[1 2] [3 4] [5 6] [7 8]]))
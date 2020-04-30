(defn v+ [& vectors] (apply mapv + vectors))                                ; сделать с произвольным числом аргументов!!!
(defn v- [& vectors] (apply mapv - vectors))
(defn v* [& vectors] (apply mapv * vectors))
(defn scalar [& vectors] (apply reduce + 0 (apply v* vectors)))
(defn vect [& vectors] (reduce
      (fn [v u] [(- (* (v 1) (u 2)) (* (v 2) (u 1)))
       (- (* (v 0) (u 2)) (* (v 2) (u 0)))
       (- (* (v 0) (u 1)) (* (v 1) (u 0)))])
      vectors))
(defn v*s [v & scalars] (mapv (fn [a] (apply * a scalars)) v))

(defn m+ [& matrices] (apply mapv v+ matrices))
(defn m- [& matrices] (apply mapv v- matrices))
(defn m* [& matrices] (apply mapv v* matrices))
(defn m*s [A & scalars] (mapv (fn [v] (apply v*s v scalars)) A))
(defn m*v [A & vectors] ())
(defn m*m [& matrices] ())
(defn transpose [A] (
                      def B 1
                      ))

;(println (scalar [60 80 90] [1 2 3] [100 100 0]))
(def vectors [[60 80 90] [1 2 3] [100 100 0]])
;(println (apply v* vectors))
(println (scalar [60 80 90] [100 100 0]))
(defn v+ [&vectors] (mapv + &vectors))                                ; сделать с произвольным числом аргументов!!!
(defn v- [&vectors] (mapv - &vectors))
(defn v* [&vectors] (mapv * &vectors))
(defn scalar [&vectors] (reduce + 0 (v* &vectors)))
(defn vect [&vectors] (reduce
      (fn [v u] [(- (* (v 1) (u 2)) (* (v 2) (u 1)))
       (- (* (v 0) (u 2)) (* (v 2) (u 0)))
       (- (* (v 0) (u 1)) (* (v 1) (u 0)))]) 
      vectors))
(defn v*s [v &scalars] (mapv (fn [a] (* a &scalars)) v))

(defn m+ [&matrixes] (mapv v+ &matrixes))
(defn m- [&matrixes] (mapv v- &matrixes))
(defn m* [&matrixes] (mapv v* &matrixes))
(defn m*s [A &scalars] (mapv (fn [v] (v*s v &scalars)) A))
(defn m*v [A &vectors] ())
(defn m*m [&matrixes] ())
(defn transpose [A] (
                      def B =
                      ))

(println (m*s [[60 80 90] [1 2 3]] 100 100))
(defn v+ [v u] (mapv + v u))                                ; сделать с произвольным числом аргументов!!!
(defn v- [v u] (mapv - v u))
(defn v* [v u] (mapv * v u))
(defn scalar [v u] (reduce + 0 (v* v u)))
(defn vect [v u]
      [(- (* (v 1) (u 2)) (* (v 2) (u 1)))
       (- (* (v 0) (u 2)) (* (v 2) (u 0)))
       (- (* (v 0) (u 1)) (* (v 1) (u 0)))])
(defn v*s [v s] (mapv (fn [a] (* s a)) v))

(defn m+ [A B] (mapv v+ A B))
(defn m- [A B] (mapv v- A B))
(defn m* [A B] (mapv v* A B))
(defn m*s [A s] (mapv (fn [v] (v*s v s)) A))
(defn m*v [A v] ())
(defn m*m [A B] ())
(defn transpose [A] (
                      def B =
                      ))

(println (m*s [[60 80 90] [1 2 3]] 100))
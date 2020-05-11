(defn trace [x]
  (println "\t" x)
  x)

(defn add-app [arg1-val arg2-val]
  (trace "add-app")
  (+ arg1-val arg2-val arg1-val arg2-val))

(defn add-norm [arg1-expr arg2-expr]
  (trace "add-norm")
  (+ (eval arg1-expr) (eval arg2-expr) (eval arg1-expr) (eval arg2-expr)))

(defn add-lazy [arg1-delayed arg2-delayed]
  (trace "add-lazy")
  (+ (force arg1-delayed) (force arg2-delayed) (force arg1-delayed) (force arg2-delayed)))

(def arg1 (trace 100))
(def arg2 (trace -100))
(def arg1-expr '(trace 100))
(def arg2-expr (list 'trace -100))
(def arg1-delayed (delay (trace 100)))
(def arg2-delayed (delay (trace -100)))

(add-app arg1 arg2)
(println)
(add-norm arg1-expr arg2-expr)
(println)
(add-lazy arg1-delayed arg2-delayed)
; just delay!

(defn constant [val]
  (fn [vars] val))
(defn variable [name]
  (fn [vars] (vars name)))

(defn make-op [op-impl]
  (fn [& args]
    (fn [vars]
      (apply op-impl
             (mapv (fn [arg] (arg vars)) args)))))

(def add (make-op +))
(def subtract (make-op -))
(def multiply (make-op *))
(def divide (make-op /))
(def negate (make-op -))

(def expr
  (subtract
    (multiply
      (constant 2)
      (variable "x"))
    (constant 3)))

(println (expr {"x" 2}))
(doc cons)
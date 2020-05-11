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
(def negate (make-op #(- 0 %1)))

(def operators
  {'+ add '- subtract '* multiply '/ divide 'negate negate})
(defn parse-ops [expr]
  (cond
    (list? expr) (mapv parse-ops expr)
    (number? expr) expr
    (= nil (operators expr)) (str expr)
    :else (operators expr)))
(defn parseFunction [string]
  (eval (parse-ops (read-string string))))

(def expr
  (subtract
    (multiply
      (constant 2)
      (variable "x"))
    (constant 3)))
(println (expr {"x" 100 "y" -100 "z" 0}))
(def expr2 (parseFunction "(+ 3000 -900)"))
(println (expr2 {"x" 100 "y" -100 "z" 0}))

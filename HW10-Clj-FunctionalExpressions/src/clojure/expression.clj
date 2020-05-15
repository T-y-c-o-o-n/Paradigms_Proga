; code for pass

(def constant constantly)
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
(def divide (make-op #(/ (double %1) (double %2))))
(def negate (make-op #(- %)))
(def med (make-op (fn [& args] (nth (sort args) (int (Math/floor (/ (count args) 2)))))))
(def avg (make-op (fn [& args] (/ (apply + args) (count args)))))

(def operators-fun
  {'+ add '- subtract '* multiply '/ divide 'negate negate 'med med 'avg avg})
(defn parse-fun [expr]
  (cond
    (list? expr) (apply (operators-fun (first expr)) (map parse-fun (rest expr)))
    (number? expr) (constant expr)
    :else (variable (str expr))))
(def parseFunction (comp parse-fun read-string))

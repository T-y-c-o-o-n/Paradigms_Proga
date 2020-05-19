; code for pass

; functions
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
(defn div [& args] (apply / (mapv double args)))
(def divide (make-op div))
(def negate (make-op #(- %)))
(def med (make-op (fn [& args] (nth (sort args) (int (Math/floor (/ (count args) 2)))))))
(def avg (make-op (fn [& args] (/ (apply + args) (count args)))))



; Objects
(defn proto-get [obj key]
  (cond
    (contains? obj key) (obj key)
    (contains? obj :prototype) (proto-get (obj :prototype) key)
    :else nil))
(defn field [field-name]
  (fn [this] (proto-get this field-name)))
(defn method [method-name]
  (fn rrrrr [this & args] (apply (proto-get this method-name) this args)))
(defn make-Class [Class-proto Class-constructor]
  (fn [& args] (apply Class-constructor {:prototype Class-proto} args)))


(def toString (method :toString))
(def evaluate (method :evaluate))
(def diff (method :diff))


(def _val (field :val))
(def Const-proto
  {:toString (fn [this] (format "%.1f" (_val this)))
   :evaluate (fn [this vars] (_val this))})
(defn Const-cnstr [this val]
  (assoc this :val val))
(def Constant (make-Class Const-proto Const-cnstr))

(def zero (Constant 0))
(def one (Constant 1))

(def _name (field :name))

(def Var-proto
  {:toString (fn [this] (_name this))
   :evaluate (fn [this vars] (vars (_name this)))})
(defn Var-cnstr [this name]
  (assoc this :name name))
(def Variable (make-Class Var-proto Var-cnstr))

(def _args (field :args))
(def _op (field :op))
(def _eval-impl (method :eval-impl))
(def _prototype (field :prototype))

(def Operation-proto
  {:toString (fn [this] (str "(" (_op this) " " (reduce #(str (toString %1) " " (toString %2)) (_args this)) ")"))
   :evaluate (fn [this vars] (apply (_eval-impl) (mapv #(evaluate % vars) (_args this))))})
(defn Operation-cnstr [this & args]
  (assoc this :args args))
(def Operation (make-Class Operation-proto Operation-cnstr))


(def Add-proto (assoc Operation-proto :op "+" :eval-impl +))
(defn Add-cnstr [this & args] (assoc this :args args))
(def Add (make-Class Add-proto Add-cnstr))

(def Sub-proto (assoc Operation-proto :op "-" :eval-impl -))
(def Sub-cnstr Operation-cnstr)
(def Subtract (make-Class Sub-proto Sub-cnstr))

(def Mul-proto (assoc Operation-proto :op "*" :eval-impl *))
(def Mul-cnstr Operation-cnstr)
(def Multiply (make-Class Mul-proto Mul-cnstr))

(def Div-proto (assoc Operation-proto :op "/" :eval-impl div))
(def Div-cnstr Operation-cnstr)
(def Divide (make-Class Div-proto Div-cnstr))

(def Neg-proto (assoc Operation-proto :op "negate" :eval-impl #(- %)))
(def Neg-cnstr Operation-cnstr)
(def Negate (make-Class Neg-proto Neg-cnstr))

;(def expr (Variable_ "x"))
;(def expr (Const_ 190))
;(def expr1 (Divide_ (Const_ 123) (Variable_ "x")))
;(def expr (Negate_ expr1))
;(println (_toString expr))
;(println (_evaluate expr {"x" 3}))



(def operators-obj
  {'+ Add '- Subtract '* Multiply '/ Divide 'negate Negate})
(def operators-fun
  {'+ add '- subtract '* multiply '/ divide 'negate negate 'med med 'avg avg})

(defn parse-fun [expr]
  (cond
    (list? expr) (apply (operators-fun (first expr)) (map parse-fun (rest expr)))
    (number? expr) (constant expr)
    :else (variable (str expr))))
(defn parse-obj [expr]
  (cond
    (list? expr) (apply (operators-obj (first expr)) (map parse-obj (rest expr)))
    (number? expr) (Constant expr)
    :else (Variable (str expr))))

(def parseFunction (comp parse-fun read-string))
(def parseObject (comp parse-obj read-string))

(def expr (Add (Constant -100) (Variable "x")))
;(def expr (parseObject "(+ x y)"))
;(println expr)
(println (toString expr))
;(println evaluate)
;(println (evaluate expr {"z" 0.0, "x" 0.0, "y" 0.0}))

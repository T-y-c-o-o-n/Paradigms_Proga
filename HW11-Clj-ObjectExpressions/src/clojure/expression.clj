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
(def divide (make-op #(/ (double %1) (double %2))))
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
  (fn [this & args] (apply (proto-get this method-name) this args)))
(defn make-Class [Class-proto Class-constructor]
  (fn [& args] (apply Class-constructor {:prototype Class-proto} args)))

(def _toString (method :toString))
(def _evaluate (method :evaluate))

(def _val (field :val))
(def Const-proto
  {:toString (fn [this] (str (_val this)))
   :evaluate (fn [this vars] (_val this))})
(defn Const-cnstr [this val]
  (assoc this :val val))
(def Const_ (make-Class Const-proto Const-cnstr))

(def _name (field :name))
(def Var-proto
  {:toString (fn [this] (_name this))
   :evaluate (fn [this vars] (vars (_name this)))})
(defn Var-cnstr [this name]
  (assoc this :name name))
(def Variable_ (make-Class Var-proto Var-cnstr))

(def _args (field :args))

(def Add-proto
  {:toString (fn [this] (str "(+ " (reduce #(str (_toString %1) " " (_toString %2)) (_args this)) ")"))
   :evaluate (fn [this vars] (apply + (mapv #(_evaluate % vars) (_args this))))})
(defn Add-cnstr [this & args]
  (assoc this :args args))
(def Add_ (make-Class Add-proto Add-cnstr))

(def Sub-proto
  {:toString (fn [this] (str "(- " (reduce #(str (_toString %1) " " (_toString %2)) (_args this)) ")"))
   :evaluate (fn [this vars] (apply - (mapv #(_evaluate % vars) (_args this))))})
(defn Sub-cnstr [this & args]
  (assoc this :args args))
(def Subtract_ (make-Class Sub-proto Sub-cnstr))

(def Mul-proto
  {:toString (fn [this] (str "(* " (reduce #(str (_toString %1) " " (_toString %2)) (_args this)) ")"))
   :evaluate (fn [this vars] (apply * (mapv #(_evaluate % vars) (_args this))))})
(defn Mul-cnstr [this & args]
  (assoc this :args args))
(def Multiply_ (make-Class Mul-proto Mul-cnstr))

(def Div-proto
  {:toString (fn [this] (str "(/ " (reduce #(str (_toString %1) " " (_toString %2)) (_args this)) ")"))
   :evaluate (fn [this vars] (apply #(/ (double %1) (double %2)) (mapv #(_evaluate % vars) (_args this))))})
(defn Div-cnstr [this & args]
  (assoc this :args args))
(def Divide_ (make-Class Div-proto Div-cnstr))

(def Neg-proto
  {:toString (fn [this] (str "(negate " (_toString (nth (_args this) 0)) ")"))
   :evaluate (fn [this vars] (- (_evaluate (nth (_args this) 0) vars)))})
(defn Neg-cnstr [this & args]
  (assoc this :args args))
(def Negate_ (make-Class Neg-proto Neg-cnstr))

;(def expr (Variable_ "x"))
;(def expr (Const_ 190))
(def expr1 (Divide_ (Const_ 123) (Variable_ "x")))
(def expr (Negate_ expr1))
(println (_toString expr))
(println (_evaluate expr {"x" 3}))


(def Add_ 1)
(def Subtract_ 1)
(def Multiply_ 1)
(def Divide_ 1)
(def Negate_ 1)

(def operators-obj
  {'+ Add_ '- Subtract_ '* Multiply_ '/ Divide_ 'negate Negate_})
(def operators-fun
  {'+ add '- subtract '* multiply '/ divide 'negate negate 'med med 'avg avg})

(defn parse-fun [expr]
  (cond
    (list? expr) (apply (operators-fun (first expr)) (map parse-fun (rest expr)))
    (number? expr) (constant expr)
    :else (variable (str expr))))
(defn parse-obj [expr]
  (cond
    (list? expr) (apply (operators-obj (first expr)) (map parse-fun (rest expr)))
    (number? expr) (Const_ expr)
    :else (Variable_ (str expr))))

(def parseFunction (comp parse-fun read-string))
(def parseObject (comp parse-obj read-string))


;(definterface IExpression
;  (evaluate [vars])
;  (toString []))
;(deftype Const [val]
;  IExpression
;  (evaluate [vars] x)
;  (toString [] (str val)))
;(deftype Variable [name]
;  IExpression
;  (evaluate [vars] (vars name))
;  (toString [] name))
;(deftype AbstractOperation [& args]
;  IExpression
;  (evaluate [vars] (apply .evalImpl (mapv args (partial evaluate vars))))
;  (toString [] (str (apply str "(" (.op) args) ")")))
;(deftype Add [& args]
;  AbstractOperation
;  (evalImpl [& arg] (apply + args)))
;(deftype Subtract [& args]
;  AbstractOperation)
;(deftype Multiply [& args]
;  AbstractOperation)
;(deftype Divide [& args]
;  AbstractOperation)
;(deftype Negate [arg] )
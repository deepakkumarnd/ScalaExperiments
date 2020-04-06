/*
  val and var

  val and var are used to define new values and variables in scala the difference is that val creates an immutable value,
  that is you cannot reassign variable with a different value, whereas var allows you to modify the value.
  Scala has type inference therefore so you don't have to specify the type always. But if you have to specify the type
  then you can do so by using a colon.
*/

val a = 10

// or val a: Int = 10

// a = 20 // If you uncomment this means you are trying to reassign a new value to a, this will result in an error.

var b = 10
// or var b: Int = 10

b = 20 // Reassignment is allowed for var

/*
  Properties and methods of a class

  Properties are defined in a class using val or var, the methods are defined using def keywork. When defining a public
  property scala rewrites it to read and write accessors depending on whether the property is a val or var.a
*/

class Person1(name: String) {
  def myName: String = s"My name is ${name}"
}

val p1 = new Person1("Lucifer")

p1.myName

/*
  Here a private val name is created for the Person1 class. Since the value is private it can't be accessed like
  p1.name. If you want to access the name outside the class you can either add an accessor method or you can define it
  as follows.
*/

class Person2(val name: String) { }

val p2 = new Person2("Noah")
p2.name

/*
  Now if you want to modify the name later you can't do it because of val, but if you define it with a var you can modify
  the name.
*/

class Person3(var name: String) { }

val p3 = new Person3("Deepak")
p3.name
p3.name = "Arjun"
p3.name

/*
  case class

  Case classes are syntax sugar for combining behaviour of Person1 and Person2 examples. Using case class you can
  simplify object creation and get the public read accessor in less number of lines of code. Case classes are generally
  used to create data objects.
*/

case class Person4(name: String)

val p4 = Person4("Vishnu")
p4.name

/*
  Principle of uniform access

  According to principle of uniform access a module should allow uniform access to its properties and behaviour. That
  means the caller need not know whether you are accessing a property calling a method, you should be able to do it
  uniformly without worrying about the implementation.

  In scala when you create a property which is public scala creates an accessor method. If you are creating a var then
  scala created a write accessor as well.
*/

class Vehicle1(k: String) {
  val kind = k
}

/*
  The above class will be rewritten by the compiler as follows. Using name Vehicle2 for convenience.
*/

class Vehicle2(k: String) {

  // these values are called backing values
  private[this] val _kind: String = k

  def kind = _kind
}

val v2 = new Vehicle2("Car")

v2.kind

/*
  If there was a need for write accessor then it would be as follows
*/

class Vehicle3(k: String) {
  private[this] var _kind: String = k

  // backing variable
  def  kind = _kind

  def kind_=(str: String) = _kind = str
}

val v3 = new Vehicle3("Jeep")
v3.kind
v3.kind = "Bus" // or v3.kind_=("Bus")
v3.kind

/*
  Interesting thing is the backing variable can be anything for example it can be a database or a cache or even a file.
  The implementation is hidden to the outside world.
*/

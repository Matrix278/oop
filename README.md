# Learning OOP from Go

This repository is a small, practical course for learning object-oriented
programming in Java while constantly comparing it with Go.

The goal is not to memorize definitions. We introduce each concept only when
our code creates a problem that the concept can solve.

## How we learn

> One small idea, one simple example, one tiny challenge.

For every lesson:

1. Explain the idea like a story.
2. Build a small Java example.
3. Compare it with Go.
4. Complete a tiny challenge.
5. Explain the code in plain language before moving on.

## Learning path

| Lesson | Child-friendly meaning | OOP concept | Progress |
|---|---|---|---|
| 1 | Things and the plans used to create them | Classes and objects | Done |
| 2 | What a thing remembers | Fields, state, and constructors | Done |
| 3 | What a thing can do | Methods and behavior | Done |
| 4 | Protecting what is inside a thing | Encapsulation and visibility | Done |
| 5 | Different things performing the same action | Polymorphism and interfaces | Done |
| 6 | A child type receiving behavior from a parent | Inheritance and overriding | Done |
| 7 | Things working together instead of becoming each other | Composition and dependency injection | Next |
| 8 | Shared rules versus shared implementation | Interfaces and abstract classes | Not started |
| 9 | Put everything together | Small OOP application | Not started |

## The four traditional OOP pillars

These are often called pillars or principles, not “types of OOP”:

| Pillar | Child-friendly meaning | Where we learn it |
|---|---|---|
| Encapsulation | Protect the inside of an object | Lesson 4 |
| Abstraction | Show a simple control and hide the machinery | Lessons 5 and 8 |
| Inheritance | A child class receives things from a parent class | Lesson 6 |
| Polymorphism | The same request produces different behavior | Lesson 5 |

Composition is not one of the traditional four pillars, but it is one of the
most important ways to design software. Go strongly favors it, so Lesson 7
compares it directly with inheritance.

## Lesson 1: Classes and objects

A class is a plan. An object is a real thing created from that plan.

```text
Cat class (the plan)
├── name and age (what a cat knows)
└── meow() (what a cat can do)

new Cat("Milo", 2) -> an actual Cat object
```

One class can create many independent objects:

```text
Cat class
├── Milo object
└── Luna object
```

### Tiny challenge 1: Create the first object

Build a `Cat` class that:

1. Stores a cat's name.
2. Receives the name through its constructor.
3. Has a `meow()` method.
4. Creates an actual cat named Milo in `main()`.

Expected output:

```text
Milo says meow!
```

Understanding check:

> Which part is the plan, and which value is the actual object?

Status: completed.

## Lesson 2: Fields, state, and constructors

Think of every object as carrying its own backpack of information:

```text
Milo object
├── name: "Milo"
└── age: 2

Luna object
├── name: "Luna"
└── age: 5
```

`name` and `age` are fields. The values currently stored in those fields are
the object's state. Changing Milo's state does not change Luna's state.

A constructor prepares the starting state when a new object is created:

```java
Cat(String name, int age) {
    this.name = name;
    this.age = age;
}
```

Read `this.name = name` as:

```text
this.name = name
    ↑         ↑
this Cat's   value received by
field        the constructor
```

`this` means “this particular object.”

### Java example

```java
public class Main {
    public static void main(String[] args) {
        Cat milo = new Cat("Milo", 2);
        Cat luna = new Cat("Luna", 5);

        milo.meow();
        luna.meow();
    }
}

class Cat {
    String name;
    int age;

    Cat(String name, int age) {
        this.name = name;
        this.age = age;
    }

    void meow() {
        System.out.println(
            name + " is " + age + " years old and says meow!"
        );
    }
}
```

### Go equivalent

```go
package main

import "fmt"

type Cat struct {
	name string
	age  int
}

func NewCat(name string, age int) *Cat {
	return &Cat{
		name: name,
		age:  age,
	}
}

func (c *Cat) Meow() {
	fmt.Printf("%s is %d years old and says meow!\n", c.name, c.age)
}

func main() {
	milo := NewCat("Milo", 2)
	luna := NewCat("Luna", 5)

	milo.Meow()
	luna.Meow()
}
```

Both programs produce:

```text
Milo is 2 years old and says meow!
Luna is 5 years old and says meow!
```

### Tiny challenge 2: Give every object its own state

Extend `Cat` so that:

1. It stores both `name` and `age`.
2. Its constructor receives and saves both values.
3. `main()` creates Milo, age 2, and Luna, age 5.
4. `meow()` prints the state belonging to the receiving object.

Expected output:

```text
Milo is 2 years old and says meow!
Luna is 5 years old and says meow!
```

Understanding check:

> Why does changing Milo's state not change Luna's state?

Status: completed.

## Lesson 3: Methods and behavior

Fields describe what an object knows. Methods describe what it can do.

```text
Cat
├── state: name, age
└── behavior: meow(), haveBirthday()
```

Methods can read an object's state and safely change it. Our new Java method
increases the age of the particular cat that receives the call:

```java
public void haveBirthday() {
    age = age + 1;
    System.out.println(name + " is now " + age + " years old!");
}
```

Calling it on Milo changes only Milo:

```java
milo.haveBirthday();
```

```text
Before: Milo is 2, Luna is 5
After:  Milo is 3, Luna is 5
```

The Go equivalent uses an explicit pointer receiver because the method changes
the original value:

```go
func (c *Cat) HaveBirthday() {
	c.age++
	fmt.Printf("%s is now %d years old!\n", c.name, c.age)
}
```

Java makes the current object available as `this`. Go writes the current value
explicitly as the receiver `c`.

### Tiny challenge 3: Let an object change itself

Add `haveBirthday()` so that:

1. It increases only the receiving cat's age by one.
2. It prints the cat's name and new age.
3. It is called for Milo but not Luna.
4. Calling `meow()` afterward proves that the new state was saved.

Expected output:

```text
Milo is now 3 years old!
Milo is 3 years old and says meow!
Luna is 5 years old and says meow!
```

Understanding check:

> Why does `milo.haveBirthday()` change Milo but not Luna?

Status: completed.

## Lesson 4: Encapsulation and visibility

Before encapsulation, outside code could put a cat into an invalid state:

```java
milo.age = -100;
milo.name = "";
```

Encapsulation protects the state and exposes meaningful behavior instead:

```java
class Cat {
    private String name;
    private int age;

    public Cat(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void meow() {
        System.out.println(
            name + " is " + age + " years old and says meow!"
        );
    }

    public void haveBirthday() {
        age = age + 1;
        System.out.println(name + " is now " + age + " years old!");
    }
}
```

The two visibility keywords used here mean:

- `private`: only code inside `Cat` can directly access the member.
- `public`: other code is allowed to use the constructor or method.

The compiler proves that the protection is real. Trying to access the private
field from `Main` produces:

```text
error: age has private access in Cat
    milo.age = -100;
        ^
```

After removing that invalid access, outside code can still ask the object to
change itself through its public behavior:

```java
milo.haveBirthday();
milo.meow();
```

This is the public surface of the object:

```text
Main
  │
  │ public methods
  ▼
┌──────────────────────────┐
│ Cat                      │
│                          │
│ private name             │
│ private age              │
│                          │
│ public haveBirthday()    │
│ public meow()            │
└──────────────────────────┘
```

Encapsulation does not mean automatically adding a getter and setter for every
field. A public `setAge(-100)` would expose the same problem under a different
name. Prefer meaningful operations such as `haveBirthday()` that keep the
object valid.

### Encapsulation in Go

Go uses capitalization rather than `private` and `public` keywords:

```go
type Cat struct {
	name string // unexported
	age  int    // unexported
}

func NewCat(name string, age int) *Cat { // exported
	return &Cat{name: name, age: age}
}

func (c *Cat) HaveBirthday() { // exported
	c.age++
}
```

There is an important difference:

- Java `private` protects a member from every other class, even in the same
  package.
- Go lowercase names are hidden from other packages, but code in the same
  package can still access them.

For a stronger Go boundary, place `Cat` in its own package and let other
packages use only `NewCat`, `Meow`, and `HaveBirthday`.

### Tiny challenge 4: Let the compiler protect the object

1. Change `name` and `age` to `private`.
2. Make the constructor and behavior methods `public`.
3. Temporarily try `milo.age = -100` inside `main()`.
4. Compile and read the protection error.
5. Remove the invalid line and run the program normally.

Expected compiler error:

```text
error: age has private access in Cat
    milo.age = -100;
        ^
```

Understanding check:

> Why is `milo.haveBirthday()` allowed while `milo.age = -100` is rejected?

Status: completed.

## Lesson 5: Polymorphism and interfaces

Polymorphism means that different objects can receive the same request and
respond in their own way:

```text
speak()
├── Cat object -> meow
└── Dog object -> woof
```

An interface describes a promise without choosing the implementation:

```java
interface Animal {
    void speak();
}
```

Interface methods are public contracts. A class implementing `speak()` must
therefore make its implementation `public` too.

`Cat` and `Dog` explicitly promise to provide that behavior:

```java
class Cat implements Animal {
    public void speak() {
        System.out.println("Meow!");
    }
}

class Dog implements Animal {
    public void speak() {
        System.out.println("Woof!");
    }
}
```

One method can now work with any implementation of `Animal`:

```java
static void makeItSpeak(Animal animal) {
    animal.speak();
}
```

The parameter type is only `Animal`, but the real object decides which method
runs:

```java
makeItSpeak(milo); // real object is Cat -> Cat.speak()
makeItSpeak(rex);  // real object is Dog -> Dog.speak()
```

This runtime selection is called dynamic dispatch:

```text
Animal parameter
      │
      ├── holds Cat object -> Cat.speak()
      └── holds Dog object -> Dog.speak()
```

### Polymorphism in Go

The Go idea is almost identical:

```go
type Animal interface {
	Speak()
}

func makeItSpeak(animal Animal) {
	animal.Speak()
}

type Cat struct {
	name string
}

func (c *Cat) Speak() {
	fmt.Printf("%s says meow!\n", c.name)
}

type Dog struct {
	name string
}

func (d *Dog) Speak() {
	fmt.Printf("%s says woof!\n", d.name)
}
```

The important language difference is:

- Java uses explicit implementation: `class Cat implements Animal`.
- Go uses implicit implementation: having `Speak()` is enough to satisfy
  `Animal`.

An interface is also our first example of abstraction. The caller knows the
simple action `speak()` but does not need to know how each animal produces its
sound.

### Tiny challenge 5: One request, different behavior

1. Create an `Animal` interface containing `speak()`.
2. Make `Cat` implement it and meow through `speak()`.
3. Create a `Dog` that implements it and woofs through `speak()`.
4. Add `makeItSpeak(Animal animal)`.
5. Pass both Milo and Rex to that same method.

Expected focused output:

```text
Milo is 2 years old and says meow!
Rex says woof!
```

Understanding check:

> How can `makeItSpeak` call the correct method without checking whether the
> object is a Cat or Dog?

Status: completed.

## Lesson 6: Inheritance, shared state, and `super`

Inheritance lets a child class receive state or behavior from a parent class.
Java uses `extends`:

```text
Pet
├── private name
├── getName()
└── sleep()
    │
    ├── Cat
    │   ├── age
    │   ├── speak()
    │   └── haveBirthday()
    │
    └── Dog
        └── speak()
```

The shared parent owns the duplicated name and sleeping behavior:

```java
class Pet {
    private String name;

    public Pet(String name) {
        this.name = name;
    }

    protected String getName() {
        return name;
    }

    public void sleep() {
        System.out.println(name + " is sleeping.");
    }
}
```

`protected getName()` can be used by child classes such as `Cat` and `Dog`.
Java also allows protected access from other classes in the same package.

The child classes receive that behavior while still satisfying `Animal`:

```java
class Cat extends Pet implements Animal {
    private int age;

    public Cat(String name, int age) {
        super(name);
        this.age = age;
    }

    public void speak() {
        System.out.println(
            getName() + " is " + age + " years old and says meow!"
        );
    }
}

class Dog extends Pet implements Animal {
    public Dog(String name) {
        super(name);
    }

    public void speak() {
        System.out.println(getName() + " says woof!");
    }
}
```

Read this declaration in two parts:

```java
class Cat extends Pet implements Animal
```

- `extends Pet`: Cat inherits implementation from one parent class.
- `implements Animal`: Cat promises to provide an interface behavior.

Java classes can extend only one class, but they can implement multiple
interfaces.

### How Java recognizes a constructor

A constructor:

1. Has exactly the same name as its class.
2. Has no return type—not even `void`.

```java
class Pet {
    public Pet(String name) { // constructor
    }

    public void Pet(String name) { // ordinary method, not a constructor
    }
}
```

Constructors can have different parameter lists:

```java
Pet()
Pet(String name)
Pet(String name, int age)
```

Java chooses the matching constructor from the number and types of arguments.

### What `super(name)` means

`super` refers to the direct parent part of the current object. Because `Cat`
declares `extends Pet`, this line:

```java
super(name);
```

means:

> Call the `Pet` constructor that accepts a `String`.

Creating Milo follows this order:

```text
new Cat("Milo", 2)
        │
        ▼
Cat constructor
├── super("Milo")
│       │
│       ▼
│   Pet(String) constructor starts
│       │
│       ▼
│   Object() runs first as the ultimate parent
│       │
│       ▼
│   Pet(String) stores name = "Milo"
│
└── this.age = 2

Result: one Cat object
├── inherited Pet part: name = "Milo"
└── Cat-specific part: age = 2
```

`super(name)` does not create a separate `Pet`. It initializes the parent part
of the same `Cat` object. A parent-constructor call must be the first statement
in a child constructor.

If no `super(...)` is written, Java tries to insert `super()` automatically.
That would fail here because `Pet` has `Pet(String)`, not a zero-argument
`Pet()` constructor.

### `this` versus `super`

| Java expression | Meaning |
|---|---|
| `this.age` | Use a member of this object |
| `this(...)` | Call another constructor in the same class |
| `super(name)` | Call a constructor in the direct parent class |
| `super.sleep()` | Call the parent's implementation of a method |

### Method overriding

A child can replace inherited behavior with its own version. This is called
overriding:

```java
class Cat extends Pet {
    @Override
    public void sleep() {
        System.out.println(getName() + " curls up and sleeps.");
    }
}
```

`@Override` asks the compiler to verify that a matching parent or interface
method really exists. Inside the replacement, the child can still call the
parent version:

```java
@Override
public void sleep() {
    super.sleep();
    System.out.println("The cat starts dreaming.");
}
```

### Go uses composition and embedding instead

Go does not have class inheritance or `super`. A Go type contains another value
instead:

```go
type Pet struct {
	name string
}

func NewPet(name string) Pet {
	return Pet{name: name}
}

func (p Pet) Name() string {
	return p.name
}

func (p Pet) Sleep() {
	fmt.Printf("%s is sleeping.\n", p.name)
}

type Cat struct {
	Pet
	age int
}

func NewCat(name string, age int) *Cat {
	return &Cat{
		Pet: NewPet(name),
		age: age,
	}
}
```

Embedding promotes the `Pet` methods, so this is convenient:

```go
milo := NewCat("Milo", 2)
milo.Sleep()
```

But the model is different:

```text
Java inheritance: Cat is a Pet
Go composition:    Cat contains a Pet
```

Go has no constructor chain. `NewCat` explicitly creates the embedded `Pet`
with `NewPet(name)`. Go usually combines composition with interfaces instead of
building deep inheritance trees.

### Tiny challenge 6A: Inherit behavior

1. Create a `Pet` parent class with a public `sleep()` method.
2. Make both `Cat` and `Dog` extend `Pet`.
3. Keep both classes implementing `Animal`.
4. Call `milo.sleep()` and `rex.sleep()` without copying `sleep()` into either
   child class.

First expected output:

```text
The pet is sleeping.
The pet is sleeping.
```

Understanding check:

> Where does Java find `sleep()` when the method is absent from Cat and Dog?

Status: completed.

### Tiny challenge 6B: Share state through the parent

1. Move the duplicated `name` field from `Cat` and `Dog` into `Pet`.
2. Keep the parent field private.
3. Give `Pet` a constructor and protected `getName()` behavior.
4. Call `super(name)` from both child constructors.
5. Keep `age` inside `Cat` because it is not shared by every pet.
6. Make inherited `sleep()` print the stored name.

Final expected sleeping output:

```text
Milo is sleeping.
Rex is sleeping.
```

Understanding checks:

> Why does `name` belong in Pet while `age` remains in Cat?

> Does `super(name)` create a second Pet object, or initialize part of the same
> Cat/Dog object?

Status: completed.

## Java and Go connection

| Java | Go | Meaning |
|---|---|---|
| `class Cat` | `type Cat struct` | Define the kind of thing |
| `String name` | `name string` | Store text in a field |
| `int age` | `age int` | Store a whole number in a field |
| `Cat(...)` | `NewCat(...)` | Create initialized values |
| `this.name` | `c.name` | Access this value's field |
| `void meow()` | `func (c *Cat) Meow()` | Attach behavior to the data |
| `private int age` | `age int` | Hide state from outside consumers |
| `public void haveBirthday()` | `func (c *Cat) HaveBirthday()` | Expose safe behavior |
| `implements Animal` | implicit interface satisfaction | Promise shared behavior |
| `extends Pet` | embed `Pet` | Reuse state and behavior, with different semantics |
| `super(name)` | `Pet: NewPet(name)` | Initialize the reused parent/component state |
| `new Cat(...)` | `NewCat(...)` | Create a new value |

Java puts fields, constructors, and methods inside the class. Go declares the
struct first and attaches methods outside it. Go does not have constructors as
a language feature; `NewCat` is a normal function following a Go convention.

## Why `String` is capitalized but `int` is not

`String` is a class, and Java class names normally begin with a capital letter.
`int` is one of Java's small built-in primitive types, whose names are
lowercase.

```java
String name = "Milo"; // class/object type
int age = 2;          // primitive type
```

Unlike Go, Java does not have a built-in type named `int32`. Java's `int` is a
signed 32-bit integer. Java also has an `Integer` class, which we will discuss
later when we need it.

## Compile and run

Java requires the public `Main` class to be stored in `Main.java` with matching
capitalization.

Compile the Java source:

```bash
javac Main.java
```

This produces `Main.class`, `Animal.class`, `Pet.class`, `Cat.class`, and
`Dog.class`. These are generated bytecode files, not source code, so do not edit
or commit them.

Run the compiled `Main` class:

```bash
java Main
```

This two-command form works reliably across Java versions:

```text
Main.java --javac--> Main.class --java/JVM--> running program
```

To try the Go equivalent, save it as `main.go` and run:

```bash
go run main.go
```

`go run` performs the build and run steps for you.

## Lessons 1–6 completion check

Before moving on, we should be able to explain:

- A class is a plan; an object is created from that plan.
- Every object has its own state.
- Fields store an object's state.
- A constructor gives a new object its starting state.
- `this` refers to the current Java object.
- `String` is a class and `int` is a primitive type.
- Java compilation and execution are separate steps.
- Methods are behavior attached to an object.
- A method call changes only the object that receives it.
- `private` protects Java state from outside access.
- `public` exposes behavior that other code may use.
- Encapsulation is controlled access, not getters and setters everywhere.
- Go visibility is package-based rather than class-based.
- An interface defines a shared behavior contract.
- Java implements interfaces explicitly; Go satisfies them implicitly.
- Polymorphism lets one interface call dispatch to different real objects.
- `extends` inherits from a class; `implements` satisfies an interface.
- Child objects can receive state and behavior from their parent class.
- `super(...)` selects and calls a matching direct-parent constructor.
- Parent construction happens before child-specific initialization.
- Java uses inheritance; Go normally uses composition and embedding.

Next: composition and dependency injection—objects working together without
becoming parent and child types.

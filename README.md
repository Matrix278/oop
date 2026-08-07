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
| 5 | Different things performing the same action | Polymorphism and interfaces | Next |
| 6 | A child type receiving behavior from a parent | Inheritance and overriding | Not started |
| 7 | Things working together instead of becoming each other | Composition and dependency injection | Not started |
| 8 | Shared rules versus shared implementation | Interfaces and abstract classes | Not started |
| 9 | Put everything together | Small OOP application | Not started |

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

This produces `Main.class` and `Cat.class`. These are generated bytecode files,
not source code, so do not edit or commit them.

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

## Lessons 1–4 completion check

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

Next: polymorphism—different objects responding to the same action in their
own way.

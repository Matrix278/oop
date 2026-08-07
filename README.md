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
| 7 | Things working together instead of becoming each other | Composition and dependency injection | Done |
| 8 | Shared rules versus shared implementation | Interfaces and abstract classes | Done |
| 9 | Put everything together | Small OOP application | Done |

## The four traditional OOP pillars

These are often called pillars or principles, not “types of OOP”:

| Pillar | Child-friendly meaning | Where we learn it |
|---|---|---|
| Encapsulation | Protect the inside of an object | Lessons 4 and 9 |
| Abstraction | Show a simple control and hide the machinery | Lessons 5, 8, and 9 |
| Inheritance | A child class receives things from a parent class | Lessons 6 and 9 |
| Polymorphism | The same request produces different behavior | Lessons 5 and 9 |

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

## Lesson 7: Composition and dependency injection

Inheritance models an **is-a** relationship. Composition models a **has-a**
relationship:

```text
Cat is a Pet    -> inheritance
Cat has a Toy   -> composition
```

A cat is not a kind of toy, so `Cat extends Toy` would describe the wrong
relationship. Instead, the cat stores another object in a field:

```java
class Cat extends Pet implements Animal {
    private int age;
    private Toy toy;
}
```

The objects now form a small team:

```text
milo -> Cat object
        ├── inherited Pet state
        ├── age: 2
        └── toy -> Toy object: "a ball"
```

### What is a dependency?

A dependency is something an object needs to do part of its work. `Cat` needs
a `Toy` to perform `play()`, so `Toy` is a dependency of `Cat`.

The cat could create the dependency internally:

```java
public Cat(String name, int age) {
    super(name);
    this.age = age;
    this.toy = new Toy("a ball");
}
```

That works, but it forces every cat to choose and construct its own ball. The
choice is hidden inside `Cat` and cannot be changed by the code creating it.

### What is dependency injection?

Dependency injection means creating a dependency outside an object and giving
it to that object:

```java
public Cat(String name, int age, Toy toy) {
    super(name);
    this.age = age;
    this.toy = toy;
}
```

The creation code chooses the dependencies:

```java
Toy ball = new Toy("a ball");
Toy mouse = new Toy("a toy mouse");

Cat milo = new Cat("Milo", 2, ball);
Cat luna = new Cat("Luna", 5, mouse);
```

The full flow is:

```text
Main creates Toy
      │
      ▼
Main passes Toy to Cat constructor
      │
      ▼
Cat stores Toy in a private field
      │
      ▼
Cat.play() delegates to Toy.useBy()
```

No framework, annotation, or container is required. Constructor injection is
ordinary object creation with a dependency passed as an argument.

### Delegation and collaboration

`Toy` owns toy-related behavior:

```java
class Toy {
    private String name;

    public Toy(String name) {
        this.name = name;
    }

    public void useBy(String petName) {
        System.out.println(petName + " plays with " + name + "!");
    }
}
```

`Cat` delegates instead of doing the toy's work itself:

```java
public void play() {
    toy.useBy(getName());
}
```

Delegation means:

> Ask the object responsible for the work to perform it.

### Composition and injection in Go

The same design is common Go:

```go
type Toy struct {
	name string
}

func NewToy(name string) *Toy {
	return &Toy{name: name}
}

func (t *Toy) UseBy(petName string) {
	fmt.Printf("%s plays with %s!\n", petName, t.name)
}

type Cat struct {
	Pet
	age int
	toy *Toy
}

func NewCat(name string, age int, toy *Toy) *Cat {
	return &Cat{
		Pet: NewPet(name),
		age: age,
		toy: toy,
	}
}

func (c *Cat) Play() {
	c.toy.UseBy(c.Name())
}
```

Java and Go both use a field for composition and a constructor/factory
parameter for injection. Go simply expresses construction with an ordinary
`NewCat` function instead of a language-level constructor.

### Is dependency injection always good?

Dependency injection is a tool, not a rule that every object must use.

It helps when:

- Different objects need different implementations or configurations.
- A dependency is expensive or should be shared.
- Tests need a small fake dependency instead of a real external service.
- Construction decisions should stay outside the business object.

It can be unnecessary when:

- The value is a tiny internal implementation detail that will never vary.
- Injecting it makes the constructor harder to understand without providing a
  useful choice.
- Many layers merely pass the same dependency around without using it.

The goal is not “inject everything.” The goal is to make important
collaborators explicit while keeping simple internal details simple.

Dependency injection is also different from dependency inversion:

- Dependency injection is the technique of giving an object its dependencies.
- Dependency inversion is a broader design principle about depending on stable
  abstractions rather than unstable details.
- A dependency-injection framework is optional automation, not DI itself.

### Tiny challenge 7A: Give each cat a toy

1. Create `Toy` with a private name, constructor, and `useBy()` behavior.
2. Add a private `Toy toy` field to `Cat`.
3. Receive and store the toy through the `Cat` constructor.
4. Create a ball and toy mouse in `Main`.
5. Inject the ball into Milo and the mouse into Luna.
6. Add `play()` and delegate to `toy.useBy(getName())`.
7. Do not call `new Toy(...)` inside `Cat`.

Expected additional output:

```text
Milo plays with a ball!
Luna plays with a toy mouse!
```

Understanding checks:

> Which line demonstrates composition?

> Which line performs dependency injection?

> Why can Milo and Luna use different toys without changing the Cat class?

Status: completed.

## Lesson 8: Interfaces, abstract classes, and abstraction

An interface and an abstract class both describe incomplete ideas, but they
solve different problems.

The `Animal` interface is a promise:

```java
interface Animal {
    void speak();
}
```

It says what an Animal must do, but it owns no ordinary per-object state and
does not decide how the sound is produced.

`Pet` is different. Every pet shares real state and working behavior:

```text
Pet
├── name
├── sleep()
└── speak() -> unknown until we know the real kind of pet
```

A generic Pet is therefore only partially defined. Java represents that with
an abstract class:

```java
abstract class Pet implements Animal {
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

    public abstract void speak();
}
```

### Why make Pet abstract?

Without `abstract`, Java would allow this:

```java
Pet mystery = new Pet("Mystery");
```

But the program cannot answer an important question:

```text
What sound does a generic Pet make?
```

Poor alternatives would be:

- Invent a meaningless default sound.
- Leave `speak()` empty.
- Throw an exception only after the program is running.
- Allow objects that are not complete enough to use correctly.

`abstract` moves that problem to compile time:

```text
Pet is abstract; cannot be instantiated
```

It communicates:

> Pet contains useful shared pieces, but only a concrete child such as Cat or
> Dog is a complete object.

An abstract class does not always need an abstract method. However, any class
containing an abstract method must itself be declared abstract.

### What does an abstract method do?

This declaration has no method body:

```java
public abstract void speak();
```

It tells every concrete Pet child:

> You must finish this missing behavior before Java will allow objects of your
> class to be created.

The children provide the missing part:

```java
class Cat extends Pet {
    @Override
    public void speak() {
        System.out.println(getName() + " says meow!");
    }
}

class Dog extends Pet {
    @Override
    public void speak() {
        System.out.println(getName() + " says woof!");
    }
}
```

`@Override` asks the compiler to check that the method really matches a parent
or interface declaration.

### Why does an abstract class have a constructor?

The abstract class cannot become a standalone final object, but its state still
forms part of every real child object:

```text
new Cat("Milo", ...)
        │
        ▼
Cat constructor
        │ super("Milo")
        ▼
Pet constructor stores name in the Pet part
        │
        ▼
Cat constructor finishes the Cat-specific part
```

So these statements are different:

```java
new Pet("Mystery"); // forbidden: tries to create an incomplete final object
super(name);        // allowed: initializes the Pet part of a concrete child
```

### Interface versus abstract class

| Interface | Abstract class |
|---|---|
| Describes a contract or capability | Describes a partially built parent |
| Has no constructor | Can have constructors |
| Has no ordinary per-object instance fields | Can own per-object state |
| Can declare abstract/default behavior | Can mix abstract and working methods |
| A class can implement many interfaces | A class can extend only one class |
| Uses `implements` | Uses `extends` |

Modern Java interfaces can also contain `default`, `static`, and private helper
methods. They still do not replace an abstract class that must own ordinary
per-object state and participate in a constructor chain.

### Where is abstraction?

Abstraction means exposing the useful control while hiding details the caller
does not need:

```java
animal.speak();
```

The caller does not need to know:

- Whether the object is Cat or Dog.
- Where its name is stored.
- How its output is constructed.
- Which constructor chain initialized it.

The simple `speak()` contract is the abstraction; the hidden implementation is
the machinery behind it.

### Go comparison

Go has interfaces but no abstract classes:

```go
type Animal interface {
	Speak()
}
```

Go normally combines that contract with an ordinary reusable struct:

```go
type Pet struct {
	name string
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

func (c *Cat) Speak() {
	fmt.Printf("%s says meow!\n", c.Name())
}
```

The Go interface forces `Speak()` at compile time. Embedding `Pet` reuses state
and behavior. Unlike Java, Go does not require every `Animal` to embed `Pet`;
those are separate design choices composed together.

### When should an abstract class be used?

It can fit when:

- The child types have a real is-a relationship with the parent.
- Closely related children share state or working behavior.
- A standalone parent object would be incomplete or invalid.
- Some behavior must be supplied by every concrete child.

Prefer an interface or composition when:

- Types only share a capability, not a common identity and state.
- A class needs several independent capabilities.
- The relationship is has-a rather than is-a.
- Reusing behavior would create an artificial inheritance tree.

### Tiny challenge 8A: Protect an incomplete parent

1. Change `Pet` to `abstract class Pet implements Animal`.
2. Add `public abstract void speak();` to `Pet`.
3. Remove the repeated `implements Animal` from `Cat` and `Dog`.
4. Add `@Override` above both concrete `speak()` implementations.
5. Temporarily try `new Pet("Mystery")`.
6. Compile and observe the protection error.
7. Remove the invalid line and verify the normal program again.

Expected compiler error:

```text
Pet is abstract; cannot be instantiated
```

Understanding checks:

> Why is a generic Pet incomplete while Cat and Dog are complete?

> Why is `super(name)` legal even though `new Pet(name)` is not?

> What does the interface provide that the abstract class does not, and vice
> versa?

Status: completed.

## Lesson 9: Final application—Pet Daycare

The final application does not introduce another OOP mechanism. It combines the
ideas from the earlier lessons and gives each object one clear responsibility.

`Main` creates and connects the objects:

```java
Toy ball = new Toy("a ball");
Toy mouse = new Toy("a toy mouse");

Cat milo = new Cat("Milo", 2, ball);
Cat luna = new Cat("Luna", 5, mouse);
Dog rex = new Dog("Rex");

Pet[] pets = {milo, luna, rex};

PetDaycare daycare = new PetDaycare(pets);
daycare.startDay();

milo.play();
luna.play();
```

This role for `Main` is sometimes called the composition root: it is the place
where the application creates objects and connects their dependencies.

`PetDaycare` owns the daily routine:

```java
class PetDaycare {
    private Pet[] pets;

    public PetDaycare(Pet[] pets) {
        this.pets = pets;
    }

    public void startDay() {
        for (Pet pet : pets) {
            pet.speak();
            pet.sleep();
        }
    }
}
```

The responsibilities are now separated:

```text
Main
└── Creates and connects the application objects

PetDaycare
└── Knows the steps in a daycare day

Pet
├── Owns the shared name
└── Provides shared sleeping behavior

Cat and Dog
└── Provide type-specific speaking behavior

Toy
└── Owns toy-related behavior
```

### A group containing different concrete objects

This array uses the common abstract parent type:

```java
Pet[] pets = {milo, luna, rex};
```

The references have type `Pet`, but the real objects keep their concrete types:

```text
Pet[]
├── Pet reference -> Cat object: Milo
├── Pet reference -> Cat object: Luna
└── Pet reference -> Dog object: Rex
```

Inside the loop:

```java
for (Pet pet : pets) {
    pet.speak();
    pet.sleep();
}
```

The reference type controls which methods are available. The runtime object
controls which overridden implementation runs:

```text
pet.speak()
├── real object is Cat -> Cat.speak()
└── real object is Dog -> Dog.speak()
```

`pet.play()` is unavailable because `play()` is not part of the shared `Pet`
contract. `milo.play()` remains valid because `milo` has the more specific
compile-time type `Cat`.

### All four OOP pillars in the final application

| Pillar | Where it appears | What it achieves |
|---|---|---|
| Encapsulation | `private` fields in `Pet`, `Cat`, `Toy`, and `PetDaycare` | Objects control direct access to their state |
| Abstraction | `Animal`, abstract `Pet`, and `daycare.startDay()` | Callers use simple operations without knowing internal steps |
| Inheritance | `Cat extends Pet` and `Dog extends Pet` | Related child types reuse shared pet state and behavior |
| Polymorphism | `Pet[]` and `pet.speak()` in the loop | One operation dispatches to Cat or Dog behavior |

#### Encapsulation

```java
private Pet[] pets;
private String name;
private int age;
private Toy toy;
```

Each object protects the state it owns. Other code uses meaningful behavior
such as `startDay()`, `speak()`, `sleep()`, and `play()`.

#### Abstraction

```java
daycare.startDay();
```

`Main` does not need to know that starting a day means looping over pets and
calling two methods. `startDay()` exposes the useful idea and hides those
details.

The `Animal` interface similarly exposes `speak()` without exposing how each
animal produces its sound.

#### Inheritance

```java
class Cat extends Pet
class Dog extends Pet
```

Cat and Dog are kinds of Pet. They reuse the inherited name and `sleep()`
behavior while completing their own `speak()` behavior.

#### Polymorphism

```java
for (Pet pet : pets) {
    pet.speak();
}
```

The same source line produces a meow for Cat objects and a woof for Dog
objects. No `if` statement needs to inspect the concrete class.

### Supporting techniques: composition and dependency injection

Composition and dependency injection are not members of the traditional four
pillars, but they connect the application:

```java
private Pet[] pets;                 // Daycare has Pets: composition
public PetDaycare(Pet[] pets)       // Daycare declares a dependency
this.pets = pets;                   // Store the injected dependency
new PetDaycare(pets);               // Main performs the injection
```

This keeps construction decisions in `Main` and daycare behavior in
`PetDaycare`.

### Go version of the daycare boundary

Go can describe the behavior needed by the daycare with an interface:

```go
type DaycarePet interface {
	Speak()
	Sleep()
}

type PetDaycare struct {
	pets []DaycarePet
}

func NewPetDaycare(pets []DaycarePet) *PetDaycare {
	return &PetDaycare{pets: pets}
}

func (d *PetDaycare) StartDay() {
	for _, pet := range d.pets {
		pet.Speak()
		pet.Sleep()
	}
}
```

Concrete Cat and Dog values satisfy `DaycarePet` implicitly when they provide
both methods. Their embedded `Pet` supplies `Sleep()`, while each concrete type
supplies `Speak()`:

```go
pets := []DaycarePet{milo, luna, rex}
daycare := NewPetDaycare(pets)
daycare.StartDay()
```

Java uses an abstract parent array here; Go uses an interface slice. Both let
the daycare operate on different concrete types through one shared view.

### Tiny challenge 9A: Treat different pets uniformly

1. Put Milo, Luna, and Rex into `Pet[] pets`.
2. Use a for-each loop with a `Pet` loop variable.
3. Call `speak()` and `sleep()` through that shared variable.
4. Remove the duplicated individual calls.
5. Keep Cat-specific `play()` calls on the Cat variables.

Expected output:

```text
Milo is 2 years old and says meow!
Milo is sleeping.
Luna is 5 years old and says meow!
Luna is sleeping.
Rex says woof!
Rex is sleeping.
Milo plays with a ball!
Luna plays with a toy mouse!
```

Understanding check:

> Why can a `Pet` variable call the correct Cat/Dog `speak()` implementation
> but not the Cat-only `play()` method?

Status: completed.

### Tiny challenge 9B: Move the routine into PetDaycare

1. Create `PetDaycare` with a private `Pet[] pets` field.
2. Inject the array through its constructor.
3. Move the loop from `Main` into `startDay()`.
4. Replace the loop in `Main` with `daycare.startDay()`.
5. Verify that the output remains exactly unchanged.

Understanding checks:

> Which responsibility moved from Main to PetDaycare?

> Where do composition, dependency injection, abstraction, and polymorphism
> appear in the new class?

Status: completed.

## Important lines and the ideas they demonstrate

| Important Java line | OOP idea | Plain meaning |
|---|---|---|
| `abstract class Pet implements Animal` | Abstract parent and interface contract | Pet shares implementation but remains incomplete |
| `class Cat extends Pet` | Inheritance | Cat completes and specializes Pet |
| `public abstract void speak()` | Required child behavior | Every concrete Pet must provide a sound |
| `@Override` | Compiler-checked replacement | The child completes or replaces inherited behavior |
| `private int age` | Encapsulation | Outside code cannot directly change the field |
| `public Cat(...)` | Constructor | Prepare a new Cat's starting state |
| `super(name)` | Parent construction | Initialize the inherited Pet part |
| `this.age = age` | Object state | Save a parameter in this Cat |
| `public void speak()` | Behavior and polymorphism | This type supplies its own response to `speak()` |
| `Pet[] pets = {milo, luna, rex}` | Polymorphic collection | Store different concrete children through one parent type |
| `for (Pet pet : pets)` | Uniform processing | Run one routine for every concrete Pet kind |
| `pet.speak()` | Runtime polymorphism | Dispatch to Cat or Dog implementation |
| `private Toy toy` | Composition | Cat has a Toy |
| `Cat(..., Toy toy)` | Declared dependency | Cat states that it needs a Toy |
| `this.toy = toy` | Constructor injection | Store the dependency supplied from outside |
| `new Cat("Milo", 2, ball)` | Injection at creation | Main chooses and gives Milo his Toy |
| `toy.useBy(getName())` | Delegation | Cat asks Toy to perform toy-related work |
| `private Pet[] pets` | Composition and encapsulation | PetDaycare owns a protected group of Pets |
| `PetDaycare(Pet[] pets)` | Constructor injection | Daycare receives its required Pets |
| `daycare.startDay()` | Abstraction | Start the routine without exposing its loop and steps |

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
| `abstract class Pet` | interface plus embedded/concrete `Pet` | Model an incomplete shared parent without a direct Go equivalent |
| `abstract void speak()` | `Animal` requires `Speak()` | Force concrete behavior at compile time |
| `extends Pet` | embed `Pet` | Reuse state and behavior, with different semantics |
| `super(name)` | `Pet: NewPet(name)` | Initialize the reused parent/component state |
| `private Toy toy` | `toy *Toy` | Compose an object from another object |
| `Cat(..., Toy toy)` | `NewCat(..., toy *Toy)` | Declare an injected dependency |
| `toy.useBy(...)` | `c.toy.UseBy(...)` | Delegate work to the collaborator |
| `Pet[]` | `[]DaycarePet` | Hold different concrete values through one shared type |
| `for (Pet pet : pets)` | `for _, pet := range pets` | Process every value uniformly |
| `new PetDaycare(pets)` | `NewPetDaycare(pets)` | Inject the application group |
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

This produces `Main.class`, `Animal.class`, `Pet.class`, `Cat.class`,
`Dog.class`, `Toy.class`, and `PetDaycare.class`. These are generated bytecode
files, not source code, so do not edit or commit them.

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

## Complete learning-path check

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
- Composition models a has-a relationship between collaborating objects.
- A dependency is something an object needs to do its work.
- Constructor injection supplies that dependency from outside the object.
- Delegation asks the responsible collaborator to perform its part of the work.
- Dependency injection is useful when it creates a meaningful choice, not as a
  ritual applied to every value.
- An interface defines a contract without owning ordinary per-object state.
- An abstract class can combine shared state, constructors, working methods,
  and unfinished methods.
- Abstract classes cannot be instantiated, but their constructors initialize
  the parent part of concrete child objects.
- Abstract methods force concrete children to provide missing behavior.
- Go combines interfaces with composition and embedding instead of abstract
  classes.
- A parent-typed array can hold multiple concrete child types.
- The reference type controls visible operations; the runtime object controls
  overridden behavior.
- A coordinating class such as `PetDaycare` can hide a workflow behind one
  meaningful method.
- The final application contains encapsulation, abstraction, inheritance, and
  polymorphism working together.

The core learning path is complete. Future extensions can add validation,
multiple toy implementations, tests, packages, collections, or persistence
without changing these foundational ideas.

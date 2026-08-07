# Learning OOP from Go

This repository is a small, practical course for understanding object-oriented
programming through Java while constantly comparing it with Go.

The goal is not to memorize definitions. We will learn each concept only when
our code creates a problem that the concept can solve.

## How we learn

> One small idea, one simple example, one tiny challenge.

For every lesson:

1. Explain the idea like a story.
2. Build a small Java example.
3. Compare it with Go.
4. Complete a tiny challenge.
5. Explain the code in our own words before moving on.

## Learning path

| Lesson | Child-friendly meaning | OOP concept | Progress |
|---|---|---|---|
| 1 | Things and the plans used to create them | Classes and objects | Done |
| 2 | What a thing remembers | Fields, state, and constructors | Next |
| 3 | What a thing can do | Methods and behavior | Not started |
| 4 | Protecting what is inside a thing | Encapsulation and visibility | Not started |
| 5 | Different things performing the same action | Polymorphism and interfaces | Not started |
| 6 | A child type receiving behavior from a parent | Inheritance and overriding | Not started |
| 7 | Things working together instead of becoming each other | Composition and dependency injection | Not started |
| 8 | Shared rules versus shared implementation | Interfaces and abstract classes | Not started |
| 9 | Put everything together | Small OOP application | Not started |

## Current checkpoint: `Cat`

The first exercise creates a cat named Milo and asks it to meow.

```text
Cat class (the plan)
├── name (what a cat knows)
└── meow() (what a cat can do)

cat variable -> the actual Cat object named Milo
```

Expected output:

```text
Milo says meow!
```

What Lesson 1 teaches:

- `Cat` is a class: a plan for creating cats.
- `new Cat("Milo")` creates an object from that plan.
- `name` belongs to each individual cat object.
- The constructor gives a new cat its starting name.
- `meow()` is behavior owned by a cat.

## Java and Go connection

| Java | Go |
|---|---|
| `class Cat` | `type Cat struct` |
| `Cat(String name)` | `NewCat(name string)` |
| `this.name` | `c.name` |
| `void meow()` | `func (c *Cat) Meow()` |
| `new Cat("Milo")` | `NewCat("Milo")` |

The syntax is different, but the basic idea is familiar: keep related state and
behavior together.

## Running a lesson

Java requires a public class named `Main` to be stored in a file named
`Main.java` with matching capitalization.

```bash
javac Main.java
java Main
```

We move to the next lesson only after the current idea can be explained in
plain language.

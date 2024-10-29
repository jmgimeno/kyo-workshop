build-lists: true
slidenumbers: true
autoscale: false
slide-transition: move(left)

<!-- https://idiomaticsoft.com/post/2024-01-02-effect-systems/ -->

# [fit] Introduction to Kyo
## Adam Hearn

---
# Who am I?
- ???

---

# What is Kyo?

- Kyo is a powerful toolkit for developing with Scala
- Built from a series of standalone modules:
    - `kyo-data`: Low allocation, performant collections
    - `kyo-prelude`: Side-effect free *Algebraic effects*
    - `kyo-core`: prelude + IO & Async
    - `kyo-scheduler`: high performance adaptive scheduler
        - `kyo-scheduler-zio`: runs your ZIO app!

---
# What are Algebraic Effects?

^ Before we dive into what Algebraic effects, let's talk about Effects in general.

---
# What are ~~Algebraic~~ Effects?

* Effects - single most overloaded term in FP
* Effects are descriptions of what you want
* Effects are backed by suspension
    * Suspension defers a computation until later
    * Separation of execution from definition enables
        * Flexibility in execution (Retry, Delay, etc)
        * Delayed implementation (Clock.live vs Clock.test)

---
# What's an Effect System?

* Structured framework to manage and reason about Effects
* Separates the concern of 'what' from 'how'
* Relies on suspension to defer evaluation

---
# What are Algebraic Effects?
<!-- TODO: Revisit this; simplify it -->

* Key components:
    * Effects: Operations that can be performed
    * Handlers: Define how effects are interpreted
* Allows for:
    * Fine-grained control over effect handling
    * Composability of different effects
    * Separation of effect declaration and implementation
* Differs from exceptions by allowing resumption of computation

---

# Why use Algebraic Effects?


^ TODO

---

# Why use Kyo?

- Kyo brings advanced algebraic effects to Scala
- Designed for simplicity and performance
- Includes core effects, as well as a framework for custom effects

---
# Kyo Syntax

```scala
val _: String < IO = IO("Hello scala.io!")
```

* Infix 'Pending' Type: `Result < Effects`
  * `String < IO`
* Effects are represented as unordered set:
  * `File < (IO & Resource)`'

---
# IO: Side-Effect Suspension



---
# Env: Dependency Injection
```
trait DB:
  def apply(id: Int): String < IO

val program: Record < (Env[DB] & IO) = 
  for
    config <- Env.get[DB]
    result <- IO(s"Connecting to ${config.url}")
  yield result

// Usage
val config = Config("http://example.com")
val result = program.provideEnv(config).eval
```

---
# Abort: Short Circuit



---
# Kyo: Effect Widening

```scala
val a: Int < IO = IO(42)
val b: Int < (IO & Abort[Exception]) = a
val c: Int < (IO & Async & Abort[Exception]) = b
```

- Computations can be widened to include more effects
- Allows for flexible and composable code
- Plain values can be widened to Kyo computation
  - Values aren't suspended & don't allocate [^1]

[^1]: Primitives widened to Kyo will box as Scala 3 does not support proper specialization
---
# Kyo: Branching


^ TODO

---

# Kyo: Flattening

- Effects can be easily combined using `map` and `flatMap`
- Resulting type includes all unique pending effects

---

# Kyo: Effect Handling

```scala
val a: Int < Abort[Exception] = 42
val b: Result[Exception, Int] < Any = Abort.run(a)
val c: Result[Exception, Int] = b.eval
```

* Effects are handled explicitly
* Order of handling can affect the result type and value

---
# KyoApp


---


# Direct Syntax in Kyo

```scala
val a: String < (Abort[Exception] & IO) =
    defer {
        val b: String = await(IO("hello"))
        val c: String = await(Abort.get(Right("world")))
        b + " " + c
    }
```

* `defer` and `await` provide a more intuitive syntax

---

# Conclusion

- Kyo provides a powerful yet simple way to work with algebraic effects
- Offers composability, type safety, and performance
- Enables cleaner, more modular functional programming in Scala

---

# Questions?


- !all effects suspend!
- Suspension + Handling
- ZIO each map/flatMap introduces suspension/resumption

- Kyo continues execution until an effect needs to be handled
https://koka-lang.github.io/koka/doc/index.html


Kyo supports a wide set of audience
- Library Authors
- Novices



---

Wrap up:

- Effects
- Kyo:
  - map vs flatMap
  - widening
  - IO/Abort/Async/Env/
  - Running your App

TODO: Kyo Data
kyo-scheduler
package org.adamhearn

import kyo.*
import kyo.Result.*
import kyo.test.KyoSpecDefault
import zio.test.assertTrue
import zio.test.assertCompletes
import zio.test.TestAspect.ignore
import zio.test.TestResult

/** kyo-prelude & kyo-core contain many useful effects.
  *
  * These exercises are intented to give a strong intuition about effects. They are not intended to
  * be comprehensive in using/combining all effects.
  */
object `01_Effects` extends KyoSpecDefault {
  def spec =
    suite("effects")(
      test("Abort") {

        /** Exercise 0: Abort
          *
          * Abort is short circuiting effect that can fail with a value of type `E`.
          *
          * Effects generally include a `run` method that will handle that effect, returning a new
          * Kyo computation. When all effects are handled, the final result is `A < Any`. `.eval`
          * will convert a `A < Any` to `A`, evaluating any remaining suspensions.
          */
        case class Fatal() extends Exception
        lazy val fail: Int < Abort[String]  = ???
        lazy val panic: Int < Abort[String] = ???

        assertTrue(Abort.run(fail).eval == Result.fail("fail")) &&
        assertTrue(Abort.run(panic).eval == Result.panic(Fatal()))
      } @@ ignore,
      test("branching") {

        /** Exercise 1: branching
          *
          * Kyo enables effectful branching via implicit widening. Pure values can be widened to an
          * effectful value.
          *
          * Write a function `even` that returns the input if the input is even. If the input is
          * odd, the function should fail with "odd".
          */
        def even(i: Int): Int < Abort[String] = ???

        for
          e <- Abort.run(even(42))
          o <- Abort.run(even(43))
        yield assertTrue(e == Success(42)) &&
          assertTrue(o == Fail("odd"))
      } @@ ignore,
      test("multiple") {

        /** Exercise 2: multiple effects
          *
          * Try combining multiple effects in a `for`. What is the combined type? Can test run this
          * effect?
          */
        val aborting: Int < Abort[String] = 42
        val sideEffecting: String < IO    = IO("hello")
        val mutating: Int < Var[Int]      = Var.update((i: Int) => i + 42)
        val result: TestResult            = assertTrue(true)

        val combined: TestResult < ??? = result
        combined
      } @@ ignore,
    )
}

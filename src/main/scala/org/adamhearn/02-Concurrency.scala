package org.adamhearn

import kyo.*
import kyo.Result.*
import kyo.test.KyoSpecDefault
import zio.test.assertTrue
import zio.test.assertCompletes
import zio.test.TestAspect.ignore
import zio.test.TestResult
import java.io.IOException

/** Async effect allows for the asynchronous execution of computations via a managed thread pool.
  *
  * The core function, run, spawns a new "green thread," also known as a fiber, to handle the given
  * computation.
  */
object `02_Async` extends KyoSpecDefault {
  def spec = suite("Async")(
    suite("Fiber")(
      test("fork") {
        // TODO: show how to make a fiber directly
        assertCompletes
      },
      test("join") {
        // show how to `.get` on a fiber
        // runs 'synchronously' for all maps
        assertCompletes
      },
    ),
    test("run") {

      /** Exercise: Async.run
        *
        * TODO: talk about Async and how it's not a single effect TODO: talk about 'forking' with
        * multiple pending effects `Async.run(_)
        */
      assertCompletes
    },
    test("race") {
      assertCompletes
    },
    test("timeout") {
      assertCompletes
    },
    test("local") {
      assertCompletes
    },
  )
}
object `02_Structures` extends KyoSpecDefault {
  def spec =
    suite("structures")(
      suite("Atomic")(
        test("Ref") {
          assertCompletes
        }
      ),
      suite("Queue")(
        test("bounded") {
          // TODO: show semantics of bounded queue
          // Maybe fork a fiber and then use a separate fiber to take from the queue
          assertCompletes
        }
      ),
    )
}

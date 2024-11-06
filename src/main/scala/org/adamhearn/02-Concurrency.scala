package org.adamhearn

import kyo.*
import kyo.Result.*
import kyo.test.KyoSpecDefault
import zio.test.assertTrue
import zio.test.assertCompletes
import zio.test.TestAspect.ignore
import zio.test.TestResult
import java.io.IOException

object `02_Async` extends KyoSpecDefault {
  def spec = suite("primitives")(
    test("concurrent") {
      assertCompletes
    }
  )
}
object `02_Structures` extends KyoSpecDefault {
  def spec =
    suite("structures")(
      test("concurrent") {
        assertCompletes
      }
    )
}

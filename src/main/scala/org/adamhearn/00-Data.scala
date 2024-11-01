package org.adamhearn

import kyo.Maybe
import kyo.Result
import kyo.Chunk
import kyo.test.KyoSpecDefault
import zio.test.assertTrue
import zio.test.Spec

// - Kyo-Data
//   - Maybe
//   - Result
//   - Chunk
//   - ll

object MaybeSpec extends KyoSpecDefault:
  def spec =
    suite("Maybe!")(
      test("apply") {

        /** EXERCISE 1
          *
          * kyo.Maybe works virtually the same as Scala's Option...without the allocations.
          * Maybe.apply converts a nullable argument to Absent, or Present.
          *
          * In this exercise, convert a Java Optional to a Maybe.
          */
        def fromJava(op: String): Maybe[String] = Maybe(op)

        assertTrue(fromJava(null) == Maybe.Absent) &&
        assertTrue(fromJava("hello") == Maybe.Present("hello"))
      },
      test("match") {

        /** EXERCISE 2
          *
          * Pattern match on a Maybe, with `Maybe.Absent`/`Maybe.Present`
          */
        assertTrue(true)
      },
    )

object ResultSpec extends KyoSpecDefault:
  def spec = ???

object ChunkSpec extends KyoSpecDefault:
  def spec = ???

package org.adamhearn

import kyo.{Absent, Maybe, Present}
import kyo.Result
import kyo.TypeMap
import kyo.Chunk
import kyo.test.KyoSpecDefault
import zio.test.assertTrue
import zio.test.Spec
import zio.test.TestAspect.ignore
import scala.reflect.ClassTag

/** kyo-data provides optimized collections for common data types.
  *
  * In these exercises, we'll explore Maybe, Result, Chunk, and TypeMap. These are an important
  * foundation, as many of Kyo's APIs use these structures to improve performance.
  */

object `00_Maybe` extends KyoSpecDefault:
  def spec =
    suite("Maybe[A]")(
      test("nested") {

        /** Exercise 1: Deep Pattern Matching
          *
          * Goal: Extract a deeply nested value using pattern matching Learning: Nested `Maybe`
          * values allocate at maximum 1 object.
          */
        extension [A](self: Maybe[Maybe[Maybe[Maybe[A]]]]) def superFlat: Maybe[A] = ???

        assertTrue(Maybe(Maybe(Maybe(Maybe("real")))).superFlat == Maybe("real")) &&
        assertTrue(Maybe(Maybe(Maybe(Absent))).superFlat == Absent)
      } @@ ignore,
      test("list") {

        /** Exercise 2: List[Maybe[A]] -> Maybe[List[A]]
          *
          * Goal: Implement a conversion from List[Maybe[A]] to Maybe[List[A]] Rules:
          *   - If ANY element is Absent, return Absent
          *   - If ALL elements are Present, return Present containing the list of values This
          *     demonstrates Maybe's strict handling of absence vs Option's propagation
          */
        extension [A](list: List[Maybe[A]]) def sequence: Maybe[List[A]] = ???

        assertTrue(List(Present(1), Absent, Present(2)).sequence == Absent) &&
        assertTrue(List(Present(1), Present(2)).sequence == Present(List(1, 2)))
      } @@ ignore,
    )

object `00_Result` extends KyoSpecDefault:
  def spec =
    suite("Result[E, A]")(
      test("catching") {

        /** Exercise 1: Result.catching offers a typesafe way to handle exceptions.
          *
          * It will catch all exceptions that are subtypes of the type parameter. If the exception
          * is not a subtype, it will be untracked (Panic).
          */
        case class InvalidRequest(message: String) extends Throwable
        case class SQLException()                  extends Throwable

        def businessLogic(request: String): Int =
          if request == "bad" then throw InvalidRequest(request)
          else throw SQLException()

        lazy val fail: Result[InvalidRequest, Int]  = ???
        lazy val panic: Result[InvalidRequest, Int] = ???

        assertTrue(fail.isFail) &&
        assertTrue(panic.isPanic)
      } @@ ignore,
      test("panic vs fail") {
        case class TrackedError()
        case class UntrackedError() extends Throwable

        /** Exercise 2: lift untracked errors to tracked errors
          *
          * `Panic` is a `Result[Nothing, Nothing]`, but contains a Throwable. `Fail` is a
          * `Result[E, Nothing]`, and contains an error `E`.
          */
        extension [E, A](self: Result[E, A]) def resurrect: Result[E | Throwable, A] = ???

        val fail    = Result.fail(TrackedError())
        val panic   = Result.panic(UntrackedError())
        val success = Result.success(42)

        assertTrue(fail.resurrect.isFail) &&
        assertTrue(panic.resurrect.isPanic) &&
        assertTrue(success.resurrect.isSuccess)
      } @@ ignore,
      test("TODO")(assertTrue(true)) @@ ignore,
    )

object `00_Chunk` extends KyoSpecDefault:
  def spec =
    suite("Chunk[A]")(
      test("apply/append/prepend") {

        /** Exercise 1: Chunk.apply Chunk offers performance optimized methods for working with
          * sequential data.
          *
          * Chunk can be used to append or prepend at O(1) time.
          */
        def grow(chunk: Chunk[Int], n: Int): Chunk[Int] =
          def loop(i: Int, acc: Chunk[Int]): Chunk[Int] =
            if i > n then ???
            else if i % 2 == 0 then ???
            else ???

          loop(0, chunk)

        assertTrue(grow(Chunk.empty, 1000).size == 1000)
      } @@ ignore,
      test("fromArray") {

        /** Exercise 2: Chunk.fromArray
          *
          * Chunk.fromArray offers a safe way to convert an Array to a Chunk.
          *
          * Note: elements must be a subtype of AnyRef.
          */
        lazy val array: Array[???] = ???
        lazy val chunk: Chunk[???] = ???
        assertTrue(chunk.size == array.length)
      } @@ ignore,
      test("flattenChunk") {

        /** Exercise 3: Chunk#flattenChunk
          *
          * While Chunk does extends Seq, it provides a more efficient implementation for
          * flattening. This method will only work for `Chunk[A]` where `A` is a subtype of
          * `Chunk[_]`.
          */
        lazy val chunk: Chunk[Chunk[Int]] = ???
        lazy val flattened: Chunk[Int]    = ???
        assertTrue(flattened.size == chunk.flatten.size)
      } @@ ignore,
    )
object `00_TypeMap` extends KyoSpecDefault:
  sealed trait DBConnection:
    def maxConnections: Int

  case class Postgres() extends DBConnection:
    def maxConnections = 10

  case class Redis() extends DBConnection:
    def maxConnections = 50

  case class Mongo() extends DBConnection:
    def maxConnections = 20

  def spec =
    suite("TypeMap[A]")(
      test("add/get") {

        /** Exercise 1: Create a TypeMap with a Postgres connection.
          *
          *   - What can you get from the map?
          *     - What happens if you ascribe the type `TypeMap[DBConnection]`?
          *   - If you add `42`, what's the type of the map?
          */
        lazy val connections: TypeMap[Postgres] = TypeMap.empty.add(???)
        lazy val widened: TypeMap[DBConnection] = connections
        lazy val andInt                         = ???

        assertTrue(??? == Postgres()) &&
        assertTrue(connections.get[???].isInstanceOf[DBConnection]) &&
        assertTrue(??? == 42)
      } @@ ignore,
      test("prune") {

        /** Exercise 2: Experiment with `TypeMap#prune`
          *
          * First prune to just `Redis`. Then prune to just `DBConnection`.
          */
        lazy val original = TypeMap.empty
          .add(Postgres())
          .add(Redis())
          .add(Mongo())

        lazy val redis: TypeMap[Redis]                = ???
        lazy val dbConnections: TypeMap[DBConnection] = ???

        assertTrue(redis.get[Redis] == Redis()) &&
        assertTrue(dbConnections.get[???] == ???)
      } @@ ignore,
      test("union") {

        /** Exercise 3: Create and combine two TypeMaps
          */
        lazy val dbs = TypeMap(Postgres())

        lazy val config = TypeMap(42, "str!", true)

        lazy val combined: TypeMap[???] = ???

        assertTrue(combined.size == dbs.size + config.size)
      } @@ ignore,
    )

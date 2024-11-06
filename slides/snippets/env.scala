//> using dep io.getkyo::kyo-core:0.13.2

import kyo.*

case class Coordinates(latitude: Double, longitude: Double)
case class Reading()
abstract class Sensor:
  def read: Reading < IO = Reading()
object Sensor extends Sensor

abstract class Drone:
  def fly(coordinates: Coordinates): Unit < IO = IO(println(s"$coordinates"))
object Drone extends Drone

abstract class Weather:
  def record(coordinates: Coordinates): Reading < IO

object Weather extends KyoApp:
  val live: Weather < (Env[Drone] & Env[Sensor]) =
    for
      drone <- Env.get[Drone]
      sensor <- Env.get[Sensor]
    yield new Weather:
      def record(coordinates: Coordinates): Reading < IO =
        drone.fly(coordinates).andThen(sensor.read)

  run:
    Env.runTypeMap(TypeMap(Drone, Sensor))(live).map(_.record(Coordinates(0, 0)))
//> using dep io.getkyo::kyo-core:0.13.2

import kyo.*

case class User(email: String)
object User extends KyoApp:
  import UserError._
  enum UserError:
    case InvalidEmail
    case AlreadyExists

  def from(email: String): User < Abort[UserError] =
    if !email.contains('@') then Abort.fail(InvalidEmail)
    else User(email)

  val x: Unit < IO =
    Abort
      .run(from("adam@veak.co"))
      .map:
        case Result.Success(user)      => Console.println(s"Success! $user")
        case Result.Fail(InvalidEmail) => Console.println("Bad email!")

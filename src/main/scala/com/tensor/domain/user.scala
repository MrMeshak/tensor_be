import java.util.UUID

object user {
  case class User(
      id: UUID,
      email: String,
      firstName: String,
      lastName: String,
      phone: Option[String],
  ) {}
}

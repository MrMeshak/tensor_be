package com.tensor.domain
import cats.syntax.all.*

import java.util.UUID
import skunk.{Codec}
import skunk.codec.all.*

object user {

  final case class User(
      id: UUID,
      email: String,
      password: String,
      firstName: String,
      lastName: String,
      phone: Option[String],
  ) {}

  val userCodec: Codec[User] = (uuid, varchar, varchar, varchar, varchar, varchar.opt).tupled.imap({
    case (id, email, password, firstName, lastName, phone) =>
      User(id, email, password, firstName, lastName, phone)
  })({ user =>
    (user.id, user.email, user.password, user.firstName, user.lastName, user.phone)
  })
}

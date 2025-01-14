package com.tensor.algebra

import cats.MonadThrow
import cats.implicits.*
import cats.syntax.*

import skunk.Session
import org.typelevel.log4cats.LoggerFactory

import com.tensor.domain.user.*

case class LoginDto(email: String, passward: String) {}
case class SignupDto(user: User) {}

trait AuthAlgebra[F[_]] {
  def login(data: LoginDto): F[Unit]
  def signup(data: SignupDto): F[Unit]
}

final class AuthAlgebraLive[F[_]: MonadThrow: LoggerFactory](session: Session[F])
    extends AuthAlgebra[F] {
  override def login(loginPayload: LoginDto) = ???
  override def signup(payload: SignupDto) = ???
}

object AuthAlgebraLive {
  def apply[F[_]: MonadThrow: LoggerFactory](session: Session[F]): AuthAlgebraLive[F] =
    new AuthAlgebraLive[F](session)
}

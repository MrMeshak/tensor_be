package com.tensor.algebra

import com.tensor.domain.user.*
import java.util.UUID
import skunk.Session

trait UserAlgebra[F[_]] {
  def findByEmail(email: String): F[Option[User]]
  def create(user: User): F[UUID]
  def update(user: User): F[Option[User]]
  def delete(user: User): F[Boolean]
}

final class UserAlgebraLive[F[_]](session: Session[F]) {}

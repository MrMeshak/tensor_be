package com.tensor.app

import cats.effect.Concurrent
import cats.implicits.*
import org.http4s.*
import org.http4s.dsl.*
import org.http4s.dsl.impl.*
import org.http4s.server.*

import org.typelevel.log4cats.LoggerFactory
import cats.effect.kernel.Resource
import skunk.Session

import com.tensor.app.{HealthRoutes, AuthRoutes}
import com.tensor.algebra.AuthAlgebraLive
import com.tensor.algebra.AuthAlgebra

class AppRoutes[F[_]: Concurrent: LoggerFactory](
    session: Session[F],
) {

  private val healthRoutes = HealthRoutes[F].routes
  private val authRoutes = AuthRoutes[F](AuthAlgebraLive[F](session)).routes

  val routes: HttpRoutes[F] = Router(
    "api" -> (healthRoutes <+> authRoutes),
  )
}

object AppRoutes {
  def apply[F[_]: Concurrent: LoggerFactory](session: Session[F]) = new AppRoutes[F](session)
}

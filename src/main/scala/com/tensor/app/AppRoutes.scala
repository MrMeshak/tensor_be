package com.tensor.app

import cats.effect.Concurrent
import org.http4s.*
import org.http4s.dsl.*
import org.http4s.dsl.impl.*
import org.http4s.server.*

import com.tensor.app.{HealthRoutes}
import org.typelevel.log4cats.LoggerFactory

class AppRoutes[F[_]: Concurrent: LoggerFactory] {
  private val healthRoutes = HealthRoutes[F].routes

  val routes: HttpRoutes[F] = Router(
    "api" -> healthRoutes,
  )
}

object AppRoutes {
  def apply[F[_]: Concurrent: LoggerFactory] = new AppRoutes[F]
}

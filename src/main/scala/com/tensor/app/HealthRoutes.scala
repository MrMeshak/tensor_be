package com.tensor.app

import org.http4s.*
import org.http4s.dsl.*
import org.http4s.dsl.impl.*
import org.http4s.server.*

import cats.Monad
import cats.implicits.*
import cats.syntax.*
import org.typelevel.log4cats.*
import org.typelevel.log4cats.LoggerFactory

class HealthRoutes[F[_]: Monad: LoggerFactory] extends Http4sDsl[F] {
  val logger = LoggerFactory[F].getLogger

  private val healthRoute: HttpRoutes[F] = HttpRoutes.of[F]({ case GET -> Root =>
    for {
      _ <- logger.info("this is a logger")
      resp <- Ok("server running")
    } yield resp
  })

  val routes = Router(
    "health" -> healthRoute,
  )
}

object HealthRoutes {
  def apply[F[_]: Monad: LoggerFactory] = new HealthRoutes[F]
}

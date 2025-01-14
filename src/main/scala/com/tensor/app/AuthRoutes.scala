package com.tensor.app

import org.http4s.circe.*
import io.circe.generic.auto.*
import io.circe.syntax.*
import org.http4s.circe.CirceEntityCodec.*

import org.http4s.*
import org.http4s.dsl.*
import org.http4s.dsl.impl.*
import org.http4s.server.*

import cats.effect.Concurrent
import cats.implicits.*
import cats.syntax.*
import org.typelevel.log4cats.*
import org.typelevel.log4cats.LoggerFactory

import com.tensor.logging.syntax.*
import com.tensor.algebra.AuthAlgebra
import com.tensor.algebra.{LoginDto, SignupDto}

class AuthRoutes[F[_]: Concurrent: LoggerFactory](authAlgebra: AuthAlgebra[F])
    extends Http4sDsl[F] {
  private val signupRoute: HttpRoutes[F] = HttpRoutes.of[F]({ case req @ POST -> Root / "signup" =>
    for {
      signupPayload <- req.as[SignupDto]
      resp <- Ok(signupPayload.asJson)
    } yield resp
  })

  private val loginRoute: HttpRoutes[F] = HttpRoutes.of[F]({ case req @ POST -> Root / "login" =>
    for {
      loginPayload <- req.as[LoginDto]
      resp <- Ok(loginPayload.asJson)
    } yield resp
  })

  val routes = Router(
    "auth" -> (signupRoute <+> loginRoute),
  )
}

object AuthRoutes {
  def apply[F[_]: Concurrent: LoggerFactory](authAlgebra: AuthAlgebra[F]) =
    new AuthRoutes[F](authAlgebra)
}

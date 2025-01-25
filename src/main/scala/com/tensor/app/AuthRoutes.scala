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

import com.tensor.validation.syntax.HttpValidationDsl
import com.tensor.logging.syntax.*
import com.tensor.algebra.AuthAlgebra
import com.tensor.algebra.{LoginDto, SignupDto}
import com.tensor.validation.authValidators.given

class AuthRoutes[F[_]: Concurrent: LoggerFactory](authAlgebra: AuthAlgebra[F]) extends HttpValidationDsl[F] {
  private val signupRoute: HttpRoutes[F] = HttpRoutes.of[F]({ case req @ POST -> Root / "signup" =>
    for {
      signupDto <- req.as[SignupDto]
      resp <- Ok(signupDto.asJson)
    } yield resp
  })

  private val loginRoute: HttpRoutes[F] = HttpRoutes.of[F]({ case req @ POST -> Root / "login" =>
    req.validate[LoginDto](loginDto => {
      for {
        resp <- Ok(loginDto.asJson)
      } yield resp
    })
  })

  val routes = Router(
    "auth" -> (signupRoute <+> loginRoute),
  )
}

object AuthRoutes {
  def apply[F[_]: Concurrent: LoggerFactory](authAlgebra: AuthAlgebra[F]) = new AuthRoutes[F](authAlgebra)
}

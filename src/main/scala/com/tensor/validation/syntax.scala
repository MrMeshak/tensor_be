package com.tensor.validation

import io.circe.generic.auto.*
import org.http4s.circe.CirceEntityCodec.*

import cats.*
import cats.implicits.*
import cats.data.*
import cats.data.Validated.*
import org.http4s.*
import org.http4s.implicits.*
import org.typelevel.log4cats.LoggerFactory
import org.http4s.dsl.Http4sDsl

import com.tensor.validation.{Validator, ValidationFailure}
import com.tensor.core.responses.{FailureRes}

object syntax {

  def validateEntity[A](entity: A)(using validator: Validator[A]): ValidatedNel[ValidationFailure, A] =
    validator.validate(entity)

  trait HttpValidationDsl[F[_]: MonadThrow: LoggerFactory] extends Http4sDsl[F] {

    extension (req: Request[F])
      def validate[A: Validator](
          serverLogicIfValid: A => F[Response[F]],
      )(using EntityDecoder[F, A]): F[Response[F]] = {
        req
          .as[A]
          .map(validateEntity)
          .flatMap({
            case Valid(entity)   => serverLogicIfValid(entity)
            case Invalid(errors) => BadRequest(FailureRes(errors.toList.map(_.errorMessage).mkString(", ")))
          })
      }
  }
}

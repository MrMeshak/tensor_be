package com.tensor.validation

import cats.*
import cats.implicits.*
import cats.data.{ValidatedNel}
import com.tensor.validation.{Validator, ValidationFailure}
import com.tensor.validation.baseValidators.*
import com.tensor.algebra.LoginDto

object authValidators {

  given loginDtoValidator: Validator[LoginDto] with {
    override def validate(data: LoginDto): ValidatedNel[ValidationFailure, LoginDto] = {
      val LoginDto(
        email, // should not be empty and should be email
        password, // should not be empty and should be a strong password
      ) = data;

      (
        validateEmail(email, "email"),
        validateRequired(password, "password")(_.nonEmpty),
      ).mapN(LoginDto.apply) // ValidatedNel[ValidationFailure, LoginDTO]
    }
  }

}

package com.tensor.validation

import cats.data.ValidatedNel

trait ValidationFailure(val errorMessage: String)
trait Validator[A] {
  def validate(value: A): ValidatedNel[ValidationFailure, A]
}

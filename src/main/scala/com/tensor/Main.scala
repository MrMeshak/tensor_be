package com.tensor

import cats.effect.{IO, IOApp}
import cats.*
import cats.implicits.*

object Main extends IOApp.Simple {
  override def run: IO[Unit] = {

    IO(println("hello world")) *> IO.pure(())

  }
}

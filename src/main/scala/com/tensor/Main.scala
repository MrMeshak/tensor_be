package com.tensor

import cats.effect.{IO, IOApp}
import cats.effect.implicits.*
import org.http4s.ember.server.EmberServerBuilder
import org.typelevel.log4cats.*
import org.typelevel.log4cats.slf4j.Slf4jFactory
import pureconfig.ConfigSource

import com.tensor.config.ServerConfig
import com.tensor.config.syntax.*

object Main extends IOApp.Simple {
  override def run: IO[Unit] = {
    given LoggerFactory[IO] = Slf4jFactory.create[IO]

    ConfigSource.default
      .at("server")
      .loadF[IO, ServerConfig]
      .flatMap(serverConfig => {
        EmberServerBuilder
          .default[IO]
          .withHost(serverConfig.host)
          .withPort(serverConfig.port)
          .build
          .use(_ => IO.println("tensor_be server running") *> IO.never)
      })

  }
}

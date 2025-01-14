package com.tensor

import cats.effect.{IO, IOApp}
import cats.effect.implicits.*
import cats.effect.kernel.Resource
import cats.implicits.*
import org.http4s.ember.server.EmberServerBuilder
import org.typelevel.log4cats.*
import org.typelevel.log4cats.slf4j.Slf4jFactory
import pureconfig.ConfigSource
import natchez.Trace.Implicits.noop
import skunk.Session

import com.tensor.config.ServerConfig
import com.tensor.config.DbConfig
import com.tensor.config.syntax.*
import com.tensor.app.AppRoutes
import com.tensor.core.Db

object Main extends IOApp.Simple {
  override def run: IO[Unit] = {
    given LoggerFactory[IO] = Slf4jFactory.create[IO]

    val serverR = for {
      serverConfig <- Resource.eval(ConfigSource.default.at("server").loadF[IO, ServerConfig])
      dbConfig <- Resource.eval(ConfigSource.default.at("db").loadF[IO, DbConfig])
      session <- Db.single[IO](dbConfig)
      server <- EmberServerBuilder
        .default[IO]
        .withHost(serverConfig.host)
        .withPort(serverConfig.port)
        .withHttpApp(AppRoutes[IO](session).routes.orNotFound)
        .build
    } yield server

    serverR.use(_ => IO.println("server started") *> IO.never)
  }
}

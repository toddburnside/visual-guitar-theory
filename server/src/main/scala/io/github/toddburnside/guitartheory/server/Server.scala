package io.github.toddburnside.guitartheory.server

import cats.effect._
import cats.syntax.functor._
import org.http4s.server.{Server => Http4sServer}
import org.http4s.server.blaze.BlazeServerBuilder

object Server extends IOApp {
  def createServer[F[_] : ContextShift: ConcurrentEffect: Timer] : Resource[F, Http4sServer[F]] = {
    val module = new Module[F]

    BlazeServerBuilder[F]
      .bindHttp(8080, "0.0.0.0")
      .withHttpApp(module.httpApp)
      .resource
  }

  override def run(args: List[String]): IO[ExitCode] =
    createServer.use(_ => IO.never).as(ExitCode.Success)
}


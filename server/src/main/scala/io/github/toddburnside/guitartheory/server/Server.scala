package io.github.toddburnside.guitartheory.server

import cats.effect._
import cats.syntax.functor._
import org.http4s.server.blaze.BlazeServerBuilder

object Server extends IOApp {
  override def run(args: List[String]): IO[ExitCode] =
    new HttpServer[IO].server.as(ExitCode.Success)
}

class HttpServer[F[_]: ConcurrentEffect: ContextShift: Timer] {

  private val module = new Module[F]

  def server: F[Unit] =
    BlazeServerBuilder[F]
      .bindHttp(8080, "0.0.0.0")
      .withHttpApp(module.httpApp)
      .serve
      .compile
      .drain
}

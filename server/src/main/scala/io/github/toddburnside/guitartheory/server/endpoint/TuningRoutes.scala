package io.github.toddburnside.guitartheory.server.endpoint

import cats.effect.Sync
import cats.implicits._
import io.circe.generic.auto._
import io.circe.syntax._
import io.github.toddburnside.guitartheory.server.service.TuningService
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl

class TuningRoutes[F[_]: Sync](tuningService: TuningService[F])(implicit H: ApiErrorHandler[F])
    extends Http4sDsl[F] {

  val routes: HttpRoutes[F] = HttpRoutes.of[F] {
    case GET -> Root / "tunings" =>
      for {
        tunings  <- tuningService.list
        response <- Ok(tunings.asJson)
      } yield response

    case GET -> Root / "tunings" / LongVar(id) =>
      for {
        tuning   <- tuningService.get(id)
        response <- tuning.fold(H.handle, s => Ok(s.asJson))
      } yield response
  }
}

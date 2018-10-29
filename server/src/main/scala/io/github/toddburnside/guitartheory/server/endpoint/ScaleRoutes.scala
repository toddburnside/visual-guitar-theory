package io.github.toddburnside.guitartheory.server.endpoint

import cats.effect.Sync
import cats.implicits._
import io.circe.generic.auto._
import io.circe.syntax._
import io.github.toddburnside.guitartheory.server.service.ScaleService
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl

class ScaleRoutes[F[_]: Sync](scaleService: ScaleService[F])(implicit H: HttpErrorHandler[F])
    extends Http4sDsl[F] {

  val routes: HttpRoutes[F] = HttpRoutes.of[F] {
    case GET -> Root / "scales" =>
      for {
        scales   <- scaleService.list
        response <- Ok(scales.asJson)
      } yield response

    case GET -> Root / "scales" / LongVar(id) =>
      for {
        scale    <- scaleService.get(id)
        response <- scale.fold(H.handle, s => Ok(s.asJson))
      } yield response
  }
}

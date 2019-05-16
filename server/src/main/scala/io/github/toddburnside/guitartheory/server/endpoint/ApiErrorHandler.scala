package io.github.toddburnside.guitartheory.server.endpoint

import cats.Monad
import io.github.toddburnside.guitartheory.server.service.{ApiError, ScaleNotFound, TuningNotFound}
import org.http4s.Response
import org.http4s.dsl.Http4sDsl

class ApiErrorHandler[F[_]: Monad] extends Http4sDsl[F] {
  val handle: ApiError => F[Response[F]] = {
    case ScaleNotFound(id)  => NotFound(s"Scale with id $id not found.")
    case TuningNotFound(id) => NotFound(s"Tuning with id $id not found.")
  }
}

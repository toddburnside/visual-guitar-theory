package io.github.toddburnside.guitartheory.server

import cats.effect.{Async, ContextShift}
import cats.implicits._
import io.github.toddburnside.guitartheory.server.endpoint._
import io.github.toddburnside.guitartheory.server.repository.inmemory._
import io.github.toddburnside.guitartheory.server.service._
import org.http4s.{HttpApp, HttpRoutes}
import org.http4s.implicits._

class Module[F[_]: Async: ContextShift] {
  private val scaleRepository  = new ScaleRepositoryInMemoryInterpretor[F]
  private val tuningRepository = new TuningRepositoryInMemoryInterpretor[F]

  private val scaleService  = new ScaleService[F](scaleRepository)
  private val tuningService = new TuningService[F](tuningRepository)

  implicit val httpErrorHandler: ApiErrorHandler[F] = new ApiErrorHandler[F]

  private val scaleRoutes: HttpRoutes[F]  = new ScaleRoutes[F](scaleService).routes
  private val tuningRoutes: HttpRoutes[F] = new TuningRoutes[F](tuningService).routes

  val httpApp: HttpApp[F] = (scaleRoutes <+> tuningRoutes).orNotFound
}

package io.github.toddburnside.guitartheory.server.service

import cats.effect.Async
import cats.implicits._
import io.github.toddburnside.guitartheory.server.algebra._
import io.github.toddburnside.guitartheory.theory.TuningDb
import mouse.all._

class TuningService[F[_]: Async](tuningRepo: TuningRepositoryAlgebra[F]) {
  def get(id: Long): F[ApiError Either TuningDb] =
    tuningRepo.get(id).map(_.right(TuningNotFound(id)))

  def list: F[List[TuningDb]] = tuningRepo.list

  // Get the notes on the neck
}

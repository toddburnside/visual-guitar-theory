package io.github.toddburnside.guitartheory.server.service

import cats.data._
import cats.effect.Async
import cats.implicits._
import io.github.toddburnside.guitartheory.server.algebra.ScaleRepositoryAlgebra
import io.github.toddburnside.guitartheory.theory._
import mouse.all._

class ScaleService[F[_]: Async](scaleRepo: ScaleRepositoryAlgebra[F]) {
  def get(id: Long): F[ApiError Either ScaleDb] =
    scaleRepo.get(id).map(_.right(ScaleNotFound(id)))

  def list: F[List[ScaleDb]] = scaleRepo.list

  def instantiate(id: Long, root: Note): EitherT[F, ApiError, List[ScaleNote]] =
    for {
      scaleDb <- EitherT(get(id))
      scaleNotes = Scale.instantiate(scaleDb.scale, root)
    } yield scaleNotes
}

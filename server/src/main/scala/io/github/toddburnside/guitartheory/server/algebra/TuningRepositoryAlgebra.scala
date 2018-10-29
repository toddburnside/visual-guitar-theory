package io.github.toddburnside.guitartheory.server.algebra

import io.github.toddburnside.guitartheory.theory._

trait TuningRepositoryAlgebra[F[_]] {
  def get(id: Long): F[Option[TuningDb]]

  def list: F[List[TuningDb]]
  // will have put/post/delete
}

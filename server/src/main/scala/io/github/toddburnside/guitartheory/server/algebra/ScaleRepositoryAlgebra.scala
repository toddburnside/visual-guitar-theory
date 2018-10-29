package io.github.toddburnside.guitartheory.server.algebra

import io.github.toddburnside.guitartheory.theory._

trait ScaleRepositoryAlgebra[F[_]] {
  def get(id: Long): F[Option[ScaleDb]]

  def list: F[List[ScaleDb]]
  // will have put/post/delete
}

package io.github.toddburnside.guitartheory.server.repository.inmemory

import cats._
import cats.implicits._
import io.github.toddburnside.guitartheory.theory._
import io.github.toddburnside.guitartheory.server.algebra.TuningRepositoryAlgebra

import scala.collection.concurrent.TrieMap

class TuningRepositoryInMemoryInterpretor[F[_]: Applicative] extends TuningRepositoryAlgebra[F] {

  private val data = TrieMap(
    1L -> Tuning.standard,
    2L -> Tuning.dadgad
  )

  override def get(id: Long): F[Option[TuningDb]] = data.get(id).map(TuningDb(id, _)).pure[F]

  override def list: F[List[TuningDb]] =
    data.map(d => TuningDb(d._1, d._2)).toList.sortBy(_.id).pure[F]
}

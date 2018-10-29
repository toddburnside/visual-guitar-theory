package io.github.toddburnside.guitartheory.server.repository.inmemory

import cats._
import cats.implicits._
import io.github.toddburnside.guitartheory.theory._
import io.github.toddburnside.guitartheory.server.algebra.ScaleRepositoryAlgebra

import scala.collection.concurrent.TrieMap

class ScaleRepositoryInMemoryInterpretor[F[_]: Applicative] extends ScaleRepositoryAlgebra[F] {
  import ScaleTone._

  private val data = TrieMap(
    1L -> Scale("Major Scale", List(iNat, iiNat, iiiNat, ivNat, vNat, viNat, viiNat)),
    2L -> Scale("Minor Scale", List(iNat, iiNat, iiiFlat, ivNat, vNat, viNat, viiNat)),
    3L -> Scale("Minor Pentatonic", List(iNat, iiiFlat, ivNat, vNat, viiFlat))
  )

  override def get(id: Long): F[Option[ScaleDb]] = data.get(id).map(ScaleDb(id, _)).pure[F]

  override def list: F[List[ScaleDb]] =
    data.map(d => ScaleDb(d._1, d._2)).toList.sortBy(_.id).pure[F]
}

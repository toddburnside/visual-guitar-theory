package io.github.toddburnside.guitartheory.theory

import cats._
import cats.kernel.CommutativeGroup
import cats.implicits._
import io.circe._

case class Semitone(value: Int) extends AnyVal

object Semitone {
  // Eq instance
  implicit val eqSemitone: Eq[Semitone] = Eq.fromUniversalEquals

  // CommutativeGroup instance
  implicit val commutativeGroupSemitone: CommutativeGroup[Semitone] = new CommutativeGroup[Semitone] {
    override def empty: Semitone = Semitone(0)

    override def combine(x: Semitone, y: Semitone): Semitone =
      Semitone(x.value |+| y.value)

    override def inverse(a: Semitone): Semitone = Semitone(a.value.inverse)
  }

  // Order instance
  implicit val orderSemitone: Order[Semitone] = (x: Semitone, y: Semitone) => Order.compare(x.value, y.value)

  // circe encoder/decoder - strip away the outer class name.
  implicit val semitoneEncoder: Encoder[Semitone] = Encoder.encodeInt.contramap[Semitone](_.value)
  implicit val semitoneDecoder: Decoder[Semitone] = Decoder.decodeInt.emap(Semitone(_).asRight)
}
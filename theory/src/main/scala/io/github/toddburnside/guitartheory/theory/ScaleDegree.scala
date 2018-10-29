package io.github.toddburnside.guitartheory.theory

import cats._
import cats.implicits._
import io.circe._

// What I REALLY want is an opaque type...
case class ScaleDegree(degree: Int) extends AnyVal

object ScaleDegree {
  val degreesInScale = 7

  val i   = ScaleDegree(1)
  val ii  = ScaleDegree(2)
  val iii = ScaleDegree(3)
  val iv  = ScaleDegree(4)
  val v   = ScaleDegree(5)
  val vi  = ScaleDegree(6)
  val vii = ScaleDegree(7)

  // I opted to allow ANY value for degree, but provide a normalized value.
  // Negatives will be a little odd, though...
  // also, scale degrees are ones based by convention.
  def normalized(scaleDegree: ScaleDegree): ScaleDegree = {
    val degree   = (scaleDegree.degree - 1) % degreesInScale
    val positive = if (degree < 0) degree + degreesInScale else degree
    ScaleDegree(positive + 1)
  }

  // Eq instance
  implicit val eqScaleDegree: Eq[ScaleDegree] = Eq.fromUniversalEquals

  implicit val showScaleDegree: Show[ScaleDegree] =
    Show.show(n => if (n.degree == 1) "R" else n.degree.show)

  trait ScaleDegreeOps {
    def self: ScaleDegree

    def normalized(): ScaleDegree = ScaleDegree.normalized(self)
  }

  implicit def toScaleDegreeOps(target: ScaleDegree): ScaleDegreeOps = new ScaleDegreeOps {
    override val self: ScaleDegree = target
  }

  implicit val scaleDegreeEncoder: Encoder[ScaleDegree] =
    Encoder.encodeInt.contramap[ScaleDegree](_.degree)
  implicit val scaleDegreeDecoder: Decoder[ScaleDegree] =
    Decoder.decodeInt.emap(i => Right(ScaleDegree(i)))
}

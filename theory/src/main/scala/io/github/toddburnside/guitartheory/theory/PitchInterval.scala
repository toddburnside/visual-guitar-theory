package io.github.toddburnside.guitartheory.theory

import cats._
import cats.kernel.CommutativeGroup
import cats.implicits._
import io.circe._

sealed abstract case class PitchInterval private(semitones: Semitone)

object PitchInterval {
  val maxSemitones = 12

  // smart constructor - constrains semitones to the range of 0 to 11.
  def apply(semitones: Semitone): PitchInterval = {
    val mod = semitones.value % maxSemitones
    val value = if (mod < 0) mod + maxSemitones else mod
    new PitchInterval(Semitone(value)) {}
  }

  // Eq instance
  implicit val eqPitchInterval: Eq[PitchInterval] = Eq.fromUniversalEquals

  // TODO: write laws tests
  // CommutativeGroup instance
  implicit val commGroupPitchInterval: CommutativeGroup[PitchInterval] = new CommutativeGroup[PitchInterval] {
    override def inverse(a: PitchInterval): PitchInterval = PitchInterval(Semitone(maxSemitones - a.semitones.value))

    override def empty: PitchInterval = PitchInterval(Monoid[Semitone].empty)

    override def combine(x: PitchInterval, y: PitchInterval): PitchInterval = PitchInterval(x.semitones |+| y.semitones)
  }

  // circe encoder/decoder - strip away the class names.
  implicit val pitchIntervalEncoder: Encoder[PitchInterval] = Encoder.encodeInt.contramap[PitchInterval](_.semitones.value)
  implicit val pitchIntervalDecoder: Decoder[PitchInterval] = Decoder.decodeInt.emap(i => PitchInterval(Semitone(i)).asRight)
}
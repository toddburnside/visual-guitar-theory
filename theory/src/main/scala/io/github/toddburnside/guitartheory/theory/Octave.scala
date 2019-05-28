package io.github.toddburnside.guitartheory.theory

import cats._
import cats.implicits._
import io.circe.{Decoder, Encoder}

// This represents an octave on the keyboard from C -> B.
// Middle C is octave 4
case class Octave(value: Int) extends AnyVal

object Octave {
  // Note: There is a big assumption that we are always moving up in pitch and
  // always less than an octave. Since this is used to determine the octave for
  // each note of a scale on a string, this works.
  def newOctave(thisOctave: Octave, thisNote: Note, nextNote: Note): Octave =
    if (thisNote.isSameAs(nextNote) ||
      thisNote.intervalTo(nextNote) < thisNote.intervalTo(Note.cNat))
      thisOctave
    else Octave(thisOctave.value + 1)

  implicit val octaveEncoder: Encoder[Octave] =
    Encoder.encodeInt.contramap[Octave](_.value)
  implicit val octaveDecoder: Decoder[Octave] =
    Decoder.decodeInt.emap(i => Octave(i).asRight)

  implicit val octaveShow: Show[Octave] = Show.fromToString

  trait OctaveOps {
    def self: Octave
    def newOctave(thisNote: Note, nextNote: Note): Octave =
      Octave.newOctave(self, thisNote, nextNote): Octave
  }

  implicit def toOctaveOps(target: Octave): OctaveOps = new OctaveOps {
    override def self: Octave = target
  }

  val octave1: Octave = Octave(1)
  val octave2: Octave = Octave(2)
  val octave3: Octave = Octave(3)
  val octave4: Octave = Octave(4)
  val octave5: Octave = Octave(5)
  val octave6: Octave = Octave(6)
}

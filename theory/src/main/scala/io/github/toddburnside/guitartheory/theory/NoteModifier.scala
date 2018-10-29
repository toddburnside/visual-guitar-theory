package io.github.toddburnside.guitartheory.theory

import cats._
import cats.implicits._
import cats.kernel.CommutativeGroup
import io.circe._

sealed case class NoteModifier(semitones: Semitone)

object NoteModifier {

  // can get a double sharp w/: sharp |+| sharp
  val sharp   = NoteModifier(Semitone(1))
  val natural = NoteModifier(Semitone(0))
  // can get a double flat w/: flat |+| flat
  val flat = NoteModifier(Semitone(-1))

  val sharpSymbol = '♯'
  val flatSymbol  = '♭'

//  val sharpSymbol = '#'
//  val flatSymbol  = 'b'

  // it's a bit odd, but it will take any number of sharps or flats in a string
  def parse(symbol: String): Option[NoteModifier] = {
    def modifier(i: Int, c: Char): Option[Int] = c match {
      case `sharpSymbol` => Some(i + 1)
      case `flatSymbol`  => Some(i - 1)
      case '#'           => Some(i + 1)
      case 'b'           => Some(i - 1)
      case _             => None
    }

    symbol
      .foldLeft(0.some)((sum, c) => sum.flatMap(modifier(_, c)))
      .map(i => NoteModifier(Semitone(i)))
  }

  def mkString(modifier: NoteModifier): String = {
    def loop(i: Int, acc: String): String = i match {
      case x if x < 0 => loop(x + 1, acc + flatSymbol)
      case x if x > 0 => loop(x - 1, acc + sharpSymbol)
      case _          => acc
    }

    loop(modifier.semitones.value, "")
  }

  // Show instance
  implicit val showNoteModifier: Show[NoteModifier] = Show.show(mkString)

  // Eq instance
  implicit val eqNoteModifier: Eq[NoteModifier] = Eq.fromUniversalEquals

  implicit val noteModifierCommutativeGroup: CommutativeGroup[NoteModifier] =
    new CommutativeGroup[NoteModifier] {
      override def inverse(a: NoteModifier): NoteModifier = NoteModifier(a.semitones.inverse())

      override def empty: NoteModifier = NoteModifier(Monoid[Semitone].empty)

      override def combine(x: NoteModifier, y: NoteModifier): NoteModifier =
        NoteModifier(x.semitones |+| y.semitones)
    }
  // circe encoder/decoder
  implicit val noteModifierEncoder: Encoder[NoteModifier] =
    Encoder.encodeInt.contramap[NoteModifier](_.semitones.value)
  implicit val noteModifierDecoder: Decoder[NoteModifier] =
    Decoder.decodeInt.emap(i => Right(NoteModifier(Semitone(i))))
}

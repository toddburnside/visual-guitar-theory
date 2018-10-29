package io.github.toddburnside.guitartheory.theory

import cats._
import cats.derived._
import cats.implicits._
import io.circe.{Decoder, Encoder}
import io.circe.syntax._
import mouse.all._

import scala.annotation.tailrec

// Represent as a PostgreSQL enum?
sealed trait NoteLetter

object NoteLetter {

  private case object A extends NoteLetter

  private case object B extends NoteLetter

  private case object C extends NoteLetter

  private case object D extends NoteLetter

  private case object E extends NoteLetter

  private case object F extends NoteLetter

  private case object G extends NoteLetter

  // smart "constructors" - really just smart references here, I guess. Widens to Note.
  val a: NoteLetter = A
  val b: NoteLetter = B
  val c: NoteLetter = C
  val d: NoteLetter = D
  val e: NoteLetter = E
  val f: NoteLetter = F
  val g: NoteLetter = G

  def nextNote(note: NoteLetter): (NoteLetter, PitchInterval) = note match {
    case A => (B, PitchInterval(Semitone(2)))
    case B => (C, PitchInterval(Semitone(1)))
    case C => (D, PitchInterval(Semitone(2)))
    case D => (E, PitchInterval(Semitone(2)))
    case E => (F, PitchInterval(Semitone(1)))
    case F => (G, PitchInterval(Semitone(2)))
    case G => (A, PitchInterval(Semitone(2)))
  }

  def fromString(note: String): Option[NoteLetter] = note match {
    case "A" => Some(A)
    case "B" => Some(B)
    case "C" => Some(C)
    case "D" => Some(D)
    case "E" => Some(E)
    case "F" => Some(F)
    case "G" => Some(G)
    case _   => None
  }

  def getInterval(root: NoteLetter, dest: NoteLetter): PitchInterval = {
    @tailrec
    def loop(current: NoteLetter, interval: PitchInterval): PitchInterval = {
      if (current == dest) interval
      else {
        val (note, semitones) = nextNote(current)
        loop(note, interval |+| semitones)
      }
    }

    loop(root, Monoid[PitchInterval].empty)
  }

  def forScaleDegree(root: NoteLetter, scaleDegree: ScaleDegree): (NoteLetter, Semitone) = {
    @tailrec
    def loop(current: NoteLetter, semitone: Semitone, counter: Int): (NoteLetter, Semitone) = {
      if (counter === 0) (current, semitone)
      else {
        val (next, interval) = nextNote(current)
        loop(next, semitone |+| interval.semitones, counter - 1)
      }
    }

    loop(root, Monoid[Semitone].empty, scaleDegree.normalized().degree - 1)
  }

  // show instance
  implicit val showNoteLetter: Show[NoteLetter] = Show.fromToString

  // Eq instance
  implicit val eqNoteLetter: Eq[NoteLetter] = {
    import auto.eq._
    semi.eq
  }

  trait NoteLetterOps {
    def self: NoteLetter
    def intervalTo(other: NoteLetter): PitchInterval = NoteLetter.getInterval(self, other)
    def forScaleDegree(scaleDegree: ScaleDegree): (NoteLetter, Semitone) =
      NoteLetter.forScaleDegree(self, scaleDegree)
  }

  implicit def toNoteLetterOps(target: NoteLetter): NoteLetterOps = new NoteLetterOps {
    override val self: NoteLetter = target
  }

  // circe encoder/decoder
  implicit val encodeNoteLetter: Encoder[NoteLetter] = Encoder.instance(_.show.asJson)

  implicit val decodeNoteLetter: Decoder[NoteLetter] =
    Decoder.decodeString.emap(s => NoteLetter.fromString(s).right(s"Invalid Note '$s'"))
}

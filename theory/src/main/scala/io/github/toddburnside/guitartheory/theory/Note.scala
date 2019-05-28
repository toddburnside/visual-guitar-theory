package io.github.toddburnside.guitartheory.theory

import cats._
import cats.implicits._

sealed case class Note(letter: NoteLetter, modifier: NoteModifier)

object Note {

  import NoteLetter._
  import NoteModifier._

  val aNat = Note(a, natural)
  val bNat = Note(b, natural)
  val cNat = Note(c, natural)
  val dNat = Note(d, natural)
  val eNat = Note(e, natural)
  val fNat = Note(f, natural)
  val gNat = Note(g, natural)

  val aFlat = Note(a, flat)
  val bFlat = Note(b, flat)
  val cFlat = Note(c, flat)
  val dFlat = Note(d, flat)
  val eFlat = Note(e, flat)
  val fFlat = Note(f, flat)
  val gFlat = Note(g, flat)

  val aSharp = Note(a, sharp)
  val bSharp = Note(b, sharp)
  val cSharp = Note(c, sharp)
  val dSharp = Note(d, sharp)
  val eSharp = Note(e, sharp)
  val fSharp = Note(f, sharp)
  val gSharp = Note(g, sharp)

  def parse(s: String): Option[Note] = {
    val Pattern = raw"^([A-G])(.*)".r
    s match {
      case Pattern(letter, modifier) =>
        (NoteLetter.fromString(letter), NoteModifier.parse(modifier))
          .mapN((l, m) => Note(l, m))
      case _ => None
    }
  }

  def getInterval(note1: Note, note2: Note): PitchInterval =
    (note1.letter intervalTo note2.letter) |-| PitchInterval(note1.modifier.semitones) |+| PitchInterval(
      note2.modifier.semitones)

  def areSame(note1: Note, note2: Note): Boolean =
    getInterval(note1, note2) === Monoid[PitchInterval].empty
  implicit val showNote: Show[Note] = Show.show(n => n.letter.show + n.modifier.show)

  // This is strictly equal - must be ths same letter and modifier. For note
  // equivalence see areSame or note.isSameAs
  implicit val eqNote: Eq[Note] = Eq.fromUniversalEquals

  trait NoteOps {
    def self: Note
    def intervalTo(other: Note): PitchInterval = Note.getInterval(self, other)
    def isSameAs(other: Note): Boolean         = Note.areSame(self, other)
  }

  implicit def toNoteOps(target: Note): NoteOps = new NoteOps {
    override val self: Note = target
  }
}

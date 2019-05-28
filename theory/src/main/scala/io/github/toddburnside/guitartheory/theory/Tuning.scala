package io.github.toddburnside.guitartheory.theory

sealed case class Tuning(name: String, strings: List[(Note, Octave)])

object Tuning {
  import Octave._

  val fretsInOctave = 12

  def notesOnString(
      nutNote: Note,
      nutOctave: Octave,
      numFrets: Int,
      notes: List[ScaleNote]): List[FretNote] =
    for {
      fret <- (0 to numFrets).toList
      pi = PitchInterval(Semitone(fret))
      nextNote = Note(
        nutNote.letter,
        NoteModifier(Semitone(nutNote.modifier.semitones.value + pi.semitones.value)))
      scaleNote <- notes.find(_.note.isSameAs(nextNote)).fold(Nil: List[ScaleNote])(List(_))
      octaveAdd  = fret / fretsInOctave
      nextOctave = Octave(nutOctave.newOctave(nutNote, scaleNote.note).value + octaveAdd)
    } yield FretNote(fret, scaleNote.scaleTone, scaleNote.note, nextOctave)

  def notesOnNeck(tuning: Tuning, numFrets: Int, notes: List[ScaleNote]): List[List[FretNote]] =
    tuning.strings.map(s => notesOnString(s._1, s._2, numFrets, notes))

  trait TuningOps {
    def self: Tuning
    def notesOnNeck(numFrets: Int, notes: List[ScaleNote]): List[List[FretNote]] =
      Tuning.notesOnNeck(self, numFrets, notes)
  }

  implicit def toTuningOps(target: Tuning): TuningOps = new TuningOps {
    override val self: Tuning = target
  }

  // Temporary
  import Note._
  val standard = Tuning(
    "Standard",
    List(
      (eNat, octave3),
      (aNat, octave3),
      (dNat, octave4),
      (gNat, octave4),
      (bNat, octave4),
      (eNat, octave5)))

  val dadgad = Tuning(
    "DADGAD",
    List(
      (dNat, octave3),
      (aNat, octave3),
      (dNat, octave4),
      (gNat, octave4),
      (aNat, octave4),
      (dNat, octave5)))
}

sealed case class TuningDb(id: Long, tuning: Tuning)

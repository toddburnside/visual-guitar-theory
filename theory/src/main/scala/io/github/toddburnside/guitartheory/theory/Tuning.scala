package io.github.toddburnside.guitartheory.theory

sealed case class Tuning(name: String, strings: List[Note])

object Tuning {
  def notesOnString(nutNote: Note, notes: List[ScaleNote]): List[FretNote] = {
    for {
      ScaleNote(scaleTone, note) <- notes
      fret = (nutNote intervalTo note).semitones.value
    } yield FretNote(fret, scaleTone, note)
  }

  def notesOnNeck(tuning: Tuning, notes: List[ScaleNote]): List[List[FretNote]] =
    tuning.strings.map(notesOnString(_, notes))

  trait TuningOps {
    def self: Tuning
    def notesOnNeck(notes: List[ScaleNote]): List[List[FretNote]] =
      Tuning.notesOnNeck(self, notes)
  }

  implicit def toTuningOps(target: Tuning): TuningOps = new TuningOps {
    override val self: Tuning = target
  }

  // Temporary
  import Note._
  val standard = Tuning("Standard", List(eNat, aNat, dNat, gNat, bNat, eNat))
  val dadgad   = Tuning("DADGAD", List(dNat, aNat, dNat, gNat, aNat, dNat))
}

sealed case class TuningDb(id: Long, tuning: Tuning)

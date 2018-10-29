package io.github.toddburnside.guitartheory.theory

import cats.implicits._

sealed case class Scale(name: String, notes: List[ScaleTone])

object Scale {
  def instantiate(scale: Scale, root: Note): List[ScaleNote] =
    for {
      scaleTone <- scale.notes
      scaleInterval              = MajorScale.intervalFromRoot(scaleTone.degree)
      (noteLetter, noteInterval) = root.letter.forScaleDegree(scaleTone.degree)
      difference                 = scaleInterval |-| noteInterval
      modifier = NoteModifier(
        difference |+| scaleTone.modifier.semitones |+| root.modifier.semitones)
      note = Note(noteLetter, modifier)
    } yield ScaleNote(scaleTone, note)

}

sealed case class ScaleDb(id: Long, scale: Scale)

package io.github.toddburnside.guitartheory.theory

import cats._
import cats.implicits._

sealed case class ScaleNote(scaleTone: ScaleTone, note: Note)

object ScaleNote {
  implicit val showScaleNote: Show[ScaleNote] = Show.show(sn => show"(${sn.scaleTone}, ${sn.note})")
}

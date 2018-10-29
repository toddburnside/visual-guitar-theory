package io.github.toddburnside.guitartheory.theory

import cats._
import cats.implicits._

sealed case class FretNote(fret: Int, scaleTone: ScaleTone, note: Note)

object FretNote {
  implicit val showFretNote: Show[FretNote] =
    Show.show(fn => show"(F${fn.fret}, ${fn.scaleTone}, ${fn.note})")
}

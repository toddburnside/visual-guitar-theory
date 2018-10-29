package io.github.toddburnside.guitartheory.server.endpoint

import io.github.toddburnside.guitartheory.theory._

object Matchers {
  object NoteVar {
    def unapply(str: String): Option[Note] = Note.parse(str)
  }
}

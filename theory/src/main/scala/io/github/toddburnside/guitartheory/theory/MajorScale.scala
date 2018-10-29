package io.github.toddburnside.guitartheory.theory

object MajorScale {
  def intervalFromRoot(scaleDegree: ScaleDegree): Semitone = scaleDegree.normalized match {
    case ScaleDegree(1) => Semitone(0)
    case ScaleDegree(2) => Semitone(2)
    case ScaleDegree(3) => Semitone(4)
    case ScaleDegree(4) => Semitone(5)
    case ScaleDegree(5) => Semitone(7)
    case ScaleDegree(6) => Semitone(9)
    case ScaleDegree(7) => Semitone(11)
    case _              => throw new Exception("Inconceivable")
  }

  // temporary
  import ScaleTone._

  val majorScale: Scale =
    Scale("Major Scale", List(iNat, iiNat, iiiNat, ivNat, vNat, viNat, viiNat))
  val minorScale: Scale =
    Scale("Minor Scale", List(iNat, iiNat, iiiFlat, ivNat, vNat, viNat, viiNat))
  val minorPentatonic: Scale =
    Scale("Minor Pentatonic", List(iNat, iiiFlat, ivNat, vNat, viiFlat))
}

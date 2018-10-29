package io.github.toddburnside.guitartheory.theory

import cats._
import cats.implicits._
import io.circe._
import io.circe.generic.semiauto._

case class ScaleTone(degree: ScaleDegree, modifier: NoteModifier)

object ScaleTone {
  import ScaleDegree._
  import NoteModifier._
  val iNat   = ScaleTone(i, natural)
  val iiNat  = ScaleTone(ii, natural)
  val iiiNat = ScaleTone(iii, natural)
  val ivNat  = ScaleTone(iv, natural)
  val vNat   = ScaleTone(v, natural)
  val viNat  = ScaleTone(vi, natural)
  val viiNat = ScaleTone(vii, natural)

  val iFlat   = ScaleTone(i, flat)
  val iiFlat  = ScaleTone(ii, flat)
  val iiiFlat = ScaleTone(iii, flat)
  val ivFlat  = ScaleTone(iv, flat)
  val vFlat   = ScaleTone(v, flat)
  val viFlat  = ScaleTone(vi, flat)
  val viiFlat = ScaleTone(vii, flat)

  val iSharp   = ScaleTone(i, sharp)
  val iiSharp  = ScaleTone(ii, sharp)
  val iiiSharp = ScaleTone(iii, sharp)
  val ivSharp  = ScaleTone(iv, sharp)
  val vSharp   = ScaleTone(v, sharp)
  val viSharp  = ScaleTone(vi, sharp)
  val viiSharp = ScaleTone(vii, sharp)

  implicit val scaleToneShow: Show[ScaleTone] = Show.show(n => n.degree.show + n.modifier.show)

  implicit val scaleToneEncoder: Encoder[ScaleTone] = deriveEncoder
  implicit val scaleToneDecoder: Decoder[ScaleTone] = deriveDecoder
}

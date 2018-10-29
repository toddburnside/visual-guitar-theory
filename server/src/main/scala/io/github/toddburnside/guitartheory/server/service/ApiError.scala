package io.github.toddburnside.guitartheory.server.service

sealed trait ApiError extends Product with Serializable

case class ScaleNotFound(id: Long)  extends ApiError
case class TuningNotFound(id: Long) extends ApiError

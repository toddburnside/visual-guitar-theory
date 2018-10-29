# visual-guitar-theory
A project to help guitarists learn music theory as it relates to the guitar.

The initial goal of this project is to have a server, plus web and mobile frontends. The user will be able to define scales and chords, save them and display them.

## Scales
These will be defined with respect to the major scale. By supplying a root note, the user will be able to view the notes of that _instance_ of the scale on a fretboard, with the note name or degree of the scale displayed in a dot on the fretboard. Different scale degrees will be displayed in different, configurable colors so that relationship between the notes can be clearly seen. Individual degrees will also be able to be turned on or off to simplify the display.

## Chords
Chords are of 2 kinds:
* Open - These chords are not movable and have an assigned root.
* Moveable - These chords have no open notes and can be moved about the fretboard. The root note will be relative to the position on the neck.

Chords will be defined and displayed on a chord diagram. The user will indicate a root note, and can also assign degrees to the other notes. If they assign scale degrees, they will be able to verify the correctness of the assignments. By assigning degrees, they can assign degrees such as a 13th or ♯9th, although in the scale these are 6th and ♯2nds, respectively. Again, tones will be color coded and there will be various display options.

The user will also be able to _scalify_ a chord and display all the notes in that chord on the guitar neck as though it is a scale.

## Notes

This is meant to be a participatory application. The user will create their own scale and chord libraries.

## Technology

### Server
This is being developed in Scala using the [Typelevel](https://typelevel.org) stack of Cats, Cats-Effect, fs2, http4s, doobie, and other libraries.

### Web front-end
This will be developed in ScalaJS. Probably using a React wrapper library, but I will assess the current state of some of the "native" ScalaJS libraries/frameworks as well.

### Mobile App
This will either be an Android app written in Kotlin and using the [Arrow](https://arrow-kt.io/) functional programming library, or a React Native app written in ScalaJS. If the former, a separate iOS app may developed as well.

## Status
Development has barely started...

The "theory" portion of the server as it relates to scales is fairly complete and some simple http endpoints have been created. User login/authentication needs to be created. It currently uses a rudimentary in-memory respository: doobie with PostgreSQL needs to be implemented. Additional endpoints and chord "theory" also need to be added.

No UI work has yet been done.

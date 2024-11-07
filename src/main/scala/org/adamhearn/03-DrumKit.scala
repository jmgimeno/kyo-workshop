package org.adamhearn

import javax.sound.midi.MidiSystem
import kyo.*
import org.jline.terminal.TerminalBuilder

object DrumKit extends KyoApp:

  case class Note(time: Duration, char: Char)

  def record: Seq[Note] < IO = ???

  def play(notes: Seq[Note]): Unit < Async = ???

  run {
    Loop.forever(record.map(play))
  }

object JLine:
  private lazy val terminal =
    val t =
      TerminalBuilder
        .builder()
        .system(true)
        .jna(false)
        .build()
    t.enterRawMode()
    t

  val readChar: Char < IO =
    IO(terminal.reader().read().toChar)

  val tryReadChar: Maybe[Char] < IO =
    IO {
      if terminal.reader().peek(1) == -2 then Absent
      else
        val c = terminal.reader().read()
        if c == -1 then Absent
        else Present(c.toChar)
    }

object Player:
  private val DrumChannel = 9
  private val Velocity    = 64

  private val DrumMap = Map(
    '1' -> 49,
    '2' -> 57,
    '3' -> 51,
    '4' -> 59,
    '5' -> 42,
    '6' -> 46,
    '7' -> 44,
    '8' -> 52,
    '9' -> 55,
    '0' -> 53,
    'q' -> 41,
    'w' -> 43,
    'e' -> 45,
    'r' -> 47,
    't' -> 48,
    'y' -> 50,
    'u' -> 67,
    'i' -> 68,
    'o' -> 56,
    'p' -> 54,
    'a' -> 35,
    's' -> 36,
    'd' -> 38,
    'f' -> 40,
    'g' -> 37,
    'h' -> 39,
    'j' -> 69,
    'k' -> 70,
    'l' -> 75,
    'z' -> 64,
    'x' -> 63,
    'c' -> 62,
    'v' -> 65,
    'b' -> 66,
    'n' -> 76,
    'm' -> 77,
    ',' -> 73,
    '.' -> 74,
  )

  private lazy val channel =
    val synth = MidiSystem.getSynthesizer
    synth.open()
    synth.getChannels()(DrumChannel)
  end channel

  def play(c: Char): Unit < IO =
    IO {
      val note = DrumMap.getOrElse(c.toLower, 49) // Default to Crash 1 if key not found
      channel.noteOn(note, Velocity)
    }
end Player

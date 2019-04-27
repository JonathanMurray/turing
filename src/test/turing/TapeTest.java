package turing;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class TapeTest {


  @Test
  public void tapeShouldBeEmptyAtTheStart() {
    assertNull(new Tape().read());
  }

  @Test
  public void shouldReadWhatWasWritten() {
    Tape tape = new Tape();
    tape.print('A');
    Character read = tape.read();
    assertThat(read, is('A'));
  }

  @Test
  public void shouldBeAbleToDelete() {
    Tape tape = new Tape();
    tape.print('A');
    tape.print(null);
    assertNull(tape.read());
  }

  @Test
  public void movingRightShouldReadDifferentSymbol() {
    Tape tape = new Tape();
    tape.print('A');
    tape.right();
    assertNull(tape.read());
    tape.print('B');
    assertThat(tape.read(), is('B'));
  }

  @Test
  public void movingBackLeftwardsShouldReadSameSymbolAsBefore() {
    Tape tape = new Tape();
    tape.print('A');
    tape.right();
    tape.left();
    assertThat(tape.read(), is('A'));
  }

  @Test(expected = IllegalStateException.class)
  public void shouldNotAllowMovingLeftFromStartPosition() {
    Tape tape = new Tape();
    tape.left();
  }

  @Test
  public void demo() {
    Tape tape = new Tape();
    tape.right();
    tape.print('A');
    tape.right();
    tape.print('B');
    tape.right();
    tape.right();
    tape.print('A');
    tape.left();
    tape.print('X');
  }

  @Test
  public void canReadBinaryNumberFromTape() {
    Tape tape = new Tape(null, '1', null, 'A', '0', '0', '1', null);
    assertThat(tape.readBinarySequenceFromTape(), is("1001"));
  }

  @Test
  public void canProduceVerboseStringRepresentation() {
    Tape tape = new Tape('A', null, 'C', 'D', null, 'E');
    assertThat(tape.getString(TapeStringType.VERBOSE), is("|(A)|   | C | D |   | E |"));
    tape.right();
    assertThat(tape.getString(TapeStringType.VERBOSE), is("| A |( )| C | D |   | E |"));
    tape.right();
    assertThat(tape.getString(TapeStringType.VERBOSE), is("| A |   |(C)| D |   | E |"));
  }

  @Test
  public void canProduceStringRepresentation() {
    Tape tape = new Tape('A', null, 'C', 'D', null, 'E');
    tape.right();
    assertThat(tape.getString(TapeStringType.REGULAR), is(" A( )C D   E "));
    tape.right();
    assertThat(tape.getString(TapeStringType.REGULAR), is(" A  (C)D   E "));
  }

  @Test
  public void canProduceCompactStringRepresentation() {
    Tape tape = new Tape('A', null, 'C', 'D', null, 'E');
    tape.right();
    assertThat(tape.getString(TapeStringType.COMPACT), is("A_CD E"));
    tape.right();
    assertThat(tape.getString(TapeStringType.COMPACT), is("A _D E"));
  }

  @Test
  public void readingTwiceShouldGiveSameValue() {
    Tape tape = new Tape('A', 'B');
    Character first = tape.read();
    Character second = tape.read();
    assertThat(first, is(second));
  }
}
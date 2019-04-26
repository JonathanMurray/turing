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
    System.out.println(tape);
    tape.right();
    System.out.println(tape);
    tape.print('A');
    System.out.println(tape);
    tape.right();
    System.out.println(tape);
    tape.print('B');
    System.out.println(tape);
    tape.right();
    System.out.println(tape);
    tape.right();
    System.out.println(tape);
    tape.print('A');
    System.out.println(tape);
    tape.left();
    System.out.println(tape);
    tape.print('X');
    System.out.println(tape);
  }

  @Test
  public void canReadBinaryNumberFromTape() {
    Tape tape = new Tape(null, '1', null, 'A', '0', '0', '1', null);
    assertThat(tape.readBinarySequenceFromTape(), is("1001"));
  }
}
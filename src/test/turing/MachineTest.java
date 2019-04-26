package turing;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import turing.Instruction.LeftInstruction;
import turing.Instruction.PrintInstruction;
import turing.Instruction.RightInstruction;
import turing.MConfiguration.Row;
import turing.Machine.ScannedUnHandledSymbol;
import turing.SymbolMatcher.MatchAny;
import turing.SymbolMatcher.MatchSymbol;

public class MachineTest {

  @Test
  public void shouldPrintToTapeWhenInstructedTo() {
    Tape tape = new Tape();
    Machine machine = new Machine(Arrays
        .asList(new MConfiguration(new MatchAny(), 0, new PrintInstruction('B'))),
        tape);
    machine.step();
    assertThat(tape.read(), is('B'));
  }

  @Test
  public void shouldPerformSeveralInstructions() {
    Tape tape = new Tape();
    Machine machine = new Machine(
        Arrays.asList(new MConfiguration(
            new MatchAny(), 0, new PrintInstruction('B'),
            new RightInstruction(), new PrintInstruction('C'))), tape);
    machine.step();
    assertThat(tape.read(), is('C'));
  }

  @Test
  public void shouldJumpBetweenMConfigurations() {
    Tape tape = new Tape();
    Machine machine = new Machine(
        Arrays.asList(
            new MConfiguration(new MatchAny(), 1, new PrintInstruction('A'),
                new RightInstruction()),
            new MConfiguration(new MatchAny(),
                0, new PrintInstruction('B'), new RightInstruction())
        ),
        tape);
    machine.step();
    assertThat(tape.getList(), is(Arrays.asList('A', null)));
    machine.step();
    assertThat(tape.getList(), is(Arrays.asList('A', 'B', null)));
  }

  @Test
  public void shouldHandleLoopsBetweenMConfigurations() {
    Tape tape = new Tape();
    Machine machine = new Machine(
        Arrays.asList(
            new MConfiguration(new MatchAny(), 1, new PrintInstruction('A'),
                new RightInstruction()),
            new MConfiguration(new MatchAny(),
                0, new PrintInstruction('B'), new LeftInstruction(), new PrintInstruction('C'))
        ),
        tape);
    machine.step();
    assertThat(tape.getList(), is(Arrays.asList('A', null)));
    machine.step();
    assertThat(tape.getList(), is(Arrays.asList('C', 'B')));
    machine.step();
    assertThat(tape.getList(), is(Arrays.asList('A', 'B')));
    machine.step();
    assertThat(tape.getList(), is(Arrays.asList('C', 'B')));
  }

  @Test
  public void shouldUseScannedSymbolToDetermineNextMConfiguration() {
    List<MConfiguration> mConfigurations = Arrays.asList(
        new MConfiguration(
            new Row(
                new MatchSymbol('X'), 1, new PrintInstruction('A'), new RightInstruction()),
            new Row(
                new MatchSymbol('Y'), 2, new PrintInstruction('B'), new RightInstruction())
        ),
        new MConfiguration(new MatchAny(), 0, new PrintInstruction('C')),
        new MConfiguration(new MatchAny(), 0, new PrintInstruction('D'))
    );

    Tape tape = new Tape();
    tape.print('X');
    Machine machine = new Machine(mConfigurations, tape);
    machine.step();
    machine.step();
    assertThat(tape.getList(), is(Arrays.asList('A', 'C')));

    tape = new Tape();
    tape.print('Y');
    machine = new Machine(mConfigurations, tape);
    machine.step();
    machine.step();
    assertThat(tape.getList(), is(Arrays.asList('B', 'D')));
  }

  @Test
  public void canGenerateLongSequences() {
    List<MConfiguration> mConfigurations = Arrays.asList(
        new MConfiguration(new MatchAny(), 1, new PrintInstruction('1'), new RightInstruction()),
        new MConfiguration(new MatchAny(), 0, new PrintInstruction('0'), new RightInstruction())
    );
    Tape tape = new Tape();
    Machine machine = new Machine(mConfigurations, tape);

    for (int i = 0; i < 50; i++) {
      machine.step();
    }

    assertThat(tape.readBinarySequenceFromTape(),
        is("10101010101010101010101010101010101010101010101010"));
  }

  @Test
  public void shouldCrashIfScanningUnHandledSymbol() {
    List<MConfiguration> mConfigurations = Arrays.asList(
        new MConfiguration(new MatchAny(), 1, new PrintInstruction('1'), new RightInstruction()),
        new MConfiguration(new MatchSymbol('X'), 0, new PrintInstruction('0'))
    );
    Machine machine = new Machine(mConfigurations, new Tape());
    machine.step();
    try {
      machine.step();
      fail();
    } catch (ScannedUnHandledSymbol e) {
      assertThat(
          e.getMessage(),
          is("Machine crashed! Scanned 'null' but current configuration doesn't handle it. Handled: [X]"));
    }
  }

  @Test
  public void shouldDetectInvalidConfigurationsAtStartup() {
    try {
      new Machine(
          Collections.singletonList(new MConfiguration(new MatchAny(), 1, new RightInstruction())),
          new Tape());
      fail();
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage(),
          is("MConfiguration has 'next' = 1! Valid index interval = [0, 0]"));
    }
  }

  @Test
  public void canComputeOneOfTuringsExampleNumbers() {

    Tape tape = new Tape();
    Machine machine = Machines.increasingNumberOfOnesDelimitedByZero(tape);

    for (int i = 0; i < 200; i++) {
      System.out.println(tape);
      machine.step();
    }

    System.out.println(tape.readBinarySequenceFromTape());
  }
}
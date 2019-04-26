package turing;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.function.Function;
import org.junit.Test;

public class ExampleMachinesTest {

  @Test
  public void canComputeOneOfTuringsExampleNumbers() {
    runMachineAndAssertExpectedOutput(
        ExampleMachines::increasingNumberOfOnesDelimitedByZero, 1000,
        "0010110111011110111110111111011111110111111110111111111"
    );
  }

  @Test
  public void canGenerateZerosInfinitely() {
    runMachineAndAssertExpectedOutput(
        ExampleMachines::zeros, 40, "0000000000000000000000000000000000000000");
  }

  @Test
  public void canGenerateAlternatingOnesAndZeros() {
    runMachineAndAssertExpectedOutput(
        ExampleMachines::alternatingOnesAndZeros, 40, "0101010101010101010101010101010101010101");
  }

  private void runMachineAndAssertExpectedOutput(Function<Tape, Machine> createMachine,
      int numberOfStepsToRun, String expectedSequence) {
    Tape tape = new Tape();
    Machine machine = createMachine.apply(tape);

    for (int i = 0; i < numberOfStepsToRun; i++) {
      machine.step();
    }

    assertThat(tape.readBinarySequenceFromTape(), is(expectedSequence));
  }
}

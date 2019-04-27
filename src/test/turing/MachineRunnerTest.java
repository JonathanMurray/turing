package turing;

import java.io.InputStream;
import java.time.Duration;
import org.junit.Test;

public class MachineRunnerTest {

  @Test
  public void canRunVisually() throws InterruptedException {
    Tape tape = new Tape();
    Machine machine = ExampleMachines.increasingNumberOfOnesDelimitedByZero(tape);
    MachineRunner.runVisually(machine, tape, 100, Duration.ZERO, TapeStringType.REGULAR);
  }

  @Test
  public void canRunInteractively() throws InterruptedException {
    Tape tape = new Tape();
    Machine machine = ExampleMachines.increasingNumberOfOnesDelimitedByZero(tape);
    MockedInputStream mockedUserInput = new MockedInputStream('\n', '\n', 'q', '\n');
    MachineRunner.runInteractively(machine, tape, Duration.ZERO, Duration.ZERO, mockedUserInput,
        TapeStringType.VERBOSE);
  }

  @Test
  public void canRunForDuration() {
    Tape tape = new Tape();
    Machine machine = ExampleMachines.increasingNumberOfOnesDelimitedByZero(tape);
    MachineRunner.runForDuration(machine, tape, Duration.ZERO);
  }

  private static class MockedInputStream extends InputStream {

    private final int[] inputs;
    private int index = 0;

    public MockedInputStream(int... inputs) {
      this.inputs = inputs;
    }

    @Override
    public int read() {
      if (index == inputs.length) {
        return -1;
      }
      int value = inputs[index];
      index++;
      return value;
    }
  }
}
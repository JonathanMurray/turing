package turing;

import java.time.Duration;

public class Main {

  public static void main(String[] args) throws InterruptedException {
    final Tape tape = new Tape();
    Machine machine = ExampleMachines.increasingNumberOfOnesDelimitedByZero(tape);
    MachineRunner.runVisually(
        machine, tape, 20, Duration.ofMillis(100), TapeStringType.REGULAR, System.out);
    //MachineRunner.runForDuration(machine, tape, Duration.ofSeconds(5), System.out);
    //MachineRunner.runInteractively(machine, tape, Duration.ofMillis(1000),
    //    Duration.ofMillis(250), System.in, TapeStringType.REGULAR, System.out);
  }

}

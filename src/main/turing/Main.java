package turing;

import java.time.Duration;

public class Main {

  public static void main(String[] args) throws InterruptedException {
    final Tape tape = new Tape();
    Machine machine = ExampleMachines.increasingNumberOfOnesDelimitedByZero(tape);
    runInteractively(machine, tape, 20, Duration.ofMillis(100));
  }

  private static void runInteractively(Machine machine, Tape tape, int steps, Duration sleep)
      throws InterruptedException {

    int totalSleepTime = (int) Math.round(steps * sleep.toMillis() / 1000.0);
    System.out.println(
        "Will run " + steps + " steps. (ETA " + totalSleepTime + " seconds)");

    System.out.println();
    System.out.println("Tape: ");
    for (int i = 0; i < steps; i++) {
      Thread.sleep(sleep.toMillis());
      System.out.print("\r" + tape.getString());
      machine.step();
    }
    System.out.println("\n\nMachine finished running.");
    System.out.println("---------------------------\n");

    machine.getHistory().printTechnicalReport();

    System.out.println();
    System.out.println("Digits computed: " + tape.readBinarySequenceFromTape().length());
    System.out.println();
    System.out.println("Computed sequence: " + tape.readBinarySequenceFromTape());
  }
}

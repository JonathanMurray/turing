package turing;

import java.util.HashMap;
import java.util.List;

public class Main {

  public static void main(String[] args) throws InterruptedException {
    interactiveComputation();
  }

  private static void interactiveComputation() throws InterruptedException {
    Tape tape = new Tape();
    Machine machine = ExampleMachines.increasingNumberOfOnesDelimitedByZero(tape);

    System.out.println();
    System.out.println(
        "--------------------------------------------------------------------------------");
    System.out.println(
        " Turing machine that computes the (infinite) binary sequence 001011011101111... ");
    System.out.println(
        "--------------------------------------------------------------------------------");
    System.out.println();

    int steps = 150;
    int sleep = 100;
    int totalSleepTime = (int) Math.round(steps * sleep / 1000.0);
    System.out.println(
        "Will run " + steps + " steps. (ETA " + totalSleepTime + " seconds)");

    System.out.println();
    System.out.println("Tape: ");
    for (int i = 0; i < steps; i++) {
      Thread.sleep(sleep);
      System.out.print("\r" + tape);
      machine.step();
    }
    System.out.println("\n");
    System.out.println("Steps run: " + steps);
    System.out.println("Instructions executed: " + machine.getInstructionHistory().size());
    List<Integer> mConfigurationHistory = machine.getMConfigurationHistory();
    HashMap<Integer, Integer> mConfigurationCounts = new HashMap<>();
    for (int mConfigurationIndex : mConfigurationHistory) {
      Integer currentCount = mConfigurationCounts.putIfAbsent(mConfigurationIndex, 0);
      mConfigurationCounts.put(mConfigurationIndex, currentCount != null ? currentCount + 1 : 1);
    }
    System.out.println("MConfiguration history counts: " + mConfigurationCounts);
    System.out.println("Digits computed: " + tape.readBinarySequenceFromTape().length());
    System.out.println();
    System.out.println("Computed sequence: " + tape.readBinarySequenceFromTape());
  }
}

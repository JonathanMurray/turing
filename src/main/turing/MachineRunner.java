package turing;

import java.io.InputStream;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Scanner;

public class MachineRunner {

  public static void runForDuration(Machine machine, Tape tape, Duration maxDuration) {
    System.out.println("Running...");
    Instant currentInstant = Instant.now();
    Instant startTime = currentInstant;
    Instant endTime = startTime.plus(maxDuration);
    Instant nextPrint = currentInstant;
    while (currentInstant.isBefore(endTime)) {
      currentInstant = Instant.now();
      machine.step();
      if (currentInstant.isAfter(nextPrint)) {
        System.out.print("\rDigits computed: " + tape.readBinarySequenceFromTape().length());
        nextPrint = currentInstant.plus(Duration.ofMillis(100));
      }
    }

    System.out.println("\n------------------------------------------");
    printResults(machine, tape);
  }

  public static void runInteractively(Machine machine, Tape tape, Duration sleepBetweenMConfigs,
      Duration sleepBetweenInstructions, InputStream userInputStream, TapeStringType tapeStringType)
      throws InterruptedException {
    Scanner userInputScanner = new Scanner(userInputStream);
    System.out.println();
    System.out.println(tape.createSnapshot().getString(tapeStringType) + "\n");
    while (true) {
      System.out.print("\nCurrent m-config ");
      System.out.println(machine.getActiveMConfiguration() + "\n");
      System.out.println("Scanned symbol: " + machine.peekAtScannedSymbol());
      System.out.println("Will execute instructions" + machine.peekAtNextInstructions() + "...");
      System.out.println("Press ENTER to continue (q to quit)");
      String userInput = userInputScanner.nextLine().trim();
      if (userInput.equals("q") || userInput.equals("quit")) {
        System.out.println("Quitting...\n");
        break;
      }

      System.out.print(tape.createSnapshot().getString(tapeStringType));
      Thread.sleep(sleepBetweenMConfigs.toMillis());
      List<TapeSnapshot> snapshots = machine.stepAndGetSnapshotAfterEachInstruction();
      for (int i = 0; i < snapshots.size(); i++) {
        TapeSnapshot snapshot = snapshots.get(i);
        System.out.print("\r" + snapshot.getString(tapeStringType));
        if (i < snapshots.size() - 1) {
          Thread.sleep(sleepBetweenInstructions.toMillis());
        }
      }
      Thread.sleep(sleepBetweenMConfigs.toMillis());
      System.out.println();
    }

    printResults(machine, tape);
  }

  public static void runVisually(Machine machine, Tape tape, int steps, Duration sleep,
      TapeStringType tapeStringType) throws InterruptedException {

    int totalSleepTime = (int) Math.round(steps * sleep.toMillis() / 1000.0);
    System.out.println(
        "Will run " + steps + " steps. (ETA " + totalSleepTime + " seconds)");

    System.out.println();
    System.out.println("Tape: ");
    for (int i = 0; i < steps; i++) {
      Thread.sleep(sleep.toMillis());
      System.out.print("\r" + tape.getString(tapeStringType));
      machine.step();
    }
    System.out.println("\n\nMachine finished running.");
    System.out.println("---------------------------\n");

    printResults(machine, tape);
  }

  private static void printResults(Machine machine, Tape tape) {
    machine.getHistory().printTechnicalReport();

    TapeSnapshot tapeSnapshot = tape.createSnapshot();
    System.out.println();
    System.out.println("Digits computed: " + tapeSnapshot.readBinarySequenceFromTape().length());
    System.out.println();
    System.out.println("Computed sequence: " + tapeSnapshot.readBinarySequenceFromTape());
  }
}

package turing;

import java.io.InputStream;
import java.io.PrintStream;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Scanner;

public class MachineRunner {

  public static void runForDuration(Machine machine, Tape tape, Duration maxDuration,
      PrintStream out) {
    out.println("Running...");
    Instant currentInstant = Instant.now();
    Instant startTime = currentInstant;
    Instant endTime = startTime.plus(maxDuration);
    Instant nextPrint = currentInstant;
    while (currentInstant.isBefore(endTime)) {
      currentInstant = Instant.now();
      machine.step();
      if (currentInstant.isAfter(nextPrint)) {
        out.print("\rDigits computed: " + tape.readBinarySequenceFromTape().length());
        nextPrint = currentInstant.plus(Duration.ofMillis(100));
      }
    }

    out.println("\n------------------------------------------");
    printResults(machine, tape, out);
  }

  public static void runInteractively(Machine machine, Tape tape, Duration sleepBetweenMConfigs,
      Duration sleepBetweenInstructions, InputStream userInputStream, TapeStringType tapeStringType,
      PrintStream out)
      throws InterruptedException {
    Scanner userInputScanner = new Scanner(userInputStream);
    out.println();
    out.println(tape.createSnapshot().getString(tapeStringType) + "\n");
    while (true) {
      out.print("\nCurrent m-config ");
      out.println(machine.getActiveMConfiguration() + "\n");
      out.println("Scanned symbol: " + machine.peekAtScannedSymbol());
      out.println("Will execute instructions" + machine.peekAtNextInstructions() + "...");
      out.println("Press ENTER to continue (q to quit)");
      String userInput = userInputScanner.nextLine().trim();
      if (userInput.equals("q") || userInput.equals("quit")) {
        out.println("Quitting...\n");
        break;
      }

      out.print(tape.createSnapshot().getString(tapeStringType));
      Thread.sleep(sleepBetweenMConfigs.toMillis());
      List<TapeSnapshot> snapshots = machine.stepAndGetSnapshotAfterEachInstruction();
      for (int i = 0; i < snapshots.size(); i++) {
        TapeSnapshot snapshot = snapshots.get(i);
        out.print("\r" + snapshot.getString(tapeStringType));
        if (i < snapshots.size() - 1) {
          Thread.sleep(sleepBetweenInstructions.toMillis());
        }
      }
      Thread.sleep(sleepBetweenMConfigs.toMillis());
      out.println();
    }

    printResults(machine, tape, out);
  }

  public static void runVisually(Machine machine, Tape tape, int steps, Duration sleep,
      TapeStringType tapeStringType, PrintStream out) throws InterruptedException {

    int totalSleepTime = (int) Math.round(steps * sleep.toMillis() / 1000.0);
    out.println(
        "Will run " + steps + " steps. (ETA " + totalSleepTime + " seconds)");

    out.println();
    out.println("Tape: ");
    for (int i = 0; i < steps; i++) {
      Thread.sleep(sleep.toMillis());
      out.print("\r" + tape.getString(tapeStringType));
      machine.step();
    }
    out.println("\n\nMachine finished running.");
    out.println("---------------------------\n");

    printResults(machine, tape, out);
  }

  private static void printResults(Machine machine, Tape tape, PrintStream out) {
    machine.getHistory().printTechnicalReport(out);

    TapeSnapshot tapeSnapshot = tape.createSnapshot();
    out.println();
    out.println("Digits computed: " + tapeSnapshot.readBinarySequenceFromTape().length());
    out.println();
    out.println("Computed sequence: " + tapeSnapshot.readBinarySequenceFromTape());
  }
}

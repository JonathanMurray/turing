package turing;

import java.util.List;
import java.util.Optional;

/**
 * A machine is defined by it's m-configurations. Using their rules, it interacts with the tape. The
 * end-goal is usually to compute a binary sequence by printing it on the tape.
 */
public class Machine {

  private final List<MConfiguration> mConfigurations;
  private final Tape tape;
  private int activeMConfigurationIndex = 0;
  private final MachineHistory history = new MachineHistory();

  public Machine(List<MConfiguration> mConfigurations, Tape tape) {
    this.mConfigurations = mConfigurations;
    for (MConfiguration mc : mConfigurations) {
      Optional<Integer> invalidNextMConfigurationIndex = mc.getPossibleNextMConfigurationIndices()
          .filter(index -> index < 0 || index >= mConfigurations.size())
          .findFirst();
      if (invalidNextMConfigurationIndex.isPresent()) {
        throw new IllegalArgumentException(
            "MConfiguration has 'next' = " + invalidNextMConfigurationIndex.get()
                + "! Valid index interval = [0, " + (mConfigurations.size() - 1) + "]");
      }
    }
    this.tape = tape;
  }

  public void step() {

    MConfiguration activeMConfiguration = mConfigurations.get(activeMConfigurationIndex);
    history.mConfigurationVisited(activeMConfigurationIndex);
    Character scanned = tape.read();
    if (!activeMConfiguration.hasRowMatchingSymbol(scanned)) {
      throw new ScannedUnHandledSymbol("Machine crashed! Scanned '" + scanned
          + "' but current configuration doesn't handle it. Handled: "
          + activeMConfiguration.getSymbolMatchers());
    }
    for (Instruction instruction : activeMConfiguration.getInstructions(scanned)) {
      instruction.applyOnTape(tape);
      history.instructionExecuted(instruction);
    }
    activeMConfigurationIndex = activeMConfiguration.getNextMConfiguration(scanned);
  }

  public MachineHistory getHistory() {
    return history;
  }

  public static class ScannedUnHandledSymbol extends RuntimeException {

    public ScannedUnHandledSymbol(String reason) {
      super(reason);
    }
  }
}

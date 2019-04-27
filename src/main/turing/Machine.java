package turing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A machine is defined by it's m-configurations. Using their rules, it interacts with the tape. The
 * end-goal is usually to compute a binary sequence by printing it on the tape.
 */
public class Machine {

  private final Map<String, MConfiguration> mConfigurations;
  private final Tape tape;
  private final MachineHistory history = new MachineHistory();
  private String activeMConfigurationId;

  public Machine(List<MConfiguration> mConfigurations, Tape tape) {
    activeMConfigurationId = mConfigurations.get(0).getId();
    this.mConfigurations = new HashMap<>();
    for (MConfiguration mConfiguration : mConfigurations) {
      this.mConfigurations.put(mConfiguration.getId(), mConfiguration);
    }
    assertNoCorrectMConfigurationJumps(this.mConfigurations);
    this.tape = tape;
  }

  private void assertNoCorrectMConfigurationJumps(Map<String, MConfiguration> mConfigurations) {
    for (MConfiguration mc : mConfigurations.values()) {
      Optional<String> invalidNextMConfigurationIndex = mc.getPossibleNextMConfigurationIndices()
          .filter((String index) -> !mConfigurations.containsKey(index))
          .findFirst();

      if (invalidNextMConfigurationIndex.isPresent()) {
        throw new IllegalArgumentException(
            "MConfiguration has 'next' = '" + invalidNextMConfigurationIndex.get()
                + "'! Valid values: " + mConfigurations.keySet());
      }
    }
  }

  public void step() {

    MConfiguration activeMConfiguration = mConfigurations.get(activeMConfigurationId);
    history.mConfigurationVisited(activeMConfigurationId);
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
    activeMConfigurationId = activeMConfiguration.getNextMConfiguration(scanned);
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

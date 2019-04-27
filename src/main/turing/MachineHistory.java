package turing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class MachineHistory {

  private List<String> visitedMConfigurations = new ArrayList<>();
  private List<Instruction> executedInstructions = new ArrayList<>();

  public boolean mConfigurationVisited(String activeMConfigurationId) {
    return visitedMConfigurations.add(activeMConfigurationId);
  }

  public void instructionExecuted(Instruction instruction) {
    executedInstructions.add(instruction);
  }

  public void printTechnicalReport() {
    HashMap<String, Integer> mcCounts = getMConfigurationVisitCounts();
    Map<String, Double> mcPercentages = getMConfigurationVisitPercentages();
    System.out.println("Instructions executed: " + executedInstructions.size());
    System.out.println("Number of times each m-configuration was visited:");
    for (String mConfigurationId : mcCounts.keySet()) {
      int count = mcCounts.get(mConfigurationId);
      double percentage = mcPercentages.get(mConfigurationId);
      System.out.println(String.format("  %s: %s (%.2f%%)",
          mConfigurationId, count, percentage * 100));
    }
  }

  private HashMap<String, Integer> getMConfigurationVisitCounts() {
    HashMap<String, Integer> mConfigurationCounts = new HashMap<>();
    for (String mConfigurationId : visitedMConfigurations) {
      Integer currentCount = mConfigurationCounts.putIfAbsent(mConfigurationId, 0);
      mConfigurationCounts.put(mConfigurationId, currentCount != null ? currentCount + 1 : 1);
    }
    return mConfigurationCounts;
  }

  private Map<String, Double> getMConfigurationVisitPercentages() {
    return getMConfigurationVisitCounts().entrySet().stream()
        .collect(Collectors.toMap(
            Entry::getKey,
            entry -> entry.getValue() / (double) visitedMConfigurations.size())
        );
  }
}

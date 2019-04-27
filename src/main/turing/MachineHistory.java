package turing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class MachineHistory {

  private List<Integer> visitedMConfigurations = new ArrayList<>();
  private List<Instruction> executedInstructions = new ArrayList<>();

  public boolean mConfigurationVisited(int activeMConfigurationIndex) {
    return visitedMConfigurations.add(activeMConfigurationIndex);
  }

  public void instructionExecuted(Instruction instruction) {
    executedInstructions.add(instruction);
  }

  public void printTechnicalReport() {
    HashMap<Integer, Integer> mcCounts = getMConfigurationVisitCounts();
    Map<Integer, Double> mcPercentages = getMConfigurationVisitPercentages();
    System.out.println("Instructions executed: " + executedInstructions.size());
    System.out.println("Number of times each m-configuration was visited:");
    for (int mConfigurationIndex : mcCounts.keySet()) {
      int count = mcCounts.get(mConfigurationIndex);
      double percentage = mcPercentages.get(mConfigurationIndex);
      System.out.println(String.format("  m-config %s: %s (%.2f%%)",
          mConfigurationIndex, count, percentage * 100));
    }
  }

  private HashMap<Integer, Integer> getMConfigurationVisitCounts() {
    HashMap<Integer, Integer> mConfigurationCounts = new HashMap<>();
    for (int mConfigurationIndex : visitedMConfigurations) {
      Integer currentCount = mConfigurationCounts.putIfAbsent(mConfigurationIndex, 0);
      mConfigurationCounts.put(mConfigurationIndex, currentCount != null ? currentCount + 1 : 1);
    }
    return mConfigurationCounts;
  }

  private Map<Integer, Double> getMConfigurationVisitPercentages() {
    return getMConfigurationVisitCounts().entrySet().stream()
        .collect(Collectors.toMap(
            Entry::getKey,
            entry -> entry.getValue() / (double) visitedMConfigurations.size())
        );
  }
}

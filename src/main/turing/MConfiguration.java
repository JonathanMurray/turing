package turing;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MConfiguration {

  private final List<Row> rows;

  public MConfiguration(SymbolMatcher symbolMatcher, int nextMConfiguration,
      Instruction... instructions) {
    this.rows = Collections.singletonList(new Row(symbolMatcher, nextMConfiguration, instructions));
  }

  public MConfiguration(Row... rows) {
    this.rows = Arrays.asList(rows);
  }

  public boolean hasRowMatchingSymbol(Character scannedSymbol) {
    return rows.stream().anyMatch(r -> r.symbolMatcher.test(scannedSymbol));
  }

  public List<Instruction> getInstructions(Character scannedSymbol) {
    return Arrays.asList(findRowMatchedByScannedSymbol(scannedSymbol).instructions);
  }

  public int getNextMConfiguration(Character scannedSymbol) {
    return findRowMatchedByScannedSymbol(scannedSymbol).nextMConfiguration;
  }

  private Row findRowMatchedByScannedSymbol(Character scannedSymbol) {
    return rows.stream()
        .filter(r -> r.symbolMatcher.test(scannedSymbol))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Unmatched symbol: " + scannedSymbol));
  }

  public List<SymbolMatcher> getSymbolMatchers() {
    return rows.stream()
        .map(row -> row.symbolMatcher)
        .collect(Collectors.toList());
  }

  public Stream<Integer> getPossibleNextMConfigurationIndices() {
    return rows.stream()
        .map(row -> row.nextMConfiguration);
  }

  public static class Row {

    private final SymbolMatcher symbolMatcher;
    private final int nextMConfiguration;
    private final Instruction[] instructions;

    public Row(SymbolMatcher symbolMatcher, int nextMConfiguration, Instruction... instructions) {
      this.symbolMatcher = symbolMatcher;
      this.nextMConfiguration = nextMConfiguration;
      this.instructions = instructions;
    }
  }

}

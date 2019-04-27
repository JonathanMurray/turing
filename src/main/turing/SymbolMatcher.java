package turing;

import java.util.Arrays;
import java.util.List;

/**
 * Rules used by m-configurations to determine the machine's course of action given what symbol it's
 * currently scanning from the tape.
 */
public interface SymbolMatcher {

  boolean test(Character symbol);

  class MatchSymbol implements SymbolMatcher {

    private final Character symbol;

    public MatchSymbol(Character symbol) {
      this.symbol = symbol;
    }

    @Override
    public boolean test(Character symbol) {
      if (this.symbol == null) {
        return symbol == null;
      }
      return this.symbol.equals(symbol);
    }

    @Override
    public String toString() {
      return String.valueOf(symbol);
    }
  }

  class MatchAny implements SymbolMatcher {

    @Override
    public boolean test(Character symbol) {
      return true;
    }

    @Override
    public String toString() {
      return "Any";
    }
  }

  class MatchAnySymbol implements SymbolMatcher {

    @Override
    public boolean test(Character symbol) {
      return symbol != null;
    }

    @Override
    public String toString() {
      return "Any(not empty)";
    }
  }

  class MatchAnyOf implements SymbolMatcher {

    private final List<Character> symbols;

    public MatchAnyOf(Character... symbols) {
      this.symbols = Arrays.asList(symbols);
    }

    @Override
    public boolean test(Character symbol) {
      return symbols.contains(symbol);
    }

    @Override
    public String toString() {
      return "Any(" + symbols + ")";
    }
  }
}

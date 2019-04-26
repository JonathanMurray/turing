package turing;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.Collections;
import org.junit.Test;
import turing.MConfiguration.Row;
import turing.Instruction.PrintInstruction;
import turing.SymbolMatcher.MatchSymbol;

public class MConfigurationTest {

  @Test
  public void shouldUseScannedSymbol() {
    MConfiguration mConfiguration = new MConfiguration(
        new Row(new MatchSymbol(null), 1, new PrintInstruction('A')),
        new Row(new MatchSymbol('X'), 2, new PrintInstruction('Y')));

    assertThat(mConfiguration.getInstructions(null),
        is(Collections.<Instruction>singletonList(new PrintInstruction('A'))));
    assertThat(mConfiguration.getNextMConfiguration(null), is(1));

    assertThat(mConfiguration.getInstructions('X'),
        is(Collections.<Instruction>singletonList(new PrintInstruction('Y'))));
    assertThat(mConfiguration.getNextMConfiguration('X'), is(2));
  }
}
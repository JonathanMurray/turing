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
        "id",
        new Row(new MatchSymbol(null), "goFromNull", new PrintInstruction('A')),
        new Row(new MatchSymbol('X'), "goFromX", new PrintInstruction('Y')));

    assertThat(mConfiguration.getInstructions(null),
        is(Collections.<Instruction>singletonList(new PrintInstruction('A'))));
    assertThat(mConfiguration.getNextMConfiguration(null), is("goFromNull"));

    assertThat(mConfiguration.getInstructions('X'),
        is(Collections.<Instruction>singletonList(new PrintInstruction('Y'))));
    assertThat(mConfiguration.getNextMConfiguration('X'), is("goFromX"));
  }
}
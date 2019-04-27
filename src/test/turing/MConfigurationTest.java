package turing;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.Collections;
import org.junit.Test;
import turing.Instruction.PrintInstruction;
import turing.Instruction.RightInstruction;
import turing.MConfiguration.Row;
import turing.SymbolMatcher.MatchAny;
import turing.SymbolMatcher.MatchAnyOf;
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

  @Test
  public void canGiveStringRepresentationForOneRow() {
    MConfiguration mConfiguration = new MConfiguration(
        "<id>",
        new MatchAnyOf('1', '0', 'X'),
        "<next id>",
        new PrintInstruction('X'),
        new RightInstruction(), new RightInstruction());

    assertThat(mConfiguration.toString(), is("<id>: Any([1, 0, X]) --> [PX, R, R] --> <next id>"));
  }

  @Test
  public void canGiveStringRepresentationForSeveralRows() {
    MConfiguration mConfiguration = new MConfiguration(
        "<id>",
        new Row(
            new MatchAnyOf('1', '0', 'X'),
            "<next 1>",
            new PrintInstruction('X'),
            new RightInstruction(), new RightInstruction()),
        new Row(
            new MatchAny(),
            "<next 2>",
            new RightInstruction()
        ));

    String expected = "<id>:"
        + "\n  Any([1, 0, X]) --> [PX, R, R] --> <next 1>"
        + "\n  Any --> [R] --> <next 2>";

    assertThat(mConfiguration.toString(), is(expected));
  }
}
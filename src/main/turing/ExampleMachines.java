package turing;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import turing.Instruction.LeftInstruction;
import turing.Instruction.PrintInstruction;
import turing.Instruction.RightInstruction;
import turing.MConfiguration.Row;
import turing.SymbolMatcher.MatchAny;
import turing.SymbolMatcher.MatchAnyOf;
import turing.SymbolMatcher.MatchAnySymbol;
import turing.SymbolMatcher.MatchSymbol;

public class ExampleMachines {

  // From p87 in "The Annotated Turing"
  // Produces the infinite sequence 001011011101111...
  public static Machine increasingNumberOfOnesDelimitedByZero(Tape tape) {
    MConfiguration b0 = new MConfiguration(
        new MatchSymbol(null),
        1,
        new PrintInstruction('e'),
        new RightInstruction(),
        new PrintInstruction('e'),
        new RightInstruction(),
        new PrintInstruction('0'),
        new RightInstruction(),
        new RightInstruction(),
        new PrintInstruction('0'),
        new LeftInstruction(),
        new LeftInstruction());

    MConfiguration o1 = new MConfiguration(
        new Row(
            new MatchSymbol('1'),
            1,
            new RightInstruction(),
            new PrintInstruction('x'),
            new LeftInstruction(),
            new LeftInstruction(),
            new LeftInstruction()
        ),
        new Row(
            new MatchSymbol('0'),
            2
        )
    );

    MConfiguration q2 = new MConfiguration(
        new Row(
            new MatchAnyOf('0', '1'),
            2,
            new RightInstruction(),
            new RightInstruction()
        ),
        new Row(
            new MatchSymbol(null),
            3,
            new PrintInstruction('1'),
            new LeftInstruction()
        )
    );

    MConfiguration p3 = new MConfiguration(
        new Row(
            new MatchSymbol('x'),
            2,
            new PrintInstruction(null),
            new RightInstruction()
        ),
        new Row(
            new MatchSymbol('e'),
            4,
            new RightInstruction()
        ),
        new Row(
            new MatchSymbol(null),
            3,
            new LeftInstruction(),
            new LeftInstruction()
        )
    );

    MConfiguration f4 = new MConfiguration(
        new Row(
            new MatchAnySymbol(),
            4,
            new RightInstruction(),
            new RightInstruction()
        ),
        new Row(
            new MatchSymbol(null),
            1,
            new PrintInstruction('0'),
            new LeftInstruction(),
            new LeftInstruction()
        )
    );

    List<MConfiguration> mConfigurations = Arrays.asList(b0, o1, q2, p3, f4);
    return new Machine(mConfigurations, tape);
  }

  public static Machine zeros(Tape tape) {
    MConfiguration mConfiguration = new MConfiguration(
        new MatchAny(),
        0,
        new PrintInstruction('0'),
        new RightInstruction());
    return new Machine(Collections.singletonList(mConfiguration), tape);
  }

  public static Machine alternatingOnesAndZeros(Tape tape) {
    MConfiguration a = new MConfiguration(
        new MatchAny(),
        1,
        new PrintInstruction('0'),
        new RightInstruction()
    );
    MConfiguration b = new MConfiguration(
        new MatchAny(),
        0,
        new PrintInstruction('1'),
        new RightInstruction()
    );
    return new Machine(Arrays.asList(a, b), tape);
  }

}

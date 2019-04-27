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
        "b",
        new MatchSymbol(null),
        "o",
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
        "o",
        new Row(
            new MatchSymbol('1'),
            "o",
            new RightInstruction(),
            new PrintInstruction('x'),
            new LeftInstruction(),
            new LeftInstruction(),
            new LeftInstruction()
        ),
        new Row(
            new MatchSymbol('0'),
            "q"
        )
    );

    MConfiguration q2 = new MConfiguration(
        "q",
        new Row(
            new MatchAnyOf('0', '1'),
            "q",
            new RightInstruction(),
            new RightInstruction()
        ),
        new Row(
            new MatchSymbol(null),
            "p",
            new PrintInstruction('1'),
            new LeftInstruction()
        )
    );

    MConfiguration p3 = new MConfiguration(
        "p",
        new Row(
            new MatchSymbol('x'),
            "q",
            new PrintInstruction(null),
            new RightInstruction()
        ),
        new Row(
            new MatchSymbol('e'),
            "f",
            new RightInstruction()
        ),
        new Row(
            new MatchSymbol(null),
            "p",
            new LeftInstruction(),
            new LeftInstruction()
        )
    );

    MConfiguration f4 = new MConfiguration(
        "f",
        new Row(
            new MatchAnySymbol(),
            "f",
            new RightInstruction(),
            new RightInstruction()
        ),
        new Row(
            new MatchSymbol(null),
            "o",
            new PrintInstruction('0'),
            new LeftInstruction(),
            new LeftInstruction()
        )
    );

    return new Machine(Arrays.asList(b0, o1, q2, p3, f4), tape);
  }

  public static Machine zeros(Tape tape) {
    List<MConfiguration> mConfigurations = Collections.singletonList(new MConfiguration(
        "printZero",
        new MatchAny(),
        "printZero",
        new PrintInstruction('0'),
        new RightInstruction()));
    return new Machine(mConfigurations, tape);
  }

  public static Machine alternatingOnesAndZeros(Tape tape) {
    List<MConfiguration> mConfigurations = Arrays.asList(new MConfiguration(
        "printZero",
        new MatchAny(),
        "printOne",
        new PrintInstruction('0'),
        new RightInstruction()
    ), new MConfiguration(
        "printOne",
        new MatchAny(),
        "printZero",
        new PrintInstruction('1'),
        new RightInstruction()
    ));

    return new Machine(mConfigurations, tape);
  }

}

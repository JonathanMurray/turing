package turing;

public interface Instruction {

  void applyOnTape(Tape tape);

  class PrintInstruction implements Instruction {

    private final Character symbol;

    public PrintInstruction(Character symbol) {
      this.symbol = symbol;
    }

    @Override
    public void applyOnTape(Tape tape) {
      tape.print(symbol);
    }

    @Override
    public boolean equals(Object obj) {
      return obj instanceof PrintInstruction && ((PrintInstruction) obj).symbol == symbol;
    }

    @Override
    public String toString() {
      return "P" + symbol;
    }
  }

  class RightInstruction implements Instruction {

    @Override
    public void applyOnTape(Tape tape) {
      tape.right();
    }

    @Override
    public boolean equals(Object obj) {
      return obj instanceof RightInstruction;
    }

    @Override
    public String toString() {
      return "R";
    }
  }

  class LeftInstruction implements Instruction {

    @Override
    public void applyOnTape(Tape tape) {
      tape.left();
    }

    @Override
    public boolean equals(Object obj) {
      return obj instanceof LeftInstruction;
    }

    @Override
    public String toString() {
      return "L";
    }
  }
}

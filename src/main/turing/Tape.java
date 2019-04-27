package turing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * The tape is the output of the machine. Here is where the results will be printed. It can also be
 * used by the machine as a scratchpad to store intermediate results that are needed for the
 * computation.
 *
 * In the case of a "Universal Turing Machine", the tape contains "code" that the machine executes
 * but that is outside the scope of this project.
 */
public class Tape {

  private final List<Character> list;
  private int headerIndex = 0;

  public Tape() {
    list = new LinkedList<>();
    list.add(null);
  }

  public Tape(Character... entries) {
    list = Arrays.asList(entries);
  }

  public void print(Character symbol) {
    list.set(headerIndex, symbol);
  }

  public Character read() {
    return list.get(headerIndex);
  }

  public void right() {
    headerIndex++;
    if (headerIndex >= list.size()) {
      list.add(null);
    }
  }

  public void left() {
    headerIndex--;
    if (headerIndex < 0) {
      throw new IllegalStateException("Moving left when at index 0!");
    }
  }

  //Used in tests
  public List<Character> getList() {
    return new ArrayList<>(list);
  }

  @Override
  public String toString() {
    return getString(TapeStringType.REGULAR);
  }

  //Create a snapshot of the tape that remains the same even if the tape is modified
  public TapeSnapshot createSnapshot() {
    return new TapeSnapshot(new ArrayList<>(list), headerIndex);
  }

  public String getString(TapeStringType type) {
    return new TapeSnapshot(list, headerIndex).getString(type);
  }

  public String readBinarySequenceFromTape() {
    return new TapeSnapshot(list, headerIndex).readBinarySequenceFromTape();
  }
}

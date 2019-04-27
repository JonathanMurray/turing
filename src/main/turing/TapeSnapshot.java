package turing;

import java.util.List;

/**
 * Represents the state of the tape at a given time. If a cloned symbol list is passed in, the
 * snapshot remains the same even if the tape is modified.
 */
public class TapeSnapshot {

  private final List<Character> list;
  private final int headerIndex;

  public TapeSnapshot(List<Character> list, int headerIndex) {
    this.list = list;
    this.headerIndex = headerIndex;
  }

  public String getString(TapeStringType type) {
    switch (type) {
      case COMPACT:
        return getCompactString();
      case REGULAR:
        return getRegularString();
      case VERBOSE:
        return getVerboseString();
      default:
        throw new IllegalArgumentException("Unhandled type: " + type);
    }
  }

  private String getRegularString() {
    StringBuilder sb = new StringBuilder();
    if (headerIndex == 0) {
      sb.append('(');
    } else {
      sb.append(' ');
    }
    for (int i = 0; i < list.size(); i++) {
      Character symbol = list.get(i);
      char ch = symbol != null ? symbol : ' ';
      if (i == headerIndex - 1) {
        sb.append(ch).append('(');
      } else if (i == headerIndex) {
        sb.append(ch).append(")");
      } else {
        sb.append(ch).append(" ");
      }
    }
    return sb.toString();
  }

  private String getVerboseString() {
    StringBuilder sb = new StringBuilder();
    sb.append('|');
    for (int i = 0; i < list.size(); i++) {
      Character symbol = list.get(i);
      char ch = symbol != null ? symbol : ' ';
      if (i == headerIndex) {
        sb.append('(').append(ch).append(")|");
      } else {
        sb.append(' ').append(ch).append(" |");
      }
    }
    return sb.toString();
  }

  private String getCompactString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < list.size(); i++) {
      Character symbol = list.get(i);
      char ch = symbol != null ? symbol : ' ';
      if (i == headerIndex) {
        sb.append('_');
      } else {
        sb.append(ch);
      }
    }
    return sb.toString();
  }

  public String readBinarySequenceFromTape() {
    StringBuilder sb = new StringBuilder();
    for (Character symbol : list) {
      if (symbol != null && (symbol == '1' || symbol == '0')) {
        sb.append(symbol);
      }
    }
    return sb.toString();
  }
}

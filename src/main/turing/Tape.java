package turing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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

  public List<Character> getList() {
    return new ArrayList<>(list);
  }


  @Override
  public String toString() {
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

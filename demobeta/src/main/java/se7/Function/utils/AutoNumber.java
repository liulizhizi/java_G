package se7.Function.utils;


import java.util.ArrayList;
import java.util.List;

public class AutoNumber {
  private List<Integer> unusedNumbers;
  private int nextNumber;

  public AutoNumber() {
    unusedNumbers = new ArrayList<>();
    nextNumber = 1;
  }

  public int getNextNumber() {
    if (unusedNumbers.isEmpty()) {
      int number = nextNumber;
      nextNumber++;
      return number;
    } else {
      return unusedNumbers.remove(0);
    }
  }

  public void releaseNumber(int number) {
    if (number == nextNumber - 1) {
      nextNumber--;
    } else {
      unusedNumbers.add(number);
    }
  }
}
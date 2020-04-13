package cz.barny;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SolverState {
    public static final int ROWS = 9;
    private final Integer[][] initial;

    private final Map<Pair, Integer> numbers = new HashMap<>();

    static final Set<Integer> allNumbers = IntStream.range(1, 10)
            .mapToObj(i -> Integer.valueOf(i))
            .collect(Collectors.toSet());
    private Map<Pair, Set<Integer>> missingNumbers = new HashMap<>();

    public SolverState(SolverState solverState, Pair pos, Integer num) {
        this.initial = solverState.initial;
        numbers.putAll(solverState.numbers);
        numbers.put(pos, num);
        initMissingNumbers();
    }

    public Map<Pair, Set<Integer>> getMissingNumbers() {
        return missingNumbers;
    }

    public SolverState(Integer[][] initial) {
        this.initial = initial;
        initMissingNumbers();
    }

    private void initMissingNumbers() {
        int changes;
        do {
            changes = 0;
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < ROWS; j++) {
                    if (getNumberAt(i, j) == null) {
                        Set<Integer> possibleNumbers = possibleNumbers(i, j);
                        if (possibleNumbers.size() == 0) {
                            throw new SolutionDoesNotExist();
                        }
                        Pair key = new Pair(i, j);
                        if (possibleNumbers.size() == 1) {
                            numbers.put(key, possibleNumbers.iterator().next());
                            missingNumbers.remove(key);
                            changes++;
                        } else {
                            Set<Integer> old = missingNumbers.put(key, possibleNumbers);
                            if (old == null || old.size() > possibleNumbers.size()) {
                                changes++;
                            }
                        }
                    }
                }
            }
        } while (changes > 1);
    }

    public Set<Integer> possibleNumbers(int row, int column) {
        Integer num = getNumberAt(row, column);
        if (num != null) {
            return Collections.singleton(num);
        }

        Set<Integer> result = new HashSet<>(allNumbers);
        result.removeAll(getRow(row));
        result.removeAll(getColumn(column));
        result.removeAll(getRectangle(row, column));
        return result;
    }

    public Integer getNumberAt(int row, int column) {
        Integer num = initial[row][column];
        if (num != null) {
            return num;
        }
        return numbers.get(new Pair(row, column));
    }

    /**
     * Get all numbers in the given row
     * @param row row
     * @return set of numbers
     */
    Set<Integer> getRow(int row) {
        Set<Integer> result = new HashSet<>();
        for (int i = 0; i < ROWS; i++) {
            Integer number = getNumberAt(row, i);
            if (number != null) {
                result.add(number);
            }
        }
        return result;
    }

    Set<Integer> getColumn(int column) {
        Set<Integer> result = new HashSet<>();
        for (int i = 0; i < ROWS; i++) {
            Integer number = getNumberAt(i, column);
            if (number != null) {
                result.add(number);
            }
        }
        return result;
    }

    Set<Integer> getRectangle(int row, int column) {
        Set<Integer> result = new HashSet<>();
        int firstRow = areaStart(row);
        for (int i = firstRow; i < firstRow + 3; i++) {
            int firstCol = areaStart(column);
            for (int j = firstCol; j < firstCol + 3; j++) {
                Integer number = getNumberAt(i, j);
                if (number != null) {
                    result.add(number);
                }
            }
        }
        return result;
    }

    private int areaStart(int row) {
        return (row / 3) * 3;
    }

    public SolverState update(Pair pos, Integer num) {
        try {
            return new SolverState(this, pos, num);
        } catch (SolutionDoesNotExist ex) {
            return null;
        }
    }

}

package cz.barny;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;

import static cz.barny.SolverState.ROWS;

/**
 * Hello world!
 */
public class Solver {

    public Integer[][] solve(Integer[][] input) {

        SolverState solverState = new SolverState(input);
        return solve(solverState);
    }

    private Integer[][] solve(SolverState solverState) {
        Map<Pair, Set<Integer>> missingNumbers = solverState.getMissingNumbers();
        print(solverState);
        if (missingNumbers.isEmpty()) {
            return asArray(solverState);
        }
        Map.Entry<Pair, Set<Integer>> next = missingNumbers.entrySet().stream()
                .sorted(Comparator.comparing(e -> e.getValue().size()))
                .findFirst()
                .get();
        for (Integer num : next.getValue()) {
            SolverState newSolverState = solverState.update(next.getKey(), num);
            if (newSolverState != null) {
                if (newSolverState.getMissingNumbers().isEmpty()) {
                    return asArray(newSolverState);
                } else {
                    Integer[][] solve = solve(newSolverState);
                    if (solve == null) {
                        continue;
                    }
                    return solve;
                }
            }
        }
        return null;
    }

    private Integer[][] asArray(SolverState newSolverState) {
        Integer[][] result = new Integer[ROWS][];
        for (int i = 0; i < ROWS; i++) {
            result[i] = new Integer[ROWS];
            for (int j = 0; j < ROWS; j++) {
                Integer number = newSolverState.getNumberAt(i, j);
                result[i][j] = number;
            }
        }
        return result;
    }

    public void print(SolverState solverState) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < ROWS; j++) {
                Integer number = solverState.getNumberAt(i, j);
                System.out.print(number == null ? 0 : number);
                System.out.print("\t");
            }
            System.out.println();
        }

        System.out.println();
    }
}

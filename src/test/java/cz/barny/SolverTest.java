package cz.barny;

import org.junit.Test;

import static cz.barny.SolverState.ROWS;
import static cz.barny.SolverState.allNumbers;
import static org.assertj.core.api.Assertions.assertThat;


public class SolverTest {
    @Test
    public void solve() {
        Solver solver = new Solver();
        Integer[][] input = {
                {null, 9, null, 7, 3, null, 8, 2, 6},
                {1, 3, 2, null, null, null, null, null, 7},
                {6, null, null, null, null, 4, 1, null, null},
                {4, null, null, null, 9, null, 2, 8, null},
                {null, null, 3, null, null, 2, null, null, 4},
                {null, 7, null, 8, 4, 3, 6, null, 5},
                {null, 4, null, 6, null, null, 7, null, null},
                {9, 2, null, 3, 7, 8, null, 4, 1},
                {7, null, 1, 4, 2, null, null, 6, null}
        };
        Integer[][] result = solver.solve(input);
        Integer[][] expected = {
                {5, 9, 4, 7, 3, 1, 8, 2, 6},
                {1, 3, 2, 9, 8, 6, 4, 5, 7},
                {6, 8, 7, 2, 5, 4, 1, 3, 9},
                {4, 6, 5, 1, 9, 7, 2, 8, 3},
                {8, 1, 3, 5, 6, 2, 9, 7, 4},
                {2, 7, 9, 8, 4, 3, 6, 1, 5},
                {3, 4, 8, 6, 1, 5, 7, 9, 2},
                {9, 2, 6, 3, 7, 8, 5, 4, 1},
                {7, 5, 1, 4, 2, 9, 3, 6, 8}
        };
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void solve2() {
        Solver solver = new Solver();
        Integer[][] input = {
                {7, 2, null, null, null, 3, 6, null, null},
                {null, null, null, null, null, null, 8, null, null},
                {null, null, 4, null, null, null, 2, 5, 3},
                {null, null, null, null, null, null, null, 7, null},
                {null, null, null, 7, null, 4, null, 3, null},
                {6, null, null, null, null, null, 9, null, null},
                {2, null, null, null, null, null, 3, 6, null},
                {9, null, 8, null, null, null, null, null, null},
                {null, 7, null, 6, 9, null, 4, 2, null}
        };
        Integer[][] result = solver.solve(input);
        checkResult(result);
    }

    @Test
    public void solve3() {
        Solver solver = new Solver();
        Integer[][] input = {
                {null, null, 2, null, null, null, null, 4, 1},
                {null, null, null, null, 8, 2, null, 7, null},
                {null, null, null, null, 4, null, null, null, 9},
                {2, null, null, null, 7, 9, 3, null, null},
                {null, 1, null, null, null, null, null, 8, null},
                {null, null, 6, 8, 1, null, null, null, 4},
                {1, null, null, null, 9, null, null, null, null},
                {null, 6, null, 4, 3, null, null, null, null},
                {8, 5, null, null, null, null, 4, null, null}
                };
        Integer[][] result = solver.solve(input);
        checkResult(result);
    }

    private void checkResult(Integer[][] result) {
        SolverState solverState = new SolverState(result);
        for (int i = 0; i < ROWS; i++) {
            assertThat(solverState.getRow(i))
                    .isEqualTo(allNumbers);
            assertThat(solverState.getColumn(i))
                    .isEqualTo(allNumbers);
        }
        for (int i = 0; i < 3; i += 3) {
            for (int j = 0; j < 3; j += 3) {
                assertThat(solverState.getRectangle(i, j))
                        .isEqualTo(allNumbers);

            }
        }
    }
}
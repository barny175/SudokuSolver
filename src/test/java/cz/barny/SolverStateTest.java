package cz.barny;

import org.assertj.core.util.Sets;
import org.junit.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class SolverStateTest {

    private Integer[][] initial = new Integer[][]{
            {null, 9, null,  7, 3, null,       8, 2, 6},
            {1, 3, 2, null,  null, null,       null, null, 7},
            {6, null, null,  null, null, 4,    1, null, null},

            {4, null, null,  null, 9, null,    2, 8, null},
            {null, null, 3,  null, null, 2,    null, null, 4},
            {null, 7, null,  8, 4, 3,          6, null, 5},

            {null, 4, null,  6, null, null,    7, null, null},
            {9, 2, null,     3, 7, 8,          null, 4, 1},
            {7, null, 1,     4, 2, null,       null, 6, null}
    };

    @Test
    public void possible_numbers() {
        SolverState solverState = new SolverState(initial);
        Set<Integer> possibleNumbers = solverState.possibleNumbers(0, 0);
        assertThat(possibleNumbers)
                .isEqualTo(Sets.newLinkedHashSet( 5));
    }
}
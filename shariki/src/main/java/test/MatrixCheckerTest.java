package test;


import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;

import dagrada.marco.shariki.MatrixChecker;

/**
 * Created by Marco on 23/07/2015.
 */


public class MatrixCheckerTest {


        int[][] start={
            {1,1,2,1,2},
            {1,2,2,1,2},
            {2,1,1,2,1},
            {1,1,2,1,2},
            {1,2,1,1,2}
        };

    @Test
    private void testMatrixChecker() {

        assertEquals(start, start);
    }




}

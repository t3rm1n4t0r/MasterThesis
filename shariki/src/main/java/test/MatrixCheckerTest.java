package test;


import android.test.InstrumentationTestCase;
import java.util.Arrays;
import dagrada.marco.shariki.MatrixChecker;

/**
 * Created by Marco on 23/07/2015.
 */


public class MatrixCheckerTest extends InstrumentationTestCase {

/*
    int[][] start={
        {1,1,2,1,2},
        {1,2,2,1,2},
        {2,1,1,2,1},
        {1,1,2,1,2},
        {1,2,1,1,2}
    };
*/





    public void testCorrectMatrixRow() throws Exception{

        int[][] start={
                {1,1,1,2,2},
                {1,2,2,1,2},
                {2,1,1,2,1},
                {1,1,2,1,2},
                {1,2,1,1,2}
        };

        int[][] finalm={
                {0,0,0,2,2},
                {1,2,2,1,2},
                {2,1,1,2,1},
                {1,1,2,1,2},
                {1,2,1,1,2}
        };

        MatrixChecker.CheckForSegments(start, 3);

        assertTrue(Arrays.equals(start[0], finalm[0]));
        assertTrue(Arrays.equals(start[1], finalm[1]));
        assertTrue(Arrays.equals(start[2], finalm[2]));
        assertTrue(Arrays.equals(start[3], finalm[3]));
        assertTrue(Arrays.equals(start[4], finalm[4]));


    }

    public void testCorrectMatrixColumn() throws Exception{

        int[][] start={
                {1,1,2,1,2},
                {1,2,2,1,2},
                {1,2,1,2,1},
                {1,1,2,1,2},
                {1,2,1,1,2}
        };

        int[][] finalm={
                {0,1,2,1,2},
                {0,2,2,1,2},
                {0,2,1,2,1},
                {0,1,2,1,2},
                {0,2,1,1,2}
        };

        MatrixChecker.CheckForSegments(start, 3);

        assertTrue(Arrays.equals(start[0], finalm[0]));
        assertTrue(Arrays.equals(start[1], finalm[1]));
        assertTrue(Arrays.equals(start[2], finalm[2]));
        assertTrue(Arrays.equals(start[3], finalm[3]));
        assertTrue(Arrays.equals(start[4], finalm[4]));


    }

    public void testCorrectMatrixButBiggerMinsize() throws Exception{

        int[][] start={
                {1,1,1,2,2},
                {1,2,2,1,2},
                {2,1,1,2,1},
                {1,1,2,1,2},
                {1,2,1,1,2}
        };

        int[][] finalm={
                {1,1,1,2,2},
                {1,2,2,1,2},
                {2,1,1,2,1},
                {1,1,2,1,2},
                {1,2,1,1,2}
        };

        MatrixChecker.CheckForSegments(start, 4);

        assertTrue(Arrays.equals(start[0], finalm[0]));
        assertTrue(Arrays.equals(start[1], finalm[1]));
        assertTrue(Arrays.equals(start[2], finalm[2]));
        assertTrue(Arrays.equals(start[3], finalm[3]));
        assertTrue(Arrays.equals(start[4], finalm[4]));


    }

    public void testShorterMinsize() throws Exception{

        int[][] start={
                {1,1,1,2,2},
                {1,2,2,1,2},
                {2,1,1,2,1},
                {1,1,2,1,2},
                {1,2,1,1,2}
        };


        try {
            MatrixChecker.CheckForSegments(start, 7);
            fail("Should have thrown exception");
        }
        catch (Exception e){
            assertEquals(e.getMessage(), "Minimum segment size to check for is greater than one of matrix's dimensions");
        }




    }




}

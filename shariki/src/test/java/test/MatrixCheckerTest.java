package test;

import android.content.Context;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import dagrada.marco.shariki.MatrixChecker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by Marco on 27/07/2015.
 */

@RunWith(MockitoJUnitRunner.class)
public class MatrixCheckerTest {

    @Mock
    Context context;

    @Mock
    Log log;



    @Test
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

    @Test
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

    @Test
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

    @Test
    public void testMatrixNotSquared() throws Exception{

        int[][] start={
                {1,1,1,2,2,5},
                {1,2,2,1,2,2},
                {2,1,1,2,1,3,7},
                {1,1,2,1,2,5},
                {1,2,1,1,2,3}
        };


        try {
            MatrixChecker.CheckForSegments(start, 3);
            fail("Should have thrown exception");
        }
        catch (Exception e){
            assertEquals(e.getMessage(), "Given Matrix is not rectangular");
        }

    }
}

package test;

import android.test.AndroidTestCase;
import android.test.InstrumentationTestCase;

import java.io.IOException;
import java.util.Arrays;

import dagrada.marco.shariki.MatrixFileReader;

/**
 * Created by Marco on 27/07/2015.
 */
public class MatrixFileReaderTest extends AndroidTestCase {

    String filename = "test1.marbles";


    public void testCorrectMatrixReadFromFile(){

        int[][] expected={
                {1, -1},
                {2, -1},
                {1, -1},
                {1, -1},
                {2, -1}
        };

        int[][] result;

        try {
            result = MatrixFileReader.getMatrix(getContext(), filename);

            assertTrue(Arrays.equals(expected[0], result[0]));
            assertTrue(Arrays.equals(expected[1], result[1]));
            assertTrue(Arrays.equals(expected[2], result[2]));
            assertTrue(Arrays.equals(expected[3], result[3]));
            assertTrue(Arrays.equals(expected[4], result[4]));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

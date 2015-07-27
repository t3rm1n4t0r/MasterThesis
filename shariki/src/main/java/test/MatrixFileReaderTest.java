package test;

import android.content.Context;
import android.test.AndroidTestCase;

import java.io.FileNotFoundException;
import java.util.Arrays;

import dagrada.marco.shariki.MatrixFileReader;

/**
 * Created by Marco on 27/07/2015.
 */
public class MatrixFileReaderTest extends AndroidTestCase {



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

            result = MatrixFileReader.getMatrix(getContext().getAssets().open("test1.marbles"));

            assertTrue(Arrays.equals(expected[0], result[0]));
            assertTrue(Arrays.equals(expected[1], result[1]));
            assertTrue(Arrays.equals(expected[2], result[2]));
            assertTrue(Arrays.equals(expected[3], result[3]));
            assertTrue(Arrays.equals(expected[4], result[4]));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testWrongFilename() throws Exception {

        int[][] result;


        try {
            result = MatrixFileReader.getMatrix(getContext().getAssets().open("pippo"));
            fail("Should have thrown exception");

        } catch (FileNotFoundException e){
            assertEquals(e.getMessage(), "pippo");
        }

    }

    public void testEmptyFile() throws Exception {

        int[][] result;

        try {
            result = MatrixFileReader.getMatrix(getContext().getAssets().open("test2.marbles"));
            fail("Should have thrown exception");

        } catch (Exception e){
            assertEquals(e.getMessage(), "File format is not respected");
        }

    }
}

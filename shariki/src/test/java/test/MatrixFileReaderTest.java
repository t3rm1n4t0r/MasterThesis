package test;


import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;

import dagrada.marco.shariki.core.MatrixFileReader;

import static org.junit.Assert.assertTrue;

/**
 * Created by Marco on 27/07/2015.
 */


public class MatrixFileReaderTest {


    private static final String path = "C:\\Users\\Marco\\AndroidStudioProjects\\MasterThesis\\shariki\\src\\main\\assets\\";

    @Test
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

            result = MatrixFileReader.getMatrix(new FileInputStream(path+"test1.txt"));

            assertTrue(Arrays.equals(expected[0], result[0]));
            assertTrue(Arrays.equals(expected[1], result[1]));
            assertTrue(Arrays.equals(expected[2], result[2]));
            assertTrue(Arrays.equals(expected[3], result[3]));
            assertTrue(Arrays.equals(expected[4], result[4]));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Test(expected = FileNotFoundException.class)
    public void testWrongFilename() throws Exception {

        int[][] result;

            result = MatrixFileReader.getMatrix(new FileInputStream(path+"pippo"));



    }

    @Test(expected = Exception.class)
    public void testFileFormatNotRespected() throws Exception {

        int[][] result;
        result = MatrixFileReader.getMatrix(new FileInputStream(path + "test2.txt"));
    }

    @Test(expected = Exception.class)
    public void testZeroValuesInTheFile() throws Exception{

        int[][] result;
        result = MatrixFileReader.getMatrix(new FileInputStream(path + "test3.txt"));
    }

}

package dagrada.marco.shariki;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Marco on 23/07/2015.
 */


public class MatrixFileReader {

    private static final String delimiter = ";";


    public static int[][] getMatrix(InputStream stream) throws Exception {

        BufferedReader br = new BufferedReader((new InputStreamReader(stream, "UTF-8")));
        String buffer;
        String[] current;
        int[][] marbles = null;

        //Reading the matrix from file

        if((buffer = br.readLine()) != null) {
            current = buffer.split(delimiter);
            if(current.length !=2){
                throw new Exception("File format is not respected");
            }

            marbles = new int[Integer.parseInt(current[0])][Integer.parseInt(current[1])];

            int count = 0;
            while ((buffer = br.readLine()) != null) {
                current = buffer.split(delimiter);

                for (int i=0;i<current.length;i++){
                    marbles[count][i] = Integer.parseInt(current[i]);

                }
                count++;
            }
        }

        br.close();

        //Checking if the matrix respects the correct syntax
        for (int i=0; i< marbles.length; i++){
            if (marbles[i][marbles[i].length-1] != -1){
                //System.out.println(marbles[i][marbles[i].length-1]);
                throw new Exception("Input data do not respect the correct syntax");
            }
            for (int j=0; j<marbles[i].length; j++){
                if (marbles[i][j] == 0){
                    throw new Exception("Input data contains 0 values");
                }
            }
        }

        return marbles;
    }
}

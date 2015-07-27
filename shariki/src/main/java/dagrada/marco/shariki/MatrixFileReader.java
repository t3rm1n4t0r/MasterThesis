package dagrada.marco.shariki;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by Marco on 23/07/2015.
 */


public class MatrixFileReader {

    private static final String delimiter = " ";


    public static int[][] getMatrix(Context context, String filename) throws Exception {

        File file = new File(filename);
        BufferedReader br = new BufferedReader((new InputStreamReader(context.getAssets().open(filename), "UTF-8")));
        String buffer;
        int[][] marbles = null;
        StringTokenizer tk;

        //Reading the matrix from file

        if((buffer = br.readLine()) != null) {
            tk = new StringTokenizer(buffer, delimiter);
            marbles = new int[Integer.parseInt(tk.nextToken())][Integer.parseInt(tk.nextToken())];

            int count = 0;
            while ((buffer = br.readLine()) != null) {
                tk = new StringTokenizer(buffer, delimiter);
                for (int i=0;i<tk.countTokens();i++){
                    marbles[count][i] = Integer.parseInt(tk.nextToken());
                }
                count++;
            }
        }
        br.close();

        //Checking if the matrix respects the correct syntax
        for (int i=0; i< marbles.length; i++){
            if (marbles[i][marbles[i].length] != -1){
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

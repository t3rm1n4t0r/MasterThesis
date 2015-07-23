package dagrada.marco.shariki;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * Created by Marco on 23/07/2015.
 */


public class MatrixFileReader {

    private static final String delimiter = " ";


    public static int[][] readFromFile(String path) throws IOException {

        File file = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String buffer;
        int[][] marbles = null;
        StringTokenizer tk;

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
        return marbles;
    }
}

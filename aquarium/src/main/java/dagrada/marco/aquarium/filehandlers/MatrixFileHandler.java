package dagrada.marco.aquarium.filehandlers;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import dagrada.marco.aquarium.FileHandler;

/**
 * Created by Marco on 06/10/2015.
 */
public class MatrixFileHandler implements FileHandler{

    String filename;
    Context context;

    public MatrixFileHandler(Context context, String filename){
        this.filename = filename;
        this.context = context;
    }


    @Override
    public Object readFromFile() throws IOException {

        BufferedReader br = new BufferedReader((new InputStreamReader(context.openFileInput(filename))));
        String buffer;
        String[] current;
        int[][] matrix = new int[5][5];

        //Reading the matrix from file

        if((buffer = br.readLine()) != null) {
            current = buffer.split(" ");

            matrix = new int[Integer.parseInt(current[0])][Integer.parseInt(current[1])];

            int count = 0;
            while ((buffer = br.readLine()) != null) {
                current = buffer.split(" ");

                for (int i=0;i<current.length;i++){
                    matrix[count][i] = Integer.parseInt(current[i]);

                }
                count++;
            }
        }

        br.close();

        return matrix;
    }

    @Override
    public void writeToFile(Object obj) throws IOException {

        int[][] matrix = (int[][])obj;

        FileOutputStream fos = context.openFileOutput(this.filename, Context.MODE_PRIVATE);
        OutputStreamWriter sw = new OutputStreamWriter(fos);
        BufferedWriter bw = new BufferedWriter(sw);

        bw.write(matrix.length +" "+matrix[0].length+"\n");


        for (int i=0; i<matrix.length; i++){
            for (int j=0; j<matrix[0].length; j++){
                bw.write(matrix[i][j]+" ");
            }
            bw.write("\n");
        }

        bw.close();


    }
}

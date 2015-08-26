package dagrada.marco.shariki;

import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Marco on 23/07/2015.
 */
public class MatrixChecker {

    private static final int BLOWN_MARBLE = 0;
    private static final int ENDGAME_MARBLE = -1;


    public static boolean CheckForSegments(int[][] matrix, int minsize) throws Exception {

        boolean found = false;
        if(minsize > matrix.length || minsize >matrix[0].length){
            //Log.e("ERROR", "Minimum segment size to check for is greater than one of matrix's dimensions");
            throw new Exception("Minimum segment size to check for is greater than one of matrix's dimensions");
        }
        else if(isRectangular(matrix) == false){
            //Log.e("ERROR", "Given Matrix is not rectangular");
            throw new Exception("Given Matrix is not rectangular");
        }
        else {

            int last;
            int current;
            int count=0;
            for (int i=0; i<matrix.length;i++)
            {

                count = 0;
                last = matrix[i][0];
                for (int j=0;j<matrix[i].length; j++){
                    current = matrix[i][j];
                    if (current != last){

                        if(count >= minsize){
                            for (int k=j-count; k<j; k++){
                                matrix[i][k] =BLOWN_MARBLE;
                            }
                            found = true;
                        }
                        last = current;
                        count=1;
                    }
                    else{
                        count++;
                    }

                }

                if(count >= minsize){
                    for (int k=matrix[i].length-count; k<matrix[i].length; k++){
                        matrix[i][k] =BLOWN_MARBLE;
                    }
                    found = true;
                }

            }

            count=0;

            for (int i=0; i<matrix.length;i++)
            {

                count = 0;
                last = matrix[0][i];
                for (int j=0;j<matrix[i].length; j++){
                    current = matrix[j][i];
                    if (current != last){

                        if(count >= minsize){
                            for (int k=j-count; k<j; k++){
                                matrix[k][i] =BLOWN_MARBLE;
                            }
                            found = true;
                        }
                        last = current;
                        count=1;
                    }
                    else{
                        count++;
                    }

                }

                if(count >= minsize){
                    for (int k=matrix[i].length-count; k<matrix[i].length; k++){
                        matrix[k][i] =BLOWN_MARBLE;
                    }
                    found = true;
                }

            }



        }
        return found;
    }

    public static boolean CheckForEndGame(int[][] matrix, int maxWidth, int maxHeight) throws Exception {

        boolean found = false;
        if(isRectangular(matrix) == false){
            //Log.e("ERROR", "Given Matrix is not rectangular");
            throw new Exception("Given Matrix is not rectangular");
        }
        else if(maxHeight>matrix.length || maxWidth > matrix[0].length){
            throw new Exception("You are checking for endgame marbles out of the matrix !");
        }
        else {

            for (int i=0; i<maxHeight;i++)
            {
                for (int j=0;j<maxWidth; j++){

                    if(matrix[i][j] == ENDGAME_MARBLE)
                        return true;

                }

            }

        }
        return found;
    }

    //might be useful
    private static boolean isRectangular(int[][] matrix){
        boolean result = true;
        int current = matrix[0].length;
        for (int i=1; i<matrix.length;i++){
            if (current != matrix[i].length )
                result = false;
        }
        return result;
    }
}

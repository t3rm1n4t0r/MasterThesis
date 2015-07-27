package dagrada.marco.shariki;

import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Marco on 23/07/2015.
 */
public class MatrixChecker {

    public static void CheckForSegments(int[][] matrix, int minsize) throws Exception {
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
                if(count >= minsize){
                    for (int k=matrix[i-1].length-count; k<matrix[i-1].length; k++){
                        matrix[i-1][k] =0;
                    }
                }
                count = 0;
                last = matrix[i][0];
                for (int j=0;j<matrix[i].length; j++){
                    current = matrix[i][j];
                    if (current != last){

                        if(count >= minsize){
                            for (int k=j-count; k<j; k++){
                                matrix[i][k] =0;
                            }
                        }
                        last = current;
                        count=1;
                    }
                    else{
                        count++;
                    }

                }

            }

            count=0;

            for (int i=0; i<matrix.length;i++)
            {
                if(count >= minsize){
                    for (int k=matrix[i-1].length-count; k<matrix[i-1].length; k++){
                        matrix[k][i-1] =0;
                    }
                }
                count = 0;
                last = matrix[0][i];
                for (int j=0;j<matrix[i].length; j++){
                    current = matrix[j][i];
                    if (current != last){

                        if(count >= minsize){
                            for (int k=j-count; k<j; k++){
                                matrix[k][i] =0;
                            }
                        }
                        last = current;
                        count=1;
                    }
                    else{
                        count++;
                    }

                }

            }



        }
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

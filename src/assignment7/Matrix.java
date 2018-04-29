package assignment7;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Matrix {
    public void createMatrix(ConcurrentHashMap<String,ArrayList<String>> chashMap, Map<Integer,LinkedList<String>> returnmap){
        Iterator<Map.Entry<String,ArrayList<String>>> it= chashMap.entrySet().iterator();
        Map.Entry<String,ArrayList<String>> pair = it.next();

        Map.Entry<Integer,LinkedList<String>> returnmappair = returnmap.entrySet().iterator().next();

        LinkedList<String>filenames=returnmappair.getValue();
        LinkedList<String>filenames2=returnmappair.getValue();

        int A=0; int B=0;
        String A1;
        String A2;
        int numfiles = returnmappair.getKey();
        /*set up matrix of Type Object*/
        Object[][] matrix = new Object[numfiles+1][numfiles+1];// a,b,c...x a,b,c...
        for(int i=1;i<=numfiles;i++){
            for(int j=1;j<=numfiles;j++){
                matrix[i][j]=0;
            }
        }
        for(int i=1;i<=numfiles;i++){
            int j=0;
            matrix[i][j]=filenames.get(i-1);
        }
        for(int j=1;j<=numfiles;j++){
            int i=0;
            matrix[i][j]=filenames.get(j-1);
        }

        filenames=returnmappair.getValue();
        /*populate matrix with num of collisions of phrases
        for each file name
         */
        for(int i=1;i<=numfiles;i++){
            A1 = (String)matrix[i][0];
            for(int j=1; j<=numfiles;j++){
                if(j<=i){continue;}// lower triangle matrix
                else{
                    while(it.hasNext()){
                        ArrayList<String> values = pair.getValue();//hashmap parsing
                        if(values.contains(A1)){
                            for(String val: values) {
                                for (int j2 = j; j2 <= numfiles; j2++) {//books them for multiple hits of phrase in same value set
                                    if (matrix[0][j2].equals(val)){
                                        int temp = (int)matrix[i][j2];
                                        temp=temp+1;
                                        matrix[i][j2]=temp;
                                    }
                                }
                            }
                            //now need to put other files in spot in matrix with their A1xfile spots
                        }
                        pair=it.next();
                    }
                    it= chashMap.entrySet().iterator();//reset iterator to first elem of hashmap
                }


            }
            //filenames.removeFirst();
        }
        /*print matrix*/
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }

    }
}

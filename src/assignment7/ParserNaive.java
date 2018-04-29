package assignment7;

import java.io.File;
import java.util.Scanner;

public class ParserNaive {
    private int index=0;
    private String line ="";
    private String word = null;
    public void parse(String dir, int numperline){
        File directory = new File(dir);
        File[] files = directory.listFiles();

        for(File f: files){
            if(f.isFile()){
                System.out.println(f.getName());
                try {
                    Scanner scan = new Scanner(f);
                    while(scan.hasNext()) {
                        if (index <= numperline) {
                            word = scan.next().replaceAll("[^\\x41-\\x5A\\x61-\\x7A]", "").toUpperCase();
                            line += word;
                            //System.out.println(word);
                            index++;
                        }
                        else if (index > numperline) {
                            index = 0;
                            System.out.println(line);
                            line = "";
                        }
                    }
                    if(!scan.hasNext()){
                        System.out.println(line);
                        line="";
                        index=0;
                    }
                    System.out.println("##### new file ####");
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }


    }

}

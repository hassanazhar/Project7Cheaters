package assignment7;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class ParserSet {
    public void parse(String dir, int numperphrase, HashSet<ArrayList<String>> hashset) {
        File directory = new File(dir);
        File[] files = directory.listFiles();
        String word = "";
        String key;
        String phrase = "";
        for (File f : files) {
            if (f.isFile()) {
                try {
                    Scanner scan = new Scanner(f);
                    while (scan.hasNext()) {
                        //test forloop
                        for (int i = 0; i < numperphrase; i++) {
                            if (scan.hasNext()) {
                                word = scan.next().replaceAll("[^\\x41-\\x5A\\x61-\\x7A]", "").toUpperCase();
                                phrase += word;
                            } else {
                                break;
                            }
                        }

                        if(!hashset.contains(phrase)){
                            ArrayList<String> arr = new ArrayList<>();
                            arr.add(f.getName());
                            hashset.add(arr);
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }
}

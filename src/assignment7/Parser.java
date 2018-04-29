package assignment7;

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/*** This Parser creates sets of phrases from each word of a file until file.len-n
 *
 */
public class Parser {
    public Map parse(String dir, int numperphrase, ConcurrentHashMap<String, ArrayList<String>> chashmap) {
        File directory = new File(dir);
        File[] files = directory.listFiles();
        String phrase ="";
        Deque<String> newdeq = new LinkedList<>();
        int numfiles=0;
        LinkedList<String> allfiles = new LinkedList<>();
        Map<Integer,LinkedList<String>> returnmap = new HashMap<>();

        for (File f : files) {
            if (f.isFile()) {
                try {
                    Scanner scan = new Scanner(f);
                    while (scan.hasNext()) {
                        newdeq.add(scan.next().replaceAll("[^\\x41-\\x5A\\x61-\\x7A]", "").toUpperCase());
                        if (newdeq.size() == numperphrase) {
                            StringBuilder str = new StringBuilder();
                            for (String s : newdeq) {
                                str.append(s);
                            }
                            phrase = str.toString();
                            System.out.println(phrase);
                            if (!chashmap.containsKey(phrase)) {
                                ArrayList<String> arr = new ArrayList<>();
                                arr.add(f.getName());// - f1-f2-f3... for phrase1
                                chashmap.put(phrase, arr);
                            } else if (chashmap.containsKey(phrase)) {
                                chashmap.get(phrase).add(f.getName());// appends f2 etc for that phrase.
                            }
                            phrase = "";
                        } /*below else handles >1 phrases per file*/
                        else if (newdeq.size() > numperphrase) {
                            newdeq.removeFirst();
                            if (newdeq.size() == numperphrase) {
                                StringBuilder str = new StringBuilder();
                                for (String s : newdeq) {
                                    str.append(s);
                                }
                                phrase = str.toString();
                                System.out.println(phrase);
                                if (!chashmap.containsKey(phrase)) {
                                    ArrayList<String> arr = new ArrayList<>();
                                    arr.add(f.getName());// - f1-f2-f3... for phrase1
                                    chashmap.put(phrase, arr);
                                }
                                else if (chashmap.containsKey(phrase)) {
                                    chashmap.get(phrase).add(f.getName());// appends f2 etc for that phrase.
                                }
                                phrase = "";
                            }
                        }
                    }/*adds stuff for files w less than 5 words*/
                    if(newdeq.size()<numperphrase&& newdeq.size()>0){
                        StringBuilder str = new StringBuilder();
                        for (String s : newdeq) {
                            str.append(s);
                        }
                        phrase = str.toString();
                        System.out.println(phrase);
                        if (!chashmap.containsKey(phrase)) {
                            ArrayList<String> arr = new ArrayList<>();
                            arr.add(f.getName());// - f1-f2-f3... for phrase1
                            chashmap.put(phrase, arr);
                        } /*not accounting for false positive in same file*/
                        else if (chashmap.containsKey(phrase)) {
                            chashmap.get(phrase).add(f.getName());// appends f2 etc for that phrase.
                        }
                    }
                    phrase = "";
                    System.out.println("NEW FILE");
                    newdeq.clear();
                    }
                    catch(Exception e){
                    e.printStackTrace();
                }
            }
            numfiles++;
            allfiles.add(f.getName());
        }
        returnmap.put(numfiles,allfiles);
        System.out.println(chashmap.size());
        //System.out.println(chashmap.toString());
        return(returnmap);
    }
}

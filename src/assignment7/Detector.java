package assignment7;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/***
 * Detector Class. Finds all mappings chashmap
 * Input: chashmap, returnmap
 * Output: Map with plagiarism
 */
public class Detector {
    public Map detect(ConcurrentHashMap<String,ArrayList<String>> chashMap, Map<Integer,LinkedList<String>> returnmap){
        /*file num and names 1 entry long*/
        Map.Entry<Integer,LinkedList<String>> returnmappair = returnmap.entrySet().iterator().next();
        LinkedList<String> filenames=returnmappair.getValue();
        /*return new map*/
        Map<String,Map<String,Integer>> maptoprint = new HashMap<>(); //f1.txt->f4.txt-10// ->f7.txt-20
        /*Add all checked files to not double count*/
        ArrayList<String> donestrings = new ArrayList<>();

        for (String outfname: filenames){
            if (!maptoprint.containsKey(outfname)) {
                    //file 1. get all
                    //file 2. ignore file 1, get rest
                    //file 3. ignore file 1,2, get rest
                    //create new entry in map. first check if other entries exist and ignore those
                    Map<String, Integer> innermap = new HashMap<>();
                    maptoprint.put(outfname, innermap);

                    Iterator<Map.Entry<String, ArrayList<String>>> it = chashMap.entrySet().iterator();//phrase and files
                    //Map.Entry<String, ArrayList<String>> pair = it.next();

                    while (it.hasNext()) {
                        Map.Entry<String, ArrayList<String>> pair = it.next();
                        ArrayList<String> values = pair.getValue();
                        if (values.contains(outfname)) {
                            for (String infname : values) {
                                if(!donestrings.contains(infname)) {//don't overcount
                                    if (!infname.equals(outfname) && !maptoprint.get(outfname).containsKey(infname)) {
                                        int newint = 1;
                                        maptoprint.get(outfname).put(infname, newint);//make sure this doesn't override
                                    } else if (!infname.equals(outfname) && maptoprint.get(outfname).containsKey(infname)) {
                                        maptoprint.get(outfname).put(infname, maptoprint.get(outfname).get(infname) + 1);
                                    }
                                }
                            }
                        }
                    }
                }
            donestrings.add(outfname);
        }
        Iterator <Map.Entry<String,Map<String,Integer>>> it = maptoprint.entrySet().iterator();
        Map.Entry<String,Map<String,Integer>> pair= it.next();
        /*while(it.hasNext()){
            System.out.println(pair);
            pair=it.next();

        }*/
        return (maptoprint);
    }
}

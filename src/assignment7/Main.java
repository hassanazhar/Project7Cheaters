package assignment7;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * We are going to utilize HashTable (thread safe) in this implementation of Cheaters.
 * We are going to add LinkedLists off each entry in table with File Name
 * For each entry, compute and add total num of similarities between files to a global matrix
 */
public class Main extends Application{
    final static String austria = "Austria";
    final static String brazil = "Brazil";
    final static String france = "France";
    final static String italy = "Italy";
    final static String usa = "USA";
    static Map<String,Map<String,Integer>> mapdisplay;
    static int numhits=200;

    public static void main(String[] args)  {
        HashSet<ArrayList<String>> hashset = new HashSet<>();//2nd try
        ConcurrentHashMap<String,ArrayList<String>> chashMap = new ConcurrentHashMap<>(); //1st try
        Map<Integer,LinkedList<String>> returnmap;
        //Map<String,Map<String,Integer>> mapdisplay;

        Parser parser = new Parser();
        Matrix matrix = new Matrix();
        Detector detector=new Detector();

        String dirname = "./alltextfiles";
        String dirname1 = "./testset";
        String dirname2 = "./med_doc_set";
        String dirname3 = "./big_doc_set";

        /* input scanner*/
        int numperline=0;
        System.out.println("Please enter dirname, phrase count and num hits as follows './dirname, int phrasenum, int numhits': ");
        Scanner scanner=new Scanner(System.in);
        String dir = scanner.next();
        numperline=scanner.nextInt();
        numhits=scanner.nextInt();


        returnmap= parser.parse(dir,numperline,chashMap);
        //matrix.createMatrix(chashMap,returnmap);
        mapdisplay = detector.detect(chashMap,returnmap);

        System.out.println(returnmap);

        /*Graphing*/
        Application.launch(args);

    }
    @Override
    public void start(Stage stage) {
        stage.setTitle("Bar Chart Sample");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc =
                new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle("Plagiarism Report");
        xAxis.setLabel("File Names");
        yAxis.setLabel("Values");
        Iterator <Map.Entry<String,Map<String,Integer>>> it = mapdisplay.entrySet().iterator();
        //Map.Entry<String,Map<String,Integer>> pair = it.next();

        ArrayList<CustomGraphNode> nodestomap = new ArrayList<>();
        while (it.hasNext()){
            Map.Entry<String,Map<String,Integer>> pair = it.next();
            Map<String,Integer> vals = pair.getValue();// get inner map for pair
            Iterator <Map.Entry<String,Integer>> it2 = vals.entrySet().iterator();//inner map entries iter
            //Map.Entry<String,Integer> pairinner = it2.next(); //each inner map entry
            while(it2.hasNext()){
                Map.Entry<String,Integer> pairinner = it2.next(); //each inner map entry
                //gets all ints in inner map
                int tempnum = pairinner.getValue();
                if(tempnum>=numhits){
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(pair.getKey()+"->"+ pairinner.getKey());
                    String tempstr = stringBuilder.toString();
                    CustomGraphNode tempnode = new CustomGraphNode(tempstr,tempnum);
                    nodestomap.add(tempnode);
                }
                //pairinner=it2.next();// causes it2.hasNext() to return!!!
            }
            //pair= it.next();
        }
        /*Graph all nodes*/
        XYChart.Series []charts = new XYChart.Series[nodestomap.size()];
        int j=0;
        for (CustomGraphNode i : nodestomap){
            XYChart.Series ser = new XYChart.Series();
            ser.setName(i.getbarname());
            ser.getData().add(new XYChart.Data(i.getbarname(),i.getheight()));
            charts[j]=ser;
            j=j+1;
            System.out.println("Plagiarism Detected: "+i.getbarname()+ " num of copied lines: "+i.getheight());

        }

        Scene scene  = new Scene(bc,800,600);
        for(int i=0; i<charts.length;i++){
            bc.getData().add(charts[i]);
        }
        //bc.getData().addAll(series1);
        stage.setScene(scene);
        stage.show();


    }


	// write your code here

}

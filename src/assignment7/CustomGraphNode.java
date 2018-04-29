package assignment7;

public class CustomGraphNode {
    private int height;
    private String barname;
    public int getheight(){
        return height;
    }
    public String getbarname(){
        return barname;
    }

    public CustomGraphNode(String in, int num){
        barname=in;
        height=num;

    }
}

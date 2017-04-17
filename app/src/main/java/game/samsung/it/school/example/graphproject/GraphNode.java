package game.samsung.it.school.example.graphproject;

import java.util.LinkedList;


public class GraphNode {
    float x;
    float y;
    float an;
    int[] list;


    public GraphNode(int number, int vertix) {
        list = new int[vertix + 1];
        for (int i = 1; i <= vertix; i++) {
            list[i] = -1;
        }
        x = 0;
        y = 0;
        an = 0;
    }
}

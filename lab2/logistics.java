import org.jacop.constraints.*;
import org.jacop.core.*;
import org.jacop.search.*;


public class Logistics {

store store;


int graphsize = 6;
int start = 1;
int n_dest = 1;
int[] dest = {6};
int n_edges = 7;
int[] from = {1,1,2,2,3,4,4};
int[] to =  {2,3,3,4,5,5,6};
int[] cost =  {4,2,5,10,3,4,11};



public void model(){
    
ArrayList<IntVar> vars = new ArrayList<IntVar>();

store = new store();

    //Variable for each node in the graph with its value meaning the next node to go to
	IntVar x1 = new IntVar(store, "1", nextNodes(1));
	IntVar x2 = new IntVar(store, "2", nextNodes(2));
	IntVar x3 = new IntVar(store, "3", nextNodes(3));
	IntVar x4 = new IntVar(store, "4", nextNodes(4));
	IntVar x5 = new IntVar(store, "5", nextNodes(5));
	IntVar x6 = new IntVar(store, "6", nextNodes(6));

};

private int[] nextNodes(int a){
    int[] nextNodes;
    for(int i = 0; i < from.length; i++){
        if(from[i] == a){
            nextNodes[nextNodes.length] = to[i];
        }
    }

};

//Returns a set of all ways to go from point a to point b 
public set ways(int a, int b, set c){


};


public static main(String args[]){

};




}; 
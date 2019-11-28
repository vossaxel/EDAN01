import org.jacop.constraints.*;
import org.jacop.core.*;
import org.jacop.search.*;


public class Logistics {

Store store;


int graph_size = 6;
int start = 1;
int n_dests = 2;
int[] dest = {5,6};
int n_edges = 9;
int[] from = {1,1,1,2,2,3,3,3,4};
int[] to = {2,3,4,3,5,4,5,6,6};
int[] cost = {6,1,5,5,3,5,6,4,2};



public void model(){

store = new Store();




IntVar[][] verts = new IntVar[n_dests][graph_size];
IntVar[][] costs = new IntVar[n_dests+1][graph_size];
IntVar sum = new IntVar(store, "sum", 1, 1000);
int[][][] weights = new int[n_dests][graph_size][graph_size];


for(int b = 0; b < n_dests; b++){
for(int n = 0; n < graph_size; n++){
    for(int k = 0; k < graph_size; k++){
    weights[b][n][k] = 10000;
        if( n == k){
            weights[b][n][k] = 0;
        };
    };
};

for(int i = 0 ; i < n_edges ; i++){
   weights[b][from[i]-1][to[i] - 1] = cost[i];
   weights[b][to[i]-1][from[i]-1] = cost[i];
};

weights[b][start-1][start-1] = 10000;
weights[b][dest[b]-1][dest[b]-1] = 10000;
weights[b][dest[b]-1][start-1] = 0;
};



for(int k = 0; k < n_dests; k++){
    for(int i = 0; i < 6; i++){
    verts[k][i] = new IntVar(store, "v"+(i+1), 1, 6);
    costs[k][i] = new IntVar(store, "c"+(i+1), 0, 10000);
    store.impose(new Element(verts[k][i], weights[k][i], costs[k][i]));
    };
};

for(int i = 0; i < n_dests; i++){
store.impose(new Subcircuit(verts[i]));
};

for(int i = 0; i < graph_size; i++){
    costs[2][i] = new IntVar(store, "c"+(i+1), -10000, 10000);
    store.impose(new IfThenElse(new XeqY(verts[0][i], verts[1][i]) , new XmulCeqZ(costs[0][i], -1, costs[2][i]), new XeqC(costs[2][i], 0)));
};
store.impose(new XeqC(verts[0][dest[0]-1], 1));
store.impose(new XeqC(verts[1][dest[1]-1], 1));


IntVar[] costsum = new IntVar[graph_size*(n_dests+1)];
IntVar[] vertsearch = new IntVar[graph_size*n_dests];

for(int i=0; i < n_dests+1; i++){
    for(int n=0; n < graph_size; n++){
        costsum[i*graph_size + n] = costs[i][n];  
    };
};


for(int i=0; i < n_dests; i++){
    for(int n=0; n < graph_size; n++){
        vertsearch[i*graph_size + n] = verts[i][n];   
    };
};

store.impose(new SumInt(costsum, "==", sum));


Search<IntVar> search = new DepthFirstSearch<IntVar>();
SelectChoicePoint<IntVar> select = new SimpleSelect<IntVar>(vertsearch, new SmallestDomain<IntVar>(), new IndomainMin<IntVar>());
search.setSolutionListener(new PrintOutListener<IntVar>());

boolean result = search.labeling(store, select, sum);
};

public static void main(String args[]){
Logistics l = new Logistics();
l.model();
}


};
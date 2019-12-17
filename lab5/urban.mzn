int : n;
int : n_commercial;
int : n_residential;
array[1..n] of int : point_distribution;

var int: score;
array[1..n][1..n] of var int 0..1: grid;
array[1..2][1..n] of var int: residents;
array[1..2][1..n] of var int: cost;

constraint sum(residents[1], n_residential);
constraint sum(residents[2], n_residential);

constraint forall(j in 1..n)(
  residents[1] = count(row(grid, i), 1)
  residents[2] = count(col(grid, i), 1)
);

constraint forall(i,j in cost)(
  element(residents[i][j], point_distribution, cost[i][j])
);

constraint score = sum(cost[1]) + sum(cost[2]);

solve maximize score

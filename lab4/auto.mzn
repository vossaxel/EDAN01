include "globals.mzn";
include "jacop.mzn";

int : del_add;
int : del_mul;
int : number_add;
int : number_mul;
int : n;
set of int : last;
set of int : add;
set of int : mul;
array[1..n] of set of int : dependencies;
int : d_max = if del_mul > del_add then del_mul else del_add endif;
int : r_max = if number_mul > number_add then number_mul else number_add endif;

array[1..n] of var 0..n*d_max : t;
array[1..n] of var 0..d_max : d;
array[1..n] of var 0..r_max : r;

array[1..length(add)] of var 0..n*2 : t_add;
array[1..length(mul)] of var 0..n*2 : t_mul;
array[1..length(add)] of var del_add..del_add : d_add;
array[1..length(mul)] of var del_mul..del_mul : d_mul;
array[1..length(add)] of var 1..number_add : r_add;
array[1..length(mul)] of var 1..number_mul : r_mul;

var int : score;

constraint forall(i in index_set(add))(t[add[i]] = t_add[i] /\ d[add[i]] = d_add[i]);
constraint forall(i in index_set(mul))(t[mul[i]] = t_mul[i] /\ d[mul[i]] = d_mul[i]);

constraint forall(i in add, j in dependencies[i])(t[i] + del_add <= t[j] /\ d[i] = del_add);
constraint forall(i in mul, j in dependencies[i])(t[i] + del_mul <= t[j] /\ d[i] = del_mul);
%constraint forall(i in add, j in dependencies[i])(t[i] + del_add <= t[j]);
%constraint forall(i in mul, j in dependencies[i])(t[i] + del_mul <= t[j]);

constraint diffn([t[i] | i in add], [r[i] | i in add], [del_add | i in add], [1 | i in add]);
constraint diffn([t[i] | i in mul], [r[i] | i in mul], [del_mul | i in mul], [1 | i in mul]);
%constraint diffn(t_add, r_add,d_add,[1 | i in add]);
%constraint diffn(t_mul, r_mul,d_mul,[1 | i in mul]);

constraint cumulative([t[i] | i in add],[del_add | i in add], [1 | i in add], number_add);
constraint cumulative([t[i] | i in mul],[del_mul | i in mul], [1 | i in mul], number_mul);
%constraint cumulative(t_add, d_add, [1 | i in add], number_add);
%constraint cumulative(t_mul, d_mul, [1 | i in mul], number_mul);

constraint forall(i in last)(t[i] + d[i] <= score);

solve :: seq_search([
         int_search(t, smallest_max, indomain_min, complete)])
         minimize score;

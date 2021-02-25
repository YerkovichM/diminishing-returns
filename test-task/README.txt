Source code: test-task/source-code
Git repo: https://github.com/YerkovichM/diminishing-returns.git

The input file you created from the Data tab: test-task/input-dir/input.csv
The same as initial data file, but without header (first line)

Text file output: test-task/output-dir/output.csv
File is csv and has columns:
"key,x,y,percentOfTotal"
Contains line per point of diminishing returns
percentOfTotal is percent of the total at the point of diminishing returns

Graphs per key: test-task/output-dir/[key]-graph.jpeg
Blue graph is original graph, green is cumulative sum
Red points are points of diminishing returns on both original and cumulative graphs

Directions on how to build and run your application:
You can run it with:
$ java -jar test-task/diminishing-returns.jar test-task/inputDir/input.csv test-task/outputDir
"test-task/inputDir/input.csv" is input file path parameter
"test-task/outputDir" is output dir parameter (graphs and result file will be there)
Or you can build it as maven project

Comments:
Bonus tasks are implemented
There are unit tests for public methods
Algorithm:
1. Process data by key
2. Sort points by x
3. Merge all duplicates by x, add their y
4. Calculate the cumulative sum by y
5. Iterate through points
6. For each calculate second derivative with finite difference method (take point as central and use second-order central difference)
7. Along the way see if derivative is positive or negative
8. Save the sign of previous derivative (ignore 0s, they are like a points on a straight line)
9. When you see negative derivative and previous one (saved) was positive you found the point of diminishing returns
10. Save the point of diminishing returns and continue





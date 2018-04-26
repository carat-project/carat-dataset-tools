# carat-dataset-tools
Holds a sample project able to open the Carat dataset.
To get access to the Carat dataset, contact carat ät cs helsinki fi.
See also: http://carat.cs.helsinki.fi/

## Build instructions

1. Install maven3.
2. `cd carat-dataset-tools; mvn clean compile assembly:single`

## Run instructions 

```
$SPARK_HOME/bin/spark-submit --class fi.helsinki.cs.nodes.carat.examples.SamplesFromJsonGz \
carat-dataset-tools-with-dependencies.jar --input /path/to/caratdata --output dummy.csv
```

## Learn more

[See the example class](src/main/scala/fi/helsinki/cs/nodes/carat/examples/SamplesFromJsonGz.scala).

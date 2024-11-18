## Benchmark for load whole data set from Mongo
```
Benchmark                           (y)  Mode  Cnt     Score    Error  Units
PerformanceMongoTest.performTest     10  avgt    5  2006.364 ±  2.304  ms/op
PerformanceMongoTest.performTest    100  avgt    5  2007.418 ±  4.291  ms/op
PerformanceMongoTest.performTest   1000  avgt    5  2020.649 ±  1.626  ms/op
PerformanceMongoTest.performTest  10000  avgt    5  2164.777 ± 30.802  ms/op
PerformanceMongoTest.performTest  29000  avgt    5  2371.385 ± 21.319  ms/op
```
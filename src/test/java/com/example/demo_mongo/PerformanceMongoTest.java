package com.example.demo_mongo;

import com.example.demo_mongo.model.Config;
import com.example.demo_mongo.model.DocumentEntity;
import com.example.demo_mongo.repository.DocumentEntityRepository;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomUtils;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Import(TestcontainersConfiguration.class)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 2)
@Measurement(iterations = 1)
public class PerformanceMongoTest {

    private final static Integer MEASUREMENT_ITERATIONS = 5;
    private final static Integer WARMUP_ITERATIONS = 3;
    public static DocumentEntityRepository repository;
    @Param({"10", "100", "1000", "10000", "29000"})
    int y;
    @Setup(Level.Trial)
    public void setup() {
        repository.deleteAll();
        for(int i=0; i < y; i++) {
            RandomStringUtils.randomAlphabetic(10);
            var config = new Config(RandomStringUtils.randomAlphabetic(10), UUID.randomUUID().toString(), RandomUtils.nextDouble(), RandomUtils.nextDouble());
            var entity = new DocumentEntity(UUID.randomUUID().toString(), RandomStringUtils.randomAlphabetic(10), List.of(RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10)), config);
            repository.save(entity);
        }
        System.out.println("Saved " + y);
    }

    @BeforeAll
    static void setup(@Autowired DocumentEntityRepository repository) {
        PerformanceMongoTest.repository = repository;
    }

    @Benchmark
    public void performTest(Blackhole blackhole) {
        var all = PerformanceMongoTest.repository.findAll();
        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(2));
        blackhole.consume(all);
    }

    @Test
    public void executeJmhRunner() throws RunnerException {
        Options opt = new OptionsBuilder()
                // set the class name regex for benchmarks to search for to the current class
                .include("\\." + this.getClass().getSimpleName() + "\\.")
                .warmupIterations(WARMUP_ITERATIONS)
                .measurementIterations(MEASUREMENT_ITERATIONS)
                .timeUnit(TimeUnit.MILLISECONDS)
                // do not use forking or the benchmark methods will not see references stored within its class
                .forks(0)
                // do not use multiple threads
                .threads(1)
                .shouldDoGC(true)
                .shouldFailOnError(true)
                .resultFormat(ResultFormatType.JSON)
                .result("src/main/resources/result.json") // set this to a valid filename if you want reports
                .shouldFailOnError(true)
                .jvmArgs("-server")
                .build();

        new Runner(opt).run();
    }

}

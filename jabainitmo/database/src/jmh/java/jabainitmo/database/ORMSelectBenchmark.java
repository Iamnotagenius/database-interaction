package jabainitmo.database;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;

import jabainitmo.database.hibernate.HibernateStreetContext;
import jabainitmo.database.jdbc.JdbcStreetContext;
import lombok.AllArgsConstructor;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(0)
public class ORMSelectBenchmark {
    @State(Scope.Benchmark)
    public static abstract class SelectBenchmark<T extends DbContext<Street>> {
        private T context;

        @Param(value = { "100", "1000" })
        private int dbSize;

        public SelectBenchmark(T context) {
            this.context = context;
        }

        @Setup
        public void populate() {
            try {
                context.deleteAll();
                for (int i = 0; i < dbSize; i++) {
                    var newStreet = new Street();
                    newStreet.setName("Benchmark av.");
                    newStreet.setPostIndex(2048);
                    context.save(newStreet);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @TearDown
        public void clear() {
            try {
                context.deleteAll();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Benchmark
        @Warmup(iterations = 3, time = 1)
        @Measurement(iterations = 3, time = 1500, timeUnit = TimeUnit.MILLISECONDS)
        public void select() {
            try {
                context.getAll();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static class Jdbc extends SelectBenchmark<JdbcStreetContext> {
        public Jdbc() {
            super(new JdbcStreetContext());
        }
    }

    public static class Hibernate extends SelectBenchmark<HibernateStreetContext> {
        public Hibernate() {
            super(new HibernateStreetContext());
        }
    }
}

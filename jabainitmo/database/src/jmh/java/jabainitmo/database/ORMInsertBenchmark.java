package jabainitmo.database;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
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
public class ORMInsertBenchmark {
    @AllArgsConstructor
    @State(Scope.Benchmark)
    public static abstract class InsertBenchmark<T extends DbContext<Street>> {
        private T context;

        @Setup(Level.Trial)
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
        public void insert() {
            var street = new Street();
            street.setName("Benchmark st.");
            street.setPostIndex(1024);
            try {
                context.save(street);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static class Jdbc extends InsertBenchmark<JdbcStreetContext> {
        public Jdbc() {
            super(new JdbcStreetContext());
        }
    }

    public static class Hibernate extends InsertBenchmark<HibernateStreetContext> {
        public Hibernate() {
            super(new HibernateStreetContext());
        }
    }
}

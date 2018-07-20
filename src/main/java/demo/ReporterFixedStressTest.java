package demo;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Arbiter;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.IntResult1;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@JCStressTest
@State
@Outcome(id = "1", expect = Expect.ACCEPTABLE)
public class ReporterFixedStressTest {

    private Fixed fixed = new Fixed();

    @Actor
    public void publisher1() {
        fixed.endReport();
    }

    @Actor
    public void publisher2() {
        fixed.endReport();
    }

    @Arbiter
    public void arbiter(IntResult1 result) {
        result.r1 = fixed.calls.get();
    }

    class Fixed {
        AtomicBoolean done = new AtomicBoolean(false);
        AtomicInteger calls = new AtomicInteger();

        void endReport() {
            if (done.compareAndSet(false, true)) {
                calls.incrementAndGet();
            }
        }
    }

}

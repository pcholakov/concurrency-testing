package demo;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Arbiter;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.IntResult1;

import java.util.concurrent.atomic.AtomicInteger;

@JCStressTest
@State
@Outcome(id = "1", expect = Expect.ACCEPTABLE)
public class ReporterBrokenStressTest {

    private Broken broken = new Broken();

    @Actor
    public void publisher1() {
        broken.endReport();
    }

    @Actor
    public void publisher2() {
        broken.endReport();
    }

    @Arbiter
    public void arbiter(IntResult1 result) {
        result.r1 = broken.calls.get();
    }

    class Broken {
        volatile boolean done = false;
        AtomicInteger calls = new AtomicInteger();

        void endReport() {
            if (done) {
                // There is a race condition between this check and the write of true below
                return;
            }

            // Expectation: code after this point is only executed once
            done = true;
            calls.incrementAndGet();
        }
    }

}

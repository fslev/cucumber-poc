package ro.cucumber.core.engineering.poller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class MethodPoller<T> {
    private Logger log = LogManager.getLogger();

    private Duration pollDurationSec = Duration.ofSeconds(30);
    private int pollIntervalMillis = 3000;

    private Supplier<T> pollMethod = null;
    private Predicate<T> pollResultPredicate = null;

    public MethodPoller<T> duration(Duration pollDurationSec, int pollIntervalMillis) {
        this.pollDurationSec = pollDurationSec;
        this.pollIntervalMillis = pollIntervalMillis;
        return this;
    }

    public MethodPoller<T> method(Supplier<T> supplier) {
        pollMethod = supplier;
        return this;
    }

    public MethodPoller<T> until(Predicate<T> predicate) {
        pollResultPredicate = predicate;
        return this;
    }

    public T poll() {
        log.info("Polling for result...");
        boolean pollSucceeded = false;
        boolean pollTimeout = false;
        T result = null;
        while (!pollSucceeded && !pollTimeout) {
            try {
                result = pollMethod.get();
                pollSucceeded = pollResultPredicate.test(result);
            } catch (Exception e) {
                pollSucceeded = false;
            }
            if (!pollSucceeded) {
                try {
                    log.debug("Poll failed, I'll take another shot after {}ms", pollIntervalMillis);
                    Thread.sleep(pollIntervalMillis);
                    pollDurationSec = pollDurationSec.minusMillis(pollIntervalMillis);
                    if (pollDurationSec.isZero() || pollDurationSec.isNegative()) {
                        pollTimeout = true;
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        log.info(!pollTimeout ? "Found correct result" : "Poll timeout");
        return result;
    }
}
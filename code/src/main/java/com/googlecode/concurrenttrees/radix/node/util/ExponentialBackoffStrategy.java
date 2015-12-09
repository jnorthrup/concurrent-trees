package com.googlecode.concurrenttrees.radix.node.util;

public class ExponentialBackoffStrategy {

    private long delayInterval;
    private long initialDelay;
    private long maxDelay;

    public ExponentialBackoffStrategy(long delayInterval, long initialDelay, long maxDelay) {
        this.delayInterval = delayInterval;
        this.initialDelay = initialDelay;
        this.maxDelay = maxDelay;
    }
    
    /**
     * Returns the delay before the next attempt.
     * 
     * @param retriesAttempted
     * @return The delay before the next attempt.
     */
    public long delayBeforeNextRetry(int retriesAttempted) {
        if (retriesAttempted < 1) {
            return initialDelay;
        }


        if (retriesAttempted > 63) {
            return maxDelay;
        }

        long multiplier = ((long)1 << (retriesAttempted - 1));
        if (multiplier > Long.MAX_VALUE / delayInterval) {
            return maxDelay;
        }

        long delay = multiplier * delayInterval;
        delay = Math.min(delay, maxDelay);
        return delay;
    }
}
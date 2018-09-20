/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.datagenerators;

public class GeneratorAdminData {
    private final String className;
    private int currentCount = 0;
    private int lastCount = 0;
    private final long maxCount;
    private final int batchBlockSize;
    private final int threadIndex;
    private long startTime;
    private long endTime;

    public GeneratorAdminData(String className, long maxCount, int batchBlockSize, int threadIndex) {
        this.className = className;
        this.maxCount = maxCount;
        this.batchBlockSize = batchBlockSize;
        this.threadIndex = threadIndex;
    }

    public int getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(int currentCount) {
        this.currentCount = currentCount;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getClassName() {
        return className;
    }

    public long getMaxCount() {
        return maxCount;
    }

    public int getBatchBlockSize() {
        return batchBlockSize;
    }

    public int getThreadIndex() {
        return threadIndex;
    }

    public int getLastCount() {
        return lastCount;
    }

    public void setLastCount(int lastCount) {
        this.lastCount = lastCount;
    }

    public boolean isFinished() {
        return currentCount >= maxCount;

    }
    @Override
    public String toString() {
        long timediff =  endTime - startTime;
        if (timediff == 0) {
            timediff = 1;
        }
        long perSec = currentCount;
        perSec = currentCount * 1000 / timediff ;
        String msg = new StringBuffer(className)
        .append("[").append(threadIndex).append("]")
        .append(" speed ")
        .append(perSec)
        .append("/sec")
        .toString();
        return msg;
    }

}

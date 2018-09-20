/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.commands;

/**
 */
public class BevraagInfo {
    private final String taskName;

    private final long timeMillis;

    private final String result;

    private final long timestamp;

    public BevraagInfo(String taskName, String result, long timeMillis) {
        this.timestamp = System.nanoTime();

        this.taskName = taskName;
        this.result = result;
        this.timeMillis = timeMillis;
    }

    /**
     * Return the name of this task.
     */
    public String getTaskName() {
        return this.taskName;
    }

    public String getResult() {
        return this.result;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    /**
     * Return the time in milliseconds this task took.
     */
    public long getTimeMillis() {
        return this.timeMillis;
    }

    /**
     * Return the time in seconds this task took.
     */
    public double getTimeSeconds() {
        return this.timeMillis / 1000.0;
    }
}

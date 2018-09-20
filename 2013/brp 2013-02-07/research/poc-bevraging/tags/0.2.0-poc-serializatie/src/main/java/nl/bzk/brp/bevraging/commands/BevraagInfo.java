/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.commands;

/**
 */
public class BevraagInfo {
    private static final double SECOND_IN_MILLIS = 1000.0;

    private final String taskName;

    private final long timeMillis;

    private final String result;

    private final long timestamp;

    /**
     *
     * @param taskName naam van de uitgevoerde taak
     * @param result resultaat string (OK of FAIL)
     * @param timeMillis duur van de taak
     */
    public BevraagInfo(final String taskName, final String result, final long timeMillis) {
        this.timestamp = System.nanoTime();

        this.taskName = taskName;
        this.result = result;
        this.timeMillis = timeMillis;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public String getResult() {
        return this.result;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public long getTimeMillis() {
        return this.timeMillis;
    }

    public double getTimeSeconds() {
        return this.timeMillis / SECOND_IN_MILLIS;
    }
}

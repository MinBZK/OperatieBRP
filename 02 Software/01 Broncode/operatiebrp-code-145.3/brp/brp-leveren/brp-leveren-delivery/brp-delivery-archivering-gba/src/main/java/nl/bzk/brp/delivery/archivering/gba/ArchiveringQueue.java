/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.archivering.gba;

/**
 * Queues voor het communiceren van archivering verzoeken.
 */
public enum ArchiveringQueue {

    /**
     * Verzoek queue.
     */
    VERZOEK("GbaArchiveringQueue");

    private final String queueNaam;

    /**
     * @param queueNaam queueNaam
     */
    ArchiveringQueue(final String queueNaam) {
        this.queueNaam = queueNaam;
    }

    /**
     * Geef de waarde van queueNaam.
     * @return queueNaam
     */
    public String getQueueNaam() {
        return queueNaam;
    }
}

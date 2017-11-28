/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.internbericht.selectie;

/**
 * Deze enum definieert alle JMS Queue namen welke gebruikt worden binnen Selectie.
 */
public enum SelectieTaakQueue {

    /**
     * SelectieTaakQueue.
     */
    SELECTIE_TAAK_QUEUE("SelectieTaakQueue"),

    /**
     * SelectieTaakQueue.
     */
    SELECTIE_TAAK_RESULTAAT_QUEUE("SelectieTaakResultaatQueue"),

    /**
     * SelectieSchrijfTaakQueue.
     */
    SELECTIE_SCHRIJF_TAAK_QUEUE("SelectieSchrijfTaakQueue"),

    /**
     * SelectieMaakSelectieResultaatTaakQueue.
     */
    SELECTIE_MAAK_RESULTAAT_QUEUE("SelectieMaakSelectieResultaatTaakQueue"),

    /**
     * SelectieMaakSelectieResultaatTaakQueue.
     */
    SELECTIE_MAAK_GEEN_RESULTAAT_NETWERK_QUEUE("SelectieMaakSelectieGeenResultaatNetwerkTaakQueue"),

    /**
     * SelectieAfnemerindicatieTaakQueue.
     */
    SELECTIE_AFNEMERINDICATIE_TAAK_QUEUE("SelectieAfnemerindicatieTaakQueue");

    private final String queueNaam;

    /**
     * @param queueNaam queueNaam
     */
    SelectieTaakQueue(final String queueNaam) {
        this.queueNaam = queueNaam;
    }

    public String getQueueNaam() {
        return queueNaam;
    }

}

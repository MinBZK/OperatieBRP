/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.afnemerindicatie;

/**
 * OnderhoudAfnemerindicatieResultaat.
 */
public final class OnderhoudAfnemerindicatieResultaat {
    private final int selectieTaakId;
    private final boolean bestand;

    /**
     * @param selectieTaakId selectieTaakId
     * @param bestand bestand
     */
    public OnderhoudAfnemerindicatieResultaat(int selectieTaakId, boolean bestand) {
        this.selectieTaakId = selectieTaakId;
        this.bestand = bestand;
    }

    /**
     * @return selectie taak id.
     */
    public int getSelectieTaakId() {
        return selectieTaakId;
    }

    /**
     * Is bestand.
     * @return the boolean
     */
    public boolean isBestand() {
        return bestand;
    }
}

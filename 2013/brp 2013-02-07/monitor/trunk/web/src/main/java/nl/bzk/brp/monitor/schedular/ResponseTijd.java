/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.monitor.schedular;

/**
 * DTO voor het opslaan van response tijden.
 *
 */
public class ResponseTijd {

    private int gemiddeldeResponseTijd;
    private int gemiddeldeResponseTijdPerTijdsEenheid;
    private int cxfGemiddeldeResponseTijd;

    /**
     * Constructor.
     *
     * @param gemiddeldeResponseTijd gemiddelde response tijd servlet
     * @param gemiddeldeResponseTijdPerTijdsEenheid gemiddelde response tijd per tijdseenheid
     * @param cxfGemiddeldeResponseTijd gemiddelde response tijd van cxf
     */
    public ResponseTijd(final int gemiddeldeResponseTijd, final int gemiddeldeResponseTijdPerTijdsEenheid,
            final int cxfGemiddeldeResponseTijd)
    {
        this.gemiddeldeResponseTijd = gemiddeldeResponseTijd;
        this.gemiddeldeResponseTijdPerTijdsEenheid = gemiddeldeResponseTijdPerTijdsEenheid;
        this.cxfGemiddeldeResponseTijd = cxfGemiddeldeResponseTijd;
    }

    public int getGemiddeldeResponseTijd() {
        return gemiddeldeResponseTijd;
    }

    public void setGemiddeldeResponseTijd(final int gemiddeldeResponseTijd) {
        this.gemiddeldeResponseTijd = gemiddeldeResponseTijd;
    }

    public int getCxfGemiddeldeResponseTijd() {
        return cxfGemiddeldeResponseTijd;
    }

    public void setCxfGemiddeldeResponseTijd(final int cxfGemiddeldeResponseTijd) {
        this.cxfGemiddeldeResponseTijd = cxfGemiddeldeResponseTijd;
    }

    public int getGemiddeldeResponseTijdPerTijdsEenheid() {
        return gemiddeldeResponseTijdPerTijdsEenheid;
    }

    public void setGemiddeldeResponseTijdPerTijdsEenheid(final int gemiddeldeResponseTijdPerTijdsEenheid) {
        this.gemiddeldeResponseTijdPerTijdsEenheid = gemiddeldeResponseTijdPerTijdsEenheid;
    }
}

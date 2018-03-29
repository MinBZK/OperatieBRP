/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.GerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.impl.AbstractOnbekendBericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Header;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;

/**
 * Bericht om aan te geven dat een bericht onbekend is.
 */
public final class OnbekendBericht extends AbstractOnbekendBericht implements Lo3Bericht {
    private static final long serialVersionUID = 1L;

    private String bronPartijCode;
    private String doelPartijCode;

    /**
     * Constructor (gebruikt in ESB).
     * @param bericht bericht inhoud
     * @param melding melding
     */
    public OnbekendBericht(final String bericht, final String melding) {
        super(bericht, melding);
    }

    /**
     * Constructor (gebruikt in processen en ESB om onverwacht bericht te wrappen).
     * @param lo3Bericht onverwacht bericht
     */
    public OnbekendBericht(final Lo3Bericht lo3Bericht) {
        super(safeFormat(lo3Bericht), "Onbekend bericht");
        setMessageId(lo3Bericht.getMessageId());
        setCorrelationId(lo3Bericht.getCorrelationId());
        setBronPartijCode(lo3Bericht.getBronPartijCode());
        setDoelPartijCode(lo3Bericht.getDoelPartijCode());
    }

    private static String safeFormat(final Lo3Bericht lo3Bericht) {
        try {
            return lo3Bericht.format();
        } catch (final BerichtInhoudException e) {
            LoggerFactory.getLogger().debug("Fout tijdens opslaan formaat", e);
            return null;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht#getBronPartijCode()
     */
    @Override
    public String getBronPartijCode() {
        return bronPartijCode;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht#setBronPartijCode(java.lang.String)
     */
    @Override
    public void setBronPartijCode(final String bronPartijCode) {
        this.bronPartijCode = bronPartijCode;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht#getDoelPartijCode()
     */
    @Override
    public String getDoelPartijCode() {
        return doelPartijCode;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht#setDoelPartijCode(java.lang.String)
     */
    @Override
    public void setDoelPartijCode(final String doelPartijCode) {
        this.doelPartijCode = doelPartijCode;
    }

    @Override
    public void setHeader(final Lo3HeaderVeld veld, final String waarde) {
        // Onbekend bericht heeft geen header
    }

    @Override
    public String getHeaderWaarde(final Lo3HeaderVeld veld) {
        return null;
    }

    @Override
    public List<String> getHeaderWaarden(final Lo3HeaderVeld veld) {
        return Collections.emptyList();
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht#getHeaderWaarde()
     */
    @Override
    public Lo3Header getHeader() {
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.bericht.model.Bericht#getGerelateerdeInformatie()
     */
    @Override
    public GerelateerdeInformatie getGerelateerdeInformatie() {
        return new GerelateerdeInformatie(null, Arrays.asList(bronPartijCode, doelPartijCode), null);
    }

    @Override
    public void parse(final String lo3Bericht) {
        // Onbekend bericht hoeft niets te parsen
    }

}

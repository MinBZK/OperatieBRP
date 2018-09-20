/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3;

import java.io.Serializable;
import java.util.Arrays;
import nl.bzk.migratiebrp.bericht.model.AbstractBericht;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.GerelateerdeInformatie;

/**
 * Abstract lo3 bericht waarin de bericht inhoud *NIET* wordt verwerkt (of aan te passen is).
 */
public abstract class AbstractUnparsedLo3Bericht extends AbstractBericht implements Lo3Bericht, Serializable {

    private static final long serialVersionUID = 1L;

    private final Lo3Header header;
    private final String berichtType;
    private final String startCyclus;

    private String inhoud = "";
    private String bronGemeente;
    private String doelGemeente;

    private String[] headers;

    /**
     * Constructor.
     *
     * @param header
     *            header velden van dit bericht
     * @param berichtType
     *            berichtnummer van dit bericht
     * @param startCyclus
     *            cyclus die dit bericht kan starten
     */
    protected AbstractUnparsedLo3Bericht(final Lo3Header header, final String berichtType, final String startCyclus) {
        this.header = header;
        this.berichtType = berichtType;
        this.startCyclus = startCyclus;
        headers = initHeaderValues(header);
    }

    private String[] initHeaderValues(final Lo3Header lo3Header) {
        final String[] headerValues = new String[lo3Header.getNumberOfHeaders()];

        final Lo3HeaderVeld[] headerVelden = lo3Header.getHeaderVelden();
        for (int i = 0; i < headerVelden.length; i++) {
            final Lo3HeaderVeld headerVeld = headerVelden[i];
            if (Lo3HeaderVeld.BERICHTNUMMER.equals(headerVeld)) {
                headerValues[i] = headerVeld.format(getBerichtType());
            } else if (Lo3HeaderVeld.BERICHT.equals(headerVeld)) {
                headerValues[i] = "";
            } else {
                headerValues[i] = headerVeld.format(null);
            }
        }

        return headerValues;
    }

    @Override
    public final String getBerichtType() {
        return berichtType;
    }

    @Override
    public final String getStartCyclus() {
        return startCyclus;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht#getBronGemeente()
     */
    @Override
    public final String getBronGemeente() {
        return bronGemeente;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht#setBronGemeente(java.lang.String)
     */
    @Override
    public final void setBronGemeente(final String bronGemeente) {
        this.bronGemeente = bronGemeente;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht#getDoelGemeente()
     */
    @Override
    public final String getDoelGemeente() {
        return doelGemeente;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht#setDoelGemeente(java.lang.String)
     */
    @Override
    public final void setDoelGemeente(final String doelGemeente) {
        this.doelGemeente = doelGemeente;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.bericht.model.Bericht#getGerelateerdeInformatie()
     */
    @Override
    public final GerelateerdeInformatie getGerelateerdeInformatie() {
        return new GerelateerdeInformatie(null, Arrays.asList(bronGemeente, doelGemeente), null);
    }

    /* ************************************************************************************************************* */
    @Override
    public final void parse(final String lo3Bericht) throws BerichtSyntaxException, BerichtInhoudException {
        headers = header.parseHeaders(lo3Bericht);
        inhoud = lo3Bericht.substring(getTotalHeaderSize());

    }

    /**
     * Geef de waarde van total header size.
     *
     * @return total header size
     */
    private int getTotalHeaderSize() {
        int totalHeaderSize = 0;
        for (final String header2 : headers) {
            totalHeaderSize += header2.length();
        }
        return totalHeaderSize;
    }

    @Override
    public final String format() {
        setHeader(Lo3HeaderVeld.BERICHTNUMMER, getBerichtType());

        return header.formatHeaders(headers) + (inhoud == null ? "" : inhoud);
    }

    @Override
    public final void setHeader(final Lo3HeaderVeld veld, final String waarde) {
        if (!Lo3HeaderVeld.BERICHT.equals(veld)) {
            headers[header.getIndexOfHeader(veld)] = veld.format(waarde);
        } else {
            headers[header.getIndexOfHeader(veld)] = waarde;
        }
    }

    @Override
    public final String getHeader(final Lo3HeaderVeld veld) {
        final int indexOfHeaderVeld = header.getIndexOfHeader(veld);
        if (indexOfHeaderVeld == -1) {
            return "";
        } else {
            return headers[indexOfHeaderVeld];
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht#getHeader()
     */
    @Override
    public final Lo3Header getHeader() {
        return header;
    }

    /**
     * Geef de waarde van inhoud.
     *
     * @return inhoud
     */
    public final String getInhoud() {
        return inhoud;
    }

}

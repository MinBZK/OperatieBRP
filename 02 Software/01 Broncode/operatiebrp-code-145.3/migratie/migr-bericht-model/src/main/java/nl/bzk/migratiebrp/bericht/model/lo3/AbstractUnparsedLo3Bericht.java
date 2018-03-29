/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
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
    private String bronPartijCode;
    private String doelPartijCode;

    private List<String> headers;

    /**
     * Constructor.
     * @param header header velden van dit bericht
     * @param berichtType berichtnummer van dit bericht
     * @param startCyclus cyclus die dit bericht kan starten
     */
    protected AbstractUnparsedLo3Bericht(final Lo3Header header, final String berichtType, final String startCyclus) {
        this.header = header;
        this.berichtType = berichtType;
        this.startCyclus = startCyclus;
        headers = initHeaderValues(header);
    }

    private List<String> initHeaderValues(final Lo3Header lo3Header) {
        return Arrays.stream(lo3Header.getHeaderVelden()).map(veld -> {
            if (Lo3HeaderVeld.BERICHTNUMMER.equals(veld)) {
                return veld.format(getBerichtType());
            } else if (Lo3HeaderVeld.BERICHT.equals(veld)) {
                return "";
            } else {
                return veld.format(null);
            }
        }).collect(Collectors.toList());
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
     * @see nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht#getBronPartijCode()
     */
    @Override
    public final String getBronPartijCode() {
        return bronPartijCode;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht#setBronPartijCode(java.lang.String)
     */
    @Override
    public final void setBronPartijCode(final String bronPartijCode) {
        this.bronPartijCode = bronPartijCode;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht#getDoelPartijCode()
     */
    @Override
    public final String getDoelPartijCode() {
        return doelPartijCode;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht#setDoelPartijCode(java.lang.String)
     */
    @Override
    public final void setDoelPartijCode(final String doelPartijCode) {
        this.doelPartijCode = doelPartijCode;
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

    /*
     * *********************************************************************************************
     * ****************
     */


    /**
     * Geef de waarde van total header size.
     * @return total header size
     */
    protected int getTotalHeaderSize() {
        int totalHeaderSize = 0;
        for (final String header2 : headers) {
            totalHeaderSize += header2.length();
        }
        return totalHeaderSize;
    }

    @Override
    public final void parse(final String lo3Bericht) throws BerichtSyntaxException, BerichtInhoudException {
        headers = Arrays.asList(header.parseHeaders(lo3Bericht));
        parseInhoud(lo3Bericht.substring(getTotalHeaderSize()));
    }

    /**
     * Parse de string representatie van de inhoud van het LO3 bericht.
     * @param inhoud De inhoud van het LO3 bericht
     * @throws BerichtSyntaxException In het geval dat het bericht syntactisch niet correct is
     * @throws BerichtInhoudException In het geval dat het bericht ongeldige inhoud heeft
     */
    public void parseInhoud(final String inhoud) throws BerichtSyntaxException, BerichtInhoudException {
        this.inhoud = inhoud;
    }

    protected String getFormattedInhoud() {
        return inhoud == null ? "" : inhoud;
    }

    @Override
    public final String format() {
        setHeader(Lo3HeaderVeld.BERICHTNUMMER, getBerichtType());

        return header.formatHeaders(headers.toArray(new String[headers.size()])) + getFormattedInhoud();
    }

    @Override
    public final void setHeader(final Lo3HeaderVeld veld, final String waarde) {
        if (!Lo3HeaderVeld.BERICHT.equals(veld)) {
            headers.set(header.getIndexOfHeader(veld), veld.format(waarde));
        } else {
            headers.set(header.getIndexOfHeader(veld), waarde);
        }
    }

    @Override
    public final String getHeaderWaarde(final Lo3HeaderVeld veld) {
        final int indexOfHeaderVeld = header.getIndexOfHeader(veld);
        if (indexOfHeaderVeld == -1) {
            return "";
        } else {
            return headers.get(indexOfHeaderVeld);
        }
    }

    @Override
    public final List<String> getHeaderWaarden(final Lo3HeaderVeld veld) {
        if (veld.getLengteType() == Lo3HeaderVeld.LengteType.VARIABEL_GEDEFINIEERDE_LENGTE) {
            String headerWaarde = getHeaderWaarde(veld);
            List<String> waarden = new ArrayList<>();
            int index = 0;
            while (index < headerWaarde.length()) {
                waarden.add(headerWaarde.substring(index, index + veld.getElementLengte()));
                index += veld.getElementLengte();
            }
            return waarden;
        } else {
            throw new IllegalArgumentException(String.format("Veld %s heeft geen variabele lengte", veld));
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht#getHeaderWaarde()
     */
    @Override
    public final Lo3Header getHeader() {
        return header;
    }

    /**
     * Geef de waarde van inhoud.
     * @return inhoud
     */
    public String getInhoud() {
        return inhoud;
    }

}

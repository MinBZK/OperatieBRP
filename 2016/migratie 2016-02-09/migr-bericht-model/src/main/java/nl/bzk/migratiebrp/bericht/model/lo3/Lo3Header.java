/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3;

import java.io.Serializable;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * LO3 Header.
 */
public final class Lo3Header implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Lo3HeaderVeld[] headerVelden;

    /**
     * Constructor.
     *
     * @param headerVelden
     *            header velden
     */
    public Lo3Header(final Lo3HeaderVeld... headerVelden) {
        this.headerVelden = headerVelden;

        if (this.headerVelden.length < 2 || this.headerVelden[0] != Lo3HeaderVeld.RANDOM_KEY || this.headerVelden[1] != Lo3HeaderVeld.BERICHTNUMMER) {
            throw new IllegalArgumentException("Header bevat verplichte elementen RANDOM_KEY en BERICHTNUMMER niet.");
        }
    }

    /**
     * Aantal header velden.
     *
     * @return aantal header velden
     */
    public int getNumberOfHeaders() {
        return headerVelden.length;
    }

    /**
     * Geef de index van een header veld.
     *
     * @param headerVeld
     *            header veld
     *
     * @return index (-1 als niet gevonden)
     */
    public int getIndexOfHeader(final Lo3HeaderVeld headerVeld) {
        for (int index = 0; index < headerVelden.length; index++) {
            if (headerVeld.equals(headerVelden[index])) {
                return index;
            }
        }
        return -1;
    }

    /**
     * Parse de headers uit een LO3 bericht.
     *
     * @param lo3Bericht
     *            lo3bericht
     * @throws BerichtSyntaxException
     *             bij syntax fouten
     * @return headers
     */
    public String[] parseHeaders(final String lo3Bericht) throws BerichtSyntaxException {
        final String[] result = new String[headerVelden.length];

        int berichtIndex = 0;
        int berichtLengteHeaderValue = 0;
        int aantalIndicator = 0;

        for (int headerIndex = 0; headerIndex < headerVelden.length; headerIndex++) {
            final Lo3HeaderVeld headerVeld = headerVelden[headerIndex];

            final String headerValue =
                    lo3Bericht.substring(
                        berichtIndex,
                        berichtIndex + (berichtLengteHeaderValue == 0 ? headerVeld.getLengte(aantalIndicator) : berichtLengteHeaderValue));
            if (Lo3HeaderVeld.LENGTE_BERICHT.equals(headerVeld)) {
                berichtLengteHeaderValue = Integer.parseInt(headerValue);
            } else if (Lo3HeaderVeld.BERICHT.equals(headerVeld)) {
                berichtLengteHeaderValue = 0;
            } else if (Lo3HeaderVeld.AANTAL.equals(headerVeld)) {
                aantalIndicator = Integer.parseInt(headerValue);
            }

            if (!Lo3HeaderVeld.AANTAL.equals(headerVeld)) {
                aantalIndicator = 0;
            }

            if (!Lo3HeaderVeld.BERICHT.equals(headerVeld) && !headerVeld.isValide(headerValue)) {
                throw new BerichtSyntaxException("Header is niet valide.");
            }
            result[headerIndex] = headerValue;
            berichtIndex = berichtIndex + headerValue.length();
        }

        return result;
    }

    /**
     * Formatteer de headers voor een LO3 bericht.
     *
     * @param headers
     *            haeders
     * @return LO3 header
     */
    public String formatHeaders(final String[] headers) {
        final StringBuilder sb = new StringBuilder(12);

        for (int headerIndex = 0; headerIndex < headerVelden.length; headerIndex++) {
            final Lo3HeaderVeld headerVeld = headerVelden[headerIndex];

            if (!Lo3HeaderVeld.BERICHT.equals(headerVeld)) {
                sb.append(headerVeld.format(headers[headerIndex]));
            } else {
                sb.append(headers[headerIndex]);
            }

        }

        return sb.toString();

    }

    /**
     * Geeft een lijst met headervelden terug.
     *
     * @return De lijst met headervelden.
     */
    public Lo3HeaderVeld[] getHeaderVelden() {
        return headerVelden.clone();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        final Lo3Header rhs = (Lo3Header) obj;
        return new EqualsBuilder().append(headerVelden, rhs.headerVelden).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(headerVelden).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("headerVelden", headerVelden).build();
    }
}

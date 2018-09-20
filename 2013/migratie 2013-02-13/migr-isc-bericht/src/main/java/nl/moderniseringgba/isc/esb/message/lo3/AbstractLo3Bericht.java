/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.lo3;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import nl.moderniseringgba.isc.esb.message.AbstractBericht;
import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.BerichtSyntaxException;
import nl.moderniseringgba.isc.esb.message.lo3.syntax.Lo3SyntaxControle;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Basis implementatie voor een LO3 bericht.
 */
public abstract class AbstractLo3Bericht extends AbstractBericht implements Lo3Bericht, Serializable {

    private static final long serialVersionUID = 1L;

    private static final Lo3SyntaxControle INHOUD_CONTROLE = new Lo3SyntaxControle();

    private final Lo3Header header;
    private String bronGemeente;
    private String doelGemeente;

    private String[] headers;

    /**
     * Constructor.
     * 
     * @param header
     *            header velden van dit bericht
     */
    protected AbstractLo3Bericht(final Lo3Header header) {
        this.header = header;
        headers = initHeaderValues(header);
    }

    private String[] initHeaderValues(final Lo3Header header) {
        final String[] headerValues = new String[header.getNumberOfHeaders()];

        final Lo3HeaderVeld[] headerVelden = header.getHeaderVelden();
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
    public abstract String getBerichtType();

    @Override
    public final String getBronGemeente() {
        return bronGemeente;
    }

    @Override
    public final void setBronGemeente(final String bronGemeente) {
        this.bronGemeente = bronGemeente;
    }

    @Override
    public final String getDoelGemeente() {
        return doelGemeente;
    }

    @Override
    public final void setDoelGemeente(final String doelGemeente) {
        this.doelGemeente = doelGemeente;
    }

    /* ************************************************************************************************************* */
    @Override
    public final void parse(final String lo3Bericht) throws BerichtSyntaxException, BerichtInhoudException {
        headers = header.parseHeaders(lo3Bericht);
        final List<Lo3CategorieWaarde> categorieen =
                Lo3Inhoud.parseInhoud(lo3Bericht.substring(getTotalHeaderSize()));
        INHOUD_CONTROLE.controleerInhoud(categorieen);
        parseInhoud(categorieen);
    }

    private int getTotalHeaderSize() {
        int totalHeaderSize = 0;
        for (int i = 0; i < headers.length; i++) {
            totalHeaderSize += headers[i].length();
        }
        return totalHeaderSize;
    }

    /**
     * Hook voor subclasses om specifieke parsing te doen.
     * 
     * @param categorieen
     *            generieke categorieen
     * @throws BerichtInhoudException
     *             bij inhoudelijke bericht fouten
     */
    protected void parseInhoud(final List<Lo3CategorieWaarde> categorieen) throws BerichtInhoudException {
        // Hook
    }

    @Override
    public final String format() {
        final List<Lo3CategorieWaarde> categorieen = formatInhoud();

        setHeader(Lo3HeaderVeld.BERICHTNUMMER, getBerichtType());

        return header.formatHeaders(headers) + Lo3Inhoud.formatInhoud(categorieen);
    }

    /**
     * Hook voor subclasses om specifieke formatting te doen.
     * 
     * @return generieke categorieen
     */
    // CHECKSTYLE:OFF - Designed voor extension
    protected List<Lo3CategorieWaarde> formatInhoud() {
        // CHECKSTYLE:ON
        return Collections.emptyList();
    }

    /* ************************************************************************************************************* */

    /**
     * Geef het eerste voorkomen van een categorie in de lijst.
     * 
     * @param categorieen
     *            lijst met categorieen
     * @param categorie
     *            te zoeken categorie
     * @return eerste voorkomen van de categorie (of null als niet gevonden)
     */
    protected final Lo3CategorieWaarde getCategorie(
            final List<Lo3CategorieWaarde> categorieen,
            final Lo3CategorieEnum categorie) {
        final List<Lo3CategorieWaarde> result = findCategorie(categorieen, categorie);

        return result.isEmpty() ? null : result.get(0);
    }

    /**
     * Geef de voorkomens van een categorie in de lijst.
     * 
     * @param categorieen
     *            lijst met categorieen
     * @param categorie
     *            te zoeken categorie
     * @return lijst met voorkomens van de categorie
     */
    protected final List<Lo3CategorieWaarde> findCategorie(
            final List<Lo3CategorieWaarde> categorieen,
            final Lo3CategorieEnum categorie) {
        final List<Lo3CategorieWaarde> result = new ArrayList<Lo3CategorieWaarde>();

        for (final Lo3CategorieWaarde cat : categorieen) {
            if (cat.getCategorie() == categorie) {
                result.add(cat);
            }
        }

        return result;
    }

    /* ************************************************************************************************************* */

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

    /* ************************************************************************************************************* */

    // CHECKSTYLE:OFF - toString/hashCode/equals mag overriden worden (werkt door het gebruik van Commons lang)

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AbstractLo3Bericht)) {
            return false;
        }
        final AbstractLo3Bericht castOther = (AbstractLo3Bericht) other;

        return new EqualsBuilder().appendSuper(super.equals(other)).append(bronGemeente, castOther.bronGemeente)
                .append(doelGemeente, castOther.doelGemeente).append(headers, castOther.headers).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(bronGemeente).append(doelGemeente)
                .append(headers).toHashCode();
    }

    @Override
    public String toString() {
        return "AbstractLo3Bericht [messageId=" + getMessageId() + ", correlationId=" + getCorrelationId()
                + ", bronGemeente=" + getBronGemeente() + ", doelGemeente=" + getDoelGemeente() + ", headers="
                + Arrays.toString(headers) + "]";
    }

    // CHECKSTYLE:ON
}

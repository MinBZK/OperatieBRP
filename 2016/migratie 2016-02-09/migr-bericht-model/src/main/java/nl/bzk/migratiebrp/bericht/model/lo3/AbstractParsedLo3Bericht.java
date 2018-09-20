/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.AbstractBericht;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.GerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.lo3.syntax.Lo3SyntaxControle;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaardeUtil;

/**
 * Basis implementatie voor een LO3 bericht.
 */
public abstract class AbstractParsedLo3Bericht extends AbstractBericht implements Lo3Bericht, Serializable {

    private static final long serialVersionUID = 1L;

    private static final Lo3SyntaxControle INHOUD_CONTROLE = new Lo3SyntaxControle();

    private final Lo3Header header;
    private final String berichtType;
    private final String startCyclus;

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
    protected AbstractParsedLo3Bericht(final Lo3Header header, final String berichtType, final String startCyclus) {
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
    public final GerelateerdeInformatie getGerelateerdeInformatie() {
        return new GerelateerdeInformatie(null, Arrays.asList(bronGemeente, doelGemeente), getGerelateerdeAnummers());
    }

    /**
     * Geeft de gerelateerde aNummers.
     *
     * @return gerelateerdeAnummers
     */
    protected abstract List<String> getGerelateerdeAnummers();

    /**
     * Geeft het anummer van de persoon.
     *
     * @param categorieen
     *            de categorien
     *
     * @return aNummer
     */
    protected final List<String> getGerelateerdeAnummer(final List<Lo3CategorieWaarde> categorieen) {
        return Arrays.asList(Lo3CategorieWaardeUtil.getElementWaarde(categorieen, Lo3CategorieEnum.PERSOON, 0, 0, Lo3ElementEnum.ANUMMER));
    }

    /* ************************************************************************************************************* */
    @Override
    public final void parse(final String lo3Bericht) throws BerichtSyntaxException, BerichtInhoudException {
        headers = header.parseHeaders(lo3Bericht);
        final List<Lo3CategorieWaarde> categorieen = Lo3Inhoud.parseInhoud(lo3Bericht.substring(getTotalHeaderSize()));
        INHOUD_CONTROLE.controleerInhoud(categorieen);
        parseInhoud(categorieen);
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
    protected List<Lo3CategorieWaarde> formatInhoud() {
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
    protected final Lo3CategorieWaarde getCategorie(final List<Lo3CategorieWaarde> categorieen, final Lo3CategorieEnum categorie) {
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
    protected final List<Lo3CategorieWaarde> findCategorie(final List<Lo3CategorieWaarde> categorieen, final Lo3CategorieEnum categorie) {
        final List<Lo3CategorieWaarde> result = new ArrayList<>();

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

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht#getHeader()
     */
    @Override
    public final Lo3Header getHeader() {
        return header;
    }

    /* ************************************************************************************************************* */
}

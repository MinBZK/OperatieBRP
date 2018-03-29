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
public abstract class AbstractParsedLo3Bericht extends AbstractUnparsedLo3Bericht implements Lo3Bericht, Serializable {

    private static final long serialVersionUID = 1L;

    private final Lo3SyntaxControle syntaxControle;

    /**
     * Constructor.
     * @param header header velden van dit bericht
     * @param berichtType berichtnummer van dit bericht
     * @param startCyclus cyclus die dit bericht kan starten
     */
    protected AbstractParsedLo3Bericht(final Lo3Header header, final Lo3SyntaxControle syntaxControle, final String berichtType, final String startCyclus) {
        super(header, berichtType, startCyclus);
        this.syntaxControle = syntaxControle;
    }

    @Override
    public final GerelateerdeInformatie getGerelateerdeInformatie() {
        return new GerelateerdeInformatie(null, Arrays.asList(getBronPartijCode(), getDoelPartijCode()), getGerelateerdeAnummers());
    }

    /**
     * Geeft de gerelateerde aNummers.
     * @return gerelateerdeAnummers
     */
    protected abstract List<String> getGerelateerdeAnummers();

    /**
     * Geeft het anummer van de persoon.
     * @param categorieen de categorien
     * @return aNummer
     */
    protected final List<String> getGerelateerdeAnummer(final List<Lo3CategorieWaarde> categorieen) {
        return Collections.singletonList(Lo3CategorieWaardeUtil.getElementWaarde(categorieen, Lo3CategorieEnum.PERSOON, 0, 0, Lo3ElementEnum.ANUMMER));
    }

    /*
     * *********************************************************************************************
     * ****************
     */

    @Override
    public final void parseInhoud(final String inhoud) throws BerichtSyntaxException, BerichtInhoudException {
        final List<Lo3CategorieWaarde> lo3Categorieen = Lo3Inhoud.parseInhoud(inhoud);
        syntaxControle.controleerInhoud(lo3Categorieen);
        parseCategorieen(lo3Categorieen);
    }

    @Override
    protected String getFormattedInhoud() {
        final List<Lo3CategorieWaarde> categorieen = formatInhoud();
        return Lo3Inhoud.formatInhoud(categorieen);
    }

    /**
     * Hook voor subclasses om specifieke parsing te doen.
     * @param categorieen generieke categorieen
     * @throws BerichtInhoudException bij inhoudelijke bericht fouten
     */
    protected void parseCategorieen(final List<Lo3CategorieWaarde> categorieen) throws BerichtInhoudException {
        // Hook
    }

    /**
     * Hook voor subclasses om specifieke formatting te doen.
     * @return generieke categorieen
     */
    protected List<Lo3CategorieWaarde> formatInhoud() {
        return Collections.emptyList();
    }

    @Override
    public String getInhoud() {
        return "";
    }

    /*
     * *********************************************************************************************
     * ****************
     */

    /**
     * Geef het eerste voorkomen van een categorie in de lijst.
     * @param categorieen lijst met categorieen
     * @param categorie te zoeken categorie
     * @return eerste voorkomen van de categorie (of null als niet gevonden)
     */
    protected final Lo3CategorieWaarde getCategorie(final List<Lo3CategorieWaarde> categorieen, final Lo3CategorieEnum categorie) {
        final List<Lo3CategorieWaarde> result = findCategorie(categorieen, categorie);
        return result.isEmpty() ? null : result.get(0);
    }

    /**
     * Geef de voorkomens van een categorie in de lijst.
     * @param categorieen lijst met categorieen
     * @param categorie te zoeken categorie
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

    /*
     * *********************************************************************************************
     * ****************
     */
}

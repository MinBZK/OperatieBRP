/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.lo3.AbstractParsedLo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Header;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3CategorieWaardeFormatter;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3Format;
import nl.bzk.migratiebrp.bericht.model.lo3.syntax.Lo3SyntaxControle;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Lq01: Synchronisatievraag.
 */
public final class Lq01Bericht extends AbstractParsedLo3Bericht implements Lo3Bericht, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Lo3Header HEADER = new Lo3Header(Lo3HeaderVeld.RANDOM_KEY, Lo3HeaderVeld.BERICHTNUMMER, Lo3HeaderVeld.HERHALING);
    private String aNummer;

    /**
     * Constructor.
     */
    public Lq01Bericht() {
        super(HEADER, Lo3SyntaxControle.STANDAARD, "Lq01", null);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.bericht.model.lo3.AbstractLo3Bericht#getGerelateerdeAnummers()
     */
    @Override
    protected List<String> getGerelateerdeAnummers() {
        return Arrays.asList(aNummer);
    }

    /*
     * *********************************************************************************************
     * ****************
     */

    @Override
    protected void parseCategorieen(final List<Lo3CategorieWaarde> categorieen) throws BerichtInhoudException {
        // rubriek 01.01.10 A-nummer uit het Lq01-bericht
        for (final Lo3CategorieWaarde catWaarde : categorieen) {
            if (Lo3CategorieEnum.CATEGORIE_01.equals(catWaarde.getCategorie())) {
                aNummer = catWaarde.getElement(Lo3ElementEnum.ELEMENT_0110);
                break;
            }
        }
    }

    @Override
    protected List<Lo3CategorieWaarde> formatInhoud() {
        if (aNummer == null) {
            throw new IllegalStateException("A-Nummer mag niet leeg zijn.");
        }

        // format element 01.10.10
        // @formatter:off
        final Lo3CategorieWaardeFormatter formatter = new Lo3CategorieWaardeFormatter();
        formatter.categorie(Lo3CategorieEnum.CATEGORIE_01);
        formatter.element(Lo3ElementEnum.ELEMENT_0110, Lo3Format.format(aNummer));

        // @formatter:off
        return formatter.getList();
    }

    /*
     * *********************************************************************************************
     * ****************
     */

    /**
     * Zet de categorieen obv een lo3 persoonslijst.
     * @param paramANummer Het A-nummer.
     */
    public void setANummer(final String paramANummer) {
        aNummer = paramANummer;
    }

    /**
     * Geeft het A-nummer terug.
     * @return Het A-nummer
     */
    public String getANummer() {
        return aNummer;
    }

}

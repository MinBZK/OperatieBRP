/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.impl;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.lo3.AbstractParsedLo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3EindBericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Header;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.syntax.Lo3SyntaxControle;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Tf11.
 */
public final class Tf11Bericht extends AbstractParsedLo3Bericht implements Lo3Bericht, Lo3EindBericht, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Lo3Header HEADER = new Lo3Header(Lo3HeaderVeld.RANDOM_KEY, Lo3HeaderVeld.BERICHTNUMMER);

    private String aNummer;

    /**
     * Constructor.
     */
    public Tf11Bericht() {
        super(HEADER, Lo3SyntaxControle.STANDAARD, "Tf11", null);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.bericht.model.lo3.AbstractLo3Bericht#getGerelateerdeAnummers()
     */
    @Override
    protected List<String> getGerelateerdeAnummers() {
        return Collections.emptyList();
    }

    @Override
    protected void parseCategorieen(final List<Lo3CategorieWaarde> categorieen) throws BerichtInhoudException {
        final Lo3CategorieWaarde categorie01 = getCategorie(categorieen, Lo3CategorieEnum.CATEGORIE_01);

        if (categorie01 != null) {
            aNummer = categorie01.getElement(Lo3ElementEnum.ELEMENT_0110);
        }
    }

    @Override
    protected List<Lo3CategorieWaarde> formatInhoud() {
        final Lo3CategorieWaarde categorie01 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, -1, -1);
        categorie01.addElement(Lo3ElementEnum.ELEMENT_0110, aNummer);

        return Collections.singletonList(categorie01);
    }

    /**
     * Zet de waarde van a nummer.
     * @param administratienummer a nummer
     */
    public void setANummer(final String administratienummer) {
        aNummer = administratienummer;
    }

    /**
     * Geef de waarde van a nummer.
     * @return a nummer
     */
    public String getANummer() {
        return aNummer;
    }
}

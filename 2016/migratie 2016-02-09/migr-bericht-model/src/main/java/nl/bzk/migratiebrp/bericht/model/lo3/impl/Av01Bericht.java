/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.impl;

import java.io.Serializable;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.lo3.AbstractParsedLo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Header;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3CategorieWaardeFormatter;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Af11: Foutbericht: verwijderen afnemersindicatie van persoonslijst onmogelijk.
 */
public final class Av01Bericht extends AbstractParsedLo3Bericht implements Lo3Bericht, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Lo3Header HEADER = new Lo3Header(Lo3HeaderVeld.RANDOM_KEY, Lo3HeaderVeld.BERICHTNUMMER, Lo3HeaderVeld.HERHALING);

    private String aNummer;

    /**
     * Constructor.
     */
    public Av01Bericht() {
        super(HEADER, "Av01", "uc1003-verwijderen");
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.bericht.model.lo3.AbstractLo3Bericht#getGerelateerdeAnummers()
     */
    @Override
    protected List<String> getGerelateerdeAnummers() {
        return null;
    }

    @Override
    protected void parseInhoud(final List<Lo3CategorieWaarde> categorieen) throws BerichtInhoudException {

        // rubriek 01.01.10 A-nummer
        for (final Lo3CategorieWaarde categorie : categorieen) {
            if (categorie.getCategorie().equals(Lo3CategorieEnum.CATEGORIE_01)) {
                aNummer = categorie.getElement(Lo3ElementEnum.ELEMENT_0110);
                break;
            }
        }

        if (aNummer == null || "".equals(aNummer)) {
            throw new BerichtInhoudException("Bericht bevat niet het verplichte zoekcriteria (01.01.10).");
        }
    }

    @Override
    protected List<Lo3CategorieWaarde> formatInhoud() {
        final Lo3CategorieWaardeFormatter formatter = new Lo3CategorieWaardeFormatter();
        formatter.categorie(Lo3CategorieEnum.CATEGORIE_01);
        formatter.element(Lo3ElementEnum.ELEMENT_0110, aNummer);
        return formatter.getList();
    }

    /**
     * Geef de waarde van a nummer.
     *
     * @return a nummer
     */
    public String getANummer() {
        return aNummer;
    }

    /**
     * Zet de waarde van a nummer.
     *
     * @param administratienummer
     *            a nummer
     */
    public void setANummer(final String administratienummer) {
        aNummer = administratienummer;
    }
}

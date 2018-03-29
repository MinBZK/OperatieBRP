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
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3CategorieWaardeFormatter;
import nl.bzk.migratiebrp.bericht.model.lo3.syntax.Lo3SyntaxControle;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Af11: Foutbericht: verwijderen afnemersindicatie van persoonslijst onmogelijk.
 */
public final class Af11Bericht extends AbstractParsedLo3Bericht implements Lo3Bericht, Lo3EindBericht, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Lo3Header HEADER =
            new Lo3Header(Lo3HeaderVeld.RANDOM_KEY, Lo3HeaderVeld.BERICHTNUMMER, Lo3HeaderVeld.FOUTREDEN, Lo3HeaderVeld.GEMEENTE, Lo3HeaderVeld.A_NUMMER);

    private String aNummer;

    /**
     * Enum met de mogelijke foutredenen.
     */
    public enum Foutreden {

        /**
         * PL is geblokkeerd i.v.m. verhuizing naar gemeente "code".
         */
        B,

        /**
         * Persoon komt niet voor.
         */
        G,

        /**
         * betrokkene heeft om geheimhouding verzocht.
         */
        H,

        /**
         * geen afnemersindicatie geplaatst bij deze persoon.
         */
        I,

        /**
         * eenduidige identificatie niet gelukt.
         */
        U,

        /**
         * persoon is verhuisd naar gemeente "code".
         */
        V
    }

    /**
     * Constructor.
     */
    public Af11Bericht() {
        super(HEADER, Lo3SyntaxControle.STANDAARD, "Af11", null);

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

    /**
     * Geeft de foutreden terug.
     * @return De foutreden.
     */
    public Foutreden getFoutreden() {
        return Foutreden.valueOf(getHeaderWaarde(Lo3HeaderVeld.FOUTREDEN));
    }

    /**
     * Bepaal of dit bericht verwijsgegevens naar een andere gemeente bevat.
     * @return true, als foutreden 'V' is en de gemeente is gevuld.
     */
    public boolean bevatVerwijsgegevens() {
        final String foutreden = getHeaderWaarde(Lo3HeaderVeld.FOUTREDEN);
        final String gemeente = getHeaderWaarde(Lo3HeaderVeld.GEMEENTE);

        return Foutreden.V.toString().equals(foutreden) && gemeente != null && !"".equals(gemeente) && !"0000".equals(gemeente);
    }

    /**
     * Geef de waarde van gemeente.
     * @return gemeente
     */
    public String getGemeente() {
        return getHeaderWaarde(Lo3HeaderVeld.GEMEENTE);
    }

    /**
     * Geef de waarde van a nummer.
     * @return a nummer
     */
    public String getANummer() {
        return aNummer;
    }

    @Override
    protected void parseCategorieen(final List<Lo3CategorieWaarde> categorieen) throws BerichtInhoudException {
        // rubriek 01.01.10 A-nummer
        for (final Lo3CategorieWaarde catWaarde : categorieen) {
            if (Lo3CategorieEnum.CATEGORIE_01.equals(catWaarde.getCategorie())) {
                aNummer = catWaarde.getElement(Lo3ElementEnum.ELEMENT_0110);
                break;
            }
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
     * Zet de waarde van a nummer.
     * @param administratienummer a nummer
     */
    public void setANummer(final String administratienummer) {
        aNummer = administratienummer;
    }
}

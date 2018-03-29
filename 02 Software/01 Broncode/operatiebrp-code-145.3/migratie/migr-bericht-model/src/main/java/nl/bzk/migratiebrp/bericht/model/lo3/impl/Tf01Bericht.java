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
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3EindBericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Header;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3CategorieWaardeFormatter;
import nl.bzk.migratiebrp.bericht.model.lo3.syntax.Lo3SyntaxControle;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Tf01. Fout: persoon niet in te schrijven. Indien de persoon niet in te schrijven is, gaat dit
 * bericht terug naar de gemeente van de 'toevallige geboorte'. Te verzenden door: gemeente van
 * eerste inschrijving Te verzenden aan: geboortegemeente
 */
public final class Tf01Bericht extends AbstractParsedLo3Bericht implements Lo3Bericht, Lo3EindBericht, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Lo3Header HEADER =
            new Lo3Header(Lo3HeaderVeld.RANDOM_KEY, Lo3HeaderVeld.BERICHTNUMMER, Lo3HeaderVeld.FOUTREDEN, Lo3HeaderVeld.GEMEENTE, Lo3HeaderVeld.A_NUMMER);

    private String akteNummer;

    /**
     * Enum met de mogelijke foutredenen.
     */
    public enum Foutreden {

        /**
         * Emigratie.
         */
        E,

        /**
         * Ministrieel besluit.
         */
        M,

        /**
         * Overleden.
         */
        O,

        /**
         * Verhuisd.
         */
        V,

        /**
         * Reeds aanwezig.
         */
        A,

        /**
         * Geblokkeerd.
         */
        B,

        /**
         * Niet gevonden.
         */
        G,

        /**
         * Niet uniek.
         */
        U
    }

    /**
     * Constructor.
     */
    public Tf01Bericht() {
        super(HEADER, Lo3SyntaxControle.STANDAARD, "Tf01", null);

        setHeader(Lo3HeaderVeld.RANDOM_KEY, null);
        setHeader(Lo3HeaderVeld.BERICHTNUMMER, getBerichtType());
        setHeader(Lo3HeaderVeld.FOUTREDEN, null);
        setHeader(Lo3HeaderVeld.GEMEENTE, null);
        setHeader(Lo3HeaderVeld.A_NUMMER, null);
    }

    /**
     * Convenience constructor.
     * @param tb01Bericht Het gerelateerde Tb01Bericht, waar dit Tf01Bericht het antwoord op is.
     * @param foutreden De foutreden
     */
    public Tf01Bericht(final Tb01Bericht tb01Bericht, final Foutreden foutreden) {
        this();

        akteNummer = tb01Bericht.getHeaderWaarde(Lo3HeaderVeld.AKTENUMMER);
        setBronPartijCode(tb01Bericht.getDoelPartijCode());
        setDoelPartijCode(tb01Bericht.getBronPartijCode());
        setHeader(Lo3HeaderVeld.FOUTREDEN, foutreden.toString());
        setHeader(Lo3HeaderVeld.GEMEENTE, tb01Bericht.getDoelPartijCode());
        setHeader(Lo3HeaderVeld.A_NUMMER, tb01Bericht.getHeaderWaarde(Lo3HeaderVeld.A_NUMMER));
        setCorrelationId(tb01Bericht.getMessageId());
    }

    /**
     * Geeft de foutreden terug.
     * @return De foutreden.
     */
    public Foutreden getFoutreden() {
        return Foutreden.valueOf(getHeaderWaarde(Lo3HeaderVeld.FOUTREDEN));
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.bericht.model.lo3.AbstractLo3Bericht#getGerelateerdeAnummers()
     */
    @Override
    protected List<String> getGerelateerdeAnummers() {
        return Arrays.asList(getHeaderWaarde(Lo3HeaderVeld.A_NUMMER));
    }

    /*
     * *********************************************************************************************
     * ****************
     */
    @Override
    protected void parseCategorieen(final List<Lo3CategorieWaarde> categorieen) throws BerichtInhoudException {
        // rubriek 01.81.20 Aktenummer uit het Tb01-bericht
        for (final Lo3CategorieWaarde catWaarde : categorieen) {
            if (Lo3CategorieEnum.CATEGORIE_01.equals(catWaarde.getCategorie())) {
                akteNummer = catWaarde.getElement(Lo3ElementEnum.ELEMENT_8120);
                break;
            }
        }
    }

    @Override
    protected List<Lo3CategorieWaarde> formatInhoud() {
        final Lo3CategorieWaardeFormatter formatter = new Lo3CategorieWaardeFormatter();
        formatter.categorie(Lo3CategorieEnum.CATEGORIE_01);
        formatter.element(Lo3ElementEnum.ELEMENT_8120, akteNummer);
        return formatter.getList();
    }

    /*
     * *********************************************************************************************
     * ****************
     */

    /**
     * Geef de waarde van aktenummer.
     * @return aktenummer
     */
    public String getAktenummer() {
        return akteNummer;
    }

}

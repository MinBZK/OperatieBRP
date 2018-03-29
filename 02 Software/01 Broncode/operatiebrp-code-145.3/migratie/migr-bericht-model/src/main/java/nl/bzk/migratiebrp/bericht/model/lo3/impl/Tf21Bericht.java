/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.impl;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
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
 * Fout: gebeurtenisgegevens niet te verwerken.
 *
 * Indien de gemeente van inschrijving niet in staat is de gegevens betreffende de gebeurtenis te
 * verwerken, volgt dit bericht onder vermelding van de foutreden die verwerking niet mogelijk
 * maakt.
 */
public final class Tf21Bericht extends AbstractParsedLo3Bericht implements Lo3Bericht, Lo3EindBericht, Serializable {

    private static final long serialVersionUID = 1L;

    private static final Lo3Header HEADER = new Lo3Header(Lo3HeaderVeld.RANDOM_KEY, Lo3HeaderVeld.BERICHTNUMMER, Lo3HeaderVeld.FOUTREDEN,
            Lo3HeaderVeld.GEMEENTE, Lo3HeaderVeld.A_NUMMER, Lo3HeaderVeld.AANTAL, Lo3HeaderVeld.IDENTIFICERENDE_RUBRIEKEN);

    private List<Lo3CategorieWaarde> categorieen;

    /**
     * Foutreden waardoor tb02 niet kan worden verwerkt.
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
         * Actuele gegevens PL komene niet overeen met gegevens van voor het rechtsfeit in het
         * Tb02-bericht.
         */
        N,

        /**
         * Bijhouden PL opgeschort wegens overlijden.
         */
        O,

        /**
         * Eenduidige identificatie niet gelukt.
         */
        U,

        /**
         * Persoon is verhuisd naar gemeente "code".
         */
        V
    }

    /**
     * Default constructor.
     */
    public Tf21Bericht() {
        super(HEADER, Lo3SyntaxControle.STANDAARD, "Tf21", null);
        setHeader(Lo3HeaderVeld.RANDOM_KEY, null);
        setHeader(Lo3HeaderVeld.BERICHTNUMMER, getBerichtType());
        setHeader(Lo3HeaderVeld.FOUTREDEN, null);
        setHeader(Lo3HeaderVeld.GEMEENTE, null);
        setHeader(Lo3HeaderVeld.A_NUMMER, null);
        setHeader(Lo3HeaderVeld.AANTAL, null);
        setHeader(Lo3HeaderVeld.IDENTIFICERENDE_RUBRIEKEN, null);
    }

    /**
     * Constructor.
     * @param tb02Bericht Het tb02 bericht waarop deze tf21 het antwoord is
     * @param foutreden De reden waarom het tf21 bericht wordt verstuur
     * @param gemeenteCode De code van de gemeente in het geval van een foutreden B of V.
     */
    public Tf21Bericht(final Tb02Bericht tb02Bericht, final Foutreden foutreden, final String gemeenteCode) {
        this();

        setHeader(Lo3HeaderVeld.RANDOM_KEY, "00000000");
        setHeader(Lo3HeaderVeld.FOUTREDEN, foutreden.name());
        if (Foutreden.B.equals(foutreden) || Foutreden.V.equals(foutreden)) {
            setHeader(Lo3HeaderVeld.GEMEENTE, gemeenteCode);
        } else {
            setHeader(Lo3HeaderVeld.GEMEENTE, null);
        }

        // Wanneer in de kop van het bericht ook Foutreden voorkomt, dan wordt bij Foutreden "G" (=
        // niet gevonden), "H"
        // = geheimhouding), "R" (= geen autorisatie), "U" (= niet uniek) of "X" (= niet
        // geautoriseerd) de waarde
        // "0000000000" opgenomen.
        if (Foutreden.G.equals(foutreden) || Foutreden.U.equals(foutreden)) {
            setHeader(Lo3HeaderVeld.A_NUMMER, "0000000000");
        } else {
            setHeader(Lo3HeaderVeld.A_NUMMER, tb02Bericht.get(Lo3CategorieEnum.CATEGORIE_01, Lo3ElementEnum.ELEMENT_0110));
        }

        setHeader(Lo3HeaderVeld.AANTAL, tb02Bericht.getHeaderWaarde(Lo3HeaderVeld.AANTAL));
        setHeader(Lo3HeaderVeld.IDENTIFICERENDE_RUBRIEKEN, tb02Bericht.getHeaderWaarde(Lo3HeaderVeld.IDENTIFICERENDE_RUBRIEKEN));
        setCorrelationId(tb02Bericht.getMessageId());
        setBronPartijCode(tb02Bericht.getDoelPartijCode());
        setDoelPartijCode(tb02Bericht.getBronPartijCode());
        parseCategorieen(tb02Bericht.formatInhoud());
    }

    @Override
    protected List<String> getGerelateerdeAnummers() {
        return Collections.emptyList();
    }

    @Override
    protected void parseCategorieen(final List<Lo3CategorieWaarde> deCategorieen) {
        categorieen = deCategorieen;
    }

    @Override
    protected List<Lo3CategorieWaarde> formatInhoud() {
        return categorieen;
    }
}

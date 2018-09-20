/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen;

import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.UniqueSequence;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.regels.AbstractComponentTest;

public abstract class AbstractRelatieConverteerderTest extends AbstractComponentTest {
    protected static final int BSN = 123456789;
    protected static final int BSN_2 = 98754321;
    protected static final long A_NUMMER = 1234567890L;
    protected static final long A_NUMMER_2 = 9876543210L;
    protected static final Lo3String AKTENUMMER = Lo3String.wrap("1 ACD12");
    protected static final String DOCUMENT_BESCHRIJVING = "Beschrijving";
    protected static final String LAND_CODE = "6030";
    protected static final String GEMEENTE_CODE = "0518";
    protected static final String GESLACHTSNAAM = "Barendsen";
    protected static final String GESLACHTSNAAM_2 = "Smit";
    protected static final String VOORNAAM = "Billy";
    protected static final String VOORNAAM_2 = "Piet";
    protected static final int DATUM_DOCUMENT = 20090103;
    protected static final int DATUM_STANDAARD = Lo3Datum.NULL_DATUM.getIntegerWaarde();
    protected static final int DATUM_GELDIGHEID = 20090101;
    protected static final int DATUM_GELDIGHEID_1 = 20080601;
    protected static final int DATUM_GELDIGHEID_2 = 20080101;
    protected static final int DATUM_GELDIGHEID_3 = 20070101;
    protected static final int DATUM_OPNEMING = 20090102;
    protected static final int DATUM_OPNEMING_1 = 20080602;
    protected static final int DATUM_OPNEMING_2 = 20080102;
    protected static final int DATUM_OPNEMING_3 = 20070102;
    protected static final int DATUM_OPNEMING_4 = 20060102;
    protected static final int DATUM_GEBOORTE = 19850101;
    protected static final int DATUM_GEBOORTE_2 = 19800101;
    protected static final int DATUM_FAMILIERECHTELIJKE_BETREKKING = 20061231;
    protected static final int DATUM_FAMILIERECHTELIJKE_BETREKKING_2 = 20050101;

    protected Lo3Historie maakActueleHistorie() {
        return maakHistorie(false, DATUM_GELDIGHEID, DATUM_OPNEMING);
    }

    protected Lo3Historie maakJuisteHistorie(final int datum) {
        return maakHistorie(false, datum, datum + 1);
    }

    protected Lo3Historie maakHistorie(final boolean onjuist, final int datumGeldigheid, final int datumOpneming) {
        Lo3IndicatieOnjuist indicatieOnjuist = null;
        if (onjuist) {
            indicatieOnjuist = new Lo3IndicatieOnjuist("O");
        }
        return new Lo3Historie(indicatieOnjuist, new Lo3Datum(datumGeldigheid), new Lo3Datum(datumOpneming));
    }

    protected Lo3Documentatie maakAkte() {
        return maakAkte(AKTENUMMER);
    }

    protected Lo3Documentatie maakAkte(final Lo3String aktenummer) {
        return new Lo3Documentatie(UniqueSequence.next(), new Lo3GemeenteCode(GEMEENTE_CODE), aktenummer, null, null, null, null, null);
    }

    protected Lo3Documentatie maakDocumentatie() {
        return maakDocumentatie(DATUM_DOCUMENT, DOCUMENT_BESCHRIJVING);
    }

    protected Lo3Documentatie maakDocumentatie(final int datumDocument, final String beschrijving) {
        return new Lo3Documentatie(
            UniqueSequence.next(),
            null,
            null,
            new Lo3GemeenteCode(GEMEENTE_CODE),
            new Lo3Datum(datumDocument),
            Lo3String.wrap(beschrijving),
            null,
            null);
    }

    /**
     * Geef de waarde van categorie.
     *
     * @return categorie
     */
    protected abstract Lo3CategorieEnum getCategorie();

    protected Lo3Herkomst[] maakLo3Herkomsten(final int aantalVoorkomen) {
        return maakLo3Herkomsten(getCategorie(), aantalVoorkomen);
    }

    protected Lo3Herkomst[] maakLo3Herkomsten(final Lo3CategorieEnum categorie, final int aantalVoorkomen) {
        final Lo3Herkomst[] herkomsten = new Lo3Herkomst[aantalVoorkomen];
        for (int i = 0; i < aantalVoorkomen; i++) {
            Lo3CategorieEnum juisteCategorie = categorie;
            if (i > 0) {
                juisteCategorie = Lo3CategorieEnum.bepaalHistorischeCategorie(categorie);
            }
            herkomsten[i] = new Lo3Herkomst(juisteCategorie, 0, i);
        }
        return herkomsten;
    }
}

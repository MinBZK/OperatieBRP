/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen;

import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper.lo3Documentatie;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper.lo3His;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.AbstractBrpIstGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3CategorieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractBrpIstConverteerderTest<T extends AbstractBrpIstGroepInhoud> {

    @Mock
    protected BrpAttribuutConverteerder attribuutConverteerder;
    protected static final String VOORNAMEN = "Piet";
    protected static final String VOORVOEGSEL = "van";
    static final char SCHEIDINGSTEKEN = ' ';
    static final String GESLACHTSNAAMSTAM = "Klaveren";
    static final String AKTENUMMER = "A350";
    protected static final String GEMEENTE_CODE = "0518";
    static final String BRP_PARTIJ_CODE = "051801";
    static final String LAND_CODE = "6030";
    protected static final String ANUMMER = "1234597890";
    protected static final String BSN = "123456789";
    protected static final int DATUM_OPNEMING = 2012_01_02;
    static final int DATUM_GELDIGHEID = 2012_01_01;
    protected static final int DATUM_GEBOORTE = 1980_01_01;
    static final String GESLACHT_MAN = "M";
    static final String GESLACHT_VROUW = "V";
    static final String BRP_ADELLIJKE_TITEL = "B";
    static final String LO3_ADELLIJKE_TITEL_MAN = "B";
    static final String LO3_ADELLIJKE_TITEL_VROUW = "BS";
    static final String BRP_PREDICAAT = "J";
    static final String LO3_PREDIKAAT_MAN = "JH";
    static final String LO3_PREDIKAAT_VROUW = "JV";
    static final int DATUM_SLUITING = 2012_01_01;
    protected static final String HUWELIJK = "H";
    static final Character REDEN_EINDE = 'S';
    static final int DATUM_DOCUMENT = 2012_01_03;
    static final String DOCUMENT_OMSCHRIJVING = "omschrijving";
    static final BrpSoortDocumentCode BRP_AKTE_DOCUMENT = new BrpSoortDocumentCode("1");

    void maakMockitoWhenStepsVoorPersoonsgegevens() {
        when(attribuutConverteerder.converteerAdministratieNummer(new BrpString(ANUMMER))).thenReturn(new Lo3String(ANUMMER));
        when(attribuutConverteerder.converteerBurgerservicenummer(new BrpString(BSN))).thenReturn(new Lo3String(BSN));
        when(attribuutConverteerder.converteerString(new BrpString(GESLACHTSNAAMSTAM))).thenReturn(new Lo3String(GESLACHTSNAAMSTAM));
        when(attribuutConverteerder.converteerString(new BrpString(VOORNAMEN))).thenReturn(new Lo3String(VOORNAMEN));

        when(attribuutConverteerder.converteerVoorvoegsel(new BrpString(VOORVOEGSEL), new BrpCharacter(SCHEIDINGSTEKEN)))
                .thenReturn(new Lo3String(VOORVOEGSEL));
        when(attribuutConverteerder.converteerGeslachtsaanduiding(new BrpGeslachtsaanduidingCode(GESLACHT_MAN)))
                .thenReturn(new Lo3Geslachtsaanduiding(GESLACHT_MAN));
        when(attribuutConverteerder.converteerGeslachtsaanduiding(new BrpGeslachtsaanduidingCode(GESLACHT_VROUW)))
                .thenReturn(new Lo3Geslachtsaanduiding(GESLACHT_VROUW));
        when(attribuutConverteerder
                .converteerLocatie(new BrpGemeenteCode(GEMEENTE_CODE), null, null, new BrpLandOfGebiedCode(LAND_CODE), null))
                .thenReturn(new BrpAttribuutConverteerder.Lo3GemeenteLand(new Lo3GemeenteCode(GEMEENTE_CODE), new Lo3LandCode(LAND_CODE)));
        when(attribuutConverteerder.converteerDatum(new BrpInteger(DATUM_GEBOORTE, null))).thenReturn(new Lo3Datum(DATUM_GEBOORTE));
    }

    protected BrpGroep<T> maakGroep(final T inhoud) {
        final BrpHistorie historie = new BrpHistorie(BrpDatumTijd.NULL_DATUM_TIJD, null, null);
        return new BrpGroep<>(inhoud, historie, null, null, null);
    }

    Lo3Documentatie maakDocumentatie(final boolean akte) {
        when(attribuutConverteerder.converteerGemeenteCode(new BrpPartijCode(BRP_PARTIJ_CODE))).thenReturn(new Lo3GemeenteCode(GEMEENTE_CODE));

        Lo3Documentatie result;
        if (akte) {
            when(attribuutConverteerder.converteerString(new BrpString(AKTENUMMER))).thenReturn(new Lo3String(AKTENUMMER));

            result = lo3Documentatie(0L, GEMEENTE_CODE, AKTENUMMER, null, null, null);
        } else {
            // Documentatie
            when(attribuutConverteerder.converteerDatum(new BrpInteger(DATUM_DOCUMENT, null))).thenReturn(new Lo3Datum(DATUM_DOCUMENT));
            when(attribuutConverteerder.converteerString(new BrpString(DOCUMENT_OMSCHRIJVING))).thenReturn(new Lo3String(DOCUMENT_OMSCHRIJVING));

            result = lo3Documentatie(0L, null, null, GEMEENTE_CODE, DATUM_DOCUMENT, DOCUMENT_OMSCHRIJVING);
        }
        return result;
    }

    Lo3Historie maakHistorie() {
        // Historie
        when(attribuutConverteerder.converteerDatum(new BrpInteger(DATUM_GELDIGHEID))).thenReturn(new Lo3Datum(DATUM_GELDIGHEID));
        when(attribuutConverteerder.converteerDatum(new BrpInteger(DATUM_OPNEMING))).thenReturn(new Lo3Datum(DATUM_OPNEMING));
        return lo3His(null, DATUM_GELDIGHEID, DATUM_OPNEMING);
    }

    Lo3Herkomst maakHerkomst(final Lo3CategorieEnum categorie) {
        return maakHerkomst(categorie, 0);
    }

    Lo3Herkomst maakHerkomst(final Lo3CategorieEnum categorie, final int stapel) {
        return new Lo3Herkomst(categorie, stapel, 0);
    }

    protected <V extends Lo3CategorieInhoud> void controleerStapel(
            final Lo3Stapel<V> lo3Stapel,
            final V expectedInhoud,
            final Lo3Documentatie expectedDocumentatie,
            final Lo3Onderzoek expectedOnderzoek,
            final Lo3Historie expectedHistorie,
            final Lo3Herkomst expectedHerkomst) {
        assertNotNull("Stapel is null", lo3Stapel);
        assertFalse("Stapel is leeg", lo3Stapel.isEmpty());
        assertEquals("Meer dan 1 stapel gevonden", 1, lo3Stapel.getCategorieen().size());

        final Lo3Categorie<V> voorkomen = lo3Stapel.get(0);
        assertNotNull("Voorkomen is leeg", voorkomen);
        assertEquals("Onderzoek niet verwacht", expectedOnderzoek, voorkomen.getOnderzoek());
        assertEquals("Inhoud niet gelijk", expectedInhoud, voorkomen.getInhoud());
        assertEquals("Documentatie niet gelijk", expectedDocumentatie, voorkomen.getDocumentatie());
        assertEquals("Historie niet gelijk", expectedHistorie, voorkomen.getHistorie());
        assertEquals("Herkomst niet gelijk", expectedHerkomst, voorkomen.getLo3Herkomst());
    }
}

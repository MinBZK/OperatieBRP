/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen;

import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper.lo3Documentatie;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper.lo3His;

import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.AbstractBrpIstGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3CategorieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.regels.AbstractComponentTest;
import org.junit.Assert;
import org.junit.Test;

public abstract class AbstractBrpIstConverteerderTest<T extends AbstractBrpIstGroepInhoud> extends AbstractComponentTest {

    protected static final String VOORNAMEN = "Piet";
    protected static final String VOORVOEGSEL = "van";
    protected static final char SCHEIDINGSTEKEN = ' ';
    protected static final String GESLACHTSNAAMSTAM = "Klaveren";
    protected static final String AKTENUMMER = "A350";
    protected static final short BRP_GEMEENTE_CODE = (short) 518;
    protected static final String LO3_GEMEENTE_CODE = "0" + BRP_GEMEENTE_CODE;
    protected static final int BRP_PARTIJ_CODE = 51801;
    protected static final short BRP_LAND_OF_GEBIED_CODE_NL = (short) 6030;
    protected static final String LO3_LAND_CODE = "" + 6030;
    protected static final long ANUMMER = 1234597890L;
    protected static final int BSN = 123456789;
    protected static final int DATUM_OPNEMING = 20120102;
    protected static final int DATUM_GELDIGHEID = 20120101;
    protected static final int DATUM_GEBOORTE = 1980101;
    protected static final String GESLACHT_MAN = "M";
    protected static final String GESLACHT_VROUW = "V";
    protected static final String BRP_ADELLIJKE_TITEL = "B";
    protected static final String LO3_ADELLIJKE_TITEL_MAN = "B";
    protected static final String LO3_ADELLIJKE_TITEL_VROUW = "BS";
    protected static final String BRP_PREDICAAT = "J";
    protected static final String LO3_PREDIKAAT_MAN = "JH";
    protected static final String LO3_PREDIKAAT_VROUW = "JV";
    protected static final int DATUM_SLUITING = 20120101;
    protected static final int DATUM_ONTBINDING = 20120501;
    protected static final String HUWELIJK = "H";
    protected static final Character REDEN_EINDE = 'S';
    protected static final int DATUM_DOCUMENT = 20120103;
    protected static final String DOCUMENT_OMSCHRIJVING = "omschrijving";
    protected static final BrpSoortDocumentCode BRP_AKTE_DOCUMENT = new BrpSoortDocumentCode("1");

    @Test
    public void testNullStapel() {
        Assert.assertNull(getTestSubject().converteer(null));
    }

    /**
     * Geef de waarde van test subject.
     *
     * @return test subject
     */
    protected abstract BrpIstAbstractConverteerder<T> getTestSubject();

    protected BrpGroep<T> maakGroep(final T inhoud) {
        final BrpHistorie historie = new BrpHistorie(BrpDatumTijd.NULL_DATUM_TIJD, null, null);
        return new BrpGroep<>(inhoud, historie, null, null, null);
    }

    protected Lo3Documentatie maakDocumentatie(final boolean akte) {
        Lo3Documentatie result = null;
        if (akte) {
            result = lo3Documentatie(0L, LO3_GEMEENTE_CODE, AKTENUMMER, null, null, null);
        } else {
            result = lo3Documentatie(0L, null, null, LO3_GEMEENTE_CODE, DATUM_DOCUMENT, DOCUMENT_OMSCHRIJVING);
        }
        return result;
    }

    protected Lo3Historie maakHistorie() {
        return maakHistorie(null);
    }

    protected Lo3Historie maakHistorie(final String indicatieOnjuist) {
        return lo3His(indicatieOnjuist, DATUM_GELDIGHEID, DATUM_OPNEMING);
    }

    protected Lo3Herkomst maakHerkomst(final Lo3CategorieEnum categorie) {
        return maakHerkomst(categorie, 0);
    }

    protected Lo3Herkomst maakHerkomst(final Lo3CategorieEnum categorie, final int stapel) {
        return new Lo3Herkomst(categorie, stapel, 0);
    }

    protected <V extends Lo3CategorieInhoud> void controleerStapel(
        final Lo3Stapel<V> lo3Stapel,
        final V expectedInhoud,
        final Lo3Documentatie expectedDocumentatie,
        final Lo3Onderzoek expectedOnderzoek,
        final Lo3Historie expectedHistorie,
        final Lo3Herkomst expectedHerkomst)
    {
        Assert.assertNotNull("Stapel is null", lo3Stapel);
        Assert.assertFalse("Stapel is leeg", lo3Stapel.isEmpty());
        Assert.assertEquals("Meer dan 1 stapel gevonden", 1, lo3Stapel.getCategorieen().size());

        final Lo3Categorie<V> voorkomen = lo3Stapel.get(0);
        Assert.assertNotNull("Voorkomen is leeg", voorkomen);
        Assert.assertEquals("Onderzoek niet verwacht", expectedOnderzoek, voorkomen.getOnderzoek());
        Assert.assertEquals("Inhoud niet gelijk", expectedInhoud, voorkomen.getInhoud());
        Assert.assertEquals("Documentatie niet gelijk", expectedDocumentatie, voorkomen.getDocumentatie());
        Assert.assertEquals("Historie niet gelijk", expectedHistorie, voorkomen.getHistorie());
        Assert.assertEquals("Herkomst niet gelijk", expectedHerkomst, voorkomen.getLo3Herkomst());
    }
}

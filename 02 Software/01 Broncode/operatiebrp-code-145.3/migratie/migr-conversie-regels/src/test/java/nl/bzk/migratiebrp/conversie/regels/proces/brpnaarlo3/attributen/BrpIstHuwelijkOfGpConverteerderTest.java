/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen;

import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper.lo3HuwelijkOfGp;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstHuwelijkOfGpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstRelatieGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstStandaardGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortVerbintenis;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import org.junit.Before;
import org.junit.Test;

public class BrpIstHuwelijkOfGpConverteerderTest extends AbstractBrpIstConverteerderTest<BrpIstHuwelijkOfGpGroepInhoud> {

    private BrpIstHuwelijkOfGpConverteerder subject;
    private Lo3Documentatie expectedAkte;
    private Lo3Historie expectedHistorie;
    private Lo3Herkomst expectedHerkomst;
    private Lo3HuwelijkOfGpInhoud expectedSluiting;
    private Lo3HuwelijkOfGpInhoud expectedOntbinding;

    @Before
    public void setUp() {
        subject = new BrpIstHuwelijkOfGpConverteerder(attribuutConverteerder);
        expectedAkte = maakDocumentatie(true);
        expectedHistorie = maakHistorie();
        expectedHerkomst = maakHerkomst(Lo3CategorieEnum.CATEGORIE_05);
        expectedOntbinding =
                lo3HuwelijkOfGp(ANUMMER, BSN, VOORNAMEN, null, VOORVOEGSEL, GESLACHTSNAAMSTAM, DATUM_GEBOORTE, GEMEENTE_CODE, LAND_CODE, GESLACHT_MAN,
                        null, null, null, DATUM_SLUITING, GEMEENTE_CODE, LAND_CODE, "" + REDEN_EINDE, HUWELIJK);
        expectedSluiting =
                lo3HuwelijkOfGp(ANUMMER, BSN, VOORNAMEN, null, VOORVOEGSEL, GESLACHTSNAAMSTAM, DATUM_GEBOORTE, GEMEENTE_CODE, LAND_CODE, GESLACHT_MAN,
                        DATUM_SLUITING, GEMEENTE_CODE, LAND_CODE, null, null, null, null, HUWELIJK);

        maakMockitoWhenStepsVoorPersoonsgegevens();

        when(attribuutConverteerder.converteerRedenOntbindingHuwelijk(new BrpRedenEindeRelatieCode(REDEN_EINDE))).thenReturn(new
                Lo3RedenOntbindingHuwelijkOfGpCode(String.valueOf(REDEN_EINDE)));
        when(attribuutConverteerder.converteerSoortVerbintenis(new BrpSoortRelatieCode(HUWELIJK))).thenReturn(new Lo3SoortVerbintenis(HUWELIJK));
        when(attribuutConverteerder.converteerDatum(new BrpInteger(DATUM_SLUITING, null))).thenReturn(new Lo3Datum(DATUM_SLUITING));

        when(attribuutConverteerder.converteerLocatie(null, null, null, null, null)).thenReturn(new BrpAttribuutConverteerder.Lo3GemeenteLand(null, null));
    }

    @Test
    public void testNullStapel() {
        assertTrue(subject.converteerHuwelijkOfGpStapels(null).isEmpty());
    }

    @Test
    public void testSluiting() {
        final List<BrpGroep<BrpIstHuwelijkOfGpGroepInhoud>> groepen = new ArrayList<>();
        final BrpIstHuwelijkOfGpGroepInhoud.Builder builder = maakHuwelijkOfGpGroepInhoud(true);
        final BrpIstHuwelijkOfGpGroepInhoud inhoud = builder.build();
        groepen.add(maakGroep(inhoud));
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> lo3Stapel = subject.converteerHuwelijkOfGpStapels(Collections.singletonList(new BrpStapel<>(groepen))).get(0);

        controleerStapel(lo3Stapel, expectedSluiting, expectedAkte, null, expectedHistorie, expectedHerkomst);
    }

    private BrpIstHuwelijkOfGpGroepInhoud.Builder maakHuwelijkOfGpGroepInhoud(final boolean sluiting) {
        return maakHuwelijkOfGpGroepInhoud(sluiting, 0);
    }

    private BrpIstHuwelijkOfGpGroepInhoud.Builder maakHuwelijkOfGpGroepInhoud(final boolean sluiting, final int stapelNr) {
        final BrpIstStandaardGroepInhoud.Builder standaardBuilder = new BrpIstStandaardGroepInhoud.Builder(Lo3CategorieEnum.CATEGORIE_05, stapelNr, 0);
        standaardBuilder.soortDocument(BRP_AKTE_DOCUMENT);
        standaardBuilder.partij(new BrpPartijCode(BRP_PARTIJ_CODE));
        standaardBuilder.rubriek8510IngangsdatumGeldigheid(new BrpInteger(DATUM_GELDIGHEID));
        standaardBuilder.rubriek8610DatumVanOpneming(new BrpInteger(DATUM_OPNEMING));
        standaardBuilder.aktenummer(new BrpString(AKTENUMMER));

        final BrpIstStandaardGroepInhoud standaardGegevens = standaardBuilder.build();

        final BrpIstRelatieGroepInhoud.Builder relatieBuilder = new BrpIstRelatieGroepInhoud.Builder(standaardGegevens);
        relatieBuilder.anummer(new BrpString(ANUMMER));
        relatieBuilder.bsn(new BrpString(BSN));
        relatieBuilder.voornamen(new BrpString(VOORNAMEN));
        relatieBuilder.voorvoegsel(new BrpString(VOORVOEGSEL));
        relatieBuilder.scheidingsteken(new BrpCharacter(SCHEIDINGSTEKEN));
        relatieBuilder.geslachtsnaamstam(new BrpString(GESLACHTSNAAMSTAM));
        relatieBuilder.datumGeboorte(new BrpInteger(DATUM_GEBOORTE));
        relatieBuilder.gemeenteCodeGeboorte(new BrpGemeenteCode(GEMEENTE_CODE));
        relatieBuilder.landOfGebiedGeboorte(new BrpLandOfGebiedCode(LAND_CODE));
        relatieBuilder.geslachtsaanduidingCode(BrpGeslachtsaanduidingCode.MAN);
        relatieBuilder.rubriek6210DatumIngangFamilierechtelijkeBetrekking(new BrpInteger(DATUM_GEBOORTE));

        final BrpIstHuwelijkOfGpGroepInhoud.Builder builder = new BrpIstHuwelijkOfGpGroepInhoud.Builder(standaardGegevens, relatieBuilder.build());
        if (sluiting) {
            builder.datumAanvang(new BrpInteger(DATUM_SLUITING));
            builder.gemeenteCodeAanvang(new BrpGemeenteCode(GEMEENTE_CODE));
            builder.landOfGebiedAanvang(new BrpLandOfGebiedCode(LAND_CODE));
        } else {
            builder.datumEinde(new BrpInteger(DATUM_SLUITING));
            builder.gemeenteCodeEinde(new BrpGemeenteCode(GEMEENTE_CODE));
            builder.landOfGebiedEinde(new BrpLandOfGebiedCode(LAND_CODE));
            builder.redenBeeindigingRelatieCode(new BrpRedenEindeRelatieCode(REDEN_EINDE));
        }
        builder.soortRelatieCode(BrpSoortRelatieCode.HUWELIJK);

        return builder;
    }

    public void testNullStapels() {
        assertTrue(subject.converteerHuwelijkOfGpStapels(null).isEmpty());
    }

    @Test
    public void testMeerdereStapels() {
        final List<BrpGroep<BrpIstHuwelijkOfGpGroepInhoud>> groepen1 = new ArrayList<>();
        groepen1.add(maakGroep(maakHuwelijkOfGpGroepInhoud(false).build()));

        final List<BrpGroep<BrpIstHuwelijkOfGpGroepInhoud>> groepen2 = new ArrayList<>();
        groepen2.add(maakGroep(maakHuwelijkOfGpGroepInhoud(false, 1).build()));

        final List<BrpStapel<BrpIstHuwelijkOfGpGroepInhoud>> stapels = new ArrayList<>();
        stapels.add(new BrpStapel<>(groepen1));
        stapels.add(new BrpStapel<>(groepen2));

        final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> lo3Stapels = subject.converteerHuwelijkOfGpStapels(stapels);
        assertNotNull(lo3Stapels);
        assertEquals(lo3Stapels.size(), 2);

        int stapelNr = 0;
        for (final Lo3Stapel<Lo3HuwelijkOfGpInhoud> lo3Stapel : lo3Stapels) {
            expectedHerkomst = maakHerkomst(Lo3CategorieEnum.CATEGORIE_05, stapelNr);
            controleerStapel(lo3Stapel, expectedOntbinding, expectedAkte, null, expectedHistorie, expectedHerkomst);
            stapelNr++;
        }
    }
}

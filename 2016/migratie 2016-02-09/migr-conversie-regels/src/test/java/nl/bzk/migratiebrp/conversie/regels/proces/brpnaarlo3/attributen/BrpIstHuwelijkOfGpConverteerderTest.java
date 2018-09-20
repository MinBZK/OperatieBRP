/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen;

import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper.lo3HuwelijkOfGp;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLong;
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
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BrpIstHuwelijkOfGpConverteerderTest extends AbstractBrpIstConverteerderTest<BrpIstHuwelijkOfGpGroepInhoud> {

    @Inject
    private BrpIstHuwelijkOfGpConverteerder subject;
    private Lo3Documentatie expectedAkte;
    private Lo3Historie expectedHistorie;
    private Lo3Herkomst expectedHerkomst;
    private Lo3HuwelijkOfGpInhoud expectedSluiting;
    private Lo3HuwelijkOfGpInhoud expectedOntbinding;

    @Before
    public void setUp() {
        expectedAkte = maakDocumentatie(true);
        expectedHistorie = maakHistorie();
        expectedHerkomst = maakHerkomst(Lo3CategorieEnum.CATEGORIE_05);
        expectedOntbinding =
                lo3HuwelijkOfGp(
                    ANUMMER,
                    BSN,
                    VOORNAMEN,
                    null,
                    VOORVOEGSEL,
                    GESLACHTSNAAMSTAM,
                    DATUM_GEBOORTE,
                    LO3_GEMEENTE_CODE,
                    LO3_LAND_CODE,
                    GESLACHT_MAN,
                    null,
                    null,
                    null,
                    DATUM_SLUITING,
                    LO3_GEMEENTE_CODE,
                    LO3_LAND_CODE,
                    "" + REDEN_EINDE,
                    HUWELIJK);
        expectedSluiting =
                lo3HuwelijkOfGp(
                    ANUMMER,
                    BSN,
                    VOORNAMEN,
                    null,
                    VOORVOEGSEL,
                    GESLACHTSNAAMSTAM,
                    DATUM_GEBOORTE,
                    LO3_GEMEENTE_CODE,
                    LO3_LAND_CODE,
                    GESLACHT_MAN,
                    DATUM_SLUITING,
                    LO3_GEMEENTE_CODE,
                    LO3_LAND_CODE,
                    null,
                    null,
                    null,
                    null,
                    HUWELIJK);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.AbstractBrpIstConverteerderTest#getTestSubject()
     */
    @Override
    protected BrpIstHuwelijkOfGpConverteerder getTestSubject() {
        return subject;
    }

    @Test
    public void testSluiting() {
        final List<BrpGroep<BrpIstHuwelijkOfGpGroepInhoud>> groepen = new ArrayList<>();
        final BrpIstHuwelijkOfGpGroepInhoud.Builder builder = maakHuwelijkOfGpGroepInhoud(true);
        final BrpIstHuwelijkOfGpGroepInhoud inhoud = builder.build();
        groepen.add(maakGroep(inhoud));
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> lo3Stapel = subject.converteer(new BrpStapel<>(groepen));

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
        relatieBuilder.anummer(new BrpLong(ANUMMER));
        relatieBuilder.bsn(new BrpInteger(BSN));
        relatieBuilder.voornamen(new BrpString(VOORNAMEN));
        relatieBuilder.voorvoegsel(new BrpString(VOORVOEGSEL));
        relatieBuilder.scheidingsteken(new BrpCharacter(SCHEIDINGSTEKEN));
        relatieBuilder.geslachtsnaamstam(new BrpString(GESLACHTSNAAMSTAM));
        relatieBuilder.datumGeboorte(new BrpInteger(DATUM_GEBOORTE));
        relatieBuilder.gemeenteCodeGeboorte(new BrpGemeenteCode(BRP_GEMEENTE_CODE));
        relatieBuilder.landOfGebiedGeboorte(new BrpLandOfGebiedCode(BRP_LAND_OF_GEBIED_CODE_NL));
        relatieBuilder.geslachtsaanduidingCode(BrpGeslachtsaanduidingCode.MAN);
        relatieBuilder.rubriek6210DatumIngangFamilierechtelijkeBetrekking(new BrpInteger(DATUM_GEBOORTE));

        final BrpIstHuwelijkOfGpGroepInhoud.Builder builder = new BrpIstHuwelijkOfGpGroepInhoud.Builder(standaardGegevens, relatieBuilder.build());
        if (sluiting) {
            builder.datumAanvang(new BrpInteger(DATUM_SLUITING));
            builder.gemeenteCodeAanvang(new BrpGemeenteCode(BRP_GEMEENTE_CODE));
            builder.landOfGebiedAanvang(new BrpLandOfGebiedCode(BRP_LAND_OF_GEBIED_CODE_NL));
        } else {
            builder.datumEinde(new BrpInteger(DATUM_SLUITING));
            builder.gemeenteCodeEinde(new BrpGemeenteCode(BRP_GEMEENTE_CODE));
            builder.landOfGebiedEinde(new BrpLandOfGebiedCode(BRP_LAND_OF_GEBIED_CODE_NL));
            builder.redenBeeindigingRelatieCode(new BrpRedenEindeRelatieCode(REDEN_EINDE));
        }
        builder.soortRelatieCode(BrpSoortRelatieCode.HUWELIJK);

        return builder;
    }

    public void testNullStapels() {
        Assert.assertNull(subject.converteer((List<BrpStapel<BrpIstHuwelijkOfGpGroepInhoud>>) null));
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

        final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> lo3Stapels = subject.converteer(stapels);
        Assert.assertNotNull(lo3Stapels);
        Assert.assertEquals(lo3Stapels.size(), 2);

        int stapelNr = 0;
        for (final Lo3Stapel<Lo3HuwelijkOfGpInhoud> lo3Stapel : lo3Stapels) {
            expectedHerkomst = maakHerkomst(Lo3CategorieEnum.CATEGORIE_05, stapelNr);
            controleerStapel(lo3Stapel, expectedOntbinding, expectedAkte, null, expectedHistorie, expectedHerkomst);
            stapelNr++;
        }
    }
}

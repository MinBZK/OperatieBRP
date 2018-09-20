/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen;

import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper.lo3Documentatie;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper.lo3His;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper.lo3Kind;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLong;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstRelatieGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstStandaardGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3KindInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BrpIstKindConverteerderTest extends AbstractBrpIstConverteerderTest<BrpIstRelatieGroepInhoud> {

    @Inject
    private BrpIstKindConverteerder subject;
    private Lo3Documentatie expectedAkte;
    private Lo3Historie expectedHistorie;
    private Lo3Herkomst expectedHerkomst;
    private Lo3KindInhoud expectedInhoud;

    @Before
    public void setUp() {
        expectedAkte = lo3Documentatie(0L, LO3_GEMEENTE_CODE, AKTENUMMER, null, null, null);
        expectedHistorie = lo3His(null, DATUM_GELDIGHEID, DATUM_OPNEMING);
        expectedHerkomst = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_09, 0, 0);
        expectedInhoud = lo3Kind(ANUMMER, BSN, VOORNAMEN, null, VOORVOEGSEL, GESLACHTSNAAMSTAM, DATUM_GEBOORTE, LO3_GEMEENTE_CODE, LO3_LAND_CODE);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.AbstractBrpIstConverteerderTest#getTestSubject()
     */
    @Override
    protected BrpIstKindConverteerder getTestSubject() {
        return subject;
    }

    @Test
    public void testHappyFlow() {
        final List<BrpGroep<BrpIstRelatieGroepInhoud>> groepen = new ArrayList<>();
        final BrpIstRelatieGroepInhoud inhoud = maakKindGroepInhoud().build();

        groepen.add(maakGroep(inhoud));
        final Lo3Stapel<Lo3KindInhoud> lo3Stapel = subject.converteer(new BrpStapel<>(groepen));

        controleerStapel(lo3Stapel, expectedInhoud, expectedAkte, null, expectedHistorie, expectedHerkomst);
    }

    @Test
    public void testGeenGeboortedatum() {
        final List<BrpGroep<BrpIstRelatieGroepInhoud>> groepen = new ArrayList<>();
        final BrpIstRelatieGroepInhoud.Builder builder = maakKindGroepInhoud();
        builder.datumGeboorte(null);

        final BrpIstRelatieGroepInhoud inhoud = builder.build();
        groepen.add(maakGroep(inhoud));
        final Lo3Stapel<Lo3KindInhoud> lo3Stapel = subject.converteer(new BrpStapel<>(groepen));

        expectedInhoud = lo3Kind(ANUMMER, BSN, VOORNAMEN, null, VOORVOEGSEL, GESLACHTSNAAMSTAM, null, LO3_GEMEENTE_CODE, LO3_LAND_CODE);
        controleerStapel(lo3Stapel, expectedInhoud, expectedAkte, null, expectedHistorie, expectedHerkomst);
    }

    @Test
    public void testNullStapels() {
        Assert.assertNull(subject.converteer((List<BrpStapel<BrpIstRelatieGroepInhoud>>) null));
    }

    @Test
    public void testMeerdereStapels() {
        final List<BrpGroep<BrpIstRelatieGroepInhoud>> groepen1 = new ArrayList<>();
        groepen1.add(maakGroep(maakKindGroepInhoud().build()));

        final List<BrpGroep<BrpIstRelatieGroepInhoud>> groepen2 = new ArrayList<>();
        groepen2.add(maakGroep(maakKindGroepInhoud(1).build()));

        final List<BrpStapel<BrpIstRelatieGroepInhoud>> stapels = new ArrayList<>();
        stapels.add(new BrpStapel<>(groepen1));
        stapels.add(new BrpStapel<>(groepen2));

        final List<Lo3Stapel<Lo3KindInhoud>> lo3Stapels = subject.converteer(stapels);
        Assert.assertNotNull(lo3Stapels);
        Assert.assertEquals(lo3Stapels.size(), 2);

        int stapelNr = 0;
        for (final Lo3Stapel<Lo3KindInhoud> lo3Stapel : lo3Stapels) {
            expectedHerkomst = maakHerkomst(Lo3CategorieEnum.CATEGORIE_09, stapelNr);
            controleerStapel(lo3Stapel, expectedInhoud, expectedAkte, null, expectedHistorie, expectedHerkomst);
            stapelNr++;
        }
    }

    private BrpIstRelatieGroepInhoud.Builder maakKindGroepInhoud() {
        return maakKindGroepInhoud(0);
    }

    private BrpIstRelatieGroepInhoud.Builder maakKindGroepInhoud(final int stapelnummer) {
        final BrpIstStandaardGroepInhoud.Builder standaardBuilder = new BrpIstStandaardGroepInhoud.Builder(Lo3CategorieEnum.CATEGORIE_09, stapelnummer, 0);
        standaardBuilder.soortDocument(BRP_AKTE_DOCUMENT);
        standaardBuilder.partij(new BrpPartijCode(BRP_PARTIJ_CODE));
        standaardBuilder.rubriek8510IngangsdatumGeldigheid(new BrpInteger(DATUM_GELDIGHEID, null));
        standaardBuilder.rubriek8610DatumVanOpneming(new BrpInteger(DATUM_OPNEMING, null));
        standaardBuilder.aktenummer(new BrpString(AKTENUMMER, null));

        final BrpIstRelatieGroepInhoud.Builder builder = new BrpIstRelatieGroepInhoud.Builder(standaardBuilder.build());
        builder.anummer(new BrpLong(ANUMMER, null));
        builder.bsn(new BrpInteger(BSN, null));
        builder.voornamen(new BrpString(VOORNAMEN, null));
        builder.voorvoegsel(new BrpString(VOORVOEGSEL, null));
        builder.scheidingsteken(new BrpCharacter(SCHEIDINGSTEKEN, null));
        builder.geslachtsnaamstam(new BrpString(GESLACHTSNAAMSTAM, null));
        builder.datumGeboorte(new BrpInteger(DATUM_GEBOORTE));
        builder.gemeenteCodeGeboorte(new BrpGemeenteCode(BRP_GEMEENTE_CODE));
        builder.landOfGebiedGeboorte(new BrpLandOfGebiedCode(BRP_LAND_OF_GEBIED_CODE_NL));
        builder.rubriek6210DatumIngangFamilierechtelijkeBetrekking(new BrpInteger(DATUM_GEBOORTE));

        return builder;
    }
}

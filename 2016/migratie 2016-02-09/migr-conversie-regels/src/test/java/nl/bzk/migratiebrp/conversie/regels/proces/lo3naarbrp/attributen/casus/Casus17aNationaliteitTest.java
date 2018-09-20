/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.casus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerkrijgingNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerliesNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.conversie.model.testutils.StapelUtils;

import org.junit.Test;

public class Casus17aNationaliteitTest extends AbstractCasusTest {

    private static final BrpRedenVerkrijgingNederlandschapCode REDEN_VERKRIJGING_NEDERLANDSCHAP_CODE =
            new BrpRedenVerkrijgingNederlandschapCode(Short.parseShort("001"));

    private static final BrpNationaliteitCode NATIONALITEIT_CODE = new BrpNationaliteitCode(Short.parseShort("0001"));

    private final List<Lo3Categorie<Lo3NationaliteitInhoud>> categorieen = new ArrayList<>();

    private final BrpNationaliteitInhoud brpNederlands033 =
            new BrpNationaliteitInhoud(
                NATIONALITEIT_CODE,
                REDEN_VERKRIJGING_NEDERLANDSCHAP_CODE,
                new BrpRedenVerliesNederlandschapCode(Short.parseShort("033")),
                null,
                null,
                null,
                null);
    private final BrpNationaliteitInhoud brpNederlands034 =
            new BrpNationaliteitInhoud(
                NATIONALITEIT_CODE,
                REDEN_VERKRIJGING_NEDERLANDSCHAP_CODE,
                new BrpRedenVerliesNederlandschapCode(Short.parseShort("034")),
                null,
                null,
                null,
                null);
    private final BrpNationaliteitInhoud brpNederlands035 =
            new BrpNationaliteitInhoud(
                NATIONALITEIT_CODE,
                REDEN_VERKRIJGING_NEDERLANDSCHAP_CODE,
                new BrpRedenVerliesNederlandschapCode(Short.parseShort("035")),
                null,
                null,
                null,
                null);

    private final Lo3NationaliteitInhoud lo3Nederlands =
            new Lo3NationaliteitInhoud(new Lo3NationaliteitCode("0001"), new Lo3RedenNederlandschapCode("001"), null, null);
    private final Lo3NationaliteitInhoud lo3Leeg033 = new Lo3NationaliteitInhoud(null, null, new Lo3RedenNederlandschapCode("033"), null);
    private final Lo3NationaliteitInhoud lo3Leeg034 = new Lo3NationaliteitInhoud(null, null, new Lo3RedenNederlandschapCode("034"), null);
    private final Lo3NationaliteitInhoud lo3Leeg035 = new Lo3NationaliteitInhoud(null, null, new Lo3RedenNederlandschapCode("035"), null);
    private final BrpTestObject<BrpNationaliteitInhoud> brp1 = new BrpTestObject<>();
    private final BrpTestObject<BrpNationaliteitInhoud> brp2 = new BrpTestObject<>();
    private final BrpTestObject<BrpNationaliteitInhoud> brp3 = new BrpTestObject<>();

    {
        final Lo3TestObject<Lo3NationaliteitInhoud> lo1 = new Lo3TestObject<>(Document.AKTE);
        final Lo3TestObject<Lo3NationaliteitInhoud> lo2 = new Lo3TestObject<>(Document.AKTE);
        final Lo3TestObject<Lo3NationaliteitInhoud> lo3 = new Lo3TestObject<>(Document.AKTE);
        final Lo3TestObject<Lo3NationaliteitInhoud> lo4 = new Lo3TestObject<>(Document.AKTE);

        final BrpActie actie1 = buildBrpActie(BrpDatumTijd.fromDatum(19900102, null), "1A");
        final BrpActie actie2 = buildBrpActie(BrpDatumTijd.fromDatum(19950102, null), "2A", BrpSoortActieCode.CONVERSIE_GBA_LEEG_CATEGORIE_ONJUIST);
        final BrpActie actie3 = buildBrpActie(BrpDatumTijd.fromDatum(19950111, null), "3A", BrpSoortActieCode.CONVERSIE_GBA_LEEG_CATEGORIE_ONJUIST);
        final BrpActie actie4 = buildBrpActie(BrpDatumTijd.fromDatum(19950113, null), "4A");

        // @formatter:off
        // LO3 input
        lo4.vul(lo3Leeg035, null, 19941231, 19950113, 4, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_04, 0, 0));
        lo3.vul(lo3Leeg034, ONJUIST, 19950110, 19950111, 3, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_54, 0, 1));
        lo2.vul(lo3Leeg033, ONJUIST, 19950101, 19950102, 2, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_54, 0, 2));
        lo1.vul(lo3Nederlands, null, 19900101, 19900102, 1, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_54, 0, 3));

        // verwachte BRP output
        brp1.vul(brpNederlands035, 19900101, 19941231, 19900102010000L, null, null, actie1, actie4, null);
        brp2.vul(brpNederlands033, 19900101, 19950101, 19900102010100L, 19900102010100L, 'O', actie1, actie2, actie1);
        brp3.vul(brpNederlands034, 19900101, 19950110, 19900102010200L, 19900102010200L, 'O', actie1, actie3, actie1);
        // @formatter:on

        vulCategorieen(lo1, lo2, lo3, lo4);
    }

    @Override
    protected Lo3Stapel<Lo3NationaliteitInhoud> maakNationaliteitStapel() {
        return StapelUtils.createStapel(categorieen);
    }

    private void vulCategorieen(final Lo3TestObject<Lo3NationaliteitInhoud>... testObjecten) {
        for (final Lo3TestObject<Lo3NationaliteitInhoud> lo : testObjecten) {
            categorieen.add(new Lo3Categorie<>(lo.getInhoud(), lo.getAkte(), lo.getHistorie(), lo.getLo3Herkomst()));
        }
    }

    @Override
    @Test
    public void testLo3NaarBrp() {
        final Lo3Persoonslijst lo3Persoonslijst = getLo3Persoonslijst();
        final BrpPersoonslijst brpPersoonslijst = converteerLo3NaarBrpService.converteerLo3Persoonslijst(lo3Persoonslijst);
        assertNotNull(brpPersoonslijst);
        final List<BrpStapel<BrpNationaliteitInhoud>> nationaliteitStapels = brpPersoonslijst.getNationaliteitStapels();
        assertEquals(1, nationaliteitStapels.size());
        final BrpStapel<BrpNationaliteitInhoud> nationaliteitStapel = nationaliteitStapels.get(0);
        sorteerBrpStapel(nationaliteitStapel);
        assertEquals(3, nationaliteitStapel.size());

        assertNationaliteit(nationaliteitStapel.get(0), brp1);
        assertNationaliteit(nationaliteitStapel.get(1), brp2);
        assertNationaliteit(nationaliteitStapel.get(2), brp3);
    }

    @Override
    @Test
    public void testRondverteer() {
        final BrpPersoonslijst brpPersoonslijst = converteerLo3NaarBrpService.converteerLo3Persoonslijst(getLo3Persoonslijst());
        final Lo3Persoonslijst terug = converteerBrpNaarLo3Service.converteerBrpPersoonslijst(brpPersoonslijst);
        final List<Lo3Stapel<Lo3NationaliteitInhoud>> rondverteerdeStapels = terug.getNationaliteitStapels();
        final List<Lo3Stapel<Lo3NationaliteitInhoud>> origineleStapels = getLo3Persoonslijst().getNationaliteitStapels();
        assertEquals(1, rondverteerdeStapels.size());
        assertEquals(origineleStapels.get(0).size(), rondverteerdeStapels.get(0).size());
        Lo3StapelHelper.vergelijk(origineleStapels, rondverteerdeStapels);
    }

}

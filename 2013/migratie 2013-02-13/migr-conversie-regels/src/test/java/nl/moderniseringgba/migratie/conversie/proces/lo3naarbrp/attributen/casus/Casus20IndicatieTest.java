/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.attributen.casus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpActie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBehandeldAlsNederlanderIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3AanduidingBijzonderNederlandschapEnum;
import nl.moderniseringgba.migratie.testutils.StapelUtils;

import org.junit.Test;

@SuppressWarnings("unchecked")
public class Casus20IndicatieTest extends CasusTest {

    private final List<Lo3Categorie<Lo3NationaliteitInhoud>> categorieen =
            new ArrayList<Lo3Categorie<Lo3NationaliteitInhoud>>();

    private final Lo3NationaliteitInhoud lo3Leeg = new Lo3NationaliteitInhoud(null, null, null, null);
    private final Lo3NationaliteitInhoud lo3BijzonderNl = new Lo3NationaliteitInhoud(null, null, null,
            Lo3AanduidingBijzonderNederlandschapEnum.BEHANDELD_ALS_NEDERLANDER.asElement());
    private final BrpBehandeldAlsNederlanderIndicatieInhoud brpBehandeldAlsNl =
            new BrpBehandeldAlsNederlanderIndicatieInhoud(true);
    private final BrpTestObject<BrpBehandeldAlsNederlanderIndicatieInhoud> brp1 =
            new BrpTestObject<BrpBehandeldAlsNederlanderIndicatieInhoud>();
    private final BrpTestObject<BrpBehandeldAlsNederlanderIndicatieInhoud> brp2 =
            new BrpTestObject<BrpBehandeldAlsNederlanderIndicatieInhoud>();
    private final BrpTestObject<BrpBehandeldAlsNederlanderIndicatieInhoud> brp3 =
            new BrpTestObject<BrpBehandeldAlsNederlanderIndicatieInhoud>();

    {
        final Lo3TestObject<Lo3NationaliteitInhoud> lo1 = new Lo3TestObject<Lo3NationaliteitInhoud>(Document.AKTE);
        final Lo3TestObject<Lo3NationaliteitInhoud> lo2 = new Lo3TestObject<Lo3NationaliteitInhoud>(Document.AKTE);
        final Lo3TestObject<Lo3NationaliteitInhoud> lo3 = new Lo3TestObject<Lo3NationaliteitInhoud>(Document.AKTE);

        final BrpActie actie3 = buildBrpActie(BrpDatumTijd.fromDatum(19950103), "A3");
        final BrpActie actie2 = buildBrpActie(BrpDatumTijd.fromDatum(19950102), "A2");
        final BrpActie actie1 = buildBrpActie(BrpDatumTijd.fromDatum(19900102), "A1");

        // @formatter:off
        // B           1-1-1995    3-1-1995    <A3>
        // <leeg>  O   1-1-1995    2-1-1995    <A2>
        // B           1-1-1990    2-1-1990    <A1>


        // LO3 input
        lo3.vul(lo3BijzonderNl, null,    19950101, 19950103, 3, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_04, 0, 0));
        lo2.vul(lo3Leeg,       ONJUIST, 19950101, 19950102, 2, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_04, 0, 1));
        lo1.vul(lo3BijzonderNl, null,    19900101, 19900102, 1, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_04, 0, 2));

        // Ja  1-1-1990    1-1-1995    2-1-1990    2-1-1990    A1  A2  A1
        // Ja  1-1-1990    1-1-1995    2-1-1990    xx-xx-xxxx  A1  x  X
        // Ja  1-1-1995    xx-xx-xxxx  3-1-1995    xx-xx-xxxx  A3  x   X


        // verwachte BRP output
        brp1.vul(brpBehandeldAlsNl, 19900101, 19950101, 19900102000100L, 19900102000100L, actie1, actie2, actie1);
        brp2.vul(brpBehandeldAlsNl, 19900101, 19950101, 19900102000000L, null,            actie1, null,   null);
        brp3.vul(brpBehandeldAlsNl, 19950101, null,     19950103000000L, null,            actie3, null,   null);
        // @formatter:on

        vulCategorieen(lo1, lo2, lo3);

    }

    @Override
    protected Lo3Stapel<Lo3NationaliteitInhoud> maakNationaliteitStapel() {
        return StapelUtils.createStapel(categorieen);
    }

    private void vulCategorieen(final Lo3TestObject<Lo3NationaliteitInhoud>... testObjecten) {
        for (final Lo3TestObject<Lo3NationaliteitInhoud> lo : testObjecten) {
            categorieen.add(new Lo3Categorie<Lo3NationaliteitInhoud>(lo.getInhoud(), lo.getAkte(), lo.getHistorie(),
                    lo.getLo3Herkomst()));
        }
    }

    @Override
    @Test
    public void testLo3NaarBrp() throws Exception {
        final Lo3Persoonslijst lo3Persoonslijst = getLo3Persoonslijst();
        final BrpPersoonslijst brpPersoonslijst = conversieService.converteerLo3Persoonslijst(lo3Persoonslijst);
        assertNotNull(brpPersoonslijst);
        final BrpStapel<BrpBehandeldAlsNederlanderIndicatieInhoud> indicatieStapel =
                brpPersoonslijst.getBehandeldAlsNederlanderIndicatieStapel();

        System.out.println("\n\n\nBehandeld als Nederlander stapel:\n" + indicatieStapel);
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");

        assertEquals(3, indicatieStapel.size());

        System.out.println(indicatieStapel.get(0));
        System.out.println(indicatieStapel.get(1));
        System.out.println(indicatieStapel.get(2));
        // aanvang=1990
        assertBehandeldAlsNederlander(indicatieStapel.get(0), brp2);
        // aanvang=1990, vervallen
        assertBehandeldAlsNederlander(indicatieStapel.get(1), brp1);
        // aanvang=1995
        assertBehandeldAlsNederlander(indicatieStapel.get(2), brp3);
    }

    @Override
    @Test
    public void testRondverteer() throws Exception {
        final BrpPersoonslijst brpPersoonslijst = conversieService.converteerLo3Persoonslijst(getLo3Persoonslijst());
        final Lo3Persoonslijst terug = conversieService.converteerBrpPersoonslijst(brpPersoonslijst);
        final List<Lo3Stapel<Lo3NationaliteitInhoud>> rondverteerdeStapels = terug.getNationaliteitStapels();
        final List<Lo3Stapel<Lo3NationaliteitInhoud>> origineleStapels =
                getLo3Persoonslijst().getNationaliteitStapels();
        assertEquals(1, rondverteerdeStapels.size());
        assertEquals(origineleStapels.get(0).size(), rondverteerdeStapels.get(0).size());
        assertEquals(origineleStapels, rondverteerdeStapels);
    }
}

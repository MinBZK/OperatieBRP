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
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVoornaamInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.conversie.model.testutils.StapelUtils;
import org.junit.Test;

public class Casus11NaamTest extends AbstractCasusTest {
    private final List<Lo3Categorie<Lo3PersoonInhoud>> categorieen = new ArrayList<>();

    private final BrpVoornaamInhoud brpPiet = new BrpVoornaamInhoud(new BrpString("Piet"), new BrpInteger(1));
    private final BrpVoornaamInhoud brpJan = new BrpVoornaamInhoud(new BrpString("Jan"), new BrpInteger(1));
    private final BrpVoornaamInhoud brpKlaas = new BrpVoornaamInhoud(new BrpString("Klaas"), new BrpInteger(1));
    private final Lo3PersoonInhoud lo3Klaas = buildPersoonMetVoornaam("Klaas");
    private final Lo3PersoonInhoud lo3Piet = buildPersoonMetVoornaam("Piet");
    private final Lo3PersoonInhoud lo3Jan = buildPersoonMetVoornaam("Jan");
    private final BrpTestObject<BrpVoornaamInhoud> brpVoornaamPiet = new BrpTestObject<>();
    private final BrpTestObject<BrpVoornaamInhoud> brpVoornaamJan = new BrpTestObject<>();
    private final BrpTestObject<BrpVoornaamInhoud> brpVoornaamKlaas = new BrpTestObject<>();

    {
        final Lo3TestObject<Lo3PersoonInhoud> lo1 = new Lo3TestObject<>(Document.AKTE);
        final Lo3TestObject<Lo3PersoonInhoud> lo2 = new Lo3TestObject<>(Document.AKTE);
        final Lo3TestObject<Lo3PersoonInhoud> lo3 = new Lo3TestObject<>(Document.AKTE);

        final BrpActie actie1 = buildBrpActie(BrpDatumTijd.fromDatum(19900102, null), "1A");
        final BrpActie actie2 = buildBrpActie(BrpDatumTijd.fromDatum(19940206, null), "2A");
        final BrpActie actie3 = buildBrpActie(BrpDatumTijd.fromDatum(19950102, null), "3A");

        // @formatter:off
        // Piet 00-02-1994 2-1-1995 <A3>
        // Klaas 05-02-1994 6-2-1994 <A2>
        // Jan 1-1-1990 2-1-1990 <A1>

        // LO3 input
        lo3.vul(lo3Klaas, null, 19940205, 19940206, 2, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0));
        lo2.vul(lo3Piet, null, 19940200, 19950102, 3, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_51, 0, 1));
        lo1.vul(lo3Jan, null, 19900101, 19900102, 1, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_51, 0, 2));

        // Piet 00-2-1994 5-2-1994 2-1-1995 x <A3>
        // Klaas 5-2-1994 xxxxxx 6-2-1994 x <A2>
        // Jan 1-1-1990 00-2-1994 2-1-1990 x <A1>

        // verwachte BRP output
        brpVoornaamJan.vul(brpJan, 19900101, 19940200, 19900102010000L, null, null, actie1, actie3, null);
        brpVoornaamPiet.vul(brpPiet, 19940200, 19940205, 19950102010000L, null, null, actie3, actie2, null);
        brpVoornaamKlaas.vul(brpKlaas, 19940205, null, 19940206010000L, null, null, actie2, null, null);
        // @formatter:on

        vulCategorieen(lo1, lo2, lo3);
    }

    @Override
    protected Lo3Stapel<Lo3PersoonInhoud> maakPersoonStapel() {
        return StapelUtils.createStapel(categorieen);
    }

    private void vulCategorieen(final Lo3TestObject<Lo3PersoonInhoud>... testObjecten) {
        for (final Lo3TestObject<Lo3PersoonInhoud> lo : testObjecten) {
            categorieen.add(new Lo3Categorie<>(lo.getInhoud(), lo.getAkte(), lo.getHistorie(), lo.getLo3Herkomst()));
        }
    }

    @Override
    @Test
    public void testLo3NaarBrp() {
        final Lo3Persoonslijst lo3Persoonslijst = getLo3Persoonslijst();
        final BrpPersoonslijst brpPersoonslijst = converteerLo3NaarBrpService.converteerLo3Persoonslijst(lo3Persoonslijst);
        assertNotNull(brpPersoonslijst);
        final List<BrpStapel<BrpVoornaamInhoud>> voornaamStapels = brpPersoonslijst.getVoornaamStapels();
        assertEquals(1, voornaamStapels.size());
        final BrpStapel<BrpVoornaamInhoud> voornaamStapel = voornaamStapels.get(0);
        sorteerBrpStapel(voornaamStapel);
        assertEquals(3, voornaamStapel.size());

        assertVoornaam(voornaamStapel.get(0), brpVoornaamJan);
        assertVoornaam(voornaamStapel.get(1), brpVoornaamKlaas);
        assertVoornaam(voornaamStapel.get(2), brpVoornaamPiet);

    }

    @Override
    @Test
    public void testRondverteer() {
        final BrpPersoonslijst brpPersoonslijst = converteerLo3NaarBrpService.converteerLo3Persoonslijst(getLo3Persoonslijst());
        final Lo3Persoonslijst terug = converteerBrpNaarLo3Service.converteerBrpPersoonslijst(brpPersoonslijst);
        final Lo3Stapel<Lo3PersoonInhoud> rondverteerdeStapel = terug.getPersoonStapel();
        final Lo3Stapel<Lo3PersoonInhoud> origineleStapel = getLo3Persoonslijst().getPersoonStapel();
        assertEquals(origineleStapel.size(), rondverteerdeStapel.size());
        Lo3StapelHelper.vergelijk(origineleStapel, rondverteerdeStapel);
    }

}

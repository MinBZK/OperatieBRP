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

public class Casus10NaamTest extends AbstractCasusTest {
    private final List<Lo3Categorie<Lo3PersoonInhoud>> categorieen = new ArrayList<>();

    private final BrpVoornaamInhoud brpPiet = new BrpVoornaamInhoud(new BrpString("Piet"), new BrpInteger(1));
    private final BrpVoornaamInhoud brpJan = new BrpVoornaamInhoud(new BrpString("Jan"), new BrpInteger(1));
    private final BrpVoornaamInhoud brpKees = new BrpVoornaamInhoud(new BrpString("Kees"), new BrpInteger(1));
    private final Lo3PersoonInhoud lo3Kees = buildPersoonMetVoornaam("Kees");
    private final Lo3PersoonInhoud lo3Piet = buildPersoonMetVoornaam("Piet");
    private final Lo3PersoonInhoud lo3Jan = buildPersoonMetVoornaam("Jan");
    private final BrpTestObject<BrpVoornaamInhoud> brpVoornaamPiet = new BrpTestObject<>();
    private final BrpTestObject<BrpVoornaamInhoud> brpVoornaamJan = new BrpTestObject<>();
    private final BrpTestObject<BrpVoornaamInhoud> brpVoornaamKees = new BrpTestObject<>();

    {
        final Lo3TestObject<Lo3PersoonInhoud> lo1 = new Lo3TestObject<>(Document.AKTE);
        final Lo3TestObject<Lo3PersoonInhoud> lo2 = new Lo3TestObject<>(Document.AKTE);
        final Lo3TestObject<Lo3PersoonInhoud> lo3 = new Lo3TestObject<>(Document.AKTE);

        final BrpActie actie1 = buildBrpActie(BrpDatumTijd.fromDatum(19920508, null), "1A");
        final BrpActie actie2 = buildBrpActie(BrpDatumTijd.fromDatum(19950202, null), "2A");
        final BrpActie actie3 = buildBrpActie(BrpDatumTijd.fromDatum(19960202, null), "3A");

        // @formatter:off
        // LO3 input
        lo3.vul(lo3Kees, null, 19950201, 19950202, 2, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0));
        lo2.vul(lo3Jan, null, 19920301, 19960202, 3, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_51, 0, 1));
        lo1.vul(lo3Piet, ONJUIST, 19920401, 19920508, 1, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_51, 0, 2));

        // verwachte BRP output
        brpVoornaamJan.vul(brpJan, 19920301, 19950201, 19960202010000L, null, null, actie3, actie2, null);
        brpVoornaamPiet.vul(brpPiet, 19920401, null, 19920508010000L, 19920508010000L, 'O', actie1, null, actie1);
        brpVoornaamKees.vul(brpKees, 19950201, null, 19950202010000L, null, null, actie2, null, null);
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
        // LO3 naar BRP
        final Lo3Persoonslijst lo3Persoonslijst = getLo3Persoonslijst();
        final BrpPersoonslijst brpPersoonslijst = converteerLo3NaarBrpService.converteerLo3Persoonslijst(lo3Persoonslijst);
        assertNotNull(brpPersoonslijst);
        final List<BrpStapel<BrpVoornaamInhoud>> voornaamStapels = brpPersoonslijst.getVoornaamStapels();
        assertEquals(1, voornaamStapels.size());
        final BrpStapel<BrpVoornaamInhoud> voornaamStapel = voornaamStapels.get(0);
        sorteerBrpStapel(voornaamStapel);
        assertEquals(3, voornaamStapel.size());

        assertVoornaam(voornaamStapel.get(0), brpVoornaamPiet);
        assertVoornaam(voornaamStapel.get(1), brpVoornaamKees);
        assertVoornaam(voornaamStapel.get(2), brpVoornaamJan);
    }

    @Override
    @Test
    public void testRondverteer() {
        final BrpPersoonslijst brpPersoonslijst = converteerLo3NaarBrpService.converteerLo3Persoonslijst(getLo3Persoonslijst());
        final Lo3Persoonslijst terug = converteerBrpNaarLo3Service.converteerBrpPersoonslijst(brpPersoonslijst);
        final Lo3Stapel<Lo3PersoonInhoud> rondverteerdeStapel = terug.getPersoonStapel();
        final Lo3Stapel<Lo3PersoonInhoud> origineleStapel = getLo3Persoonslijst().getPersoonStapel();
        Lo3StapelHelper.vergelijk(origineleStapel, rondverteerdeStapel);
    }

}

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
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVoornaamInhoud;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.Lo3StapelHelper;
import nl.moderniseringgba.migratie.testutils.StapelUtils;

import org.junit.Test;

@SuppressWarnings("unchecked")
public class Casus32NaamTest extends CasusTest {
    private final List<Lo3Categorie<Lo3PersoonInhoud>> categorieen = new ArrayList<Lo3Categorie<Lo3PersoonInhoud>>();

    private final BrpVoornaamInhoud brpPiet = new BrpVoornaamInhoud("Piet", 1);
    private final BrpVoornaamInhoud brpJan = new BrpVoornaamInhoud("Jan", 1);
    private final BrpVoornaamInhoud brpKees = new BrpVoornaamInhoud("Kees", 1);
    private final BrpVoornaamInhoud brpCees = new BrpVoornaamInhoud("Cees", 1);
    private final Lo3PersoonInhoud lo3Kees = buildPersoonMetVoornaam("Kees");
    private final Lo3PersoonInhoud lo3Piet = buildPersoonMetVoornaam("Piet");
    private final Lo3PersoonInhoud lo3Jan = buildPersoonMetVoornaam("Jan");
    private final Lo3PersoonInhoud lo3Cees = buildPersoonMetVoornaam("Cees");
    private final BrpTestObject<BrpVoornaamInhoud> brpVoornaamPiet = new BrpTestObject<BrpVoornaamInhoud>();
    private final BrpTestObject<BrpVoornaamInhoud> brpVoornaamJan = new BrpTestObject<BrpVoornaamInhoud>();
    private final BrpTestObject<BrpVoornaamInhoud> brpVoornaamKees = new BrpTestObject<BrpVoornaamInhoud>();
    private final BrpTestObject<BrpVoornaamInhoud> brpVoornaamCees = new BrpTestObject<BrpVoornaamInhoud>();

    {
        final Lo3TestObject<Lo3PersoonInhoud> lo1 = new Lo3TestObject<Lo3PersoonInhoud>(Document.AKTE);
        final Lo3TestObject<Lo3PersoonInhoud> lo2 = new Lo3TestObject<Lo3PersoonInhoud>(Document.AKTE);
        final Lo3TestObject<Lo3PersoonInhoud> lo3 = new Lo3TestObject<Lo3PersoonInhoud>(Document.AKTE);
        final Lo3TestObject<Lo3PersoonInhoud> lo4 = new Lo3TestObject<Lo3PersoonInhoud>(Document.AKTE);

        final BrpActie actie4 = buildBrpActie(BrpDatumTijd.fromDatum(19970103), "A4");
        final BrpActie actie3 = buildBrpActie(BrpDatumTijd.fromDatum(19970102), "A3");
        final BrpActie actie2 = buildBrpActie(BrpDatumTijd.fromDatum(19960102), "A2");
        final BrpActie actie1 = buildBrpActie(BrpDatumTijd.fromDatum(19900102), "A1");

        // @formatter:off
        // LO3 input

        // Cees        1-1-1995    3-1-1997    <A4>
        // Kees    O   1-1-1995    2-1-1997    <A3>
        // Piet        1-1-1996    2-1-1996    <A2>
        // Jan         1-1-1990    2-1-1990    <A1>

        lo4.vul(lo3Cees, null,    19950101, 19970103, 4, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0));
        lo3.vul(lo3Kees, ONJUIST, 19950101, 19970102, 3, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 1));
        lo2.vul(lo3Piet, null,    19960101, 19960102, 2, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 2));
        lo1.vul(lo3Jan,  null,    19900101, 19900102, 1, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 3));

        // verwachte BRP output

        // Cees    1-1-1995    1-1-1996    3-1-1997    xxxxxxxx    <A4>
        // Kees    1-1-1995    xxxxxxxx    2-1-1997    2-1-1997    <A3>        <A3>
        // Piet    1-1-1996    xxxxxxxx    2-1-1996    xxxxxxxx    <A2>
        // Jan     1-1-1990    1-1-1995    2-1-1990    xxxxxxxx    <A1>

        brpVoornaamCees.vul(brpCees,  19950101, 19960101, 19970103000000L, null,            actie4, null, null);
        brpVoornaamKees.vul(brpKees,  19950101, null,     19970102000000L, 19970102000000L, actie3, null, actie3);
        brpVoornaamPiet.vul(brpPiet,  19960101, null,     19960102000000L, null,            actie2, null, null);
        brpVoornaamJan. vul(brpJan,   19900101, 19950101, 19900102000000L, null,            actie1, null, null);
        // @formatter:on

        vulCategorieen(lo1, lo2, lo3, lo4);
    }

    @Override
    protected Lo3Stapel<Lo3PersoonInhoud> maakPersoonStapel() {
        return StapelUtils.createStapel(categorieen);
    }

    private void vulCategorieen(final Lo3TestObject<Lo3PersoonInhoud>... testObjecten) {
        for (final Lo3TestObject<Lo3PersoonInhoud> lo : testObjecten) {
            categorieen.add(new Lo3Categorie<Lo3PersoonInhoud>(lo.getInhoud(), lo.getAkte(), lo.getHistorie(), lo
                    .getLo3Herkomst()));
        }
    }

    @Override
    @Test
    public void testLo3NaarBrp() throws Exception {
        final Lo3Persoonslijst lo3Persoonslijst = getLo3Persoonslijst();
        final BrpPersoonslijst brpPersoonslijst = conversieService.converteerLo3Persoonslijst(lo3Persoonslijst);
        assertNotNull(brpPersoonslijst);
        final List<BrpStapel<BrpVoornaamInhoud>> voornaamStapels = brpPersoonslijst.getVoornaamStapels();
        assertEquals(1, voornaamStapels.size());
        final BrpStapel<BrpVoornaamInhoud> voornaamStapel = voornaamStapels.get(0);
        sorteerBrpStapel(voornaamStapel);
        assertEquals(4, voornaamStapel.size());

        System.out.println(voornaamStapel.get(0));
        System.out.println(voornaamStapel.get(1));
        System.out.println(voornaamStapel.get(2));
        System.out.println(voornaamStapel.get(3));

        // kees
        assertVoornaam(voornaamStapel.get(0), brpVoornaamKees);
        // jan
        assertVoornaam(voornaamStapel.get(1), brpVoornaamJan);
        // piet
        assertVoornaam(voornaamStapel.get(2), brpVoornaamPiet);
        // cees
        assertVoornaam(voornaamStapel.get(3), brpVoornaamCees);
    }

    @Override
    @Test
    public void testRondverteer() throws Exception {
        final BrpPersoonslijst brpPersoonslijst = conversieService.converteerLo3Persoonslijst(getLo3Persoonslijst());
        final Lo3Persoonslijst terug = conversieService.converteerBrpPersoonslijst(brpPersoonslijst);
        final Lo3Stapel<Lo3PersoonInhoud> rondverteerdeStapel = terug.getPersoonStapel();
        final Lo3Stapel<Lo3PersoonInhoud> origineleStapel = getLo3Persoonslijst().getPersoonStapel();
        assertEquals(origineleStapel.size(), rondverteerdeStapel.size());
        Lo3StapelHelper.vergelijk(origineleStapel, rondverteerdeStapel);
    }

}

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
import nl.moderniseringgba.migratie.conversie.model.brp.BrpRelatie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpRelatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.Lo3StapelHelper;
import nl.moderniseringgba.migratie.testutils.StapelUtils;

import org.junit.Test;

@SuppressWarnings("unchecked")
public class Casus39OmzettenPartnerschapTest extends CasusTest {

    private final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> categorieen =
            new ArrayList<Lo3Categorie<Lo3HuwelijkOfGpInhoud>>();

    private final String rotterdam = "0599";
    private final String denhaag = "0518";

    private final BrpTestObject<BrpRelatieInhoud> brp1 = new BrpTestObject<BrpRelatieInhoud>();
    private final BrpTestObject<BrpRelatieInhoud> brp2 = new BrpTestObject<BrpRelatieInhoud>();
    private final BrpTestObject<BrpRelatieInhoud> brp3 = new BrpTestObject<BrpRelatieInhoud>();

    {
        final Lo3TestObject<Lo3HuwelijkOfGpInhoud> lo1 = new Lo3TestObject<Lo3HuwelijkOfGpInhoud>(Document.AKTE);
        final Lo3TestObject<Lo3HuwelijkOfGpInhoud> lo2 = new Lo3TestObject<Lo3HuwelijkOfGpInhoud>(Document.AKTE);
        final Lo3TestObject<Lo3HuwelijkOfGpInhoud> lo3 = new Lo3TestObject<Lo3HuwelijkOfGpInhoud>(Document.AKTE);
        final Lo3TestObject<Lo3HuwelijkOfGpInhoud> lo4 = new Lo3TestObject<Lo3HuwelijkOfGpInhoud>(Document.AKTE);

        final BrpActie actie4 = buildBrpActie(BrpDatumTijd.fromDatum(19950105), "A4", "PL - Verbintenis stapel 1"); // correctie
        // 1
        final BrpActie actie3 = buildBrpActie(BrpDatumTijd.fromDatum(19950104), "A3", "PL - Verbintenis stapel 1"); // ontbinding
        // 1
        final BrpActie actie2 = buildBrpActie(BrpDatumTijd.fromDatum(19900203), "A2", "PL - Verbintenis stapel 1"); // sluiting
        // 1-b
        final BrpActie actie1 = buildBrpActie(BrpDatumTijd.fromDatum(19900202), "A1", "PL - Verbintenis stapel 1"); // sluiting
        // 1-a

        // @formatter:off

        // xxxxxxxx    -           Xxxxxxxx    -          -   -   1-2-1990    5-1-1995    Rotterdam   A4
        // Xxxxxxxx    -           3-1-1995    Rotterdam  P   O   3-1-1995    4-1-1995    Rotterdam   A3
        // 1-2-1990    Rotterdam   Xxxxxxxx    -          P   O   1-2-1990    3-2-1990    Rotterdam   A2
        // 1-2-1990    Den Haag    xxxxxxxx    -          H   O   1-2-1990    2-2-1990    Rotterdam   A1

        // LO3 input
        lo4.vul(buildHuwelijk(null,     null,      null,      null),         null,    19900201, 19950105, 4, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_05, 0, 0));
        lo3.vul(buildHuwelijk(null,     null,      19950103,  rotterdam, P), ONJUIST, 19950103, 19950104, 3, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_05, 0, 1));
        lo2.vul(buildHuwelijk(19900201, rotterdam, null,      null,      P), ONJUIST, 19900201, 19900203, 2, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_05, 0, 2));
        lo1.vul(buildHuwelijk(19900201, denhaag,   null,      null,      H), ONJUIST, 19900201, 19900202, 1, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_05, 0, 3));

        // verwachte BRP output

        // soort P
        // 1-2-1990 rotterdam   3-1-1995    Rotterdam    4-1-1995 12:00  5-1-1995 12:00  Ontbinding-1  Correctie-1
        // 1-2-1990 rotterdam   xxxxxxxx    -            3-2-1990 12:00  3-2-1990 12:00  Sluiting-1b   Sluiting-1b
        brp1.vulHuwelijk(19900201, rotterdam, 19950103, rotterdam, 19950104000000L, 19950105000000L, actie3, actie4);
        brp2.vulHuwelijk(19900201, rotterdam, null,     null,      19900203000000L, 19950105000000L, actie2, actie4);

        // soort H
        // 1-2-1990 den haag    xxxxxxxx    -            2-2-1990 12:00  2-2-1995  Sluiting-1a   Correctie-1
        brp3.vulHuwelijk(19900201, denhaag,   null,     null,      19900202000000L, 19900202000000L, actie1, actie1);

        // @formatter:on

        vulCategorieen(lo1, lo2, lo3, lo4);
    }

    @Override
    protected Lo3Stapel<Lo3HuwelijkOfGpInhoud> maakHuwelijkStapel() {
        return StapelUtils.createStapel(categorieen);
    }

    private void vulCategorieen(final Lo3TestObject<Lo3HuwelijkOfGpInhoud>... testObjecten) {
        for (final Lo3TestObject<Lo3HuwelijkOfGpInhoud> lo : testObjecten) {
            categorieen.add(new Lo3Categorie<Lo3HuwelijkOfGpInhoud>(lo.getInhoud(), lo.getAkte(), lo.getHistorie(),
                    lo.getLo3Herkomst()));
        }
    }

    @Override
    @Test
    public void testLo3NaarBrp() throws Exception {
        final Lo3Persoonslijst lo3Persoonslijst = getLo3Persoonslijst();
        final BrpPersoonslijst brpPersoonslijst = conversieService.converteerLo3Persoonslijst(lo3Persoonslijst);
        assertNotNull(brpPersoonslijst);

        final List<BrpRelatie> huwelijkRelaties = brpPersoonslijst.getRelaties(BrpSoortRelatieCode.HUWELIJK);
        assertEquals(1, huwelijkRelaties.size());
        final BrpStapel<BrpRelatieInhoud> huwelijkStapel = huwelijkRelaties.get(0).getRelatieStapel();

        sorteerBrpStapel(huwelijkStapel);
        assertEquals(1, huwelijkStapel.size());

        System.out.println(huwelijkStapel.get(0));
        assertHuwelijk(huwelijkStapel.get(0), brp3);

        final List<BrpRelatie> partnerRelaties =
                brpPersoonslijst.getRelaties(BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP);
        assertEquals(1, partnerRelaties.size());
        final BrpStapel<BrpRelatieInhoud> partnerStapel = partnerRelaties.get(0).getRelatieStapel();

        sorteerBrpStapel(partnerStapel);
        assertEquals(2, partnerStapel.size());

        System.out.println(partnerStapel.get(0));
        System.out.println(partnerStapel.get(1));

        assertHuwelijk(partnerStapel.get(0), brp2);
        // beeindigd
        assertHuwelijk(partnerStapel.get(1), brp1);
    }

    @Override
    @Test
    public void testRondverteer() throws Exception {
        final BrpPersoonslijst brpPersoonslijst = conversieService.converteerLo3Persoonslijst(getLo3Persoonslijst());
        final Lo3Persoonslijst terug = conversieService.converteerBrpPersoonslijst(brpPersoonslijst);
        final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> rondverteerdeStapels = terug.getHuwelijkOfGpStapels();
        final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> origineleStapels =
                getLo3Persoonslijst().getHuwelijkOfGpStapels();

        Lo3StapelHelper.vergelijk(origineleStapels, rondverteerdeStapels);
        //
        // assertEquals(1, rondverteerdeStapels.size());
        // assertEquals(4, rondverteerdeStapels.get(0).size());
        // assertEquals(origineleStapels, rondverteerdeStapels);
    }

}

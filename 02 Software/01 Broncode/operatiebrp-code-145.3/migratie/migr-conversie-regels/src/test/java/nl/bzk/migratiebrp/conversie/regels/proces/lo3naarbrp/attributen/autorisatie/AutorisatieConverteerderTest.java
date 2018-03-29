/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.autorisatie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import org.junit.Assert;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Autorisatie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3AutorisatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.tussen.autorisatie.TussenDienstbundel;
import nl.bzk.migratiebrp.conversie.model.tussen.autorisatie.TussenLeveringsautorisatie;
import nl.bzk.migratiebrp.conversie.regels.AbstractComponentTest;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import org.junit.Before;
import org.junit.Test;

public class AutorisatieConverteerderTest extends AbstractComponentTest {

    @Inject
    private AutorisatieConverteerder converteerder;

    @Before
    public void before() {
        Logging.initContext();
    }

    @Test
    public void testConverteerAbonnementen() {

        final Lo3AutorisatieInhoud inhoud = maakAutorisatieInhoud();
        final List<TussenDienstbundel> tussenAbonnementList = converteerder.converteerDienstbundels(maakAutorisatie(inhoud).getAutorisatieStapel().get(0));
        Assert.assertNotNull(tussenAbonnementList);

        // 4 abonnementen (ADHOC, SELECTIE, SPONTAAN)
        Assert.assertEquals(3, tussenAbonnementList.size());

        // for (final TussenDienstbundel abo : tussenAbonnementList) {
        // controleerLeveringsautorisatie(abo);
        // }

    }

    private void controleerLeveringsautorisatie(final TussenLeveringsautorisatie abo) {
        Assert.assertNotNull(abo.getStelsel());
        Assert.assertNotNull(abo.getDienstBundels());
        Assert.assertNotNull(abo.getIndicatieModelautorisatie());
        Assert.assertNotNull(abo.getLeveringsautorisatieStapel());
    }

    @Test
    public void testConverteerAutorisatieBesluit() {
        final Lo3AutorisatieInhoud inhoud = maakAutorisatieInhoud();
        final List<TussenLeveringsautorisatie> tussenLeveringsautorisaties = converteerder.converteerAutorisatie(maakAutorisatie(inhoud));
        for (final TussenLeveringsautorisatie tussenLeveringsautorisatie : tussenLeveringsautorisaties) {
            controleerLeveringsautorisatie(tussenLeveringsautorisatie);

            Assert.assertNotNull(tussenLeveringsautorisatie);
            Assert.assertNotNull(tussenLeveringsautorisatie.getLeveringsautorisatieStapel());
            Assert.assertEquals(
                    inhoud.getAfnemersindicatie().toString(),
                    tussenLeveringsautorisatie.getLeveringsautorisatieStapel().get(0).getInhoud().getNaam());
        }
    }

    @Test
    public void testConverteerAutorisatieBesluitVersie2() {
        final Lo3AutorisatieInhoud inhoud = maakAutorisatieInhoud();
        final Lo3AutorisatieInhoud inhoudVersie2 = maakAutorisatieInhoud();
        final Lo3Autorisatie autorisatie = maakAutorisaties(inhoud, inhoudVersie2);
        final List<TussenLeveringsautorisatie> tussenLeveringsautorisaties = converteerder.converteerAutorisatie(autorisatie);
        int index = 0;
        for (final TussenLeveringsautorisatie tussenLeveringsautorisatie : tussenLeveringsautorisaties) {
            controleerLeveringsautorisatie(tussenLeveringsautorisatie);

            Assert.assertNotNull(tussenLeveringsautorisatie);
            Assert.assertNotNull(tussenLeveringsautorisatie.getLeveringsautorisatieStapel());
            if (index > 0) {
                Assert.assertEquals(
                        inhoud.getAfnemersindicatie().toString()
                                + "(1)",
                        tussenLeveringsautorisatie.getLeveringsautorisatieStapel().get(0).getInhoud().getNaam());
            } else {
                Assert.assertEquals(
                        inhoud.getAfnemersindicatie().toString(),
                        tussenLeveringsautorisatie.getLeveringsautorisatieStapel().get(0).getInhoud().getNaam());
            }
            index++;
        }
    }

    private Lo3AutorisatieInhoud maakAutorisatieInhoud() {
        return AutorisatieConversieHelperTest.maakAutorisatie();
    }

    private Lo3Autorisatie maakAutorisatie(final Lo3AutorisatieInhoud inhoud) {
        final Lo3Historie historie = new Lo3Historie(null, new Lo3Datum(19900101), new Lo3Datum(19900101));
        final Lo3Herkomst herkomst = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_35, 1, -1);

        final Lo3Categorie<Lo3AutorisatieInhoud> categorie = new Lo3Categorie<>(inhoud, null, historie, herkomst);

        return new Lo3Autorisatie(new Lo3Stapel<>(Collections.singletonList(categorie)));
    }

    private Lo3Autorisatie maakAutorisaties(final Lo3AutorisatieInhoud inhoud, final Lo3AutorisatieInhoud inhoudVersie2) {
        final Lo3Historie historie = new Lo3Historie(null, new Lo3Datum(19900101), new Lo3Datum(19910101));
        new Lo3Historie(null, new Lo3Datum(19910101), new Lo3Datum(29900101));
        final Lo3Herkomst herkomst = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_35, 1, -1);

        final Lo3Categorie<Lo3AutorisatieInhoud> categorie = new Lo3Categorie<>(inhoud, null, historie, herkomst);
        final Lo3Categorie<Lo3AutorisatieInhoud> categorieVersie2 = new Lo3Categorie<>(inhoudVersie2, null, historie, herkomst);

        final List<Lo3Categorie<Lo3AutorisatieInhoud>> inhouden = new ArrayList<>();
        inhouden.add(categorie);
        inhouden.add(categorieVersie2);

        return new Lo3Autorisatie(new Lo3Stapel<>(inhouden));
    }

}

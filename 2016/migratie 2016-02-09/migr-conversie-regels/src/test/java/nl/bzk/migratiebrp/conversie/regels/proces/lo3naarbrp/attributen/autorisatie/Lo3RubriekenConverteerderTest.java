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

import junit.framework.Assert;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstbundelLo3RubriekInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3AutorisatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import nl.bzk.migratiebrp.conversie.model.tussen.autorisatie.TussenDienstbundelLo3Rubriek;
import nl.bzk.migratiebrp.conversie.regels.AbstractComponentTest;

import org.junit.Ignore;
import org.junit.Test;

public class Lo3RubriekenConverteerderTest extends AbstractComponentTest {

    @Inject
    private DienstbundelLo3RubriekenConverteerder converteerder;

    @Test
    @Ignore
    // TODO: Test fixen
    public void test() {
        final Lo3Stapel<Lo3AutorisatieInhoud> autorisatieStapel = maakAutorisatieStapel();
        for (final Lo3Categorie<Lo3AutorisatieInhoud> inhoud : autorisatieStapel) {
            System.out.println(inhoud.getHistorie().getIngangsdatumGeldigheid() + " -> " + inhoud.getInhoud().getRubrieknummerSpontaan());
        }

        final List<TussenDienstbundelLo3Rubriek> tussenAbonnementExpressies =
                converteerder.converteerDienstbundelLo3Rubriek(autorisatieStapel.getCategorieen().get(0), autorisatieStapel.getCategorieen()
                    .get(0)
                    .getInhoud()
                    .getRubrieknummerSpontaan());
        Assert.assertNotNull(tussenAbonnementExpressies);

        Assert.assertEquals(4, tussenAbonnementExpressies.size());

        for (final TussenDienstbundelLo3Rubriek rubriek : tussenAbonnementExpressies) {
            final String rubriekNummer = rubriek.getConversieRubriek();

            if ("01.01.10".equals(rubriekNummer)) {
                controleer(rubriek, true, 19900101);
                controleer(rubriek, true, 19910101);
            } else if ("01.01.20".equals(rubriekNummer)) {
                controleer(rubriek, true, 19900101);
                controleer(rubriek, true, 19910101);
            } else if ("01.04.10".equals(rubriekNummer)) {
                controleer(rubriek, true, 19900101);
                controleer(rubriek, true, 19910101);
            } else if ("01.02.10".equals(rubriekNummer)) {
                controleer(rubriek, true, 19900101);
                controleer(rubriek, true, 19910101);
            } else {
                Assert.fail("Onverwachte rubriek gevonden: " + rubriekNummer);
            }

        }
    }

    private void controleer(final TussenDienstbundelLo3Rubriek rubriek, final Boolean actief, final int datum) {
        boolean gevonden = false;
        for (final TussenGroep<BrpDienstbundelLo3RubriekInhoud> groep : rubriek.getDienstbundelLo3RubriekStapel()) {
            if (groep.getHistorie().getIngangsdatumGeldigheid().getIntegerWaarde().equals(datum)) {
                gevonden = true;
                Assert.assertEquals(actief, rubriek.getActief());
            }
        }
        Assert.assertTrue("gewenste rij niet gevonden in tussenabonnementlo3rubriek.stapel", gevonden);
    }

    private Lo3Stapel<Lo3AutorisatieInhoud> maakAutorisatieStapel() {
        final Lo3Historie historie = new Lo3Historie(null, new Lo3Datum(19900101), new Lo3Datum(19900101));

        final Lo3AutorisatieInhoud inhoud = AutorisatieConversieHelperTest.maakAutorisatie();

        // 01.01.10#01.01.20#01.02.10#01.02.20#01.02.30#01.02.40#01.03.10#01.04.10#01.61.10#05.02.30#05.02.40#05.06.10#05.07.10#06.08.10#07.70.10#08.09.10#08.10.20#08.10.30#08.11.10#08.11.15#08.11.20#08.11.30#08.11.40#08.11.50#08.11.60#08.11.70#08.11.80#08.11.90#08.12.10#08.13.20#08.14.20
        // categorie . rubriek . volgnummer # categorie . rubriek . volgnummer
        inhoud.setRubrieknummerSpontaan("01.01.10#01.01.20");
        final Lo3Categorie<Lo3AutorisatieInhoud> categorie = new Lo3Categorie<>(inhoud, null, historie, null);

        final Lo3Historie historie2 = new Lo3Historie(null, new Lo3Datum(19910101), new Lo3Datum(19910101));
        final Lo3AutorisatieInhoud inhoud2 = AutorisatieConversieHelperTest.maakAutorisatie();
        inhoud2.setRubrieknummerSpontaan("01.01.10#01.04.10#01.02.10");
        final Lo3Categorie<Lo3AutorisatieInhoud> categorie2 = new Lo3Categorie<>(inhoud2, null, historie2, null);

        final List<Lo3Categorie<Lo3AutorisatieInhoud>> categorieen = new ArrayList<>();
        categorieen.add(categorie);
        categorieen.add(categorie2);

        return new Lo3Stapel<>(Collections.unmodifiableList(categorieen));
    }

}

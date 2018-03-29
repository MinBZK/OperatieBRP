/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.format;

import java.util.Arrays;
import java.util.List;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.levering.lo3.filter.Ng01BerichtFilter;
import nl.bzk.brp.levering.lo3.filter.VulBerichtFilter;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3PersoonslijstFormatter;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test voor {@link Ng01Formatter}.
 */
@RunWith(MockitoJUnitRunner.class)
public class Ng01FormatterTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final String F = "F";
    private static final String GEBOORTE_GEMEENTE_CODE = "0518";

    private final VulBerichtFilter filter = new VulBerichtFilter();
    private final Ng01BerichtFilter ng01filter = new Ng01BerichtFilter(filter);
    private final Ng01Formatter subject = new Ng01Formatter();

    @Test
    public void test() {
        final List<Lo3CategorieWaarde> categorieen = new Lo3PersoonslijstFormatter().format(buildLo3Persoonslijst());

        final List<Lo3CategorieWaarde> gefilterdeCategorieen =
                ng01filter.filter(
                    null,
                    null,
                    null,
                    null,
                    categorieen,
                    Arrays.asList("01.01.10", "01.02.10", "01.02.20", "01.02.30", "01.02.40", "01.03.10", "01.03.20", "01.03.30", "07.80.10"));

        final String ng01 = subject.maakPlatteTekst(null, null, categorieen, gefilterdeCategorieen);
        LOGGER.info(ng01);

        final StringBuilder verwachteNg01 = new StringBuilder();
        // Header
        verwachteNg01.append("00000000Ng01");
        // Bericht lengte
        verwachteNg01.append("00050");

        // Categorie 01 + lengte (totale lengte: 22)
        verwachteNg01.append("01").append("017");
        // Element 01.10 + lengte + waarde (totale lengte: 17)
        verwachteNg01.append("0110").append("010").append("3450924321");

        // Categorie 07 + lengte (totale: lengte: 28)
        verwachteNg01.append("07").append("023");
        // Element 67.10 + lengte + waarde (totale lengte: 15)
        verwachteNg01.append("6710").append("008").append("20140101");
        // Element 67.20 + lengte + waarde (totale lengte: 8)
        verwachteNg01.append("6720").append("001").append(F);

        Assert.assertEquals(verwachteNg01.toString(), ng01);
    }

    public Lo3Persoonslijst buildLo3Persoonslijst() {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(buildPersoonstapel());
        builder.inschrijvingStapel(buildInschrijvingStapel());

        return builder.build();
    }

    private Lo3Stapel<Lo3PersoonInhoud> buildPersoonstapel() {
        return Lo3StapelHelper.lo3Stapel(
            Lo3StapelHelper.lo3Cat(
                Lo3StapelHelper.lo3Persoon("3450924321", "Scarlet", "Simpspoon", 19770101, GEBOORTE_GEMEENTE_CODE, "6030", "V"),
                Lo3StapelHelper.lo3Akt(1),
                Lo3StapelHelper.lo3His(19770101),
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0)));
    }

    private Lo3Stapel<Lo3InschrijvingInhoud> buildInschrijvingStapel() {
        return Lo3StapelHelper.lo3Stapel(
            Lo3StapelHelper.lo3Cat(
                Lo3StapelHelper.lo3Inschrijving(null, 20140101, F, 19770202, GEBOORTE_GEMEENTE_CODE, 0, 1, 19770102123014000L, true),
                null,
                new Lo3Historie(null, new Lo3Datum(0), new Lo3Datum(0)),
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0)));
    }
}

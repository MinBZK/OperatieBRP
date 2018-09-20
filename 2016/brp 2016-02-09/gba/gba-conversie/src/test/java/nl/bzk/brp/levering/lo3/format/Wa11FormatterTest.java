/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.format;

import java.util.Arrays;
import java.util.List;
import nl.bzk.brp.levering.lo3.filter.VulBerichtFilter;
import nl.bzk.brp.levering.lo3.filter.Wa11BerichtFilter;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3PersoonslijstFormatter;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Test voor {@link Wa11Formatter}.
 */
public class Wa11FormatterTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final VulBerichtFilter filter = new VulBerichtFilter();
    private final Wa11BerichtFilter wa11filter = new Wa11BerichtFilter();
    private final Wa11Formatter subject = new Wa11Formatter();

    @Before
    public void injectDependencies() {
        ReflectionTestUtils.setField(wa11filter, "vulBerichtFilter", filter);
    }

    @Test
    public void test() {
        final List<Lo3CategorieWaarde> categorieen = new Lo3PersoonslijstFormatter().format(buildLo3Persoonslijst());

        final List<Lo3CategorieWaarde> gefilterdeCategorieen =
                wa11filter.filter(
                    null,
                    null,
                    null,
                    categorieen,
                    Arrays.asList("01.01.10", "01.02.10", "01.02.20", "01.02.30", "01.02.40", "01.03.10", "01.03.20", "01.03.30", "07.80.10"));
        LOGGER.info("Gefilterde categorieen: " + gefilterdeCategorieen);

        final String wa11 = subject.maakPlatteTekst(null, categorieen, gefilterdeCategorieen);
        LOGGER.info(wa11);

        final StringBuilder verwachteWa11 = new StringBuilder();
        // Header (bevat *NIEUW* a-nummer)
        verwachteWa11.append("00000000");
        verwachteWa11.append("Wa11");
        verwachteWa11.append("3450924321");
        verwachteWa11.append("19770101");
        // Bericht lengte
        verwachteWa11.append("00089");

        // Categorie 01 + lengte (totale lengte: 89)
        verwachteWa11.append("01").append("084");
        // Element 01.10 + lengte + waarde (totale lengte: 17)
        verwachteWa11.append("0110").append("010").append("9539040545");
        // Element 02.10 + lengte + waarde (totale lengte: 14)
        verwachteWa11.append("0210").append("007").append("Scarlet");
        // Element 02.40 + lengte + waarde (totale lengte: 16)
        verwachteWa11.append("0240").append("009").append("Simpspoon");
        // Element 03.10 + lengte + waarde (totale lengte: 15)
        verwachteWa11.append("0310").append("008").append("19770101");
        // Element 03.20 + lengte + waarde (totale lengte: 11)
        verwachteWa11.append("0320").append("004").append("0518");
        // Element 03.30 + lengte + waarde (totale lengte: 11)
        verwachteWa11.append("0330").append("004").append("6030");

        Assert.assertEquals(verwachteWa11.toString(), wa11);
    }

    public Lo3Persoonslijst buildLo3Persoonslijst() {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(buildPersoonstapel());
        builder.inschrijvingStapel(buildInschrijvingStapel());

        return builder.build();
    }

    private Lo3Stapel<Lo3PersoonInhoud> buildPersoonstapel() {
        return Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
            Lo3StapelHelper.lo3Persoon(3450924321L, null, "Scarlet", null, null, "Simpspoon", 19770101, "0518", "6030", "V", 9539040545L, null, "E"),
            Lo3StapelHelper.lo3Akt(1),
            Lo3StapelHelper.lo3His(19770101),
            new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0)));
    }

    private Lo3Stapel<Lo3InschrijvingInhoud> buildInschrijvingStapel() {
        return Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
            Lo3StapelHelper.lo3Inschrijving(null, 20140101, "F", 19770202, "0518", 0, 1, 19770102123014000L, true),
            null,
            Lo3Historie.NULL_HISTORIE,
            new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0)));
    }

}

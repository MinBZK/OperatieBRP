/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.component.util;

import nl.bzk.brp.datataal.leveringautorisatie.AutAutDSL;
import nl.bzk.brp.datataal.leveringautorisatie.ToegangLeveringautorisatieDsl;
import nl.bzk.brp.datataal.leveringautorisatie.ToegangLeveringautorisatieDslParseerResultaat;
import nl.bzk.brp.testrunner.component.BrpOmgeving;
import nl.bzk.brp.testrunner.component.services.datatoegang.VerzoekMetJdbcTemplate;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashSet;
import java.util.List;

/**
 */
public class LeveringautorisatieHelper {

    private final BrpOmgeving omgeving;

    public LeveringautorisatieHelper(BrpOmgeving omgeving) {
        this.omgeving = omgeving;

        //niet zo netjes
        if (AutAutDSL.IDENTITEIT_GROEPEN == null) {

            omgeving.database().voerUit(new VerzoekMetJdbcTemplate() {
                @Override
                public void voerUitMet(JdbcTemplate jdbcTemplate) {
                    final List<String> stringList = jdbcTemplate.queryForList("select naam from kern.element where elementnaam = 'Identiteit'", String.class);
                    AutAutDSL.IDENTITEIT_GROEPEN = new HashSet<String>(stringList);
                }
            });
        }
    }

    //    @Given("leveringautorisatie uit \$bestanden")
    public void uitBestand(String... bestanden) {

        AutorisatieContext.get().reset();

        String totaleSqlString = "";
        totaleSqlString += "delete from autaut.dienstbundelgroepattr;\n";
        totaleSqlString += "delete from autaut.dienstbundelgroep;\n";
        totaleSqlString += "TRUNCATE autaut.dienst CASCADE;\n";
        totaleSqlString += "delete from autaut.dienstbundel;\n";
        totaleSqlString += "delete from autaut.toeganglevsautorisatie;\n";
        totaleSqlString += "TRUNCATE autaut.levsautorisatie CASCADE;\n";

        //voeg nieuwe autaut vulling toe
        for (String bestand : bestanden) {
            final ToegangLeveringautorisatieDslParseerResultaat resultaat = AutAutDSL.voerLeveringautorisatiesOp(new ClassPathResource(bestand));
            for (ToegangLeveringautorisatieDsl tla : resultaat.getToegangLeveringautorisatieDsls()) {
                AutorisatieContext.get().voegToegangleveringsautorisatieToe(tla);
                //zetOinInPartijTabelVoorGeautoriseerde
                //totaleSqlString += String.format("update kern.partij set oin = (select code from kern.partij where naam = '%s') where naam = " +
                //        "'%s';%n", tla.getGeautoriseerde(), tla.getGeautoriseerde());
            }

            String resultaatString = resultaat.toSql();
            if (omgeving.bevat("leveringontvanger")) {
                resultaatString = resultaatString.replaceAll("<afleverpunt>", "<uri ontvanger TODO>");
            }
            totaleSqlString += resultaatString;

        }

        ByteArrayResource resource = new ByteArrayResource(totaleSqlString.getBytes());
        omgeving.database().sqlScript(resource);

        //refresh caches
        omgeving.cache().update();
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.datataal;

import nl.bzk.brp.datataal.leveringautorisatie.AutAutDSL;
import nl.bzk.brp.datataal.leveringautorisatie.ToegangBijhoudingautorisatieDsl;
import nl.bzk.brp.datataal.leveringautorisatie.ToegangLeveringautorisatieDslParseerResultaat;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/**
 */
public class AutAutDSLTest {

    @Test
    public void testLeveringautorisatieDsl() throws IOException {
        final Resource resource = new ClassPathResource("/autaut/autautdsl-voorbeeld.txt");
        ToegangLeveringautorisatieDslParseerResultaat resultaat = AutAutDSL.voerLeveringautorisatiesOp(resource);
        Writer writer = new StringWriter();
        resultaat.toSQL(writer);
        System.out.println(writer.toString());
    }

    /**
     * Autorisatie voor attribuut 'Persoon.Bijhouding.DatumAanvangGeldigheid' kan niet
     * ingeregeld worden omdat groep 'Persoon.Bijhouding' ontbreekt
     * @throws IOException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testLeveringautorisatieDslFoutiefOmdatGroepOntbreekt() throws IOException {
        final Resource resource = new ClassPathResource("/autaut/autautdsl-voorbeeld-foutief-groep-ontbreekt.txt");
        ToegangLeveringautorisatieDslParseerResultaat resultaat = AutAutDSL.voerLeveringautorisatiesOp(resource);
        Writer writer = new StringWriter();
        resultaat.toSQL(writer);
        System.out.println(writer.toString());
    }

    @Test
    public void testBijhoudingautorisatieDsl() throws IOException {
        final Resource resource = new ClassPathResource("/autaut/autautdsl-voorbeeld-bijhouding.txt");
        ToegangBijhoudingautorisatieDsl ba = AutAutDSL.voerToegangBijhoudingautorisatieOp(resource);
        Writer writer = new StringWriter();
        ba.toSQL(writer);
        System.out.println(writer.toString());
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarbrp;

import junit.framework.Assert;
import nl.bzk.migratiebrp.init.naarbrp.repository.jdbc.JdbcAutorisatieRepository.RubriekenFormatter;
import org.junit.Test;

public class RubriekenFormatterTest {

    @Test
    public void test1Rubriek5Lang() {
        final String rubrieken = "11010";

        final String geconverteerdeRubrieken = RubriekenFormatter.format(rubrieken);

        Assert.assertEquals("01.10.10", geconverteerdeRubrieken);
    }

    @Test
    public void test1Rubriek6Lang() {
        final String rubrieken = "851010";

        final String geconverteerdeRubrieken = RubriekenFormatter.format(rubrieken);

        Assert.assertEquals("85.10.10", geconverteerdeRubrieken);
    }

    @Test
    public void test2Rubrieken5Lang() {
        final String rubrieken = "11010 22020";

        final String geconverteerdeRubrieken = RubriekenFormatter.format(rubrieken);

        Assert.assertEquals("01.10.10#02.20.20", geconverteerdeRubrieken);
    }

    @Test
    public void test2Rubrieken6Lang() {
        final String rubrieken = "851010 852020";

        final String geconverteerdeRubrieken = RubriekenFormatter.format(rubrieken);

        Assert.assertEquals("85.10.10#85.20.20", geconverteerdeRubrieken);
    }

    @Test
    public void test3Rubrieken6Lang() {
        final String rubrieken = "851010 852020 853030";

        final String geconverteerdeRubrieken = RubriekenFormatter.format(rubrieken);

        Assert.assertEquals("85.10.10#85.20.20#85.30.30", geconverteerdeRubrieken);
    }

}

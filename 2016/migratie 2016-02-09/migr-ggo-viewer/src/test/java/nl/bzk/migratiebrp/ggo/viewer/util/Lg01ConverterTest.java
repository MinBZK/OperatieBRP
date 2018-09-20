/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigePersoonslijstException;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.ggo.viewer.converter.Lg01Converter;
import nl.bzk.migratiebrp.ggo.viewer.log.FoutMelder;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test Lg01ConverterTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class Lg01ConverterTest {

    private final Lg01Converter lg01Converter = new Lg01Converter();
    private final FoutMelder foutMelder = new FoutMelder();

    @Test
    public void converteerLg01NaarLo3PersoonslijstOk() throws IOException, BerichtSyntaxException {
        final String filename = "Omzetting.txt";
        final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filename);
        final List<Lo3CategorieWaarde> result =
                lg01Converter.converteerLg01NaarLo3CategorieWaarde(IOUtils.toString(inputStream, "UTF-8"), foutMelder);
        assertTrue(result.size() > 1);
    }

    @Test
    public void converteerLg01NaarLo3PersoonslijstLeeg() throws BerichtSyntaxException, OngeldigePersoonslijstException {
        lg01Converter.converteerLg01NaarLo3CategorieWaarde("", foutMelder);
        assertEquals(1, foutMelder.getFoutRegels().size());
        assertEquals("Fout bij het lezen van lg01", foutMelder.getFoutRegels().get(0).getCode());
    }

    @Test
    public void converteerLg01NaarLo3PersoonslijstOngeldig() throws BerichtSyntaxException, OngeldigePersoonslijstException, IOException {
        final String filename = "Bericht ongeldig.txt";
        final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filename);
        lg01Converter.converteerLg01NaarLo3CategorieWaarde(IOUtils.toString(inputStream, "UTF-8"), foutMelder);
        assertEquals(1, foutMelder.getFoutRegels().size());
        assertEquals("Ongeldig bericht", foutMelder.getFoutRegels().get(0).getCode());
    }

    @Test
    public void converteerLg01NaarLo3PersoonslijstOnbekend() throws IOException, BerichtSyntaxException {
        final String filename = "Bericht onbekend.txt";
        final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filename);
        lg01Converter.converteerLg01NaarLo3CategorieWaarde(IOUtils.toString(inputStream, "UTF-8"), foutMelder);
        assertEquals(1, foutMelder.getFoutRegels().size());
        assertEquals("Onbekend bericht", foutMelder.getFoutRegels().get(0).getCode());
    }

}

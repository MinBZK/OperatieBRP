/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.app;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import java.util.List;

import joptsimple.OptionException;
import joptsimple.OptionSet;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Test that asserts the configuration of our commandline options.
 */
@RunWith(JUnit4.class)
public class CliParserTest {

    private CliParser parser = new CliParser();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void cliWithLongOptionsShouldBeParsed() {
        //given
        String[] args = {"--scenario=bevragen", "--bsn", "20", "--threads", "20"};

        // when
        OptionSet options = parser.parse(args);

        // then
        assertThat(options.has("threads"), is(true));
        assertThat((Integer) options.valueOf("T"), is(20));
        assertThat((List<String>) options.valuesOf("scenario"), contains("bevragen"));
        assertThat((String) options.valueOf("S"), is("bevragen"));
        assertThat((Integer) options.valueOf("bsn"), is(20));
    }

    @Test
    public void cliWithShortOptionsShouldBeParsed() {
        //given
        String[] args = {"-Sbevragen", "-B20", "-T", "7"};

        // when
        OptionSet options = parser.parse(args);

        // then
        assertThat(options.has("threads"), is(true));
        assertThat((Integer) options.valueOf("T"), is(7));
        assertThat((List<String>) options.valuesOf("scenario"), contains("bevragen"));
        assertThat((Integer) options.valueOf("bsn"), is(20));
    }

    @Test
    public void cliWithoutOptionsThatHaveDefaultsShouldBeParsed() {
        //given
        String[] args = {"-Sbevragen"};

        // when
        OptionSet options = parser.parse(args);

        // then
        assertThat(options.has("threads"), is(false));
        assertThat(options.has("dubbele"), is(false));

        assertThat((Integer) options.valueOf("T"), is(5));
        assertThat((Integer) options.valueOf("B"), is(1000));
        assertThat((Integer) options.valueOf("dubbele"), is(0));

        assertThat(options.valueOf("message"), nullValue());

    }

    @Test
    public void providedMessageIsRead() {
        //given
        String[] args = {"-Sbevragen", "--message=foo bar this test"};

        // when
        OptionSet options = parser.parse(args);

        // then
        assertThat(options.has("message"), is(true));

        assertThat((String)options.valueOf("message"), is("foo bar this test"));
    }

    @Test
    public void cliWithoutRequiredOptionsShouldFail() {
        //given
        thrown.expect(OptionException.class);
        String[] args = {};

        // when
        OptionSet options = parser.parse(args);
    }

    @Test
    public void cliWithDubbeleShouldBeParsed() {
        //given
        String[] args = {"-Sbevragen", "--dubbele=10"};

        // when
        OptionSet options = parser.parse(args);

        // then
        assertThat(options.has("threads"), is(false));
        assertThat((Integer) options.valueOf("T"), is(5));
        assertThat((Integer) options.valueOf("B"), is(1000));
        assertThat((Integer) options.valueOf("dubbele"), is(10));
    }

}

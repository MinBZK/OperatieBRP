/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.afnemersindicatie;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAfnemersindicatie;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAfnemersindicaties;

import org.junit.Test;

public class BrpAfnemersindicatiesVergelijkerTest {

    @Test
    public void expectedNull() {
        final StringBuilder vergelijking = new StringBuilder();
        final Boolean result = BrpAfnemersindicatiesVergelijker.vergelijk(vergelijking, null, new BrpAfnemersindicaties("12345", null));

        assertEquals(false, result);
        assertThat(vergelijking.toString(), containsString("Een van de afnemersindicaties is null"));
    }

    @Test
    public void actualNull() {
        final StringBuilder vergelijking = new StringBuilder();
        final Boolean result = BrpAfnemersindicatiesVergelijker.vergelijk(vergelijking, new BrpAfnemersindicaties("12345", null), null);

        assertEquals(false, result);
        assertThat(vergelijking.toString(), containsString("Een van de afnemersindicaties is null"));
    }

    @Test
    public void allebeiNull() {
        final StringBuilder vergelijking = new StringBuilder();
        final Boolean result = BrpAfnemersindicatiesVergelijker.vergelijk(vergelijking, null, null);

        assertEquals(true, result);
        assertThat(vergelijking.toString(), isEmptyString());
    }

    @Test
    public void verschillendeAdministratieNummers() {
        final StringBuilder vergelijking = new StringBuilder();
        final Boolean result =
                BrpAfnemersindicatiesVergelijker.vergelijk(
                        vergelijking,
                        new BrpAfnemersindicaties("12345", Collections.emptyList()),
                        new BrpAfnemersindicaties("12346", Collections.emptyList()));

        assertEquals(false, result);
        assertThat(vergelijking.toString(), containsString("expected=12345, actual=12346"));
    }

    @Test
    public void identiekeAdministratieNummers() {
        final StringBuilder vergelijking = new StringBuilder();
        final Boolean result =
                BrpAfnemersindicatiesVergelijker.vergelijk(
                        vergelijking,
                        new BrpAfnemersindicaties("12345", Collections.emptyList()),
                        new BrpAfnemersindicaties("12345", Collections.emptyList()));

        assertEquals(true, result);
        assertThat(vergelijking.toString(), isEmptyString());
    }

    @Test
    public void verschillendeAantallenAfnemerIndicaties() {
        final List<BrpAfnemersindicatie> afnemerindicaties1 = Collections.singletonList(new BrpAfnemersindicatie(new BrpPartijCode("000666"), null, null));
        final List<BrpAfnemersindicatie> afnemerindicaties2 =
                Arrays.asList(new BrpAfnemersindicatie(new BrpPartijCode("000666"), null, null), new BrpAfnemersindicatie(new BrpPartijCode("000777"), null, null));

        StringBuilder vergelijking = new StringBuilder();
        Boolean result =
                BrpAfnemersindicatiesVergelijker.vergelijk(
                        vergelijking,
                        new BrpAfnemersindicaties("12345", afnemerindicaties1),
                        new BrpAfnemersindicaties("12345", afnemerindicaties2));

        assertEquals(false, result);
        assertThat(vergelijking.toString(), containsString("(expected=1, actual=2)"));

        vergelijking = new StringBuilder();
        result =
                BrpAfnemersindicatiesVergelijker.vergelijk(
                        vergelijking,
                        new BrpAfnemersindicaties("12345", afnemerindicaties2),
                        new BrpAfnemersindicaties("12345", afnemerindicaties1));

        assertEquals(false, result);
        assertThat(vergelijking.toString(), containsString("(expected=2, actual=1)"));
    }

    @Test
    public void verschillendeAfnemerIndicaties() {
        final List<BrpAfnemersindicatie> afnemerindicaties1 =
                Collections.singletonList(new BrpAfnemersindicatie(new BrpPartijCode("000666"), null, "levsaut1"));
        final List<BrpAfnemersindicatie> afnemerindicaties2 =
                Collections.singletonList(new BrpAfnemersindicatie(new BrpPartijCode("000666"), null, "levsaut2"));

        StringBuilder vergelijking = new StringBuilder();
        Boolean result =
                BrpAfnemersindicatiesVergelijker.vergelijk(
                        vergelijking,
                        new BrpAfnemersindicaties("12345", afnemerindicaties1),
                        new BrpAfnemersindicaties("12345", afnemerindicaties2));

        assertEquals(false, result);
        assertThat(vergelijking.toString(), containsString("expected=levsaut1, actual=levsaut2"));
    }
}

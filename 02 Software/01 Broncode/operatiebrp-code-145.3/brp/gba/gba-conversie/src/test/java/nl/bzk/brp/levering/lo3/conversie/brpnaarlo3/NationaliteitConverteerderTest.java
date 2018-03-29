/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.brpnaarlo3;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.Collections;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenBeeindigingNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenVerliesNLNationaliteit;
import nl.bzk.brp.levering.lo3.tabel.NationaliteitConversietabel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerliesNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpAttribuutConverteerder;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel.RedenBeeindigingNationaliteitConversietabel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel.RedenOpnameNationaliteitConversieTabel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class NationaliteitConverteerderTest {

    private NationaliteitConverteerder subject;

    private Lo3NationaliteitInhoud lo3NationaliteitInhoud =
            new Lo3NationaliteitInhoud(
                Lo3NationaliteitCode.NATIONALITEIT_CODE_NEDERLANDSE,
                new Lo3RedenNederlandschapCode("A"),
                new Lo3RedenNederlandschapCode("B"),
                null, null);

    @Before
    public void setup() {
        ConversietabelFactory conversietabelFactory = mock(ConversietabelFactory.class);
        Mockito.when(conversietabelFactory.createNationaliteitConversietabel()).thenReturn(new NationaliteitConversietabel(Arrays.asList()));
        Mockito.when(conversietabelFactory.createRedenOpnameNationaliteitConversietabel())
               .thenReturn(new RedenOpnameNationaliteitConversieTabel(Arrays.asList()));
        Mockito.when(conversietabelFactory.createRedenBeeindigingNationaliteitConversietabel()).thenReturn(
            new RedenBeeindigingNationaliteitConversietabel(
                Collections.singletonList(new RedenBeeindigingNationaliteit("34", new RedenVerliesNLNationaliteit("034", "omschrijving")))));

        subject = new NationaliteitConverteerder(new BrpAttribuutConverteerder(conversietabelFactory));
    }

    @Test
    public void testBrpInhoudNull() {
        Lo3NationaliteitInhoud gevuldeInhoud = subject.vulInhoud(lo3NationaliteitInhoud, null, null);
        assertEquals(null, gevuldeInhoud.getNationaliteitCode());
        assertEquals(null, gevuldeInhoud.getRedenVerkrijgingNederlandschapCode());
        assertEquals(null, gevuldeInhoud.getRedenVerliesNederlandschapCode());
    }

    @Test
    public void testBrpInhoudLeeg() {
        Lo3NationaliteitInhoud gevuldeInhoud =
                subject.vulInhoud(lo3NationaliteitInhoud, new BrpNationaliteitInhoud(null, null, null, null, null, null, null), null);
        assertEquals(null, gevuldeInhoud.getNationaliteitCode());
        assertEquals(null, gevuldeInhoud.getRedenVerkrijgingNederlandschapCode());
        assertEquals(null, gevuldeInhoud.getRedenVerliesNederlandschapCode());
    }

    @Test
    public void testBrpInhoudLeegMetRedenVerliesNederlandschap() {
        Lo3NationaliteitInhoud gevuldeInhoud =
                subject.vulInhoud(
                    lo3NationaliteitInhoud,
                    new BrpNationaliteitInhoud(null, null, new BrpRedenVerliesNederlandschapCode("034"), null, null, null, null),
                    null);
        assertEquals(null, gevuldeInhoud.getNationaliteitCode());
        assertEquals(null, gevuldeInhoud.getRedenVerkrijgingNederlandschapCode());
        assertThat(gevuldeInhoud.getRedenVerliesNederlandschapCode(), is(instanceOf(Lo3RedenNederlandschapCode.class)));
    }

    @Test
    public void testBrpInhoudNietLeeg() {
        Lo3NationaliteitInhoud gevuldeInhoud =
                subject.vulInhoud(
                    lo3NationaliteitInhoud,
                    new BrpNationaliteitInhoud(
                        new BrpNationaliteitCode("0001"),
                        null,
                        new BrpRedenVerliesNederlandschapCode("034"),
                        null,
                        null,
                        null,
                        null),
                    null);
        assertThat(gevuldeInhoud.getNationaliteitCode(), is(instanceOf(Lo3NationaliteitCode.class)));
        assertEquals(null, gevuldeInhoud.getRedenVerkrijgingNederlandschapCode());
        assertThat(gevuldeInhoud.getRedenVerliesNederlandschapCode(), is(instanceOf(Lo3RedenNederlandschapCode.class)));
    }
}

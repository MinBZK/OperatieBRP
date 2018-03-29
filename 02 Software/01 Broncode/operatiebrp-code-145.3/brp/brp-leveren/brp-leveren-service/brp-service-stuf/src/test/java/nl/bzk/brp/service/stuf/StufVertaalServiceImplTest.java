/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.stuf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.google.common.collect.Iterables;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import org.junit.Test;

public class StufVertaalServiceImplTest {

    final String PARTIJ_CODE = "999999";
    final String PARTIJ_CODE_039201 = "039201";
    final String PARTIJ_CODE_017401 = "017401";

    private StufVertaalServiceImpl subject = new StufVertaalServiceImpl();

    @Test
    public void vertaal() throws Exception {
        final StufBerichtVerzoek stufBerichtVerzoek = new StufBerichtVerzoek();
        stufBerichtVerzoek.getStuurgegevens().setZendendePartijCode(PARTIJ_CODE);

        StufTransformatieResultaat stufTransformatieResultaat = subject.vertaal(stufBerichtVerzoek);

        assertTrue(stufTransformatieResultaat.getMeldingen().isEmpty());
    }

    @Test
    public void vertaal_partij039201() throws Exception {
        final StufBerichtVerzoek stufBerichtVerzoek = new StufBerichtVerzoek();
        stufBerichtVerzoek.getStuurgegevens().setZendendePartijCode(PARTIJ_CODE_039201);

        StufTransformatieResultaat stufTransformatieResultaat = subject.vertaal(stufBerichtVerzoek);

        assertEquals(Regel.R2445, Iterables.getOnlyElement(stufTransformatieResultaat.getMeldingen()).getRegel());
    }

    @Test
    public void vertaal_partij017401() throws Exception {
        final StufBerichtVerzoek stufBerichtVerzoek = new StufBerichtVerzoek();
        stufBerichtVerzoek.getStuurgegevens().setZendendePartijCode(PARTIJ_CODE_017401);

        StufTransformatieResultaat stufTransformatieResultaat = subject.vertaal(stufBerichtVerzoek);

        assertEquals(Regel.R2446, Iterables.getOnlyElement(stufTransformatieResultaat.getMeldingen()).getRegel());
    }

}
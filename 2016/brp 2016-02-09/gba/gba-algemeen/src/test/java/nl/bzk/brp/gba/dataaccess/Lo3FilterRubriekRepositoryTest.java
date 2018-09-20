/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.dataaccess;

import static org.junit.Assert.assertThat;

import java.util.List;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienstbundel;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

public class Lo3FilterRubriekRepositoryTest extends AbstractIntegratieTest {

    @Autowired
    private Lo3FilterRubriekRepository lo3FilterRubriekRepository;

    @Test
    public final void haalLo3FilterRubriekenVoorDienstbundel() {
        final Dienstbundel dienstbundel = new Dienstbundel() {
        };
        ReflectionTestUtils.setField(dienstbundel, "iD", 1);

        final List<String> rubrieken = lo3FilterRubriekRepository.haalLo3FilterRubriekenVoorDienstbundel(dienstbundel);
        assertThat(rubrieken.size(), CoreMatchers.is(5));
    }
}

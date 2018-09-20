/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.List;
import javax.inject.Inject;
import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import org.junit.Test;

/**
 *
 */
public class ToegangLeveringsautorisatieRepositoryTest extends AbstractRepositoryTestCase {

    @Inject
    private ToegangLeveringsautorisatieRepository tlaRepository;

    @Test
    public void haalAlleToegangLeveringsautorisatieOp() {
        // when
        final List<ToegangLeveringsautorisatie> resultaat = tlaRepository.haalAlleToegangLeveringsautorisatieOp();

        // then
        assertThat(resultaat, notNullValue());
        assertThat(resultaat.isEmpty(), is(false));

        final ToegangLeveringsautorisatie next = resultaat.iterator().next();
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = tlaRepository.haalToegangLeveringsautorisatieOp(next.getID());
        assertNotNull(toegangLeveringsautorisatie);
    }

    @Test
    public void haalAlleLeveringsautorisatieOp() {
        // when
        final List<Leveringsautorisatie> resultaat = tlaRepository.haalAlleLeveringsautorisatieOp();

        // then
        assertThat(resultaat, notNullValue());
        assertThat(resultaat.isEmpty(), is(false));
    }

}

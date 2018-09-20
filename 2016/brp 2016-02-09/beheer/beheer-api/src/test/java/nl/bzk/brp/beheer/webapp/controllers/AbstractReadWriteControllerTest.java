/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers;

import java.util.Collections;
import java.util.List;
import nl.bzk.brp.beheer.webapp.controllers.ErrorHandler.NotFoundException;
import nl.bzk.brp.beheer.webapp.controllers.query.Filter;
import nl.bzk.brp.beheer.webapp.repository.ReadWriteRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.beheer.kern.Partij;
import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.PlatformTransactionManager;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AbstractReadWriteControllerTest {

    private static final int DATUM_20150101 = 20150101;
    private static final int CODE_10001 = 10001;
    private static final Short ID = 1;
    private ReadWriteControllerImpl instance;
    private ReadWriteRepository<Partij, Short> mockRepository;

    /**
     * Test.
     */
    public AbstractReadWriteControllerTest() {
        instance = null;
    }

    /**
     * Test.
     */
    @Before
    public final void setup() {
        mockRepository = mock(ReadWriteRepository.class);
        final Partij een = mock(Partij.class);
        when(een.getID()).thenReturn(ID);
        when(mockRepository.save(any(Partij.class))).thenReturn(een);

        instance = new ReadWriteControllerImpl(mockRepository, Collections.<Filter<?>>emptyList());
        instance.setTransactionManagerReadWrite(mock(PlatformTransactionManager.class));
    }

    /**
     * Test of save method, of class AbstractReadWriteController.
     */
    @Test
    public final void testSave() throws NotFoundException {
        final Partij opteslaan = new Partij();
        opteslaan.setCode(new PartijCodeAttribuut(CODE_10001));
        opteslaan.setNaam(new NaamEnumeratiewaardeAttribuut("Test"));
        opteslaan.setDatumIngang(new DatumEvtDeelsOnbekendAttribuut(DATUM_20150101));
        final Partij opgeslagen = instance.save(opteslaan);
        assertEquals(ID, opgeslagen.getID());
    }

    /**
     * Test.
     */
    public static class ReadWriteControllerImpl extends AbstractReadWriteController<Partij, Short> {

        /**
         * Test.
         *
         * @param repository repo
         * @param filters filters
         */
        public ReadWriteControllerImpl(final ReadWriteRepository<Partij, Short> repository, final List<Filter<?>> filters) {
            super(repository, filters);
        }
    }

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.web.service.stamgegevens;

import nl.bzk.brp.beheer.web.dao.GenericDaoImpl;
import nl.bzk.brp.domein.DomeinObjectFactory;
import nl.bzk.brp.domein.PersistentDomeinObjectFactory;
import nl.bzk.brp.domein.kern.Partij;
import nl.bzk.brp.domein.kern.persistent.PersistentPartij;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class StamgegevensServiceImplTest {

    @Mock
    private GenericDaoImpl            genericDao;

    private StamgegevensServiceImpl   stamgegevensService;

    // TODO Hosing: deze moet gemockt worden, maar weet op het moment even niet zo snel een oplossing te vinden voor de
    // return value
    private final DomeinObjectFactory domeinObjectFactory = new PersistentDomeinObjectFactory();

    @Before
    public void setup() {
        stamgegevensService = new StamgegevensServiceImpl();
        stamgegevensService.setGenericDao(genericDao);
        stamgegevensService.setDomeinObjectFactory(domeinObjectFactory);
    }

    @Test
    public void testFind() {
        stamgegevensService.find(Partij.class, 1);
        Mockito.verify(genericDao, Mockito.times(1)).getById(PersistentPartij.class, 1);
    }
}

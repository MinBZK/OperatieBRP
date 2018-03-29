/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.autaut;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Protocolleringsniveau;
import nl.bzk.brp.beheer.webapp.controllers.ErrorHandler.NotFoundException;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.autaut.ProtocolleringsniveauRepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.PlatformTransactionManager;

@RunWith(MockitoJUnitRunner.class)
public class ProtocolleringsniveauControllerTest {

    private final ProtocolleringsniveauRepository repository = new ProtocolleringsniveauRepository();
    @Mock
    private PlatformTransactionManager transactionManager;
    @InjectMocks
    private ProtocolleringsniveauController subject;

    @Before
    public void setup() {
        subject = new ProtocolleringsniveauController(repository);
        subject.setTransactionManagerReadonly(transactionManager);
    }

    @Test
    public void wijzigObjectVoorOpslag() throws NotFoundException {

        // Execute
        final Protocolleringsniveau result = subject.get(1);
        subject.list(null, new PageRequest(0, 20));
        Assert.assertEquals(Protocolleringsniveau.GEEN_BEPERKINGEN, result);

    }

}

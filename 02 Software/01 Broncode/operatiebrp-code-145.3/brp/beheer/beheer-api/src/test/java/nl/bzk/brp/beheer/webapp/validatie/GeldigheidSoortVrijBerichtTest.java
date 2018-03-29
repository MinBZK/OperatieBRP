/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.validatie;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortVrijBericht;
import nl.bzk.brp.beheer.webapp.repository.ReadonlyRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test voor GeldigheidRechtsgrond
 */
@RunWith(MockitoJUnitRunner.class)
public class GeldigheidSoortVrijBerichtTest {

    @Mock
    private ReadonlyRepository<SoortVrijBericht, Number> repository;

    @InjectMocks
    private GeldigheidSoortVrijBericht subject;

    @Test
    public void test() throws Exception {
        Assert.assertTrue(subject.supports(SoortVrijBericht.class));
    }
}
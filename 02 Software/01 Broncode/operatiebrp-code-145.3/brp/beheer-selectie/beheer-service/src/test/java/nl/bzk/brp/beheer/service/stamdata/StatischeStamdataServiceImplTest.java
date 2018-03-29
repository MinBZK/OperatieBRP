/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.service.stamdata;

import static org.mockito.Mockito.verify;

import nl.bzk.brp.beheer.service.dal.StamdataRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit test voor {@link StatischeStamdataServiceImpl}.
 */
@RunWith(MockitoJUnitRunner.class)
public class StatischeStamdataServiceImplTest {

    @InjectMocks
    private StatischeStamdataServiceImpl statischeStamdataService;
    @Mock
    private StamdataRepository stamdataRepository;

    @Test
    public void statischeStamdata() {
        statischeStamdataService.getStatischeStamdata("test");

        verify(stamdataRepository).getStatischeStamdata("test");
    }
}
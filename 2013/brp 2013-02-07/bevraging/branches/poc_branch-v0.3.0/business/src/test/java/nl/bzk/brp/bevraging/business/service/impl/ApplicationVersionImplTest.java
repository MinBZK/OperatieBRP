/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.service.impl;

import java.util.HashMap;
import java.util.Map;

import nl.bzk.brp.bevraging.business.service.SvnVersionService.SvnVersionEnum;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


public class ApplicationVersionImplTest {

    private static final String    POMVERSION          = "POMVERSION";
    private static final String    MAVENBUILDTIMESTAMP = "MAVENBUILDTIMESTAMP";

    @Mock
    private SvnVersionServiceImpl      svnVersionService;

    private ApplicationVersionImpl applicationVersionImpl;

    @Test
    public void testApplicationVersionImpl() {

        applicationVersionImpl.init();

    }

    /**
     * Initialiseert de mocks en de service.
     */
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        Map<SvnVersionEnum, String> svnVersion = new HashMap<SvnVersionEnum, String>();

        svnVersion.put(SvnVersionEnum.pomversion, POMVERSION);
        svnVersion.put(SvnVersionEnum.mavenbuildtimestamp, MAVENBUILDTIMESTAMP);

        ReflectionTestUtils.setField(svnVersionService, "svnVersion", svnVersion);

        applicationVersionImpl = new ApplicationVersionImpl();

        ReflectionTestUtils.setField(applicationVersionImpl, "svnVersionService", svnVersionService);
    }

}

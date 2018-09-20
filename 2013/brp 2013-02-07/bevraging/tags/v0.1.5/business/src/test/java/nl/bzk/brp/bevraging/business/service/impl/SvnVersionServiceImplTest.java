/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.util.Assert;


public class SvnVersionServiceImplTest {

    private SvnVersionServiceImpl      svnVersionServiceImpl;

    @Test
    public void testApplicationVersionImpl() {
        Assert.notNull(svnVersionServiceImpl.getAppString());
        Assert.isTrue(svnVersionServiceImpl.getAppString().length() > 0);
    }

    /**
     * Initialiseert de mocks en de service.
     */
    @Before
    public void init() {

        MockitoAnnotations.initMocks(this);

        svnVersionServiceImpl = new SvnVersionServiceImpl();

        svnVersionServiceImpl.init();

    }

}

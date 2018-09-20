/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.service.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.test.util.ReflectionTestUtils;


public class SvnVersionServiceImplTest {

    private static final String   VALID_VERSION_STRING = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> "
        + "<root> "
        + "    <MAVENBUILDTIMESTAMP>11-11-2011</MAVENBUILDTIMESTAMP> "
        + "    <POMVERSION>0.1.6</POMVERSION> "
        + "    <JAVA_VERSION>8.0</JAVA_VERSION> "
        + "</root>";
    private static final String   INVALID_VERSION_STRING = "Dit is gewoon tekst";
    private static final String   LEGE_VERSION_STRING = "";

    @Mock
    private ApplicationContext    ctx;

    private SvnVersionServiceImpl svnVersionServiceImpl;

    @Test
    public void testApplicationVersionImpl() {
        setupService(VALID_VERSION_STRING);
        Assert.assertNotNull(svnVersionServiceImpl.getAppString());
        Assert.assertEquals("BRP Bevraging 0.1.6, gebouwd op 11-11-2011", svnVersionServiceImpl.getAppString());
    }

    @Test
    public void testApplicationVersionImplMetInvalideVersieString() {
        setupService(INVALID_VERSION_STRING);
        Assert.assertNotNull(svnVersionServiceImpl.getAppString());
        Assert.assertEquals("BRP Bevraging - versie onbekend", svnVersionServiceImpl.getAppString());
    }

    @Test
    public void testApplicationVersionImplMetLegeVersieString() {
        setupService(LEGE_VERSION_STRING);
        Assert.assertNotNull(svnVersionServiceImpl.getAppString());
        Assert.assertEquals("BRP Bevraging - versie onbekend", svnVersionServiceImpl.getAppString());
    }

    /**
     * Initialiseert de mocks en de service.
     */
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        svnVersionServiceImpl = new SvnVersionServiceImpl();
    }

    private void setupService(final String versieFileTekst) {
        ByteArrayResource resource = new ByteArrayResource(versieFileTekst.getBytes());
        Mockito.when(ctx.getResource(Matchers.anyString())).thenReturn(resource);

        ReflectionTestUtils.setField(svnVersionServiceImpl, "ctx", ctx);
        svnVersionServiceImpl.init();
    }
}

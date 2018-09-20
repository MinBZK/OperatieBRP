/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RunWith(PowerMockRunner.class)
@PrepareForTest({LoggerFactory.class })
public abstract class AbstractBedrijfsregelsTest {

    private static Logger logger;

    @BeforeClass
    public static final void initAbstractBedrijfsregelsTest() {
        PowerMockito.mockStatic(LoggerFactory.class);
        logger = PowerMockito.mock(Logger.class);
        PowerMockito.when(LoggerFactory.getLogger(Matchers.any(Class.class))).thenReturn(logger);
    }

    @Before
    public final void initTest() {
        Mockito.reset(logger);
    }

    protected static Logger getLogger() {
        return logger;
    }
}

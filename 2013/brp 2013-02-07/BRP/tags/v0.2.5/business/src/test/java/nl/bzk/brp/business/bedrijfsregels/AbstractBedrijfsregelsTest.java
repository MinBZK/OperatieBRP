/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractBedrijfsregelsTest {

    private UnitTestLogAppender logAppender;

    @Before
    public final void initAbstractBedrijfsregelsTest() {
        final Logger classWithLoggerLogger = Logger.getLogger(getTestClass());
        classWithLoggerLogger.removeAllAppenders();
        logAppender = new UnitTestLogAppender(getTestClass());
        classWithLoggerLogger.addAppender(logAppender);
        logAppender.clear();
    }

    protected UnitTestLogAppender getLogAppender() {
        return logAppender;
    }

    protected abstract Class getTestClass();
}

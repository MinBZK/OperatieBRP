/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc;

import java.io.File;
import java.util.Collections;
import java.util.List;
import nl.bzk.migratiebrp.test.dal.AbstractTestCasusFactory;
import nl.bzk.migratiebrp.test.dal.TestCasus;
import nl.bzk.migratiebrp.test.dal.TestSkipper;

/**
 * Test casus factory: processen.
 */
public final class ProcessenTestCasusFactory extends AbstractTestCasusFactory {

    private final TestEnvironment environment;
    private final TestSkipper skipper;

    /**
     * Constructor.
     * @param environment omgeving
     */
    public ProcessenTestCasusFactory(final TestEnvironment environment, final TestSkipper skipper) {
        this.environment = environment;
        this.skipper = skipper;
    }

    @Override
    public List<TestCasus> leesTestCasussen(final File input) {
        final TestCasus testCasus = new ProcessenTestCasus(getThema(), input.getName(), getOutputFolder(), getExpectedFolder(), input, environment);
        testCasus.setSkipper(skipper);

        return Collections.<TestCasus>singletonList(testCasus);
    }

}

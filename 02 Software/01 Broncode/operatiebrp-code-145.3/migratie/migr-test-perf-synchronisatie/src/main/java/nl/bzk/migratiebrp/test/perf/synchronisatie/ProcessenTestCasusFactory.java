/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.perf.synchronisatie;

import java.io.File;
import java.util.Collections;
import java.util.List;

import nl.bzk.migratiebrp.test.dal.AbstractTestCasusFactory;
import nl.bzk.migratiebrp.test.dal.TestCasus;
import nl.bzk.migratiebrp.test.perf.synchronisatie.environment.TestEnvironment;

/**
 * Test casus factory: processen.
 */
public final class ProcessenTestCasusFactory extends AbstractTestCasusFactory {

    private final TestEnvironment environment;

    /**
     * Constructor.
     * @param environment omgeving
     */
    protected ProcessenTestCasusFactory(final TestEnvironment environment) {
        this.environment = environment;
    }

    @Override
    public List<TestCasus> leesTestCasussen(final File input) {
        return Collections.<TestCasus>singletonList(
                new ProcessenTestCasus(getThema(), input.getName(), getOutputFolder(), getExpectedFolder(), input, environment));
    }

}

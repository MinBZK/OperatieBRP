/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.dal.runner;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.bzk.migratiebrp.test.dal.TestCasus;
import org.junit.runner.Description;

/**
 * As we read tests from files and not from classes the description class of junit cannot give us enough space to store
 * our information. This file combines the two.
 */
public final class Test {

    private final TestCasus testCasus;
    private final Description description;
    private final List<Test> children = new ArrayList<>();

    /**
     * Creates a composite test.
     * @param name name
     */
    Test(final String name) {
        this(name, null);
    }

    /**
     * Creates an atomic test.
     * @param name name
     * @param testCasus testcasus
     */
    Test(final String name, final TestCasus testCasus) {
        this.testCasus = testCasus;

        if (testCasus == null) {
            description = Description.createSuiteDescription(name, new Annotation[0]);
        } else {
            description = Description.createTestDescription(testCasus.getClass(), name);
        }
    }

    /**
     * Add a child (only usefull for suites).
     * @param child child
     */
    public void addChild(final Test child) {
        children.add(child);
        description.addChild(child.getDescription());

    }

    /**
     * Geef de suite.
     * @return suite
     */
    public boolean isSuite() {
        return testCasus == null;
    }

    /**
     * Geef de test.
     * @return test
     */
    public boolean isTest() {
        return testCasus != null;
    }

    /**
     * Geef de waarde van description.
     * @return description
     */
    public Description getDescription() {
        return description;
    }

    /**
     * Geef de waarde van test casus.
     * @return test casus
     */
    public TestCasus getTestCasus() {
        return testCasus;
    }

    /**
     * Geef de waarde van children.
     * @return children
     */
    public List<Test> getChildren() {
        return Collections.unmodifiableList(children);
    }

}

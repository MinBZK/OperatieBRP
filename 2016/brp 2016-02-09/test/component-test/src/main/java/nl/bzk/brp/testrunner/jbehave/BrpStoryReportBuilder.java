/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.jbehave;

import org.jbehave.core.reporters.StoryReporter;
import org.jbehave.core.reporters.StoryReporterBuilder;

/**
 * Extends de {@link StoryReporterBuilder} zodat we onze eigen {@link BrpStoryReporter} kunnen toewijzen aan het process.
 */
public final class BrpStoryReportBuilder extends StoryReporterBuilder {

    @Override
    public StoryReporter build(final String storyPath) {
        return new BrpStoryReporter(super.build(storyPath));
    }
}

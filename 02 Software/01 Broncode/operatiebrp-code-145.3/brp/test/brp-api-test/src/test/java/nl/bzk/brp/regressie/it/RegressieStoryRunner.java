/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.regressie.it;

import java.io.File;
import org.junit.Assert;

/**
 * RegressieStoryRunner.
 */
public class RegressieStoryRunner {

    private static final String STORY_2_RUN = "story2run";

    public static void main(String[] args) {
        final File file = new File(args[0]);
        Assert.assertTrue(file.exists());
        System.setProperty(STORY_2_RUN, file.getName());

        System.out.println("RegressieStoryRunner: " + file.getName());
//        final Result result = JUnitCore.runClasses(InterneStoryTest.class);
//        final int failureCount = result.getFailureCount();
    }

//    public final static class InterneStoryTest extends AbstractApiTest {
//
////        @Override
////        protected String[] getFilter() {
////            return new String[]{ String.format("**/%s", System.getProperty(STORY_2_RUN)) };
////        }
//    }
}

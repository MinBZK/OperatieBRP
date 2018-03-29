/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.regressie.it.jenkins;


import nl.bzk.brp.regressie.it.AbstractApiTest;
import org.jbehave.core.annotations.UsingEmbedder;
import org.jbehave.core.annotations.UsingPaths;


/**
 * Test draait alle stories met status != Klaar
 */
@UsingPaths(
        searchIn = "src/test/resources",
        includes = "testcases/**/*.story"
)
@UsingEmbedder(
        embedder = AbstractApiTest.BrpEmbedder.class,
        verboseFailures = true,
        storyTimeoutInSecs = 600,
        metaFilters = "-status Klaar"
)
public class IT_Niet_Klaar extends AbstractApiTest {

}
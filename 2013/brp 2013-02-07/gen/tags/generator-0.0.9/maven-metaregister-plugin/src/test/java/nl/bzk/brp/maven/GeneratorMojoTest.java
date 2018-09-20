/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.maven;

import java.io.File;

import nl.bzk.brp.maven.GeneratorMojo;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class GeneratorMojoTest extends AbstractMojoTestCase {

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void testExecute() throws Exception {
        File pluginXmlFile = new File(getBasedir(), "src/test/plugin-configs/generator/java-plugin-config.xml");
        GeneratorMojo mojo = (GeneratorMojo) lookupMojo("generator", pluginXmlFile);
        assertNotNull("Mojo found.", mojo);

        mojo.execute();
        File outputDir = (File) getVariableValueFromObject( mojo, "outputDirectory" );
        assertTrue( outputDir.getAbsolutePath() + " not generated!", outputDir.exists() );

    }


}

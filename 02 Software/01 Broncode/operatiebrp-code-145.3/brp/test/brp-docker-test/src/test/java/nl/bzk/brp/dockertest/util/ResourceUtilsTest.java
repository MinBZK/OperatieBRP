/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Unit test voor {@link ResourceUtils}.
 */
@Ignore("werkt niet bij mij")
public class ResourceUtilsTest {

    @Test
    public void schrijfBerichtNaarBestand() throws Exception {
        ResourceUtils.schrijfBerichtNaarBestand("dit is een heel mooi xml bericht", "dir", "subdir", "filenaam.xml");

        assertThat(Files.exists(Paths.get(System.getProperty("user.dir") + "/target/dir/subdir/filenaam.xml")), is(true));
    }

}
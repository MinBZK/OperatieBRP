/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import nl.bzk.algemeenbrp.util.xml.exception.XmlException;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.serialize.MigratieXml;
import org.junit.Test;

/**
 * Inlezen Test.
 */
public class InlezenTest {

    @Test
    public void testLeesVerwachteBrpPersoonslijst() throws XmlException {
        final URL resource = this.getClass().getClassLoader().getResource("brppersoonslijst.xml");
        try {
            if (resource != null) {
                final File file = new File(resource.getFile());
                final FileInputStream fis = new FileInputStream(file);
                final Reader reader = new InputStreamReader(fis);

                MigratieXml.decode(BrpPersoonslijst.class, reader);

            }
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}

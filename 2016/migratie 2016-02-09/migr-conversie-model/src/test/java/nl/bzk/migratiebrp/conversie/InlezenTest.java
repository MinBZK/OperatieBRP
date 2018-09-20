/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie;

import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.serialize.XmlEncoding;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;

/**
 * Inlezen Test.
 */
public class InlezenTest {

    @Test
    public void testLeesVerwachteBrpPersoonslijst() {
        URL resource = this.getClass().getClassLoader().getResource("brppersoonslijst.xml");
        try {
            if(resource!=null) {
                final File file = new File(resource.getFile());
                FileInputStream fis = new FileInputStream(file);
                XmlEncoding.decode(BrpPersoonslijst.class, fis);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }




}
/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.brp;

import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.brp.xml.BrpXml;
import org.junit.Test;

public class BrpBerichtenTest {

    @Test(expected = RuntimeException.class)
    public void testElementToStringExceptie() throws BerichtInhoudException {
        BrpXml.SINGLETON.elementToString(null);
    }

}

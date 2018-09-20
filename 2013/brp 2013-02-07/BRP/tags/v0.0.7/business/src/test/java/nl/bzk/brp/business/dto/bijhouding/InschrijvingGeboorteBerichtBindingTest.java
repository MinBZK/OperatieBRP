/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto.bijhouding;


import java.io.IOException;

import nl.bzk.brp.model.binding.AbstractBindingTest;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Test;

/** Binding test voor een bijhoudings bericht. */
public class InschrijvingGeboorteBerichtBindingTest extends AbstractBindingTest<InschrijvingGeboorteBericht> {

    @Test
    public void testInschrijvingGeboorteBerichtBinding() throws IOException, JiBXException {
        String xml = leesBestand("inschrijving_geboorte.xml");
        InschrijvingGeboorteBericht bericht = unmarshalObject(xml);

        Assert.assertNotNull(bericht);
//        Assert.assertEquals(Integer.valueOf(0), bericht.getBrpActies().get(0).getDatumAanvangGeldigheid());
    }

    @Override
    protected Class<InschrijvingGeboorteBericht> getBindingClass() {
        return InschrijvingGeboorteBericht.class;
    }
}

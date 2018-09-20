/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import nl.bzk.brp.beheer.webapp.util.AttribuutUtils;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import org.junit.Assert;
import org.junit.Test;

public class AttribuutUtilsTest {

    @Test
    public void getAsAttribuut() {
        Assert.assertEquals(null, AttribuutUtils.getAsAttribuut(PartijCodeAttribuut.class, null));
        Assert.assertEquals(new PartijCodeAttribuut(123), AttribuutUtils.getAsAttribuut(PartijCodeAttribuut.class, 123));
    }

    @Test
    public void getWaarde() {
        Assert.assertEquals(null, AttribuutUtils.getWaarde(null));
        Assert.assertEquals(Integer.valueOf(123), AttribuutUtils.getWaarde(new PartijCodeAttribuut(123)));
    }
}

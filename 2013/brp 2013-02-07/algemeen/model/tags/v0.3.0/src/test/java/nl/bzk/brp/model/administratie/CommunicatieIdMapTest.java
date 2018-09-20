/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.administratie;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import org.junit.Assert;
import org.junit.Test;

public class CommunicatieIdMapTest {

    @Test
    public void testVoegNieuwIdentificeerbaarObjectToe() {
        CommunicatieIdMap ids = new CommunicatieIdMap();

        PersoonBericht persoonBerichtLowerCase = new PersoonBericht();
        persoonBerichtLowerCase.setCommunicatieID("lowercase");

        PersoonBericht persoonBerichtCamelCase = new PersoonBericht();
        persoonBerichtCamelCase.setCommunicatieID("camelCase");

        ids.put(persoonBerichtLowerCase);
        ids.put(persoonBerichtCamelCase);

        Assert.assertEquals(1, ids.get("lowercase").size());
        Assert.assertEquals(persoonBerichtLowerCase, ids.get("lowercase").get(0));

        Assert.assertEquals(1, ids.get("camelcase").size());
        Assert.assertEquals(persoonBerichtCamelCase, ids.get("camelcase").get(0));
        Assert.assertEquals(persoonBerichtCamelCase, ids.get("camelCase").get(0));
    }

    @Test
    public void testVoegIdentificeerbaarObjectToeAanBestaandeKey() {
        CommunicatieIdMap ids = new CommunicatieIdMap();

        PersoonBericht eerste = new PersoonBericht();
        eerste.setCommunicatieID("id");

        PersoonBericht tweede = new PersoonBericht();
        tweede.setCommunicatieID("id");

        ids.put(eerste);
        ids.put(tweede);

        Assert.assertEquals(2, ids.get("id").size());
        Assert.assertEquals(eerste, ids.get("id").get(0));
        Assert.assertEquals(tweede, ids.get("id").get(1));
    }

    @Test
    public void testVoegToeMetAndereKey() {
        List<Identificeerbaar> lijst = new ArrayList<Identificeerbaar>();
        List<Identificeerbaar> lijstNullKey = new ArrayList<Identificeerbaar>();

        CommunicatieIdMap ids = new CommunicatieIdMap();
        ids.put("ABC", lijst);
        ids.put(null, lijstNullKey);

        Assert.assertEquals(lijst, ids.get("abc"));
        Assert.assertEquals(lijstNullKey, ids.get(null));
    }


}

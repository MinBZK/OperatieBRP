/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import nl.bzk.brp.model.logisch.kern.Actie;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


public class AdministratieveHandelingBerichtTest {

    private AdministratieveHandelingBericht administratieveHandeling;
    private Actie                           actie1;
    private Actie                           actie2;
    private Actie                           actie3;

    @Before
    public void init() {
        this.administratieveHandeling = new HandelingGBABijhoudingActueelBericht();
        this.actie1 = new ActieConversieGBABericht();
        this.actie2 = new ActieConversieGBABericht();
        this.actie3 = new ActieConversieGBABericht();
    }

    private void zetActies(final Actie... acties) {
        Collection<Actie> actieCollectie = new ArrayList<Actie>();
        actieCollectie.addAll(Arrays.asList(acties));
        ReflectionTestUtils.setField(administratieveHandeling, "acties", actieCollectie);
    }

    @Test
    public void testGetHoofdActie() {
        // Null collectie.
        ReflectionTestUtils.setField(administratieveHandeling, "acties", null);
        Assert.assertEquals(null, administratieveHandeling.getHoofdActie());

        // Lege collectie.
        zetActies();
        Assert.assertEquals(null, administratieveHandeling.getHoofdActie());

        // Collectie met 1 item.
        zetActies(actie1);
        Assert.assertEquals(actie1, administratieveHandeling.getHoofdActie());

        // Collectie met meer items.
        zetActies(actie3, actie2, actie1);
        Assert.assertEquals(actie3, administratieveHandeling.getHoofdActie());
    }

    @Test
    public void testGetNevenActies() {
        // Null collectie.
        ReflectionTestUtils.setField(administratieveHandeling, "acties", null);
        Assert.assertEquals(0, administratieveHandeling.getNevenActies().size());

        // Lege collectie.
        zetActies();
        Assert.assertEquals(0, administratieveHandeling.getNevenActies().size());

        // Collectie met 1 item.
        zetActies(actie1);
        Assert.assertEquals(0, administratieveHandeling.getNevenActies().size());

        // Collectie met 2 items.
        zetActies(actie2, actie1);
        Assert.assertEquals(1, administratieveHandeling.getNevenActies().size());
        Assert.assertEquals(actie1, administratieveHandeling.getNevenActies().get(0));

        // Collectie met 3 items.
        zetActies(actie1, actie2, actie3);
        Assert.assertEquals(2, administratieveHandeling.getNevenActies().size());
        Assert.assertTrue(administratieveHandeling.getNevenActies().contains(actie2));
        Assert.assertTrue(administratieveHandeling.getNevenActies().contains(actie3));
    }

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie.validatie;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.model.validatie.Melding;
import org.junit.Test;

public class ValidatieUtilTest {

    @Test
    public void testIsWaardeNullOfLeegNullWaarde() {
        Assert.assertTrue(ValidatieUtil.isWaardeNullOfLeeg(null));
    }

    @Test
    public void testIsWaardeNullOfLeegLeegWaarde() {
        Assert.assertTrue(ValidatieUtil.isWaardeNullOfLeeg(""));
    }

    @Test
    public void testIsWaardeNullOfLeegSpatiesWaarde() {
        Assert.assertTrue(ValidatieUtil.isWaardeNullOfLeeg("        "));
    }

    @Test
    public void testIsWaardeNullOfLeegWelEenWaarde() {
        Assert.assertFalse(ValidatieUtil.isWaardeNullOfLeeg("  8748965      "));
    }

    @Test
    public void testControleerVerplichtVeldNull() {
        List<Melding> meldingen = new ArrayList<Melding>();
        ValidatieUtil.controleerVerplichtVeld(meldingen, null, "veld");
        Assert.assertFalse(meldingen.isEmpty());
    }

    @Test
    public void testControleerVerplichtLeegWaarde() {
        List<Melding> meldingen = new ArrayList<Melding>();
        ValidatieUtil.controleerVerplichtVeld(meldingen, "", "veld");
        Assert.assertFalse(meldingen.isEmpty());
    }

    @Test
    public void testControleerVerplichtLeegSpatiesWaarde() {
        List<Melding> meldingen = new ArrayList<Melding>();
        ValidatieUtil.controleerVerplichtVeld(meldingen, "        ", "veld");
        Assert.assertFalse(meldingen.isEmpty());
    }

    @Test
    public void testControleerVerplichtWelEenWaarde() {
        List<Melding> meldingen = new ArrayList<Melding>();
        ValidatieUtil.controleerVerplichtVeld(meldingen, "   546", "veld");
        Assert.assertTrue(meldingen.isEmpty());
    }
}

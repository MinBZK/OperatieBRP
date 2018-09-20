/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.regels.context;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bevraging.bijhouding.GeefDetailsPersoonBericht;
import org.junit.Assert;
import org.junit.Test;

public class BerichtRegelContextTest {

    @Test
    public void testGetNieuweSituatie() {
        final PersoonBericht persoonBericht = new PersoonBericht();
        final BerichtRegelContext berichtRegelContext = new BerichtRegelContext(persoonBericht);
        final BerichtIdentificeerbaar nieuweSituatie = berichtRegelContext.getNieuweSituatie();

        Assert.assertEquals(persoonBericht, nieuweSituatie);
    }

    @Test
    public void testGetSoortAdministratieveHandeling() {
        final PersoonBericht persoonBericht = new PersoonBericht();
        final BerichtRegelContext berichtRegelContext =
                new BerichtRegelContext(persoonBericht, SoortAdministratieveHandeling.DUMMY);
        final SoortAdministratieveHandeling soortAdministratieveHandeling =
                berichtRegelContext.getSoortAdministratieveHandeling();

        Assert.assertEquals(SoortAdministratieveHandeling.DUMMY, soortAdministratieveHandeling);
    }

    @Test
    public void testGetBericht() {
        final PersoonBericht persoonBericht = new PersoonBericht();
        final BerichtBericht bericht = new GeefDetailsPersoonBericht();
        final BerichtRegelContext berichtRegelContext =
                new BerichtRegelContext(persoonBericht, SoortAdministratieveHandeling.DUMMY, bericht);
        final BerichtBericht berichtBericht = berichtRegelContext.getBericht();

        Assert.assertEquals(bericht, berichtBericht);
        Assert.assertEquals(berichtBericht, berichtRegelContext.getSituatie());
    }

    @Test
    public void testGetBerichtViaAndereConstructor() {
        final BerichtBericht bericht = new GeefDetailsPersoonBericht();
        final BerichtRegelContext berichtRegelContext =
                new BerichtRegelContext(bericht);
        final BerichtBericht berichtBericht = berichtRegelContext.getBericht();

        Assert.assertEquals(bericht, berichtBericht);
    }
}

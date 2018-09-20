/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.synchronisatie.regels.stamgegeven;

import java.util.List;
import nl.bzk.brp.business.regels.context.BerichtRegelContext;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.StamgegevenAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.bericht.ber.BerichtParametersGroepBericht;
import nl.bzk.brp.model.synchronisatie.GeefSynchronisatieStamgegevenBericht;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests voor BRLV0024.
 */
public class BRLV0024Test {

    @Test
    public final void testVoerRegelUit() {
        final GeefSynchronisatieStamgegevenBericht bericht = new GeefSynchronisatieStamgegevenBericht();
        bericht.setParameters(new BerichtParametersGroepBericht());
        bericht.getParameters().setStamgegeven(new StamgegevenAttribuut("GemeenteTabel"));

        final BerichtRegelContext regelContext = new BerichtRegelContext(bericht);
        final List<BerichtIdentificeerbaar> berichtIdentificeerbaars = new BRLV0024().valideer(regelContext);
        Assert.assertTrue(berichtIdentificeerbaars.isEmpty());
    }

    @Test
    public final void testVoerRegelUitFoutiefStamgegeven() {
        final GeefSynchronisatieStamgegevenBericht bericht = new GeefSynchronisatieStamgegevenBericht();
        bericht.setParameters(new BerichtParametersGroepBericht());
        bericht.getParameters().setStamgegeven(new StamgegevenAttribuut("OperatieBRPMedewerkersTabel"));

        final BerichtRegelContext regelContext = new BerichtRegelContext(bericht);
        final List<BerichtIdentificeerbaar> berichtIdentificeerbaars = new BRLV0024().valideer(regelContext);
        Assert.assertFalse(berichtIdentificeerbaars.isEmpty());
    }

    @Test
    public final void testVoerRegelUitStamgegevenNietOpgegeven() {
        final GeefSynchronisatieStamgegevenBericht bericht = new GeefSynchronisatieStamgegevenBericht();
        bericht.setParameters(new BerichtParametersGroepBericht());

        final BerichtRegelContext regelContext = new BerichtRegelContext(bericht);
        final List<BerichtIdentificeerbaar> berichtIdentificeerbaars = new BRLV0024().valideer(regelContext);
        Assert.assertTrue(berichtIdentificeerbaars.isEmpty());
    }

    @Test
    public final void testGetRegel() {
        Assert.assertEquals(Regel.BRLV0024, new BRLV0024().getRegel());
    }
}

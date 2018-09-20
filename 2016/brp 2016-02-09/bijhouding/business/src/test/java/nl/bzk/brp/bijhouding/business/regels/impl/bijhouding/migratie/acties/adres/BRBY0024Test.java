/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.migratie.acties.adres;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieCorrectieAdresBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.bericht.kern.HandelingCorrectieAdresBericht;
import nl.bzk.brp.model.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.model.bijhouding.CorrigeerAdresBericht;
import org.junit.Assert;
import org.junit.Test;

public class BRBY0024Test {

    private final BRBY0024 brby0024 = new BRBY0024();

    @Test
    public void testGetRegel() {
        Assert.assertEquals("BRBY0024", brby0024.getRegel().getCode());
    }

    @Test
    public void testGeenOverlapEenActie() {
        final List<BerichtIdentificeerbaar> overtreders = brby0024.voerRegelUit(maakBericht(2001, 2005));
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testGeenOverlapEenActieGeenDeg() {
        final List<BerichtIdentificeerbaar> overtreders = brby0024.voerRegelUit(maakBericht(2001, null));
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testGeenOverlapTweeActies() {
        final List<BerichtIdentificeerbaar> overtreders = brby0024.voerRegelUit(maakBericht(2001, 2005, 2007, 2009));
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testGeenOverlapTweeActiesZonderDeg() {
        final List<BerichtIdentificeerbaar> overtreders = brby0024.voerRegelUit(maakBericht(2001, 2005, 2007, null));
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testGeenOverlapTweeActiesAndersom() {
        final List<BerichtIdentificeerbaar> overtreders = brby0024.voerRegelUit(maakBericht(2007, 2009, 2001, 2005));
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testWelOverlapTweeActies() {
        final List<BerichtIdentificeerbaar> overtreders = brby0024.voerRegelUit(maakBericht(2001, 2005, 2003, 2009));
        Assert.assertEquals(1, overtreders.size());
    }

    @Test
    public void testWelOverlapTweeActiesZonderDeg() {
        final List<BerichtIdentificeerbaar> overtreders = brby0024.voerRegelUit(maakBericht(2001, 2005, 2003, null));
        Assert.assertEquals(1, overtreders.size());
    }

    @Test
    public void testWelOverlapTweeActiesZonderDegAndersom() {
        final List<BerichtIdentificeerbaar> overtreders = brby0024.voerRegelUit(maakBericht(2003, null, 2001, 2005));
        Assert.assertEquals(1, overtreders.size());
    }

    @SuppressWarnings("deprecation")
    private BijhoudingsBericht maakBericht(final Integer ... actieDagDegs) {
        final BijhoudingsBericht bericht = new CorrigeerAdresBericht();
        final AdministratieveHandelingBericht handeling = new HandelingCorrectieAdresBericht();
        bericht.getStandaard().setAdministratieveHandeling(handeling);
        handeling.setActies(new ArrayList<ActieBericht>());
        for (int i = 0; i < actieDagDegs.length; i += 2) {
            final DatumAttribuut dag = new DatumAttribuut(new Date(actieDagDegs[i] - 1900, 1, 1));
            DatumAttribuut deg = null;
            if (actieDagDegs[i + 1] != null) {
                deg = new DatumAttribuut(new Date(actieDagDegs[i + 1] - 1900, 1, 1));
            }
            final ActieBericht actie = new ActieCorrectieAdresBericht();
            actie.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(dag));
            if (deg != null) {
                actie.setDatumEindeGeldigheid(new DatumEvtDeelsOnbekendAttribuut(deg));
            }
            handeling.getActies().add(actie);
        }
        return bericht;
    }

}

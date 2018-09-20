/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.overlijden;

import java.util.List;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieOverlijdenBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonOverlijdenGroepBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.junit.Assert;
import org.junit.Test;


public class BRBY0908Test {

    private final BRBY0908 brby0908 = new BRBY0908();

    @Test
    public void testDatumGelijk() {
        ActieBericht actie = new ActieRegistratieOverlijdenBericht();
        actie.setDatumAanvangGeldigheid(new Datum(20120101));

        PersoonOverlijdenGroepBericht overlijden = new PersoonOverlijdenGroepBericht();
        overlijden.setDatumOverlijden(new Datum(20120101));
        PersoonBericht persoon = new PersoonBericht();
        persoon.setOverlijden(overlijden);

        List<Melding> meldingen = brby0908.executeer(null, persoon, actie);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testDatumNietGelijk() {
        ActieBericht actie = new ActieRegistratieOverlijdenBericht();
        actie.setDatumAanvangGeldigheid(new Datum(20120101));

        PersoonOverlijdenGroepBericht overlijden = new PersoonOverlijdenGroepBericht();
        overlijden.setDatumOverlijden(new Datum(20120100));
        PersoonBericht persoon = new PersoonBericht();
        persoon.setOverlijden(overlijden);

        List<Melding> meldingen = brby0908.executeer(null, persoon, actie);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.BRBY0908, meldingen.get(0).getCode());
    }

}

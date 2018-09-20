/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.reisdocument;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonReisdocumentBericht;
import nl.bzk.brp.model.bericht.kern.PersoonReisdocumentStandaardGroepBericht;
import org.junit.Assert;
import org.junit.Test;

public class BRBY0037Test {

    @Test
    public void testRegelGaatAfGrensGeval() {
        final PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setReisdocumenten(new ArrayList<PersoonReisdocumentBericht>());
        persoonBericht.getReisdocumenten().add(maakPersoonReisDocumentBericht(20120101, 20220102));
        final List<BerichtEntiteit> berichtEntiteits = new BRBY0037().voerRegelUit(null, persoonBericht, null, null);
        Assert.assertEquals(persoonBericht.getReisdocumenten().get(0), berichtEntiteits.get(0));
    }

    @Test
    public void testRegelGaatNietAfGrensGeval() {
        final PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setReisdocumenten(new ArrayList<PersoonReisdocumentBericht>());
        persoonBericht.getReisdocumenten().add(maakPersoonReisDocumentBericht(20120101, 20220101));
        final List<BerichtEntiteit> berichtEntiteits = new BRBY0037().voerRegelUit(null, persoonBericht, null, null);
        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testGeenStandaardGroep() {
        final PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setReisdocumenten(new ArrayList<PersoonReisdocumentBericht>());
        persoonBericht.getReisdocumenten().add(new PersoonReisdocumentBericht());
        final List<BerichtEntiteit> berichtEntiteits = new BRBY0037().voerRegelUit(null, persoonBericht, null, null);
        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testGeenDataOpgegeven() {
        final PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setReisdocumenten(new ArrayList<PersoonReisdocumentBericht>());
        persoonBericht.getReisdocumenten().add(new PersoonReisdocumentBericht());
        persoonBericht.getReisdocumenten().get(0).setStandaard(new PersoonReisdocumentStandaardGroepBericht());
        final List<BerichtEntiteit> berichtEntiteits = new BRBY0037().voerRegelUit(null, persoonBericht, null, null);
        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0037, new BRBY0037().getRegel());
    }

    private PersoonReisdocumentBericht maakPersoonReisDocumentBericht(
            final Integer datumUitgifte, final Integer datumVoorzieneEindeGeldigheid)
    {
        final PersoonReisdocumentBericht persoonReisdocumentBericht = new PersoonReisdocumentBericht();
        persoonReisdocumentBericht.setStandaard(new PersoonReisdocumentStandaardGroepBericht());
        persoonReisdocumentBericht.getStandaard().setDatumUitgifte(new DatumEvtDeelsOnbekendAttribuut(datumUitgifte));
        persoonReisdocumentBericht.getStandaard().setDatumEindeDocument(
                new DatumEvtDeelsOnbekendAttribuut(datumVoorzieneEindeGeldigheid));
        return persoonReisdocumentBericht;
    }
}

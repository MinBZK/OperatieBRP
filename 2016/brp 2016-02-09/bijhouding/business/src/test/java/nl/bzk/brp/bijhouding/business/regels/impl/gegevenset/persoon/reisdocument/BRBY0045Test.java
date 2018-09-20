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
import org.junit.Before;
import org.junit.Test;


public class BRBY0045Test {

    private BRBY0045 brby0045;

    @Before
    public void init() {
        brby0045 = new BRBY0045();
    }

    @Test
    public void testRegelAfAlleenDatumBekend() {
        final PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setReisdocumenten(new ArrayList<PersoonReisdocumentBericht>());
        persoonBericht.getReisdocumenten().add(maakPersoonReisDocumentBericht(null, 20220101));
        final List<BerichtEntiteit> berichtEntiteits = brby0045.voerRegelUit(null, persoonBericht, null, null);
        Assert.assertFalse(berichtEntiteits.isEmpty());
    }

    @Test
    public void testRegelAfAlleenRedenVervallenBekend() {
        final PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setReisdocumenten(new ArrayList<PersoonReisdocumentBericht>());
        persoonBericht.getReisdocumenten().add(maakPersoonReisDocumentBericht("redenVervallen", null));
        final List<BerichtEntiteit> berichtEntiteits = brby0045.voerRegelUit(null, persoonBericht, null, null);
        Assert.assertFalse(berichtEntiteits.isEmpty());
    }

    @Test
    public void testRegelGaatNietAfZowelRedenVervallenAlsDatumOnbekend() {
        final PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setReisdocumenten(new ArrayList<PersoonReisdocumentBericht>());
        persoonBericht.getReisdocumenten().add(maakPersoonReisDocumentBericht(null, null));
        final List<BerichtEntiteit> berichtEntiteits = brby0045.voerRegelUit(null, persoonBericht, null, null);
        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testRegelGaatNietAfZowelRedenVervallenAlsDatumBekend() {
        final PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setReisdocumenten(new ArrayList<PersoonReisdocumentBericht>());
        persoonBericht.getReisdocumenten().add(maakPersoonReisDocumentBericht("redenVervallen", 20220101));
        final List<BerichtEntiteit> berichtEntiteits = brby0045.voerRegelUit(null, persoonBericht, null, null);
        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testGeenStandaardGroep() {
        final PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setReisdocumenten(new ArrayList<PersoonReisdocumentBericht>());
        persoonBericht.getReisdocumenten().add(new PersoonReisdocumentBericht());
        final List<BerichtEntiteit> berichtEntiteits = brby0045.voerRegelUit(null, persoonBericht, null, null);
        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testGeenReisDocumenten() {
        final PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setReisdocumenten(new ArrayList<PersoonReisdocumentBericht>());
        persoonBericht.getReisdocumenten().add(new PersoonReisdocumentBericht());
        persoonBericht.getReisdocumenten().get(0).setStandaard(new PersoonReisdocumentStandaardGroepBericht());
        final List<BerichtEntiteit> berichtEntiteits = brby0045.voerRegelUit(null, persoonBericht, null, null);
        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testGeenDataOpgegeven() {
        final PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setReisdocumenten(null);
        final List<BerichtEntiteit> berichtEntiteits = brby0045.voerRegelUit(null, persoonBericht, null, null);
        Assert.assertTrue(berichtEntiteits.isEmpty());
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0045, brby0045.getRegel());
    }

    private PersoonReisdocumentBericht maakPersoonReisDocumentBericht(final String redenVervallenCode,
            final Integer datumInhoudingVermissing)
    {
        final PersoonReisdocumentBericht persoonReisdocumentBericht = new PersoonReisdocumentBericht();
        persoonReisdocumentBericht.setStandaard(new PersoonReisdocumentStandaardGroepBericht());
        persoonReisdocumentBericht.getStandaard().setAanduidingInhoudingVermissingCode(redenVervallenCode);
        if (datumInhoudingVermissing != null) {
            persoonReisdocumentBericht.getStandaard().setDatumInhoudingVermissing(
                    new DatumEvtDeelsOnbekendAttribuut(datumInhoudingVermissing));
        }
        return persoonReisdocumentBericht;
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.reisdocument;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortNederlandsReisdocumentAttribuut;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BestaansPeriodeStamgegeven;
import nl.bzk.brp.model.basis.DatumBasisAttribuut;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonReisdocumentBericht;
import nl.bzk.brp.model.bericht.kern.PersoonReisdocumentStandaardGroepBericht;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


public class BRBY1026Test {

    @Test
    public void testReisdocumentenNull() {
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setReisdocumenten(new ArrayList<PersoonReisdocumentBericht>());
        persoonBericht.getReisdocumenten().add(maakPersoonReisDocumentBericht(20120101, null));
        Assert.assertEquals(0, new BRBY1026().getStamgegevensEnEntiteiten(persoonBericht).size());
    }

    @Test
    public void testSoortReisdocumentNull() {
        PersoonBericht persoonBericht = new PersoonBericht();
        Assert.assertEquals(0, new BRBY1026().getStamgegevensEnEntiteiten(persoonBericht).size());
    }

    @Test
    public void testGetStamgegevensEnEntiteiten() {
        final SoortNederlandsReisdocumentAttribuut nederlandseIdKaart = StatischeObjecttypeBuilder.NEDERLANDSE_ID_KAART;
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setReisdocumenten(new ArrayList<PersoonReisdocumentBericht>());
        persoonBericht.getReisdocumenten().add(maakPersoonReisDocumentBericht(20120101, nederlandseIdKaart));
        final Map<BestaansPeriodeStamgegeven, PersoonReisdocumentBericht> stamgegevensEnEntiteiten =
            new BRBY1026().getStamgegevensEnEntiteiten(persoonBericht);

        Assert.assertFalse(stamgegevensEnEntiteiten.isEmpty());
        Assert.assertEquals(persoonBericht.getReisdocumenten().get(0),
                stamgegevensEnEntiteiten.get(nederlandseIdKaart.getWaarde()));
    }

    @Test
    public void testGetPeilDatum() {
        PersoonReisdocumentBericht persoonReisdocumentBericht = maakPersoonReisDocumentBericht(20120505, null);
        final DatumBasisAttribuut peilDatum = new BRBY1026().getPeilDatum(null, persoonReisdocumentBericht);
        Assert.assertEquals(20120505, peilDatum.getWaarde().intValue());
    }

    private PersoonReisdocumentBericht maakPersoonReisDocumentBericht(final Integer datumUitgifte,
            final SoortNederlandsReisdocumentAttribuut srtNlDoc)
    {
        PersoonReisdocumentBericht persoonReisdocumentBericht = new PersoonReisdocumentBericht();
        persoonReisdocumentBericht.setStandaard(new PersoonReisdocumentStandaardGroepBericht());
        persoonReisdocumentBericht.getStandaard().setDatumUitgifte(new DatumEvtDeelsOnbekendAttribuut(datumUitgifte));
        if (srtNlDoc != null) {
            persoonReisdocumentBericht.setSoort(srtNlDoc);
        }
        return persoonReisdocumentBericht;
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY1026, new BRBY1026().getRegel());
    }

    @Test
    public void testGetLogger() {
        Assert.assertNotNull(new BRBY1026().getLogger());
    }

    @Test
    public void testOngeldig() {
        final SoortNederlandsReisdocumentAttribuut nederlandseIdKaart = StatischeObjecttypeBuilder.NEDERLANDSE_ID_KAART;
        ReflectionTestUtils.setField(nederlandseIdKaart.getWaarde(), "datumAanvangGeldigheid",
                new DatumEvtDeelsOnbekendAttribuut(20110101));
        ReflectionTestUtils.setField(nederlandseIdKaart.getWaarde(), "datumEindeGeldigheid",
                new DatumEvtDeelsOnbekendAttribuut(20120101));
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setReisdocumenten(new ArrayList<PersoonReisdocumentBericht>());
        persoonBericht.getReisdocumenten().add(maakPersoonReisDocumentBericht(20130101, nederlandseIdKaart));

        final List<BerichtEntiteit> berichtEntiteits = new BRBY1026().voerRegelUit(null, persoonBericht, null, null);
        Assert.assertEquals(persoonBericht.getReisdocumenten().get(0), berichtEntiteits.get(0));
    }

    @Test
    public void testGeldig() {
        final SoortNederlandsReisdocumentAttribuut nederlandseIdKaart = StatischeObjecttypeBuilder.NEDERLANDSE_ID_KAART;
        ReflectionTestUtils.setField(nederlandseIdKaart.getWaarde(), "datumAanvangGeldigheid",
                new DatumEvtDeelsOnbekendAttribuut(20110101));
        ReflectionTestUtils.setField(nederlandseIdKaart.getWaarde(), "datumEindeGeldigheid",
                new DatumEvtDeelsOnbekendAttribuut(20900101));
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setReisdocumenten(new ArrayList<PersoonReisdocumentBericht>());
        persoonBericht.getReisdocumenten().add(maakPersoonReisDocumentBericht(20130101, nederlandseIdKaart));

        final List<BerichtEntiteit> berichtEntiteits = new BRBY1026().voerRegelUit(null, persoonBericht, null, null);
        Assert.assertTrue(berichtEntiteits.isEmpty());
    }
}

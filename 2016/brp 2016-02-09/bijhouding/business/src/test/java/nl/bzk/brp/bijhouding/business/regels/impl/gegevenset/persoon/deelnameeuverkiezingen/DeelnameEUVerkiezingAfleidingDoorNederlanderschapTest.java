/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.deelnameeuverkiezingen;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieNationaliteitBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNationaliteitModel;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonNationaliteitHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Test klasse voor afgeleide beëindiging EU verkiezingen.
 * Heeft slechts enkele simpele testgevallen, omdat de daadwerkelijke historie logica
 * al in de smart set tests afgedekt wordt.
 */
public class DeelnameEUVerkiezingAfleidingDoorNederlanderschapTest {

    private DeelnameEUVerkiezingAfleidingDoorNederlanderschap afleiding;
    private PersoonHisVolledigImpl persoon;
    private ActieModel actie;
    private HisPersoonNationaliteitModel toegevoegdRecord;

    @Before
    public void init() {
        this.persoon = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();

        ActieBericht actieBericht = new ActieRegistratieNationaliteitBericht();
        actieBericht.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20100101));
        actieBericht.setTijdstipRegistratie(DatumTijdAttribuut.bouwDatumTijd(2010, 1, 1));
        this.actie = new ActieModel(actieBericht, null);

        this.persoon.getNationaliteiten().add(new PersoonNationaliteitHisVolledigImplBuilder(persoon, StatischeObjecttypeBuilder.NATIONALITEIT_NEDERLANDS.getWaarde())
                .nieuwStandaardRecord(actie)
                .eindeRecord()
                .build());
        this.toegevoegdRecord = this.persoon.getNationaliteiten().iterator().next().getPersoonNationaliteitHistorie().iterator().next();

        this.afleiding = new DeelnameEUVerkiezingAfleidingDoorNederlanderschap(persoon, actie, toegevoegdRecord);
    }

    @Test
    public void testRegel() {
        Assert.assertEquals(Regel.VR00016b, afleiding.getRegel());
    }

    @Test
    public void testZonderDeelnameEUVerkiezingen() {
        this.afleiding.leidAf();
        Assert.assertEquals(0, persoon.getPersoonDeelnameEUVerkiezingenHistorie().getAantal());
    }

    @Test
    public void testMetDeelnameEUVerkiezingenNationaliteitNederlands() {
        voegToeDeelnameEUVerkiezing();
        Assert.assertEquals(1, persoon.getPersoonDeelnameEUVerkiezingenHistorie().getAantal());
        this.afleiding.leidAf();

        Assert.assertEquals(1, persoon.getPersoonDeelnameEUVerkiezingenHistorie().getAantal());
        Assert.assertNotNull(persoon.getPersoonDeelnameEUVerkiezingenHistorie().iterator().next().getDatumTijdVerval());
        // In 2005 nog wel eu verkiezingen.
        Assert.assertNotNull(new PersoonView(persoon, DatumTijdAttribuut.bouwDatumTijd(2005, 1, 1)).getDeelnameEUVerkiezingen());
        // In 2011 niet meer eu verkiezingen ivm verkregen NL nationaliteit.
        Assert.assertNull(new PersoonView(persoon, DatumTijdAttribuut.bouwDatumTijd(2011, 1, 1)).getDeelnameEUVerkiezingen());
    }

    @Test
    public void testMetDeelnameEUVerkiezingenNationaliteitNietNederlands() {
        voegToeDeelnameEUVerkiezing();
        ReflectionTestUtils.setField(persoon.getNationaliteiten().iterator().next(), "nationaliteit", StatischeObjecttypeBuilder.NATIONALITEIT_SLOWAAKS);
        Assert.assertEquals(1, persoon.getPersoonDeelnameEUVerkiezingenHistorie().getAantal());
        this.afleiding.leidAf();

        Assert.assertEquals(1, persoon.getPersoonDeelnameEUVerkiezingenHistorie().getAantal());
        Assert.assertNull(persoon.getPersoonDeelnameEUVerkiezingenHistorie().iterator().next().getDatumTijdVerval());
        // In 2005 eu verkiezingen.
        Assert.assertNotNull(new PersoonView(persoon, DatumTijdAttribuut.bouwDatumTijd(2005, 1, 1)).getDeelnameEUVerkiezingen());
        // In 2011 nog steeds eu verkiezingen ivm verkregen niet NL nationaliteit.
        Assert.assertNotNull(new PersoonView(persoon, DatumTijdAttribuut.bouwDatumTijd(2011, 1, 1)).getDeelnameEUVerkiezingen());
    }

    private void voegToeDeelnameEUVerkiezing() {
        PersoonHisVolledigImpl dummy = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwDeelnameEUVerkiezingenRecord(20010101)
                    .indicatieDeelnameEUVerkiezingen(true)
                .eindeRecord()
                .build();
        // Hele nasty manier van snel een EU verkiezing record aanmaken: overnemen van een dummy persoon. :)
        persoon.getPersoonDeelnameEUVerkiezingenHistorie().voegToe(dummy.getPersoonDeelnameEUVerkiezingenHistorie().iterator().next());
    }

}

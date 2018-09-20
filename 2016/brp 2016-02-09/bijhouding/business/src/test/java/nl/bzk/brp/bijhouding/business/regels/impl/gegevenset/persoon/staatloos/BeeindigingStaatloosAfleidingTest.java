/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.staatloos;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieNationaliteitBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonIndicatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNationaliteitModel;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonIndicatieStaatloosHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonNationaliteitHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test klasse voor afgeleide beëindiging staatloos.
 * Heeft slechts enkele simpele testgevallen, omdat de daadwerkelijke historie logica
 * al in de smart set tests afgedekt wordt.
 */
public class BeeindigingStaatloosAfleidingTest {

    private BeeindigingStaatloosAfleiding afleiding;
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

        this.persoon.getNationaliteiten().add(new PersoonNationaliteitHisVolledigImplBuilder(persoon, null)
                .nieuwStandaardRecord(actie)
                .eindeRecord()
                .build());
        this.toegevoegdRecord = this.persoon.getNationaliteiten().iterator().next().getPersoonNationaliteitHistorie().iterator().next();

        this.afleiding = new BeeindigingStaatloosAfleiding(persoon, actie, toegevoegdRecord);
    }

    @Test
    public void testRegel() {
        Assert.assertEquals(Regel.VR00005a, afleiding.getRegel());
    }

    @Test
    public void testZonderIndicatieStaatloos() {
        this.afleiding.leidAf();
        Assert.assertEquals(0, persoon.getIndicaties().size());
    }

    @Test
    public void testMetIndicatieStaatloos() {
        voegToeStaatloos();
        Assert.assertEquals(1, persoon.getIndicaties().size());
        PersoonIndicatieHisVolledig staatloosHisVolledig = persoon.getIndicaties().iterator().next();
        Assert.assertEquals(1, staatloosHisVolledig.getPersoonIndicatieHistorie().getAantal());
        this.afleiding.leidAf();

        Assert.assertEquals(1, persoon.getIndicaties().size());
        Assert.assertEquals(2, staatloosHisVolledig.getPersoonIndicatieHistorie().getAantal());
        // In 2005 nog wel staatloos.
        Assert.assertEquals(1, new PersoonView(persoon, DatumTijdAttribuut.bouwDatumTijd(2005, 1, 1), new DatumAttribuut(20050101)).getIndicaties().size());
        // In 2011 niet meer staatloos ivm verkregen nationaliteit.
        Assert.assertEquals(0, new PersoonView(persoon, DatumTijdAttribuut.bouwDatumTijd(2011, 1, 1), new DatumAttribuut(20110101)).getIndicaties().size());
    }

    private void voegToeStaatloos() {
        persoon.setIndicatieStaatloos(new PersoonIndicatieStaatloosHisVolledigImplBuilder()
                .nieuwStandaardRecord(20010101, null, 20010101)
                    .waarde(Ja.J)
                .eindeRecord()
                .build());
    }

}

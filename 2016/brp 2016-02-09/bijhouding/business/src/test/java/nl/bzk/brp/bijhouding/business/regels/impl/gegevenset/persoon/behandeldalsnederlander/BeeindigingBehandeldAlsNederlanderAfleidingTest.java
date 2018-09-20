/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.behandeldalsnederlander;

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
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonIndicatieBehandeldAlsNederlanderHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonNationaliteitHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Test klasse voor afgeleide beëindiging behandeld als nederlander.
 * Heeft slechts enkele simpele testgevallen, omdat de daadwerkelijke historie logica
 * al in de smart set tests afgedekt wordt.
 */
public class BeeindigingBehandeldAlsNederlanderAfleidingTest {

    private BeeindigingBehandeldAlsNederlanderAfleiding afleiding;
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

        this.afleiding = new BeeindigingBehandeldAlsNederlanderAfleiding(persoon, actie, toegevoegdRecord);
    }

    @Test
    public void testRegel() {
        Assert.assertEquals(Regel.VR00007a, afleiding.getRegel());
    }

    @Test
    public void testZonderIndicatieBehandeldAlsNederlander() {
        this.afleiding.leidAf();
        Assert.assertEquals(0, persoon.getIndicaties().size());
    }

    @Test
    public void testMetIndicatieBehandeldAlsNederlanderNationaliteitNederlands() {
        voegToeBehandeldAlsNederlander();
        Assert.assertEquals(1, persoon.getIndicaties().size());
        PersoonIndicatieHisVolledig behandeldAlsNederlanderHisVolledig = persoon.getIndicaties().iterator().next();
        Assert.assertEquals(1, behandeldAlsNederlanderHisVolledig.getPersoonIndicatieHistorie().getAantal());
        this.afleiding.leidAf();

        Assert.assertEquals(1, persoon.getIndicaties().size());
        Assert.assertEquals(2, behandeldAlsNederlanderHisVolledig.getPersoonIndicatieHistorie().getAantal());
        // In 2005 nog wel behandeld als nederlander.
        Assert.assertEquals(1, new PersoonView(persoon, DatumTijdAttribuut.bouwDatumTijd(2005, 1, 1), new DatumAttribuut(20050101)).getIndicaties().size());
        // In 2011 niet meer behandeld als nederlander ivm verkregen NL nationaliteit.
        Assert.assertEquals(0, new PersoonView(persoon, DatumTijdAttribuut.bouwDatumTijd(2011, 1, 1), new DatumAttribuut(20110101)).getIndicaties().size());
    }

    @Test
    public void testMetIndicatieBehandeldAlsNederlanderNationaliteitNietNederlands() {
        voegToeBehandeldAlsNederlander();
        ReflectionTestUtils.setField(persoon.getNationaliteiten().iterator().next(), "nationaliteit", StatischeObjecttypeBuilder.NATIONALITEIT_SLOWAAKS);
        Assert.assertEquals(1, persoon.getIndicaties().size());
        PersoonIndicatieHisVolledig behandeldAlsNederlanderHisVolledig = persoon.getIndicaties().iterator().next();
        Assert.assertEquals(1, behandeldAlsNederlanderHisVolledig.getPersoonIndicatieHistorie().getAantal());
        this.afleiding.leidAf();

        Assert.assertEquals(1, persoon.getIndicaties().size());
        Assert.assertEquals(1, behandeldAlsNederlanderHisVolledig.getPersoonIndicatieHistorie().getAantal());
        // In 2005 wel behandeld als nederlander.
        Assert.assertEquals(1, new PersoonView(persoon, DatumTijdAttribuut.bouwDatumTijd(2005, 1, 1), new DatumAttribuut(20050101)).getIndicaties().size());
        // In 2011 nog steeds behandeld als nederlander ivm verkregen niet NL nationaliteit.
        Assert.assertEquals(1, new PersoonView(persoon, DatumTijdAttribuut.bouwDatumTijd(2011, 1, 1), new DatumAttribuut(20110101)).getIndicaties().size());
    }

    private void voegToeBehandeldAlsNederlander() {
        persoon.setIndicatieBehandeldAlsNederlander(new PersoonIndicatieBehandeldAlsNederlanderHisVolledigImplBuilder()
                .nieuwStandaardRecord(20010101, null, 20010101)
                    .waarde(Ja.J)
                .eindeRecord()
                .build());
    }

}

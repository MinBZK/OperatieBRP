/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.overlijden;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonInschrijvingGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonOverlijdenGroepBericht;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.util.DatumUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;


public class BRBY0902Test {

    private BRBY0902 brby0902;


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        brby0902 = new BRBY0902();
    }

    @Test
    public void testDrieNullen() throws Exception {
        List<Melding> meldingen = brby0902.executeer(null, null, null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testPersoonOverledenMetModelNull() throws Exception {
        PersoonBericht persoonBericht = new PersoonBericht();
        PersoonOverlijdenGroepBericht overlijdenGroep = new PersoonOverlijdenGroepBericht();
        persoonBericht.setOverlijden(overlijdenGroep);

        List<Melding> meldingen = brby0902.executeer(null, persoonBericht, null);
        // gaat goed, omdat de persoonModel niet bestaat.
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testLeegBerichtNullModel() throws Exception {
        PersoonBericht bericht = new PersoonBericht();
        List<Melding> meldingen = brby0902.executeer(null, bericht, null);
        // gaat ook goed, omdat bericht.persoon.getOverlijden groep is null.
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testLeegBericht() throws Exception {
        // beide datas hebben geen laatsteWijziging en overlijdenDatum.
        List<Melding> meldingen = brby0902.executeer(
            maakOverLedenPersoon(null, null),
            maakOverLedenPersoon(null, null),
            null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testLeegBericht2() throws Exception {
        List<Melding> meldingen = brby0902.executeer(
            maakOverLedenPersoon(null, null),
            maakOverLedenPersoon(20110830, null),
            null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testOverlijdenCorrect() throws Exception {
        List<Melding> meldingen = brby0902.executeer(
            maakOverLedenPersoon(null, "20110829"),
            maakOverLedenPersoon(20110830, null),
            null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testOverlijdenEerderDanEersteInschrijving() throws Exception {
        List<Melding> meldingen = brby0902.executeer(
            maakOverLedenPersoon(null, "20110831"),
            maakOverLedenPersoon(20110830, null),
            null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(SoortMelding.FOUT, meldingen.get(0).getSoort());
        Assert.assertEquals(MeldingCode.BRBY0902, meldingen.get(0).getCode());
        Assert.assertEquals("id.pers.overlijden", meldingen.get(0).getCommunicatieID());
    }

    @Test
    public void testOverlijdenZelfdeDagEersteInschrijving() throws Exception {
        List<Melding> meldingen = brby0902.executeer(
            maakOverLedenPersoon(null, "20110830"),
            maakOverLedenPersoon(20110830, null),
            null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testOverlijdenVandaag() throws Exception {
        Integer vandaagInt = DatumUtil.vandaag().getWaarde();
        List<Melding> meldingen = brby0902.executeer(
            maakOverLedenPersoon(null, "20110830"),
            maakOverLedenPersoon(vandaagInt, null),
            null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testOverlijdenToekomst() throws Exception {
        Integer morgenInt = DatumUtil.morgen().getWaarde();
        List<Melding> meldingen = brby0902.executeer(
            maakOverLedenPersoon(null, "20110830"),
            maakOverLedenPersoon(morgenInt, null),
            null);
        Assert.assertEquals(1, meldingen.size());
    }

    /**
     * Bouw een overleden persoon op
     *
     * @param datumOverlijden met overleden datum (null == WEL overleden groep, geen datum)
     * @param eersteInschrijving, null == geen
     * @return de persoon.
     * @throws ParseException wrong timestamp format.
     */
    private Persoon maakOverLedenPersoon(final Integer datumOverlijden, final String eersteInschrijving)
        throws ParseException
    {
        PersoonBericht persoon = new PersoonBericht();
        PersoonOverlijdenGroepBericht overlijdenGroep = new PersoonOverlijdenGroepBericht();
        persoon.setOverlijden(overlijdenGroep);
        if (null != datumOverlijden) {
            overlijdenGroep.setCommunicatieID("id.pers.overlijden");
            overlijdenGroep.setDatumOverlijden(new Datum(datumOverlijden));
        }
        if (null != eersteInschrijving) {
            Date date = new SimpleDateFormat("yyyyMMdd").parse(eersteInschrijving);
            persoon.setInschrijving(new PersoonInschrijvingGroepBericht());
            persoon.getInschrijving().setDatumInschrijving(DatumUtil.dateToDatum(date));
        }
        return persoon;
    }
}

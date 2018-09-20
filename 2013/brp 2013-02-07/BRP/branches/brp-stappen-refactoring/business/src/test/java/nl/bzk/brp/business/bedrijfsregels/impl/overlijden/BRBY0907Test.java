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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.bericht.kern.PersoonAfgeleidAdministratiefGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonOverlijdenGroepBericht;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;


public class BRBY0907Test {

    private BRBY0907 brby0907;


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        brby0907 = new BRBY0907();
    }

    @Test
    public void testDrieNullen() throws Exception {
        List<Melding> meldingen = brby0907.executeer(null, null, null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testPersoonOverledenMetModelNull() throws Exception {
        PersoonBericht persoonBericht = new PersoonBericht();
        PersoonOverlijdenGroepBericht overlijdenGroep = new PersoonOverlijdenGroepBericht();
        persoonBericht.setOverlijden(overlijdenGroep);

        List<Melding> meldingen = brby0907.executeer(null, persoonBericht, null);
        // gaat goed, omdat de persoonModel niet bestaat.
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testLeegBerichtNullModel() throws Exception {
        PersoonBericht bericht = new PersoonBericht();
        List<Melding> meldingen = brby0907.executeer(null, bericht, null);
        // gaat ook goed, omdat bericht.persoon.getOverlijden groep is null.
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testLeegBericht() throws Exception {
        // beide datas hebben geen laatsteWijziging en overlijdenDatum.
        List<Melding> meldingen = brby0907.executeer(
            maakOverLedenPersoon(null, null),
            maakOverLedenPersoon(null, null),
            null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testOverlijdenZonderHuidigAdministratie() throws Exception {
        // huidig persoon heeft geen afgeleide afministratie
        List<Melding> meldingen = brby0907.executeer(
            maakOverLedenPersoon(null, null),
            maakOverLedenPersoon(20110831, null),
            null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testOverlijdenCorrect() throws Exception {
        // huidig persoon heeft ouder afgeleide afministratie
        List<Melding> meldingen = brby0907.executeer(
            maakOverLedenPersoon(null, "20110707 17:45:23.33"),
            maakOverLedenPersoon(20110831, null),
            null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testOverlijdenOpZelfdeDag() throws Exception {
        // huidig persoon heeft wijziging op dezelfde dag => Waarschuwing
        List<Melding> meldingen = brby0907.executeer(
            maakOverLedenPersoon(null, "2011831 17:45:23.33"),
            maakOverLedenPersoon(20110831, null),
            null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(SoortMelding.WAARSCHUWING, meldingen.get(0).getSoort());
        Assert.assertEquals(MeldingCode.BRBY0907, meldingen.get(0).getCode());
        Assert.assertEquals("id.pers.overlijden", meldingen.get(0).getCommunicatieID());
    }

    @Test
    public void testOverlijdenOpZelfdeDag00uur() throws Exception {
        // huidig persoon heeft wijziging op dezelfde dag (00:00:00) = OK
        List<Melding> meldingen = brby0907.executeer(
            maakOverLedenPersoon(null, "2011831 00:00:00.00"),
            maakOverLedenPersoon(20110831, null),
            null);
        Assert.assertEquals(1, meldingen.size());
    }

    @Test
    public void testOverlijdenOpVolgendedag() throws Exception {
        // huidig persoon heeft wijziging op de volgende dag => Waarschuwing
        List<Melding> meldingen = brby0907.executeer(
            maakOverLedenPersoon(null, "20110901 17:45:23.33"),
            maakOverLedenPersoon(20110831, null),
            null);
        Assert.assertEquals(1, meldingen.size());

    }

    @Test
    public void testHuidigDatumOverlijdenNietTerzake() throws Exception {
        // HuidigDatum overlijden doet er niet terzake
        List<Melding> meldingen = brby0907.executeer(
            maakOverLedenPersoon(20130831, "2000831 00:00:00.00"),
            maakOverLedenPersoon(20110831, null),
            null);
        Assert.assertEquals(0, meldingen.size());
    }


    /**
     * Bouw een overleden persoon op
     *
     * @param datumOverlijden met overleden datum (null == WEL overleden groep, geen datum)
     * @param laatsteWijziging met tijdstip laatste wijzigingen (voor huidige situatie), null == geen
     * @return de persoon.
     * @throws ParseException wrong timestamp format.
     */
    private Persoon maakOverLedenPersoon(final Integer datumOverlijden, final String laatsteWijziging)
        throws ParseException
    {
        PersoonBericht persoon = new PersoonBericht();
        PersoonOverlijdenGroepBericht overlijdenGroep = new PersoonOverlijdenGroepBericht();
        persoon.setOverlijden(overlijdenGroep);
        if (null != datumOverlijden) {
            overlijdenGroep.setCommunicatieID("id.pers.overlijden");
            overlijdenGroep.setDatumOverlijden(new Datum(datumOverlijden));
        }
        if (null != laatsteWijziging) {
            Date date = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(laatsteWijziging);
            persoon.setAfgeleidAdministratief(new PersoonAfgeleidAdministratiefGroepBericht());
            persoon.getAfgeleidAdministratief().setTijdstipLaatsteWijziging(new DatumTijd(date));
        }
        return persoon;
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.overlijden.acties.registratieoverlijden;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonOverlijdenGroepBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BRBY0907Test {

    private static final String ID_PERS = "id.pers";
    private BRBY0907 brby0907;

    @Before
    public void init() {
        brby0907 = new BRBY0907();
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0907, brby0907.getRegel());
    }

    @Test
    public void testLeegBericht() throws Exception {
        // beide datas hebben geen laatsteWijziging en overlijdenDatum.
        final List<BerichtEntiteit> resultaat = brby0907.voerRegelUit(
            maakOverledenHuidigePersoon(null, null),
            maakOverledenNieuwePersoon(null),
            null, null);
        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testOverlijdenZonderHuidigAdministratie() throws Exception {
        // huidig persoon heeft geen afgeleide afministratie
        final List<BerichtEntiteit> resultaat = brby0907.voerRegelUit(
            maakOverledenHuidigePersoon(null, null),
            maakOverledenNieuwePersoon(20110831),
            null, null);
        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testOverlijdenCorrect() throws Exception {
        // huidig persoon heeft ouder afgeleide afministratie
        final List<BerichtEntiteit> resultaat = brby0907.voerRegelUit(
            maakOverledenHuidigePersoon(null, "20110707 17:45:23.33"),
            maakOverledenNieuwePersoon(20110831),
            null, null);
        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testOverlijdenOpZelfdeDag() throws Exception {
        // huidig persoon heeft wijziging op dezelfde dag => OK
        final List<BerichtEntiteit> resultaat = brby0907.voerRegelUit(
            maakOverledenHuidigePersoon(null, "20110831 17:45:23.33"),
            maakOverledenNieuwePersoon(20110831),
            null, null);
        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testOverlijdenOpZelfdeDag00uur() throws Exception {
        // huidig persoon heeft wijziging op dezelfde dag (00:00:00) = OK
        final List<BerichtEntiteit> resultaat = brby0907.voerRegelUit(
            maakOverledenHuidigePersoon(null, "20110831 00:00:00.00"),
            maakOverledenNieuwePersoon(20110831),
            null, null);
        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testOverlijdenOpVolgendedag() throws Exception {
        // huidig persoon heeft wijziging op de volgende dag => Waarschuwing
        final List<BerichtEntiteit> resultaat = brby0907.voerRegelUit(
            maakOverledenHuidigePersoon(null, "20110901 17:45:23.33"),
            maakOverledenNieuwePersoon(20110831),
            null, null);
        Assert.assertEquals(1, resultaat.size());
        Assert.assertEquals(ID_PERS, resultaat.get(0).getCommunicatieID());
    }

    @Test
    public void testOverlijdenOpVolgendedag00uur() throws Exception {
        // huidig persoon heeft wijziging op de volgende dag => Waarschuwing
        final List<BerichtEntiteit> resultaat = brby0907.voerRegelUit(
            maakOverledenHuidigePersoon(null, "20110901 00:00:00.00"),
            maakOverledenNieuwePersoon(20110831),
            null, null);
        Assert.assertEquals(1, resultaat.size());
        Assert.assertEquals(ID_PERS, resultaat.get(0).getCommunicatieID());
    }

    @Test
    public void testHuidigDatumOverlijdenNietTerzake() throws Exception {
        // HuidigDatum overlijden doet er niet terzake
        final List<BerichtEntiteit> resultaat = brby0907.voerRegelUit(
            maakOverledenHuidigePersoon(20130831, "2000831 00:00:00.00"),
            maakOverledenNieuwePersoon(20110831),
            null, null);
        Assert.assertEquals(0, resultaat.size());
    }


    /**
     * Bouw een overleden persoon op
     *
     * @param datumOverlijden met overleden datum (null == WEL overleden groep, geen datum)
     * @param laatsteWijziging met tijdstip laatste wijzigingen (voor huidige situatie), null == geen
     * @return de persoon.
     * @throws ParseException wrong timestamp format.
     */
    private PersoonView maakOverledenHuidigePersoon(final Integer datumOverlijden, final String laatsteWijziging)
        throws ParseException
    {
        PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        if (datumOverlijden != null) {
            builder = builder.nieuwOverlijdenRecord(20010101).datumOverlijden(datumOverlijden).eindeRecord();
        }
        if (laatsteWijziging != null) {
            final Date date = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(laatsteWijziging);
            builder = builder.nieuwAfgeleidAdministratiefRecord(20010101).tijdstipLaatsteWijziging(date).eindeRecord();
        }
        return new PersoonView(builder.build());
    }

    /**
     * Bouw een overleden persoon op
     *
     * @param datumOverlijden met overleden datum (null == WEL overleden groep, geen datum)
     * @return de persoon.
     * @throws ParseException wrong timestamp format.
     */
    private PersoonBericht maakOverledenNieuwePersoon(final Integer datumOverlijden) throws ParseException {
        final PersoonBericht persoon = new PersoonBericht();
        persoon.setCommunicatieID(ID_PERS);
        final PersoonOverlijdenGroepBericht overlijdenGroep = new PersoonOverlijdenGroepBericht();
        persoon.setOverlijden(overlijdenGroep);
        if (null != datumOverlijden) {
            overlijdenGroep.setCommunicatieID("id.pers.overlijden");
            overlijdenGroep.setDatumOverlijden(new DatumEvtDeelsOnbekendAttribuut(datumOverlijden));
        }
        return persoon;
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.migratie;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.business.bedrijfsregels.util.ActieBerichtBuilder;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LangeNaamEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Woonplaatscode;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Plaats;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.util.PersoonAdresBuilder;
import nl.bzk.brp.util.PersoonBuilder;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.junit.Before;
import org.junit.Test;


/** Unit test voor de {@link BRBY0531} bedrijfsregel. */
public class BRBY0531Test {

    private BRBY0531 brby0531;

    @Before
    public void init() {
        brby0531 = new BRBY0531();
    }

    @Test
    public void testCorrectePeriodePlaatsGeenPeriode() {
        // happy flow
        List<Melding> meldingen = brby0531.executeer(
            null,
            maakPersoonMetPlaats(maakPlaats("24", "mijn plaats", null, null)),
            maakActie("id.actie1", 20120303, 20120404));
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testCorrectePeriodePlaatsWelPeriode() {
        List<Melding> meldingen = brby0531.executeer(
            null,
            maakPersoonMetPlaats(maakPlaats("24", "mijn plaats", 19000101, 25000101)),
            maakActie("id.actie1", 20120303, 20120404));
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testCorrectePeriodePlaatsZonderEinde() {
        List<Melding> meldingen = brby0531.executeer(
            null,
            maakPersoonMetPlaats(maakPlaats("24", "mijn plaats", 19000101, null)),
            maakActie("id.actie1", 20120303, 20120404));
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testPeriodeZonderDatumsActieZonderDatums() {
        List<Melding> meldingen = brby0531.executeer(
            null,
            maakPersoonMetPlaats(maakPlaats("24", "mijn plaats", null, null)),
            maakActie("id.actie1", null, null));
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testCorrectePeriodeActieZonderDatums() {
        List<Melding> meldingen = brby0531.executeer(
            null,
            maakPersoonMetPlaats(maakPlaats("24", "mijn plaats", 19000101, null)),
            maakActie("id.actie1", null, null));
        Assert.assertEquals(1, meldingen.size());
    }

    @Test
    public void testCorrectePeriodeActieZonderEinde() {
        List<Melding> meldingen = brby0531.executeer(
            null,
            maakPersoonMetPlaats(maakPlaats("24", "mijn plaats", 19000101, null)),
            maakActie("id.actie1", 20010101, null));
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testCorrectePeriodeActieZonderEinde2() {
        List<Melding> meldingen = brby0531.executeer(
            null,
            maakPersoonMetPlaats(maakPlaats("24", "mijn plaats", 19000101, null)),
            maakActie("id.actie1", 19000101, null));
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testAfgelopenPeriodeActieZonderEinde() {
        List<Melding> meldingen = brby0531.executeer(
            null,
            maakPersoonMetPlaats(maakPlaats("24", "mijn plaats", 20000101, null)),
            maakActie("id.actie1", 19911231, null));
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.BRBY0531, meldingen.get(0).getCode());
        Assert.assertEquals(SoortMelding.DEBLOKKEERBAAR, meldingen.get(0).getSoort());
        Assert.assertEquals("adres1", meldingen.get(0).getCommunicatieID());
    }

    @Test
    public void testAfgelopenPeriodeActieZonderEinde2() {
        List<Melding> meldingen = brby0531.executeer(
            null,
            maakPersoonMetPlaats(maakPlaats("24", "mijn plaats", 20000101, null)),
            maakActie("id.actie1", 19911231, 19911231));
        Assert.assertEquals(1, meldingen.size());
    }

    @Test
    public void testVerschillendeDatums1() {
        List<Melding> meldingen = brby0531.executeer(
            null,
            maakPersoonMetPlaats(maakPlaats("24", "mijn plaats", 19920101, 200020101)),
            maakActie("id.actie1", 19911231, null));
        Assert.assertEquals(1, meldingen.size());
    }

    @Test
    public void testVerschillendeDatums2() {
        List<Melding> meldingen = brby0531.executeer(
            null,
            maakPersoonMetPlaats(maakPlaats("24", "mijn plaats", 19920101, 200020101)),
            maakActie("id.actie1", 19910231, 19911231));
        Assert.assertEquals(1, meldingen.size());
    }

    @Test
    public void testVerschillendeDatums3() {
        List<Melding> meldingen = brby0531.executeer(
            null,
            maakPersoonMetPlaats(maakPlaats("24", "mijn plaats", 19920101, 200020101)),
            maakActie("id.actie1", 19910231, 19970231));
        Assert.assertEquals(1, meldingen.size());
    }

    @Test
    public void testVerschillendeDatums4() {
        List<Melding> meldingen = brby0531.executeer(
            null,
            maakPersoonMetPlaats(maakPlaats("24", "mijn plaats", 19920101, 200020101)),
            maakActie("id.actie1", 19910231, 20070231));
        Assert.assertEquals(1, meldingen.size());
    }

    @Test
    public void testVerschillendeDatums5() {
        List<Melding> meldingen = brby0531.executeer(
            null,
            maakPersoonMetPlaats(maakPlaats("24", "mijn plaats", 19920101, 200020101)),
            maakActie("id.actie1", 19920101, 200020101));
        Assert.assertEquals(0, meldingen.size());
    }


    @Test
    public void testVerschillendeDatums6() {
        List<Melding> meldingen = brby0531.executeer(
            null,
            maakPersoonMetPlaats(maakPlaats("24", "mijn plaats", 19920101, 200020101)),
            maakActie("id.actie1", 200020102, 200040101));
        Assert.assertEquals(1, meldingen.size());
    }

    @Test
    public void testVerschillendeDatums7() {
        List<Melding> meldingen = brby0531.executeer(
            null,
            maakPersoonMetPlaats(maakPlaats("24", "mijn plaats", 19920101, 200020101)),
            maakActie("id.actie1", 200011231, 200020101));
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testVerschillendeDatums8() {
        List<Melding> meldingen = brby0531.executeer(
            null,
            maakPersoonMetPlaats(maakPlaats("24", "mijn plaats", 19920101, 200020101)),
            maakActie("id.actie1", 200020101, 200020102));
        Assert.assertEquals(1, meldingen.size());
    }

    private Actie maakActie(final String verzendendId, final Integer begin, final Integer eind) {
        ActieBericht actie = ActieBerichtBuilder.bouwNieuweActie(SoortActie.CORRECTIE_ADRES)
                                                .setDatumAanvang(begin)
                                                .setDatumEinde(eind)
                                                .getActie();
        actie.setCommunicatieID(verzendendId);
        return actie;
    }

    private PersoonBericht maakPersoonMetPlaats(final Plaats plaats) {
        PersoonBericht persoon = PersoonBuilder.bouwPersoon(
            1234567890, Geslachtsaanduiding.MAN,
            19660606, null, "vn", "vg", "gsln");

        PersoonAdresBericht paBericht = PersoonAdresBuilder.bouwWoonadres("NOR", 123, "1234HH",
            plaats,
            StatischeObjecttypeBuilder.GEMEENTE_BREDA,
            20120713);
        paBericht.setCommunicatieID("adres1");
        persoon.setAdressen(new ArrayList<PersoonAdresBericht>());
        persoon.getAdressen().add(paBericht);
        return persoon;
    }

    private Plaats maakPlaats(final String code, final String naam, final Integer begin, final Integer eind) {
        Datum datumAanvang = null;
        Datum datumEinde = null;
        if (begin != null) {
            datumAanvang = new Datum(begin);
        }
        if (eind != null) {
            datumEinde = new Datum(eind);
        }

        Plaats plaats = new Plaats(new Woonplaatscode(Short.parseShort(code)), new LangeNaamEnumeratiewaarde(naam),
                                   datumAanvang,
                                   datumEinde);
        return plaats;
    }
}

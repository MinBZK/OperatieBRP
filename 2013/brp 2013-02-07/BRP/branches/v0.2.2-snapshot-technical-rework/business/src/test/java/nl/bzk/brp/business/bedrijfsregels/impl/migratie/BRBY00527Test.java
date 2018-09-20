/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.migratie;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.business.bedrijfsregels.util.ActieBerichtBuilder;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Gemeentecode;
import nl.bzk.brp.model.attribuuttype.NaamEnumeratiewaarde;
import nl.bzk.brp.model.objecttype.bericht.ActieBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonAdresBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Geslachtsaanduiding;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortActie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortMelding;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.util.PersoonAdresBuilder;
import nl.bzk.brp.util.PersoonBuilder;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class BRBY00527Test {

    private BRBY0527 brby0527;

    @Before
    public void init() {
        brby0527 = new BRBY0527();
    }

    @Test
    public void testCorrectePeriodeGemeenteGeenPeriode() {
        // happy flow
        List<Melding> meldingen =
            brby0527.executeer(null, maakPersoonMetPlaats(maakGemeente((short) 24, "gemeente", null, null)),
                    maakActie("id.actie1", 20120303, 20120404));
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testCorrectePeriodeGemeenteWelPeriode() {
        List<Melding> meldingen =
            brby0527.executeer(null, maakPersoonMetPlaats(maakGemeente((short) 24, "gemeente", 19000101, 25000101)),
                    maakActie("id.actie1", 20120303, 20120404));
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testCorrectePeriodeGemeenteZonderEinde() {
        List<Melding> meldingen =
            brby0527.executeer(null, maakPersoonMetPlaats(maakGemeente((short) 24, "gemeente", 19000101, null)),
                    maakActie("id.actie1", 20120303, 20120404));
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testPeriodeZonderDatumsActieZonderDatums() {
        List<Melding> meldingen =
            brby0527.executeer(null, maakPersoonMetPlaats(maakGemeente((short) 24, "gemeente", null, null)),
                    maakActie("id.actie1", null, null));
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testCorrectePeriodeActieZonderDatums() {
        List<Melding> meldingen =
            brby0527.executeer(null, maakPersoonMetPlaats(maakGemeente((short) 24, "gemeente", 19000101, null)),
                    maakActie("id.actie1", null, null));
        Assert.assertEquals(1, meldingen.size());
    }

    @Test
    public void testCorrectePeriodeActieZonderEinde() {
        List<Melding> meldingen =
            brby0527.executeer(null, maakPersoonMetPlaats(maakGemeente((short) 24, "gemeente", 19000101, null)),
                    maakActie("id.actie1", 20010101, null));
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testCorrectePeriodeActieZonderEinde2() {
        List<Melding> meldingen =
            brby0527.executeer(null, maakPersoonMetPlaats(maakGemeente((short) 24, "gemeente", 19000101, null)),
                    maakActie("id.actie1", 19000101, null));
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testAfgelopenPeriodeActieZonderEinde() {
        List<Melding> meldingen =
            brby0527.executeer(null, maakPersoonMetPlaats(maakGemeente((short) 24, "gemeente", 20000101, null)),
                    maakActie("id.actie1", 19911231, null));
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.BRBY0527, meldingen.get(0).getCode());
        Assert.assertEquals(SoortMelding.OVERRULEBAAR, meldingen.get(0).getSoort());
    }

    @Test
    public void testAfgelopenPeriodeActieZonderEinde2() {
        List<Melding> meldingen =
            brby0527.executeer(null, maakPersoonMetPlaats(maakGemeente((short) 24, "gemeente", 20000101, null)),
                    maakActie("id.actie1", 19911231, 19911231));
        Assert.assertEquals(1, meldingen.size());
    }

    @Test
    public void testVerschillendeDatums1() {
        List<Melding> meldingen =
            brby0527.executeer(null, maakPersoonMetPlaats(maakGemeente((short) 24, "gemeente", 19920101, 200020101)),
                    maakActie("id.actie1", 19911231, null));
        Assert.assertEquals(1, meldingen.size());
    }

    @Test
    public void testVerschillendeDatums2() {
        List<Melding> meldingen =
            brby0527.executeer(null, maakPersoonMetPlaats(maakGemeente((short) 24, "gemeente", 19920101, 200020101)),
                    maakActie("id.actie1", 19910231, 19911231));
        Assert.assertEquals(1, meldingen.size());
    }

    @Test
    public void testVerschillendeDatums3() {
        List<Melding> meldingen =
            brby0527.executeer(null, maakPersoonMetPlaats(maakGemeente((short) 24, "gemeente", 19920101, 200020101)),
                    maakActie("id.actie1", 19910231, 19970231));
        Assert.assertEquals(1, meldingen.size());
    }

    @Test
    public void testVerschillendeDatums4() {
        List<Melding> meldingen =
            brby0527.executeer(null, maakPersoonMetPlaats(maakGemeente((short) 24, "gemeente", 19920101, 200020101)),
                    maakActie("id.actie1", 19910231, 20070231));
        Assert.assertEquals(1, meldingen.size());
    }

    @Test
    public void testVerschillendeDatums5() {
        List<Melding> meldingen =
            brby0527.executeer(null, maakPersoonMetPlaats(maakGemeente((short) 24, "gemeente", 19920101, 200020101)),
                    maakActie("id.actie1", 19920101, 200020101));
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testVerschillendeDatums6() {
        List<Melding> meldingen =
            brby0527.executeer(null, maakPersoonMetPlaats(maakGemeente((short) 24, "gemeente", 19920101, 200020101)),
                    maakActie("id.actie1", 200020102, 200020103));
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals("adres1", meldingen.get(0).getVerzendendId());
    }

    @Test
    public void testVerschillendeDatums7() {
        List<Melding> meldingen =
            brby0527.executeer(null, maakPersoonMetPlaats(maakGemeente((short) 24, "gemeente", 19920101, 200020101)),
                    maakActie("id.actie1", 19911230, 19911231));
        Assert.assertEquals(1, meldingen.size());
    }


    private Actie maakActie(final String verzendendId, final Integer begin, final Integer eind) {
        Datum beginDatum = null;
        Datum eindDatum = null;

        if (begin != null) {
            beginDatum = new Datum(begin);
        }

        if (eind != null) {
            eindDatum = new Datum(eind);
        }

        ActieBericht actie =
            ActieBerichtBuilder.bouwNieuweActie(SoortActie.CORRECTIE_ADRES_NL).setDatumAanvang(beginDatum)
                    .setDatumEinde(eindDatum).getActie();
        actie.setVerzendendId(verzendendId);
        return actie;
    }

    private PersoonBericht maakPersoonMetPlaats(final Partij gemeente) {
        PersoonBericht persoon =
            PersoonBuilder.bouwPersoon("1234567890", Geslachtsaanduiding.MAN, 19660606, null, "vn", "vg", "gsln");

        PersoonAdresBericht paBericht =
            PersoonAdresBuilder.bouwWoonadres("NOR", 123, "1234HH", StatischeObjecttypeBuilder.WOONPLAATS_AMSTERDAM,
                    gemeente, 20120713);
        paBericht.setVerzendendId("adres1");
        persoon.setAdressen(new ArrayList<PersoonAdresBericht>());
        persoon.getAdressen().add(paBericht);
        return persoon;
    }

    private Partij maakGemeente(final short code, final String naam, final Integer begin, final Integer eind) {
        Partij gemeente = new Partij();
        gemeente.setNaam(new NaamEnumeratiewaarde(naam));
        gemeente.setGemeentecode(new Gemeentecode(code));
        if (begin != null) {
            gemeente.setDatumAanvang(new Datum(begin));
        }
        if (eind != null) {
            gemeente.setDatumEinde(new Datum(eind));
        }

        return gemeente;
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.gba.autorisatie;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.service.cache.TestLeveringsAutorisatieCache;
import org.junit.Before;
import org.junit.Test;

public class GbaAutorisatiesTest {

    private final TestLeveringsAutorisatieCache cache = new TestLeveringsAutorisatieCache();
    private final GbaAutorisaties subject = new GbaAutorisaties(cache);

    @Before
    public void setup() {
        cache.clear();
        BrpNu.set();
    }

    @Test
    public void emptyResultVoorNullPartij() {
        assertEquals(false, subject.bepaalAutorisatie(null, Rol.AFNEMER, SoortDienst.ATTENDERING).isPresent());
    }

    @Test
    public void enkeleAutorisatie() {
        addAutorisaties("000123", SoortDienst.ATTENDERING, SoortDienst.ZOEK_PERSOON);
        assertEquals(true, subject.bepaalAutorisatie("000123", Rol.AFNEMER, SoortDienst.ZOEK_PERSOON).isPresent());
    }

    @Test
    public void enkeleAutorisatieZonderRol() {
        addAutorisaties("000123", SoortDienst.ATTENDERING, SoortDienst.ZOEK_PERSOON);
        assertEquals(true, subject.bepaalAutorisatie("000123", null, SoortDienst.ZOEK_PERSOON).isPresent());
    }

    @Test
    public void meerdereAutorisatie() {
        addAutorisaties("000123", SoortDienst.ATTENDERING, SoortDienst.ZOEK_PERSOON);
        addAutorisaties("000123", SoortDienst.ZOEK_PERSOON_OP_ADRESGEGEVENS);
        addAutorisaties("000123", SoortDienst.GEEF_DETAILS_PERSOON, SoortDienst.GEEF_KANDIDAAT_OUDER);
        addAutorisaties("000234", SoortDienst.GEEF_KANDIDAAT_OUDER);
        assertEquals(true, subject.bepaalAutorisatie("000123", Rol.AFNEMER, SoortDienst.GEEF_KANDIDAAT_OUDER).isPresent());
    }

    @Test
    public void verkeerdeDienst() {
        addAutorisaties("000123", SoortDienst.ATTENDERING, SoortDienst.ZOEK_PERSOON);
        assertEquals(false, subject.bepaalAutorisatie("000123", Rol.AFNEMER, SoortDienst.GEEF_KANDIDAAT_OUDER).isPresent());
    }

    @Test
    public void verkeerdStelsel() {
        Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.BRP, false);
        Dienstbundel dienstbundel = new Dienstbundel(leveringsautorisatie);
        dienstbundel.addDienstSet(new Dienst(dienstbundel, SoortDienst.ZOEK_PERSOON));
        leveringsautorisatie.addDienstbundelSet(dienstbundel);
        ToegangLeveringsAutorisatie t1 = new ToegangLeveringsAutorisatie(new PartijRol(new Partij("partij1", "000123"), Rol.AFNEMER), leveringsautorisatie);
        cache.addAutorisatie("000123", t1);
        assertEquals(false, subject.bepaalAutorisatie("000123", Rol.AFNEMER, SoortDienst.ZOEK_PERSOON).isPresent());
    }

    @Test
    public void moetGeldigeLeveringsautorisatieBepalen() {
        Leveringsautorisatie lev1 = new Leveringsautorisatie(Stelsel.GBA, false);
        lev1.setNaam("lev1");
        lev1.setDatumEinde(20010101);
        Dienstbundel dienstbundel1 = new Dienstbundel(lev1);
        Dienst dienst1 = new Dienst(dienstbundel1, SoortDienst.ZOEK_PERSOON);
        dienst1.setDatumIngang(20000101);
        dienstbundel1.addDienstSet(dienst1);
        dienstbundel1.setDatumIngang(20000101);
        lev1.addDienstbundelSet(dienstbundel1);
        ToegangLeveringsAutorisatie t1 = new ToegangLeveringsAutorisatie(new PartijRol(new Partij("partij1", "000123"), Rol.AFNEMER), lev1);

        Leveringsautorisatie lev2 = new Leveringsautorisatie(Stelsel.GBA, false);
        lev2.setNaam("lev2");
        lev2.setDatumIngang(20000101);
        Dienstbundel dienstbundel2 = new Dienstbundel(lev2);
        Dienst dienst2 = new Dienst(dienstbundel2, SoortDienst.ZOEK_PERSOON);
        dienst2.setDatumEinde(20021201);
        dienstbundel2.addDienstSet(dienst2);
        dienstbundel2.setDatumIngang(20000101);
        lev2.addDienstbundelSet(dienstbundel2);
        ToegangLeveringsAutorisatie t2 = new ToegangLeveringsAutorisatie(new PartijRol(new Partij("partij1", "000123"), Rol.AFNEMER), lev2);
        t2.setDatumIngang(20000101);

        Leveringsautorisatie lev3 = new Leveringsautorisatie(Stelsel.GBA, false);
        lev3.setNaam("lev3");
        lev3.setDatumIngang(20000102);
        Dienstbundel dienstbundel3 = new Dienstbundel(lev3);
        Dienst dienst3 = new Dienst(dienstbundel3, SoortDienst.ZOEK_PERSOON);
        dienst3.setDatumIngang(20000102);
        dienstbundel3.addDienstSet(dienst3);
        dienstbundel3.setDatumIngang(20000102);
        lev3.addDienstbundelSet(dienstbundel3);
        ToegangLeveringsAutorisatie t3 = new ToegangLeveringsAutorisatie(new PartijRol(new Partij("partij1", "000123"), Rol.AFNEMER), lev3);
        t3.setDatumIngang(20000101);

        cache.addAutorisatie("000123", t1);
        cache.addAutorisatie("000123", t2);
        cache.addAutorisatie("000123", t3);

        assertEquals("lev3", subject.bepaalAutorisatie("000123", Rol.AFNEMER, SoortDienst.ZOEK_PERSOON).get().getLeveringsautorisatie().getNaam());
    }

    private void addAutorisaties(final String partijCode, SoortDienst... diensten) {
        Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.GBA, false);
        leveringsautorisatie.setDatumIngang(20000101);
        Dienstbundel dienstbundel = new Dienstbundel(leveringsautorisatie);
        dienstbundel.setDatumIngang(20000101);
        Arrays.stream(diensten).forEach(dienst -> {
            Dienst d = new Dienst(dienstbundel, dienst);
            d.setDatumIngang(20000101);
            dienstbundel.addDienstSet(d);
        });
        leveringsautorisatie.addDienstbundelSet(dienstbundel);
        ToegangLeveringsAutorisatie t1 = new ToegangLeveringsAutorisatie(new PartijRol(new Partij("partij1", "000123"), Rol.AFNEMER), leveringsautorisatie);
        t1.setDatumIngang(20000101);
        cache.addAutorisatie(partijCode, t1);
    }
}

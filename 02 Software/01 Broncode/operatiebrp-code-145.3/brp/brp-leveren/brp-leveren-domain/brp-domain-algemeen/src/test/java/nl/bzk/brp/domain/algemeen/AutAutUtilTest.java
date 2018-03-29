/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.algemeen;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import org.junit.Assert;
import org.junit.Test;

/**
 * AutAutUtilTest.
 */
public class AutAutUtilTest {

    @Test
    public void testGeblokkeerdToegangLeveringsAutorisatieNull() {
        final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie = null;
        boolean geldigEnNietGeblokkeerd = AutAutUtil.isGeldigEnNietGeblokkeerdInclusiefLeveringsautorisatie(toegangLeveringsAutorisatie, 20101010);
        Assert.assertFalse(geldigEnNietGeblokkeerd);
    }

    @Test
    public void testGeblokkeerdToegangLeveringsAutorisatie() {
        final Partij partij = new Partij("test", "000123");
        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER);
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.BRP, false);
        final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);
        leveringsautorisatie.setDatumIngang(20091010);
        toegangLeveringsAutorisatie.setDatumIngang(20091010);
        boolean geldigEnNietGeblokkeerd = AutAutUtil.isGeldigEnNietGeblokkeerdInclusiefLeveringsautorisatie(toegangLeveringsAutorisatie, 20101010);
        Assert.assertTrue(geldigEnNietGeblokkeerd);
        //blokkeer tla
        toegangLeveringsAutorisatie.setIndicatieGeblokkeerd(true);
        geldigEnNietGeblokkeerd = AutAutUtil.isGeldigEnNietGeblokkeerdInclusiefLeveringsautorisatie(toegangLeveringsAutorisatie, 20101010);
        Assert.assertFalse(geldigEnNietGeblokkeerd);
        //reset tla en blokker la
        toegangLeveringsAutorisatie.setIndicatieGeblokkeerd(null);
        leveringsautorisatie.setIndicatieGeblokkeerd(true);
        geldigEnNietGeblokkeerd = AutAutUtil.isGeldigEnNietGeblokkeerdInclusiefLeveringsautorisatie(toegangLeveringsAutorisatie, 20101010);
        Assert.assertFalse(geldigEnNietGeblokkeerd);
        //zet ook tla geblokeerd
        toegangLeveringsAutorisatie.setIndicatieGeblokkeerd(true);
        geldigEnNietGeblokkeerd = AutAutUtil.isGeldigEnNietGeblokkeerdInclusiefLeveringsautorisatie(toegangLeveringsAutorisatie, 20101010);
        Assert.assertFalse(geldigEnNietGeblokkeerd);
        //zet weer geldig
        leveringsautorisatie.setIndicatieGeblokkeerd(null);
        toegangLeveringsAutorisatie.setIndicatieGeblokkeerd(null);
        geldigEnNietGeblokkeerd = AutAutUtil.isGeldigEnNietGeblokkeerdInclusiefLeveringsautorisatie(toegangLeveringsAutorisatie, 20101010);
        Assert.assertTrue(geldigEnNietGeblokkeerd);
    }

    @Test
    public void testGeldigToegangLeveringsAutorisatie() {
        final int peildatum = 20101010;
        final Partij partij = new Partij("test", "000123");
        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER);
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.BRP, false);
        final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);
        leveringsautorisatie.setDatumIngang(peildatum - 10);
        toegangLeveringsAutorisatie.setDatumIngang(peildatum - 10);
        boolean geldigEnNietGeblokkeerd = AutAutUtil.isGeldigEnNietGeblokkeerdInclusiefLeveringsautorisatie(toegangLeveringsAutorisatie, peildatum);
        Assert.assertTrue(geldigEnNietGeblokkeerd);
        //dat ingang na peildatum
        toegangLeveringsAutorisatie.setDatumIngang(peildatum + 10);
        geldigEnNietGeblokkeerd = AutAutUtil.isGeldigEnNietGeblokkeerdInclusiefLeveringsautorisatie(toegangLeveringsAutorisatie, peildatum);
        Assert.assertFalse(geldigEnNietGeblokkeerd);
        toegangLeveringsAutorisatie.setDatumIngang(peildatum - 10);
        leveringsautorisatie.setDatumIngang(peildatum + 10);
        geldigEnNietGeblokkeerd = AutAutUtil.isGeldigEnNietGeblokkeerdInclusiefLeveringsautorisatie(toegangLeveringsAutorisatie, peildatum);
        Assert.assertFalse(geldigEnNietGeblokkeerd);
        //dateinde voor peildatum
        toegangLeveringsAutorisatie.setDatumIngang(peildatum - 10);
        leveringsautorisatie.setDatumIngang(peildatum - 10);
        toegangLeveringsAutorisatie.setDatumEinde(peildatum - 1);
        geldigEnNietGeblokkeerd = AutAutUtil.isGeldigEnNietGeblokkeerdInclusiefLeveringsautorisatie(toegangLeveringsAutorisatie, peildatum);
        Assert.assertFalse(geldigEnNietGeblokkeerd);
        toegangLeveringsAutorisatie.setDatumEinde(null);
        leveringsautorisatie.setDatumEinde(peildatum - 1);
        geldigEnNietGeblokkeerd = AutAutUtil.isGeldigEnNietGeblokkeerdInclusiefLeveringsautorisatie(toegangLeveringsAutorisatie, peildatum);
        Assert.assertFalse(geldigEnNietGeblokkeerd);
    }

    @Test
    public void testDienstGeldigEnNietGeblokkeerd() {
        final int peildatum = 20101010;
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.BRP, false);
        final Set<Dienstbundel> dienstbundelSet = new HashSet<>();
        final Dienstbundel dienstbundel = new Dienstbundel(leveringsautorisatie);
        dienstbundelSet.add(dienstbundel);
        final Set<Dienst> dienstSet = new HashSet<>();
        final Dienst dienst = new Dienst(dienstbundel, SoortDienst.GEEF_DETAILS_PERSOON);
        int id = 889800;
        dienst.setId(id);
        dienst.setDatumIngang(peildatum - 10);
        dienstSet.add(dienst);
        dienstbundel.setDienstSet(dienstSet);
        leveringsautorisatie.setDienstbundelSet(dienstbundelSet);

        boolean geldigEnNietGeblokkeerd = AutAutUtil.isGeldigEnNietGeblokkeerd(dienst, peildatum);
        Assert.assertTrue(geldigEnNietGeblokkeerd);
        //datingang null
        dienst.setDatumIngang(null);
        geldigEnNietGeblokkeerd = AutAutUtil.isGeldigEnNietGeblokkeerd(dienst, peildatum);
        Assert.assertFalse(geldigEnNietGeblokkeerd);
        //datingang na peildatum
        dienst.setDatumIngang(peildatum + 10);
        geldigEnNietGeblokkeerd = AutAutUtil.isGeldigEnNietGeblokkeerd(dienst, peildatum);
        Assert.assertFalse(geldigEnNietGeblokkeerd);
        //dateinde na peildatum
        dienst.setDatumIngang(peildatum - 10);
        dienst.setDatumEinde(peildatum + 10);
        geldigEnNietGeblokkeerd = AutAutUtil.isGeldigEnNietGeblokkeerd(dienst, peildatum);
        Assert.assertTrue(geldigEnNietGeblokkeerd);
        //dateinde voor peildatum
        dienst.setDatumIngang(peildatum - 10);
        dienst.setDatumEinde(peildatum - 1);
        geldigEnNietGeblokkeerd = AutAutUtil.isGeldigEnNietGeblokkeerd(dienst, peildatum);
        Assert.assertFalse(geldigEnNietGeblokkeerd);
    }

    @Test
    public void testDienstGeldigEnNietGeblokkeerd_GeblokkeerdeDienst() {
        final int peildatum = 20101010;
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.BRP, false);
        final Set<Dienstbundel> dienstbundelSet = new HashSet<>();
        final Dienstbundel dienstbundel = new Dienstbundel(leveringsautorisatie);
        dienstbundelSet.add(dienstbundel);
        final Set<Dienst> dienstSet = new HashSet<>();
        final Dienst dienst = new Dienst(dienstbundel, SoortDienst.GEEF_DETAILS_PERSOON);
        int id = 889800;
        dienst.setId(id);
        dienst.setIndicatieGeblokkeerd(true);
        dienst.setDatumIngang(peildatum - 10);
        dienstSet.add(dienst);
        dienstbundel.setDienstSet(dienstSet);
        leveringsautorisatie.setDienstbundelSet(dienstbundelSet);

        boolean geldigEnNietGeblokkeerd = AutAutUtil.isGeldigEnNietGeblokkeerd(dienst, peildatum);
        Assert.assertFalse(geldigEnNietGeblokkeerd);
        //dienst geblokkeerd, datingang null
        dienst.setDatumIngang(null);
        geldigEnNietGeblokkeerd = AutAutUtil.isGeldigEnNietGeblokkeerd(dienst, peildatum);
        Assert.assertFalse(geldigEnNietGeblokkeerd);
        //dienst geblokkeerd, datingang na peildatum
        dienst.setDatumIngang(peildatum + 10);
        geldigEnNietGeblokkeerd = AutAutUtil.isGeldigEnNietGeblokkeerd(dienst, peildatum);
        Assert.assertFalse(geldigEnNietGeblokkeerd);
        //dienst geblokkeerd, dateinde na peildatum
        dienst.setDatumIngang(peildatum - 10);
        dienst.setDatumEinde(peildatum + 10);
        geldigEnNietGeblokkeerd = AutAutUtil.isGeldigEnNietGeblokkeerd(dienst, peildatum);
        Assert.assertFalse(geldigEnNietGeblokkeerd);
        //dienst geblokkeerd, dateinde voor peildatum
        dienst.setDatumIngang(peildatum - 10);
        dienst.setDatumEinde(peildatum - 1);
        geldigEnNietGeblokkeerd = AutAutUtil.isGeldigEnNietGeblokkeerd(dienst, peildatum);
        Assert.assertFalse(geldigEnNietGeblokkeerd);
    }

    @Test
    public void testDienstbundelGeldigEnNietGeblokkeerdDatIngang0() {
        final int peildatum = 20101010;
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.BRP, false);
        final Set<Dienstbundel> dienstbundelSet = new HashSet<>();
        final Dienstbundel dienstbundel = new Dienstbundel(leveringsautorisatie);
        dienstbundelSet.add(dienstbundel);
        final Set<Dienst> dienstSet = new HashSet<>();
        final Dienst dienst = new Dienst(dienstbundel, SoortDienst.GEEF_DETAILS_PERSOON);
        dienstSet.add(dienst);
        dienstbundel.setDienstSet(dienstSet);
        leveringsautorisatie.setDienstbundelSet(dienstbundelSet);
        dienstbundel.setDatumIngang(0);

        boolean geldigEnNietGeblokkeerd = AutAutUtil.isGeldigEnNietGeblokkeerd(dienstbundel, peildatum);
        Assert.assertTrue(geldigEnNietGeblokkeerd);
    }

    @Test
    public void testDienstbundelGeldigEnNietGeblokkeerdDatIngangNull() {
        final int peildatum = 20101010;
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.BRP, false);
        final Set<Dienstbundel> dienstbundelSet = new HashSet<>();
        final Dienstbundel dienstbundel = new Dienstbundel(leveringsautorisatie);
        dienstbundelSet.add(dienstbundel);
        final Set<Dienst> dienstSet = new HashSet<>();
        final Dienst dienst = new Dienst(dienstbundel, SoortDienst.GEEF_DETAILS_PERSOON);
        dienstSet.add(dienst);
        dienstbundel.setDienstSet(dienstSet);
        leveringsautorisatie.setDienstbundelSet(dienstbundelSet);
        dienstbundel.setDatumIngang(null);

        boolean geldigEnNietGeblokkeerd = AutAutUtil.isGeldigEnNietGeblokkeerd(dienstbundel, peildatum);
        Assert.assertFalse(geldigEnNietGeblokkeerd);
    }

    @Test
    public void testDienstbundelGeldigEnNietGeblokkeerd() {
        final int peildatum = 20101010;
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.BRP, false);
        final Set<Dienstbundel> dienstbundelSet = new HashSet<>();
        final Dienstbundel dienstbundel = new Dienstbundel(leveringsautorisatie);
        dienstbundelSet.add(dienstbundel);
        final Set<Dienst> dienstSet = new HashSet<>();
        final Dienst dienst = new Dienst(dienstbundel, SoortDienst.GEEF_DETAILS_PERSOON);
        dienstSet.add(dienst);
        dienstbundel.setDienstSet(dienstSet);
        leveringsautorisatie.setDienstbundelSet(dienstbundelSet);
        dienstbundel.setDatumIngang(peildatum - 10);

        boolean geldigEnNietGeblokkeerd = AutAutUtil.isGeldigEnNietGeblokkeerd(dienstbundel, peildatum);
        Assert.assertTrue(geldigEnNietGeblokkeerd);
        //datingang null
        dienstbundel.setDatumIngang(null);
        geldigEnNietGeblokkeerd = AutAutUtil.isGeldigEnNietGeblokkeerd(dienstbundel, peildatum);
        Assert.assertFalse(geldigEnNietGeblokkeerd);
        //datingang na peildatum
        dienstbundel.setDatumIngang(peildatum + 10);
        geldigEnNietGeblokkeerd = AutAutUtil.isGeldigEnNietGeblokkeerd(dienstbundel, peildatum);
        Assert.assertFalse(geldigEnNietGeblokkeerd);
        //dateinde na peildatum
        dienstbundel.setDatumIngang(peildatum - 10);
        dienstbundel.setDatumEinde(peildatum + 10);
        geldigEnNietGeblokkeerd = AutAutUtil.isGeldigEnNietGeblokkeerd(dienstbundel, peildatum);
        Assert.assertTrue(geldigEnNietGeblokkeerd);
        //dateinde voor peildatum
        dienstbundel.setDatumIngang(peildatum - 10);
        dienstbundel.setDatumEinde(peildatum - 1);
        geldigEnNietGeblokkeerd = AutAutUtil.isGeldigEnNietGeblokkeerd(dienstbundel, peildatum);
        Assert.assertFalse(geldigEnNietGeblokkeerd);
    }

    @Test
    public void testDienstbundelGeldigEnNietGeblokkeerd_DienstbundelGeblokkeerd() {
        final int peildatum = 20101010;
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.BRP, false);
        final Set<Dienstbundel> dienstbundelSet = new HashSet<>();
        final Dienstbundel dienstbundel = new Dienstbundel(leveringsautorisatie);
        dienstbundelSet.add(dienstbundel);
        final Set<Dienst> dienstSet = new HashSet<>();
        final Dienst dienst = new Dienst(dienstbundel, SoortDienst.GEEF_DETAILS_PERSOON);
        dienstSet.add(dienst);
        dienstbundel.setDienstSet(dienstSet);
        leveringsautorisatie.setDienstbundelSet(dienstbundelSet);
        dienstbundel.setDatumIngang(peildatum - 10);
        dienstbundel.setIndicatieGeblokkeerd(true);

        boolean geldigEnNietGeblokkeerd = AutAutUtil.isGeldigEnNietGeblokkeerd(dienstbundel, peildatum);
        Assert.assertFalse(geldigEnNietGeblokkeerd);
        //db geblokkeerd, datingang null
        dienstbundel.setDatumIngang(null);
        geldigEnNietGeblokkeerd = AutAutUtil.isGeldigEnNietGeblokkeerd(dienstbundel, peildatum);
        Assert.assertFalse(geldigEnNietGeblokkeerd);
        //db geblokkeerd, datingang na peildatum
        dienstbundel.setDatumIngang(peildatum + 10);
        geldigEnNietGeblokkeerd = AutAutUtil.isGeldigEnNietGeblokkeerd(dienstbundel, peildatum);
        Assert.assertFalse(geldigEnNietGeblokkeerd);
        //db geblokkeerd, dateinde na peildatum
        dienstbundel.setDatumIngang(peildatum - 10);
        dienstbundel.setDatumEinde(peildatum + 10);
        geldigEnNietGeblokkeerd = AutAutUtil.isGeldigEnNietGeblokkeerd(dienstbundel, peildatum);
        Assert.assertFalse(geldigEnNietGeblokkeerd);
        //db geblokkeerd, ddateinde voor peildatum
        dienstbundel.setDatumIngang(peildatum - 10);
        dienstbundel.setDatumEinde(peildatum - 1);
        geldigEnNietGeblokkeerd = AutAutUtil.isGeldigEnNietGeblokkeerd(dienstbundel, peildatum);
        Assert.assertFalse(geldigEnNietGeblokkeerd);
    }

    @Test
    public void testGeefDienstMetId() {
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.BRP, false);
        final Set<Dienstbundel> dienstbundelSet = new HashSet<>();
        final Dienstbundel dienstbundel = new Dienstbundel(leveringsautorisatie);
        dienstbundelSet.add(dienstbundel);
        final Set<Dienst> dienstSet = new HashSet<>();
        final Dienst dienst = new Dienst(dienstbundel, SoortDienst.GEEF_DETAILS_PERSOON);
        int id = 889800;
        dienst.setId(id);
        dienstSet.add(dienst);
        dienstbundel.setDienstSet(dienstSet);
        leveringsautorisatie.setDienstbundelSet(dienstbundelSet);

        final Dienst dienstCheck = AutAutUtil.zoekDienst(leveringsautorisatie, id);
        Assert.assertNotNull(dienstCheck);
        Assert.assertEquals(dienst, dienstCheck);

        final Dienst dienstCheckInCorrect = AutAutUtil.zoekDienst(leveringsautorisatie, id + 10);
        Assert.assertNull(dienstCheckInCorrect);

    }

    @Test
    public void testGeefDienstMetSoort() {
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.BRP, false);
        final Set<Dienstbundel> dienstbundelSet = new HashSet<>();
        final Dienstbundel dienstbundel = new Dienstbundel(leveringsautorisatie);
        dienstbundelSet.add(dienstbundel);
        final Set<Dienst> dienstSet = new HashSet<>();
        final Dienst dienst = new Dienst(dienstbundel, SoortDienst.GEEF_DETAILS_PERSOON);
        dienstSet.add(dienst);
        dienstbundel.setDienstSet(dienstSet);
        leveringsautorisatie.setDienstbundelSet(dienstbundelSet);

        final Dienst dienstCheck = AutAutUtil.zoekDienst(leveringsautorisatie, SoortDienst.GEEF_DETAILS_PERSOON);
        Assert.assertNotNull(dienstCheck);
        Assert.assertEquals(dienst, dienstCheck);

        final Dienst dienstCheckInCorrect = AutAutUtil.zoekDienst(leveringsautorisatie, SoortDienst.ZOEK_PERSOON);
        Assert.assertNull(dienstCheckInCorrect);

    }

    @Test
    public void testGeefDienstenMetSoort() {
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.BRP, false);
        final Set<Dienstbundel> dienstbundelSet = new HashSet<>();
        final Dienstbundel dienstbundel = new Dienstbundel(leveringsautorisatie);
        dienstbundelSet.add(dienstbundel);
        final Set<Dienst> dienstSet = new HashSet<>();
        final Dienst dienst = new Dienst(dienstbundel, SoortDienst.GEEF_DETAILS_PERSOON);
        dienstSet.add(dienst);
        dienstbundel.setDienstSet(dienstSet);
        leveringsautorisatie.setDienstbundelSet(dienstbundelSet);

        final Collection<Dienst> dienstCheck = AutAutUtil.zoekDiensten(leveringsautorisatie, SoortDienst.GEEF_DETAILS_PERSOON);
        Assert.assertNotNull(dienstCheck);
        Assert.assertEquals(dienst, dienstCheck.iterator().next());
    }

    @Test
    public void testConstructorIsPrivate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<AutAutUtil> constructor = AutAutUtil.class.getDeclaredConstructor();
        Assert.assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }
}

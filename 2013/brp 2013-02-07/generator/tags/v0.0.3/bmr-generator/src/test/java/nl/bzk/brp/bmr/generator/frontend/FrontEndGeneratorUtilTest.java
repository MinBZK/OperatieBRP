/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.frontend;

import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.bmr.generator.generators.frontend.FrontEndGeneratorUtil;
import nl.bzk.brp.ecore.bmr.FrameVeld;

import org.junit.Test;


public class FrontEndGeneratorUtilTest extends AbstractFrontEndTest {

    @Test
    public void testGetFormulierBron() {
        Assert.assertEquals("ObjectType-Partij", FrontEndGeneratorUtil.getFormulierBron(getFormulier()));
    }

    @Test
    public void testGetFrameBron() {
        Assert.assertEquals("ObjectType-Partij", FrontEndGeneratorUtil.getFrameBron(getFrame(0)));
    }

    @Test
    public void testGetFormulierNaam() {
        Assert.assertEquals("formulier1", FrontEndGeneratorUtil.getFormulierNaam(getFormulier()));
    }

    @Test
    public void testGetFrameNaam() {
        Assert.assertEquals("frame1", FrontEndGeneratorUtil.getFrameNaam(getFrame(0)));
    }

    @Test
    public void testGetAttribuutType() {
        Assert.assertEquals("attribuutType-Partij", FrontEndGeneratorUtil.getAttribuutType(getVeld(0)));
        Assert.assertEquals("enum-Soort", FrontEndGeneratorUtil.getAttribuutType(getVeld(1)));
        Assert.assertEquals("ObjectType-type-Certificaat1", FrontEndGeneratorUtil.getAttribuutType(getVeld(2)));
        Assert.assertEquals("ObjectType-type-Certificaat2", FrontEndGeneratorUtil.getAttribuutType(getVeld(3)));
    }

    @Test
    public void testGetAttribuutNaam() {
        Assert.assertEquals("attribuut-Partij", FrontEndGeneratorUtil.getAttribuutNaam(getVeld(0)));
    }

    @Test
    public void testGetAttribuutNaamVanObjectVeld() {
        Assert.assertEquals("type-Certificaat1otAttribuut2",
                FrontEndGeneratorUtil.getAttribuutNaamVanObjectVeld(getVeld(2), null));

        Assert.assertEquals("type-Certificaat1otAttribuut3",
                FrontEndGeneratorUtil.getAttribuutNaamVanObjectVeld(getVeld(2), 2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetAttribuutNaamVanObjectVeldOngeldig() {
        FrontEndGeneratorUtil.getAttribuutNaamVanObjectVeld(getVeld(0), null);
    }

    @Test
    public void testGetModelPad() {
        Assert.assertEquals("nl.bzk.brp.domein.schemanaam.ObjectType-type-Certificaat1",
                FrontEndGeneratorUtil.getModelPad(getVeld(2)));
    }

    @Test
    public void testGetModelPadVoorFrameBron() {
        Assert.assertEquals("nl.bzk.brp.domein.schemanaam.ObjectType-Bericht",
                FrontEndGeneratorUtil.getModelPadVoorFrameBron(getFrame(1)));
    }

    @Test
    public void testGetModelPadVoorFormulierBron() {
        Assert.assertEquals("nl.bzk.brp.domein.schemanaam.ObjectType-Partij",
                FrontEndGeneratorUtil.getModelPadVoorFormulierBron(getFormulier()));
    }

    @Test
    public void testIsEnumVeld() {
        Assert.assertFalse(FrontEndGeneratorUtil.isEnumVeld(getVeld(0)));
        Assert.assertTrue(FrontEndGeneratorUtil.isEnumVeld(getVeld(1)));
        Assert.assertFalse(FrontEndGeneratorUtil.isEnumVeld(getVeld(2)));
    }

    @Test
    public void testIsDynamischVeld() {
        Assert.assertFalse(FrontEndGeneratorUtil.isDynamischVeld(getVeld(0)));
        Assert.assertFalse(FrontEndGeneratorUtil.isDynamischVeld(getVeld(1)));
        Assert.assertTrue(FrontEndGeneratorUtil.isDynamischVeld(getVeld(2)));
    }

    @Test
    public void testGetStamgegevensVeldenVoorApplicatie() {
        List<FrameVeld> velden = FrontEndGeneratorUtil.getStamgegevensVeldenVoorApplicatie(getApplicatie());

        Assert.assertEquals("Er zouden geen gedupliceerde velden moeten zijn", 3, velden.size());
    }

    @Test
    public void testGetStamgegevensVeldenVoorFrame() {
        List<FrameVeld> velden = FrontEndGeneratorUtil.getStamgegevensVeldenVoorFrame(getFrame(0));

        Assert.assertEquals("Er zouden geen gedupliceerde velden moeten zijn", 3, velden.size());
    }

    @Test
    public void isHoofdFrame() {
        Assert.assertTrue(FrontEndGeneratorUtil.isHoofdFrame(getFrame(0)));
        Assert.assertFalse(FrontEndGeneratorUtil.isHoofdFrame(getFrame(1)));
    }
}

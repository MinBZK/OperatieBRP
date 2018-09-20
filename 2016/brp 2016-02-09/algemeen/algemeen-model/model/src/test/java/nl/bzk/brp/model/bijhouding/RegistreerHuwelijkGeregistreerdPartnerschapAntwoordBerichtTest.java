/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bijhouding;

import nl.bzk.brp.model.bericht.ber.BerichtStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.HandelingAangaanGeregistreerdPartnerschapInBuitenlandBericht;
import nl.bzk.brp.model.bericht.kern.HandelingAangaanGeregistreerdPartnerschapInNederlandBericht;
import nl.bzk.brp.model.bericht.kern.HandelingBeeindigingGeregistreerdPartnerschapInBuitenlandBericht;
import nl.bzk.brp.model.bericht.kern.HandelingBeeindigingGeregistreerdPartnerschapInNederlandBericht;
import nl.bzk.brp.model.bericht.kern.HandelingNietigverklaringGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.HandelingNietigverklaringHuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.HandelingOmzettingGeregistreerdPartnerschapInHuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.HandelingOntbindingHuwelijkInBuitenlandBericht;
import nl.bzk.brp.model.bericht.kern.HandelingOntbindingHuwelijkInNederlandBericht;
import nl.bzk.brp.model.bericht.kern.HandelingVoltrekkingHuwelijkInBuitenlandBericht;
import nl.bzk.brp.model.bericht.kern.HandelingVoltrekkingHuwelijkInNederlandBericht;

import org.junit.Assert;
import org.junit.Test;


/**
 * Unit test voor de {@link RegistreerHuwelijkGeregistreerdPartnerschapAntwoordBericht} klasse.
 */
public class RegistreerHuwelijkGeregistreerdPartnerschapAntwoordBerichtTest {

    @Test
    public void testIsSluitingHuwelijkNederland() {
        RegistreerHuwelijkGeregistreerdPartnerschapAntwoordBericht bericht =
                new RegistreerHuwelijkGeregistreerdPartnerschapAntwoordBericht();

        // Test zonder adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(null);
        Assert.assertFalse(bericht.isVoltrekkingHuwelijkInNederland());

        // Zet adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(new HandelingVoltrekkingHuwelijkInNederlandBericht());
        Assert.assertTrue(bericht.isVoltrekkingHuwelijkInNederland());

        // Zet adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(new HandelingVoltrekkingHuwelijkInBuitenlandBericht());
        Assert.assertFalse(bericht.isVoltrekkingHuwelijkInNederland());
    }

    @Test
    public void testIsOntbindingHuwelijkNederland() {
        RegistreerHuwelijkGeregistreerdPartnerschapAntwoordBericht bericht =
                new RegistreerHuwelijkGeregistreerdPartnerschapAntwoordBericht();

        // Test zonder adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(null);
        Assert.assertFalse(bericht.isOntbindingHuwelijkInNederland());

        // Zet adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(new HandelingOntbindingHuwelijkInNederlandBericht());
        Assert.assertTrue(bericht.isOntbindingHuwelijkInNederland());

        // Zet adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(new HandelingOntbindingHuwelijkInBuitenlandBericht());
        Assert.assertFalse(bericht.isOntbindingHuwelijkInNederland());
    }

    @Test
    public void testIsSluitingHuwelijkBuitenland() {
        RegistreerHuwelijkGeregistreerdPartnerschapAntwoordBericht bericht =
                new RegistreerHuwelijkGeregistreerdPartnerschapAntwoordBericht();

        // Test zonder adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(null);
        Assert.assertFalse(bericht.isVoltrekkingHuwelijkInBuitenland());

        // Zet adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(new HandelingVoltrekkingHuwelijkInBuitenlandBericht());
        Assert.assertTrue(bericht.isVoltrekkingHuwelijkInBuitenland());

        // Zet adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(new HandelingVoltrekkingHuwelijkInNederlandBericht());
        Assert.assertFalse(bericht.isVoltrekkingHuwelijkInBuitenland());
    }

    @Test
    public void testIsOntbindingHuwelijkBuitenland() {
        RegistreerHuwelijkGeregistreerdPartnerschapAntwoordBericht bericht =
                new RegistreerHuwelijkGeregistreerdPartnerschapAntwoordBericht();

        // Test zonder adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(null);
        Assert.assertFalse(bericht.isOntbindingHuwelijkInBuitenland());

        // Zet adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(new HandelingOntbindingHuwelijkInBuitenlandBericht());
        Assert.assertTrue(bericht.isOntbindingHuwelijkInBuitenland());

        // Zet adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(new HandelingOntbindingHuwelijkInNederlandBericht());
        Assert.assertFalse(bericht.isOntbindingHuwelijkInBuitenland());
    }

    @Test
    public void testIsSluitingGeregistreerdPartnerschapNederland() {
        RegistreerHuwelijkGeregistreerdPartnerschapAntwoordBericht bericht =
                new RegistreerHuwelijkGeregistreerdPartnerschapAntwoordBericht();

        // Test zonder adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(null);
        Assert.assertFalse(bericht.isAangaanGeregistreerdPartnerschapInNederland());

        // Zet adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(
                new HandelingAangaanGeregistreerdPartnerschapInNederlandBericht());
        Assert.assertTrue(bericht.isAangaanGeregistreerdPartnerschapInNederland());

        // Zet adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(
                new HandelingBeeindigingGeregistreerdPartnerschapInNederlandBericht());
        Assert.assertFalse(bericht.isAangaanGeregistreerdPartnerschapInNederland());
    }

    @Test
    public void testIsOntbindingGeregistreerdPartnerschapNederland() {
        RegistreerHuwelijkGeregistreerdPartnerschapAntwoordBericht bericht =
                new RegistreerHuwelijkGeregistreerdPartnerschapAntwoordBericht();

        // Test zonder adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(null);
        Assert.assertFalse(bericht.isBeeindigingGeregistreerdPartnerschapInNederland());

        // Zet adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(
                new HandelingBeeindigingGeregistreerdPartnerschapInNederlandBericht());
        Assert.assertTrue(bericht.isBeeindigingGeregistreerdPartnerschapInNederland());

        // Zet adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(
                new HandelingAangaanGeregistreerdPartnerschapInNederlandBericht());
        Assert.assertFalse(bericht.isBeeindigingGeregistreerdPartnerschapInNederland());
    }

    @Test
    public void testIsSluitingGeregistreerdPartnerschapBuitenland() {
        RegistreerHuwelijkGeregistreerdPartnerschapAntwoordBericht bericht =
                new RegistreerHuwelijkGeregistreerdPartnerschapAntwoordBericht();

        // Test zonder adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(null);
        Assert.assertFalse(bericht.isAangaanGeregistreerdPartnerschapInBuitenland());

        // Zet adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(
                new HandelingAangaanGeregistreerdPartnerschapInBuitenlandBericht());
        Assert.assertTrue(bericht.isAangaanGeregistreerdPartnerschapInBuitenland());

        // Zet adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(
                new HandelingBeeindigingGeregistreerdPartnerschapInBuitenlandBericht());
        Assert.assertFalse(bericht.isAangaanGeregistreerdPartnerschapInBuitenland());
    }

    @Test
    public void testIsOntbindingGeregistreerdPartnerschapBuitenland() {
        RegistreerHuwelijkGeregistreerdPartnerschapAntwoordBericht bericht =
                new RegistreerHuwelijkGeregistreerdPartnerschapAntwoordBericht();

        // Test zonder adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(null);
        Assert.assertFalse(bericht.isBeeindigingGeregistreerdPartnerschapInBuitenland());

        // Zet adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(
                new HandelingBeeindigingGeregistreerdPartnerschapInBuitenlandBericht());
        Assert.assertTrue(bericht.isBeeindigingGeregistreerdPartnerschapInBuitenland());

        // Zet adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(
                new HandelingAangaanGeregistreerdPartnerschapInBuitenlandBericht());
        Assert.assertFalse(bericht.isBeeindigingGeregistreerdPartnerschapInBuitenland());
    }

    @Test
    public void testIsOmzettingPartnerschapInHuwelijk() {
        RegistreerHuwelijkGeregistreerdPartnerschapAntwoordBericht bericht =
                new RegistreerHuwelijkGeregistreerdPartnerschapAntwoordBericht();

        // Test zonder adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(null);
        Assert.assertFalse(bericht.isOmzettingGeregistreerdPartnerschapInHuwelijk());

        // Zet adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(
                new HandelingOmzettingGeregistreerdPartnerschapInHuwelijkBericht());
        Assert.assertTrue(bericht.isOmzettingGeregistreerdPartnerschapInHuwelijk());

        // Zet adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(
                new HandelingAangaanGeregistreerdPartnerschapInBuitenlandBericht());
        Assert.assertFalse(bericht.isOmzettingGeregistreerdPartnerschapInHuwelijk());
    }

    @Test
    public void testIsNietigverklaringHuwelijk() {
        RegistreerHuwelijkGeregistreerdPartnerschapAntwoordBericht bericht =
                new RegistreerHuwelijkGeregistreerdPartnerschapAntwoordBericht();

        // Test zonder adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(null);
        Assert.assertFalse(bericht.isNietigverklaringHuwelijk());

        // Zet adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(new HandelingNietigverklaringHuwelijkBericht());
        Assert.assertTrue(bericht.isNietigverklaringHuwelijk());

        // Zet adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(
                new HandelingNietigverklaringGeregistreerdPartnerschapBericht());
        Assert.assertFalse(bericht.isNietigverklaringHuwelijk());
    }

    @Test
    public void testIsNietigverklaringPartnerschap() {
        RegistreerHuwelijkGeregistreerdPartnerschapAntwoordBericht bericht =
                new RegistreerHuwelijkGeregistreerdPartnerschapAntwoordBericht();

        // Test zonder adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(null);
        Assert.assertFalse(bericht.isNietigverklaringGeregistreerdPartnerschap());

        // Zet adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(
                new HandelingNietigverklaringGeregistreerdPartnerschapBericht());
        Assert.assertTrue(bericht.isNietigverklaringGeregistreerdPartnerschap());

        // Zet adm. handeling
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(new HandelingNietigverklaringHuwelijkBericht());
        Assert.assertFalse(bericht.isNietigverklaringGeregistreerdPartnerschap());
    }
}

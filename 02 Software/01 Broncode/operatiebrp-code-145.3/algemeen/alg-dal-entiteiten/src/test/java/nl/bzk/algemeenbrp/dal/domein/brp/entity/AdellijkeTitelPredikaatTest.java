/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.AdellijkeTitel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Geslachtsaanduiding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Predicaat;
import org.junit.Test;

/**
 * Unittest voor {@link AdellijkeTitelPredikaat} voor de getters/setters die niet door {@link nl.bzk.algemeenbrp.dal.domein.EntityGetterSetterTest} wordt
 * ondervangen. Dit zijn de getters/setters waarbij er een enumeratie waarde wordt doorgegeven, maar de ID wordt opgeslagen.
 */
public class AdellijkeTitelPredikaatTest {

    private static final AdellijkeTitelPredikaat ENTITY = new AdellijkeTitelPredikaat();

    @Test
    public void testGetterSetterGeslachtsaanduiding() {
        ENTITY.setGeslachtsaanduiding(Geslachtsaanduiding.MAN);
        assertEquals(Geslachtsaanduiding.MAN, ENTITY.getGeslachtsaanduiding());
    }

    @Test(expected = NullPointerException.class)
    public void testSetterGeslachtsaanduidingNull() {
        ENTITY.setGeslachtsaanduiding(null);
    }

    @Test
    public void testGetterSetterAdellijkeTitel() {
        assertNull(ENTITY.getAdellijkeTitel());
        ENTITY.setAdellijkeTitel(AdellijkeTitel.B);
        assertEquals(AdellijkeTitel.B, ENTITY.getAdellijkeTitel());

        ENTITY.setAdellijkeTitel(null);
        assertNull(ENTITY.getAdellijkeTitel());
    }

    @Test
    public void testGetterSetterPredikaat() {
        assertNull(ENTITY.getPredikaat());
        ENTITY.setPredikaat(Predicaat.H);
        assertEquals(Predicaat.H, ENTITY.getPredikaat());

        ENTITY.setPredikaat(null);
        assertNull(ENTITY.getPredikaat());
    }
}

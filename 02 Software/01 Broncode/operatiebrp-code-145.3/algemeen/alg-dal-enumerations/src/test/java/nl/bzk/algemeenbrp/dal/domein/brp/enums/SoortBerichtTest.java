/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * Unittest voor {@link Richting} voor de methodes die niet door de {@link nl.bzk.algemeenbrp.dal.domein.EnumeratieTest}
 * getest worden.
 */
public class SoortBerichtTest {

    private static final SoortBericht SOORT_BERICHT = SoortBericht.BHG_HGP_REGISTREER_HUWELIJK_GEREGISTREERD_PARTNERSCHAP;

    @Test
    public void getModule() throws Exception {
        assertEquals(BurgerzakenModule.HUWELIJKGEREGISTREERD_PARTNERSCHAP, SOORT_BERICHT.getModule());
        assertNull(SoortBericht.AF11.getModule());
    }

    @Test
    public void getKoppelvlak() throws Exception {
        assertEquals(Koppelvlak.BIJHOUDING, SOORT_BERICHT.getKoppelvlak());
    }

    @Test
    public void getDatumAanvanGeldigheid() throws Exception {
        assertNull(SOORT_BERICHT.getDatumAanvanGeldigheid());
    }

    @Test
    public void getDatumEindeGeldigheid() throws Exception {
        assertNull(SOORT_BERICHT.getDatumEindeGeldigheid());
    }

    @Test
    public void parseIdentifier() {
        assertEquals(
            SoortBericht.BHG_HGP_REGISTREER_HUWELIJK_GEREGISTREERD_PARTNERSCHAP,
            SoortBericht.parseIdentifier("bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap"));
    }

    /**
     * Test om te zorgen dat de wijzigingen in de enum-naam voor status- en deliveryreport goed zijn gegaan.
     */
    @Test
    public void testNaamgeving() {
        assertEquals("StatusReport", SoortBericht.STATUS_REPORT.getIdentifier());
        assertEquals("DeliveryReport", SoortBericht.DELIVERY_REPORT.getIdentifier());
    }
}

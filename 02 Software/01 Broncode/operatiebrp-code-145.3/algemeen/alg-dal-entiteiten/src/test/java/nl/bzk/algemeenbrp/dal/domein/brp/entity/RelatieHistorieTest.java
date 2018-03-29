/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import static org.junit.Assert.*;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import org.junit.Test;

/**
 * Testen voor {@link RelatieHistorie}.
 */
public class RelatieHistorieTest {

    @Test
    public void testMaakKopieVanAanvangLeeg() {
        //setup
        final RelatieHistorie bron = new RelatieHistorie(new Relatie(SoortRelatie.HUWELIJK));
        //exectute
        final RelatieHistorie kopie = bron.maakKopieVanAanvang();
        //verify
        assertNotNull(kopie);
        assertEquals(bron.getRelatie(), kopie.getRelatie());
    }

    @Test
    public void testMaakKopieVanAanvangGevuld() {
        //setup
        final RelatieHistorie bron = new RelatieHistorie(new Relatie(SoortRelatie.HUWELIJK));
        bron.setBuitenlandsePlaatsAanvang("plaats aanvang");
        bron.setBuitenlandsePlaatsEinde("plaats einde");
        bron.setBuitenlandseRegioAanvang("region aanvang");
        bron.setBuitenlandseRegioEinde("region einde");
        bron.setDatumAanvang(20160101);
        bron.setDatumEinde(20160202);
        bron.setOmschrijvingLocatieAanvang("locatie aanvang");
        bron.setOmschrijvingLocatieEinde("locatie einde");
        bron.setLandOfGebiedAanvang(new LandOfGebied("0000", "landofgebied aanvang"));
        bron.setLandOfGebiedEinde(new LandOfGebied("0001", "landofgebied einde"));
        bron.setGemeenteAanvang(new Gemeente((short) 0, "gemeente aanvang", "0000", new Partij("partij aanvang", "000000")));
        bron.setGemeenteEinde(new Gemeente((short) 1, "gemeente einde", "0001", new Partij("partij einde", "000001")));
        bron.setWoonplaatsnaamAanvang("woonplaats aanvang");
        bron.setWoonplaatsnaamEinde("woonplaats einde");
        bron.setRedenBeeindigingRelatie(new RedenBeeindigingRelatie('T', "Test"));
        //exectute
        final RelatieHistorie kopie = bron.maakKopieVanAanvang();
        //verify
        assertNotNull(kopie);
        assertEquals(bron.getRelatie(), kopie.getRelatie());
        assertEquals(bron.getBuitenlandsePlaatsAanvang(), kopie.getBuitenlandsePlaatsAanvang());
        assertNull(kopie.getBuitenlandsePlaatsEinde());
        assertEquals(bron.getBuitenlandseRegioAanvang(), kopie.getBuitenlandseRegioAanvang());
        assertNull(kopie.getBuitenlandseRegioEinde());
        assertEquals(bron.getDatumAanvang(), kopie.getDatumAanvang());
        assertNull(kopie.getDatumEinde());
        assertEquals(bron.getOmschrijvingLocatieAanvang(), kopie.getOmschrijvingLocatieAanvang());
        assertNull(kopie.getOmschrijvingLocatieEinde());
        assertEquals(bron.getLandOfGebiedAanvang(), kopie.getLandOfGebiedAanvang());
        assertNull(kopie.getLandOfGebiedEinde());
        assertEquals(bron.getGemeenteAanvang(), kopie.getGemeenteAanvang());
        assertNull(kopie.getGemeenteEinde());
        assertEquals(bron.getWoonplaatsnaamAanvang(), kopie.getWoonplaatsnaamAanvang());
        assertNull(kopie.getWoonplaatsnaamEinde());
        assertNull(kopie.getRedenBeeindigingRelatie());
    }
}

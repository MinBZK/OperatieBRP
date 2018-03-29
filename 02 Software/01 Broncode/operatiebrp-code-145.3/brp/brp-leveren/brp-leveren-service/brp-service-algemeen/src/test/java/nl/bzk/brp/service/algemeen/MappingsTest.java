/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import org.junit.Test;
import org.springframework.util.Assert;

public class MappingsTest {

    @Test
    public void testSoortDienstMapping() {
        assertEquals(SoortDienst.GEEF_DETAILS_PERSOON, geefSoortDienst("lvg_bvgGeefDetailsPersoon"));

        assertEquals(SoortDienst.ZOEK_PERSOON, geefSoortDienst("lvg_bvgZoekPersoon"));

        assertEquals(SoortDienst.ZOEK_PERSOON_OP_ADRESGEGEVENS, geefSoortDienst("lvg_bvgZoekPersoonOpAdres"));

        assertEquals(SoortDienst.GEEF_MEDEBEWONERS_VAN_PERSOON, geefSoortDienst("lvg_bvgGeefMedebewoners"));

        assertEquals(SoortDienst.SYNCHRONISATIE_PERSOON, geefSoortDienst("lvg_synGeefSynchronisatiePersoon"));

        assertEquals(SoortDienst.SYNCHRONISATIE_STAMGEGEVEN, geefSoortDienst("lvg_synGeefSynchronisatieStamgegeven"));

        final Set<SoortDienst> regAfnemerIndicatieDiensten = Sets.newHashSet(SoortDienst.PLAATSING_AFNEMERINDICATIE, SoortDienst.VERWIJDERING_AFNEMERINDICATIE);
        assertTrue(Sets.symmetricDifference(regAfnemerIndicatieDiensten,Mappings.soortDienst("lvg_synRegistreerAfnemerindicatie")).isEmpty());
    }


    @Test
    public void testSoortBerichtBevraging() {
        Mappings.SoortBerichten soortBerichten = Mappings.getBevragingSoortBerichten(SoortDienst.GEEF_DETAILS_PERSOON);
        assertEquals(SoortBericht.LVG_BVG_GEEF_DETAILS_PERSOON, soortBerichten.getInkomendBericht());
        assertEquals(SoortBericht.LVG_BVG_GEEF_DETAILS_PERSOON_R, soortBerichten.getUitgaandBericht());

        soortBerichten = Mappings.getBevragingSoortBerichten(SoortDienst.GEEF_MEDEBEWONERS_VAN_PERSOON);
        assertEquals(SoortBericht.LVG_BVG_GEEF_MEDEBEWONERS, soortBerichten.getInkomendBericht());
        assertEquals(SoortBericht.LVG_BVG_GEEF_MEDEBEWONERS_R, soortBerichten.getUitgaandBericht());

        soortBerichten = Mappings.getBevragingSoortBerichten(SoortDienst.ZOEK_PERSOON);
        assertEquals(SoortBericht.LVG_BVG_ZOEK_PERSOON, soortBerichten.getInkomendBericht());
        assertEquals(SoortBericht.LVG_BVG_ZOEK_PERSOON_R, soortBerichten.getUitgaandBericht());

        soortBerichten = Mappings.getBevragingSoortBerichten(SoortDienst.ZOEK_PERSOON_OP_ADRESGEGEVENS);
        assertEquals(SoortBericht.LVG_BVG_ZOEK_PERSOON_OP_ADRES, soortBerichten.getInkomendBericht());
        assertEquals(SoortBericht.LVG_BVG_ZOEK_PERSOON_OP_ADRES_R, soortBerichten.getUitgaandBericht());

        Assert.isNull(Mappings.getBevragingSoortBerichten(SoortDienst.PLAATSING_AFNEMERINDICATIE));
        Assert.isNull(Mappings.getBevragingSoortBerichten(SoortDienst.VERWIJDERING_AFNEMERINDICATIE));
    }

    @Test
    public void testIsSoortDienstBevragingDienst() {
        assertTrue(Mappings.isBevragingSoortDienst(SoortDienst.GEEF_DETAILS_PERSOON));
        assertTrue(Mappings.isBevragingSoortDienst(SoortDienst.ZOEK_PERSOON));
        assertTrue(Mappings.isBevragingSoortDienst(SoortDienst.ZOEK_PERSOON_OP_ADRESGEGEVENS));
        assertTrue(Mappings.isBevragingSoortDienst(SoortDienst.GEEF_MEDEBEWONERS_VAN_PERSOON));

        assertFalse(Mappings.isBevragingSoortDienst(SoortDienst.SYNCHRONISATIE_PERSOON));
        assertFalse(Mappings.isBevragingSoortDienst(SoortDienst.SYNCHRONISATIE_STAMGEGEVEN));
        assertFalse(Mappings.isBevragingSoortDienst(SoortDienst.PLAATSING_AFNEMERINDICATIE));
        assertFalse(Mappings.isBevragingSoortDienst(SoortDienst.VERWIJDERING_AFNEMERINDICATIE));
    }

    private SoortDienst geefSoortDienst(final String requestElem) {
        return Iterables.getOnlyElement(Mappings.soortDienst(requestElem));
    }
}
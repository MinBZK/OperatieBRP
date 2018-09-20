/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.verbintenis;

import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LandGebiedCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebied;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieConversieGBABericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.RelatieStandaardGroepBericht;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class BRBY0446Test {

    private static final LandGebied NEDERLAND = new LandGebied(LandGebiedCodeAttribuut.NEDERLAND, null, null, null, null);
    private static final LandGebied BUITENLAND = new LandGebied(new LandGebiedCodeAttribuut((short) 1234), null, null, null, null);

    private BRBY0446 brby0446;
    private ActieBericht actie;
    private SoortAdministratieveHandeling soortAH;

    @Before
    public void init() {
        brby0446 = new BRBY0446();
        actie = new ActieConversieGBABericht();
        actie.setAdministratieveHandeling(new AdministratieveHandelingBericht(null) {
            @Override
            public SoortAdministratieveHandelingAttribuut getSoort() {
                return new SoortAdministratieveHandelingAttribuut(soortAH);
            }
        });
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0446, brby0446.getRegel());
    }

    @Test
    public void testGeenStandaardGroep() {
        soortAH = SoortAdministratieveHandeling.ONTBINDING_HUWELIJK_IN_BUITENLAND;
        final List<BerichtEntiteit> resultaat = brby0446.voerRegelUit(null, bouwHuwelijk(null), actie, null);
        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testGeenLandAanvang() {
        soortAH = SoortAdministratieveHandeling.ONTBINDING_HUWELIJK_IN_BUITENLAND;
        final HuwelijkBericht huwelijk = bouwHuwelijk(NEDERLAND);
        ReflectionTestUtils.setField(huwelijk.getStandaard(), "landGebiedEinde", null);
        final List<BerichtEntiteit> resultaat = brby0446.voerRegelUit(null, huwelijk, actie, null);
        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testHuwelijkNederlandLandNederland() {
        soortAH = SoortAdministratieveHandeling.ONTBINDING_HUWELIJK_IN_NEDERLAND;
        final List<BerichtEntiteit> resultaat = brby0446.voerRegelUit(null, bouwHuwelijk(NEDERLAND), actie, null);
        Assert.assertEquals(0, resultaat.size());
    }

    @Test
    public void testHuwelijkBuitenlandLandNederland() {
        soortAH = SoortAdministratieveHandeling.ONTBINDING_HUWELIJK_IN_BUITENLAND;
        final List<BerichtEntiteit> resultaat = brby0446.voerRegelUit(null, bouwHuwelijk(NEDERLAND), actie, null);
        Assert.assertEquals(1, resultaat.size());
    }

    @Test
    public void testPartnerschapBuitenlandLandNederland() {
        soortAH = SoortAdministratieveHandeling.BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND;
        final List<BerichtEntiteit> resultaat = brby0446.voerRegelUit(null, bouwHuwelijk(NEDERLAND), actie, null);
        Assert.assertEquals(1, resultaat.size());
    }

    @Test
    public void testHuwelijkBuitenlandLandBuitenland() {
        soortAH = SoortAdministratieveHandeling.ONTBINDING_HUWELIJK_IN_BUITENLAND;
        final List<BerichtEntiteit> resultaat = brby0446.voerRegelUit(null, bouwHuwelijk(BUITENLAND), actie, null);
        Assert.assertEquals(0, resultaat.size());
    }

    /**
     * Bouwt een huwelijk.
     *
     * @param land land einde
     * @return huwelijk bericht
     */
    private HuwelijkBericht bouwHuwelijk(final LandGebied land) {
        final HuwelijkBericht huwelijk = new HuwelijkBericht();
        if (land != null) {
            final RelatieStandaardGroepBericht hgpGroep = new RelatieStandaardGroepBericht();
            hgpGroep.setLandGebiedEinde(new LandGebiedAttribuut(land));
            huwelijk.setStandaard(hgpGroep);
        }
        return huwelijk;
    }

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.overlijden;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;
import nl.bzk.brp.constanten.BrpConstanten;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Land;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenOpschorting;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonOverlijdenGroepBericht;
import nl.bzk.brp.model.operationeel.kern.BetrokkenheidModel;
import nl.bzk.brp.model.operationeel.kern.HuwelijkGeregistreerdPartnerschapModel;
import nl.bzk.brp.model.operationeel.kern.HuwelijkGeregistreerdPartnerschapStandaardGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.operationeel.kern.RelatieModel;
import nl.bzk.brp.util.DatumUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;


public class VR00012Test {

    @Mock
    private ReferentieDataRepository referentieDataRepository;

    @Mock
    private PersoonModel persoonModel;

    private final PersoonBericht persoonBericht = new PersoonBericht();

    PersoonOverlijdenGroepBericht overlijdenGroep = new PersoonOverlijdenGroepBericht();

    @InjectMocks
    private final VR00012 vr00012 = new VR00012();


    @Before
    public void init() {
        initMocks(this);

        Land land = new Land(BrpConstanten.NL_LAND_CODE, new NaamEnumeratiewaarde("Nederland"), null, null, null);
        persoonBericht.setOverlijden(overlijdenGroep);

        when(referentieDataRepository.vindLandOpCode(BrpConstanten.NL_LAND_CODE)).thenReturn(land);

    }

    @Test
    public void testPersoonOverleden() {
        when(persoonModel.heeftBetrokkenheden()).thenReturn(false);
        vr00012.executeer(persoonModel, persoonBericht, null);

        Assert.assertEquals("Nederland", persoonBericht.getOverlijden().getLandOverlijden().getNaam().getWaarde());
        Assert.assertEquals(RedenOpschorting.OVERLIJDEN, persoonBericht.getOpschorting().getRedenOpschortingBijhouding());
    }

    @Test
    public void testPersoonOverledenInBuitenland() {
        Land land = new Land(null, new NaamEnumeratiewaarde("Andere land"), null, null, null);

        overlijdenGroep.setLandOverlijden(land);

        persoonBericht.setOverlijden(overlijdenGroep);
        when(persoonModel.heeftBetrokkenheden()).thenReturn(false);

        vr00012.executeer(persoonModel, persoonBericht, null);

        Assert.assertEquals("Andere land", persoonBericht.getOverlijden().getLandOverlijden().getNaam().getWaarde());
        Assert.assertEquals(RedenOpschorting.OVERLIJDEN, persoonBericht.getOpschorting().getRedenOpschortingBijhouding());
    }

    @Test
    public void zouGeenRelatieMoetenVerbrekenOmdatBetrokkenhedenGeenHuwelijkOfRelatiesZijn() {
        BetrokkenheidModel betrokkenheid = mock(BetrokkenheidModel.class);
        RelatieModel relatieModel = mock(RelatieModel.class);
        Set<BetrokkenheidModel> betrokkenheden = new HashSet<BetrokkenheidModel>();
        betrokkenheden.add(betrokkenheid);

        when(persoonModel.heeftBetrokkenheden()).thenReturn(true);
        when(persoonModel.getBetrokkenheden()).thenReturn(betrokkenheden);
        when(betrokkenheid.getRelatie()).thenReturn(relatieModel);
        when(relatieModel.getSoort()).thenReturn(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);

        vr00012.executeer(persoonModel, persoonBericht, null);

        assertNull(persoonBericht.getBetrokkenheden());
    }

    @Test
    public void zouGeenRelatieMoetenVerbrekenOmdatRelatieAlVerbrokenIs() {
        BetrokkenheidModel betrokkenheid = mock(BetrokkenheidModel.class);
        HuwelijkGeregistreerdPartnerschapModel relatieModel = mock(HuwelijkGeregistreerdPartnerschapModel.class);
        HuwelijkGeregistreerdPartnerschapStandaardGroepModel relatieStandaardGroep = mock(HuwelijkGeregistreerdPartnerschapStandaardGroepModel.class);

        Set<BetrokkenheidModel> betrokkenheden = new HashSet<BetrokkenheidModel>();
        betrokkenheden.add(betrokkenheid);

        when(persoonModel.heeftBetrokkenheden()).thenReturn(true);
        when(persoonModel.getBetrokkenheden()).thenReturn(betrokkenheden);
        when(betrokkenheid.getRelatie()).thenReturn(relatieModel);
        when(relatieModel.getSoort()).thenReturn(SoortRelatie.HUWELIJK);
        when(relatieModel.getStandaard()).thenReturn(relatieStandaardGroep);
        when(relatieStandaardGroep.getDatumEinde()).thenReturn(DatumUtil.gisteren());

        vr00012.executeer(persoonModel, persoonBericht, null);

        assertNull(persoonBericht.getBetrokkenheden());
    }
}

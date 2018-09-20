/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.overlijden;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;
import nl.bzk.brp.constanten.BrpConstanten;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.model.attribuuttype.Naam;
import nl.bzk.brp.model.groep.bericht.PersoonOverlijdenGroepBericht;
import nl.bzk.brp.model.groep.logisch.RelatieStandaardGroep;
import nl.bzk.brp.model.groep.operationeel.actueel.RelatieStandaardGroepModel;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.logisch.Betrokkenheid;
import nl.bzk.brp.model.objecttype.operationeel.BetrokkenheidModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.RelatieModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Land;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenOpschorting;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortRelatie;
import nl.bzk.brp.util.DatumUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;


public class VR00012Test {

    @Mock
    private ReferentieDataRepository referentieDataRepository;

    @Mock
    private PersoonModel persoonModel;

    private PersoonBericht persoonBericht = new PersoonBericht();

    PersoonOverlijdenGroepBericht overlijdenGroep = new PersoonOverlijdenGroepBericht();
    
    @InjectMocks
    private VR00012 vr00012 = new VR00012();


    @Before
    public void init() {
        initMocks(this);

        Land land = new Land();
        land.setCode(BrpConstanten.NL_LAND_CODE);
        land.setNaam(new Naam("Nederland"));

        persoonBericht.setOverlijden(overlijdenGroep);

        when(referentieDataRepository.vindLandOpCode(BrpConstanten.NL_LAND_CODE)).thenReturn(land);

    }

    @Test
    public void testPersoonOverleden() {
        when(persoonModel.heeftBetrokkenheden()).thenReturn(false);
        vr00012.executeer(persoonModel, persoonBericht, null);

        Assert.assertEquals("Nederland", persoonBericht.getOverlijden().getLandOverlijden().getNaam().getWaarde());
        Assert.assertEquals(RedenOpschorting.OVERLIJDEN, persoonBericht.getOpschorting().getRedenOpschorting());
    }

    @Test
    public void testPersoonOverledenInBuitenland() {
        Land land = new Land();
        land.setNaam(new Naam("Andere land"));

        overlijdenGroep.setLandOverlijden(land);

        persoonBericht.setOverlijden(overlijdenGroep);
        when(persoonModel.heeftBetrokkenheden()).thenReturn(false);

        vr00012.executeer(persoonModel, persoonBericht, null);

        Assert.assertEquals("Andere land", persoonBericht.getOverlijden().getLandOverlijden().getNaam().getWaarde());
        Assert.assertEquals(RedenOpschorting.OVERLIJDEN, persoonBericht.getOpschorting().getRedenOpschorting());
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
        RelatieModel relatieModel = mock(RelatieModel.class);
        RelatieStandaardGroepModel relatieStandaardGroep = mock(RelatieStandaardGroepModel.class);
        
        Set<BetrokkenheidModel> betrokkenheden = new HashSet<BetrokkenheidModel>();
        betrokkenheden.add(betrokkenheid);

        when(persoonModel.heeftBetrokkenheden()).thenReturn(true);
        when(persoonModel.getBetrokkenheden()).thenReturn(betrokkenheden);
        when(betrokkenheid.getRelatie()).thenReturn(relatieModel);
        when(relatieModel.getSoort()).thenReturn(SoortRelatie.HUWELIJK);
        when(relatieModel.getGegevens()).thenReturn(relatieStandaardGroep);
        when(relatieStandaardGroep.getDatumEinde()).thenReturn(DatumUtil.gisteren());
        
        vr00012.executeer(persoonModel, persoonBericht, null);

        assertNull(persoonBericht.getBetrokkenheden());
    }
}

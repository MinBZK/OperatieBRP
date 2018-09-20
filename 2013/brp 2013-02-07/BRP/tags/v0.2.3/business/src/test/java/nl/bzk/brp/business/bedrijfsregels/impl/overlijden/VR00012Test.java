/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.overlijden;

import junit.framework.Assert;
import nl.bzk.brp.constanten.BrpConstanten;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.model.attribuuttype.Naam;
import nl.bzk.brp.model.groep.bericht.PersoonOverlijdenGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Land;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenOpschorting;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


public class VR00012Test {

    @Mock
    private ReferentieDataRepository referentieDataRepository;

    private VR00012 vr00012;


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        vr00012 = new VR00012();
        ReflectionTestUtils.setField(vr00012, "referentieRepository", referentieDataRepository);

        Land land = new Land();
        land.setCode(BrpConstanten.NL_LAND_CODE);
        land.setNaam(new Naam("Nederland"));

        Mockito.when(referentieDataRepository.vindLandOpCode(BrpConstanten.NL_LAND_CODE)).thenReturn(land);
    }

    @Test
    public void testPersoonOverleden() {
        PersoonBericht persoonBericht = new PersoonBericht();
        PersoonOverlijdenGroepBericht overlijdenGroep = new PersoonOverlijdenGroepBericht();
        persoonBericht.setOverlijden(overlijdenGroep);

        vr00012.executeer(null, persoonBericht, null);

        Assert.assertEquals("Nederland", persoonBericht.getOverlijden().getLandOverlijden().getNaam().getWaarde());
        Assert.assertEquals(RedenOpschorting.OVERLIJDEN, persoonBericht.getOpschorting().getRedenOpschorting());
    }

    @Test
    public void testPersoonOverledenInBuitenland() {
        PersoonBericht persoonBericht = new PersoonBericht();
        PersoonOverlijdenGroepBericht overlijdenGroep = new PersoonOverlijdenGroepBericht();
        Land land = new Land();
        land.setNaam(new Naam("Andere land"));

        overlijdenGroep.setLandOverlijden(land);

        persoonBericht.setOverlijden(overlijdenGroep);

        vr00012.executeer(null, persoonBericht, null);

        Assert.assertEquals("Andere land", persoonBericht.getOverlijden().getLandOverlijden().getNaam().getWaarde());
        Assert.assertEquals(RedenOpschorting.OVERLIJDEN, persoonBericht.getOpschorting().getRedenOpschorting());
    }

}

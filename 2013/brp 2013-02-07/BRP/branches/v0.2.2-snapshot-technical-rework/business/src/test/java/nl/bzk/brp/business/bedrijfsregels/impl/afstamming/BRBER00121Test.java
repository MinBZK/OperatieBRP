/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.afstamming;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.groep.bericht.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.groep.bericht.RelatieStandaardGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.BetrokkenheidBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.operationeel.BetrokkenheidModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.RelatieModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortMelding;
import nl.bzk.brp.model.validatie.Melding;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


public class BRBER00121Test {

    @Mock
    private PersoonRepository persoonRepository;

    private BRBER00121   dubbeleBSNCheck;

    @Before
    public void setUp() {
        dubbeleBSNCheck = new BRBER00121();
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(dubbeleBSNCheck, "persoonRepository", persoonRepository);
    }

    @Test
    public void testBSNNogNietAanwezig() {
        PersoonBericht persoon = new PersoonBericht();
        persoon.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        persoon.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer("123456789"));

        BetrokkenheidBericht betrokkenheid = new BetrokkenheidBericht();
        betrokkenheid.setRol(SoortBetrokkenheid.KIND);
        betrokkenheid.setBetrokkene(persoon);

        RelatieBericht relatie = new RelatieBericht();
        relatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        relatie.setGegevens(new RelatieStandaardGroepBericht());
        relatie.getBetrokkenheden().add(betrokkenheid);
        RelatieModel relatieModel = new RelatieModel(relatie);
        relatieModel.getBetrokkenheden().add(new BetrokkenheidModel(betrokkenheid, new PersoonModel(persoon), relatieModel));

        Mockito.when(persoonRepository.isBSNAlIngebruik(Matchers.any(Burgerservicenummer.class))).thenReturn(false);
        List<Melding> meldingen = dubbeleBSNCheck.executeer(null, relatie, null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testBSNAlAanwezig() {
        PersoonBericht persoon = new PersoonBericht();
        persoon.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        persoon.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer("111111111"));

        BetrokkenheidBericht betrokkenheid = new BetrokkenheidBericht();
        betrokkenheid.setRol(SoortBetrokkenheid.KIND);
        betrokkenheid.setBetrokkene(persoon);

        RelatieBericht relatie = new RelatieBericht();
        relatie.setGegevens(new RelatieStandaardGroepBericht());
        relatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        relatie.getBetrokkenheden().add(betrokkenheid);
        RelatieModel relatieModel = new RelatieModel(relatie);
        relatieModel.getBetrokkenheden().add(new BetrokkenheidModel(betrokkenheid, new PersoonModel(persoon), relatieModel));

        Mockito.when(persoonRepository.isBSNAlIngebruik(Matchers.any(Burgerservicenummer.class))).thenReturn(true);
        List<Melding> meldingen = dubbeleBSNCheck.executeer(null, relatie, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(SoortMelding.FOUT, meldingen.get(0).getSoort());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGeenRelatie() {
        dubbeleBSNCheck.executeer(null, new PersoonModel(new PersoonBericht()), null);
    }
}

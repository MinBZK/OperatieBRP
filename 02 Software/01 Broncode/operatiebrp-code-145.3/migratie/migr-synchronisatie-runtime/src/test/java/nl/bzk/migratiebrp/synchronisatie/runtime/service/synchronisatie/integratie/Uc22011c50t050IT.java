/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.integratie;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.StatusLeveringAdministratieveHandeling;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.TypeSynchronisatieBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class Uc22011c50t050IT extends AbstractUc220IT {

    private static final String ACHTERVOEGING_GEMEENTE_CODE = "01";

    @Test
    public void test() throws Exception {
        // BRP
        final SynchroniseerNaarBrpVerzoekBericht verzoekBrp = maakVerzoekObvExcel("UC220-11C50T050 BRP.xls");
        verzoekBrp.setTypeBericht(TypeSynchronisatieBericht.LG_01);
        verzoekBrp.setVerzendendeGemeente(getGemeenteBijhouding(verzoekBrp).getWaarde() + ACHTERVOEGING_GEMEENTE_CODE);
        final SynchroniseerNaarBrpAntwoordBericht antwoordBrp = getSubject().verwerkBericht(verzoekBrp);
        System.out.println("---[BRP]--------------------------------------------------------------------------");
        System.out.println(antwoordBrp.getMelding());
        System.out.println("------------------------------------------------------------------------------------");
        System.out.println("Gemeente van inschrijving: " + getGemeenteBijhouding(verzoekBrp));
        final BrpPersoonslijst plBrp = super.leesBrpPersoonslijst("8456196385");
        System.out.println("Bijhouder: " + getBijhoudingsPartij(plBrp));
        Assert.assertEquals(StatusType.TOEGEVOEGD, antwoordBrp.getStatus());

        // Update levering zodat de status overeenkomt met geleverd.
        List<AdministratieveHandeling> nietGeleverdeAdministratieveHandelingen = zoekNietGeleverdeAdministratieveHandelingen();
        for (AdministratieveHandeling nietGeleverdeAdministratieveHandeling : nietGeleverdeAdministratieveHandelingen) {
            updateLeveringStatusVoorAdministratieveHandeling(nietGeleverdeAdministratieveHandeling, StatusLeveringAdministratieveHandeling.GELEVERD);
        }

        // AGB-1
        final SynchroniseerNaarBrpVerzoekBericht verzoekAgb1 = maakVerzoekObvExcel("UC220-11C50T050 AGB-1.xls");
        verzoekAgb1.setTypeBericht(TypeSynchronisatieBericht.LA_01);
        verzoekAgb1.setVerzendendeGemeente(getGemeenteBijhouding(verzoekAgb1).getWaarde() + ACHTERVOEGING_GEMEENTE_CODE);
        final SynchroniseerNaarBrpAntwoordBericht antwoordAgb1 = getSubject().verwerkBericht(verzoekAgb1);
        System.out.println("---[AGB-1]--------------------------------------------------------------------------");
        System.out.println(antwoordAgb1.getMelding());
        System.out.println("------------------------------------------------------------------------------------");
        System.out.println("Gemeente van inschrijving: " + getGemeenteBijhouding(verzoekAgb1));
        final BrpPersoonslijst plAgb1 = super.leesBrpPersoonslijst("8456196385");
        System.out.println("Bijhouder: " + getBijhoudingsPartij(plAgb1));
        Assert.assertEquals(StatusType.VERVANGEN, antwoordAgb1.getStatus());

        // Update levering zodat de status overeenkomt met geleverd.
        nietGeleverdeAdministratieveHandelingen = zoekNietGeleverdeAdministratieveHandelingen();
        for (AdministratieveHandeling nietGeleverdeAdministratieveHandeling : nietGeleverdeAdministratieveHandelingen) {
            updateLeveringStatusVoorAdministratieveHandeling(nietGeleverdeAdministratieveHandeling, StatusLeveringAdministratieveHandeling.GELEVERD);
        }

        // AGB-2
        final SynchroniseerNaarBrpVerzoekBericht verzoekAgb2 = maakVerzoekObvExcel("UC220-11C50T050 AGB-2.xls");
        verzoekAgb2.setTypeBericht(TypeSynchronisatieBericht.LA_01);
        verzoekAgb2.setVerzendendeGemeente(getGemeenteBijhouding(verzoekAgb2).getWaarde() + ACHTERVOEGING_GEMEENTE_CODE);
        final SynchroniseerNaarBrpAntwoordBericht antwoordAgb2 = getSubject().verwerkBericht(verzoekAgb2);
        System.out.println("---[AGB-2]--------------------------------------------------------------------------");
        System.out.println(antwoordAgb2.getMelding());
        System.out.println("------------------------------------------------------------------------------------");
        System.out.println("Gemeente van inschrijving: " + getGemeenteBijhouding(verzoekAgb2));
        final BrpPersoonslijst plAgb2 = super.leesBrpPersoonslijst("8456196385");
        System.out.println("Bijhouder: " + getBijhoudingsPartij(plAgb2));
        Assert.assertEquals(StatusType.VERVANGEN, antwoordAgb2.getStatus());

        // END
        System.out.println("---[END]--------------------------------------------------------------------------");
    }

    private Lo3GemeenteCode getGemeenteBijhouding(final SynchroniseerNaarBrpVerzoekBericht verzoekBrp) {
        return verzoekBrp.getLo3Persoonslijst().getVerblijfplaatsStapel().getLo3ActueelVoorkomen().getInhoud().getGemeenteInschrijving();
    }

    private BrpPartijCode getBijhoudingsPartij(final BrpPersoonslijst persoonslijst) {
        final BrpStapel<BrpBijhoudingInhoud> stapel = persoonslijst.getBijhoudingStapel();
        if (stapel == null) {
            return null;
        }

        return stapel.getActueel().getInhoud().getBijhoudingspartijCode();
    }

}

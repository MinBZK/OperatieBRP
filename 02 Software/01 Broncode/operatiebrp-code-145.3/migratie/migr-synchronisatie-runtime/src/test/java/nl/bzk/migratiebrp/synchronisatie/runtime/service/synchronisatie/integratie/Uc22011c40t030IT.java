/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.integratie;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.StatusLeveringAdministratieveHandeling;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * <p>
 * <strong>JIRA: BLAUW-2200</strong>
 * </p>
 * <p>
 * <i>Testcase UC220-11C40T030:</i>
 * </p>
 *
 * <p>
 * PL BRP bevat een PL met cat08 09.10 = 1999 (RNI)
 * </p>
 * <p>
 * PL AGB bevat een PL met cat08 en cat58 09.10 = 1999 (RNI)
 * </p>
 * <p>
 * Bij het aanbieden van het sync bericht (gestart door uc811) wordt een controle gedaan of de gemeente van bijhouding
 * van PL AGB gelijk is aan gemeente van bijhouding van PL BRP. In bovenstaande zou de vergelijking gelijk moeten zijn,
 * maar de gemeente van bijhouding in de PL BRP wordt niet herkend. Dit komt omdat de gemeente wordt vergeleken met de
 * gemeente uit adres. Dit is niet juist.
 * </p>
 */
public class Uc22011c40t030IT extends AbstractUc220IT {

    private static final String ACHTERVOEGING_GEMEENTE_CODE = "01";

    @Test
    public void test() throws Exception {
        final SynchroniseerNaarBrpVerzoekBericht verzoekBrp = maakVerzoekObvExcel("UC220-11C40T030 BRP.xls");
        verzoekBrp.setVerzendendeGemeente(getGemeenteBijhouding(verzoekBrp).getWaarde() + ACHTERVOEGING_GEMEENTE_CODE);
        final SynchroniseerNaarBrpAntwoordBericht antwoordBrp = getSubject().verwerkBericht(verzoekBrp);
        Assert.assertEquals(StatusType.TOEGEVOEGD, antwoordBrp.getStatus());

        // Gezaghebbend
        final SynchroniseerNaarBrpVerzoekBericht verzoekAgb = maakVerzoekObvExcel("UC220-11C40T030 AGB.xls");
        verzoekAgb.setVerzendendeGemeente(getGemeenteBijhouding(verzoekAgb).getWaarde() + ACHTERVOEGING_GEMEENTE_CODE);

        // Update levering zodat de status overeenkomt met geleverd.
        List<AdministratieveHandeling> nietGeleverdeAdministratieveHandelingen = zoekNietGeleverdeAdministratieveHandelingen();
        for (AdministratieveHandeling nietGeleverdeAdministratieveHandeling : nietGeleverdeAdministratieveHandelingen) {
            updateLeveringStatusVoorAdministratieveHandeling(nietGeleverdeAdministratieveHandeling, StatusLeveringAdministratieveHandeling.GELEVERD);
        }

        final SynchroniseerNaarBrpAntwoordBericht antwoordAgb = getSubject().verwerkBericht(verzoekAgb);
        System.out.println(antwoordAgb.getMelding());
        Assert.assertEquals(StatusType.VERVANGEN, antwoordAgb.getStatus());
    }

    private Lo3GemeenteCode getGemeenteBijhouding(final SynchroniseerNaarBrpVerzoekBericht verzoekBrp) {
        return verzoekBrp.getLo3Persoonslijst().getVerblijfplaatsStapel().getLo3ActueelVoorkomen().getInhoud().getGemeenteInschrijving();
    }
}

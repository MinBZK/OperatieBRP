/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.integratie;

import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import org.junit.Assert;
import org.junit.Test;

/**
 * <strong>JIRA: BLAUW-2200<br/>
 * <i>Testcase UC220-11C40T030:</i></strong>
 * 
 * <p/>
 * PL BRP bevat een PL met cat08 09.10 = 1999 (RNI)
 * <p/>
 * PL AGB bevat een PL met cat08 en cat58 09.10 = 1999 (RNI)
 * <p/>
 * Bij het aanbieden van het sync bericht (gestart door uc811) wordt een controle gedaan of de gemeente van bijhouding
 * van PL AGB gelijk is aan gemeente van bijhouding van PL BRP. In bovenstaande zou de vergelijking gelijk moeten zijn,
 * maar de gemeente van bijhouding in de PL BRP wordt niet herkend. Dit komt omdat de gemeente wordt vergeleken met de
 * gemeente uit adres. Dit is niet juist.
 */
public class Uc22011c40t030Test extends AbstractUc220Test {

    @Test
    public void test() throws Exception {
        final SynchroniseerNaarBrpVerzoekBericht verzoekBrp = maakVerzoekObvExcel("UC220-11C40T030 BRP.xls");
        final SynchroniseerNaarBrpAntwoordBericht antwoordBrp = getSubject().verwerkBericht(verzoekBrp);
        Assert.assertEquals(StatusType.TOEGEVOEGD, antwoordBrp.getStatus());

        // Gezaghebbend
        final SynchroniseerNaarBrpVerzoekBericht verzoekAgb = maakVerzoekObvExcel("UC220-11C40T030 AGB.xls");
        verzoekAgb.setGezaghebbendBericht(true);
        final SynchroniseerNaarBrpAntwoordBericht antwoordAgb = getSubject().verwerkBericht(verzoekAgb);
        System.out.println(antwoordAgb.getMelding());
        Assert.assertEquals(StatusType.VERVANGEN, antwoordAgb.getStatus());
    }

}

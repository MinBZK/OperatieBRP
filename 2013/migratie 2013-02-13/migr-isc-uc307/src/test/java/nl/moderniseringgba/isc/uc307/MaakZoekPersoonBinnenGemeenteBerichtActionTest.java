/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc307;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.BerichtOnbekendException;
import nl.moderniseringgba.isc.esb.message.BerichtSyntaxException;
import nl.moderniseringgba.isc.esb.message.brp.impl.ZoekPersoonVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3BerichtFactory;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb01Bericht;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OuderInhoud;

import org.junit.Assert;
import org.junit.Test;

public class MaakZoekPersoonBinnenGemeenteBerichtActionTest {

    /**
     * LO3-factory voor het converteren van teletext naar Lo3-bericht.
     */
    private static final Lo3BerichtFactory LO3_FACTORY = new Lo3BerichtFactory();

    /**
     * Doelgemeente voor de test.
     */
    private static final String DOELGEMEENTE = "5678";

    /**
     * Brongemeente voor de test.
     */
    private static final String BRONGEMEENTE = "1234";

    /**
     * Teletex string van het Tb01 bericht.
     */
    private static final String TB01_TELETEX =
            "00000000Tb010M1 A123400317010990210011Maarten Jan0230003van0240005Haren03100082012110103200040600033000460300410001M81200071 A1234021030210013"
                    + "Gina Jennifer0230003van0240006Lloyds03100081990100103200040599033000460300410001V621000820121101031000210011Hans Herman0230003van0240005Haren0"
                    + "3100081989010103200040599033000460300410001M621000820121101";

    private final MaakZoekPersoonBinnenGemeenteBerichtAction maakZoekPersoonBinnenGemeenteBerichtAction =
            new MaakZoekPersoonBinnenGemeenteBerichtAction();

    @Test
    public void testHappyFlow() throws BerichtSyntaxException, BerichtOnbekendException, BerichtInhoudException {
        final Map<String, Object> parameters = new HashMap<String, Object>();

        final Tb01Bericht tb01Bericht = maakTb01BerichtVoorHappyFlow();
        final Lo3OuderInhoud moeder = UC307Utils.actueleMoederGegevens(tb01Bericht.getLo3Persoonslijst());

        parameters.put(UC307Constants.TB01_BERICHT, tb01Bericht);

        final Map<String, Object> result = maakZoekPersoonBinnenGemeenteBerichtAction.execute(parameters);

        final ZoekPersoonVerzoekBericht zoekPersoonVerzoekBericht =
                (ZoekPersoonVerzoekBericht) result
                        .get(MaakZoekPersoonBinnenGemeenteBerichtAction.ZOEK_PERSOON_BINNEN_GEMEENTE_BERICHT);

        Assert.assertNotNull("Bij HappyFlow hoort het zoekPersoonBinnenGemeenteBericht niet 'null' te zijn.",
                zoekPersoonVerzoekBericht);

        Assert.assertEquals("Bijhoudingsgemeente is niet gelijk aan doelgemeente.", tb01Bericht.getDoelGemeente(),
                zoekPersoonVerzoekBericht.getBijhoudingsGemeente().getFormattedStringCode());

        Assert.assertEquals("Geslachtsnaam moeder komt niet overeen.", moeder.getGeslachtsnaam(),
                zoekPersoonVerzoekBericht.getGeslachtsnaam());

        Assert.assertEquals("Voornamen moeder komen niet overeen.", moeder.getVoornamen(),
                zoekPersoonVerzoekBericht.getVoornamen());

    }

    private Tb01Bericht maakTb01BerichtVoorHappyFlow() throws BerichtSyntaxException, BerichtOnbekendException,
            BerichtInhoudException {

        // Tb01 bericht als teletex string.
        System.out.println("TB01 text: " + TB01_TELETEX);

        // Stel het Tb01 bericht op basis van de teletext string op.
        final Tb01Bericht tb01_bericht = (Tb01Bericht) LO3_FACTORY.getBericht(TB01_TELETEX);
        tb01_bericht.setDoelGemeente(DOELGEMEENTE);
        tb01_bericht.setBronGemeente(BRONGEMEENTE);
        return tb01_bericht;
    }
}

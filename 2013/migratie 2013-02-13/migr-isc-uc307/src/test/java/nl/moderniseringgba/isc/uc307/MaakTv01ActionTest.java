/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc307;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.lo3.Lo3BerichtFactory;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Inhoud;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tv01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.parser.Lo3PersoonslijstParser;
import nl.moderniseringgba.isc.esb.message.sync.impl.LeesUitBrpAntwoordBericht;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;

import org.junit.Assert;
import org.junit.Test;

public class MaakTv01ActionTest {

    /**
     * Doelgemeente voor de test.
     */
    private static final String DOELGEMEENTE = "5678";

    /**
     * Brongemeente voor de test.
     */
    private static final String BRONGEMEENTE = "1234";

    private static final String MESSAGE_ID_TB01 = "132456789";

    private static final String MESSAGE_ID_TV01 = "987121033";

    private static final String MESSAGE_NAME_TB01 = "input";

    private static final String MESSAGE_NAME_QUERY_RESPONSE = "leesUitBrpAntwoordBericht";
    /**
     * LO3-factory voor het converteren van teletext naar Lo3-bericht.
     */
    private static final Lo3BerichtFactory LO3_FACTORY = new Lo3BerichtFactory();

    /**
     * Teletex string van het Tb01 bericht.
     */
    private static final String TB01_TELETEX =
            "00000000Tb010M1 A123400317010990210011Maarten Jan0230003van0240005Haren03100082012110103200040600033000460300410001M81200071 A1234021030210013"
                    + "Gina Jennifer0230003van0240006Lloyds03100081990100103200040599033000460300410001V621000820121101031000210011Hans Herman0230003van0240005Haren0"
                    + "3100081989010103200040599033000460300410001M621000820121101";
    /**
     * LO3 persoonslijst als teletext string.
     */
    private static final String LO3_PL_STRING =
            "00697011640110010817238743501200092995889450210004Mart0240005Vries03100081990010103200040599033000460300410001M6110001E8110004059981200071 A9102851000819900101861000819900102021720110010192829389501200099911223340210006Jannie0240004Smit03100081969010103200041901033000460300410001M6210008199001018110004059981200071 A9102851000819900101861000819900102031750110010172625463201200093827261340210008Mitchell0240005Vries03100081970010103200041900033000460300410001M6210008199001018110004059981200071 A910285100081990010186100081990010207055681000819900101701000108010001180200170000000000000000008106091000405990920008199001011010001W102000405991030008199001011110001.7210001G851000819900101861000819900102";

    private final MaakTv01Action maakTv01Action = new MaakTv01Action();

    private static Lo3Persoonslijst maakLo3Persoonslijst() throws Exception {
        final List<Lo3CategorieWaarde> categorieen = Lo3Inhoud.parseInhoud(LO3_PL_STRING);
        return new Lo3PersoonslijstParser().parse(categorieen);
    }

    @Test
    public void testHappyFlow() throws Exception {
        final Map<String, Object> parameters = maakBerichtenVoorTests(false);

        final Map<String, Object> result = maakTv01Action.execute(parameters);
        Assert.assertNotNull("Bij HappyFlow hoort het Tv01 bericht niet 'null' te zijn.",
                result.get(UC307Constants.TV01_BERICHT));

        final Tv01Bericht tv01Bericht = (Tv01Bericht) result.get(UC307Constants.TV01_BERICHT);

        Assert.assertNotNull("Bij HappyFlow horen de verwijsgegevens niet 'null' te zijn.",
                tv01Bericht.getCategorieen());
        Assert.assertEquals("Bij HappyFlow horen de verwijsgegevens niet 'leeg' te zijn.", false, tv01Bericht
                .getCategorieen().get(0).isEmpty());
        Assert.assertEquals(DOELGEMEENTE, tv01Bericht.getBronGemeente());
        Assert.assertEquals(BRONGEMEENTE, tv01Bericht.getDoelGemeente());
        Assert.assertNotSame(MESSAGE_ID_TV01, tv01Bericht.getMessageId());
    }

    @Test
    public void testHerhaling() throws Exception {
        final Map<String, Object> parameters = maakBerichtenVoorTests(true);

        final Map<String, Object> result = maakTv01Action.execute(parameters);
        Assert.assertNotNull("Bij HappyFlow hoort het Tv01 bericht niet 'null' te zijn.",
                result.get(UC307Constants.TV01_BERICHT));

        final Tv01Bericht tv01Bericht = (Tv01Bericht) result.get(UC307Constants.TV01_BERICHT);

        Assert.assertNotNull("Bij HappyFlow horen de verwijsgegevens niet 'null' te zijn.",
                tv01Bericht.getCategorieen());
        Assert.assertEquals("Bij HappyFlow horen de verwijsgegevens niet 'leeg' te zijn.", false, tv01Bericht
                .getCategorieen().get(0).isEmpty());
        Assert.assertEquals(DOELGEMEENTE, tv01Bericht.getBronGemeente());
        Assert.assertEquals(BRONGEMEENTE, tv01Bericht.getDoelGemeente());
        Assert.assertEquals(MESSAGE_ID_TV01, tv01Bericht.getMessageId());
    }

    private Map<String, Object> maakBerichtenVoorTests(final boolean isHerhaling) throws Exception {
        final Map<String, Object> parameters = new HashMap<String, Object>();

        final Tb01Bericht tb01_bericht = (Tb01Bericht) LO3_FACTORY.getBericht(TB01_TELETEX);
        tb01_bericht.setMessageId(MESSAGE_ID_TB01);
        tb01_bericht.setDoelGemeente(DOELGEMEENTE);
        tb01_bericht.setBronGemeente(BRONGEMEENTE);
        parameters.put(MESSAGE_NAME_TB01, tb01_bericht);

        final LeesUitBrpAntwoordBericht queryResponseBericht =
                new LeesUitBrpAntwoordBericht(tb01_bericht.getMessageId(), maakLo3Persoonslijst());
        parameters.put(MESSAGE_NAME_QUERY_RESPONSE, queryResponseBericht);

        if (isHerhaling) {
            parameters.put("tv01Herhaling", "2");
            final Tv01Bericht tv01Bericht = new Tv01Bericht();
            tv01Bericht.setMessageId(MESSAGE_ID_TV01);
            parameters.put(UC307Constants.TV01_BERICHT, tv01Bericht);
        }

        return parameters;
    }

}

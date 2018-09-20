/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc201;

import java.util.List;

import nl.moderniseringgba.isc.esb.bpm.AbstractJbpmTest;
import nl.moderniseringgba.isc.esb.message.brp.impl.SynchronisatieSignaalBericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Inhoud;
import nl.moderniseringgba.isc.esb.message.lo3.parser.Lo3PersoonslijstParser;
import nl.moderniseringgba.isc.esb.message.mvi.impl.PlSyncBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.LeesUitBrpAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.LeesUitBrpVerzoekBericht;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;

import org.junit.Assert;
import org.junit.Test;

public class Uc201Test extends AbstractJbpmTest {
    private static final String LO3_PL_STRING =
            "00697011640110010817238743501200092995889450210004Mart0240005Vries03100081990010103200040599033000460300410001M6110001E8110004059981200071 A9102851000819900101861000819900102021720110010192829389501200099911223340210006Jannie0240004Smit03100081969010103200041901033000460300410001M6210008199001018110004059981200071 A9102851000819900101861000819900102031750110010172625463201200093827261340210008Mitchell0240005Vries03100081970010103200041900033000460300410001M6210008199001018110004059981200071 A910285100081990010186100081990010207055681000819900101701000108010001180200170000000000000000008106091000405990920008199001011010001W102000405991030008199001011110001.7210001G851000819900101861000819900102";

    public Uc201Test() {
        super("/uc201/processdefinition.xml,/foutafhandeling/processdefinition.xml");
    }

    private static Lo3Persoonslijst maakLo3Persoonslijst() throws Exception {
        final List<Lo3CategorieWaarde> categorieen = Lo3Inhoud.parseInhoud(LO3_PL_STRING);
        final Lo3Persoonslijst result = new Lo3PersoonslijstParser().parse(categorieen);
        return result;
    }

    @Test
    public void happyFlow() throws Exception {
        final SynchronisatieSignaalBericht input = new SynchronisatieSignaalBericht();
        input.setANummer("123456789");

        startProcess(input);

        // Verwacht: sync
        checkBerichten(0, 0, 0, 1);
        final LeesUitBrpVerzoekBericht queryBericht = getBericht(LeesUitBrpVerzoekBericht.class);
        Assert.assertEquals(input.getANummer(), queryBericht.getANummer().toString());

        // Verzend: query response
        final LeesUitBrpAntwoordBericht queryResponse =
                new LeesUitBrpAntwoordBericht(queryBericht.getMessageId(), maakLo3Persoonslijst());
        signalSync(queryResponse);

        // Verwacht: MVI
        checkBerichten(0, 1, 0, 0);
        getBericht(PlSyncBericht.class);

        Assert.assertTrue(processEnded());
    }

    @Test
    public void happyFlowSingleTimeout() throws Exception {
        final SynchronisatieSignaalBericht input = new SynchronisatieSignaalBericht();
        input.setANummer("123456789");

        startProcess(input);

        // Verwacht: sync
        checkBerichten(0, 0, 0, 1);
        final LeesUitBrpVerzoekBericht queryBericht = getBericht(LeesUitBrpVerzoekBericht.class);
        Assert.assertEquals(input.getANummer(), queryBericht.getANummer().toString());

        // Timeout (sync geconfigureerd op drie keer herhalen)
        signalProcess("timeout");

        // Verwacht: sync
        checkBerichten(0, 0, 0, 1);
        final LeesUitBrpVerzoekBericht queryBericht2 = getBericht(LeesUitBrpVerzoekBericht.class);
        Assert.assertEquals(input.getANummer(), queryBericht2.getANummer().toString());

        // Verzend: query response
        final LeesUitBrpAntwoordBericht queryResponse =
                new LeesUitBrpAntwoordBericht(queryBericht2.getMessageId(), maakLo3Persoonslijst());
        signalSync(queryResponse);

        // Verwacht: MVI
        checkBerichten(0, 1, 0, 0);
        getBericht(PlSyncBericht.class);

        Assert.assertTrue(processEnded());
    }

    @Test
    public void happyFlowFoutRestartGoed() throws Exception {
        final SynchronisatieSignaalBericht input = new SynchronisatieSignaalBericht();
        input.setANummer("123456789");

        startProcess(input);

        // Verwacht: sync
        checkBerichten(0, 0, 0, 1);
        final LeesUitBrpVerzoekBericht queryBericht = getBericht(LeesUitBrpVerzoekBericht.class);
        Assert.assertEquals(input.getANummer(), queryBericht.getANummer().toString());

        // Verzend: query response
        final LeesUitBrpAntwoordBericht queryResponse =
                new LeesUitBrpAntwoordBericht(queryBericht.getMessageId(), "Foutmelding");
        signalSync(queryResponse);

        // Assert human task (beheerder actie)
        Assert.assertTrue(processHumanTask());
        checkBerichten(0, 0, 0, 0);

        //
        signalHumanTask("restartAtLeesUitBrpVerzoek");

        // Verwacht: sync
        checkBerichten(0, 0, 0, 1);
        final LeesUitBrpVerzoekBericht queryBericht2 = getBericht(LeesUitBrpVerzoekBericht.class);
        Assert.assertEquals(input.getANummer(), queryBericht2.getANummer().toString());

        // Verzend: query response
        final LeesUitBrpAntwoordBericht queryResponse2 =
                new LeesUitBrpAntwoordBericht(queryBericht2.getMessageId(), maakLo3Persoonslijst());
        signalSync(queryResponse2);

        // Verwacht: MVI
        checkBerichten(0, 1, 0, 0);
        getBericht(PlSyncBericht.class);

        Assert.assertTrue(processEnded());
    }
}

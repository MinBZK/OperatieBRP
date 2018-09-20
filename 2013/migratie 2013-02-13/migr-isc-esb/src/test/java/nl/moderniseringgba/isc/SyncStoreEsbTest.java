/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc;

import java.util.ArrayList;
import java.util.List;

import nl.moderniseringgba.isc.esb.message.BerichtId;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Inhoud;
import nl.moderniseringgba.isc.esb.message.lo3.parser.Lo3PersoonslijstParser;
import nl.moderniseringgba.isc.esb.message.sync.impl.LeesUitBrpVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.moderniseringgba.migratie.conversie.model.logging.LogRegel;
import nl.moderniseringgba.migratie.conversie.model.logging.LogSeverity;
import nl.moderniseringgba.migratie.conversie.model.logging.LogType;

import org.junit.Test;

public class SyncStoreEsbTest extends AbstractEsbTest {

    private static final String LO3_PL_STRING =
            "00697011640110010817238743501200092995889450210004Mart0240005Vries03100081990010103200040599033000460300410001M6110001E8110004059981200071 A9102851000819900101861000819900102021720110010192829389501200099911223340210006Jannie0240004Smit03100081969010103200041901033000460300410001M6210008199001018110004059981200071 A9102851000819900101861000819900102031750110010172625463201200093827261340210008Mitchell0240005Vries03100081970010103200041900033000460300410001M6210008199001018110004059981200071 A910285100081990010186100081990010207055681000819900101701000108010001180200170000000000000000008106091000405990920008199001011010001W102000405991030008199001011110001.7210001G851000819900101861000819900102";

    @Test
    public void test() throws Exception {
        final List<Lo3CategorieWaarde> categorieen = Lo3Inhoud.parseInhoud(LO3_PL_STRING);
        final Lo3Persoonslijst lo3 = new Lo3PersoonslijstParser().parse(categorieen);
        final SynchroniseerNaarBrpVerzoekBericht storeBericht = new SynchroniseerNaarBrpVerzoekBericht(lo3, null);

        final Bericht bericht = new Bericht();
        bericht.setMessageId(BerichtId.generateMessageId());
        bericht.setInhoud(storeBericht.format());

        verstuurSyncRequest(bericht);
    }

    @Test
    public void testQuery() throws Exception {
        final LeesUitBrpVerzoekBericht queryBericht = new LeesUitBrpVerzoekBericht(8172387435L);

        final Bericht bericht = new Bericht();
        bericht.setMessageId(BerichtId.generateMessageId());
        bericht.setInhoud(queryBericht.format());

        verstuurSyncRequest(bericht);
    }

    private List<LogRegel> maakLogging() {
        final List<LogRegel> logging = new ArrayList<LogRegel>();
        logging.add(new LogRegel(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 4, 2), LogSeverity.INFO,
                LogType.BIJZONDERE_SITUATIE, "TEST-01", "Test logregel nummer een."));
        logging.add(new LogRegel(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_02, 3, 1), LogSeverity.ERROR,
                LogType.PRECONDITIE, "TEST-02", "Test logregel nummer twee."));
        logging.add(new LogRegel(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_03, 2, 4), LogSeverity.WARNING,
                LogType.STRUCTUUR, "TEST-03", "Test logregel nummer drie."));
        logging.add(new LogRegel(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_04, 1, 3), LogSeverity.WARNING,
                LogType.VERWERKING, "TEST-04", "Test logregel nummer vier."));

        return logging;
    }

}

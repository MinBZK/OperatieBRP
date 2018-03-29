/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.MessageIdGenerator;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AutorisatieAntwoordRecordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AutorisatieRecordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AutorisatieRecordsType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AutorisatieType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogRegel;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import org.junit.Assert;
import org.junit.Test;

public class AutorisatieAntwoordBerichtTest {

    private static final String FOUTMELDING = "Foutmelding";
    private final SyncBerichtFactory factory = SyncBerichtFactory.SINGLETON;

    @Test
    public void testAutorisatieAntwoordBericht() throws BerichtInhoudException {
        final AutorisatieAntwoordBericht bericht = new AutorisatieAntwoordBericht();
        bericht.setMessageId(MessageIdGenerator.generateId());

        AutorisatieAntwoordRecordType antwoordRegel = new AutorisatieAntwoordRecordType();
        antwoordRegel.setAutorisatieId(345343L);
        antwoordRegel.setStatus(StatusType.FOUT);
        antwoordRegel.setFoutmelding(FOUTMELDING);
        bericht.getAutorisatieTabelRegels().add(antwoordRegel);


        final String xml = bericht.format();
        final SyncBericht parsed = factory.getBericht(xml);

        Assert.assertEquals(AutorisatieAntwoordBericht.class, parsed.getClass());
        parsed.setMessageId(bericht.getMessageId());
        Assert.assertEquals(bericht, parsed);
        Assert.assertEquals(345343L, bericht.getAutorisatieTabelRegels().get(0).getAutorisatieId());
        Assert.assertEquals(StatusType.FOUT, bericht.getAutorisatieTabelRegels().get(0).getStatus());
        Assert.assertEquals(FOUTMELDING, bericht.getAutorisatieTabelRegels().get(0).getFoutmelding());
    }

    @Test
    public void testVerwerkLogging() {
        // Vraagbericht
        final AutorisatieType type = new AutorisatieType();
        type.setAfnemerCode("1234");
        type.setAutorisatieTabelRegels(new AutorisatieRecordsType());
        type.getAutorisatieTabelRegels().getAutorisatieTabelRegel().add(maakBerichtInhoud(1, 20170101, null));
        type.getAutorisatieTabelRegels().getAutorisatieTabelRegel().add(maakBerichtInhoud(2, 20000101, 20161231));
        AutorisatieBericht vraagBericht = new AutorisatieBericht(type);

        // Logging
        Set<LogRegel> logRegels = new HashSet<>();
        logRegels.add(maakLogRegel(SoortMeldingCode.AUT001, -1,-1));
        logRegels.add(maakLogRegel(SoortMeldingCode.AUT002, 0,0));

        final AutorisatieAntwoordBericht antwoordBericht = new AutorisatieAntwoordBericht();
        antwoordBericht.setMessageId(MessageIdGenerator.generateId());

        // Verwerk logging
        antwoordBericht.verwerkLogging(vraagBericht, logRegels);

        // Expect
        Assert.assertEquals(1L, antwoordBericht.getAutorisatieTabelRegels().get(0).getAutorisatieId());
        Assert.assertEquals("AUT001, AUT002", antwoordBericht.getAutorisatieTabelRegels().get(0).getFoutmelding());
        Assert.assertEquals(2L, antwoordBericht.getAutorisatieTabelRegels().get(1).getAutorisatieId());
        Assert.assertEquals("AUT001", antwoordBericht.getAutorisatieTabelRegels().get(1).getFoutmelding());
    }

    private LogRegel maakLogRegel(SoortMeldingCode code, int stapel, int volgnr) {
        Lo3Herkomst herkomst = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_35, stapel, volgnr);

       return new LogRegel(herkomst, LogSeverity.ERROR, code, null);
}


    /**
     * Maak een AutorisatieRecordType aan met verplichte velden en een startdatum en evt einddatum.
     * @return AutorisatieRecordType
     */
    private AutorisatieRecordType maakBerichtInhoud(int autorisatieId, final Integer datumIngang, final Integer datumEinde) {
        final AutorisatieRecordType record = new AutorisatieRecordType();
        record.setAutorisatieId(autorisatieId);
        record.setGeheimhoudingInd(new Short("0"));
        record.setVerstrekkingsBeperking(new Short("0"));
        record.setTabelRegelStartDatum(new BigInteger(datumIngang.toString()));
        record.setTabelRegelEindDatum(datumEinde == null ? null : new BigInteger(datumEinde.toString()));
        return record;
    }

}

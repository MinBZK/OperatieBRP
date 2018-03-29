/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.MessageIdGenerator;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.SynchroniseerNaarBrpAntwoordType;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogRegel;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import org.junit.Assert;
import org.junit.Test;

//import nl.bzk.isc.esb.message.bzm.generated.SoortMeldingCode;

public class SynchroniseerNaarBrpAntwoordBerichtTest extends AbstractSyncBerichtTestBasis {

    private static final Long ADMINISTRATIEVE_HANDELING_ID = Long.valueOf(123123L);

    @Test
    public void test() throws BerichtInhoudException, ClassNotFoundException, IOException {
        final SynchroniseerNaarBrpAntwoordBericht bericht = new SynchroniseerNaarBrpAntwoordBericht();
        final String MELDING = "Situatie onduidelijk";
        bericht.setMessageId(MessageIdGenerator.generateId());
        bericht.setStatus(StatusType.ONDUIDELIJK);
        bericht.setMelding(MELDING);
        bericht.toevoegenKandidaat(123, 4, "EERSTE-PL");
        bericht.toevoegenKandidaat(1234, 6, "TWEEDE-PL");
        bericht.toevoegenKandidaat(12345, 7, "DERDE-PL");
        bericht.setAdministratieveHandelingIds(Arrays.asList(ADMINISTRATIEVE_HANDELING_ID));

        controleerFormatParse(bericht);
        controleerSerialization(bericht);

        Assert.assertNull(bericht.getStartCyclus());
        Assert.assertEquals("SynchroniseerNaarBrpAntwoord", bericht.getBerichtType());
        Assert.assertEquals(StatusType.ONDUIDELIJK, bericht.getStatus());
        Assert.assertNotNull(bericht.getKandidaten());
        Assert.assertEquals(3, bericht.getKandidaten().size());
        Assert.assertNotNull(bericht.getAdministratieveHandelingIds());
        Assert.assertEquals(1, bericht.getAdministratieveHandelingIds().size());
        Assert.assertEquals(MELDING, bericht.getMelding());
        Assert.assertEquals(ADMINISTRATIEVE_HANDELING_ID, bericht.getGerelateerdeInformatie().getAdministratieveHandelingIds().get(0));
        Assert.assertEquals("SynchroniseerNaarBrpAntwoord", bericht.getBerichtType());
    }

    @Test
    public void testNullKandidaten() throws BerichtInhoudException, ClassNotFoundException, IOException {
        final SynchroniseerNaarBrpAntwoordBericht bericht = new SynchroniseerNaarBrpAntwoordBericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        bericht.setStatus(StatusType.ONDUIDELIJK);

        controleerFormatParse(bericht);
        controleerSerialization(bericht);

        Assert.assertNotNull(bericht.getKandidaten());
        Assert.assertEquals(0, bericht.getKandidaten().size());
    }

    @Test
    public void testGeenKandidaten() throws BerichtInhoudException, ClassNotFoundException, IOException {
        final SynchroniseerNaarBrpAntwoordBericht bericht = new SynchroniseerNaarBrpAntwoordBericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        bericht.setStatus(StatusType.ONDUIDELIJK);

        controleerFormatParse(bericht);
        controleerSerialization(bericht);

        Assert.assertNotNull(bericht.getKandidaten());
        Assert.assertEquals(0, bericht.getKandidaten().size());
    }

    @Test
    public void testEenKandidaat() throws BerichtInhoudException, ClassNotFoundException, IOException {
        final SynchroniseerNaarBrpAntwoordType type = new SynchroniseerNaarBrpAntwoordType();
        final SynchroniseerNaarBrpAntwoordBericht bericht = new SynchroniseerNaarBrpAntwoordBericht(type);
        bericht.setStatus(StatusType.ONDUIDELIJK);
        bericht.setMessageId(MessageIdGenerator.generateId());
        bericht.toevoegenKandidaat(3, 5, "PL");

        controleerFormatParse(bericht);
        controleerSerialization(bericht);

        Assert.assertNotNull(bericht.getKandidaten());
        Assert.assertEquals(1, bericht.getKandidaten().size());
    }

    @Test
    public void testLogging() throws BerichtInhoudException, ClassNotFoundException, IOException {
        final SynchroniseerNaarBrpAntwoordBericht bericht = new SynchroniseerNaarBrpAntwoordBericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        bericht.setStatus(StatusType.ONDUIDELIJK);

        final Set<LogRegel> logRegels = new HashSet<>();
        logRegels.add(
                new LogRegel(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0), LogSeverity.INFO, SoortMeldingCode.AFN001, Lo3ElementEnum.ANUMMER));

        bericht.setLogging(logRegels);

        controleerFormatParse(bericht);
        controleerSerialization(bericht);

        final HashSet<LogRegel> resultLogRegels = new HashSet<>();
        resultLogRegels.addAll(bericht.getLogging());

        Assert.assertEquals(logRegels, resultLogRegels);

    }

}

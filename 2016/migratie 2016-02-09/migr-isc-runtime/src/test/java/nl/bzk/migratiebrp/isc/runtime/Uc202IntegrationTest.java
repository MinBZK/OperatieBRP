/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime;

import java.util.List;
import junit.framework.Assert;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Lg01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.PersoonsaanduidingType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringInfoAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringInfoVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.junit.Test;

public class Uc202IntegrationTest extends AbstractIntegrationTest {
    private static final Logger LOG = LoggerFactory.getLogger();

    @Test
    public void test() throws BerichtInhoudException {
        LOG.info("Starting test");

        // Valideer
        controleerAlleProcessenBeeindigd();

        final Long aNummer = 1231231234L;
        final Long msSequenceNumber = 123L;

        // Start
        final Lo3Bericht input = maakLg01(aNummer, aNummer, null, null, "0599", "0599");
        input.setMessageId("lg01-001");
        putMessage(VOISC_ONTVANGST_QUEUE, input, msSequenceNumber, null);

        // Blokkering info opvragen
        final BlokkeringInfoVerzoekBericht blokkeringInfoVerzoek = expectMessage(SYNC_VERZOEK_QUEUE, BlokkeringInfoVerzoekBericht.class);
        Assert.assertEquals("1231231234", blokkeringInfoVerzoek.getANummer());
        final SyncBericht blokkeringInfoAntwoord = maakBlokkeringInfoAntwoordBericht(blokkeringInfoVerzoek, null, null, null);
        putMessage(SYNC_ANTWOORD_QUEUE, blokkeringInfoAntwoord);

        // Sync naar BRP
        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoek = expectMessage(SYNC_VERZOEK_QUEUE, SynchroniseerNaarBrpVerzoekBericht.class);
        final SyncBericht synchroniseerNaarBrpAntwoord = maakSynchroniseerNaarBrpAntwoordBericht(synchroniseerNaarBrpVerzoek, StatusType.TOEGEVOEGD, null);
        putMessage(SYNC_ANTWOORD_QUEUE, synchroniseerNaarBrpAntwoord);

        // Controleer
        controleerAlleProcessenBeeindigd();
    }

    /* *** LG01 *************************************************************************************** */

    protected Lg01Bericht maakLg01(
        final Long aNummerKop,
        final Long aNummerInhoud,
        final Long vorigANummerKop,
        final Long vorigANummerInhoud,
        final String gemeenteKop,
        final String gemeenteInhoud)
    {

        final Lg01Bericht lg01 = new Lg01Bericht();
        lg01.setMessageId(generateMessageId());
        lg01.setHeader(Lo3HeaderVeld.A_NUMMER, aNummerKop == null ? null : aNummerKop.toString());
        lg01.setHeader(Lo3HeaderVeld.OUD_A_NUMMER, vorigANummerKop == null ? null : vorigANummerKop.toString());
        lg01.setLo3Persoonslijst(maakPersoonslijst(aNummerInhoud, vorigANummerInhoud, gemeenteInhoud));
        lg01.setBronGemeente(gemeenteKop);
        lg01.setDoelGemeente("3000200");

        final String formattedLg01 = lg01.format();

        try {
            lg01.parse(formattedLg01);
        } catch (
            BerichtSyntaxException
            | BerichtInhoudException e)
        {
            e.printStackTrace();
            return null;
        }

        return lg01;
    }

    protected Lo3Persoonslijst maakPersoonslijst(final Long aNummerInhoud, final Long vorigANummerInhoud, final String gemeenteInhoud) {

        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        // @formatter:off
        builder.persoonStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Persoon(aNummerInhoud,
                                                                                                          null,
                                                                                                          "Jan",
                                                                                                          null,
                                                                                                          null,
                                                                                                          "Jansen",
                                                                                                          19700101,
                                                                                                          "0518",
                                                                                                          "6030",
                                                                                                          "M",
                                                                                                          vorigANummerInhoud,
                                                                                                          null,
                                                                                                          "E"),
                                                                               Lo3StapelHelper.lo3Akt(1),
                                                                               Lo3StapelHelper.lo3His(19700101),
                                                                               new Lo3Herkomst(Lo3CategorieEnum.PERSOON, 0, 0))));

        builder.verblijfplaatsStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Verblijfplaats(gemeenteInhoud,
                                                                                                                        1970101,
                                                                                                                        1970101,
                                                                                                                        "Straat",
                                                                                                                        15,
                                                                                                                        "9876AA",
                                                                                                                        "I"),
                                                                                      null,
                                                                                      Lo3StapelHelper.lo3His(19700101),
                                                                                      new Lo3Herkomst(Lo3CategorieEnum.VERBLIJFPLAATS, 0, 0))));
        // @formatter:on

        return builder.build();
    }

    /* *** BLOKKERING INFO ***************************************************************************************** */

    protected BlokkeringInfoAntwoordBericht maakBlokkeringInfoAntwoordBericht(
        final BlokkeringInfoVerzoekBericht blokkeringInfoVerzoek,
        final PersoonsaanduidingType persoonsaanduiding,
        final String processId,
        final String gemeenteNaar)
    {
        final BlokkeringInfoAntwoordBericht result = new BlokkeringInfoAntwoordBericht();
        result.setStatus(StatusType.OK);
        result.setPersoonsaanduiding(persoonsaanduiding);
        result.setProcessId(processId);
        result.setGemeenteNaar(gemeenteNaar);
        result.setMessageId(generateMessageId());
        result.setCorrelationId(blokkeringInfoVerzoek.getMessageId());

        return result;
    }

    /* *** SYNC NAAR BRP******************************************************************************************** */

    protected SynchroniseerNaarBrpAntwoordBericht maakSynchroniseerNaarBrpAntwoordBericht(
        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoek,
        final StatusType status,
        final List<String> persoonslijsten)
    {
        final SynchroniseerNaarBrpAntwoordBericht result = new SynchroniseerNaarBrpAntwoordBericht();
        result.setStatus(status);
        result.setMessageId(generateMessageId());
        result.setCorrelationId(synchroniseerNaarBrpVerzoek.getMessageId());
        result.setKandidaten(persoonslijsten);

        return result;
    }

}

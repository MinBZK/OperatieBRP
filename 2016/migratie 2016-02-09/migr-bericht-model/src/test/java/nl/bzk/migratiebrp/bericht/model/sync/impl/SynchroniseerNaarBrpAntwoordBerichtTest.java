/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.MessageIdGenerator;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3PersoonslijstFormatter;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.SynchroniseerNaarBrpAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.SynchroniseerNaarBrpAntwoordType.Persoonslijsten;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogRegel;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;

import org.junit.Assert;
import org.junit.Test;

//import nl.bzk.isc.esb.message.bzm.generated.SoortMeldingCode;

public class SynchroniseerNaarBrpAntwoordBerichtTest extends AbstractSyncBerichtTest {

    private static final Long ADMINISTRATIEVE_HANDELING_ID = Long.valueOf(123123L);

    @Test
    public void test() throws BerichtInhoudException, ClassNotFoundException, IOException {
        final SynchroniseerNaarBrpAntwoordBericht bericht = new SynchroniseerNaarBrpAntwoordBericht();
        final String MELDING = "Situatie onduidelijk";
        bericht.setMessageId(MessageIdGenerator.generateId());
        bericht.setStatus(StatusType.ONDUIDELIJK);
        bericht.setMelding(MELDING);
        bericht.setKandidaten(maakPersoonslijsten(1112223334L, 2223334445L, 3334445556L, 4445556667L, 5556667778L, 6667778889L));
        bericht.setAdministratieveHandelingIds(Arrays.asList(ADMINISTRATIEVE_HANDELING_ID));

        controleerFormatParse(bericht);
        controleerSerialization(bericht);

        Assert.assertNull(bericht.getStartCyclus());
        Assert.assertEquals("SynchroniseerNaarBrpAntwoord", bericht.getBerichtType());
        Assert.assertEquals(StatusType.ONDUIDELIJK, bericht.getStatus());
        Assert.assertNotNull(bericht.getKandidaten());
        Assert.assertEquals(6, bericht.getKandidaten().size());
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
        bericht.setKandidaten(null);

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
        bericht.setKandidaten(Collections.<String>emptyList());

        controleerFormatParse(bericht);
        controleerSerialization(bericht);

        Assert.assertNotNull(bericht.getKandidaten());
        Assert.assertEquals(0, bericht.getKandidaten().size());
    }

    @Test
    public void testEenKandidaat() throws BerichtInhoudException, ClassNotFoundException, IOException {
        final SynchroniseerNaarBrpAntwoordType type = new SynchroniseerNaarBrpAntwoordType();
        type.setStatus(StatusType.ONDUIDELIJK);
        type.setPersoonslijsten(new Persoonslijsten());
        final SynchroniseerNaarBrpAntwoordBericht bericht = new SynchroniseerNaarBrpAntwoordBericht(type);
        bericht.setMessageId(MessageIdGenerator.generateId());
        bericht.setKandidaten(maakPersoonslijsten(123143L));

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
        bericht.setKandidaten(Collections.<String>emptyList());

        final Set<LogRegel> logRegels = new HashSet<>();
        logRegels.add(new LogRegel(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0), LogSeverity.INFO, SoortMeldingCode.AFN001, Lo3ElementEnum.ANUMMER));

        bericht.setLogging(logRegels);

        controleerFormatParse(bericht);
        controleerSerialization(bericht);

        final HashSet<LogRegel> resultLogRegels = new HashSet<>();
        resultLogRegels.addAll(bericht.getLogging());

        Assert.assertEquals(logRegels, resultLogRegels);

    }

    /* *** SYNC NAAR BRP******************************************************************************************** */

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

    protected List<String> maakPersoonslijsten(final Long... aNummers) {
        final List<String> result = new ArrayList<>();

        if (aNummers != null) {
            for (final Long aNummer : aNummers) {
                final Lo3Persoonslijst pl = maakPersoonslijst(aNummer, null, "0599");
                result.add(plToString(pl));
            }
        }

        return result;
    }

    private String plToString(final Lo3Persoonslijst pl) {
        return Lo3Inhoud.formatInhoud(new Lo3PersoonslijstFormatter().format(pl));
    }

}

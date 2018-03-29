/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.integratie;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3PersoonslijstFormatter;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.SyncParameters;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.SynchronisatieBerichtService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Rollback(false)
public class InitieleVullingSynchronisatieVerwerkerIT extends AbstractSynchronisatieServiceIT {

    @Inject
    @Named("syncDalDataSource")
    private DataSource dataSource;

    @Inject
    @Named("synchroniseerNaarBrpService")
    private SynchronisatieBerichtService<SynchroniseerNaarBrpVerzoekBericht, SynchroniseerNaarBrpAntwoordBericht> subject;

    @Inject
    @Named("syncParameters")
    private SyncParameters syncParameters;

    @Before
    public void setupSyncParameters() {
        syncParameters.setInitieleVulling(true);
    }

    @Test
    @Transactional(propagation = Propagation.NEVER, value = "syncDalTransactionManager")
    public void testNieuwePl() throws Exception {

        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                Lo3StapelHelper
                                        .lo3Persoon("4241824289", "424824289", "Piet", null, null, "Klaassen", 19770101, "0516", "6030", "M", null, null, null),
                                Lo3CategorieEnum.PERSOON)));

        builder.ouder1Stapel(
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                Lo3StapelHelper.lo3Ouder("9806953249", "Betsy", "Pietersen", 19130101, "0516", "6030", "V", 19770101),
                                Lo3CategorieEnum.OUDER_1)));
        builder.ouder2Stapel(
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                Lo3StapelHelper.lo3Ouder("9806953249", "Johan", "Klaassen", 19130101, "0516", "6030", "M", 19770101),
                                Lo3CategorieEnum.OUDER_2)));

        builder.inschrijvingStapel(
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                Lo3StapelHelper.lo3Inschrijving(null, null, null, 19770101, "0516", 0, 1, 20130101000000000L, null),
                                null,
                                new Lo3Historie(null, null, null),
                                new Lo3Herkomst(Lo3CategorieEnum.INSCHRIJVING, 0, 0))));
        builder.verblijfplaatsStapel(
                Lo3StapelHelper.lo3Stapel(
                        Lo3StapelHelper.lo3Cat(
                                Lo3StapelHelper.lo3Verblijfplaats("0516", 19770101, 19770101, "Langstraat", 14, "1234RE", "A"),
                                null,
                                Lo3StapelHelper.lo3His(19770101),
                                new Lo3Herkomst(Lo3CategorieEnum.VERBLIJFPLAATS, 0, 0))));

        final Lo3Persoonslijst lo3Pl = builder.build();
        final List<Lo3CategorieWaarde> categorieen = new Lo3PersoonslijstFormatter().format(lo3Pl);

        // Check uitgangssituatie, als hier iets niet klopt dan interfereren tests met elkaar.
        Assert.assertFalse(komtAnummerActueelVoor(lo3Pl.getActueelAdministratienummer()));

        // Execute
        final SynchroniseerNaarBrpVerzoekBericht verzoek = new SynchroniseerNaarBrpVerzoekBericht();
        verzoek.setMessageId("iv-test-ok");
        verzoek.setLo3PersoonslijstAlsTeletexString(Lo3Inhoud.formatInhoud(categorieen));
        final SynchroniseerNaarBrpAntwoordBericht antwoord = subject.verwerkBericht(verzoek);

        System.out.println("Melding:\n" + antwoord.getMelding());

        // Verify
        Assert.assertEquals(verzoek.getMessageId(), antwoord.getCorrelationId());
        Assert.assertEquals(StatusType.TOEGEVOEGD, antwoord.getStatus());

        Assert.assertTrue(komtAnummerActueelVoor(lo3Pl.getActueelAdministratienummer()));
    }
}

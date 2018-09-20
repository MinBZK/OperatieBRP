/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.service;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.Set;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.brp.blobifier.service.AfnemerIndicatieBlobifierService;
import nl.bzk.brp.dataaccess.repository.PersoonHisVolledigRepository;
import nl.bzk.brp.dataaccess.test.AbstractDBUnitIntegratieTest;
import nl.bzk.brp.dataaccess.test.Data;
import nl.bzk.brp.levering.afnemerindicaties.model.BewerkAfnemerindicatieResultaat;
import nl.bzk.brp.levering.business.toegang.leveringsautorisatie.cache.LeveringAutorisatieCache;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.hisvolledig.autaut.PersoonAfnemerindicatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.impl.autaut.PersoonAfnemerindicatieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.autaut.HisPersoonAfnemerindicatieModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;


@Data(resources = {
    "classpath:/data/stamgegevensLandGebied.xml",
    "classpath:/data/stamgegevensNationaliteit.xml",
    "classpath:/data/testdata.xml",
    "classpath:/data/testdata-autaut.xml",
    "classpath:/data/schoon-persafnemer.xml",
    "classpath:/data/pre-conv-clean.xml" })
public class AfnemerindicatiesMetRegelsServiceIntegratieTest extends AbstractDBUnitIntegratieTest {

    private static final Long ADMINISTRATIEVE_HANDELING_ID = 1L;

    @Inject
    @Named("lezenSchrijvenTransactionManager")
    private PlatformTransactionManager transactionManager;

    @Inject
    private AfnemerindicatiesZonderRegelsService afnemerindicatiesZonderRegelsServiceService;

    @Inject
    private AfnemerindicatiesMetRegelsService afnemerindicatiesMetRegelsService;

    @Inject
    private PersoonHisVolledigRepository persoonHisVolledigRepository;

    @Inject
    private AfnemerIndicatieBlobifierService afnemerIndicatieBlobifierService;

    @Inject
    private LeveringAutorisatieCache leveringAutorisatieCache;

    @Before
    public void before() {
        leveringAutorisatieCache.herlaad();
    }

    final int toegangLeveringsautorisatieId = 1;
    final int partijCode             = 34401;


    @Test
    public final void testPlaatsAfnemerIndicatieMetRegels() {

        final int persoonId = 2;
        final int dienstId = 1;

        final TransactionStatus transactionStatus = geefTransactionStatus();
        final BewerkAfnemerindicatieResultaat resultaat = afnemerindicatiesMetRegelsService
            .plaatsAfnemerindicatie(toegangLeveringsautorisatieId, persoonId, dienstId, null, null);
        transactionManager.commit(transactionStatus);
        resultaat.setAdministratieveHandelingId(ADMINISTRATIEVE_HANDELING_ID);

        final Set<PersoonAfnemerindicatieHisVolledigImpl> afnemerindicatieBlobs = afnemerIndicatieBlobifierService.leesBlob(persoonId);
        assertThat(afnemerindicatieBlobs.size(), is(greaterThan(0)));

        final PersoonHisVolledig persoonHisVolledig = persoonHisVolledigRepository.leesGenormalizeerdModel(persoonId);
        Assert.assertEquals(1, persoonHisVolledig.getAfnemerindicaties().size());
        final PersoonAfnemerindicatieHisVolledig afnemerindicatieOpPersoon =
            persoonHisVolledig.getAfnemerindicaties().iterator().next();
        Assert.assertEquals(toegangLeveringsautorisatieId, afnemerindicatieOpPersoon.getLeveringsautorisatie().getWaarde().getID().intValue());
        Assert.assertEquals(partijCode, afnemerindicatieOpPersoon.getAfnemer().getWaarde().getCode().getWaarde().intValue());
        Assert.assertEquals(0, resultaat.getMeldingen().size());

        final Dienst dienstInhoud =
            afnemerindicatieOpPersoon.getPersoonAfnemerindicatieHistorie().getHistorie().iterator().next()
                .getVerantwoordingInhoud();
        Assert.assertEquals(dienstId, dienstInhoud.getID().intValue());
        Assert.assertEquals(ADMINISTRATIEVE_HANDELING_ID, resultaat.getAdministratieveHandelingId());
    }

    private TransactionStatus geefTransactionStatus() {
        final DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("TEST_TRANSACTIE");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        return transactionManager.getTransaction(def);
    }

    @Test
    public final void testPlaatsAfnemerIndicatieMetRegelsPersoonOnbekend() {
        final int persoonId = 22222222;
        final int dienstId = 1;

        final BewerkAfnemerindicatieResultaat resultaat = afnemerindicatiesMetRegelsService
            .plaatsAfnemerindicatie(toegangLeveringsautorisatieId, persoonId,
                dienstId, null, null);

        Assert.assertEquals(1, resultaat.getMeldingen().size());
    }

    @Test
    public final void testPlaatsAfnemerIndicatieMetRegelsDienstOnbekend() {
        final int persoonId = 2;
        final int dienstId = 111111111;

        final BewerkAfnemerindicatieResultaat resultaat = afnemerindicatiesMetRegelsService.plaatsAfnemerindicatie(toegangLeveringsautorisatieId, persoonId,
            dienstId, null, null);

        Assert.assertEquals(1, resultaat.getMeldingen().size());

        final PersoonHisVolledig persoonHisVolledig = persoonHisVolledigRepository.leesGenormalizeerdModel(persoonId);
        Assert.assertEquals(0, persoonHisVolledig.getAfnemerindicaties().size());
    }

    @Test
    public final void testPlaatsAfnemerIndicatieMetRegelsMetFout() {
        final int persoonId = 510;
        final int dienstId = 2;

        final BewerkAfnemerindicatieResultaat resultaat = afnemerindicatiesMetRegelsService
            .plaatsAfnemerindicatie(toegangLeveringsautorisatieId, persoonId, dienstId,
                new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.morgen()), null);

        final PersoonHisVolledig persoonHisVolledig = persoonHisVolledigRepository.leesGenormalizeerdModel(persoonId);
        Assert.assertEquals(0, persoonHisVolledig.getAfnemerindicaties().size());
        // Meldingen voor bedrijfsregels BRLV0011 en BRLV0014
        //FIXME assert aangepast. In verdere autaut stappen moet dit weer getest worden
        Assert.assertEquals(1, resultaat.getMeldingen().size());
    }

    @Test
    public final void testVerwijderAfnemerIndicatieMetRegels() {
        final int persoonId = 503;
        final int dienstIdAaanmaken = 2;
        final int dienstIdVerwijderen = 3;

        //Eerst plaatsen van de indicatie
        TransactionStatus transactionStatus = geefTransactionStatus();
        afnemerindicatiesZonderRegelsServiceService.plaatsAfnemerindicatie(toegangLeveringsautorisatieId, persoonId, dienstIdAaanmaken, null, null, null);
        transactionManager.commit(transactionStatus);
        //Dan verwijderen
        transactionStatus = geefTransactionStatus();
        afnemerindicatiesMetRegelsService.verwijderAfnemerindicatie(toegangLeveringsautorisatieId, persoonId, dienstIdVerwijderen);
        transactionManager.commit(transactionStatus);

        final Set<PersoonAfnemerindicatieHisVolledigImpl> afnemerindicatieBlobs = afnemerIndicatieBlobifierService.leesBlob(persoonId);
        assertThat(afnemerindicatieBlobs.size(), is(1));
        assertThat(afnemerindicatieBlobs.iterator().next().getPersoonAfnemerindicatieHistorie().getActueleRecord(), is(nullValue()));

        final PersoonHisVolledig persoonHisVolledig = persoonHisVolledigRepository.leesGenormalizeerdModel(persoonId);
        Assert.assertEquals(1, persoonHisVolledig.getAfnemerindicaties().size());
        final PersoonAfnemerindicatieHisVolledig afnemerindicatieOpPersoon = persoonHisVolledig.getAfnemerindicaties().iterator().next();
        Assert.assertEquals(toegangLeveringsautorisatieId, afnemerindicatieOpPersoon.getLeveringsautorisatie().getWaarde().getID().intValue());
        Assert.assertEquals(partijCode, afnemerindicatieOpPersoon.getAfnemer().getWaarde().getCode().getWaarde().intValue());

        final HisPersoonAfnemerindicatieModel historieElement =
            afnemerindicatieOpPersoon.getPersoonAfnemerindicatieHistorie().getHistorie().iterator().next();

        Assert.assertEquals(dienstIdAaanmaken, historieElement.getVerantwoordingInhoud().getID().intValue());
        Assert.assertEquals(dienstIdVerwijderen, historieElement.getVerantwoordingVerval().getID().intValue());
    }

    @Test
    public final void testVerwijderAfnemerIndicatieMetRegelsMetFout() {
        final int persoonId = 504;
        final int dienstIdVerwijderen = 1;

        final BewerkAfnemerindicatieResultaat resultaat =
            afnemerindicatiesMetRegelsService.verwijderAfnemerindicatie(toegangLeveringsautorisatieId, persoonId, dienstIdVerwijderen);

        Assert.assertEquals(1, resultaat.getMeldingen().size());
    }

}

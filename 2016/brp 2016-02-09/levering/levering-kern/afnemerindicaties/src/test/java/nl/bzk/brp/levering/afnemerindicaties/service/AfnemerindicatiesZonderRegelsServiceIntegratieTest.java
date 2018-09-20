/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.service;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Set;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.brp.blobifier.service.AfnemerIndicatieBlobifierService;
import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie;
import nl.bzk.brp.dataaccess.repository.PersoonHisVolledigRepository;
import nl.bzk.brp.dataaccess.test.AbstractDBUnitIntegratieTest;
import nl.bzk.brp.dataaccess.test.Data;
import nl.bzk.brp.levering.afnemerindicaties.model.AfnemerindicatieNietGevondenExceptie;
import nl.bzk.brp.levering.afnemerindicaties.model.AfnemerindicatieReedsAanwezigExceptie;
import nl.bzk.brp.levering.business.toegang.leveringsautorisatie.cache.LeveringAutorisatieCache;
import nl.bzk.brp.locking.BrpLockerExceptie;
import nl.bzk.brp.model.FormeleHistorieSet;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.hisvolledig.autaut.PersoonAfnemerindicatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.impl.autaut.PersoonAfnemerindicatieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.autaut.HisPersoonAfnemerindicatieModel;
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
    "classpath:/data/pre-conv-clean.xml",
    "classpath:/data/schoon-persafnemer.xml" })
public class AfnemerindicatiesZonderRegelsServiceIntegratieTest extends AbstractDBUnitIntegratieTest {

    @Inject
    @Named("lezenSchrijvenTransactionManager")
    private PlatformTransactionManager transactionManager;

    @Inject
    private AfnemerindicatiesZonderRegelsService afnemerindicatiesZonderRegelsServiceService;

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

    @Test
    public final void testPlaatsAfnemerIndicatieZonderRegels() throws BrpLockerExceptie {
        final int toegangLeveringautorisatieId = 1;
        final int leveringautorisatieId = 1;
        final int partijCode = 34401;
        final int persoonId = 2;
        final int dienstId = 1;

        TransactionStatus transactionStatus = geefTransactionStatus();
        afnemerindicatiesZonderRegelsServiceService
            .plaatsAfnemerindicatie(toegangLeveringautorisatieId, persoonId, dienstId, null, null, DatumTijdAttribuut.nu());
        transactionManager.commit(transactionStatus);

        final Set<PersoonAfnemerindicatieHisVolledigImpl> afnemerindicatieBlobs = afnemerIndicatieBlobifierService.leesBlob(persoonId);
        assertThat(afnemerindicatieBlobs.size(), is(greaterThan(0)));

        final PersoonHisVolledig persoonHisVolledig = persoonHisVolledigRepository.leesGenormalizeerdModel(persoonId);
        assertEquals(1, persoonHisVolledig.getAfnemerindicaties().size());

        final PersoonAfnemerindicatieHisVolledig afnemerindicatieOpPersoon =
            persoonHisVolledig.getAfnemerindicaties().iterator().next();
        assertEquals(leveringautorisatieId, afnemerindicatieOpPersoon.getLeveringsautorisatie().getWaarde().getID().intValue());
        assertEquals(partijCode, afnemerindicatieOpPersoon.getAfnemer().getWaarde().getCode().getWaarde().intValue());

        final Dienst dienst =
            afnemerindicatieOpPersoon.getPersoonAfnemerindicatieHistorie().getHistorie().iterator().next()
                .getVerantwoordingInhoud();
        assertEquals(dienstId, dienst.getID().intValue());

        transactionStatus = geefTransactionStatus();
        afnemerindicatiesZonderRegelsServiceService.verwijderAfnemerindicatie(toegangLeveringautorisatieId, persoonId, dienstId);
        transactionManager.commit(transactionStatus);
    }

    private TransactionStatus geefTransactionStatus() {
        final DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("TEST_TRANSACTIE");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        return transactionManager.getTransaction(def);
    }

    @Test(expected = OnbekendeReferentieExceptie.class)
    public final void testPlaatsAfnemerIndicatieMetNietBestaandDienstId() throws BrpLockerExceptie {
        final int toegangLeveringautorisatieId = 1;
        final int persoonId = 3;
        final int dienstId = 199;

        afnemerindicatiesZonderRegelsServiceService.plaatsAfnemerindicatie(toegangLeveringautorisatieId, persoonId,
            dienstId, null, null, DatumTijdAttribuut.nu());
    }

    @Test(expected = OnbekendeReferentieExceptie.class)
    public final void testPlaatsAfnemerIndicatieMetNietBestaandPersoonId() throws BrpLockerExceptie {
        final int toegangLeveringautorisatieId = 1;
        final int persoonId = 22222222;
        final int dienstId = 1;

        final TransactionStatus transactionStatus = geefTransactionStatus();

        afnemerindicatiesZonderRegelsServiceService.plaatsAfnemerindicatie(toegangLeveringautorisatieId, persoonId,
            dienstId, null, null, DatumTijdAttribuut.nu());
        transactionManager.commit(transactionStatus);
    }

    @Test
    public final void testVerwijderAfnemerIndicatieZonderRegels() throws BrpLockerExceptie {
        final int toegangLeveringautorisatieId = 1;
        final int leveringautorisatieId = 1;
        final int partijCode = 34401;
        final int persoonId = 2;
        final int dienstIdAaanmaken = 1;
        final int dienstIdVerwijderen = 1;

        //Eerst plaatsen van de indicatie
        TransactionStatus transactionStatus = geefTransactionStatus();
        afnemerindicatiesZonderRegelsServiceService.plaatsAfnemerindicatie(toegangLeveringautorisatieId,
            persoonId, dienstIdAaanmaken, null, null, DatumTijdAttribuut.nu());
        transactionManager.commit(transactionStatus);

        //Dan verwijderen
        transactionStatus = geefTransactionStatus();
        afnemerindicatiesZonderRegelsServiceService.verwijderAfnemerindicatie(toegangLeveringautorisatieId, persoonId, dienstIdVerwijderen);
        transactionManager.commit(transactionStatus);

        final Set<PersoonAfnemerindicatieHisVolledigImpl> afnemerindicatieBlobs = afnemerIndicatieBlobifierService.leesBlob(persoonId);
        assertThat(afnemerindicatieBlobs.size(), is(1));
        assertThat(afnemerindicatieBlobs.iterator().next().getPersoonAfnemerindicatieHistorie().getActueleRecord(), is(nullValue()));

        final PersoonHisVolledig persoonHisVolledig = persoonHisVolledigRepository.leesGenormalizeerdModel(persoonId);
        assertEquals(1, persoonHisVolledig.getAfnemerindicaties().size());
        final PersoonAfnemerindicatieHisVolledig afnemerindicatieOpPersoon =
            persoonHisVolledig.getAfnemerindicaties().iterator().next();
        assertEquals(leveringautorisatieId, afnemerindicatieOpPersoon.getLeveringsautorisatie().getWaarde().getID().intValue());
        assertEquals(partijCode, afnemerindicatieOpPersoon.getAfnemer().getWaarde().getCode().getWaarde().intValue());

        final HisPersoonAfnemerindicatieModel historieElement =
            afnemerindicatieOpPersoon.getPersoonAfnemerindicatieHistorie().getHistorie().iterator().next();

        assertEquals(dienstIdAaanmaken, historieElement.getVerantwoordingInhoud().getID().intValue());
        assertEquals(dienstIdVerwijderen, historieElement.getVerantwoordingVerval().getID().intValue());
    }

    @Test(expected = AfnemerindicatieNietGevondenExceptie.class)
    public final void testVerwijderAfnemerIndicatieZonderRegelsIsNooitGeplaatst() throws BrpLockerExceptie {
        final int toegangLeveringautorisatieId = 1;
        final int persoonId = 2;
        final int dienstIdVerwijderen = 1;

        // Verwijderen terwijl er geen afnemerindicatie is
        afnemerindicatiesZonderRegelsServiceService.verwijderAfnemerindicatie(toegangLeveringautorisatieId, persoonId, dienstIdVerwijderen);
    }

    @Test(expected = AfnemerindicatieNietGevondenExceptie.class)
    public final void testVerwijderAfnemerIndicatieZonderRegelsIsAlVerwijderd() throws BrpLockerExceptie {
        final int toegangLeveringautorisatieId = 1;
        final int persoonId = 2;
        final int dienstIdAaanmaken = 1;
        final int dienstIdVerwijderen = 1;

        TransactionStatus transactionStatus = geefTransactionStatus();

        // Eerst plaatsen van de indicatie
        afnemerindicatiesZonderRegelsServiceService.plaatsAfnemerindicatie(toegangLeveringautorisatieId, persoonId,
            dienstIdAaanmaken, null, null, DatumTijdAttribuut.nu());

        transactionManager.commit(transactionStatus);

        // Dan verwijderen (tweemaal om foutmelding te triggeren)
        transactionStatus = geefTransactionStatus();
        afnemerindicatiesZonderRegelsServiceService.verwijderAfnemerindicatie(toegangLeveringautorisatieId, persoonId, dienstIdVerwijderen);
        transactionManager.commit(transactionStatus);
        afnemerindicatiesZonderRegelsServiceService.verwijderAfnemerindicatie(toegangLeveringautorisatieId, persoonId, dienstIdVerwijderen);
    }

    @Test(expected = AfnemerindicatieReedsAanwezigExceptie.class)
    public final void testPlaatsAfnemerIndicatieZonderRegelsTweemaal() throws BrpLockerExceptie {
        final int toegangLeveringautorisatieId = 1;
        final int persoonId = 2;
        final int dienstIdAaanmaken = 1;

        // Eerst plaatsen van de indicatie

        afnemerindicatiesZonderRegelsServiceService.plaatsAfnemerindicatie(toegangLeveringautorisatieId, persoonId,
            dienstIdAaanmaken, null, null, DatumTijdAttribuut.nu());

        // Nogmaals plaatsen van de indicatie moet een exceptie geven
        afnemerindicatiesZonderRegelsServiceService.plaatsAfnemerindicatie(toegangLeveringautorisatieId, persoonId,
            dienstIdAaanmaken, null, null, DatumTijdAttribuut.nu());
    }

    @Test
    public final void testHerPlaatsAfnemerIndicatieZonderRegelsNaVerwijdering() throws BrpLockerExceptie {
        final int toegangLeveringautorisatieId = 1;
        final int persoonId = 2;
        final int dienstIdAaanmaken = 1;

        TransactionStatus transactionStatus = geefTransactionStatus();
        // Eerst plaatsen van de indicatie
        afnemerindicatiesZonderRegelsServiceService.plaatsAfnemerindicatie(toegangLeveringautorisatieId, persoonId,
            dienstIdAaanmaken, null, null, DatumTijdAttribuut.nu());

        // Verwijder indicatie

        afnemerindicatiesZonderRegelsServiceService.verwijderAfnemerindicatie(toegangLeveringautorisatieId, persoonId, dienstIdAaanmaken);

        // Nogmaals plaatsen van de indicatie moet geen exceptie geven

        afnemerindicatiesZonderRegelsServiceService.plaatsAfnemerindicatie(toegangLeveringautorisatieId, persoonId,
            dienstIdAaanmaken, null, null, DatumTijdAttribuut.nu());

        transactionManager.commit(transactionStatus);

        final PersoonHisVolledigImpl persoonhisVolledigErna =
            (PersoonHisVolledigImpl) persoonHisVolledigRepository.leesGenormalizeerdModel(persoonId);
        assertEquals(1, persoonhisVolledigErna.getAfnemerindicaties().size());

        // Verifieer verder dat hierbinnen één vervallen en één actueel afnemerindicatie bestaat
        final PersoonAfnemerindicatieHisVolledigImpl afnemerindicatie = persoonhisVolledigErna.getAfnemerindicaties().iterator().next();
        final FormeleHistorieSet<HisPersoonAfnemerindicatieModel> persoonAfnemerindicatieHistorie = afnemerindicatie.getPersoonAfnemerindicatieHistorie();

        assertThat(persoonAfnemerindicatieHistorie.getActueleRecord(), is(not(nullValue())));
        assertThat(persoonAfnemerindicatieHistorie.getAantal(), is(greaterThan(1)));

        transactionStatus = geefTransactionStatus();
        afnemerindicatiesZonderRegelsServiceService.verwijderAfnemerindicatie(toegangLeveringautorisatieId, persoonId, dienstIdAaanmaken);
        transactionManager.commit(transactionStatus);
    }


}

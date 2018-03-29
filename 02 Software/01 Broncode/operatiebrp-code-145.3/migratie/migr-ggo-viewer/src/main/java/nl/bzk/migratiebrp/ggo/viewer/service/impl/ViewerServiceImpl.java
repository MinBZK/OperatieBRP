/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.service.impl;

import java.sql.Timestamp;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Bericht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Lo3BerichtenBron;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigePersoonslijstException;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.regels.proces.ConverteerBrpNaarLo3Service;
import nl.bzk.migratiebrp.conversie.regels.proces.ConverteerLo3NaarBrpService;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.PreconditiesService;
import nl.bzk.migratiebrp.ggo.viewer.log.FoutMelder;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoStap;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoVoorkomenVergelijking;
import nl.bzk.migratiebrp.ggo.viewer.service.ViewerService;
import nl.bzk.migratiebrp.ggo.viewer.vergelijk.Lo3HerkomstBuilder;
import nl.bzk.migratiebrp.ggo.viewer.vergelijk.Lo3Vergelijker;
import nl.bzk.migratiebrp.synchronisatie.dal.service.SyncParameters;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.PersoonMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapperImpl;
import nl.gba.gbav.impl.util.configuration.ServiceLocatorSpringImpl;
import nl.gba.gbav.util.configuration.ServiceLocator;
import org.springframework.stereotype.Component;

/**
 * Roept de voor de Viewer relevante delen van de omliggende code aan.
 */
@Component
public class ViewerServiceImpl implements ViewerService {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String CONVERSIE_FOUT_CODE = "Conversie (GBA > BRP)";
    private static final String TERUGCONVERSIE_FOUT_CODE = "Terugconversie (BRP > GBA)";
    private static final String CONVERSIE_FOUT_OMSCHRIJVING = "niet geslaagd.";
    private static final String RUNTIME_EXCEPTION_OPGETREDEN = "RuntimeException opgetreden: ";

    static {
        // Nodig voor de GBAV dependencies.
        if (!ServiceLocator.isInitialized()) {
            final String id = System.getProperty("gbav.deployment.id", "gbavContext");
            final String context = System.getProperty("gbav.deployment.context", "classpath:gbavconfig/deploymentContext.xml");
            ServiceLocator.initialize(new ServiceLocatorSpringImpl(context, id));
        }
    }

    @Inject
    private PreconditiesService preconditieService;
    @Inject
    private ConverteerLo3NaarBrpService converteerLo3NaarBrpService;
    @Inject
    private ConverteerBrpNaarLo3Service converteerBrpNaarLo3Service;
    @Inject
    private DynamischeStamtabelRepository dynamischeStamtabelRepository;
    @Inject
    private SyncParameters syncParameters;

    /**
     * Verwerk precondities.
     * @param lo3Persoonslijst De Lo3 Persoonslijst waarop de precondities worden gecontroleerd.
     * @param foutMelder Het object om verwerkingsfouten aan te melden.
     * @return de (opgeschonde) lo3Persoonslijst
     */
    @Override
    
    public final Lo3Persoonslijst verwerkPrecondities(final Lo3Persoonslijst lo3Persoonslijst, final FoutMelder foutMelder) {
        foutMelder.setHuidigeStap(GgoStap.PRECONDITIES);

        Lo3Persoonslijst lo3PersoonslijstOpgeschoond = null;
        try {
            lo3PersoonslijstOpgeschoond = preconditieService.verwerk(lo3Persoonslijst);
        } catch (final RuntimeException e /* Alle fouten afvangen en een nette melding op het scherm. */) {
            foutMelder.log(LogSeverity.ERROR, "Fout bij verwerken precondities", e);
        } catch (final OngeldigePersoonslijstException e) {
            // Persoonslijst voldoet niet aan noodzakelijke precondities
            lo3PersoonslijstOpgeschoond = null;
        }
        return lo3PersoonslijstOpgeschoond;
    }

    /**
     * Converteer naar BRP.
     * @param lo3Persoonslijst De Lo3 Persoonslijst die geconverteerd moet worden.
     * @param foutMelder Het object om verwerkingsfouten aan te melden.
     * @return De bijbehorende BRP Persoonslijst
     */
    @Override
    
    public final BrpPersoonslijst converteerNaarBrp(final Lo3Persoonslijst lo3Persoonslijst, final FoutMelder foutMelder) {
        foutMelder.setHuidigeStap(GgoStap.BRP);

        try {
            BrpPersoonslijst brpPersoonslijst = converteerLo3NaarBrpService.converteerLo3Persoonslijst(lo3Persoonslijst);

            // Sorteren van de BrpPersoonslijst op basis van geldigheid. Anders komt het slecht overeen met
            // de LO3 weergave
            if (brpPersoonslijst == null) {
                foutMelder.setHuidigeStap(GgoStap.BRP);
                foutMelder.log(LogSeverity.ERROR, "Fout bij sorteren BRP Persoonslijst", "BRP Persoonslijst mag niet leeg zijn.");
                return null;
            }
            brpPersoonslijst.sorteer();

            return brpPersoonslijst;

        } catch (final RuntimeException e /* Alle fouten afvangen en een nette melding op het scherm. */) {
            LOG.error(RUNTIME_EXCEPTION_OPGETREDEN, e);
            foutMelder.log(LogSeverity.ERROR, CONVERSIE_FOUT_CODE, CONVERSIE_FOUT_OMSCHRIJVING);
            foutMelder.log(LogSeverity.ERROR, "", e.getMessage());
        }
        return null;
    }

    @Override
    public final Persoon converteerNaarEntityModel(final BrpPersoonslijst brpPersoonslijst) {
        // Map persoon
        final Persoon nieuwePersoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final BrpPartijCode bijhoudingspartijCode = brpPersoonslijst.getBijhoudingStapel().getActueel().getInhoud().getBijhoudingspartijCode();
        final Partij partij = dynamischeStamtabelRepository.getPartijByCode(bijhoudingspartijCode.getWaarde());
        final OnderzoekMapper onderzoekMapper = new OnderzoekMapperImpl(nieuwePersoon, partij);

        final Lo3Bericht lo3Bericht =
                new Lo3Bericht("GGO Viewer", Lo3BerichtenBron.INITIELE_VULLING, new Timestamp(System.currentTimeMillis()), "BOGUS_DATA", true);

        new PersoonMapper(dynamischeStamtabelRepository, syncParameters, onderzoekMapper).mapVanMigratie(brpPersoonslijst, nieuwePersoon, lo3Bericht);

        return nieuwePersoon;
    }

    /**
     * Converteer een naar BRP geconverteerde persoonslijst terug naar Lo3.
     * @param brpPersoonslijst De BRP Persoonslijst die geconverteerd moet worden.
     * @param foutMelder Het object om verwerkingsfouten aan te melden.
     * @return De bijbehorende terugconversie in de vorm van een Lo3 Persoonslijst.
     */
    @Override
    
    public final Lo3Persoonslijst converteerTerug(final BrpPersoonslijst brpPersoonslijst, final FoutMelder foutMelder) {
        foutMelder.setHuidigeStap(GgoStap.TERUGCONVERSIE);

        try {
            return converteerBrpNaarLo3Service.converteerBrpPersoonslijst(brpPersoonslijst);

        } catch (final RuntimeException e /* Alle fouten afvangen en een nette melding op het scherm. */) {
            LOG.error(RUNTIME_EXCEPTION_OPGETREDEN, e);
            foutMelder.log(LogSeverity.ERROR, TERUGCONVERSIE_FOUT_CODE, CONVERSIE_FOUT_OMSCHRIJVING);
            foutMelder.log(LogSeverity.ERROR, "", e.getMessage());
            return null;
        }
    }

    /**
     * Vergelijk Lo3 origineel met de teruggeconverteerde variant.
     * @param origineel Het origineel
     * @param teruggeconverteerd De teruggeconverteerde variant
     * @param foutMelder Het object om verwerkingsfouten aan te melden.
     * @return Een lijst StapelVergelijking'en, overigens inclusief 'IDENTICAL' regels.
     */
    @Override
    
    public final List<GgoVoorkomenVergelijking> vergelijkLo3OrigineelMetTerugconversie(
            final Lo3Persoonslijst origineel,
            final Lo3Persoonslijst teruggeconverteerd,
            final FoutMelder foutMelder) {
        foutMelder.setHuidigeStap(GgoStap.VERGELIJKING);

        try {
            return new Lo3Vergelijker().vergelijk(origineel, teruggeconverteerd);

        } catch (final RuntimeException e /* Alle fouten afvangen en een nette melding op het scherm. */) {
            foutMelder.log(LogSeverity.ERROR, "Fout bij het vergelijken", e);
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    
    public final Lo3Persoonslijst voegLo3HerkomstToe(
            final Lo3Persoonslijst origineel,
            final Lo3Persoonslijst teruggeconverteerd,
            final FoutMelder foutMelder) {
        foutMelder.setHuidigeStap(GgoStap.VERGELIJKING);

        try {
            return new Lo3HerkomstBuilder().kopieerTerugconversiePlMetHerkomst(origineel, teruggeconverteerd);
        } catch (final RuntimeException e /* Alle fouten afvangen en een nette specifieke melding voor op het scherm. */) {
            foutMelder.log(LogSeverity.ERROR, "Fout bij het bepalen van de lo3Herkomst van de teruggeconverteerde Persoonslijst", e);
            return null;
        }
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.migratiebrp.conversie.model.Requirement;
import nl.bzk.migratiebrp.conversie.model.Requirements;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAfnemersindicatie;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAfnemersindicaties;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAutorisatie;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3.Foutmelding;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.blokkering.entity.Blokkering;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.Leveringsautorisatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.ToegangLeveringsAutorisatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Gemeente;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.HistorieUtil;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Bericht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.NadereBijhoudingsaard;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Onderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonAfgeleidAdministratiefHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortAdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.AfnemersindicatieRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.BlokkeringRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.LeveringsautorisatieRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.Lo3BerichtRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.PersoonRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.StamtabelRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.PersoonslijstPersisteerResultaat;
import nl.bzk.migratiebrp.synchronisatie.dal.service.SyncParameters;
import nl.bzk.migratiebrp.synchronisatie.dal.service.autorisatie.AfnemerIndicatieService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.autorisatie.AutorisatieService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.autorisatie.impl.AfnemerIndicatieServiceImpl;
import nl.bzk.migratiebrp.synchronisatie.dal.service.autorisatie.impl.AutorisatieServiceImpl;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaBepalingContext;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.proces.DeltaProces;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.proces.SamengesteldDeltaProces;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.logging.LoggingService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.logging.LoggingServiceImpl;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.BrpPersoonslijstMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.PersoonMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapperImpl;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapperImpl;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementatie voor de BrpDalService.
 */
@Service
public final class BrpDalServiceImpl implements BrpDalService {

    private static final String SYNC_DAL_TRANSACTION_MANAGER = "syncDalTransactionManager";
    private static final String MEER_DAN_1_PERSOON_GEVONDEN_VOOR_A_NUMMER_S = "Meer dan 1 persoon gevonden voor a-nummer '%s'.";

    private final PersoonRepository persoonRepository;
    private final StamtabelRepository stamtabelRepository;
    private final BlokkeringRepository blokkeringRepository;
    private final DynamischeStamtabelRepository dynamischeStamtabelRepository;

    private final AutorisatieService autorisatieService;
    private final AfnemerIndicatieService afnemerIndicatieService;
    private final LoggingService loggingService;

    private final SyncParameters syncParameters;
    private final BrpPersoonslijstMapper brpPersoonslijstMapper;

    /**
     * Default constructor.
     *
     * @param persoonRepository
     *            De persoonrepository.
     * @param dynamischeStamtabelRepository
     *            de repository voor het bevragen van de stamtabellen.
     * @param berichtLogRepository
     *            de repository voor het bevragen en opslaan van BerichtLog objecten
     * @param brpPersoonslijstMapper
     *            de mapper voor het mappen van BRP-entities op het migratie model
     * @param blokkeringRepository
     *            de repository voor het bevragen en opslaan van blokkering.
     * @param stamtabelRepository
     *            de repository met stam gegevens.
     * @param afnemersindicatieRepository
     *            de repository voor het opslaan en bevragen van afnemersindicaties
     * @param leveringsautorisatieRepository
     *            de repository voor het bevragen van leveringautorisaties
     * @param syncParameters
     *            de synchronisatie parameters.
     */
    @Inject
    /*
     * Het is nodig om alle repositories in een keer te injecten, daardoor overschrijdt de constructor het maximale
     * aantal parameters.
     */
    BrpDalServiceImpl(
        final PersoonRepository persoonRepository,
        final DynamischeStamtabelRepository dynamischeStamtabelRepository,
        final Lo3BerichtRepository berichtLogRepository,
        final BrpPersoonslijstMapper brpPersoonslijstMapper,
        final BlokkeringRepository blokkeringRepository,
        final StamtabelRepository stamtabelRepository,
        final AfnemersindicatieRepository afnemersindicatieRepository,
        final LeveringsautorisatieRepository leveringsautorisatieRepository,
        @Named("syncParameters") final SyncParameters syncParameters)
    {
        this.persoonRepository = persoonRepository;
        this.dynamischeStamtabelRepository = dynamischeStamtabelRepository;
        this.brpPersoonslijstMapper = brpPersoonslijstMapper;
        this.blokkeringRepository = blokkeringRepository;
        this.stamtabelRepository = stamtabelRepository;
        this.syncParameters = syncParameters;

        autorisatieService = new AutorisatieServiceImpl(dynamischeStamtabelRepository);
        afnemerIndicatieService =
                new AfnemerIndicatieServiceImpl(dynamischeStamtabelRepository, afnemersindicatieRepository, leveringsautorisatieRepository);
        loggingService = new LoggingServiceImpl(berichtLogRepository);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService#getSyncParameters()
     */
    @Override
    public SyncParameters getSyncParameters() {
        return syncParameters;
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED)
    @Override
    public PersoonslijstPersisteerResultaat persisteerPersoonslijst(final BrpPersoonslijst brpPersoonslijst, final Lo3Bericht lo3Bericht) {
        return converteerEnRelateerPersoonslijst(brpPersoonslijst, null, false, lo3Bericht);
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED)
    @Override
    public PersoonslijstPersisteerResultaat persisteerPersoonslijst(
        final BrpPersoonslijst brpPersoonslijst,
        final Long aNummerTeVervangenPersoon,
        final boolean isANummerWijziging,
        final Lo3Bericht lo3Bericht)
    {
        if (aNummerTeVervangenPersoon == null) {
            throw new NullPointerException("aNummerTeVervangenPersoon mag niet null zijn");
        }
        return converteerEnRelateerPersoonslijst(brpPersoonslijst, aNummerTeVervangenPersoon, isANummerWijziging, lo3Bericht);
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED)
    @Override
    public Lo3Bericht persisteerLo3Bericht(final Lo3Bericht bericht) {
        return loggingService.persisteerLo3Bericht(bericht);
    }

    @Requirement({Requirements.CRP001_LB02, Requirements.CRP001_LB03 })
    private PersoonslijstPersisteerResultaat converteerEnRelateerPersoonslijst(
        final BrpPersoonslijst brpPersoonslijst,
        final Long aNummerTeVervangenPersoon,
        final boolean isANummerWijziging,
        final Lo3Bericht lo3Bericht)
    {
        // Map BRP conversiemodel op database entiteiten
        final Persoon kluizenaar = mapPersoonEnOnderzoek(brpPersoonslijst, lo3Bericht);

        // Opvragen van bestaande persoon uit database.
        final Long anummerFilter = aNummerTeVervangenPersoon != null ? aNummerTeVervangenPersoon : brpPersoonslijst.getActueelAdministratienummer();
        final Persoon dbPersoon = zoekIngeschrevenPersoon(anummerFilter, false);

        if (dbPersoon != null) {
            final DeltaBepalingContext deltaBepalingContext = new DeltaBepalingContext(kluizenaar, dbPersoon, lo3Bericht, isANummerWijziging);
            return werkBestaandePersoonslijstBij(deltaBepalingContext);
        } else {
            return verwerkNieuwePersoonslijst(kluizenaar, lo3Bericht);
        }
    }

    private PersoonslijstPersisteerResultaat werkBestaandePersoonslijstBij(final DeltaBepalingContext context) {
        final Persoon bestaandePersoon = context.getBestaandePersoon();
        final Set<Long> oudeAfgeleidAdministratieveHistorieIds = verzamelAdministratieveHandelingenIds(bestaandePersoon);

        // Oude persoon cache 'blob' verwijderen uit db
        persoonRepository.removeCache(bestaandePersoon);

        verwijderDummyHistorie(context.getBestaandePersoon());

        final DeltaProces deltaProces = SamengesteldDeltaProces.newInstanceMetAlleProcessen();
        deltaProces.bepaalVerschillen(context);
        deltaProces.verwerkVerschillen(context);

        final PersoonAfgeleidAdministratiefHistorie historie =
                HistorieUtil.getActueelHistorieVoorkomen(bestaandePersoon.getPersoonAfgeleidAdministratiefHistorieSet());
        bestaandePersoon.setAdministratieveHandeling(historie.getAdministratieveHandeling());

        for (final AdministratieveHandeling administratieveHandeling : context.getAdministratieveHandelingen()) {
            final SoortAdministratieveHandeling soortAdministratieveHandeling = administratieveHandeling.getSoort();
            SynchronisatieLogging.addMelding(String.format("Deltabepaling: Uiteindelijke conclusie: %s", soortAdministratieveHandeling.getNaam()));
            SynchronisatieLogging.addDeltaResultaat(soortAdministratieveHandeling);
        }

        persoonRepository.save(bestaandePersoon);

        final Set<AdministratieveHandeling> nieuweAdministratieveHandelingen =
                bepaalNieuweAdministratieveHandelingen(
                    oudeAfgeleidAdministratieveHistorieIds,
                    bestaandePersoon.getPersoonAfgeleidAdministratiefHistorieSet());

        return new PersoonslijstPersisteerResultaat(bestaandePersoon, nieuweAdministratieveHandelingen);
    }

    /**
     * Als de bestaande persoon in de BRP afkomstig is van een door conversie gegenereerde Dummy PL, dan moet de
     * historie van de groepen Bijhouding, ID, en Inschrijving verwijdert worden. Als dat niet wordt gedaan dan worden
     * acties van de Dummy PL opnieuw gebruikt, en deze komen niet overeen met de acties van de conversie van de nieuwe
     * PL. Dit zou tijdens terugconversie tot extra rijen leiden doordat verschillende acties die inhoudelijk identiek
     * zijn, toch tot meerdere rijen worden geconverteerd.
     *
     * Voor het checken of een Persoon van de Dummy PL afkomstig is wordt gedaan door te kijken of de persoon
     * opshort-reden FOUT heeft, en een lege geslachtsnaamstam.
     *
     * Zie ook Jira issue ORANJE-2864.
     *
     * @param bestaandePersoon
     *            de persoon waarvan als het een dummy is, de historie moet worden opgeschoond
     */
    private void verwijderDummyHistorie(final Persoon bestaandePersoon) {
        // Dummy PL resulteert in een niet-gevulde geslachtsnaamstam
        if (bestaandePersoon != null
            && bestaandePersoon.getGeslachtsnaamstam() == null
            && NadereBijhoudingsaard.FOUT.equals(bestaandePersoon.getNadereBijhoudingsaard()))
        {
            bestaandePersoon.getPersoonBijhoudingHistorieSet().clear();
            bestaandePersoon.getPersoonIDHistorieSet().clear();
            bestaandePersoon.getPersoonInschrijvingHistorieSet().clear();
        }
    }

    private Set<Long> verzamelAdministratieveHandelingenIds(final Persoon persoon) {
        final Set<Long> administratieveHandelingenIds = new HashSet<>();
        final Set<PersoonAfgeleidAdministratiefHistorie> afgeleidAdministratiefHistorieSet = persoon.getPersoonAfgeleidAdministratiefHistorieSet();
        for (final PersoonAfgeleidAdministratiefHistorie historie : afgeleidAdministratiefHistorieSet) {
            final Long administratieveHandelingId = historie.getAdministratieveHandeling().getId();
            if (administratieveHandelingId != null) {
                administratieveHandelingenIds.add(administratieveHandelingId);
            }
        }
        return administratieveHandelingenIds;
    }

    private Set<AdministratieveHandeling> bepaalNieuweAdministratieveHandelingen(
        final Set<Long> oudeAfgeleidAdministratieveHistorieIds,
        final Set<PersoonAfgeleidAdministratiefHistorie> nieuweAfgeleidAdministratieveHistorieSet)
    {
        final Set<AdministratieveHandeling> nieuweAdministratieveHandelingSet = new HashSet<>();

        for (final PersoonAfgeleidAdministratiefHistorie historie : nieuweAfgeleidAdministratieveHistorieSet) {
            final AdministratieveHandeling nieuweAdministratieveHandeling = historie.getAdministratieveHandeling();
            if (!oudeAfgeleidAdministratieveHistorieIds.contains(nieuweAdministratieveHandeling.getId())) {
                nieuweAdministratieveHandelingSet.add(nieuweAdministratieveHandeling);
            }
        }

        return nieuweAdministratieveHandelingSet;
    }

    private PersoonslijstPersisteerResultaat verwerkNieuwePersoonslijst(final Persoon persoon, final Lo3Bericht lo3Bericht) {
        lo3Bericht.setPersoon(persoon);
        persoon.addLo3Bericht(lo3Bericht);

        final Set<AdministratieveHandeling> nieuweAdministratieveHandelingen = new HashSet<>();
        nieuweAdministratieveHandelingen.add(persoon.getAdministratieveHandeling());

        persoonRepository.save(persoon);
        return new PersoonslijstPersisteerResultaat(persoon, nieuweAdministratieveHandelingen);
    }

    private Persoon mapPersoonEnOnderzoek(final BrpPersoonslijst brpPersoonslijst, final Lo3Bericht lo3Bericht) {
        // Map persoon
        final Persoon kluizenaar = new Persoon(SoortPersoon.INGESCHREVENE);
        final OnderzoekMapper onderzoekMapper = new OnderzoekMapperImpl(kluizenaar);

        new PersoonMapper(dynamischeStamtabelRepository, syncParameters, onderzoekMapper).mapVanMigratie(brpPersoonslijst, kluizenaar, lo3Bericht);

        return kluizenaar;
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public BrpPersoonslijst bevraagPersoonslijst(final long administratienummer) {
        final Persoon result = zoekIngeschrevenPersoon(administratienummer, false);
        if (result == null) {
            throw new IllegalStateException(String.format("Geen persoon gevonden voor a-nummer '%s'.", administratienummer));
        }
        return converteerNaarBrpPersoonslijst(result);
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public BrpPersoonslijst bevraagPersoonslijstOpTechnischeSleutel(final Long technischeSleutel) {
        final Persoon result = zoekIngeschrevenPersoonOpTechnischeSleutel(technischeSleutel);
        if (result == null) {
            throw new IllegalStateException(String.format("Geen persoon gevonden voor technische sleutel '%s'.", technischeSleutel));
        }
        return converteerNaarBrpPersoonslijst(result);
    }

    private BrpPersoonslijst converteerNaarBrpPersoonslijst(final Persoon result) {
        final List<Onderzoek> onderzoeken = persoonRepository.findOnderzoekenVoorPersoon(result);
        final BrpOnderzoekMapper brpOnderzoekMapper = new BrpOnderzoekMapperImpl(onderzoeken);
        return brpPersoonslijstMapper.mapNaarMigratie(result, brpOnderzoekMapper);
    }

    private Persoon zoekIngeschrevenPersoon(final Long administratienummer, final boolean indicatieFoutiefOpgeschortUitsluiten) {
        final List<Persoon> personen =
                persoonRepository.findByAdministratienummer(administratienummer, SoortPersoon.INGESCHREVENE, indicatieFoutiefOpgeschortUitsluiten);

        final Persoon result;
        if (personen == null || personen.isEmpty()) {
            result = null;
        } else if (personen.size() > 1) {
            throw new IllegalStateException(String.format(MEER_DAN_1_PERSOON_GEVONDEN_VOOR_A_NUMMER_S, administratienummer));
        } else {
            result = personen.get(0);
        }
        return result;
    }

    private Persoon zoekIngeschrevenPersoonOpTechnischeSleutel(final Long technischeSleutel) {
        final List<Persoon> personen = persoonRepository.findByTechnischeSleutel(technischeSleutel, SoortPersoon.INGESCHREVENE);

        final Persoon result;
        if (personen == null || personen.isEmpty()) {
            result = null;
        } else if (personen.size() > 1) {
            throw new IllegalStateException(String.format("Meer dan 1 persoon gevonden voor technische sleutel '%s'.", technischeSleutel));
        } else {
            result = personen.get(0);
        }
        return result;
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public BrpPersoonslijst zoekPersoonOpAnummer(final long administratienummer) {
        final Persoon result = zoekIngeschrevenPersoon(administratienummer, false);
        if (result == null) {
            return null;
        } else {
            return converteerNaarBrpPersoonslijst(result);
        }
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public BrpPersoonslijst zoekPersoonOpAnummerFoutiefOpgeschortUitsluiten(final long administratienummer) {
        final Persoon result = zoekIngeschrevenPersoon(administratienummer, true);
        if (result == null) {
            return null;
        } else {
            return converteerNaarBrpPersoonslijst(result);
        }
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public List<Persoon> zoekPersonenOpAnummerFoutiefOpgeschortUitsluiten(final long administratienummer) {
        return persoonRepository.findByAdministratienummer(administratienummer, SoortPersoon.INGESCHREVENE, true);
    }

    @Transactional(value = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public Lo3Bericht zoekLo3PeroonslijstBerichtOpAnummer(final long administratienummer) {
        return loggingService.zoekLo3PersoonslijstBerichtOpAnummer(administratienummer);
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public Set<Long> zoekBerichtLogAnrs(final Date vanaf, final Date tot) {
        return loggingService.zoekBerichtLogAnrs(vanaf, tot);
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public BrpPersoonslijst zoekPersoonOpHistorischAnummer(final long administratienummer) {
        final Persoon result = zoekIngeschrevenPersoonHistorisch(administratienummer);
        if (result == null) {
            return null;
        } else {
            return converteerNaarBrpPersoonslijst(result);
        }
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public List<BrpPersoonslijst> zoekPersonenOpHistorischAnummer(final long administratienummer) {
        final List<Persoon> personen = persoonRepository.findByAdministratienummerHistorisch(administratienummer, SoortPersoon.INGESCHREVENE);

        final List<BrpPersoonslijst> result = new ArrayList<>();
        for (final Persoon persoon : personen) {
            final List<Onderzoek> onderzoeken = persoonRepository.findOnderzoekenVoorPersoon(persoon);
            final BrpOnderzoekMapper brpOnderzoekMapper = new BrpOnderzoekMapperImpl(onderzoeken);
            result.add(brpPersoonslijstMapper.mapNaarMigratie(persoon, brpOnderzoekMapper));
        }
        return result;
    }

    private Persoon zoekIngeschrevenPersoonHistorisch(final long administratienummer) {
        final List<Persoon> personen = persoonRepository.findByAdministratienummerHistorisch(administratienummer, SoortPersoon.INGESCHREVENE);

        final Persoon result;
        if (personen == null || personen.isEmpty()) {
            result = null;
        } else if (personen.size() > 1) {
            throw new IllegalStateException(String.format(MEER_DAN_1_PERSOON_GEVONDEN_VOOR_A_NUMMER_S, administratienummer));
        } else {
            result = personen.get(0);
        }
        return result;
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED)
    @Override
    public Blokkering persisteerBlokkering(final Blokkering blokkering) {

        if (blokkeringRepository.statusBlokkering(blokkering.getaNummer()) != null) {
            throw new IllegalStateException("De persoonlijst met het opgegeven aNummer is al geblokkeerd.");
        }

        return blokkeringRepository.blokkeerPersoonslijst(blokkering);
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public Blokkering vraagOpBlokkering(final Long aNummer) {

        if (aNummer == null) {
            throw new IllegalStateException("Er is geen aNummer opgegeven.");
        }

        return blokkeringRepository.statusBlokkering(aNummer);
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED)
    @Override
    public void verwijderBlokkering(final Blokkering teVerwijderenBlokkering) {

        final Blokkering controleBlokkering = blokkeringRepository.statusBlokkering(teVerwijderenBlokkering.getaNummer());

        if (controleBlokkering == null) {
            throw new IllegalStateException("De persoonlijst met het opgegeven aNummer is niet geblokkeerd.");
        }

        if (!teVerwijderenBlokkering.getProcessId().equals(controleBlokkering.getProcessId())) {
            throw new IllegalStateException("Het process ID komt niet overeen met het process ID waarmee de persoonslijst is geblokkeerd.");
        }

        if (!teVerwijderenBlokkering.getRegistratieGemeente().equals(controleBlokkering.getRegistratieGemeente())) {
            throw new IllegalStateException(
                "De registratiegemeente komt niet overeen met de registratiegemeent die de persoonslijst " + "heeft geblokkeerd.");
        }

        blokkeringRepository.deblokkeerPersoonslijst(teVerwijderenBlokkering);
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public List<Persoon> zoekPersoon(final Persoon persoon) {
        if (persoon != null) {
            return persoonRepository.zoekPersoon(persoon);
        } else {
            return null;
        }
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public Collection<Gemeente> geefAlleGemeenten() {
        return stamtabelRepository.findAllGemeentes();
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public Collection<Leveringsautorisatie> geefAlleGbaAutorisaties() {
        return autorisatieService.geefAlleGbaAutorisaties();
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED)
    @Override
    public List<ToegangLeveringsAutorisatie> persisteerAutorisatie(final BrpAutorisatie brpAutorisatie) {
        return autorisatieService.persisteerAutorisatie(brpAutorisatie);
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public BrpAutorisatie bevraagAutorisatie(final Integer partijCode, final String naam, final Integer ingangsDatumRegel) {
        return autorisatieService.bevraagAutorisatie(partijCode, naam, ingangsDatumRegel);
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, noRollbackFor = {IllegalStateException.class })
    @Override
    public void persisteerAfnemersindicaties(final BrpAfnemersindicaties brpAfnemersindicaties) {
        final Persoon persoon = zoekIngeschrevenPersoon(brpAfnemersindicaties.getAdministratienummer(), false);
        if (persoon == null) {
            for (final BrpAfnemersindicatie brpAfnemersindicatie : brpAfnemersindicaties.getAfnemersindicaties()) {
                final int stapel = brpAfnemersindicatie.getAfnemersindicatieStapel().get(0).getActieInhoud().getLo3Herkomst().getStapel();
                Foutmelding.logMeldingFout(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_14, stapel, -1), LogSeverity.ERROR, SoortMeldingCode.AFN009, null);
            }
        } else {
            afnemerIndicatieService.persisteerAfnemersindicaties(brpAfnemersindicaties.getAfnemersindicaties(), persoon);
        }
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public BrpAfnemersindicaties bevraagAfnemersindicaties(final Long administratienummer) {
        final Persoon persoon = zoekIngeschrevenPersoon(administratienummer, false);
        if (persoon == null) {
            throw new IllegalStateException(
                String.format("Geen persoon gevonden voor a-nummer '%s' (bij bevragen afnemersindicaties).", administratienummer));
        }

        return afnemerIndicatieService.bevraagAfnemersindicaties(persoon);
    }

    @Override
    public List<Persoon> zoekPersonenOpActueleGegevens(
        final Long administratienummer,
        final Integer burgerservicenummer,
        final String geslachtsnaamstam,
        final String postcode)
    {
        return persoonRepository.zoekPersonenOpActueleGegevens(administratienummer, burgerservicenummer, geslachtsnaamstam, postcode);
    }

    @Override
    public List<Persoon> zoekPersonenOpHistorischeGegevens(
        final Long administratienummer,
        final Integer burgerservicenummer,
        final String geslachtsnaamstam)
    {
        return persoonRepository.zoekPersonenOpHistorischeGegevens(administratienummer, burgerservicenummer, geslachtsnaamstam);
    }
}

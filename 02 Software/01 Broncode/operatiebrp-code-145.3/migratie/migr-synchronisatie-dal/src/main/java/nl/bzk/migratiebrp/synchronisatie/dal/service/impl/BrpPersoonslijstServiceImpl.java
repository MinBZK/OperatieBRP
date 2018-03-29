/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Bericht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfgeleidAdministratiefHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpPersoonslijstService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.PersoonService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.PersoonslijstPersisteerResultaat;
import nl.bzk.migratiebrp.synchronisatie.dal.service.SyncParameters;
import nl.bzk.migratiebrp.synchronisatie.dal.service.TeLeverenAdministratieveHandelingenAanwezigException;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaBepalingContext;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.proces.DeltaProces;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.proces.SamengesteldDeltaProces;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.PersoonMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapperImpl;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementatie van BrpPersoonslijstDalService.
 */
@Service
public final class BrpPersoonslijstServiceImpl implements BrpPersoonslijstService {

    private static final String SYNC_DAL_TRANSACTION_MANAGER = "syncDalTransactionManager";

    private final DynamischeStamtabelRepository dynamischeStamtabelRepository;
    private final PersoonService persoonDalService;
    private final SyncParameters syncParameters;

    /**
     * @param dynamischeStamtabelRepository de repository voor het bevragen van de stamtabellen.
     * @param persoonDalService de service dal voor personen.
     * @param syncParameters de synchronisatie parameters.
     */
    @Inject
    public BrpPersoonslijstServiceImpl(final DynamischeStamtabelRepository dynamischeStamtabelRepository, final PersoonService persoonDalService,
                                       @Named("syncParameters") final SyncParameters syncParameters) {
        this.dynamischeStamtabelRepository = dynamischeStamtabelRepository;
        this.persoonDalService = persoonDalService;
        this.syncParameters = syncParameters;
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
        // Map BRP conversiemodel op database entiteiten
        final Persoon kluizenaar = mapPersoonEnOnderzoek(brpPersoonslijst, lo3Bericht);

        // Toevoegen persoon in de database.
        return verwerkNieuwePersoonslijst(kluizenaar, lo3Bericht);
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED)
    @Override
    public PersoonslijstPersisteerResultaat persisteerPersoonslijst(final BrpPersoonslijst brpPersoonslijst, final Long teVervangenPersoonslijstId,
                                                                    final Lo3Bericht lo3Bericht) throws TeLeverenAdministratieveHandelingenAanwezigException {
        if (teVervangenPersoonslijstId == null) {
            throw new NullPointerException("teVervangenPersoonslijstId mag niet null zijn");
        }

        // Map BRP conversiemodel op database entiteiten
        final Persoon kluizenaar = mapPersoonEnOnderzoek(brpPersoonslijst, lo3Bericht);

        // Opvragen van bestaande persoon uit database.
        final Persoon dbPersoon = persoonDalService.zoekIngeschrevenPersoonOpTechnischeSleutel(teVervangenPersoonslijstId, false);

        // DB persoon is opgevraagd, nu kunnen we veilig de controle doen op te leveren
        // administratieve handelingen.
        // Anders kan je een race conditie krijgen (t1(controle), t2(controle, lees persoon,
        // bijwerken, opslaan), t1 (lees persoon, etc))
        if (!controleerGeenTeLeverenAdministratieveHandelingen(dbPersoon)) {
            throw new TeLeverenAdministratieveHandelingenAanwezigException(teVervangenPersoonslijstId);
        }

        final DeltaBepalingContext deltaBepalingContext = new DeltaBepalingContext(kluizenaar, dbPersoon, lo3Bericht, false);
        return werkBestaandePersoonslijstBij(deltaBepalingContext);
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public BrpPersoonslijst bevraagPersoonslijst(final String administratienummer) {
        final Persoon result = persoonDalService.zoekIngeschrevenPersoon(administratienummer, false);
        if (result == null) {
            throw new IllegalStateException(String.format("Geen persoon gevonden voor a-nummer '%s'.", administratienummer));
        }
        return persoonDalService.converteerNaarBrpPersoonslijst(result);
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public BrpPersoonslijst bevraagPersoonslijstOpTechnischeSleutel(final Long technischeSleutel) {
        final Persoon result = persoonDalService.zoekIngeschrevenPersoonOpTechnischeSleutel(technischeSleutel, false);
        if (result == null) {
            throw new IllegalStateException(String.format("Geen persoon gevonden voor technische sleutel '%s'.", technischeSleutel));
        }
        return persoonDalService.converteerNaarBrpPersoonslijst(result);
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public BrpPersoonslijst bevraagPersoonslijstOpTechnischeSleutelFoutiefOpgeschortUitsluiten(final Long technischeSleutel) {
        final Persoon result = persoonDalService.zoekIngeschrevenPersoonOpTechnischeSleutel(technischeSleutel, true);
        if (result == null) {
            throw new IllegalStateException(String.format("Geen of foutief opgeschorte persoon gevonden voor technische sleutel '%s'.", technischeSleutel));
        }
        return persoonDalService.converteerNaarBrpPersoonslijst(result);
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public BrpPersoonslijst zoekPersoonOpAnummer(final String administratienummer) {
        final Persoon result = persoonDalService.zoekIngeschrevenPersoon(administratienummer, false);
        if (result == null) {
            return null;
        } else {
            return persoonDalService.converteerNaarBrpPersoonslijst(result);
        }
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public BrpPersoonslijst zoekPersoonOpAnummerFoutiefOpgeschortUitsluiten(final String administratienummer) {
        final Persoon result = persoonDalService.zoekIngeschrevenPersoon(administratienummer, true);
        if (result == null) {
            return null;
        } else {
            return persoonDalService.converteerNaarBrpPersoonslijst(result);
        }
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public List<BrpPersoonslijst> zoekPersonenOpHistorischAnummerFoutiefOpgeschortUitsluiten(String administratienummer) {
        return persoonDalService.zoekIngeschrevenPersonenHistorisch(administratienummer).stream().map(persoonDalService::converteerNaarBrpPersoonslijst)
                .collect(Collectors.toList());
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public BrpPersoonslijst zoekPersoonOpBsnFoutiefOpgeschortUitsluiten(String burgerservicenummer) {
        final Persoon result = persoonDalService.zoekIngeschrevenPersoonOpBsn(burgerservicenummer);
        if (result == null) {
            return null;
        } else {
            return persoonDalService.converteerNaarBrpPersoonslijst(result);
        }
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public List<BrpPersoonslijst> zoekPersoonOpHistorischBsnFoutiefOpgeschortUitsluiten(String burgerservicenummer) {
        return persoonDalService.zoekIngeschrevenPersonenHistorischOpBsn(burgerservicenummer).stream().map(persoonDalService::converteerNaarBrpPersoonslijst)
                .collect(Collectors.toList());
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public List<BrpPersoonslijst> zoekPersoonOpVolgendeAnummerFoutiefOpgeschortUitsluiten(String administratienummer) {
        return persoonDalService.zoekIngeschrevenPersonenOpVolgendeAnummer(administratienummer).stream().map(persoonDalService::converteerNaarBrpPersoonslijst)
                .collect(Collectors.toList());
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public List<BrpPersoonslijst> zoekPersoonOpHistorischVolgendeAnummerFoutiefOpgeschortUitsluiten(String administratienummer) {
        return persoonDalService.zoekIngeschrevenPersonenHistorischOpVolgendeAnummer(administratienummer).stream()
                .map(persoonDalService::converteerNaarBrpPersoonslijst).collect(Collectors.toList());
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public List<BrpPersoonslijst> zoekPersoonOpVorigeAnummerFoutiefOpgeschortUitsluiten(String administratienummer) {
        return persoonDalService.zoekIngeschrevenPersonenOpVorigeAnummer(administratienummer).stream().map(persoonDalService::converteerNaarBrpPersoonslijst)
                .collect(Collectors.toList());
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public List<BrpPersoonslijst> zoekPersoonOpHistorischVorigeAnummerFoutiefOpgeschortUitsluiten(String administratienummer) {
        return persoonDalService.zoekIngeschrevenPersonenHistorischOpVorigeAnummer(administratienummer).stream()
                .map(persoonDalService::converteerNaarBrpPersoonslijst).collect(Collectors.toList());
    }

    private PersoonslijstPersisteerResultaat werkBestaandePersoonslijstBij(final DeltaBepalingContext context) {
        final Persoon bestaandePersoon = context.getBestaandePersoon();
        final Set<Long> oudeAfgeleidAdministratieveHistorieIds = verzamelAdministratieveHandelingenIds(bestaandePersoon);

        verwijderDummyHistorie(context.getBestaandePersoon());

        final DeltaProces deltaProces = SamengesteldDeltaProces.newInstanceMetAlleProcessen();
        deltaProces.bepaalVerschillen(context);
        deltaProces.verwerkVerschillen(context);

        final PersoonAfgeleidAdministratiefHistorie historie =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(bestaandePersoon.getPersoonAfgeleidAdministratiefHistorieSet());
        bestaandePersoon.setAdministratieveHandeling(historie.getAdministratieveHandeling());

        for (final AdministratieveHandeling administratieveHandeling : context.getAdministratieveHandelingen()) {
            final SoortAdministratieveHandeling soortAdministratieveHandeling = administratieveHandeling.getSoort();
            SynchronisatieLogging.addMelding(String.format("Deltabepaling: Uiteindelijke conclusie: %s", soortAdministratieveHandeling.getNaam()));
            SynchronisatieLogging.addDeltaResultaat(soortAdministratieveHandeling);
        }

        persoonDalService.persisteerPersoon(bestaandePersoon);

        final Set<AdministratieveHandeling> nieuweAdministratieveHandelingen =
                bepaalNieuweAdministratieveHandelingen(oudeAfgeleidAdministratieveHistorieIds, bestaandePersoon.getPersoonAfgeleidAdministratiefHistorieSet());

        return new PersoonslijstPersisteerResultaat(bestaandePersoon, nieuweAdministratieveHandelingen);
    }

    private Persoon mapPersoonEnOnderzoek(final BrpPersoonslijst brpPersoonslijst, final Lo3Bericht lo3Bericht) {
        // Map persoon
        final Persoon kluizenaar = new Persoon(SoortPersoon.INGESCHREVENE);
        final BrpPartijCode partijCode = brpPersoonslijst.getBijhoudingStapel().getActueel().getInhoud().getBijhoudingspartijCode();
        final Partij partij = dynamischeStamtabelRepository.getPartijByCode(partijCode.getWaarde());
        final OnderzoekMapper onderzoekMapper = new OnderzoekMapperImpl(kluizenaar, partij);

        new PersoonMapper(dynamischeStamtabelRepository, syncParameters, onderzoekMapper).mapVanMigratie(brpPersoonslijst, kluizenaar, lo3Bericht);

        return kluizenaar;
    }

    /**
     * Als de bestaande persoon in de BRP afkomstig is van een door conversie gegenereerde Dummy PL,
     * dan moet de historie van de groepen Bijhouding, ID, en Inschrijving verwijdert worden. Als
     * dat niet wordt gedaan dan worden acties van de Dummy PL opnieuw gebruikt, en deze komen niet
     * overeen met de acties van de conversie van de nieuwe PL. Dit zou tijdens terugconversie tot
     * extra rijen leiden doordat verschillende acties die inhoudelijk identiek zijn, toch tot
     * meerdere rijen worden geconverteerd.
     *
     * Voor het checken of een Persoon van de Dummy PL afkomstig is wordt gedaan door te kijken of
     * de persoon opshort-reden FOUT heeft, en een lege geslachtsnaamstam.
     *
     * Zie ook Jira issue ORANJE-2864.
     * @param bestaandePersoon de persoon waarvan als het een dummy is, de historie moet worden opgeschoond
     */
    private void verwijderDummyHistorie(final Persoon bestaandePersoon) {
        // Dummy PL resulteert in een niet-gevulde geslachtsnaamstam
        if (bestaandePersoon != null && bestaandePersoon.getGeslachtsnaamstam() == null
                && NadereBijhoudingsaard.FOUT.equals(bestaandePersoon.getNadereBijhoudingsaard())) {
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

    private Set<AdministratieveHandeling> bepaalNieuweAdministratieveHandelingen(final Set<Long> oudeAfgeleidAdministratieveHistorieIds,
                                                                                 final Set<PersoonAfgeleidAdministratiefHistorie>
                                                                                         nieuweAfgeleidAdministratieveHistorieSet) {
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

        persoonDalService.persisteerPersoon(persoon);

        final Set<AdministratieveHandeling> nieuweAdministratieveHandelingen = new HashSet<>();
        nieuweAdministratieveHandelingen.add(persoon.getAdministratieveHandeling());

        return new PersoonslijstPersisteerResultaat(persoon, nieuweAdministratieveHandelingen);
    }

    private boolean controleerGeenTeLeverenAdministratieveHandelingen(final Persoon dbPersoon) {
        for (final PersoonAfgeleidAdministratiefHistorie afgeleidAdministratief : dbPersoon.getPersoonAfgeleidAdministratiefHistorieSet()) {
            final AdministratieveHandeling administratieveHandeling = afgeleidAdministratief.getAdministratieveHandeling();
            switch (administratieveHandeling.getStatusLevering()) {
                case GELEVERD:
                case NIET_LEVEREN:
                case FOUT:
                    // Status is ok. Administratieve handeling hoeft niet meer verwerkt te worden.
                    break;
                case IN_LEVERING:
                case TE_LEVEREN:
                    // Status is niet ok. Administratie handeling moet nog verwerkt worden.
                    return false;
                default:
                    throw new IllegalArgumentException("Onbekend status voor levering");
            }
        }

        return true;
    }
}

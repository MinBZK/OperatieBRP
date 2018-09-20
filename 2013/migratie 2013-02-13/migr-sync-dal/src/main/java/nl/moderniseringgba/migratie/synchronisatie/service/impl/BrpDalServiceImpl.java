/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import nl.moderniseringgba.migratie.Requirement;
import nl.moderniseringgba.migratie.Requirements;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.synchronisatie.domein.blokkering.entity.Blokkering;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.SoortPersoon;
import nl.moderniseringgba.migratie.synchronisatie.domein.logging.entity.BerichtLog;
import nl.moderniseringgba.migratie.synchronisatie.repository.BerichtLogRepository;
import nl.moderniseringgba.migratie.synchronisatie.repository.BlokkeringRepository;
import nl.moderniseringgba.migratie.synchronisatie.repository.DynamischeStamtabelRepository;
import nl.moderniseringgba.migratie.synchronisatie.repository.PersoonRepository;
import nl.moderniseringgba.migratie.synchronisatie.service.BrpDalService;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.BrpPersoonslijstMapper;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.PersoonMapper;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.relateren.PersoonRelateerder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementatie voor de BrpDalService.
 */
@Service
public final class BrpDalServiceImpl implements BrpDalService {

    private final PersoonRepository persoonRepository;
    private final DynamischeStamtabelRepository dynamischeStamtabelRepository;
    private final BerichtLogRepository berichtLogRepository;
    private final PersoonRelateerder persoonRelateerder;
    private final BrpPersoonslijstMapper brpPersoonslijstMapper;
    private final BlokkeringRepository blokkeringRepository;

    /**
     * Default constructor.
     * 
     * @param persoonRepository
     *            De persoonrepository.
     * @param dynamischeStamtabelRepository
     *            de repository voor het bevragen van de stamtabellen.
     * @param berichtLogRepository
     *            de repository voor het bevragen en opslaan van BerichtLog objecten
     * @param persoonRelateerder
     *            de helper klasse die functionaliteit bevat voor het relateren van personen
     * @param brpPersoonslijstMapper
     *            de mapper voor het mappen van BRP-entities op het migratie model
     * @param blokkeringRepository
     *            de repository voor het bevragen en opslaan van blokkering.
     */
    @Inject
    BrpDalServiceImpl(
            final PersoonRepository persoonRepository,
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final BerichtLogRepository berichtLogRepository,
            @Named("persoonRelateerderImpl") final PersoonRelateerder persoonRelateerder,
            final BrpPersoonslijstMapper brpPersoonslijstMapper,
            final BlokkeringRepository blokkeringRepository) {
        this.persoonRepository = persoonRepository;
        this.dynamischeStamtabelRepository = dynamischeStamtabelRepository;
        this.berichtLogRepository = berichtLogRepository;
        this.persoonRelateerder = persoonRelateerder;
        this.brpPersoonslijstMapper = brpPersoonslijstMapper;
        this.blokkeringRepository = blokkeringRepository;

    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Persoon persisteerPersoonslijst(final BrpPersoonslijst brpPersoonslijst) {
        return converteerEnRelateerPersoonslijst(brpPersoonslijst, null);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Persoon persisteerPersoonslijst(
            final BrpPersoonslijst brpPersoonslijst,
            final BigDecimal aNummerTeVervangenPersoon) {
        if (aNummerTeVervangenPersoon == null) {
            throw new NullPointerException("aNummerTeVervangenPersoon mag niet null zijn");
        }
        return converteerEnRelateerPersoonslijst(brpPersoonslijst, aNummerTeVervangenPersoon);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public BerichtLog persisteerBerichtLog(final BerichtLog berichtLog) {
        if (berichtLog == null) {
            throw new NullPointerException("berichtLog mag niet null zijn");
        }
        return berichtLogRepository.save(berichtLog);
    }

    @Requirement({ Requirements.CRP001_LB02, Requirements.CRP001_LB03 })
    private Persoon converteerEnRelateerPersoonslijst(
            final BrpPersoonslijst brpPersoonslijst,
            final BigDecimal aNummerIstPersoon) {

        // Map persoon
        final Persoon kluizenaar = new Persoon();
        mapPersoonslijstOpPersoon(brpPersoonslijst, kluizenaar);

        // TODO return value gebruiken? -> kluizenaar = persoonRelateerder.updateRelatiesVanPersoon(kluizenaar);
        persoonRelateerder.updateRelatiesVanPersoon(kluizenaar, aNummerIstPersoon);

        // merge persoon
        return persoonRepository.save(kluizenaar);
    }

    private void mapPersoonslijstOpPersoon(final BrpPersoonslijst brpPersoonslijst, final Persoon persoon) {
        new PersoonMapper(dynamischeStamtabelRepository).mapVanMigratie(brpPersoonslijst, persoon);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public BrpPersoonslijst bevraagPersoonslijst(final long administratienummer) {
        final Persoon result = zoekIngeschrevenPersoon(administratienummer);
        if (result == null) {
            throw new IllegalStateException(String.format("Geen persoon gevonden voor a-nummer '%s'.",
                    administratienummer));
        }
        return brpPersoonslijstMapper.mapNaarMigratie(result);
    }

    private Persoon zoekIngeschrevenPersoon(final long administratienummer) {
        final List<Persoon> personen =
                persoonRepository.findByAdministratienummer(new BigDecimal(administratienummer),
                        SoortPersoon.INGESCHREVENE);

        final Persoon result;
        if (personen == null || personen.isEmpty()) {
            result = null;
        } else if (personen.size() > 1) {
            throw new IllegalStateException(String.format(
                    "Meer dan 1 persoon gevonden voor (historisch) a-nummer '%s'.", administratienummer));
        } else {
            result = personen.get(0);
        }
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public BrpPersoonslijst zoekPersoonOpAnummer(final long administratienummer) {
        final Persoon result = zoekIngeschrevenPersoon(administratienummer);
        return result == null ? null : brpPersoonslijstMapper.mapNaarMigratie(result);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public BerichtLog zoekBerichtLogOpAnummer(final long administratienummer) {
        return berichtLogRepository.findLaatsteBerichtLogVoorANummer(BigDecimal.valueOf(administratienummer));
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public List<Long> zoekBerichtLogAnrs(final Date vanaf, final Date tot, final String gemeentecode) {
        return berichtLogRepository.findLaatsteBerichtLogAnrs(vanaf, tot, gemeentecode);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public BrpPersoonslijst zoekPersoonOpHistorischAnummer(final long administratienummer) {
        final Persoon result = zoekIngeschrevenPersoonHistorisch(administratienummer);
        return result == null ? null : brpPersoonslijstMapper.mapNaarMigratie(result);
    }

    private Persoon zoekIngeschrevenPersoonHistorisch(final long administratienummer) {
        final List<Persoon> personen =
                persoonRepository.findByAdministratienummerHistorisch(new BigDecimal(administratienummer),
                        SoortPersoon.INGESCHREVENE);

        final Persoon result;
        if (personen == null || personen.isEmpty()) {
            result = null;
        } else if (personen.size() > 1) {
            throw new IllegalStateException(String.format("Meer dan 1 persoon gevonden voor a-nummer '%s'.",
                    administratienummer));
        } else {
            result = personen.get(0);
        }
        return result;
    }

    @Override
    public Blokkering persisteerBlokkering(final Blokkering blokkering) {

        if (blokkeringRepository.statusBlokkering(blokkering.getaNummer()) != null) {
            throw new IllegalStateException("De persoonlijst met het opgegeven aNummer is al geblokkeerd.");
        }

        return blokkeringRepository.blokkeerPersoonslijst(blokkering);
    }

    @Override
    public Blokkering vraagOpBlokkering(final String aNummer) {

        if (aNummer == null || "".equals(aNummer)) {
            throw new IllegalStateException("Er is geen aNummer opgegeven.");
        }

        return blokkeringRepository.statusBlokkering(aNummer);
    }

    @Override
    public void verwijderBlokkering(final Blokkering teVerwijderenBlokkering) {

        final Blokkering controleBlokkering =
                blokkeringRepository.statusBlokkering(teVerwijderenBlokkering.getaNummer());

        if (controleBlokkering == null) {
            throw new IllegalStateException("De persoonlijst met het opgegeven aNummer is niet geblokkeerd.");
        }

        if (!teVerwijderenBlokkering.getProcessId().equals(controleBlokkering.getProcessId())) {
            throw new IllegalStateException(
                    "Het process ID komt niet overeen met het process ID waarmee de persoonslijst is geblokkeerd.");
        }

        if (!teVerwijderenBlokkering.getRegistratieGemeente().equals(controleBlokkering.getRegistratieGemeente())) {
            throw new IllegalStateException(
                    "De registratiegemeente komt niet overeen met de registratiegemeent die de persoonslijst "
                            + "heeft geblokkeerd.");
        }

        blokkeringRepository.deblokkeerPersoonslijst(teVerwijderenBlokkering);
    }

}

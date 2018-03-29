/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Onderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.PersoonRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.service.PersoonService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.BrpPersoonslijstMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapperImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementatie van PersoonDalService.
 */
@Service
public final class PersoonServiceImpl implements PersoonService {

    private static final String SYNC_DAL_TRANSACTION_MANAGER = "syncDalTransactionManager";
    private static final String MEER_DAN_1_PERSOON_GEVONDEN_VOOR_A_NUMMER_S = "Meer dan 1 persoon gevonden voor a-nummer '%s'.";

    private final PersoonRepository persoonRepository;
    private final BrpPersoonslijstMapper brpPersoonslijstMapper;

    /**
     * Instantieert de persoon service.
     * @param persoonRepository persoon repository
     * @param brpPersoonslijstMapper brp persoonslijst mapper
     */
    @Inject
    public PersoonServiceImpl(final PersoonRepository persoonRepository, final BrpPersoonslijstMapper brpPersoonslijstMapper) {
        this.persoonRepository = persoonRepository;
        this.brpPersoonslijstMapper = brpPersoonslijstMapper;
    }


    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public BrpPersoonslijst converteerNaarBrpPersoonslijst(final Persoon persoon) {
        final Set<Onderzoek> onderzoeken = persoon.getOnderzoeken();
        final BrpOnderzoekMapper brpOnderzoekMapper = new BrpOnderzoekMapperImpl(onderzoeken);
        return brpPersoonslijstMapper.mapNaarMigratie(persoon, brpOnderzoekMapper);
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED)
    @Override
    public void persisteerPersoon(final Persoon persoon) {
        persoonRepository.save(persoon);
    }


    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public List<Persoon> zoekPersoon(final Persoon persoon) {
        if (persoon != null) {
            return persoonRepository.zoekPersoon(persoon);
        } else {
            return Collections.emptyList();
        }
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public List<Persoon> zoekPersonenOpActueleGegevens(
            final String administratienummer,
            final String burgerservicenummer,
            final String geslachtsnaamstam,
            final String postcode) {
        return persoonRepository.zoekPersonenOpActueleGegevens(administratienummer, burgerservicenummer, geslachtsnaamstam, postcode);
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public List<Persoon> zoekPersonenOpHistorischeGegevens(
            final String administratienummer,
            final String burgerservicenummer,
            final String geslachtsnaamstam) {
        return persoonRepository.zoekPersonenOpHistorischeGegevens(administratienummer, burgerservicenummer, geslachtsnaamstam);
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public Persoon zoekIngeschrevenPersoonOpTechnischeSleutel(final Long technischeSleutel, final boolean indicatieFoutiefOpgeschortUitsluiten) {
        final List<Persoon> personen =
                persoonRepository.findByTechnischeSleutel(technischeSleutel, SoortPersoon.INGESCHREVENE, indicatieFoutiefOpgeschortUitsluiten);

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
    public Persoon zoekIngeschrevenPersoon(final String administratienummer, final boolean indicatieFoutiefOpgeschortUitsluiten) {
        return enkelePersoon(
                persoonRepository.findByAdministratienummer(administratienummer, SoortPersoon.INGESCHREVENE, indicatieFoutiefOpgeschortUitsluiten));
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public List<Persoon> zoekIngeschrevenPersonenHistorisch(final String administratienummer) {
        return persoonRepository.findByAdministratienummerHistorisch(administratienummer, SoortPersoon.INGESCHREVENE);
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public Persoon zoekIngeschrevenPersoonOpBsn(final String burgerservicenummer) {
        return enkelePersoon(
                persoonRepository.findByBurgerServiceNummer(burgerservicenummer));
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public List<Persoon> zoekIngeschrevenPersonenHistorischOpBsn(final String burgerservicenummer) {
        return persoonRepository.findByBurgerservicenummerHistorisch(burgerservicenummer, SoortPersoon.INGESCHREVENE);
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public List<Persoon> zoekIngeschrevenPersonenOpVolgendeAnummer(final String administratienummer) {
        return persoonRepository.findByVolgendeAnummer(administratienummer, SoortPersoon.INGESCHREVENE);
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public List<Persoon> zoekIngeschrevenPersonenHistorischOpVolgendeAnummer(final String administratienummer) {
        return persoonRepository.findByVolgendeAnummerHistorisch(administratienummer, SoortPersoon.INGESCHREVENE);
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public List<Persoon> zoekIngeschrevenPersonenOpVorigeAnummer(final String administratienummer) {
        return persoonRepository.findByVorigeAnummer(administratienummer, SoortPersoon.INGESCHREVENE);
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public List<Persoon> zoekIngeschrevenPersonenHistorischOpVorigeAnummer(final String administratienummer) {
        return persoonRepository.findByVorigeAnummerHistorisch(administratienummer, SoortPersoon.INGESCHREVENE);
    }

    private Persoon enkelePersoon(final List<Persoon> personen) {
        final Persoon result;
        if (personen == null || personen.isEmpty()) {
            result = null;
        } else if (personen.size() > 1) {
            throw new IllegalStateException(String.format(MEER_DAN_1_PERSOON_GEVONDEN_VOOR_A_NUMMER_S, personen.get(0).getAdministratienummer()));
        } else {
            result = personen.get(0);
        }
        return result;
    }
}

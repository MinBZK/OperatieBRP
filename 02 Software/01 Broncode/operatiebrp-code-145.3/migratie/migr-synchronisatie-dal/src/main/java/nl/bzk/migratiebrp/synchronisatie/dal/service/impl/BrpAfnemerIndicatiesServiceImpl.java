/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAfnemersindicatie;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAfnemersindicaties;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.regels.proces.foutmelding.Foutmelding;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.AfnemersindicatieRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.LeveringsautorisatieRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.PartijRolRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpAfnemerIndicatiesService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.PersoonService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.autorisatie.AfnemerIndicatieService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.autorisatie.impl.AfnemerIndicatieServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementatie voor de BrpAfnemerIndicatieDalService.
 */
@Service
public final class BrpAfnemerIndicatiesServiceImpl implements BrpAfnemerIndicatiesService {

    private static final String SYNC_DAL_TRANSACTION_MANAGER = "syncDalTransactionManager";

    private final PersoonService persoonDalService;
    private final AfnemerIndicatieService afnemerIndicatieService;

    /**
     * Default constructor.
     * @param persoonDalService de persoonservice
     * @param dynamischeStamtabelRepository de repository voor het bevragen van de stamtabellen
     * @param afnemersindicatieRepository de repository voor het opslaan en bevragen van afnemersindicaties
     * @param leveringsautorisatieRepository de repository voor het bevragen van leveringautorisaties
     * @param partijRolRepository de repository voor het bevragen van partijrollen
     */
    @Inject
    public BrpAfnemerIndicatiesServiceImpl(
            final PersoonService persoonDalService,
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final AfnemersindicatieRepository afnemersindicatieRepository,
            final LeveringsautorisatieRepository leveringsautorisatieRepository,
            final PartijRolRepository partijRolRepository) {
        this.persoonDalService = persoonDalService;
        this.afnemerIndicatieService =
                new AfnemerIndicatieServiceImpl(dynamischeStamtabelRepository, afnemersindicatieRepository, leveringsautorisatieRepository,
                        partijRolRepository);
    }

    @Transactional(transactionManager = SYNC_DAL_TRANSACTION_MANAGER, propagation = Propagation.REQUIRED, noRollbackFor = {IllegalStateException.class})
    @Override
    public void persisteerAfnemersindicaties(final BrpAfnemersindicaties brpAfnemersindicaties) {
        final Persoon persoon = persoonDalService.zoekIngeschrevenPersoon(brpAfnemersindicaties.getAdministratienummer(), false);
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
    public BrpAfnemersindicaties bevraagAfnemersindicaties(final String administratienummer) {
        final Persoon persoon = persoonDalService.zoekIngeschrevenPersoon(administratienummer, false);
        if (persoon == null) {
            throw new IllegalStateException(
                    String.format("Geen persoon gevonden voor a-nummer '%s' (bij bevragen afnemersindicaties).", administratienummer));
        }

        return afnemerIndicatieService.bevraagAfnemersindicaties(persoon);
    }
}

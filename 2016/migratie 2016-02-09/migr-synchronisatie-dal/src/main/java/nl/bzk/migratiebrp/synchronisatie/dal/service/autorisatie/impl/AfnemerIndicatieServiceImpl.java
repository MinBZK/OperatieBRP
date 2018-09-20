/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.autorisatie.impl;

import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAfnemersindicatie;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAfnemersindicaties;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.PersoonAfnemerindicatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.AfnemersindicatieRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.LeveringsautorisatieRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.service.autorisatie.AfnemerIndicatieService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.autorisatie.BrpAfnemersindicatiesMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.autorisatie.PersoonAfnemersindicatiesMapper;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

/**
 * Implementatie class voor de {@link AfnemerIndicatieService}.
 */
public final class AfnemerIndicatieServiceImpl implements AfnemerIndicatieService {
    private static final Logger LOG = LoggerFactory.getLogger();

    private final AfnemersindicatieRepository afnemersindicatieRepository;
    private final DynamischeStamtabelRepository dynamischeStamtabelRepository;
    private final LeveringsautorisatieRepository leveringsautorisatieRepository;

    /**
     * Constructor voor de AutorisatieServiceImpl met alle repositories die nodig zijn.
     *
     * @param dynamischeStamtabelRepository
     *            repository voor dynamische stamtabellen
     * @param afnemersindicatieRepository
     *            repository voor afnemersindicaties
     * @param leveringsautorisatieRepository
     *            repository voor leverings autorisaties
     */
    public AfnemerIndicatieServiceImpl(
        final DynamischeStamtabelRepository dynamischeStamtabelRepository,
        final AfnemersindicatieRepository afnemersindicatieRepository,
        final LeveringsautorisatieRepository leveringsautorisatieRepository)
    {
        this.afnemersindicatieRepository = afnemersindicatieRepository;
        this.dynamischeStamtabelRepository = dynamischeStamtabelRepository;
        this.leveringsautorisatieRepository = leveringsautorisatieRepository;
    }

    @Override
    @SuppressWarnings("checkstyle:illegalcatch")
    public void persisteerAfnemersindicaties(final List<BrpAfnemersindicatie> brpAfnemersindicaties, final Persoon persoon) {
        for (final BrpAfnemersindicatie brpAfnemersindicatie : brpAfnemersindicaties) {
            try {
                final PersoonAfnemersindicatiesMapper persoonAfnemersindicatiesMapper =
                        new PersoonAfnemersindicatiesMapper(dynamischeStamtabelRepository, leveringsautorisatieRepository);
                final PersoonAfnemerindicatie persoonAfnemerindicatie = persoonAfnemersindicatiesMapper.mapVanMigratie(persoon, brpAfnemersindicatie);

                if (persoonAfnemerindicatie != null) {
                    afnemersindicatieRepository.save(persoonAfnemerindicatie);
                }
            } catch (final Exception e) {
                // We willen hier juist Exception afvangen zodat alle onverwachte fouten netjes gelogd worden.

                final Lo3Herkomst herkomst = brpAfnemersindicatie.getAfnemersindicatieStapel().getLaatsteElement().getActieInhoud().getLo3Herkomst();
                final Lo3Herkomst voorkomenOnafhankelijkeHerkomst = new Lo3Herkomst(herkomst.getCategorie(), herkomst.getStapel(), -1);
                Logging.log(voorkomenOnafhankelijkeHerkomst, LogSeverity.ERROR, SoortMeldingCode.AFN012, null);
                LOG.error("Er is een fout opgetreden bij het opslaan van een afnemer indicatie. ", e);
            }
        }
    }

    @Override
    public BrpAfnemersindicaties bevraagAfnemersindicaties(final Persoon persoon) {
        final List<PersoonAfnemerindicatie> afnemersindicaties = afnemersindicatieRepository.findByPersoon(persoon);
        return new BrpAfnemersindicatiesMapper().mapNaarMigratie(persoon, afnemersindicaties);
    }

}

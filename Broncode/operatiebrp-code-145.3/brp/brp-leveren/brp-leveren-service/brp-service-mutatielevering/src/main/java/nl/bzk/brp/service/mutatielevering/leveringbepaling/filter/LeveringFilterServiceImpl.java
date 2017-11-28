/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering.leveringbepaling.filter;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Populatie;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import org.springframework.stereotype.Component;


/**
 * Implementatie van de BepaalTeLeverenPersonenService.
 */
@Component
final class LeveringFilterServiceImpl implements LeveringFilterService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    @Named("PersoonPopulatieFilter")
    private Leveringfilter persoonPopulatieFilter;
    @Inject
    @Named("PersoonAfnemerindicatieFilter")
    private Leveringfilter persoonAfnemerindicatieFilter;
    @Inject
    @Named("PersoonAttenderingFilter")
    private Leveringfilter persoonAttenderingFilter;
    @Inject
    @Named("Verstrekkingsbeperkingfilter")
    private Leveringfilter verstrekkingsbeperkingfilter;

    private List<Leveringfilter> filterList;

    private LeveringFilterServiceImpl() {

    }

    /**
     * Spring postConstruct callback.
     */
    @PostConstruct
    public void postConstruct() {
        filterList = Lists.newArrayList(
                persoonPopulatieFilter, persoonAfnemerindicatieFilter, persoonAttenderingFilter, verstrekkingsbeperkingfilter
        );
    }

    @Override
    public Set<Persoonslijst> bepaalTeLeverenPersonen(final Autorisatiebundel autorisatiebundel,
                                                      final Map<Persoonslijst, Populatie> mogelijkTeLeverenPersonen) throws ExpressieException {
        final Set<Persoonslijst> teLeverenPersonen = Sets.newHashSet();
        for (final Map.Entry<Persoonslijst, Populatie> entry : mogelijkTeLeverenPersonen.entrySet()) {
            if (moetGeleverdWorden(entry.getKey(), entry.getValue(), autorisatiebundel)) {
                teLeverenPersonen.add(entry.getKey());
            }
        }
        return teLeverenPersonen;
    }

    /**
     * Bepaalt op basis van de populatie, de leveringsautorisatie en de administratieve handeling of de persoon geleverd moet worden.
     * @param persoon De persoon.
     * @param populatie De populatie van de persoon.
     * @param autorisatiebundel De autorisatiebundel
     * @return boolean true als geleverd moet worden, anders false.
     */
    @Bedrijfsregel(Regel.R1334)
    private boolean moetGeleverdWorden(final Persoonslijst persoon, final Populatie populatie,
                                       final Autorisatiebundel autorisatiebundel) {
        if (Populatie.BUITEN == populatie && SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE == autorisatiebundel.getSoortDienst()) {
            logPersoonValtBuitenPopulatie(autorisatiebundel, persoon
                    .getMetaObject().getObjectsleutel());
        }
        return filterList.stream().allMatch(leveringfilter -> leveringfilter.magLeveren(persoon, populatie, autorisatiebundel));
    }

    /**
     * Registreer een melding als de persoon buiten populatie valt en een afnemer indicatie heeft.
     * @param autorisatiebundel de leveringAutorisatie
     * @param persoonId persoon id
     */
    private void logPersoonValtBuitenPopulatie(final Autorisatiebundel autorisatiebundel, final Long persoonId) {
        final String partijNaam = autorisatiebundel.getPartij().getNaam();
        final Integer leveringsautorisatieId = autorisatiebundel.getLeveringsautorisatie().getId();
        LOGGER.warn("Er bevindt zich een afnemerindicatie van afnemer '{}' op "
                        + "persoon {} met leveringsautorisatie {}, terwijl de persoon buiten de populatie valt.",
                partijNaam, persoonId, leveringsautorisatieId);
    }

}

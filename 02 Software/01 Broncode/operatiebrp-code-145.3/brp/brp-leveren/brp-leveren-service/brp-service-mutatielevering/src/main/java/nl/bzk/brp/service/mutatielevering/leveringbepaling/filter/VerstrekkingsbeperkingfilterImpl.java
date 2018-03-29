/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering.leveringbepaling.filter;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Populatie;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.VerstrekkingsbeperkingService;
import org.springframework.stereotype.Component;


/**
 * Filter dat personen wegfiltert die vanwege een verstrekkingsbeperking niet geleverd mogen worden. Deze regel is enkel van toepassing op Attendering,
 * Attendering met plaatsen afnemerindicatie, Mutatielevering op basis van afnemerindicatie, Selectie, Selectie met plaatsen afnemerindicatie.
 * (NB: dus niet voor de diensten Mutatielevering op basis van doelbinding en voor Selectie met verwijderen afnemerindicatie)
 */
@Component("Verstrekkingsbeperkingfilter")
@Bedrijfsregel(Regel.R1983)
final class VerstrekkingsbeperkingfilterImpl implements Leveringfilter {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private VerstrekkingsbeperkingService verstrekkingsbeperkingService;

    private VerstrekkingsbeperkingfilterImpl() {
    }

    @Override
    public boolean magLeveren(final Persoonslijst persoon,
                              final Populatie populatie,
                              final Autorisatiebundel autorisatiebundel) {

        final SoortDienst soortDienst = autorisatiebundel.getDienst().getSoortDienst();
        if (!(soortDienst == SoortDienst.ATTENDERING || soortDienst == SoortDienst.SELECTIE
                || soortDienst == SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE)) {
            return true;
        }
        final Partij partij = autorisatiebundel.getPartij();
        final boolean leveringMagDoorgaan;
        if (soortDienst == SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE) {
            boolean heeftVerstrekkingsBeperkingVorigeHandeling = persoon.beeldVan().vorigeHandeling() != null &&
                    verstrekkingsbeperkingService.heeftGeldigeVerstrekkingsbeperking(persoon.beeldVan().vorigeHandeling(), partij);
            boolean heeftVertrekkingsbeperkingNu = verstrekkingsbeperkingService.heeftGeldigeVerstrekkingsbeperking(persoon.getNuNuBeeld(), partij);
            leveringMagDoorgaan = !(heeftVerstrekkingsBeperkingVorigeHandeling && heeftVertrekkingsbeperkingNu);
            if (!leveringMagDoorgaan) {
                LOGGER.debug("Levering voor mutatielevering obv afnemerindicatie mag niet doorgaan, er geldt een "
                        + "verstrekkingsbeperking voor persoon {} op partij {}", persoon.getId(), partij.getCode());
            }
        } else {
            leveringMagDoorgaan = !verstrekkingsbeperkingService.heeftGeldigeVerstrekkingsbeperking(persoon.getNuNuBeeld(), partij);
            if (!leveringMagDoorgaan) {
                LOGGER.debug("Levering mag niet doorgaan, er geldt een "
                        + "verstrekkingsbeperking voor persoon {} op partij {}", persoon.getId(), partij.getCode());
            }
        }

        return leveringMagDoorgaan;
    }

}

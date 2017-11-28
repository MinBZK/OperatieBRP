/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen;

import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import org.springframework.stereotype.Service;

/**
 * In deze regel wordt bepaald of een persoon een verstrekkingsbeperking heeft voor een partij. Dit kan een algehele verstrekkingsbeperking zijn, of een
 * specifieke.
 */
@Bedrijfsregel(Regel.R1342)
@Bedrijfsregel(Regel.R1339)
@Service
public final class VerstrekkingsbeperkingServiceImpl implements VerstrekkingsbeperkingService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    public boolean heeftGeldigeVerstrekkingsbeperking(final Persoonslijst persoonslijst, final Partij partij) {
        final Boolean verstrekkingsbeperkingMogelijk = Boolean.TRUE.equals(partij.isIndicatieVerstrekkingsbeperkingMogelijk());
        if (verstrekkingsbeperkingMogelijk
                && (persoonslijst.heeftIndicatieVolledigeVerstrekkingsbeperking() || heeftVerstrekkingsbeperkingOpPartij(persoonslijst,
                partij))) {
            LOGGER.info("Persoon met id {} heeft een verstrekkingsbeperking voor partij met code {}.",
                    persoonslijst.getMetaObject().getObjectsleutel(),
                    partij.getCode());
            return true;
        } else if (!verstrekkingsbeperkingMogelijk && heeftVerstrekkingsbeperkingOpPartij(persoonslijst, partij)) {
            LOGGER.info("De persoon met id {} heeft een verstrekkingsbeperking op partij met code {}, terwijl "
                    + "op deze partij geen verstrekkingsbeperking mogelijk is.", persoonslijst.getMetaObject().getObjectsleutel(), partij.getCode());
        }
        return false;
    }

    @Override
    public RegelValidatie maakRegelValidatie(final Persoonslijst persoonslijst, final Partij partij) {
        return new RegelValidatie() {
            @Override
            public Regel getRegel() {
                return Regel.R1339;
            }

            @Override
            public Melding valideer() {
                if (heeftGeldigeVerstrekkingsbeperking(persoonslijst, partij)) {
                    return new Melding(getRegel());
                }
                return null;
            }
        };
    }

    /**
     * Controleert of de persoon een verstrekkingsbeperking heeft op deze partij (dus niet een algehele verstrekkings- beperking).
     * @param persoonslijst De persoonsgegevens.
     * @param partij De Partij.
     * @return True als de persoon een verstrekkingsbeperking heeft op de partij, anders false.
     */
    private boolean heeftVerstrekkingsbeperkingOpPartij(final Persoonslijst persoonslijst, final Partij partij) {
        for (final String partijCode : persoonslijst.getVerstrekkingsbeperkingen()) {
            // Dit filter werkt alleen voor de variant waarbij de verstrekkingsbeperking op de partij ligt. Dus niet
            // voor de 'gemeenteverordening met omschrijving derde'-variant.
            if (partijCode.equals(partij.getCode())) {
                LOGGER.debug("Persoon met id {} heeft verstrekkingsbeperking op partij met code {}.",
                        persoonslijst.getMetaObject().getObjectsleutel(),
                        partij.getCode());
                return true;
            }
        }
        return false;
    }
}

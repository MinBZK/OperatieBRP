/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.regels.impl.levering.definitieregels;

import javax.inject.Named;
import nl.bzk.brp.logging.FunctioneleMelding;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonIndicatieView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonVerstrekkingsbeperkingView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;


/**
 * In deze regel wordt bepaald of een persoon een verstrekkingsbeperking heeft voor een partij. Dit kan een algehele
 * verstrekkingsbeperking zijn, of een specifieke.
 *
 * @brp.bedrijfsregel BRLV0035
 */
@Named("BRLV0035")
public final class BRLV0035 {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    /**
     * Bepaalt of de persoon een geldige verstrekkingsbeperking heeft voor de partij.
     *
     * @param persoonView De persoon.
     * @param partij De partij.
     * @return True als er een geldige verstrekkingsbeperking is, anders false.
     */
    public boolean isErEenGeldigeVerstrekkingsBeperking(final PersoonView persoonView, final Partij partij) {
        if (verstrekkingsBeperkingMogelijk(partij)
            && (heeftVolledigeVerstrekkingsbeperking(persoonView) || heeftVerstrekkingsbeperkingOpPartij(persoonView,
                    partij)))
        {
            LOGGER.info(FunctioneleMelding.LEVERING_BEDRIJFSREGEL_BRLV0035,
                    "Persoon met id {} heeft een verstrekkingsbeperking voor partij met code {}.", persoonView.getID(),
                    partij.getCode());
            return true;
        } else if (!verstrekkingsBeperkingMogelijk(partij) && heeftVerstrekkingsbeperkingOpPartij(persoonView, partij))
        {
            LOGGER.info("De persoon met id {} heeft een verstrekkingsbeperking op partij met code {}, terwijl "
                + "op deze partij geen verstrekkingsbeperking mogelijk is.", persoonView.getID(), partij.getCode());
        }
        return false;
    }

    /**
     * Controleert of een verstrekkingsbeperking mogelijk is op deze partij.
     *
     * @param partij De partij.
     * @return True als de verstrekkingsbeperking mogelijk is, anders false.
     */
    private boolean verstrekkingsBeperkingMogelijk(final Partij partij) {
        final JaNeeAttribuut verstrekkingsbeperkingMogelijk = partij.getIndicatieVerstrekkingsbeperkingMogelijk();
        return verstrekkingsbeperkingMogelijk != null && verstrekkingsbeperkingMogelijk.getWaarde();
    }

    /**
     * Controleert of de persoon een verstrekkingsbeperking heeft op deze partij (dus niet een algehele verstrekkings-
     * beperking).
     *
     * @param persoon De persoon.
     * @param partij De Partij.
     * @return True als de persoon een verstrekkingsbeperking heeft op de partij, anders false.
     */
    private boolean heeftVerstrekkingsbeperkingOpPartij(final PersoonView persoon, final Partij partij) {
        for (final PersoonVerstrekkingsbeperkingView verstrekkingsbeperking : persoon.getVerstrekkingsbeperkingen()) {
            // Deze filter werkt alleen voor de variant waarbij de verstrekkingsbeperking op de partij ligt. Dus niet
            // voor de 'gemeenteverordening met omschrijving derde'-variant.
            if (verstrekkingsbeperking.getPartij() != null
                && verstrekkingsbeperking.getPartij().getWaarde().getCode().getWaarde()
                        .equals(partij.getCode().getWaarde()))
            {
                LOGGER.debug("Persoon met id {} heeft verstrekkingsbeperking op partij met code {}.", persoon.getID(),
                        partij.getCode().getWaarde());
                return true;
            }
        }
        return false;
    }

    /**
     * Controleert of de persoon een volledige verstrekkingsbeperking heeft.
     *
     * @param persoon De persoon.
     * @return True als de persoon een volledige verstrekkingsbeperking heeft, anders false.
     */
    private boolean heeftVolledigeVerstrekkingsbeperking(final PersoonView persoon) {
        final PersoonIndicatieView volledigeVerstrekkingsbeperking =
            persoon.getIndicatieVolledigeVerstrekkingsbeperking();
        if (volledigeVerstrekkingsbeperking != null) {
            final Ja heeftVolledigeVerstrekkingsbeperking =
                volledigeVerstrekkingsbeperking.getStandaard().getWaarde().getWaarde();
            if (heeftVolledigeVerstrekkingsbeperking != null && heeftVolledigeVerstrekkingsbeperking.getVasteWaarde()) {
                LOGGER.debug("Persoon met id {} heeft een volledige verstrekkingsbeperking.", persoon.getID());
                return true;
            }
        }
        return false;
    }

}

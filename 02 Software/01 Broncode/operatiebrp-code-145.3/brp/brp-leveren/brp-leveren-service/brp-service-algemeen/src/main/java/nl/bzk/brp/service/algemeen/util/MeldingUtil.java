/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.util;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMelding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerwerkingsResultaat;
import nl.bzk.brp.domain.algemeen.Melding;

/**
 * SoortMeldingBepaler.
 */
public final class MeldingUtil {

    private MeldingUtil() {
    }

    /**
     * Bepaalt het hoogste melding niveau aan de hand van de lijst met meldingen.
     * @param meldingen Lijst van meldingen.
     * @return SoortMelding.
     */
    public static SoortMelding bepaalHoogsteMeldingNiveau(final List<Melding> meldingen) {
        final SoortMelding hoogste;
        if (meldingen.isEmpty()) {
            hoogste = null;
        } else {
            int hoogsteId = 1;
            for (final Melding melding : meldingen) {
                hoogsteId = Math.max(hoogsteId, melding.getSoort().getId());
            }
            hoogste = SoortMelding.parseId(hoogsteId);
        }
        return hoogste == null ? SoortMelding.GEEN : hoogste;
    }

    /**
     * Bepaal het {@link VerwerkingsResultaat} ahv een reeks meldingen.
     * @param meldingen reeks meldingen
     * @return het verwerkingsresultaat
     */
    public static VerwerkingsResultaat bepaalVerwerking(final List<Melding> meldingen) {
        for (final Melding melding : meldingen) {
            if (melding.getSoort() == SoortMelding.DEBLOKKEERBAAR || melding.getSoort() == SoortMelding.FOUT) {
                return VerwerkingsResultaat.FOUTIEF;
            }
        }
        return VerwerkingsResultaat.GESLAAGD;
    }
}

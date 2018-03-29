/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMelding;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBericht;
import nl.bzk.brp.bijhouding.bericht.model.MeldingElement;
import org.springframework.stereotype.Service;

/**
 * Service voor het valideren van bijhoudingsberichten.
 */
@Service
public final class ValidatieServiceImpl implements ValidatieService {

    @Override
    public List<MeldingElement> valideer(final BijhoudingVerzoekBericht bericht) {
        return bericht.valideer();
    }

    @Override
    public boolean kanVerwerkingDoorgaan(final List<MeldingElement> meldingen) {
        boolean resultaat = true;
        for (final MeldingElement melding : meldingen) {

            if (melding.getRegel().getSoortMelding().getMeldingNiveau() > SoortMelding.WAARSCHUWING.getMeldingNiveau()) {
                resultaat = false;
                break;
            }
        }
        return resultaat;
    }

    @Override
    public SoortMelding bepaalHoogsteMeldingNiveau(final List<MeldingElement> meldingen) {
        SoortMelding hoogsteMeldingNiveau = SoortMelding.GEEN;
        for (final MeldingElement melding : meldingen) {
            final SoortMelding soortMelding = melding.getRegel().getSoortMelding();
            if (soortMelding.getMeldingNiveau() > hoogsteMeldingNiveau.getMeldingNiveau()) {
                hoogsteMeldingNiveau = soortMelding;
            }
        }

        return hoogsteMeldingNiveau;
    }
}

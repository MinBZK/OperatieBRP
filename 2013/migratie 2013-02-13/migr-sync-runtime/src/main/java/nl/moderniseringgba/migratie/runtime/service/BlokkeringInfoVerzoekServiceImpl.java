/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.runtime.service;

import javax.inject.Inject;

import nl.moderniseringgba.isc.esb.message.sync.generated.BlokkeringInfoAntwoordType;
import nl.moderniseringgba.isc.esb.message.sync.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.sync.impl.BlokkeringInfoAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.BlokkeringInfoVerzoekBericht;
import nl.moderniseringgba.migratie.synchronisatie.domein.blokkering.entity.Blokkering;
import nl.moderniseringgba.migratie.synchronisatie.service.BrpDalService;

import org.springframework.stereotype.Service;

/**
 * De default implementatie van de BlokkingInfoVerzoekService.
 */
@Service
public final class BlokkeringInfoVerzoekServiceImpl implements BlokkeringInfoVerzoekService {

    @Inject
    private BrpDalService brpDalService;

    @Override
    public BlokkeringInfoAntwoordBericht verwerkBlokkeringInfoVerzoek(
            final BlokkeringInfoVerzoekBericht blokkeringInfoVerzoekBericht) {

        final BlokkeringInfoAntwoordType blokkeringInfoAntwoordType = new BlokkeringInfoAntwoordType();

        if (blokkeringInfoVerzoekBericht == null) {
            blokkeringInfoAntwoordType.setStatus(StatusType.FOUT);
            blokkeringInfoAntwoordType.setToelichting("Het BlokkeringInfoVerzoekBericht bevat geen inhoud.");
        } else {

            if (blokkeringInfoVerzoekBericht.getANummer() == null) {
                blokkeringInfoAntwoordType.setStatus(StatusType.FOUT);
                blokkeringInfoAntwoordType.setToelichting("Er is geen aNummer opgegeven.");
            }

            final Blokkering opgeslagenBlokkering =
                    brpDalService.vraagOpBlokkering(blokkeringInfoVerzoekBericht.getANummer());

            if (opgeslagenBlokkering == null) {
                blokkeringInfoAntwoordType.setStatus(StatusType.FOUT);
                blokkeringInfoAntwoordType
                        .setToelichting("Er is voor het opgegeven aNummer geen blokkering gevonden.");
            } else {
                blokkeringInfoAntwoordType.setStatus(StatusType.OK);
            }
        }

        final BlokkeringInfoAntwoordBericht blokkeringInfoAntwoordBericht =
                new BlokkeringInfoAntwoordBericht(blokkeringInfoAntwoordType);
        blokkeringInfoAntwoordBericht
                .setCorrelationId(blokkeringInfoVerzoekBericht != null ? blokkeringInfoVerzoekBericht.getMessageId()
                        : null);

        return blokkeringInfoAntwoordBericht;
    }

}

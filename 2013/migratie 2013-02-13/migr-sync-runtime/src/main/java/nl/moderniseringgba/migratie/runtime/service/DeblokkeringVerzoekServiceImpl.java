/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.runtime.service;

import javax.inject.Inject;

import nl.moderniseringgba.isc.esb.message.sync.generated.DeblokkeringAntwoordType;
import nl.moderniseringgba.isc.esb.message.sync.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.sync.impl.DeblokkeringAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.DeblokkeringVerzoekBericht;
import nl.moderniseringgba.migratie.synchronisatie.domein.blokkering.entity.Blokkering;
import nl.moderniseringgba.migratie.synchronisatie.service.BrpDalService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * De default implementatie van de DeblokkeringVerzoekService.
 */
@Service
public final class DeblokkeringVerzoekServiceImpl implements DeblokkeringVerzoekService {

    @Inject
    private BrpDalService brpDalService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public DeblokkeringAntwoordBericht verwerkDeblokkeringVerzoek(
            final DeblokkeringVerzoekBericht deblokkeringVerzoekBericht) {
        final DeblokkeringAntwoordType deblokkeringAntwoordType = new DeblokkeringAntwoordType();

        if (deblokkeringVerzoekBericht == null) {
            deblokkeringAntwoordType.setStatus(StatusType.FOUT);
            deblokkeringAntwoordType.setToelichting("Het BlokkeringInfoVerzoekBericht bevat geen inhoud.");
        } else {

            final Blokkering opgeslagenBlokkering =
                    brpDalService.vraagOpBlokkering(deblokkeringVerzoekBericht.getANummer());

            if (opgeslagenBlokkering == null) {
                deblokkeringAntwoordType.setStatus(StatusType.FOUT);
                deblokkeringAntwoordType.setToelichting("Er is voor het opgegeven aNummer geen blokkering gevonden.");
            } else {
                brpDalService.verwijderBlokkering(opgeslagenBlokkering);
    
                deblokkeringAntwoordType.setStatus(StatusType.OK);
            }
        }

        final DeblokkeringAntwoordBericht deblokkeringAntwoordBericht =
                new DeblokkeringAntwoordBericht(deblokkeringAntwoordType);
        deblokkeringAntwoordBericht.setCorrelationId(deblokkeringVerzoekBericht != null ? deblokkeringVerzoekBericht
                .getMessageId() : null);

        return deblokkeringAntwoordBericht;
    }

}

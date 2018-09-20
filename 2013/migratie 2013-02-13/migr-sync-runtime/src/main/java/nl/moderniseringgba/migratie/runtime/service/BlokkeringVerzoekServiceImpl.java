/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.runtime.service;

import javax.inject.Inject;

import nl.moderniseringgba.isc.esb.message.sync.generated.BlokkeringAntwoordType;
import nl.moderniseringgba.isc.esb.message.sync.generated.PersoonsaanduidingType;
import nl.moderniseringgba.isc.esb.message.sync.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.sync.impl.BlokkeringAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.BlokkeringVerzoekBericht;
import nl.moderniseringgba.migratie.synchronisatie.domein.blokkering.entity.Blokkering;
import nl.moderniseringgba.migratie.synchronisatie.service.BrpDalService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * De default implementatie van de BlokkeringVerzoekService.
 */
@Service
public final class BlokkeringVerzoekServiceImpl implements BlokkeringVerzoekService {

    @Inject
    private BrpDalService brpDalService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BlokkeringAntwoordBericht
            verwerkBlokkeringVerzoek(final BlokkeringVerzoekBericht blokkeringVerzoekBericht) {

        final BlokkeringAntwoordType blokkeringAntwoordType = new BlokkeringAntwoordType();

        if (blokkeringVerzoekBericht == null) {
            blokkeringAntwoordType.setStatus(StatusType.FOUT);
            blokkeringAntwoordType.setToelichting("Het BlokkeringInfoVerzoekBericht bevat geen inhoud.");
        } else if (blokkeringVerzoekBericht.getANummer() == null) {
            blokkeringAntwoordType.setStatus(StatusType.FOUT);
            blokkeringAntwoordType.setToelichting("Het BlokkeringInfoVerzoekBericht bevat geen aNummer.");
        } else {

            final Blokkering controleBlokkering =
                    brpDalService.vraagOpBlokkering(blokkeringVerzoekBericht.getANummer());

            if (controleBlokkering != null) {
                blokkeringAntwoordType.setStatus(StatusType.FOUT);
                blokkeringAntwoordType.setToelichting("Er is voor het opgegeven aNummer al een blokkering gevonden.");
            } else {

                final Blokkering opTeSlaanBlokkering =
                        Blokkering.newInstance(blokkeringVerzoekBericht.getANummer(), Long
                                .valueOf(blokkeringVerzoekBericht.getProcessId()), blokkeringVerzoekBericht
                                .getGemeenteNaar(), blokkeringVerzoekBericht.getGemeenteRegistratie(),
                                blokkeringVerzoekBericht.getPersoonsaanduiding().toString());

                final Blokkering opgeslagenBlokkering = brpDalService.persisteerBlokkering(opTeSlaanBlokkering);

                if (opgeslagenBlokkering == null) {
                    blokkeringAntwoordType.setStatus(StatusType.FOUT);
                    blokkeringAntwoordType.setToelichting("Het opslaan van de blokkering is mislukt.");
                } else {
                    blokkeringAntwoordType.setGemeenteNaar(opgeslagenBlokkering.getGemeenteCodeNaar());
                    blokkeringAntwoordType.setPersoonsaanduiding(PersoonsaanduidingType.valueOf(opgeslagenBlokkering
                            .getPersoonsAanduiding()));
                    blokkeringAntwoordType.setProcessId(opgeslagenBlokkering.getProcessId().toString());
    
                    blokkeringAntwoordType.setStatus(StatusType.OK);
                }
            }
        }

        final BlokkeringAntwoordBericht blokkeringAntwoordBericht =
                new BlokkeringAntwoordBericht(blokkeringAntwoordType);
        blokkeringAntwoordBericht.setCorrelationId(blokkeringVerzoekBericht != null ? blokkeringVerzoekBericht
                .getMessageId() : null);

        return blokkeringAntwoordBericht;
    }
}

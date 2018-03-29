/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service;

import java.math.BigInteger;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.migratiebrp.bericht.model.sync.generated.LeesPartijRegisterAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.PartijRegisterType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.PartijType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.RolType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesPartijRegisterAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesPartijRegisterVerzoekBericht;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService;
import nl.bzk.migratiebrp.synchronisatie.runtime.util.MessageId;
import org.springframework.stereotype.Service;

/**
 * Deze service bied de functionaliteit om het partij register op te halen.
 */
@Service
public class PartijRegisterService implements SynchronisatieBerichtService<LeesPartijRegisterVerzoekBericht, LeesPartijRegisterAntwoordBericht> {

    private final BrpDalService brpDalService;

    /**
     * Constructor.
     * @param brpDalService service om de benodigde gegevens op te halen
     */
    @Inject
    public PartijRegisterService(final BrpDalService brpDalService) {
        this.brpDalService = brpDalService;
    }


    @Override
    public Class<LeesPartijRegisterVerzoekBericht> getVerzoekType() {
        return LeesPartijRegisterVerzoekBericht.class;
    }

    @Override
    public LeesPartijRegisterAntwoordBericht verwerkBericht(final LeesPartijRegisterVerzoekBericht verzoek) {
        final LeesPartijRegisterAntwoordType type = new LeesPartijRegisterAntwoordType();
        type.setPartijRegister(new PartijRegisterType());

        brpDalService.geefAllePartijen()
                .stream()
                .filter(Partij::isGeldig)
                .map(this::maakPartijType)
                .forEach(type.getPartijRegister().getPartij()::add);

        final LeesPartijRegisterAntwoordBericht antwoord = new LeesPartijRegisterAntwoordBericht(type);
        antwoord.setStatus(StatusType.OK);
        antwoord.setMessageId(MessageId.generateSyncMessageId());
        antwoord.setCorrelationId(verzoek.getMessageId());
        return antwoord;
    }

    @Override
    public String getServiceNaam() {
        return this.getClass().getSimpleName();
    }

    private PartijType maakPartijType(Partij partij) {
        final PartijType partijType = new PartijType();
        partij.getGemeenten().stream().findFirst().ifPresent(gemeente -> partijType.setGemeenteCode(gemeente.getCode()));
        partijType.setPartijCode(partij.getCode());

        if (partij.getDatumOvergangNaarBrp() != null) {
            partijType.setDatumBrp(BigInteger.valueOf(partij.getDatumOvergangNaarBrp().longValue()));
        }

        partij.getActueleRollen()
                .stream()
                .map(rol -> RolType.valueOf(rol.toString()))
                .forEach(partijType.getRollen()::add);
        return partijType;
    }
}

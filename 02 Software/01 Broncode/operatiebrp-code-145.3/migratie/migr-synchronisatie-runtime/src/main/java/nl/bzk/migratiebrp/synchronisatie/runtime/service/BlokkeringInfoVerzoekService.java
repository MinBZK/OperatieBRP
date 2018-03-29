/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service;

import javax.inject.Inject;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Blokkering;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.RedenBlokkering;
import nl.bzk.migratiebrp.bericht.model.sync.generated.BlokkeringInfoAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.PersoonsaanduidingType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringInfoAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringInfoVerzoekBericht;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService;
import nl.bzk.migratiebrp.synchronisatie.runtime.util.MessageId;

import org.springframework.stereotype.Service;

/**
 * Deze service biedt functionaliteit voor het opvragen van de blokkeringstatus van persoonslijsten.
 */
@Service
public final class BlokkeringInfoVerzoekService implements SynchronisatieBerichtService<BlokkeringInfoVerzoekBericht, BlokkeringInfoAntwoordBericht> {

    private final BrpDalService brpDalService;

    /**
     * constructor.
     * @param brpDalService
     */
    @Inject
    public BlokkeringInfoVerzoekService(final BrpDalService brpDalService) {
        this.brpDalService = brpDalService;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.synchronisatie.runtime.service.SynchronisatieBerichtService#getVerzoekType()
     */
    @Override
    public Class<BlokkeringInfoVerzoekBericht> getVerzoekType() {
        return BlokkeringInfoVerzoekBericht.class;
    }

    /**
     * Haalt op basis van het a-nummer in het BlokkeringInfoVerzoek bericht de blokkeringstatus op van een persoonslijst
     * en retourneert de gevonden informatie in het antwoordbericht.
     * @param blokkeringInfoVerzoekBericht het blokkeringinfo verzoek met daarin het a-nummer
     * @return het antwoordbericht met daarin informatie omtrent de blokkeringstatus
     */
    @Override
    public BlokkeringInfoAntwoordBericht verwerkBericht(final BlokkeringInfoVerzoekBericht blokkeringInfoVerzoekBericht) {

        final BlokkeringInfoAntwoordType blokkeringInfoAntwoordType = new BlokkeringInfoAntwoordType();

        final Blokkering opgeslagenBlokkering = brpDalService.vraagOpBlokkering(blokkeringInfoVerzoekBericht.getANummer());

        if (opgeslagenBlokkering == null) {
            blokkeringInfoAntwoordType.setStatus(StatusType.OK);
            blokkeringInfoAntwoordType.setPersoonsaanduiding(null);
        } else {
            blokkeringInfoAntwoordType.setStatus(StatusType.OK);
            blokkeringInfoAntwoordType.setProcessId(opgeslagenBlokkering.getProcessId().toString());
            blokkeringInfoAntwoordType.setPersoonsaanduiding(mapPersoonsaanduiding(opgeslagenBlokkering.getRedenBlokkering()));
            blokkeringInfoAntwoordType.setGemeenteNaar(opgeslagenBlokkering.getGemeenteCodeNaar());
        }

        final BlokkeringInfoAntwoordBericht blokkeringInfoAntwoordBericht = new BlokkeringInfoAntwoordBericht(blokkeringInfoAntwoordType);
        blokkeringInfoAntwoordBericht.setMessageId(MessageId.generateSyncMessageId());
        blokkeringInfoAntwoordBericht.setCorrelationId(blokkeringInfoVerzoekBericht.getMessageId());

        return blokkeringInfoAntwoordBericht;
    }

    private PersoonsaanduidingType mapPersoonsaanduiding(final RedenBlokkering redenBlokkering) {
        PersoonsaanduidingType type;

        switch (redenBlokkering) {
            case VERHUIZEND_VAN_LO3_NAAR_BRP:
                type = PersoonsaanduidingType.VERHUIZEND_VAN_LO_3_NAAR_BRP;
                break;
            case VERHUIZEND_VAN_BRP_NAAR_LO3_GBA:
                type = PersoonsaanduidingType.VERHUIZEND_VAN_BRP_NAAR_LO_3_GBA;
                break;
            case VERHUIZEND_VAN_BRP_NAAR_LO3_RNI:
                type = PersoonsaanduidingType.VERHUIZEND_VAN_BRP_NAAR_LO_3_RNI;
                break;
            default:
                type = null;
                break;
        }

        return type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getServiceNaam() {
        return this.getClass().getSimpleName();
    }
}

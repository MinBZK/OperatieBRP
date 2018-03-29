/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service;

import java.sql.Timestamp;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Blokkering;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.RedenBlokkering;
import nl.bzk.migratiebrp.bericht.model.sync.generated.BlokkeringAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.PersoonsaanduidingType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringVerzoekBericht;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService;
import nl.bzk.migratiebrp.synchronisatie.runtime.util.MessageId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Deze service biedt functionaliteit voor het blokkeren van persoonslijsten.
 */
@Service
public final class BlokkeringVerzoekService implements SynchronisatieBerichtService<BlokkeringVerzoekBericht, BlokkeringAntwoordBericht> {

    private final BrpDalService brpDalService;

    /**
     * constructor
     * @param brpDalService
     */
    @Inject
    public BlokkeringVerzoekService(final BrpDalService brpDalService) {
        this.brpDalService = brpDalService;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.synchronisatie.runtime.service.SynchronisatieBerichtService#getVerzoekType()
     */
    @Override
    public Class<BlokkeringVerzoekBericht> getVerzoekType() {
        return BlokkeringVerzoekBericht.class;
    }

    /**
     * Blokkeert op basis van onder andere het a-nummer een PL en retourneert een antwoordbericht met informatie omtrent
     * de blokkering.
     * @param blokkeringVerzoekBericht het blokkering verzoek met daarin de benodigde gegevens voor blokkeren.
     * @return het antwoordbericht met daarin informatie omtrent de blokkering.
     */
    @Override
    @Transactional(value = "syncDalTransactionManager", propagation = Propagation.REQUIRED)
    public BlokkeringAntwoordBericht verwerkBericht(final BlokkeringVerzoekBericht blokkeringVerzoekBericht) {

        final BlokkeringAntwoordType blokkeringAntwoordType = new BlokkeringAntwoordType();

        final Blokkering controleBlokkering = brpDalService.vraagOpBlokkering(blokkeringVerzoekBericht.getANummer());

        if (controleBlokkering != null) {
            blokkeringAntwoordType.setStatus(StatusType.GEBLOKKEERD);
            blokkeringAntwoordType.setGemeenteNaar(controleBlokkering.getGemeenteCodeNaar());
            blokkeringAntwoordType.setPersoonsaanduiding(mapPersoonsaanduiding(controleBlokkering.getRedenBlokkering()));
            blokkeringAntwoordType.setProcessId(controleBlokkering.getProcessId().toString());

        } else {

            final Blokkering opTeSlaanBlokkering =
                    new Blokkering(blokkeringVerzoekBericht.getANummer(), new Timestamp(System.currentTimeMillis()));
            opTeSlaanBlokkering.setProcessId(Long.valueOf(blokkeringVerzoekBericht.getProcessId()));
            opTeSlaanBlokkering.setGemeenteCodeNaar(blokkeringVerzoekBericht.getGemeenteNaar());
            opTeSlaanBlokkering.setRegistratieGemeente(blokkeringVerzoekBericht.getGemeenteRegistratie());
            opTeSlaanBlokkering.setRedenBlokkering(mapRedenBlokkering(blokkeringVerzoekBericht.getPersoonsaanduiding()));

            brpDalService.persisteerBlokkering(opTeSlaanBlokkering);
            blokkeringAntwoordType.setStatus(StatusType.OK);
        }

        final BlokkeringAntwoordBericht blokkeringAntwoordBericht = new BlokkeringAntwoordBericht(blokkeringAntwoordType);
        blokkeringAntwoordBericht.setMessageId(MessageId.generateSyncMessageId());
        blokkeringAntwoordBericht.setCorrelationId(blokkeringVerzoekBericht.getMessageId());

        return blokkeringAntwoordBericht;
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

    private RedenBlokkering mapRedenBlokkering(final PersoonsaanduidingType type) {
        RedenBlokkering redenBlokkering;

        switch (type) {
            case VERHUIZEND_VAN_LO_3_NAAR_BRP:
                redenBlokkering = RedenBlokkering.VERHUIZEND_VAN_LO3_NAAR_BRP;
                break;
            case VERHUIZEND_VAN_BRP_NAAR_LO_3_GBA:
                redenBlokkering = RedenBlokkering.VERHUIZEND_VAN_BRP_NAAR_LO3_GBA;
                break;
            case VERHUIZEND_VAN_BRP_NAAR_LO_3_RNI:
                redenBlokkering = RedenBlokkering.VERHUIZEND_VAN_BRP_NAAR_LO3_RNI;
                break;
            default:
                redenBlokkering = null;
                break;
        }

        return redenBlokkering;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getServiceNaam() {
        return this.getClass().getSimpleName();
    }
}

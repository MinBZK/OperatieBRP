/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Blokkering;
import nl.bzk.migratiebrp.bericht.model.sync.generated.DeblokkeringAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.DeblokkeringAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.DeblokkeringVerzoekBericht;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService;
import nl.bzk.migratiebrp.synchronisatie.runtime.util.MessageId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Deze service biedt functionaliteit voor het blokkeren van persoonslijsten.
 */
@Service
public final class DeblokkeringVerzoekService implements SynchronisatieBerichtService<DeblokkeringVerzoekBericht, DeblokkeringAntwoordBericht> {

    private final BrpDalService brpDalService;

    /**
     * contructor.
     * @param brpDalService
     */
    @Inject
    public DeblokkeringVerzoekService(final BrpDalService brpDalService) {
        this.brpDalService = brpDalService;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.synchronisatie.runtime.service.SynchronisatieBerichtService#getVerzoekType()
     */
    @Override
    public Class<DeblokkeringVerzoekBericht> getVerzoekType() {
        return DeblokkeringVerzoekBericht.class;
    }

    /**
     * Deblokkeert op basis van onder andere het a-nummer een PL en retourneert een antwoordbericht met informatie
     * omtrent de blokkering.
     * @param deblokkeringVerzoekBericht het deblokkering verzoek met daarin de benodigde gegevens voor blokkeren.
     * @return het antwoordbericht met daarin informatie omtrent de blokkering.
     */
    @Override
    @Transactional(value = "syncDalTransactionManager", propagation = Propagation.REQUIRED)
    public DeblokkeringAntwoordBericht verwerkBericht(final DeblokkeringVerzoekBericht deblokkeringVerzoekBericht) {

        if ("".equals(deblokkeringVerzoekBericht.getProcessId())) {
            throw new IllegalStateException("Het proces ID dient gevuld te zijn met een geldig proces ID.");
        }

        final DeblokkeringAntwoordType deblokkeringAntwoordType = new DeblokkeringAntwoordType();

        final Blokkering opgeslagenBlokkering = brpDalService.vraagOpBlokkering(deblokkeringVerzoekBericht.getANummer());

        brpDalService.verwijderBlokkering(opgeslagenBlokkering);

        deblokkeringAntwoordType.setStatus(StatusType.OK);

        final DeblokkeringAntwoordBericht deblokkeringAntwoordBericht = new DeblokkeringAntwoordBericht(deblokkeringAntwoordType);
        deblokkeringAntwoordBericht.setMessageId(MessageId.generateSyncMessageId());
        deblokkeringAntwoordBericht.setCorrelationId(deblokkeringVerzoekBericht.getMessageId());

        return deblokkeringAntwoordBericht;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getServiceNaam() {
        return this.getClass().getSimpleName();
    }

}

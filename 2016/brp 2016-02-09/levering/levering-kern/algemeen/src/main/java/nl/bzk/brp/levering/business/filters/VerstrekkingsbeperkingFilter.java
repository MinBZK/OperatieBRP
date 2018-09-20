/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.filters;

import nl.bzk.brp.business.regels.impl.levering.definitieregels.BRLV0035;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * Filter dat personen wegfiltert die vanwege een verstrekkingsbeperking niet geleverd mogen worden.
 *
 * @brp.bedrijfsregel R1983
 */
@Component
@Order(Ordered.LOWEST_PRECEDENCE)
@Regels(Regel.R1983)
public class VerstrekkingsbeperkingFilter implements LeverenPersoonFilter {

    private static final Logger LOGGER   = LoggerFactory.getLogger();

    private final BRLV0035      brlv0035 = new BRLV0035();

    @Override
    public final boolean
            magLeverenDoorgaan(final PersoonHisVolledig persoon, final Populatie populatie,
            final Leveringinformatie leveringAutorisatie,
            final AdministratieveHandelingModel administratieveHandeling)
    {

        /**
         * FIXME Dit geldt voor: Attendering, Attendering met plaatsen afnemerindicatie, Selectie, Selectie met plaatsen afnemerindicatie
         */
        final Partij partij = leveringAutorisatie.getToegangLeveringsautorisatie().getGeautoriseerde().getPartij();
        final PersoonView persoonView = new PersoonView(persoon);

        final boolean leveringMagDoorgaan = !brlv0035.isErEenGeldigeVerstrekkingsBeperking(persoonView, partij);

        LOGGER.debug("Levering mag doorgaan voor persoon {} op partij {}: {}", persoon.getID(), partij.getCode()
                .getWaarde(), leveringMagDoorgaan);

        return leveringMagDoorgaan;
    }

}

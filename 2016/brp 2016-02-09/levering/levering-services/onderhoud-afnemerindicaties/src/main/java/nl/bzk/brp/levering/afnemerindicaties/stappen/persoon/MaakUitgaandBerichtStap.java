/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.stappen.persoon;

import javax.inject.Inject;

import nl.bzk.brp.levering.afnemerindicaties.service.OnderhoudAfnemerindicatiesBerichtContext;
import nl.bzk.brp.levering.afnemerindicaties.service.OnderhoudAfnemerindicatiesResultaat;
import nl.bzk.brp.levering.business.bericht.MarshallService;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.levering.VolledigBericht;
import nl.bzk.brp.model.synchronisatie.RegistreerAfnemerindicatieBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.webservice.business.stappen.AbstractBerichtVerwerkingStap;

import org.jibx.runtime.JiBXException;
import org.springframework.beans.factory.annotation.Qualifier;


/**
 * De stap waarin het uitgaande bericht wordt gemaakt.
 */
public class MaakUitgaandBerichtStap
        extends
        AbstractBerichtVerwerkingStap<RegistreerAfnemerindicatieBericht, OnderhoudAfnemerindicatiesBerichtContext, OnderhoudAfnemerindicatiesResultaat>
{

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    @Qualifier("levMarshallService")
    private MarshallService     marshallService;

    @Override
    public final boolean voerStapUit(final RegistreerAfnemerindicatieBericht onderwerp,
            final OnderhoudAfnemerindicatiesBerichtContext context,
            final OnderhoudAfnemerindicatiesResultaat resultaat)
    {
        final VolledigBericht bericht = context.getVolledigBericht();

        try {
            final String xmlBercht = marshallService.maakBericht(bericht);
            context.setXmlBericht(xmlBercht);
        } catch (final JiBXException e) {
            LOGGER.error("Fout bij het marshallen van het bericht.", e);

            resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.ALG0001,
                    "Het uitgaande bericht kon niet worden gemaakt"));
            return STOPPEN;
        }

        return DOORGAAN;
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.service;

import nl.bzk.brp.business.stappen.BerichtContext;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;
import nl.bzk.brp.webservice.business.service.BerichtResultaatFactory;
import org.springframework.stereotype.Component;


/**
 * Factory class die {@link nl.bzk.brp.webservice.business.stappen.BerichtVerwerkingsResultaat} instanties creeert op
 * basis van een opgegeven bericht. de bericht resultaat instanties zijn namelijk bericht specifiek.
 */
@Component
public class BerichtResultaatFactoryImpl implements BerichtResultaatFactory {

    @Override
    public final OnderhoudAfnemerindicatiesResultaat creeerBerichtResultaat(final BerichtBericht bericht,
            final BerichtContext berichtContext)
    {
        final OnderhoudAfnemerindicatiesResultaat resultaat = new OnderhoudAfnemerindicatiesResultaat(null);
        resultaat.setTijdstipRegistratie(berichtContext.getTijdstipVerwerking());
        return resultaat;
    }
}

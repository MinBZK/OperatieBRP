/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.brp.factory;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import nl.bzk.migratiebrp.bericht.model.BerichtFactory;
import nl.bzk.migratiebrp.bericht.model.brp.BrpBericht;
import nl.bzk.migratiebrp.bericht.model.brp.generated.MigratievoorzieningRegistreerHuwelijkGeregistreerdPartnerschapBijhouding;
import nl.bzk.migratiebrp.bericht.model.brp.generated.MigratievoorzieningRegistreerHuwelijkGeregistreerdPartnerschapBijhoudingResultaat;
import nl.bzk.migratiebrp.bericht.model.brp.generated.MigratievoorzieningRegistreerNaamGeslachtBijhouding;
import nl.bzk.migratiebrp.bericht.model.brp.generated.MigratievoorzieningRegistreerNaamGeslachtBijhoudingResultaat;
import nl.bzk.migratiebrp.bericht.model.brp.generated.MigratievoorzieningRegistreerOverlijdenBijhouding;
import nl.bzk.migratiebrp.bericht.model.brp.generated.MigratievoorzieningRegistreerOverlijdenBijhoudingResultaat;
import nl.bzk.migratiebrp.bericht.model.brp.impl.OngeldigBericht;
import nl.bzk.migratiebrp.bericht.model.brp.impl.RegistreerHuwelijkGeregistreerdPartnerschapBijhouding;
import nl.bzk.migratiebrp.bericht.model.brp.impl.RegistreerHuwelijkGeregistreerdPartnerschapBijhoudingResultaat;
import nl.bzk.migratiebrp.bericht.model.brp.impl.RegistreerNaamGeslachtBijhouding;
import nl.bzk.migratiebrp.bericht.model.brp.impl.RegistreerNaamGeslachtBijhoudingResultaat;
import nl.bzk.migratiebrp.bericht.model.brp.impl.RegistreerOverlijdenBijhouding;
import nl.bzk.migratiebrp.bericht.model.brp.impl.RegistreerOverlijdenBijhoudingResultaat;
import nl.bzk.migratiebrp.bericht.model.brp.xml.BrpXml;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

/**
 * Vertaal een binnengekomen BRP bericht naar een BRP Bericht object.
 */
public enum BrpBerichtFactory implements BerichtFactory {

    /**
     * Singleton object.
     */
    SINGLETON;

    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Vertaal een binnengekomen BRP bericht naar een ESB BRP Bericht object.
     *
     * @param berichtAlsString
     *            binnengekomen BRP bericht
     * @return ESB BRP Bericht
     */
    @Override
    public BrpBericht getBericht(final String berichtAlsString) {
        try {
            final JAXBElement<?> element = BrpXml.SINGLETON.stringToElement(berichtAlsString);
            return maakBericht(element.getValue());
        } catch (final JAXBException e) {
            LOG.warn("Verwerken bericht mislukt", e);
            return new OngeldigBericht(berichtAlsString, e.getMessage());
        }
    }

    /**
     * Maakt het bericht op basis van de meegegeven value (representatie van het bericht).
     *
     * @param value
     *            De meegegeven value.
     * @return Het bericht.
     */
    private BrpBericht maakBericht(final Object value) {

        final BrpBericht result;

        // BRP berichten
        if (value instanceof MigratievoorzieningRegistreerHuwelijkGeregistreerdPartnerschapBijhouding) {
            result =
                    new RegistreerHuwelijkGeregistreerdPartnerschapBijhouding(
                        (MigratievoorzieningRegistreerHuwelijkGeregistreerdPartnerschapBijhouding) value);
        } else if (value instanceof MigratievoorzieningRegistreerHuwelijkGeregistreerdPartnerschapBijhoudingResultaat) {
            result =
                    new RegistreerHuwelijkGeregistreerdPartnerschapBijhoudingResultaat(
                        (MigratievoorzieningRegistreerHuwelijkGeregistreerdPartnerschapBijhoudingResultaat) value);

        } else if (value instanceof MigratievoorzieningRegistreerNaamGeslachtBijhouding) {
            result = new RegistreerNaamGeslachtBijhouding((MigratievoorzieningRegistreerNaamGeslachtBijhouding) value);
        } else if (value instanceof MigratievoorzieningRegistreerNaamGeslachtBijhoudingResultaat) {
            result = new RegistreerNaamGeslachtBijhoudingResultaat((MigratievoorzieningRegistreerNaamGeslachtBijhoudingResultaat) value);

        } else if (value instanceof MigratievoorzieningRegistreerOverlijdenBijhouding) {
            result = new RegistreerOverlijdenBijhouding((MigratievoorzieningRegistreerOverlijdenBijhouding) value);
        } else if (value instanceof MigratievoorzieningRegistreerOverlijdenBijhoudingResultaat) {
            result = new RegistreerOverlijdenBijhoudingResultaat((MigratievoorzieningRegistreerOverlijdenBijhoudingResultaat) value);

            // ELSE
        } else {
            throw new IllegalArgumentException("Onbekende input kan niet worden vertaald in een BrpBericht. Type: " + value.getClass());
        }
        return result;
    }

}

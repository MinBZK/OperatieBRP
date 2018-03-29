/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.brp.factory;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.BerichtFactory;
import nl.bzk.migratiebrp.bericht.model.brp.BrpBericht;
import nl.bzk.migratiebrp.bericht.model.brp.generated.BijhoudingRegistreerHuwelijkGeregistreerdPartnerschapMigVrz;
import nl.bzk.migratiebrp.bericht.model.brp.generated.BijhoudingResultaatRegistreerHuwelijkGeregistreerdPartnerschapMigVrz;
import nl.bzk.migratiebrp.bericht.model.brp.generated.BijhoudingRegistreerNaamGeslachtMigVrz;
import nl.bzk.migratiebrp.bericht.model.brp.generated.BijhoudingResultaatRegistreerNaamGeslachtMigVrz;
import nl.bzk.migratiebrp.bericht.model.brp.generated.BijhoudingRegistreerOverlijdenMigVrz;
import nl.bzk.migratiebrp.bericht.model.brp.generated.BijhoudingResultaatRegistreerOverlijdenMigVrz;
import nl.bzk.migratiebrp.bericht.model.brp.impl.OngeldigBericht;
import nl.bzk.migratiebrp.bericht.model.brp.impl.RegistreerHuwelijkGeregistreerdPartnerschapBijhouding;
import nl.bzk.migratiebrp.bericht.model.brp.impl.RegistreerHuwelijkGeregistreerdPartnerschapBijhoudingResultaat;
import nl.bzk.migratiebrp.bericht.model.brp.impl.RegistreerNaamGeslachtBijhouding;
import nl.bzk.migratiebrp.bericht.model.brp.impl.RegistreerNaamGeslachtBijhoudingResultaat;
import nl.bzk.migratiebrp.bericht.model.brp.impl.RegistreerOverlijdenBijhouding;
import nl.bzk.migratiebrp.bericht.model.brp.impl.RegistreerOverlijdenBijhoudingResultaat;
import nl.bzk.migratiebrp.bericht.model.brp.xml.BrpXml;

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
     * @param berichtAlsString binnengekomen BRP bericht
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
     * @param value De meegegeven value.
     * @return Het bericht.
     */
    private BrpBericht maakBericht(final Object value) {

        final BrpBericht result;

        // BRP berichten
        if (value instanceof BijhoudingRegistreerHuwelijkGeregistreerdPartnerschapMigVrz) {
            result =
                    new RegistreerHuwelijkGeregistreerdPartnerschapBijhouding(
                            (BijhoudingRegistreerHuwelijkGeregistreerdPartnerschapMigVrz) value);
        } else if (value instanceof BijhoudingResultaatRegistreerHuwelijkGeregistreerdPartnerschapMigVrz) {
            result =
                    new RegistreerHuwelijkGeregistreerdPartnerschapBijhoudingResultaat(
                            (BijhoudingResultaatRegistreerHuwelijkGeregistreerdPartnerschapMigVrz) value);

        } else if (value instanceof BijhoudingRegistreerNaamGeslachtMigVrz) {
            result = new RegistreerNaamGeslachtBijhouding((BijhoudingRegistreerNaamGeslachtMigVrz) value);
        } else if (value instanceof BijhoudingResultaatRegistreerNaamGeslachtMigVrz) {
            result = new RegistreerNaamGeslachtBijhoudingResultaat((BijhoudingResultaatRegistreerNaamGeslachtMigVrz) value);

        } else if (value instanceof BijhoudingRegistreerOverlijdenMigVrz) {
            result = new RegistreerOverlijdenBijhouding((BijhoudingRegistreerOverlijdenMigVrz) value);
        } else if (value instanceof BijhoudingResultaatRegistreerOverlijdenMigVrz) {
            result = new RegistreerOverlijdenBijhoudingResultaat((BijhoudingResultaatRegistreerOverlijdenMigVrz) value);

            // ELSE
        } else {
            throw new IllegalArgumentException("Onbekende input kan niet worden vertaald in een BrpBericht. Type: " + value.getClass());
        }
        return result;
    }

}

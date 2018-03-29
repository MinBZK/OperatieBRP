/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.isc.factory;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.BerichtFactory;
import nl.bzk.migratiebrp.bericht.model.isc.IscBericht;
import nl.bzk.migratiebrp.bericht.model.isc.generated.Uc811Type;
import nl.bzk.migratiebrp.bericht.model.isc.generated.Uc812Type;
import nl.bzk.migratiebrp.bericht.model.isc.impl.OngeldigBericht;
import nl.bzk.migratiebrp.bericht.model.isc.impl.Uc811Bericht;
import nl.bzk.migratiebrp.bericht.model.isc.impl.Uc812Bericht;
import nl.bzk.migratiebrp.bericht.model.isc.xml.IscXml;

/**
 * ISC Bericht factory.
 */
public enum IscBerichtFactory implements BerichtFactory {

    /**
     * We willen een singleton object hebben hiervan.
     */
    SINGLETON;

    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * {@inheritDoc}
     */
    @Override
    public IscBericht getBericht(final String bericht) {
        try {
            final JAXBElement<?> element = IscXml.SINGLETON.stringToElement(bericht);
            return maakBericht(element.getValue());

        } catch (final JAXBException je) {
            LOG.debug("Kon bericht-text niet omzetten naar een Bericht object", je);
            return new OngeldigBericht(bericht, je.getMessage());
        }
    }

    /**
     * Maakt het bericht op basis van de meegegeven value (representatie van het bericht).
     * @param value De meegegeven value.
     * @return Het bericht.
     */
    private IscBericht maakBericht(final Object value) {
        final IscBericht bericht;
        if (value instanceof Uc811Type) {
            bericht = new Uc811Bericht((Uc811Type) value);
        } else if (value instanceof Uc812Type) {
            bericht = new Uc812Bericht((Uc812Type) value);

        } else {
            throw new IllegalArgumentException("Onbekende input kan niet worden vertaald in een ISC Bericht. Type: " + value.getClass());
        }
        return bericht;
    }

}

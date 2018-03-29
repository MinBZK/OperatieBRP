/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.notificatie.factory;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.BerichtFactory;
import nl.bzk.migratiebrp.bericht.model.notificatie.impl.OngeldigBericht;
import nl.bzk.migratiebrp.bericht.model.notificatie.NotificatieBericht;
import nl.bzk.migratiebrp.bericht.model.notificatie.generated.VrijBerichtVerwerkVrijBericht;
import nl.bzk.migratiebrp.bericht.model.notificatie.impl.VrijBerichtNotificatieBericht;
import nl.bzk.migratiebrp.bericht.model.notificatie.xml.NotificatieXml;

/**
 * Vertaal een binnengekomen BRP bericht naar een BRP Bericht object.
 */
public enum NotificatieBerichtFactory implements BerichtFactory {

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
    public NotificatieBericht getBericht(final String berichtAlsString) {
        try {
            final JAXBElement<?> element = NotificatieXml.SINGLETON.stringToElement(berichtAlsString);
            return maakBericht(element.getValue());
        } catch (final JAXBException e) {
            LOG.warn("Verwerken bericht mislukt", e);
            return new OngeldigBericht(berichtAlsString, e.getMessage());
        }
    }

    /**
     * Maakt het bericht op basis van de meegegeven value (representatie van het bericht).
     * @param value De meegegeven value.
     * @return Het notificatie bericht.
     */
    private NotificatieBericht maakBericht(final Object value) {

        final NotificatieBericht result;

        // Notificatie berichten
        if (value instanceof VrijBerichtVerwerkVrijBericht) {
            result = new VrijBerichtNotificatieBericht((VrijBerichtVerwerkVrijBericht) value);
        } else {
            throw new IllegalArgumentException("Onbekende input kan niet worden vertaald in een NotificatieBericht. Type: " + value.getClass());
        }
        return result;
    }

}

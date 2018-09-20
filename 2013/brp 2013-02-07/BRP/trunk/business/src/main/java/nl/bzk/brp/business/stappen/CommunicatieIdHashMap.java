/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen;

import java.util.HashMap;

import nl.bzk.brp.model.basis.Identificeerbaar;
import org.apache.commons.lang.StringUtils;

/**
 * Dit is een hashmap waarin alle identificeerbare objecten in een xml bericht worden bijgehouden
 * aan de hand van een unieke comunicatieID in dat bericht.
 *
 */
@SuppressWarnings("serial")
public class CommunicatieIdHashMap extends HashMap<String, Identificeerbaar> {

    @Override
    public Identificeerbaar get(final Object key) {
        // zorg dat de key lowercase en getrimmed is.
        if (key instanceof String) {
            if (StringUtils.isNotBlank((String) key)) {
                String commID = ((String) key).toLowerCase().trim();
                return super.get(commID);
            }
        }
        return null;
    }

    @Override
    public Identificeerbaar put(final String key, final Identificeerbaar ident) {
        // zorg dat de key lowercase en getrimmed is.
        String commID = ident.getCommunicatieID().toLowerCase().trim();
        if (!containsKey(commID)) {
            super.put(commID, ident);
            return ident;
        }
        return null;
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.vrijbericht;

import nl.bzk.brp.domain.berichtmodel.VrijBerichtAntwoordBericht;
import nl.bzk.brp.service.algemeen.ServiceCallback;

/**
 * De service voor het verwerken van vrij bericht verzoeken.
 */
public interface VrijBerichtVerwerker {

    /**
     * Verwerk een {@link VrijBerichtVerzoek}.
     * @param verzoek het verzoek
     * @param callback callback
     */
    void stuurVrijBericht(VrijBerichtVerzoek verzoek, final ServiceCallback<VrijBerichtAntwoordBericht, String> callback);
}

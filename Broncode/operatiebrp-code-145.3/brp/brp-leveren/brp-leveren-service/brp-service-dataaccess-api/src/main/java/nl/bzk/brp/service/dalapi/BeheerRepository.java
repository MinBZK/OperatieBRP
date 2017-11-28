/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.dalapi;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortVrijBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.VrijBericht;

/**
 * Reposittory voor {@link VrijBericht}.
 */
public interface BeheerRepository {

    /**
     * Slaat een nieuw {@link VrijBericht} op
     * @param vrijBericht het nieuwe {@link VrijBericht}
     */
    void opslaanNieuwVrijBericht(final VrijBericht vrijBericht);

    /**
     * Haalt een {@link SoortVrijBericht} op
     * @param naam van het {@link SoortVrijBericht}
     * @return het soort vrij bericht of null
     */
    SoortVrijBericht haalSoortVrijBerichtOp(final String naam);
}

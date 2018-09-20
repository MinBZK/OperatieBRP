/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;


/**
 * Interface methodes van een groep met materiele historie.
 */
public interface MaterieleHistorie extends FormeleHistorie {

    /**
     * Haalt (een kopie van) de datum aanvang geldigheid op.
     *
     * @return datum
     */
    DatumEvtDeelsOnbekendAttribuut getDatumAanvangGeldigheid();

    /**
     * Haalt (een kopie van) de datum einde geldigheid op.
     *
     * @return datum
     */
    DatumEvtDeelsOnbekendAttribuut getDatumEindeGeldigheid();

}

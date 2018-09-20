/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;


/**
 * De classes die materiele historie in zich heeft.
 */
public interface MaterieleHistorieModel extends MaterieleHistorie, FormeleHistorieModel, Cloneable {

    /**
     * Zet de datum aanvang geldigheid.
     *
     * @param datum Datum
     */
    void setDatumAanvangGeldigheid(DatumEvtDeelsOnbekendAttribuut datum);

    /**
     * Zet de datum einde geldigheid.
     *
     * @param datum Datum
     */
    void setDatumEindeGeldigheid(DatumEvtDeelsOnbekendAttribuut datum);

    /**
     * Clone methode is vereist voor het kunnen aanpassen van de historie. Wordt nog niet ondersteund door alle entiteiten.
     *
     * @return Cloned object.
     */
    MaterieleHistorieModel kopieer();

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;


/**
 * De classes die materiele historie in zich heeft.
 *
 */
public interface MaterieleHistorie extends FormeleHistorie, Cloneable {

    /**
     * Haalt de datum aanvang geldigheid op.
     *
     * @return datum
     */
    Datum getDatumAanvangGeldigheid();

    /**
     * Zet de datum aanvang geldigheid.
     *
     * @param datum Datum
     */
    void setDatumAanvangGeldigheid(Datum datum);

    /**
     * Haalt de datum einde geldigheid op.
     *
     * @return datum
     */
    Datum getDatumEindeGeldigheid();

    /**
     * Zet de datum einde geldigheid.
     *
     * @param datum Datum
     */
    void setDatumEindeGeldigheid(Datum datum);

    /**
     * Haalt de actie aanpassing geldigheid op.
     *
     * @return actie
     */
    ActieModel getActieAanpassingGeldigheid();

    /**
     * Zet de actie aanpassing geldigheid.
     *
     * @param actie ActieMdl
     */
    void setActieAanpassingGeldigheid(ActieModel actie);

    /**
     * Clone methode is vereist voor het kunnen aanpassen van de historie.
     * Wordt nog niet ondersteund door alle entiteiten.
     * @return Cloned object.
     */
    MaterieleHistorie kopieer();

}

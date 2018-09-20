/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;

import nl.bzk.brp.model.RootObject;


/**
 * Kleinste eenheid van gegevensbewerking in de BRP.
 * <p/>
 * Het bijhouden van de BRP geschiedt door het verwerken van administratieve handelingen. Deze administratieve handelingen vallen uiteen in ��n of meer
 * 'eenheden' van gegevensbewerkingen.
 */
public interface Actie extends ActieBasis {

    /**
     * Retourneert het rootobject dat de basis is waarop de actie wordt uitgevoerd. Per actie is er altijd maar één rootobject.
     *
     * @return het rootobject in de actie.
     */
    RootObject getRootObject();

}

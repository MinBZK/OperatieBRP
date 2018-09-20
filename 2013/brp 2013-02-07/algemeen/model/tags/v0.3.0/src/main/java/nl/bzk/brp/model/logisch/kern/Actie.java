/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;

import java.util.List;

import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.logisch.kern.basis.ActieBasis;


/**
 * Eenheid van gegevensbewerking in de BRP.
 *
 * Het bijhouden van de BRP geschiedt door het uitwerken van gegevensbewerkingen op de inhoud van de BRP, c.q. het doen
 * van bijhoudingsacties. De kleinste eenheid van gegevensbewerking is de "BRP actie".
 *
 *
 *
 */
public interface Actie extends ActieBasis {

    /**
     * Retourneert de rootobject die de basis zijn waarop de actie wordt uitgevoerd.
     * @return de rootobject(en) in de actie.
     */
    List<RootObject> getRootObjecten();
}

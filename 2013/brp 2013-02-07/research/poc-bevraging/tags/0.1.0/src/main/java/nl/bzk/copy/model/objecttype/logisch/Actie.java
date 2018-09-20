/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.objecttype.logisch;

import java.util.List;

import nl.bzk.copy.model.RootObject;
import nl.bzk.copy.model.objecttype.logisch.basis.ActieBasis;

/**
 * User Interface voor objecttype actie.
 */
public interface Actie extends ActieBasis {

    /**
     * Retourneert de rootobject die de basis zijn waarop de actie wordt uitgevoerd.
     *
     * @return de rootobject(en) in de actie.
     */
    List<RootObject> getRootObjecten();
}

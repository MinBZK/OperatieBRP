/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model;

import nl.bzk.brp.model.basis.BrpObject;


/**
 * Een rootobject dient als basis om bedrijfsregels uit te kunnen voeren en is het eerste (en enige) object in een actie. Een rootobject is dan ook een
 * subtypering van de standaard {@link BrpObject} is is daarmee slechts een 'tagging/marker' interface.
 */
public interface RootObject extends BrpObject {

}

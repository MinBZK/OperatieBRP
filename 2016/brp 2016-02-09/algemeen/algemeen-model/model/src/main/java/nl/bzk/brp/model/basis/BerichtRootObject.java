/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import nl.bzk.brp.model.RootObject;


/**
 * Interface voor alle root objecten in de 'Bericht' model boom. Deze interface is ook een koppeling tussen de {@link RootObject} interface en de {@link
 * BerichtEntiteit} interface.
 */
public interface BerichtRootObject extends RootObject, BerichtEntiteit {

}

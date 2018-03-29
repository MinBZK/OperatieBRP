/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.dalapi;

import java.util.List;
import java.util.function.Supplier;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;


/**
 * Repository voor Partij.
 */
public interface PartijRepository extends Supplier<List<Partij>> {

}

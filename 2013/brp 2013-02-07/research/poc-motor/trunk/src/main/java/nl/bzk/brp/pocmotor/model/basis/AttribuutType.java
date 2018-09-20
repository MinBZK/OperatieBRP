/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.basis;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Tim
 * Date: 21-12-11
 * Time: 13:17
 * To change this template use File | Settings | File Templates.
 */
public interface AttribuutType<T extends Object> extends Serializable {

    T getWaarde();

}

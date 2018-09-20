/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.logisch.basis;

import nl.bzk.brp.model.attribuuttype.BerichtData;
import nl.bzk.brp.model.attribuuttype.DatumTijd;
import nl.bzk.brp.model.basis.ObjectType;
import nl.bzk.brp.model.objecttype.operationeel.BerichtModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Richting;


/**
 * .
 *
 */
public interface BerichtBasis extends ObjectType {

    /**
     * .
     *
     * @return BerichtData
     */
    BerichtData getData();

    /**
     * .
     *
     * @return DatumTijd
     */
    DatumTijd getDatumTijdOntvangst();

    /**
     * .
     *
     * @return DatumTijd
     */
    DatumTijd getDatumTijdVerzenden();

    /**
     * .
     *
     * @return BerichtModel
     */
    BerichtModel getAntwoordOp();

    /**
     * .
     *
     * @return Richting
     */
    Richting getRichting();
}

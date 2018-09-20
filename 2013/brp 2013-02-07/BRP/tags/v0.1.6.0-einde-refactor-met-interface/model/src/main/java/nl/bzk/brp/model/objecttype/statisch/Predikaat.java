/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.statisch;

import nl.bzk.brp.model.attribuuttype.Naam;
import nl.bzk.brp.model.attribuuttype.PredikaatCode;
import nl.bzk.brp.model.attribuuttype.TechnischIdKlein;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;


/**
 * Wrapper voor de predikaten.
 *
 */
public class Predikaat extends AbstractStatischObjectType {

    /* predikaat objecten hebben een id, code, mannelijke en vrouwelijke aanschrijftitel */
    private TechnischIdKlein id;
    private PredikaatCode    code;
    private Naam             naamMannelijk;
    private Naam             naamVrouwelijk;

    public TechnischIdKlein getId() {
        return id;
    }

    public PredikaatCode getCode() {
        return code;
    }

    public Naam getNaamMannelijk() {
        return naamMannelijk;
    }

    public Naam getNaamVrouwelijk() {
        return naamVrouwelijk;
    }
}

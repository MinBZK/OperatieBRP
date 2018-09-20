/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.impl.gen;

import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.basis.AbstractGroep;
import nl.bzk.brp.model.groep.interfaces.gen.BetrokkenheidOuderlijkGezagGroepBasis;

/**
 * Implementatie voor de groep ouderlijk gezag van objecttype betrokkenheid.
 */
public abstract class AbstractBetrokkenheidOuderlijkGezagGroepMdl extends AbstractGroep implements BetrokkenheidOuderlijkGezagGroepBasis {

    private JaNee indOuderlijkGezag;

    public JaNee getIndOuderlijkGezag() {
        return indOuderlijkGezag;
    }
}

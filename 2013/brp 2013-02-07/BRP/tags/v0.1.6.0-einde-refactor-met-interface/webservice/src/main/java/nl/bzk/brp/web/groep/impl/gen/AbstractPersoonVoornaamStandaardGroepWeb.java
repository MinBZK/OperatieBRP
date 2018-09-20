/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.groep.impl.gen;

import nl.bzk.brp.model.attribuuttype.Voornaam;
import nl.bzk.brp.model.groep.interfaces.gen.PersoonVoornaamStandaardGroepBasis;
import nl.bzk.brp.web.AbstractGroepWeb;

/**
 * Implementatie voor standaard groep van persoon voornaam.
 */
public abstract class AbstractPersoonVoornaamStandaardGroepWeb extends AbstractGroepWeb
    implements PersoonVoornaamStandaardGroepBasis
{

    private Voornaam voornaam;

    @Override
    public Voornaam getVoornaam() {
        return voornaam;
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.web.objecttype.impl.gen;

import nl.bzk.brp.model.objecttype.interfaces.gen.PersoonAdresBasis;
import nl.bzk.brp.model.web.AbstractObjectTypeWeb;
import nl.bzk.brp.model.web.groep.impl.usr.PersoonAdresStandaardGroepWeb;
import nl.bzk.brp.model.web.objecttype.impl.usr.PersoonWeb;


/**
 * Implementatie voor objecttype Persoon adres.
 */
@SuppressWarnings("serial")
public abstract class AbstractPersoonAdresWeb extends AbstractObjectTypeWeb implements PersoonAdresBasis {
    private PersoonWeb persoon;
    private PersoonAdresStandaardGroepWeb gegevens;

    @Override
    public PersoonWeb getPersoon() {
        return persoon;
    }

    @Override
    public PersoonAdresStandaardGroepWeb getGegevens() {
        return gegevens;
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.impl.gen;

import nl.bzk.brp.model.attribuuttype.TechnischIdMiddel;
import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonAdresStandaardGroep;
import nl.bzk.brp.model.objecttype.interfaces.gen.PersoonAdresBasis;
import nl.bzk.brp.model.objecttype.interfaces.usr.Persoon;


/**
 * Implementatie voor objecttype Persoon adres.
 */
public abstract class AbstractPersoonAdresMdl extends AbstractDynamischObjectType implements PersoonAdresBasis {

    private TechnischIdMiddel id;
    private Persoon persoon;
    private PersoonAdresStandaardGroep gegevens;

    public TechnischIdMiddel getId() {
        return id;
    }

    @Override
    public Persoon getPersoon() {
        return persoon;
    }

    @Override
    public PersoonAdresStandaardGroep getGegevens() {
        return gegevens;
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Embeddable;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.logisch.kern.OuderOuderschapGroep;


/**
 * {@inheritDoc}
 */
@Embeddable
public class OuderOuderschapGroepModel extends AbstractOuderOuderschapGroepModel implements OuderOuderschapGroep {

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected OuderOuderschapGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param indicatieOuder indicatieOuder van Ouderschap.
     * @param indicatieOuderUitWieKindIsGeboren
     *                       indicatieOuderUitWieKindIsGeboren van Ouderschap.
     */
    public OuderOuderschapGroepModel(final JaAttribuut indicatieOuder,
        final JaNeeAttribuut indicatieOuderUitWieKindIsGeboren)
    {
        super(indicatieOuder, indicatieOuderUitWieKindIsGeboren);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param ouderOuderschapGroep te kopieren groep.
     */
    public OuderOuderschapGroepModel(final OuderOuderschapGroep ouderOuderschapGroep) {
        super(ouderOuderschapGroep);
    }

}

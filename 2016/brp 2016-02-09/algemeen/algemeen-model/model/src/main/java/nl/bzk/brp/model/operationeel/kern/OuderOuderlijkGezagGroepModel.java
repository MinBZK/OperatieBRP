/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Embeddable;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.logisch.kern.OuderOuderlijkGezagGroep;


/**
 * Gegevens met betrekking tot het ouderlijk gezag.
 */
@Embeddable
public class OuderOuderlijkGezagGroepModel extends AbstractOuderOuderlijkGezagGroepModel implements
    OuderOuderlijkGezagGroep
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected OuderOuderlijkGezagGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param indicatieOuderHeeftGezag indicatieOuderHeeftGezag van Ouderlijk gezag.
     */
    public OuderOuderlijkGezagGroepModel(final JaNeeAttribuut indicatieOuderHeeftGezag) {
        super(indicatieOuderHeeftGezag);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param ouderOuderlijkGezagGroep te kopieren groep.
     */
    public OuderOuderlijkGezagGroepModel(final OuderOuderlijkGezagGroep ouderOuderlijkGezagGroep) {
        super(ouderOuderlijkGezagGroep);
    }

}

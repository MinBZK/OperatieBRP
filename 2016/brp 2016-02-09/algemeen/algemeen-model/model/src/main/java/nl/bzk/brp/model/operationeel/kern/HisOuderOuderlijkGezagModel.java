/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.basis.MaterieleHistorie;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.logisch.kern.HisOuderOuderlijkGezagGroep;
import nl.bzk.brp.model.logisch.kern.OuderOuderlijkGezagGroep;


/**
 *
 *
 */
@Entity
@Table(schema = "Kern", name = "His_OuderOuderlijkGezag")
public class HisOuderOuderlijkGezagModel extends AbstractHisOuderOuderlijkGezagModel implements
    HisOuderOuderlijkGezagGroep
{

    /**
     * Default constructor t.b.v. JPA
     */
    protected HisOuderOuderlijkGezagModel() {
        super();
    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param betrokkenheidHisVolledig instantie van A-laag klasse.
     * @param groep                    groep
     * @param historie                 historie
     * @param actieInhoud              actie inhoud
     */
    public HisOuderOuderlijkGezagModel(final BetrokkenheidHisVolledig betrokkenheidHisVolledig,
        final OuderOuderlijkGezagGroep groep, final MaterieleHistorie historie, final ActieModel actieInhoud)
    {
        super(betrokkenheidHisVolledig, groep, historie, actieInhoud);
    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie instantie van A-laag klasse.
     */
    public HisOuderOuderlijkGezagModel(final AbstractHisOuderOuderlijkGezagModel kopie) {
        super(kopie);
    }

}

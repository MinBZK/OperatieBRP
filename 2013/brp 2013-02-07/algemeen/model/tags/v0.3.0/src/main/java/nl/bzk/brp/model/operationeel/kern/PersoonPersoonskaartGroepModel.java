/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Embeddable;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.logisch.kern.PersoonPersoonskaartGroep;
import nl.bzk.brp.model.operationeel.kern.basis.AbstractPersoonPersoonskaartGroepModel;


/**
 * Vorm van historie: alleen formeel. Immers, een persoonskaart is wel-of-niet geconverteerd, en een persoonskaart
 * 'verhuist' niet mee (c.q.: de plaats van een persoonskaart veranderd in principe niet).
 *
 *
 *
 */
@Embeddable
public class PersoonPersoonskaartGroepModel extends AbstractPersoonPersoonskaartGroepModel implements
        PersoonPersoonskaartGroep
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected PersoonPersoonskaartGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param gemeentePersoonskaart gemeentePersoonskaart van Persoonskaart.
     * @param indicatiePersoonskaartVolledigGeconverteerd indicatiePersoonskaartVolledigGeconverteerd van Persoonskaart.
     */
    public PersoonPersoonskaartGroepModel(final Partij gemeentePersoonskaart,
            final JaNee indicatiePersoonskaartVolledigGeconverteerd)
    {
        super(gemeentePersoonskaart, indicatiePersoonskaartVolledigGeconverteerd);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonPersoonskaartGroep te kopieren groep.
     */
    public PersoonPersoonskaartGroepModel(final PersoonPersoonskaartGroep persoonPersoonskaartGroep) {
        super(persoonPersoonskaartGroep);
    }

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.ist;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3CategorieAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.ist.StapelHisVolledig;

/**
 * HisVolledig klasse voor Stapel.
 *
 */
@Entity
@Table(schema = "IST", name = "Stapel")
public class StapelHisVolledigImpl extends AbstractStapelHisVolledigImpl implements HisVolledigImpl, StapelHisVolledig {

    /**
     * Default contructor voor JPA.
     *
     */
    protected StapelHisVolledigImpl() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param persoon persoon van Stapel.
     * @param categorie categorie van Stapel.
     * @param volgnummer volgnummer van Stapel.
     */
    public StapelHisVolledigImpl(final PersoonHisVolledigImpl persoon, final LO3CategorieAttribuut categorie, final VolgnummerAttribuut volgnummer) {
        super(persoon, categorie, volgnummer);
    }

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.ist;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.ist.StapelVoorkomenHisVolledig;

/**
 * HisVolledig klasse voor Stapel voorkomen.
 *
 */
@Entity
@Table(schema = "IST", name = "StapelVoorkomen")
public class StapelVoorkomenHisVolledigImpl extends AbstractStapelVoorkomenHisVolledigImpl implements HisVolledigImpl,
        StapelVoorkomenHisVolledig
{

    /**
     * Default contructor voor JPA.
     *
     */
    protected StapelVoorkomenHisVolledigImpl() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param stapel stapel van Stapel voorkomen.
     * @param volgnummer volgnummer van Stapel voorkomen.
     */
    public StapelVoorkomenHisVolledigImpl(final StapelHisVolledigImpl stapel, final VolgnummerAttribuut volgnummer) {
        super(stapel, volgnummer);
    }

}

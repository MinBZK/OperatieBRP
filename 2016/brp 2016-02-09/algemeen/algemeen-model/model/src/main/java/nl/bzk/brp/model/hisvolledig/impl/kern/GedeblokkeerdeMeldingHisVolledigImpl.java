/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.MeldingtekstAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RegelAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.GedeblokkeerdeMeldingHisVolledig;

/**
 * HisVolledig klasse voor Gedeblokkeerde melding.
 *
 */
@Entity
@Table(schema = "Kern", name = "GedeblokkeerdeMelding")
public class GedeblokkeerdeMeldingHisVolledigImpl extends AbstractGedeblokkeerdeMeldingHisVolledigImpl implements HisVolledigImpl,
        GedeblokkeerdeMeldingHisVolledig, ElementIdentificeerbaar
{

    /**
     * Default contructor voor JPA.
     *
     */
    protected GedeblokkeerdeMeldingHisVolledigImpl() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param regel regel van Gedeblokkeerde melding.
     * @param melding melding van Gedeblokkeerde melding.
     */
    public GedeblokkeerdeMeldingHisVolledigImpl(final RegelAttribuut regel, final MeldingtekstAttribuut melding) {
        super(regel, melding);
    }

}

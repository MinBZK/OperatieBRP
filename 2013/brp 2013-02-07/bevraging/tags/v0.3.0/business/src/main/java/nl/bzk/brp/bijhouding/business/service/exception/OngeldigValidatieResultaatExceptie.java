/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.service.exception;

import nl.bzk.brp.bijhouding.business.bedrijfsregel.AbstractValidatie;
import nl.bzk.brp.bijhouding.business.bedrijfsregel.ValidatieResultaat;


/**
 * Exceptie die kan optreden als de {@link AbstractValidatie#voerUit()} methode iets anders dan
 * {@link ValidatieResultaat#CONFORM} of {@link ValidatieResultaat#AFWIJKEND} teruggeeft.
 */
public class OngeldigValidatieResultaatExceptie extends RuntimeException {

    /**
     * Constructor.
     *
     * @param validatie de validatie die het resultaat teruggaf
     * @param resultaat het ongeldige resultaat
     */
    public OngeldigValidatieResultaatExceptie(final AbstractValidatie<?> validatie, final ValidatieResultaat resultaat)
    {
        super(String.format("ongeldig resultaat voor %s.voerUit(): %s", validatie.getClass().getName(), resultaat));
    }

}

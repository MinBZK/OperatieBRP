/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie.validatie;

import java.util.List;

import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.validatie.Melding;

/**
 * Valideert de gegevens in een BRPActie. Persoon, Adres etc...
 */
public interface ActieValidator {

    /**
     * Valideert de gegevens in de actie.
     *
     * @param actie De te valideren actie.
     * @return Lijst van validatie meldingen.
     */
    List<Melding> valideerActie(Actie actie);
}

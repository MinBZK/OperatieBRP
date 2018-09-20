/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GeslachtsnaamstamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ScheidingstekenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoorvoegselAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitelAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PredicaatAttribuut;
import nl.bzk.brp.model.basis.Groep;

/**
 * Vorm van historie: beiden. Motivatie: net als bijvoorbeeld de Samengestelde naam kan een individuele
 * geslachtsnaamcomponent (bijv. die met volgnummer 1 voor persoon X) in de loop van de tijd veranderen, dus nog los van
 * eventuele registratiefouten. Er is dus óók sprake van materiële historie.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface PersoonGeslachtsnaamcomponentStandaardGroepBasis extends Groep {

    /**
     * Retourneert Predicaat van Standaard.
     *
     * @return Predicaat.
     */
    PredicaatAttribuut getPredicaat();

    /**
     * Retourneert Adellijke titel van Standaard.
     *
     * @return Adellijke titel.
     */
    AdellijkeTitelAttribuut getAdellijkeTitel();

    /**
     * Retourneert Voorvoegsel van Standaard.
     *
     * @return Voorvoegsel.
     */
    VoorvoegselAttribuut getVoorvoegsel();

    /**
     * Retourneert Scheidingsteken van Standaard.
     *
     * @return Scheidingsteken.
     */
    ScheidingstekenAttribuut getScheidingsteken();

    /**
     * Retourneert Stam van Standaard.
     *
     * @return Stam.
     */
    GeslachtsnaamstamAttribuut getStam();

}

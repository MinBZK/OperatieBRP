/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;

import javax.annotation.Generated;

/**
 * De wijze waarop een Persoon betrokken is bij een Relatie.
 *
 * Er wordt expliciet onderscheid gemaakt tussen de Relatie enerzijds, en de Persoon die in de Relatie betrokken is
 * anderzijds. De koppeling van een Persoon en een Relatie gebeurt via Betrokkenheid.
 *
 * Er zit geen unique constraint (meer) op de Relatie, Persoon combinatie. In een FRB kan het zo zijn dat het kind
 * beëindigd is op de PL van het ouder en de ouder niet op de PL van het kind (of vice versa). Bij migratie worden dan
 * twee ouderbetrekkingen aangemaakt met dezelfde ouder: de één ontkend door de ouder, de ander door het kind. Dit gaat
 * niet met een UC. Dit kan theoretisch ook voor komen bij 'heradoptie', waarbij op de ene PL de FRB is beeindigd en
 * daarna weer is opgenomen en op de andere PL niet is beëindigd, of is beëindigd maar niet weer opnieuw opgenomen.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisMomentGenerator")
public interface BetrokkenheidHisMoment extends Betrokkenheid {

    /**
     * Retourneert Identiteit van Betrokkenheid.
     *
     * @return Identiteit.
     */
    HisBetrokkenheidIdentiteitGroep getIdentiteit();

}

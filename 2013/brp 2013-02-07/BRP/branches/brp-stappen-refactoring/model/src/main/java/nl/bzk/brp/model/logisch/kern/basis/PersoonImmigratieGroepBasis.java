/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Land;
import nl.bzk.brp.model.basis.Groep;


/**
 * Vorm van historie: beiden. Motivatie: je kunt vaker immigreren. Alleen vastleggen van materi�le tijdsaspecten is dus
 * niet voldoende: je moet meerdere (niet overlappende maar wel gaten hebbende) perioden kunnen aanwijzen waarin je
 * 'geimmigreerd bent'. (Logischerwijs is de datum einde geldigheid immigratie gelijk aan de datum ingang emigratie.) In
 * de praktijk zal de datum immigratie 'dicht' bij de datum liggen waarop de immigratie geregistreerd wordt; dit kan
 * echter afwijken. Om die reden materi�le historie vastgelegd NAAST de formele historie.
 *
 * De datum ingang geldigheid komt normaliter overeen met de datum vestiging in Nederland; de laatste is (ook) opgenomen
 * omdat hier vanuit migratie verschillende waarden in kunnen staan.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.LogischModelGenerator.
 * Generator versie: 1.0-SNAPSHOT.
 * Metaregister versie: 1.6.0.
 * Gegenereerd op: Tue Jan 15 12:53:52 CET 2013.
 */
public interface PersoonImmigratieGroepBasis extends Groep {

    /**
     * Retourneert Land vanwaar gevestigd van Immigratie.
     *
     * @return Land vanwaar gevestigd.
     */
    Land getLandVanwaarGevestigd();

    /**
     * Retourneert Datum vestiging in Nederland van Immigratie.
     *
     * @return Datum vestiging in Nederland.
     */
    Datum getDatumVestigingInNederland();

}

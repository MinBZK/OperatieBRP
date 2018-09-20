/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.bericht.kern.basis.AbstractPersoonIndicatieBericht;
import nl.bzk.brp.model.logisch.kern.PersoonIndicatie;


/**
 * Indicaties bij een persoon.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.BerichtModelGenerator.
 * Metaregister versie: 1.1.8.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2012-11-27 12:02:51.
 * Gegenereerd op: Tue Nov 27 13:50:43 CET 2012.
 */
public class PersoonIndicatieBericht extends AbstractPersoonIndicatieBericht implements PersoonIndicatie {

    /**
     * Constructor die het discriminator attribuut zet of doorgeeft.
     *
     * @param soort de waarde van het discriminator attribuut
     */
    public PersoonIndicatieBericht(final SoortIndicatie soort) {
        super(soort);
    }

}

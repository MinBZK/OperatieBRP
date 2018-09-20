/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern.basis;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.bericht.kern.ActieBericht;


/**
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.BerichtModelGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-15 12:43:16.
 * Gegenereerd op: Tue Jan 15 12:53:47 CET 2013.
 */
public abstract class AbstractActieRegistratieVerstrekkingsbeperkingBericht extends ActieBericht {

    /**
     * Default constructor instantieert met de juiste SoortActie.
     *
     */
    public AbstractActieRegistratieVerstrekkingsbeperkingBericht() {
        super(SoortActie.REGISTRATIE_VERSTREKKINGSBEPERKING);
    }

}

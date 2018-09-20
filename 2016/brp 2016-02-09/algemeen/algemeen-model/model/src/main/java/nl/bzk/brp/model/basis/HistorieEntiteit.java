/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Verwerkingssoort;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;


/**
 * Generieke interface voor alle historie entiteiten, His....GroepModel etc..
 */
public interface HistorieEntiteit {

    /**
     * Geef de verwerkingssoort.
     *
     * @return de verwerkingssoort
     */
    Verwerkingssoort getVerwerkingssoort();

    /**
     * Zet de verwerkingssoort.
     *
     * @param verwerkingsSoort de verwerkingssoort
     */
    void setVerwerkingssoort(final Verwerkingssoort verwerkingsSoort);

    /**
     * Geeft aan of deze entiteit geleverd mag worden. Voor groepen kan gedefinieerd worden welke attributen aanwezig dienen te zijn om geleverd te mogen
     * worden. Zo kunnen "lege" groepen gefilterd worden uit berichten.
     *
     * @return of deze entiteit geleverd mag worden
     */
    @Regels(Regel.VR00088)
    @Deprecated // voorlopig behouden om de boel compilerend te houden, wordt gegenereerd met body "return false;"
    boolean isMagGeleverdWorden();

}

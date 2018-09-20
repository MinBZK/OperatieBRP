/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.brm;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;

/**
 * De implementatie van een Regel voor een Soort bericht.
 *
 * Het vertalen van Regels naar software die de regel implementeert, is een releasematig proces. In dat proces wordt er
 * voor elk soort bericht aparte code geschreven dan wel gegenereerd, die de regel voor dát soort bericht implementeert.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator")
public enum RegelSoortBericht {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY(null, null);

    private final Regel regel;
    private final SoortBericht soortBericht;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param regel Regel voor RegelSoortBericht
     * @param soortBericht SoortBericht voor RegelSoortBericht
     */
    private RegelSoortBericht(final Regel regel, final SoortBericht soortBericht) {
        this.regel = regel;
        this.soortBericht = soortBericht;
    }

    /**
     * Retourneert Regel van Regel \ Soort bericht.
     *
     * @return Regel.
     */
    public Regel getRegel() {
        return regel;
    }

    /**
     * Retourneert Soort bericht van Regel \ Soort bericht.
     *
     * @return Soort bericht.
     */
    public SoortBericht getSoortBericht() {
        return soortBericht;
    }

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;


/**
 * Simpel object om 2 attributen van een persoon op te halen. We kunnen geen hibernate gebruiken, omdat we
 * misschien in de weg zitten met de caching. Deze methode willen we gebruiken om een persoon op te halen VOOR de
 * Read/Write Lock stap te doen (dus eigenlijk VOOR onze beurt al gegevens ophalen).
 */
public class PersoonInformatieDto {
    private final Integer      iD;
    private final SoortPersoon soort;

    /**
     * Enige constructor om te vullen.
     *
     * @param iD het iD.
     * @param soort de soort.
     */
    public PersoonInformatieDto(final Integer iD, final SoortPersoon soort) {
        this.iD = iD;
        this.soort = soort;
    }

    /**
     * Retourneert het id van de persoon.
     *
     * @return het id van de persoon.
     */
    public Integer getiD() {
        return iD;
    }

    /**
     * Retourneert de soort persoon.
     *
     * @return de soort van de persoon.
     */
    public SoortPersoon getSoort() {
        return soort;
    }
}

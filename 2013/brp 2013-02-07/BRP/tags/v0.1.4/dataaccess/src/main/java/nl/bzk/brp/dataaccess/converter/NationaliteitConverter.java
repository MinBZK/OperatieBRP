/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.converter;

import nl.bzk.brp.model.gedeeld.Nationaliteit;
import nl.bzk.brp.model.logisch.groep.PersoonNationaliteit;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoonNationaliteit;

/**
 * Converteert objecten van operationeel model naar logisch model. in dit geval gaat het om natonaliteit.
 *
 */

public final class NationaliteitConverter {
    /**
     * default constructor.
     */
    private NationaliteitConverter() {

    }

    /**
     * Converteert een Operationeel nationaliteit model instantie ({@link PersistentPersoonNationaliteit})
     * naar een logisch nationaliteit model instantie ({@link PersoonNationaliteit}).
     *
     * @param persistentPersoonNationaliteit de operationeel model instantie van een nationaliteit.
     * @return de logisch model instantie van de opgegeven nationaliteit (of null als parameter is null).
     */
    public static PersoonNationaliteit converteerOperationeelNaarLogisch(
            final PersistentPersoonNationaliteit persistentPersoonNationaliteit)
    {
        PersoonNationaliteit persoonNationaliteit = null;
        if (persistentPersoonNationaliteit != null) {
            Nationaliteit nationaliteit = new Nationaliteit();
            nationaliteit.setCode(persistentPersoonNationaliteit.getNationaliteit().getCode());
            nationaliteit.setNaam(persistentPersoonNationaliteit.getNationaliteit().getNaam());

            persoonNationaliteit = new PersoonNationaliteit();
            persoonNationaliteit.setNationaliteit(nationaliteit);
        }
        return persoonNationaliteit;
    }

}

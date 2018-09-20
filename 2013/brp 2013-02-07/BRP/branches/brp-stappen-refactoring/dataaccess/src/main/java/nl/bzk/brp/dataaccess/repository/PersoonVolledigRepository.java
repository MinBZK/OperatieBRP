/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.operationeel.kern.PersoonVolledig;

/**
 * Repository voor de {@link nl.bzk.brp.model.operationeel.kern.PersoonVolledig} class.
 */
public interface PersoonVolledigRepository {

    /**
     * Leest een {@link PersoonVolledig} instantie uit de persistence oplossing. Als deze niet bestaat wordt hij
     * gemaakt (uit het genormalizeerde model (Kern.Pers etc.)) en ook direct weggeschreven in de persistencelaag.
     *
     * @param id technische sleutel van de cache
     * @return een instantie van PersoonVolledig
     */
    PersoonVolledig haalPersoonOp(final Integer id);

    /**
     * Leest een {@link PersoonVolledig} instantie uit de persistence oplossing. Als deze niet bestaat wordt hij
     * gemaakt (uit het genormalizeerde model (Kern.Pers etc.)) en ook direct weggeschreven in de persistencelaag.
     *
     * @param persoonModel het model waarvoor de volledige versie opgevraagd wordt
     * @return een instantie van PersoonVolledig
     */
    PersoonVolledig haalPersoonOp(final PersoonModel persoonModel);

    /**
     * Schrijft een {@link PersoonVolledig} als blob weg in de database.
     *
     * @param persoon het model om weg te schrijven
     */
    void opslaanPersoon(final PersoonVolledig persoon);
}

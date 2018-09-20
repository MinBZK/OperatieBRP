/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import java.util.List;

import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;

/**
 * Repository voor de {@link nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig} class.
 */
public interface PersoonHisVolledigRepository {

    /**
     * Leest een {@link PersoonHisVolledig} instantie uit de persistence oplossing. Als deze niet bestaat wordt hij
     * gemaakt (uit het genormalizeerde model (Kern.Pers etc.)) en ook direct weggeschreven in de persistencelaag.
     *
     * @param id technische sleutel van de cache
     * @return een instantie van PersoonHisVolledig
     */
    PersoonHisVolledig haalPersoonOp(final Integer id);

    /**
     * Leest een lijst van {@link PersoonHisVolledig} instanties uit de persistence oplossing. Als een of meerdere hiervan
     * niet bestaan worden ze gemaakt (uit het genormalizeerde model (Kern.Pers etc.)) en ook direct weggeschreven in
     * de persistencelaag.
     * @param ids lijst van technische sleutels van de cache
     * @return een lijst van instanties van PersoonHisVolledig
     */
    List<PersoonHisVolledig> haalPersonenOp(final List<Integer> ids);

    /**
     * Leest een {@link PersoonHisVolledig} instantie uit de persistence oplossing. Als deze niet bestaat wordt hij
     * gemaakt (uit het genormalizeerde model (Kern.Pers etc.)) en ook direct weggeschreven in de persistencelaag.
     *
     * @param persoonModel het model waarvoor de volledige versie opgevraagd wordt
     * @return een instantie van PersoonHisVolledig
     */
    PersoonHisVolledig haalPersoonOp(final PersoonModel persoonModel);

    /**
     * Schrijft een {@link PersoonHisVolledig} als blob weg in de database.
     *
     * @param persoon het model om weg te schrijven
     */
    void opslaanPersoon(final PersoonHisVolledig persoon);
}

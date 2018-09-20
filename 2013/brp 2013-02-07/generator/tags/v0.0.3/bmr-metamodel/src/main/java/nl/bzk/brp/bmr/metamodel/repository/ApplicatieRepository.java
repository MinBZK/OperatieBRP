/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.metamodel.repository;

import java.util.List;

import nl.bzk.brp.bmr.metamodel.NamedModelElement;
import nl.bzk.brp.bmr.metamodel.ui.Applicatie;


/**
 * Abstracte repository interface voor alle metamodel element repositories.
 */
public interface ApplicatieRepository extends ModelRepository {

    /**
     * {@inheritDoc}
     */
    @Override
    NamedModelElement findByNaam(final String naam);

    List<Applicatie> findAll();
}

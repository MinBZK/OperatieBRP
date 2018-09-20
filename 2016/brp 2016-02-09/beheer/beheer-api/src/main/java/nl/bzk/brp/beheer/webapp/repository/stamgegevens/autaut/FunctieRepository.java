/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.repository.stamgegevens.autaut;

import nl.bzk.brp.beheer.webapp.repository.stamgegevens.EnumReadonlyRepository;

import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Functie;
import org.springframework.stereotype.Repository;

/**
 * Functie repository.
 */
@Repository
public class FunctieRepository extends EnumReadonlyRepository<Functie> {

    /**
     * Constructor.
     */
    public FunctieRepository() {
        super(Functie.class);
    }
}

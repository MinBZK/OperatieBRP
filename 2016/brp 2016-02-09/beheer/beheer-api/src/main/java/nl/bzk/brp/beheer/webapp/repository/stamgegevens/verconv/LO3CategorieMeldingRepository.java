/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.repository.stamgegevens.verconv;

import nl.bzk.brp.beheer.webapp.repository.stamgegevens.EnumReadonlyRepository;

import nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3CategorieMelding;
import org.springframework.stereotype.Repository;

/**
 * LO3CategorieMelding repository.
 */
@Repository
public class LO3CategorieMeldingRepository extends EnumReadonlyRepository<LO3CategorieMelding> {

    /**
     * Constructor.
     */
    public LO3CategorieMeldingRepository() {
        super(LO3CategorieMelding.class);
    }
}

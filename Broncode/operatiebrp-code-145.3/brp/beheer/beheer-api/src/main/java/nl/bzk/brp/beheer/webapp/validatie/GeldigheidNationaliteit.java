/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.validatie;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Nationaliteit;
import nl.bzk.brp.beheer.webapp.repository.ReadonlyRepository;
import org.springframework.stereotype.Component;

/**
 * Valideert de geldigheid van een Nationaliteit.
 */
@Component
public class GeldigheidNationaliteit extends GenericGeldigheidStamgegevenValidator<Nationaliteit, Integer> {

    /**
     * Constructor.
     * @param repository repository van Nationaliteit
     */
    @Inject
    public GeldigheidNationaliteit(final ReadonlyRepository<Nationaliteit, Integer> repository) {
        super(Nationaliteit.class, repository);
    }
}

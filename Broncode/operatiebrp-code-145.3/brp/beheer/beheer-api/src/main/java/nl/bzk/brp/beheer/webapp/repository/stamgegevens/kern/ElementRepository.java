/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.repository.stamgegevens.kern;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.ElementEnumReadonlyRepository;

import org.springframework.stereotype.Repository;

/**
 * Element repository.
 */
@Repository
public class ElementRepository extends ElementEnumReadonlyRepository<Element> {

    /**
     * Constructor.
     */
    public ElementRepository() {
        super(Element.class);
    }

}

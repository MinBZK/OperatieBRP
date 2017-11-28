/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.repository.stamgegevens.conv;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortNlReisdocument;
import nl.bzk.brp.beheer.webapp.configuratie.annotations.Master;
import nl.bzk.brp.beheer.webapp.repository.ReadWriteRepository;

/**
 * Conversie soort NL reisdocument repository.
 */
@Master
public interface ConversieSoortNLReisdocumentRepository extends ReadWriteRepository<SoortNlReisdocument, Integer> {

}

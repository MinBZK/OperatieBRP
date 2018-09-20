/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto.bevraging;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import nl.bzk.brp.business.dto.AbstractBRPBericht;
import nl.bzk.brp.business.dto.BRPBericht;
import org.apache.commons.lang.StringUtils;

/**
 * Abstracte klasse voor elk vraag bericht aan de BRP.
 */
public abstract class AbstractBevragingsBericht extends AbstractBRPBericht implements BRPBericht {

    /**
     * Retourneert de vraag die gesteld wordt in het BRP bevragingsbericht.
     * @return De vraag.
     */
    public abstract AbstractVraag getVraag();

    @Override
    public Collection<String> getReadBsnLocks() {
        Collection<String> resultaat = new ArrayList<String>();

        if (getVraag() != null) {
            String bsn = getVraag().getBurgerServiceNummerForLocks();
            if (StringUtils.isNotBlank(bsn)) {
                resultaat.add(bsn);
            }
        }
        return resultaat;
    }

    @Override
    public Collection<String> getWriteBsnLocks() {
        return Collections.emptyList();
    }

}

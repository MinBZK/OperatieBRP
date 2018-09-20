/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.bericht;

import java.util.Collection;

import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.objecttype.bericht.basis.AbstractBerichtBericht;
import nl.bzk.brp.model.objecttype.logisch.Bericht;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortbericht;
import nl.bzk.brp.util.AttribuutTypeUtil;
import org.apache.commons.lang.StringUtils;

/**
 *.
 */
public abstract class BerichtBericht extends AbstractBerichtBericht implements Bericht, Identificeerbaar {

    /**
     * BSN's betrokken in het Verzoek t.b.v. applicatief READ locking
     *
     * @return Collectie van BSN's waarvoor het verzoek een read lock nodig heeft.
     */
    public abstract Collection<String> getReadBsnLocks();

    /**
     * BSN's betrokken in het Verzoek t.b.v. applicatief WRITE locking
     *
     * @return Collectie van BSN's waarvoor het verzoek een write lock nodig heeft.
     */
    public abstract Collection<String> getWriteBsnLocks();

    /**
     * Retourneert het soort bericht.
     * @return Soort bericht.
     */
    public abstract Soortbericht getSoortBericht();

    /**
     * Retourneert de partij die dit bericht heeft ingestuurd.
     *
     * @return De verzendende partijid van het bericht.
     */
    public final Short getPartijId() {
        Short partijId = null;
        if (getBerichtStuurgegevensGroep() != null
            && AttribuutTypeUtil.isNotBlank(getBerichtStuurgegevensGroep().getOrganisatie())
            && StringUtils.isNumeric(getBerichtStuurgegevensGroep().getOrganisatie().getWaarde()))
        {
            partijId = Short.valueOf(getBerichtStuurgegevensGroep().getOrganisatie().getWaarde());
        }
        return partijId;
    }

}

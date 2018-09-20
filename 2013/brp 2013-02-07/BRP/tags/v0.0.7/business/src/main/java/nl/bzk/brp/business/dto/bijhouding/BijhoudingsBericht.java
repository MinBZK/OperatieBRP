/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto.bijhouding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import nl.bzk.brp.business.dto.BRPBericht;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.logisch.BRPActie;
import nl.bzk.brp.model.logisch.Persoon;

/** Model class voor het xsd type BRPbericht. */
public class BijhoudingsBericht implements BRPBericht {

    private List<BRPActie> brpActies;
    private Integer afzenderId = null;

    public List<BRPActie> getBrpActies() {
        return brpActies;
    }

    public void setBrpActies(final List<BRPActie> brpActies) {
        this.brpActies = brpActies;
    }

    /**
     * {@inheritDoc}
     * <p/>
     * Standaard wordt de partijId van de eerste actie geretourneerd.
     */
    @Override
    public Integer getPartijId() {
        Integer resultaat;
        if (brpActies != null && !brpActies.isEmpty() && brpActies.get(0).getPartij() != null) {
            resultaat = brpActies.get(0).getPartij().getId();
        } else {
            resultaat = afzenderId;
        }
        return resultaat;
    }

    public Integer getAfzenderId() {
        return afzenderId;
    }

    public void setAfzenderId(final Integer afzenderId) {
        this.afzenderId = afzenderId;
    }

    /** {@inheritDoc} */
    @Override
    public Collection<String> getReadBsnLocks() {
        return Collections.emptyList();
    }

    /** {@inheritDoc} */
    @Override
    public Collection<String> getWriteBsnLocks() {
        Collection<String> resultaat = new ArrayList<String>();
        for (BRPActie actie : getBrpActies()) {
            for (RootObject object : actie.getRootObjecten()) {
                if (object instanceof Persoon) {
                    Persoon persoon = (Persoon) object;
                    resultaat.add(persoon.getIdentificatienummers().getBurgerservicenummer());
                }
            }
        }
        return resultaat;
    }
}

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

import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.util.AttribuutTypeUtil;


/** Model class voor het xsd type BRPbericht. */
public abstract class AbstractBijhoudingsBericht extends BerichtBericht {

    /** default constructor. */
    protected AbstractBijhoudingsBericht(final SoortBericht soort) {
        super(soort);
    }

    /**
     * Retourneert de lijst van BRP acties.
     *
     * @deprecated gebruik getAdimistratieveHandeling().getActies();
     * @return Lijst van acties.
     */
    @Deprecated
    public List<ActieBericht> getBrpActies() {
        if (null != getAdministratieveHandeling()) {
            return getAdministratieveHandeling().getActies();
        } else {
            return null;
        }
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
        if (getBrpActies() != null) {
            for (Actie actie : getBrpActies()) {
                voegWriteBsnLocksVoorActieToeAanResultaat(actie, resultaat);
            }
        }
        return resultaat;
    }

    /**
     * Voegt de BSNs toe aan de opgegeven collectie van bsns, waarvoor voor opgegeven actie een 'write lock' nodig is.
     *
     * @param actie de actie waarvoor bepaald dient te worden welke BSNs gelockt moeten worden.
     * @param bsns een lijst van BSNs die gelockt dienen te worden.
     */
    private void voegWriteBsnLocksVoorActieToeAanResultaat(final Actie actie, final Collection<String> bsns) {
        if (actie.getRootObjecten() != null && !actie.getRootObjecten().isEmpty()) {
            for (RootObject object : actie.getRootObjecten()) {
                if (object instanceof PersoonBericht) {
                    PersoonBericht persoon = (PersoonBericht) object;
                    if (persoon.getIdentificatienummers() != null
                        && AttribuutTypeUtil.isNotBlank(persoon.getIdentificatienummers().getBurgerservicenummer()))
                    {
                        bsns.add(persoon.getIdentificatienummers().getBurgerservicenummer().toString());
                    }
                }
            }
        }
    }

}

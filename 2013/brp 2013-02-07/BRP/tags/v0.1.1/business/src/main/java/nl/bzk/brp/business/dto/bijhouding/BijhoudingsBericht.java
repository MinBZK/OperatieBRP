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

import nl.bzk.brp.business.dto.AbstractBRPBericht;
import nl.bzk.brp.business.dto.BRPBericht;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.logisch.BRPActie;
import nl.bzk.brp.model.logisch.Persoon;
import org.apache.commons.lang.StringUtils;


/** Model class voor het xsd type BRPbericht. */
public class BijhoudingsBericht extends AbstractBRPBericht implements BRPBericht {

    private List<BRPActie> brpActies;

    /** default constructor. */
    public BijhoudingsBericht() {
        // bolie:sorry, deze default constructor is alleen maar om de private methode getActie() aan te roepen.
        // deze moest bestaan voor Jibx, maar PDM klaagt dat deze nooit gebruikt wordt.
        // vandaar.
        getActie();

    }

    public List<BRPActie> getBrpActies() {
        return brpActies;
    }

    public void setBrpActies(final List<BRPActie> brpActies) {
        this.brpActies = brpActies;
    }

    /**
     * Voegt een actie toe aan de lijst van BRP acties.
     *
     * @param actie De toe te voegen actie.
     */
    public void voegActieToe(final BRPActie actie) {
        if (actie != null) {
            if (brpActies == null) {
                brpActies = new ArrayList<BRPActie>();
            }
            brpActies.add(actie);
        }
    }

    /**
     * Methode puur voor input binding. Zal dus nooit aangeroepen worden.
     * Wordt helaas vereist door Jibx.
     *
     * @return null
     */
    private BRPActie getActie() {
        return null;
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
            for (BRPActie actie : getBrpActies()) {
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
    private void voegWriteBsnLocksVoorActieToeAanResultaat(final BRPActie actie, final Collection<String> bsns) {
        if (actie.getRootObjecten() != null && !actie.getRootObjecten().isEmpty()) {
            for (RootObject object : actie.getRootObjecten()) {
                if (object instanceof Persoon) {
                    Persoon persoon = (Persoon) object;
                    if (persoon.getIdentificatienummers() != null
                        && StringUtils.isNotBlank(persoon.getIdentificatienummers().getBurgerservicenummer()))
                    {
                        bsns.add(persoon.getIdentificatienummers().getBurgerservicenummer());
                    }
                }
            }
        }
    }
}

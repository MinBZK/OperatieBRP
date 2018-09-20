/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domein.lev.persistent;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.domein.PersistentDomeinObjectFactory;
import nl.bzk.brp.domein.ber.SoortBericht;
import nl.bzk.brp.domein.lev.Abonnement;
import nl.bzk.brp.domein.lev.AbonnementSoortBericht;
import nl.bzk.brp.domein.lev.persistent.basis.AbstractPersistentAbonnement;


@Entity
@Table(name = "Abonnement", schema = "Lev")
public class PersistentAbonnement extends AbstractPersistentAbonnement implements Abonnement {

    /**
     * Voegt een {@link SoortBericht} toe aan het abonnement, waardoor de partij behorend bij dit abonnement gerechtigd
     * is dat soort berichten te versturen.
     *
     * @param soortBericht het soort bericht waarvoor dit abonnement geldt.
     */
    @Override
    public void voegSoortBerichtToe(final SoortBericht soortBericht) {
        if (soortBericht != null) {
            AbonnementSoortBericht abonnementSoortBericht =
                new PersistentDomeinObjectFactory().createAbonnementSoortBericht();
            abonnementSoortBericht.setSoortBericht(soortBericht);
            addAbonnementSoortBericht(abonnementSoortBericht);
        }
    }
}

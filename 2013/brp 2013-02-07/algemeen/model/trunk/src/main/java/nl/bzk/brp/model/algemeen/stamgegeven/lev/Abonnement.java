/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.lev;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.algemeen.attribuuttype.autaut.Populatiecriterium;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Doelbinding;
import nl.bzk.brp.model.algemeen.stamgegeven.lev.basis.AbstractAbonnement;


/**
 * Een overeenkomst tussen Afnemers van de BRP enerzijds, en de eigenaar van de BRP anderzijds, op basis waarvan de
 * Afnemer persoonsgegevens vanuit de BRP mag ontvangen.
 *
 * Een Abonnement is uiteindelijk gebaseerd op een Autorisatiebesluit, die bepaalt dat de Afnemer gegevens mag
 * ontvangen. Gegevens over de wijze waarop de Afnemer deze gegevens mag ontvangen, welke persoonsgegevens het precies
 * betreft (welk deel van de populatie maar ook wel deel van de gegevens) wordt vastgelegd bij het Abonnement.
 *
 *
 *
 */
@Entity
@Table(schema = "Lev", name = "Abonnement")
public class Abonnement extends AbstractAbonnement {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected Abonnement() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param doelbinding doelbinding van Abonnement.
     * @param soortAbonnement soortAbonnement van Abonnement.
     * @param populatiecriterium populatiecriterium van Abonnement.
     * @param abonnementStatusHis abonnementStatusHis van Abonnement.
     */
    protected Abonnement(final Doelbinding doelbinding, final SoortAbonnement soortAbonnement,
            final Populatiecriterium populatiecriterium, final StatusHistorie abonnementStatusHis)
    {
        super(doelbinding, soortAbonnement, populatiecriterium, abonnementStatusHis);
    }

}

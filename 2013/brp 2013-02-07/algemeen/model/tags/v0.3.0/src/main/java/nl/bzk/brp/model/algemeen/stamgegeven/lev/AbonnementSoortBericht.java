/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.lev;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.lev.basis.AbstractAbonnementSoortBericht;


/**
 * Toegestaan berichtsoort in kader van een Abonnement.
 *
 * Door middel van het opsommen van de Soorten bericht die mogelijk zijn bij een Abonnement, wordt invulling gegeven aan
 * de behoefte om de relevante services die worden gestart naar aanleiding van een Abonnement.
 *
 *
 *
 */
@Entity
@Table(schema = "Lev", name = "AbonnementSrtBer")
public class AbonnementSoortBericht extends AbstractAbonnementSoortBericht {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbonnementSoortBericht() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param abonnement abonnement van AbonnementSoortBericht.
     * @param soortBericht soortBericht van AbonnementSoortBericht.
     * @param abonnementSoortBerichtStatusHis abonnementSoortBerichtStatusHis van AbonnementSoortBericht.
     */
    protected AbonnementSoortBericht(final Abonnement abonnement, final SoortBericht soortBericht,
            final StatusHistorie abonnementSoortBerichtStatusHis)
    {
        super(abonnement, soortBericht, abonnementSoortBerichtStatusHis);
    }

}

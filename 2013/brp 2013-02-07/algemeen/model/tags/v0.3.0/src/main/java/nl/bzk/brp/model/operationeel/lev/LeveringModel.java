/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.lev;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Authenticatiemiddel;
import nl.bzk.brp.model.algemeen.stamgegeven.lev.Abonnement;
import nl.bzk.brp.model.algemeen.stamgegeven.lev.SoortLevering;
import nl.bzk.brp.model.logisch.lev.Levering;
import nl.bzk.brp.model.operationeel.ber.BerichtModel;
import nl.bzk.brp.model.operationeel.lev.basis.AbstractLeveringModel;


/**
 * De (voorgenomen) Levering van persoonsgegevens aan een Afnemer.
 *
 * Een Afnemer krijgt gegevens doordat er sprake is van een Abonnement. Vlak voordat de gegevens daadwerkelijk
 * afgeleverd gaan worden, wordt dit geprotocolleerd door een regel weg te schrijven in de Levering tabel.
 *
 *
 *
 */
@Entity
@Table(schema = "Lev", name = "Lev")
public class LeveringModel extends AbstractLeveringModel implements Levering {

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected LeveringModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param soort soort van Levering.
     * @param authenticatiemiddel authenticatiemiddel van Levering.
     * @param abonnement abonnement van Levering.
     * @param datumTijdBeschouwing datumTijdBeschouwing van Levering.
     * @param datumTijdKlaarzettenLevering datumTijdKlaarzettenLevering van Levering.
     * @param gebaseerdOp gebaseerdOp van Levering.
     */
    public LeveringModel(final SoortLevering soort, final Authenticatiemiddel authenticatiemiddel,
            final Abonnement abonnement, final DatumTijd datumTijdBeschouwing,
            final DatumTijd datumTijdKlaarzettenLevering, final BerichtModel gebaseerdOp)
    {
        super(soort, authenticatiemiddel, abonnement, datumTijdBeschouwing, datumTijdKlaarzettenLevering, gebaseerdOp);
    }

}

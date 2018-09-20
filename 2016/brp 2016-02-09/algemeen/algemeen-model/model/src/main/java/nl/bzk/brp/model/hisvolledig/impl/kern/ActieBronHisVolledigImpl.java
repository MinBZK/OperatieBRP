/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.kern;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RechtsgrondAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.ActieBronHisVolledig;

/**
 * HisVolledig klasse voor Actie \ Bron.
 *
 */
@Entity
@Table(schema = "Kern", name = "ActieBron")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "iD", scope = ActieBronHisVolledigImpl.class)
public class ActieBronHisVolledigImpl extends AbstractActieBronHisVolledigImpl implements HisVolledigImpl,
        ActieBronHisVolledig, ElementIdentificeerbaar
{

    /**
     * Default contructor voor JPA.
     *
     */
    protected ActieBronHisVolledigImpl() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param actie actie van Actie \ Bron.
     * @param document document van Actie \ Bron.
     * @param rechtsgrond rechtsgrond van Actie \ Bron.
     * @param rechtsgrondomschrijving rechtsgrondomschrijving van Actie \ Bron.
     */
    public ActieBronHisVolledigImpl(
        final ActieHisVolledigImpl actie,
        final DocumentHisVolledigImpl document,
        final RechtsgrondAttribuut rechtsgrond,
        final OmschrijvingEnumeratiewaardeAttribuut rechtsgrondomschrijving)
    {
        super(actie, document, rechtsgrond, rechtsgrondomschrijving);
    }

}

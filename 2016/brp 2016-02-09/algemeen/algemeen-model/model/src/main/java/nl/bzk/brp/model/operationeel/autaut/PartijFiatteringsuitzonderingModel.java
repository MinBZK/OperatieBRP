/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.autaut;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.logisch.autaut.PartijFiatteringsuitzondering;

/**
 * Bijhoudingsvoorstellen waarvan de Partij niet wil dat deze automatisch gefiatteerd worden.
 *
 * Fiatteringsuitzonderingen mogen alleen gedefinieerd zijn als de Partij (in principe) automatisch fiatteert
 * (A:"Partij.Automatisch fiatteren?" = Ja). Alle bijhoudingen van andere bijhouders die voldoen aan een criterium in
 * dit Objecttype zijn dan uitzonderingen op het automatisch fiatteren en zal deze Partij handmatig fiatteren.
 *
 *
 *
 */
@Entity
@Table(schema = "AutAut", name = "PartijFiatuitz")
@Access(value = AccessType.FIELD)
public class PartijFiatteringsuitzonderingModel extends AbstractPartijFiatteringsuitzonderingModel implements PartijFiatteringsuitzondering,
        ModelIdentificeerbaar<Integer>
{

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected PartijFiatteringsuitzonderingModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param partij partij van Partij \ Fiatteringsuitzondering.
     */
    public PartijFiatteringsuitzonderingModel(final PartijAttribuut partij) {
        super(partij);
    }

}

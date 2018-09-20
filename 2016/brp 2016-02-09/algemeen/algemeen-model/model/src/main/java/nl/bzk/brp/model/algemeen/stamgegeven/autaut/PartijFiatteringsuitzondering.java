/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocument;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

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
@Table(schema = "AutAut", name = "PartijFiatuitz")
@Access(value = AccessType.FIELD)
@Entity
@Immutable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class PartijFiatteringsuitzondering extends AbstractPartijFiatteringsuitzondering {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected PartijFiatteringsuitzondering() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param partij partij van PartijFiatteringsuitzondering.
     * @param datumIngang datumIngang van PartijFiatteringsuitzondering.
     * @param datumEinde datumEinde van PartijFiatteringsuitzondering.
     * @param partijBijhoudingsvoorstel partijBijhoudingsvoorstel van PartijFiatteringsuitzondering.
     * @param soortDocument soortDocument van PartijFiatteringsuitzondering.
     * @param soortAdministratieveHandeling soortAdministratieveHandeling van PartijFiatteringsuitzondering.
     * @param indicatieGeblokkeerd indicatieGeblokkeerd van PartijFiatteringsuitzondering.
     */
    protected PartijFiatteringsuitzondering(
        final Partij partij,
        final DatumAttribuut datumIngang,
        final DatumAttribuut datumEinde,
        final Partij partijBijhoudingsvoorstel,
        final SoortDocument soortDocument,
        final SoortAdministratieveHandeling soortAdministratieveHandeling,
        final JaAttribuut indicatieGeblokkeerd)
    {
        super(partij, datumIngang, datumEinde, partijBijhoudingsvoorstel, soortDocument, soortAdministratieveHandeling, indicatieGeblokkeerd);
    }

}

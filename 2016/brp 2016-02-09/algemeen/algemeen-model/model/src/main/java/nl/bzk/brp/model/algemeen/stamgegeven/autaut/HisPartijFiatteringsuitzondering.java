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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocument;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.operationeel.kern.ActieModel;

/**
 *
 *
 */
@Entity
@Table(schema = "AutAut", name = "His_PartijFiatuitz")
@Access(value = AccessType.FIELD)
public class HisPartijFiatteringsuitzondering extends AbstractHisPartijFiatteringsuitzondering implements ModelIdentificeerbaar<Integer> {

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected HisPartijFiatteringsuitzondering() {
        super();
    }

    /**
     * Constructor voor het initialiseren van alle attributen. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param partijFiatteringsuitzondering partijFiatteringsuitzondering van HisPartijFiatteringsuitzondering
     * @param datumIngang datumIngang van HisPartijFiatteringsuitzondering
     * @param datumEinde datumEinde van HisPartijFiatteringsuitzondering
     * @param partijBijhoudingsvoorstel partijBijhoudingsvoorstel van HisPartijFiatteringsuitzondering
     * @param soortDocument soortDocument van HisPartijFiatteringsuitzondering
     * @param soortAdministratieveHandeling soortAdministratieveHandeling van HisPartijFiatteringsuitzondering
     * @param indicatieGeblokkeerd indicatieGeblokkeerd van HisPartijFiatteringsuitzondering
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     */
    public HisPartijFiatteringsuitzondering(
        final PartijFiatteringsuitzondering partijFiatteringsuitzondering,
        final DatumAttribuut datumIngang,
        final DatumAttribuut datumEinde,
        final Partij partijBijhoudingsvoorstel,
        final SoortDocument soortDocument,
        final SoortAdministratieveHandelingAttribuut soortAdministratieveHandeling,
        final JaAttribuut indicatieGeblokkeerd,
        final ActieModel actieInhoud)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        super(partijFiatteringsuitzondering,
              datumIngang,
              datumEinde,
              partijBijhoudingsvoorstel,
              soortDocument,
              soortAdministratieveHandeling,
              indicatieGeblokkeerd,
              actieInhoud);
    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public HisPartijFiatteringsuitzondering(final AbstractHisPartijFiatteringsuitzondering kopie) {
        super(kopie);
    }

}

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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocumentAttribuut;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.autaut.PartijFiatteringsuitzonderingHisVolledig;
import nl.bzk.brp.model.logisch.autaut.PartijFiatteringsuitzonderingStandaardGroep;
import nl.bzk.brp.model.operationeel.kern.ActieModel;

/**
 *
 *
 */
@Entity
@Table(schema = "AutAut", name = "His_PartijFiatuitz")
@Access(value = AccessType.FIELD)
public class HisPartijFiatteringsuitzonderingModel extends AbstractHisPartijFiatteringsuitzonderingModel implements
        PartijFiatteringsuitzonderingStandaardGroep, ModelIdentificeerbaar<Integer>
{

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected HisPartijFiatteringsuitzonderingModel() {
        super();
    }

    /**
     * Constructor voor het initialiseren van alle attributen. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param partijFiatteringsuitzondering partijFiatteringsuitzondering van HisPartijFiatteringsuitzonderingModel
     * @param datumIngang datumIngang van HisPartijFiatteringsuitzonderingModel
     * @param datumEinde datumEinde van HisPartijFiatteringsuitzonderingModel
     * @param partijBijhoudingsvoorstel partijBijhoudingsvoorstel van HisPartijFiatteringsuitzonderingModel
     * @param soortDocument soortDocument van HisPartijFiatteringsuitzonderingModel
     * @param soortAdministratieveHandeling soortAdministratieveHandeling van HisPartijFiatteringsuitzonderingModel
     * @param indicatieGeblokkeerd indicatieGeblokkeerd van HisPartijFiatteringsuitzonderingModel
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     */
    public HisPartijFiatteringsuitzonderingModel(
        final PartijFiatteringsuitzonderingHisVolledig partijFiatteringsuitzondering,
        final DatumAttribuut datumIngang,
        final DatumAttribuut datumEinde,
        final PartijAttribuut partijBijhoudingsvoorstel,
        final SoortDocumentAttribuut soortDocument,
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
    public HisPartijFiatteringsuitzonderingModel(final AbstractHisPartijFiatteringsuitzonderingModel kopie) {
        super(kopie);
    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param partijFiatteringsuitzonderingHisVolledig instantie van A-laag klasse.
     * @param groep groep
     * @param actieInhoud Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public HisPartijFiatteringsuitzonderingModel(
        final PartijFiatteringsuitzonderingHisVolledig partijFiatteringsuitzonderingHisVolledig,
        final PartijFiatteringsuitzonderingStandaardGroep groep,
        final ActieModel actieInhoud)
    {
        super(partijFiatteringsuitzonderingHisVolledig, groep, actieInhoud);
    }

}

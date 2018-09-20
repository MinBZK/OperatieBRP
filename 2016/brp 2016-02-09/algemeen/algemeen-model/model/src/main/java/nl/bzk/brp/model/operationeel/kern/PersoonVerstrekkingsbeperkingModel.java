/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.PersoonVerstrekkingsbeperking;


/**
 * De verstrekkingsbeperking zoals die voor een in de BRP gekende partij of een in een gemeentelijke verordening benoemde derde voor de persoon van
 * toepassing is.
 */
@Entity
@Table(schema = "Kern", name = "PersVerstrbeperking")
public class PersoonVerstrekkingsbeperkingModel extends AbstractPersoonVerstrekkingsbeperkingModel implements
    PersoonVerstrekkingsbeperking, ModelIdentificeerbaar<Integer>
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected PersoonVerstrekkingsbeperkingModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param persoon             persoon van Persoon \ Verstrekkingsbeperking.
     * @param partij              partij van Persoon \ Verstrekkingsbeperking.
     * @param omschrijvingDerde   omschrijvingDerde van Persoon \ Verstrekkingsbeperking.
     * @param gemeenteVerordening gemeenteVerordening van Persoon \ Verstrekkingsbeperking.
     */
    public PersoonVerstrekkingsbeperkingModel(final PersoonModel persoon, final PartijAttribuut partij,
        final OmschrijvingEnumeratiewaardeAttribuut omschrijvingDerde, final PartijAttribuut gemeenteVerordening)
    {
        super(persoon, partij, omschrijvingDerde, gemeenteVerordening);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonVerstrekkingsbeperking Te kopieren object type.
     * @param persoon                       Bijbehorende Persoon.
     */
    public PersoonVerstrekkingsbeperkingModel(final PersoonVerstrekkingsbeperking persoonVerstrekkingsbeperking,
        final PersoonModel persoon)
    {
        super(persoonVerstrekkingsbeperking, persoon);
    }

}

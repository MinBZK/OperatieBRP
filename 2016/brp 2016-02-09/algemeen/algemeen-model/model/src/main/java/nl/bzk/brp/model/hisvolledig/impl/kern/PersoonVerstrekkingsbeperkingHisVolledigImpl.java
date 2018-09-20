/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.ALaagAfleidbaar;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.AbstractPersoonVerstrekkingsbeperkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVerstrekkingsbeperkingHisVolledig;


/**
 * HisVolledig klasse voor Persoon \ Verstrekkingsbeperking.
 */
@Entity
@Table(schema = "Kern", name = "PersVerstrbeperking")
public class PersoonVerstrekkingsbeperkingHisVolledigImpl extends AbstractPersoonVerstrekkingsbeperkingHisVolledigImpl
    implements HisVolledigImpl, PersoonVerstrekkingsbeperkingHisVolledig,
    ALaagAfleidbaar, ElementIdentificeerbaar
{

    /**
     * Default contructor voor JPA.
     */
    protected PersoonVerstrekkingsbeperkingHisVolledigImpl() {
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
    public PersoonVerstrekkingsbeperkingHisVolledigImpl(final PersoonHisVolledigImpl persoon,
        final PartijAttribuut partij, final OmschrijvingEnumeratiewaardeAttribuut omschrijvingDerde,
        final PartijAttribuut gemeenteVerordening)
    {
        super(persoon, partij, omschrijvingDerde, gemeenteVerordening);
    }

}

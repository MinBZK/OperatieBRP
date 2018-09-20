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
import javax.persistence.Transient;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.ActieHisVolledig;

/**
 * HisVolledig klasse voor Actie.
 *
 */
@Entity
@Table(schema = "Kern", name = "Actie")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "iD", scope = ActieHisVolledigImpl.class)
public class ActieHisVolledigImpl extends AbstractActieHisVolledigImpl implements HisVolledigImpl, ActieHisVolledig,
        ElementIdentificeerbaar
{
    @Transient
    private boolean magGeleverdWorden;

    /**
     * Default contructor voor JPA.
     *
     */
    protected ActieHisVolledigImpl() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param soort soort van Actie.
     * @param administratieveHandeling administratieveHandeling van Actie.
     * @param partij partij van Actie.
     * @param datumAanvangGeldigheid datumAanvangGeldigheid van Actie.
     * @param datumEindeGeldigheid datumEindeGeldigheid van Actie.
     * @param tijdstipRegistratie tijdstipRegistratie van Actie.
     * @param datumOntlening datumOntlening van Actie.
     */
    public ActieHisVolledigImpl(
        final SoortActieAttribuut soort,
        final AdministratieveHandelingHisVolledigImpl administratieveHandeling,
        final PartijAttribuut partij,
        final DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid,
        final DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid,
        final DatumTijdAttribuut tijdstipRegistratie,
        final DatumEvtDeelsOnbekendAttribuut datumOntlening)
    {
        super(soort, administratieveHandeling, partij, datumAanvangGeldigheid, datumEindeGeldigheid, tijdstipRegistratie, datumOntlening);
    }

    @Override
    public boolean isMagGeleverdWorden() {
        return magGeleverdWorden;
    }

    @Override
    public void setMagGeleverdWorden(final boolean magGeleverdWorden) {
        this.magGeleverdWorden = magGeleverdWorden;
    }
}

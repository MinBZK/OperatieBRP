/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OntleningstoelichtingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.AbstractAdministratieveHandelingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.AdministratieveHandelingHisVolledig;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * HisVolledig klasse voor Administratieve handeling.
 */
@Entity
@Table(schema = "Kern", name = "AdmHnd")
public class AdministratieveHandelingHisVolledigImpl extends AbstractAdministratieveHandelingHisVolledigImpl implements HisVolledigImpl,
        AdministratieveHandelingHisVolledig, ElementIdentificeerbaar
{

    @Transient
    private boolean magGeleverdWorden;

    /**
     * Default contructor voor JPA.
     */
    protected AdministratieveHandelingHisVolledigImpl() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param soort                soort van Administratieve handeling.
     * @param partij               partij van Administratieve handeling.
     * @param toelichtingOntlening toelichtingOntlening van Administratieve handeling.
     * @param tijdstipRegistratie  tijdstipRegistratie van Administratieve handeling.
     */
    public AdministratieveHandelingHisVolledigImpl(
        final SoortAdministratieveHandelingAttribuut soort,
        final PartijAttribuut partij,
        final OntleningstoelichtingAttribuut toelichtingOntlening,
        final DatumTijdAttribuut tijdstipRegistratie)
    {
        super(soort, partij, toelichtingOntlening, tijdstipRegistratie);
    }

    @Override
    public boolean isMagGeleverdWorden() {
        return magGeleverdWorden;
    }

    @Override
    public void setMagGeleverdWorden(final boolean magGeleverdWorden) {
        this.magGeleverdWorden = magGeleverdWorden;
    }

    @Override
    public final int hashCode() {
        if (getID() != null) {
            return super.getID().hashCode();
        }
        return super.hashCode();
    }

    @Override
    public final boolean equals(final Object obj) {
        final boolean isGelijk;

        if (obj == null || obj.getClass() != getClass()) {
            isGelijk = false;
        } else if (this == obj) {
            isGelijk = true;
        } else {
            final AdministratieveHandelingHisVolledig ander = (AdministratieveHandelingHisVolledig) obj;
            isGelijk = new EqualsBuilder().append(this.getID(), ander.getID()).isEquals();
        }

        return isGelijk;
    }
}

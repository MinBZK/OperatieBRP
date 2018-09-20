/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.operationeel;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.attribuuttype.ANummer;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.basis.AbstractGroep;
import nl.bzk.brp.model.groep.logisch.basis.PersoonIdentificatienummersGroepBasis;


/**
 * .
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonIdentificatienummersGroep extends AbstractGroep implements
        PersoonIdentificatienummersGroepBasis
{

    @Embedded
    @AttributeOverride(name = "waardeAlsInteger", column = @Column(name = "bsn"))
    private Burgerservicenummer burgerservicenummer;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "anr"))
    private ANummer administratienummer;

    /**
     * Default constructor tbv hibernate.
     */
    protected AbstractPersoonIdentificatienummersGroep() {

    }

    /**
     * .
     *
     * @param persoonIdentificatienummersGroepBasis PersoonIdentificatienummersGroepBasis
     */
    protected AbstractPersoonIdentificatienummersGroep(
            final PersoonIdentificatienummersGroepBasis persoonIdentificatienummersGroepBasis)
    {
        burgerservicenummer = persoonIdentificatienummersGroepBasis.getBurgerservicenummer();
        administratienummer = persoonIdentificatienummersGroepBasis.getAdministratienummer();
    }

    /**
     * .
     *
     * @see nl.bzk.brp.model.groep.logisch.basis.PersoonIdentificatienummersGroepBasis#getBurgerservicenummer
     * @return .
     */
    @Override
    public Burgerservicenummer getBurgerservicenummer() {
        return burgerservicenummer;
    }

    /**
     * .
     *
     * @see nl.bzk.brp.model.groep.logisch.basis.PersoonIdentificatienummersGroepBasis#getAdministratienummer()
     * @return .
     */
    @Override
    public ANummer getAdministratienummer() {
        return administratienummer;
    }
}

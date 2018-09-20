/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.groep.operationeel;

import javax.persistence.*;

import nl.bzk.copy.model.attribuuttype.Administratienummer;
import nl.bzk.copy.model.attribuuttype.Burgerservicenummer;
import nl.bzk.copy.model.basis.AbstractGroep;
import nl.bzk.copy.model.groep.logisch.basis.PersoonIdentificatienummersGroepBasis;


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
    private Administratienummer administratienummer;

    /**
     * Default constructor tbv hibernate.
     */
    protected AbstractPersoonIdentificatienummersGroep() {

    }

    /**
     * .
     *
     * @param persoonIdentificatienummersGroepBasis
     *         PersoonIdentificatienummersGroepBasis
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
     * @return .
     * @see nl.bzk.copy.model.groep.logisch.basis.PersoonIdentificatienummersGroepBasis#getBurgerservicenummer
     */
    @Override
    public Burgerservicenummer getBurgerservicenummer() {
        return burgerservicenummer;
    }

    /**
     * .
     *
     * @return .
     * @see nl.bzk.copy.model.groep.logisch.basis.PersoonIdentificatienummersGroepBasis#getAdministratienummer()
     */
    @Override
    public Administratienummer getAdministratienummer() {
        return administratienummer;
    }


    public void setBurgerservicenummer(final Burgerservicenummer burgerservicenummer) {
        this.burgerservicenummer = burgerservicenummer;
    }


    public void setAdministratienummer(final Administratienummer administratienummer) {
        this.administratienummer = administratienummer;
    }
}

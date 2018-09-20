/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 * .
 */
package nl.bzk.brp.model.groep.impl.gen;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import nl.bzk.brp.model.attribuuttype.Administratienummer;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.basis.AbstractGroep;
import nl.bzk.brp.model.groep.interfaces.gen.PersoonIdentificatieNummersGroepBasis;
import nl.bzk.brp.model.objecttype.statisch.StatusHistorie;

/**
 * .
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonIdentificatieNummersGroepMdl extends AbstractGroep implements PersoonIdentificatieNummersGroepBasis {

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "bsn"))
    private Burgerservicenummer burgerServiceNummer;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "anr"))
    private Administratienummer administratieNummer;

    @Transient
    private StatusHistorie statusHistorie;
    /**
     * .
     * @see nl.bzk.brp.model.groep.interfaces.gen.PersoonIdentificatieNummersGroepBasis#getBurgerServiceNummer()
     * @return .
     */
    @Override
    public Burgerservicenummer getBurgerServiceNummer() {
        return burgerServiceNummer;
    }

    /**
     * .
     * @see nl.bzk.brp.model.groep.interfaces.gen.PersoonIdentificatieNummersGroepBasis#getAdministratieNummer()
     * @return .
     */
    @Override
    public Administratienummer getAdministratieNummer() {
        return administratieNummer;
    }

    @Override
    public StatusHistorie getStatusHistorie() {
        return statusHistorie;
    }
}

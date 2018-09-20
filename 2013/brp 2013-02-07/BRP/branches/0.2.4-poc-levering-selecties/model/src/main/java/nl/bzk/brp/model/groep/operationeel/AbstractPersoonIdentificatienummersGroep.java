/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.operationeel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.bzk.brp.model.attribuuttype.Administratienummer;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.basis.AbstractGroep;
import nl.bzk.brp.model.groep.logisch.basis.PersoonIdentificatienummersGroepBasis;
import nl.bzk.brp.util.ExternalReaderService;
import nl.bzk.brp.util.ExternalWriterService;


/**
 * .
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonIdentificatienummersGroep extends AbstractGroep implements
        PersoonIdentificatienummersGroepBasis, Externalizable
{

    @Embedded
    @AttributeOverride(name = "waardeAlsInteger", column = @Column(name = "bsn"))
    @JsonProperty
    private Burgerservicenummer burgerservicenummer;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "anr"))
    @JsonProperty
    private Administratienummer administratienummer;

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
    public Administratienummer getAdministratienummer() {
        return administratienummer;
    }

    @Override
	public void writeExternal(final ObjectOutput out) throws IOException {
    	ExternalWriterService.schrijfWaarde(out, getBurgerservicenummer());
    	ExternalWriterService.schrijfWaarde(out, getAdministratienummer());
	}

    @Override
	public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
		burgerservicenummer = (Burgerservicenummer) ExternalReaderService.leesWaarde(in, Burgerservicenummer.class);
    	administratienummer = (Administratienummer) ExternalReaderService.leesWaarde(in, Administratienummer.class);
	}


}

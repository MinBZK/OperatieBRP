package nl.bzk.brp.model.attribuuttype.basis;

import javax.persistence.MappedSuperclass;
import javax.persistence.Access;
import javax.persistence.AccessType;

import nl.bzk.brp.model.basis.AbstractGegevensAttribuutType;

/**
 * IpAdresBasis.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class IpAdresBasis extends AbstractGegevensAttribuutType<String> {

    /**
     * Private constructor t.b.v. Hibernate.
     */
    private IpAdresBasis() {
        super(null);
    }

    /**
     * De (op dit moment) enige constructor voor deze immutable class.
     *
     * @param waarde de waarde
     */
    public IpAdresBasis(final String waarde) {
        super(waarde);
    }
}

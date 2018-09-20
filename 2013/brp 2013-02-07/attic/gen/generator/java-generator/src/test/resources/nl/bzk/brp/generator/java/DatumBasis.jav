package nl.bzk.brp.model.attribuuttype.basis;

import javax.persistence.MappedSuperclass;
import javax.persistence.Access;
import javax.persistence.AccessType;

import nl.bzk.brp.model.basis.AbstractGegevensAttribuutType;

/**
 * DatumBasis.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class DatumBasis extends AbstractGegevensAttribuutType<Integer> {

    /**
     * Private constructor t.b.v. Hibernate.
     */
    private DatumBasis() {
        super(null);
    }

    /**
     * De (op dit moment) enige constructor voor deze immutable class.
     *
     * @param waarde de waarde
     */
    public DatumBasis(final Integer waarde) {
        super(waarde);
    }
}

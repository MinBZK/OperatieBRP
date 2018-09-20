package nl.bzk.brp.model.attribuuttype;

import javax.persistence.Embeddable;

import nl.bzk.brp.model.attribuuttype.basis.IpAdresBasis;

/**
 * IpAdres.
 */
@Embeddable
public final class IpAdres extends IpAdresBasis {

    /**
     * Private constructor t.b.v. Hibernate.
     */
    private IpAdres() {
        super(null);
    }

    /**
     * De (op dit moment) enige constructor voor deze immutable class.
     *
     * @param waarde de waarde
     */
    public IpAdres(final String waarde) {
        super(waarde);
    }
}

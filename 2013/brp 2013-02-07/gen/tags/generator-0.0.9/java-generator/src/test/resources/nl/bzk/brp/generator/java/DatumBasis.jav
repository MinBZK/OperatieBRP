/**
 * Deze code is gegenereerd vanuit het metaregister van het BRP, versie ${metaregister.version}.
 *
 */
package nl.bzk.brp.model.attribuuttype.basis;

import javax.persistence.Embeddable;

import nl.bzk.brp.model.basis.AbstractStatischAttribuutType;

/**
 * DatumBasis.
 * @version ${metaregister.version}.
 */
@Embeddable
public abstract class DatumBasis extends AbstractStatischAttribuutType<Integer> {

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

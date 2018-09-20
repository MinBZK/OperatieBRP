/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.attribuuttype;

import javax.persistence.Embeddable;

import nl.bzk.brp.model.attribuuttype.basis.GemeentedeelBasis;
import nl.bzk.brp.model.basis.Nullable;
import nl.bzk.brp.model.basis.Onderzoekbaar;
import nl.bzk.brp.model.basis.SoortNull;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Gemeentedeel.
 * @GEN: TODO vanuit het BMR is dit een statisch attribuuttype, maar het zou een gegevens attribuuttype moeten zijn
 */
@Embeddable
public final class Gemeentedeel extends GemeentedeelBasis implements Nullable, Onderzoekbaar {

    //TODO: Hmm, volgens mij is dit niet helemaal de bedoeling, maar voor snelle integratie van de gegenereerde code handmatig de Nullable en Onderzoekbaar interfaces geimplementeerd
    private boolean   inOnderzoek;
    private SoortNull soortNull;

    /**
     * Private constructor t.b.v. Hibernate.
     */
    private Gemeentedeel() {
        super(null);
    }

    /**
     * De (op dit moment) enige constructor voor deze immutable class.
     *
     * @param waarde de waarde
     */
    public Gemeentedeel(final String waarde) {
        super(waarde);
    }

    /**
     * Constructor die direct de waarde en de meta data als 'in onderzoek' en 'soort null' zet.
     * @param waarde de waarde van het attribuut.
     * @param inOnderzoek of het attribuut in onderzoek staat of niet.
     * @param soortNull de reden waarom het attribuut null is.
     */
    public Gemeentedeel(final String waarde, final boolean inOnderzoek, final SoortNull soortNull) {
        super(waarde);
        this.inOnderzoek = inOnderzoek;
        this.soortNull = soortNull;
    }

    @Override
    public boolean isInOnderzoek() {
        return inOnderzoek;
    }

    @Override
    public SoortNull getNullWaarde() {
        return soortNull;
    }

    @Override
    public boolean equals(final Object object) {
        boolean retval = super.equals(object);
        if (retval) {
            final Gemeentedeel that = (Gemeentedeel) object;
            retval = (isEqual(inOnderzoek, that.inOnderzoek) && isEqual(soortNull, that.soortNull));
        }
        return retval;
    }

    @Override
    protected HashCodeBuilder getHashCodeBuilder() {
        return super.getHashCodeBuilder().append(inOnderzoek).append(soortNull);
    }

    @Override
    public int hashCode() {
        return getHashCodeBuilder().toHashCode();
    }
}

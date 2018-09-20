/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.lo3;

import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.proces.UniqueSequence;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze abstracte class is de super class voor alle LO3 categorien. Een LO3 categorie bestaat mogelijk uit:
 * <ul>
 * <li>Lo3CategorieInhoud</li>
 * <li>Lo3Historie</li>
 * <li>Lo3Documentatie</li>
 * <li>Herkomst</li>
 * </ul>
 * Deze klasse staat geen wijzigingen toe op de referenties die deze klasse vasthoudt. Dit betekend dat als de instantie
 * voor inhoud immutable is dat de gehele categorie immutable is (aangezien ook lo3historie en lo3documentatie immutable
 * zijn).
 * 
 * @param <T>
 *            de specifieke LO3 categorie inhoud
 * 
 */
public final class Lo3Categorie<T extends Lo3CategorieInhoud> {

    private final T inhoud;
    private final Lo3Historie historie;
    private final Lo3Documentatie documentatie;
    private final Lo3Herkomst lo3Herkomst;

    /**
     * Maakt een Lo3Categorie object.
     * 
     * @param inhoud
     *            de LO3 categorie inhoud, mag niet null zijn
     * @param documentatie
     *            de LO3 documentatie, mag null zijn
     * @param historie
     *            de LO3 historie, mag niet null zijn
     * @param lo3Herkomst
     *            de herkomst van de categorie, mag null zijn
     * @throws NullPointerException
     *             als inhoud of historie null is
     */
    public Lo3Categorie(
            @Element(name = "inhoud") final T inhoud,
            @Element(name = "documentatie", required = false) final Lo3Documentatie documentatie,
            @Element(name = "historie") final Lo3Historie historie,
            @Element(name = "lo3Herkomst", required = false) final Lo3Herkomst lo3Herkomst) {
        if (inhoud == null) {
            throw new NullPointerException("inhoud mag niet null zijn");
        }
        if (historie == null) {
            throw new NullPointerException("historie mag niet null zijn");
        }
        this.inhoud = inhoud;

        if (documentatie != null) {
            this.documentatie = documentatie;
        } else {
            this.documentatie = new Lo3Documentatie(UniqueSequence.next(), null, null, null, null, null, null, null);
        }

        this.historie = historie;
        this.lo3Herkomst = lo3Herkomst;
    }

    /**
     * @return de LO3 inhoud van deze LO3 categorie
     */
    @Element(name = "inhoud")
    public T getInhoud() {
        return inhoud;
    }

    /**
     * @return de LO3 historie van deze LO3 categorie
     */
    @Element(name = "historie")
    public Lo3Historie getHistorie() {
        return historie;
    }

    /**
     * @return de LO3 documentatie van deze LO3 categorie
     */
    @Element(name = "documentatie", required = false)
    public Lo3Documentatie getDocumentatie() {
        return documentatie;
    }

    /**
     * @return de herkomst van deze LO3 categorie
     */
    @Element(name = "lo3Herkomst", required = false)
    public Lo3Herkomst getLo3Herkomst() {
        return lo3Herkomst;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Lo3Categorie)) {
            return false;
        }
        @SuppressWarnings("rawtypes")
        final Lo3Categorie castOther = (Lo3Categorie) other;
        return new EqualsBuilder().append(inhoud, castOther.inhoud).append(historie, castOther.historie)
                .append(documentatie, castOther.documentatie).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(inhoud).append(historie).append(documentatie).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("inhoud", inhoud).append("historie", historie).append("documentatie", documentatie)
                .append("herkomst", lo3Herkomst).toString();
    }

}

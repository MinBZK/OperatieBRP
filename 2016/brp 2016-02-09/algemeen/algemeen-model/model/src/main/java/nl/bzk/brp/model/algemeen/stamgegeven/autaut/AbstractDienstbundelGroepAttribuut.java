/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Immutable;

/**
 * Het van toepassing zijn van een attribuut bij een abonnement.
 *
 * Een afnemer mag normaliter in kader van een abonnement over bepaalde soorten gegevens beschikken, zoals bijvoorbeeld
 * over het BSN of de geboortedatum. De gegevens waarover de afnemer mag beschikken, worden door elementen vastgelegd.
 * Zo wordt een afnemer geautoriseerd voor het beschikken over het BSN van een persoon door het betreffende abonnement
 * te koppelen aan het element voor BSN.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@Immutable
@Generated(value = "nl.bzk.brp.generatoren.java.DynamischeStamgegevensGenerator")
@MappedSuperclass
public abstract class AbstractDienstbundelGroepAttribuut {

    @Id
    @JsonProperty
    private Integer iD;

    @ManyToOne
    @JoinColumn(name = "DienstbundelGroep")
    @Fetch(value = FetchMode.JOIN)
    private DienstbundelGroep dienstbundelGroep;

    @ManyToOne
    @JoinColumn(name = "Attr")
    @Fetch(value = FetchMode.JOIN)
    private Element attribuut;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractDienstbundelGroepAttribuut() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param dienstbundelGroep dienstbundelGroep van DienstbundelGroepAttribuut.
     * @param attribuut attribuut van DienstbundelGroepAttribuut.
     */
    protected AbstractDienstbundelGroepAttribuut(final DienstbundelGroep dienstbundelGroep, final Element attribuut) {
        this.dienstbundelGroep = dienstbundelGroep;
        this.attribuut = attribuut;

    }

    /**
     * Retourneert ID van Dienstbundel \ Groep \ Attribuut.
     *
     * @return ID.
     */
    protected Integer getID() {
        return iD;
    }

    /**
     * Retourneert Dienstbundel \ Groep van Dienstbundel \ Groep \ Attribuut.
     *
     * @return Dienstbundel \ Groep.
     */
    public final DienstbundelGroep getDienstbundelGroep() {
        return dienstbundelGroep;
    }

    /**
     * Retourneert Attribuut van Dienstbundel \ Groep \ Attribuut.
     *
     * @return Attribuut.
     */
    public final Element getAttribuut() {
        return attribuut;
    }

}

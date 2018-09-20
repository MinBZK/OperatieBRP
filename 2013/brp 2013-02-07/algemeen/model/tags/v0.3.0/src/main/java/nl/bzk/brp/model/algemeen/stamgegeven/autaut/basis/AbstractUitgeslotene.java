/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Bijhoudingsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;


/**
 * Het expliciet uitgesloten zijn van een Partij in een Bijhoudingsautorisatie.
 *
 * Een Bijhoudingsautorisatie is meestal positief verwoord: een Partij die expliciet is geautoriseerd wordt als
 * geautoriseerde vastgelegd.
 * Indien alle partijen van een soort worden geautoriseerd, is er de mogelijkheid om specifieke Partijen uit te sluiten.
 * In dat geval wordt de negatie vastgelegd: de Partij die expliciet wordt uitgesloten.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@MappedSuperclass
public abstract class AbstractUitgeslotene extends AbstractStatischObjectType {

    @Id
    @JsonProperty
    private Integer                iD;

    @ManyToOne
    @JoinColumn(name = "Bijhautorisatie")
    @Fetch(value = FetchMode.JOIN)
    private Bijhoudingsautorisatie bijhoudingsautorisatie;

    @ManyToOne
    @JoinColumn(name = "UitgeslotenPartij")
    @Fetch(value = FetchMode.JOIN)
    private Partij                 uitgeslotenPartij;

    @Type(type = "StatusHistorie")
    @Column(name = "UitgesloteneStatusHis")
    private StatusHistorie         uitgesloteneStatusHis;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractUitgeslotene() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param bijhoudingsautorisatie bijhoudingsautorisatie van Uitgeslotene.
     * @param uitgeslotenPartij uitgeslotenPartij van Uitgeslotene.
     * @param uitgesloteneStatusHis uitgesloteneStatusHis van Uitgeslotene.
     */
    protected AbstractUitgeslotene(final Bijhoudingsautorisatie bijhoudingsautorisatie, final Partij uitgeslotenPartij,
            final StatusHistorie uitgesloteneStatusHis)
    {
        this.bijhoudingsautorisatie = bijhoudingsautorisatie;
        this.uitgeslotenPartij = uitgeslotenPartij;
        this.uitgesloteneStatusHis = uitgesloteneStatusHis;

    }

    /**
     * Retourneert ID van Uitgeslotene.
     *
     * @return ID.
     */
    protected Integer getID() {
        return iD;
    }

    /**
     * Retourneert Bijhoudingsautorisatie van Uitgeslotene.
     *
     * @return Bijhoudingsautorisatie.
     */
    public Bijhoudingsautorisatie getBijhoudingsautorisatie() {
        return bijhoudingsautorisatie;
    }

    /**
     * Retourneert Uitgesloten partij van Uitgeslotene.
     *
     * @return Uitgesloten partij.
     */
    public Partij getUitgeslotenPartij() {
        return uitgeslotenPartij;
    }

    /**
     * Retourneert Uitgeslotene StatusHis van Uitgeslotene.
     *
     * @return Uitgeslotene StatusHis.
     */
    public StatusHistorie getUitgesloteneStatusHis() {
        return uitgesloteneStatusHis;
    }

}

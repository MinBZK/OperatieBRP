/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.domein.aut;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nl.bzk.brp.bevraging.domein.Partij;
import nl.bzk.brp.bevraging.domein.StatusHistorie;

/**
 * Een Bijhoudingsautorisatie is meestal positief verwoord: een Partij die expliciet is geautoriseerd wordt als
 * geautoriseerde vastgelegd. Indien alle partijen van een soort worden geautoriseerd, is er de mogelijkheid om
 * specifieke Partijen uit te sluiten. In dat geval wordt de negatie vastgelegd: de Partij die
 * expliciet wordt uitgesloten.
 */
@Entity
@Table(name = "uitgeslotene", schema = "autaut")
@Access(AccessType.FIELD)
public class Uitgeslotene {

    @SequenceGenerator(name = "UITGESLOTENE_SEQUENCE_GENERATOR", sequenceName = "AutAut.seq_Uitgeslotene")
    @Id
    @GeneratedValue(generator = "UITGESLOTENE_SEQUENCE_GENERATOR")
    private Long                   id;
    @ManyToOne
    @JoinColumn(name = "bijhautorisatie")
    private BijhoudingsAutorisatie bijhoudingsAutorisatie;
    @ManyToOne
    @JoinColumn(name = "uitgeslotenpartij")
    private Partij                 uitgeslotenPartij;
    @Column(name = "uitgeslotenestatushis")
    private StatusHistorie         statusHistorie;

    /**
     * No-arg constructor.
     */
    public Uitgeslotene() {
    }

    public Long getId() {
        return id;
    }

    /**
     * De BijhoudingsAutorisatie waar deze uitgeslotene bijhoort.
     *
     * @return BijhoudingsAutorisatia die hoort bij deze uitgeslotene.
     */
    public BijhoudingsAutorisatie getBijhoudingsAutorisatie() {
        return bijhoudingsAutorisatie;
    }

    /**
     * De partij die uitgesloten is van de bijhoudings autorisatie.
     *
     * @return De uitgesloten partij.
     */
    public Partij getUitgeslotenPartij() {
        return uitgeslotenPartij;
    }

    public StatusHistorie getStatusHistorie() {
        return statusHistorie;
    }
}

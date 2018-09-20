/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.lev;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nl.bzk.brp.bevraging.domein.GegevensElement;


/**
 * Het voorkomen van een gegevenselement in een Abonnement.
 *
 * Bij het Abonnement wordt vastgelegd welke Gegevenselementen in het kader van het Abonnement geraakt mogen worden. Het
 * betreft een deelverzameling van de Gegevenselement doelbinding.
 */
@Entity
@Table(name = "AbonnementGegevensElement", schema = "Lev")
@Access(AccessType.FIELD)
public class AbonnementGegevensElement {

    @SequenceGenerator(name = "ABONNEMENT_GEGEVENS_ELEMENT_SEQUENCE_GENERATOR", sequenceName = "Lev.seq_AbonnementGegevensElement")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ABONNEMENT_GEGEVENS_ELEMENT_SEQUENCE_GENERATOR")
    private Long         id;

    @ManyToOne
    @JoinColumn(name = "Abonnement")
    private Abonnement   abonnement;

    @Column(name = "GegevensElement")
    private GegevensElement gegevensElement;

    /**
     * No-arg constructor vereist voor JPA.
     */
    protected AbonnementGegevensElement() {
    }

    /**
     * Constructor voor programmatische instantiatie met verplichte attributen.
     *
     * @param abonnement het abonnement waar dit gegevenselement bij hoort.
     * @param gegevensElement het gegevenselement dat bij dit abonnement hoort.
     */
    public AbonnementGegevensElement(final Abonnement abonnement, final GegevensElement gegevensElement) {
        this.abonnement = abonnement;
        this.gegevensElement = gegevensElement;
    }

    public Long getId() {
        return id;
    }

    public Abonnement getAbonnement() {
        return abonnement;
    }

    public GegevensElement getGegevensElement() {
        return gegevensElement;
    }
}

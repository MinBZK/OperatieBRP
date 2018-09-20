/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.model;

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


/**
 * Toegestaan bericht in kader van een Abonnement.
 *
 * Door middel van het opsommen van de Soorten bericht die mogelijk zijn bij een Abonnement, wordt invulling gegeven aan
 * de behoefte om de relevante services die worden gestart naar aanleiding van een Abonnement.
 */
@Entity
@Table(name = "AbonnementSrtBer", schema = "Lev")
@Access(AccessType.FIELD)
public class AbonnementSoortBericht {

    @SequenceGenerator(name = "ABONNEMENT_SOORT_BERICHT_SEQUENCE_GENERATOR", sequenceName = "Lev.seq_AbonnementSrtBer")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ABONNEMENT_SOORT_BERICHT_SEQUENCE_GENERATOR")
    private Long         id;

    @ManyToOne
    @JoinColumn(name = "Abonnement")
    private Abonnement   abonnement;

    @Column(name = "SrtBer")
    private SoortBericht soortBericht;

    /**
     * No-arg constructor vereist voor JPA.
     */
    protected AbonnementSoortBericht() {
    }

    /**
     * Constructor voor programmatische instantiatie met verplichte attributen.
     *
     * @param abonnement het abonnement waar deze soort bericht bij hoort.
     * @param soortBericht het soort bericht dat bij dit abonnement hoort.
     */
    public AbonnementSoortBericht(final Abonnement abonnement, final SoortBericht soortBericht) {
        this.abonnement = abonnement;
        this.soortBericht = soortBericht;
    }

    public Long getId() {
        return id;
    }

    public Abonnement getAbonnement() {
        return abonnement;
    }

    public SoortBericht getSoortBericht() {
        return soortBericht;
    }
}

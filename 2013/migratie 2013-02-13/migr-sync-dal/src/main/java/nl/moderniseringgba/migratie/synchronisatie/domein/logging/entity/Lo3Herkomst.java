/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.logging.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.BRPActie;

/**
 * Als een actie is ontstaan vanuit een migratie van een LO3 persoonslijst naar een BRP persoon dan is voor elke
 * BRPActie de Lo3Herkomst gevuld. Deze kan worden gebruikt voor het traceren van de LO3 herkomst van een BRP gegeven.
 * 
 * TODO voorlopig skelet
 */
@Entity
@Table(name = "lo3herkomst", schema = "logging")
public class Lo3Herkomst {

    @Id
    @SequenceGenerator(name = "lo3herkomst_id_generator", sequenceName = "logging.seq_lo3herkomst",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lo3herkomst_id_generator")
    @Column(nullable = false)
    private Long id;

    // bi-directional one-to-one association to BRPActie
    @OneToOne(optional = false)
    @JoinColumn(name = "brp_actie_id", unique = true, nullable = false, updatable = false)
    private BRPActie brpActie;

    @Column(name = "lo3_categorie", nullable = false)
    private Integer categorie;

    @Column(name = "lo3_stapel", nullable = false)
    private Integer stapel;

    @Column(name = "lo3_voorkomen", nullable = false)
    private Integer voorkomen;

    /**
     * Maakt een nieuw Lo3Herkomst object.
     */
    public Lo3Herkomst() {
    }

    /**
     * Maakt een nieuw Lo3Herkomst object.
     * 
     * @param categorie
     *            de LO3 categorie
     * @param stapel
     *            de LO3 stapel identificatie 0..n
     * @param voorkomen
     *            de LO3 voorkomen identificatie 0..n
     */
    public Lo3Herkomst(final Integer categorie, final Integer stapel, final Integer voorkomen) {
        this.categorie = categorie;
        this.stapel = stapel;
        this.voorkomen = voorkomen;
    }

    public final BRPActie getBrpActie() {
        return brpActie;
    }

    public final Long getId() {
        return id;
    }

    /**
     * Maakt een bi-directionele associatie tussen Lo3Herkomst en BRPActie.
     * 
     * @param brpActie
     *            de BRP actie
     */
    public final void setBrpActie(final BRPActie brpActie) {
        this.brpActie = brpActie;
        if (brpActie != null && !this.equals(brpActie.getLo3Herkomst())) {
            brpActie.setLo3Herkomst(this);
        }
    }

    public final Integer getCategorie() {
        return categorie;
    }

    public final void setCategorie(final Integer categorie) {
        this.categorie = categorie;
    }

    public final Integer getStapel() {
        return stapel;
    }

    public final void setStapel(final Integer stapel) {
        this.stapel = stapel;
    }

    public final Integer getVoorkomen() {
        return voorkomen;
    }

    public final void setVoorkomen(final Integer voorkomen) {
        this.voorkomen = voorkomen;
    }
}

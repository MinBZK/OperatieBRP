/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;

/**
 * The persistent class for the persverstrbeperking database table.
 * 
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "persverstrbeperking", schema = "kern", uniqueConstraints = @UniqueConstraint(
        columnNames = {"pers", "partij", "omsderde", "gemverordening" }))
@SuppressWarnings("checkstyle:designforextension")
public class PersoonVerstrekkingsbeperking extends AbstractDeltaEntiteit implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "persverstrbeperking_id_generator", sequenceName = "kern.seq_persverstrbeperking", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "persverstrbeperking_id_generator")
    @Column(nullable = false)
    /**
     * Het veld zou Integer moeten zijn maar is Long i.v.m. de link naar GegevenInOnderzoek.
     */
    private Long id;

    // bi-directional many-to-one association to PersoonVerstrekkingsbeperkingHistorie
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persoonVerstrekkingsbeperking",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, orphanRemoval = true)
    private Set<PersoonVerstrekkingsbeperkingHistorie> persoonVerstrekkingsbeperkingHistorieSet = new LinkedHashSet<>(0);

    // bi-directional many-to-one association to Persoon
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "partij")
    private Partij partij;

    @Column(name = "omsderde")
    private String omschrijvingDerde;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "gemverordening")
    private Partij gemeenteVerordening;

    /**
     * JPA default constructor.
     */
    protected PersoonVerstrekkingsbeperking() {
    }

    /**
     * Maak een nieuwe persoon verstrekkingsbeperking.
     *
     * @param persoon
     *            persoon
     */
    public PersoonVerstrekkingsbeperking(final Persoon persoon) {
        setPersoon(persoon);
    }

    /**
     * Geef de Id waarde voor de entiteit. Intern is de Id een Long voor integratie met GegevenInOnderzoek. De waarde
     * wordt geconverteerd naar een Integer.
     *
     * @return de Id waarde.
     */
    public Integer getId() {
        return convertLongNaarInteger(id);
    }

    /**
     * Zet de Id waarde voor de entiteit. Intern wordt de Id waarde geconverteert naar een Long voor integratie met
     * GegevenInOnderzoek.
     *
     * @param id
     *            de Id waarde.
     */
    public void setId(final Integer id) {
        this.id = convertIntegerNaarLong(id);
    }

    /**
     * Geef de waarde van persoon verstrekkingsbeperking historie set.
     *
     * @return persoon verstrekkingsbeperking historie set
     */
    public Set<PersoonVerstrekkingsbeperkingHistorie> getPersoonVerstrekkingsbeperkingHistorieSet() {
        return persoonVerstrekkingsbeperkingHistorieSet;
    }

    /**
     * Toevoegen van een persoon verstrekkingsbeperking historie.
     *
     * @param persoonVerstrekkingsbeperkingHistorie
     *            persoon verstrekkingsbeperking historie
     */
    public void addPersoonVerstrekkingsbeperkingHistorie(final PersoonVerstrekkingsbeperkingHistorie persoonVerstrekkingsbeperkingHistorie) {
        persoonVerstrekkingsbeperkingHistorie.setPersoonVerstrekkingsbeperking(this);
        persoonVerstrekkingsbeperkingHistorieSet.add(persoonVerstrekkingsbeperkingHistorie);
    }

    /**
     * Geef de waarde van persoon.
     *
     * @return persoon
     */
    public Persoon getPersoon() {
        return persoon;
    }

    /**
     * Zet de waarde van persoon.
     *
     * @param persoon
     *            persoon
     */
    public void setPersoon(final Persoon persoon) {
        ValidationUtils.controleerOpNullWaarden("persoon mag niet null zijn", persoon);
        this.persoon = persoon;
    }

    /**
     * Geef de waarde van partij.
     *
     * @return partij
     */
    public Partij getPartij() {
        return partij;
    }

    /**
     * Zet de waarde van partij.
     *
     * @param partij
     *            partij
     */
    public void setPartij(final Partij partij) {
        ValidationUtils.controleerOpNullWaarden("partij mag niet null zijn", partij);
        this.partij = partij;
    }

    /**
     * Geef de waarde van gemeente verordening.
     *
     * @return gemeente verordening
     */
    public Partij getGemeenteVerordening() {
        return gemeenteVerordening;
    }

    /**
     * Zet de waarde van gemeente verordening.
     *
     * @param gemeenteVerordening
     *            gemeente verordening
     */
    public void setGemeenteVerordening(final Partij gemeenteVerordening) {
        this.gemeenteVerordening = gemeenteVerordening;
    }

    /**
     * Geef de waarde van omschrijving derde.
     *
     * @return omschrijving derde
     */
    public String getOmschrijvingDerde() {
        return omschrijvingDerde;
    }

    /**
     * Zet de waarde van omschrijving derde.
     *
     * @param soortVerificatie
     *            omschrijving derde
     */
    public void setOmschrijvingDerde(final String soortVerificatie) {
        this.omschrijvingDerde = soortVerificatie;
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

import java.io.Serializable;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie;
import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;

/**
 * The persistent class for the bron database table.
 * 
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "actiebron", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"actie", "doc", "rechtsgrond", "rechtsgrondoms" }))
@SuppressWarnings("checkstyle:designforextension")
public class ActieBron extends AbstractDeltaEntiteit implements AdministratiefGegeven, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "bron_id_generator", sequenceName = "kern.seq_actiebron", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bron_id_generator")
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "actie", nullable = false)
    private BRPActie actie;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
    @JoinColumn(name = "doc")
    private Document document;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "rechtsgrond")
    private Rechtsgrond rechtsgrond;

    @Column(name = "rechtsgrondoms", length = 250)
    private String rechtsgrondOmschrijving;

    /**
     * JPA default constructor.
     */
    protected ActieBron() {
    }

    /**
     * Maak een nieuwe actie bron.
     *
     * @param actie
     *            actie
     */
    public ActieBron(final BRPActie actie) {
        setActie(actie);
    }

    /**
     * Geef de waarde van id.
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Zet de waarde van id.
     *
     * @param id
     *            id
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van actie.
     *
     * @return actie
     */
    public BRPActie getActie() {
        return actie;
    }

    /**
     * Zet de waarde van actie.
     *
     * @param actie
     *            actie
     */
    public void setActie(final BRPActie actie) {
        ValidationUtils.controleerOpNullWaarden("actie mag niet null zijn", actie);
        this.actie = actie;
    }

    /**
     * Geef de waarde van document.
     *
     * @return document
     */
    public Document getDocument() {
        return document;
    }

    /**
     * Zet de waarde van document.
     *
     * @param document
     *            document
     */
    public void setDocument(final Document document) {
        this.document = document;
    }

    /**
     * Geef de waarde van rechtsgrond.
     *
     * @return rechtsgrond
     */
    public Rechtsgrond getRechtsgrond() {
        return rechtsgrond;
    }

    /**
     * Zet de waarde van rechtsgrond.
     *
     * @param rechtsgrond
     *            rechtsgrond
     */
    public void setRechtsgrond(final Rechtsgrond rechtsgrond) {
        this.rechtsgrond = rechtsgrond;
    }

    /**
     * Geef de waarde van rechtsgrond omschrijving.
     *
     * @return rechtsgrond omschrijving
     */
    public String getRechtsgrondOmschrijving() {
        return rechtsgrondOmschrijving;
    }

    /**
     * Zet de waarde van rechtsgrond omschrijving.
     *
     * @param rechtsgrondOmschrijving
     *            rechtsgrond omschrijving
     */
    public void setRechtsgrondOmschrijving(final String rechtsgrondOmschrijving) {
        Validatie.controleerOpLegeWaarden("rechtsgrondOmschrijving mag geen lege string zijn", rechtsgrondOmschrijving);
        this.rechtsgrondOmschrijving = rechtsgrondOmschrijving;
    }
}

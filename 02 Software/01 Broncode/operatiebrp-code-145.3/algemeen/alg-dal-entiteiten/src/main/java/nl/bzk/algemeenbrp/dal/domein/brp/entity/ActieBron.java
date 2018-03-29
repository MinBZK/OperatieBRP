/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

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
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;

/**
 * The persistent class for the bron database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "actiebron", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"actie", "doc", "rechtsgrond", "rechtsgrondoms"}))
public class ActieBron extends AbstractEntiteit implements AdministratiefGegeven, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "bron_id_generator", sequenceName = "kern.seq_actiebron", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bron_id_generator")
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "actie", nullable = false)
    private BRPActie actie;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "doc")
    private Document document;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rechtsgrond")
    private Rechtsgrond rechtsgrond;

    @Column(name = "rechtsgrondoms", length = 250)
    private String rechtsgrondOmschrijving;

    /**
     * JPA default constructor.
     */
    protected ActieBron() {}

    /**
     * Maak een nieuwe actie bron.
     *
     * @param actie actie
     */
    public ActieBron(final BRPActie actie) {
        setActie(actie);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.algemeen.dal.domein.brp.kern.entity.DeltaEntiteit#getId()
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van ActieBron.
     *
     * @param id de nieuwe waarde voor id van ActieBron
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van actie van ActieBron.
     *
     * @return de waarde van actie van ActieBron
     */
    public BRPActie getActie() {
        return actie;
    }

    /**
     * Zet de waarden voor actie van ActieBron.
     *
     * @param actie de nieuwe waarde voor actie van ActieBron
     */
    public void setActie(final BRPActie actie) {
        ValidationUtils.controleerOpNullWaarden("actie mag niet null zijn", actie);
        this.actie = actie;
    }

    /**
     * Geef de waarde van document van ActieBron.
     *
     * @return de waarde van document van ActieBron
     */
    public Document getDocument() {
        return document;
    }

    /**
     * Zet de waarden voor document van ActieBron.
     *
     * @param document de nieuwe waarde voor document van ActieBron
     */
    public void setDocument(final Document document) {
        this.document = document;
    }

    /**
     * Geef de waarde van rechtsgrond van ActieBron.
     *
     * @return de waarde van rechtsgrond van ActieBron
     */
    public Rechtsgrond getRechtsgrond() {
        return rechtsgrond;
    }

    /**
     * Zet de waarden voor rechtsgrond van ActieBron.
     *
     * @param rechtsgrond de nieuwe waarde voor rechtsgrond van ActieBron
     */
    public void setRechtsgrond(final Rechtsgrond rechtsgrond) {
        this.rechtsgrond = rechtsgrond;
    }

    /**
     * Geef de waarde van rechtsgrond omschrijving van ActieBron.
     *
     * @return de waarde van rechtsgrond omschrijving van ActieBron
     */
    public String getRechtsgrondOmschrijving() {
        return rechtsgrondOmschrijving;
    }

    /**
     * Zet de waarden voor rechtsgrond omschrijving van ActieBron.
     *
     * @param rechtsgrondOmschrijving de nieuwe waarde voor rechtsgrond omschrijving van ActieBron
     */
    public void setRechtsgrondOmschrijving(final String rechtsgrondOmschrijving) {
        ValidationUtils.controleerOpLegeWaarden("rechtsgrondOmschrijving mag geen lege string zijn", rechtsgrondOmschrijving);
        this.rechtsgrondOmschrijving = rechtsgrondOmschrijving;
    }
}

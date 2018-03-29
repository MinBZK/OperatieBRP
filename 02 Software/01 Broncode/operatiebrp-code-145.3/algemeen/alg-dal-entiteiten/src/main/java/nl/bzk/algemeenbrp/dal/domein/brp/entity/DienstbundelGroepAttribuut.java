/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;

/**
 * The persistent class for the dienstbundelgroepattr database table.
 *
 */
@Entity
@Table(name = "dienstbundelgroepattr", schema = "autaut", uniqueConstraints = @UniqueConstraint(columnNames = {"dienstbundelgroep", "attr"}))
@NamedQuery(name = "DienstbundelGroepAttribuut" + Constanten.ZOEK_ALLES_VOOR_CACHE, query = "SELECT d FROM DienstbundelGroepAttribuut d")
@Access(AccessType.FIELD)
public class DienstbundelGroepAttribuut implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "dienstbundelgroepattr_id_generator", sequenceName = "autaut.seq_dienstbundelgroepattr", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dienstbundelgroepattr_id_generator")
    @Column(updatable = false)
    @Access(AccessType.PROPERTY)
    private Integer id;

    @Column(name = "attr", nullable = false)
    private int elementId;

    // bi-directional many-to-one association to DienstbundelGroep
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dienstbundelgroep", nullable = false)
    private DienstbundelGroep dienstbundelGroep;

    /**
     * JPA no-args constructor.
     */
    DienstbundelGroepAttribuut() {}

    /**
     * Maak een nieuw DienstbundelGroepAttribuut object.
     *
     * @param dienstbundelGroep dienstbundelGroep
     * @param attribuut attribuut
     */
    public DienstbundelGroepAttribuut(final DienstbundelGroep dienstbundelGroep, final Element attribuut) {
        setDienstbundelGroep(dienstbundelGroep);
        setAttribuut(attribuut);
    }

    /**
     * Geef de waarde van id van DienstbundelGroepAttribuut.
     *
     * @return de waarde van id van DienstbundelGroepAttribuut
     */
    public Integer getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van DienstbundelGroepAttribuut.
     *
     * @param id de nieuwe waarde voor id van DienstbundelGroepAttribuut
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van attribuut van DienstbundelGroepAttribuut.
     *
     * @return de waarde van attribuut van DienstbundelGroepAttribuut
     */
    public Element getAttribuut() {
        return Element.parseId(elementId);
    }

    /**
     * Zet de waarden voor attribuut van DienstbundelGroepAttribuut.
     *
     * @param attribuut de nieuwe waarde voor attribuut van DienstbundelGroepAttribuut
     */
    public void setAttribuut(final Element attribuut) {
        ValidationUtils.controleerOpNullWaarden("attribuut mag niet null zijn", attribuut);
        elementId = attribuut.getId();
    }

    /**
     * Geef de waarde van dienstbundel groep van DienstbundelGroepAttribuut.
     *
     * @return de waarde van dienstbundel groep van DienstbundelGroepAttribuut
     */
    public DienstbundelGroep getDienstbundelGroep() {
        return dienstbundelGroep;
    }

    /**
     * Zet de waarden voor dienstbundel groep van DienstbundelGroepAttribuut.
     *
     * @param dienstbundelGroep de nieuwe waarde voor dienstbundel groep van
     *        DienstbundelGroepAttribuut
     */
    public void setDienstbundelGroep(final DienstbundelGroep dienstbundelGroep) {
        ValidationUtils.controleerOpNullWaarden("dienstbundelGroep mag niet null zijn", dienstbundelGroep);
        this.dienstbundelGroep = dienstbundelGroep;
    }
}

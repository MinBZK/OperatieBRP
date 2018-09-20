/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.model.domein.entities;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3CategorieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;
import nl.bzk.migratiebrp.init.logging.model.StapelMatch;
import nl.bzk.migratiebrp.init.logging.model.VerschilType;
import nl.bzk.migratiebrp.init.logging.model.VoorkomenMatch;

/**
 * Vertegenwoordigt een regel in de verschil_analyse tabel.
 */
@Entity
@Table(name = "verschil_analyse", schema = "initvul")
@SuppressWarnings("checkstyle:designforextension")
public class VerschilAnalyseRegel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "VERSCHIL_ANALYSE_ID_GENERATOR", sequenceName = "initvul.seq_verschil_analyse", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VERSCHIL_ANALYSE_ID_GENERATOR")
    @Column(nullable = false)
    private Long id;

    @Column(name = "categorie", nullable = false)
    private Integer categorie;

    @Column(name = "stapel", nullable = true)
    private Integer stapel;

    @Column(name = "voorkomen", nullable = true)
    private Integer voorkomen;

    @Column(name = "verschil_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private VerschilType type;

    @Column(name = "element", nullable = true)
    private String element;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "gbav_pl_id")
    private InitVullingLog log;

    /** Default constructor voor hibernate. */
    protected VerschilAnalyseRegel() {
    }

    /**
     * Constructor.
     *
     * @param stapelMatch
     *            stapel match
     * @param <T>
     *            type
     */
    public <T extends Lo3CategorieInhoud> VerschilAnalyseRegel(final StapelMatch<T> stapelMatch) {
        this(stapelMatch.getHerkomst(), stapelMatch.getVerschilType(), false);
    }

    /**
     * Constructor.
     *
     * @param voorkomenMatch
     *            voorkomen match
     * @param <T>
     *            type
     */
    public <T extends Lo3CategorieInhoud> VerschilAnalyseRegel(final VoorkomenMatch<T> voorkomenMatch) {
        this(voorkomenMatch.getHerkomst(), voorkomenMatch.getVerschilType(), true);
    }

    /**
     * Constructor.
     *
     * @param herkomst
     *            herkomst
     * @param type
     *            type
     * @param vulVoorkomen
     *            indicatie of voorkomen uit herkomst genomen moet worden
     */
    private VerschilAnalyseRegel(final Lo3Herkomst herkomst, final VerschilType type, final boolean vulVoorkomen) {
        ValidationUtils.controleerOpNullWaarden("Herkomst mag niet null zijn", herkomst);

        categorie = herkomst.getCategorie().getCategorieAsInt();
        stapel = herkomst.getStapel();
        voorkomen = vulVoorkomen ? herkomst.getVoorkomen() : null;
        this.type = type;
    }

    /**
     * Constructor.
     *
     * @param herkomst
     *            herkomst
     * @param type
     *            verschil type
     * @param element
     *            element
     */
    public VerschilAnalyseRegel(final Lo3Herkomst herkomst, final VerschilType type, final String element) {
        this(herkomst, type, true);
        this.element = element;
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
     * Geef de waarde van categorie.
     *
     * @return categorie
     */
    public Integer getCategorie() {
        return categorie;
    }

    /**
     * Zet de waarde van categorie.
     *
     * @param categorie
     *            categorie
     */
    public void setCategorie(final Integer categorie) {
        this.categorie = categorie;
    }

    /**
     * Geef de waarde van stapel.
     *
     * @return stapel
     */
    public Integer getStapel() {
        return stapel;
    }

    /**
     * Zet de waarde van stapel.
     *
     * @param stapel
     *            stapel
     */
    public void setStapel(final Integer stapel) {
        this.stapel = stapel;
    }

    /**
     * Geef de waarde van voorkomen.
     *
     * @return voorkomen
     */
    public Integer getVoorkomen() {
        return voorkomen;
    }

    /**
     * Zet de waarde van voorkomen.
     *
     * @param voorkomen
     *            voorkomen
     */
    public void setVoorkomen(final Integer voorkomen) {
        this.voorkomen = voorkomen;
    }

    /**
     * Geef de waarde van type.
     *
     * @return type
     */
    public VerschilType getType() {
        return type;
    }

    /**
     * Zet de waarde van type.
     *
     * @param type
     *            type
     */
    public void setType(final VerschilType type) {
        this.type = type;
    }

    /**
     * Geef de waarde van element.
     *
     * @return element
     */
    public String getElement() {
        return element;
    }

    /**
     * Zet de waarde van element.
     *
     * @param element
     *            element
     */
    public void setElement(final String element) {
        this.element = element;
    }

    /**
     * Geef de waarde van log.
     *
     * @return log
     */
    public InitVullingLog getLog() {
        return log;
    }

    /**
     * Zet de waarde van log.
     *
     * @param log
     *            log
     */
    void setLog(final InitVullingLog log) {
        this.log = log;
    }
}

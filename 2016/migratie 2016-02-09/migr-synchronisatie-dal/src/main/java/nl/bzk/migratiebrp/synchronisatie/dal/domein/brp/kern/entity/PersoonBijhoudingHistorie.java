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

import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;

/**
 * The persistent class for the his_persbijhgem database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_persbijhouding", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"pers", "tsreg", "dataanvgel" }))
@SuppressWarnings("checkstyle:designforextension")
public class PersoonBijhoudingHistorie extends AbstractMaterieleHistorie implements Serializable {

    /** Veldnaam van bijhoudingsaardId tbv verschil verwerking. */
    public static final String BIJHOUDINGSAARD_ID = "bijhoudingsaardId";
    /** Veldnaam van nadereBijhoudingsaardId tbv verschil verwerking. */
    public static final String NADERE_BIJHOUDINGSAARD_ID = "nadereBijhoudingsaardId";

    private static final long serialVersionUID = 1L;
    private static final String PERSOON_MAG_NIET_NULL_ZIJN = "persoon mag niet null zijn";
    private static final String PARTIJ_MAG_NIET_NULL_ZIJN = "partij mag niet null zijn";

    @Id
    @SequenceGenerator(name = "his_persbijhouding_id_generator", sequenceName = "kern.seq_his_persbijhouding", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_persbijhouding_id_generator")
    @Column(nullable = false)
    /**
     * Het veld zou Integer moeten zijn maar is Long i.v.m. de link naar GegevenInOnderzoek.
     */
    private Long id;

    @Column(name = "bijhaard", nullable = false)
    /* Bij wijziging veld-naam ook de string constante daarvoor aanpassen */
    private short bijhoudingsaardId;

    @Column(name = "naderebijhaard", nullable = false)
    /* Bij wijziging veld-naam ook de string constante daarvoor aanpassen */
    private short nadereBijhoudingsaardId;

    @Column(name = "indonverwdocaanw", nullable = false)
    private boolean indicatieOnverwerktDocumentAanwezig;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "bijhpartij", nullable = false)
    private Partij partij;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    /**
     * JPA default constructor.
     */
    protected PersoonBijhoudingHistorie() {
    }

    /**
     * Maak een nieuwe persoon bijhouding historie.
     *
     * @param persoon
     *            persoon
     * @param partij
     *            partij
     * @param bijhoudingsaard
     *            bijhoudingsaard
     * @param nadereBijhoudingsaard
     *            nadere bijhoudingsaard
     * @param indicatieOnverwerktDocumentAanwezig
     *            indicatie onverwerkt document aanwezig
     */
    public PersoonBijhoudingHistorie(
        final Persoon persoon,
        final Partij partij,
        final Bijhoudingsaard bijhoudingsaard,
        final NadereBijhoudingsaard nadereBijhoudingsaard,
        final boolean indicatieOnverwerktDocumentAanwezig)
    {
        ValidationUtils.controleerOpNullWaarden(PERSOON_MAG_NIET_NULL_ZIJN, persoon);
        this.persoon = persoon;
        ValidationUtils.controleerOpNullWaarden(PARTIJ_MAG_NIET_NULL_ZIJN, partij);
        this.partij = partij;
        setBijhoudingsaard(bijhoudingsaard);
        setNadereBijhoudingsaard(nadereBijhoudingsaard);
        this.indicatieOnverwerktDocumentAanwezig = indicatieOnverwerktDocumentAanwezig;
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
     * Geef de waarde van bijhoudingsaard.
     *
     * @return bijhoudingsaard
     */
    public Bijhoudingsaard getBijhoudingsaard() {
        return Bijhoudingsaard.parseId(bijhoudingsaardId);
    }

    /**
     * Zet de waarde van bijhoudingsaard.
     *
     * @param bijhoudingsaard
     *            bijhoudingsaard
     */
    public void setBijhoudingsaard(final Bijhoudingsaard bijhoudingsaard) {
        ValidationUtils.controleerOpNullWaarden("bijhoudingsaard mag niet null zijn", bijhoudingsaard);
        bijhoudingsaardId = bijhoudingsaard.getId();
    }

    /**
     * Geef de waarde van nadere bijhoudingsaard.
     *
     * @return nadere bijhoudingsaard
     */
    public NadereBijhoudingsaard getNadereBijhoudingsaard() {
        return NadereBijhoudingsaard.parseId(nadereBijhoudingsaardId);
    }

    /**
     * Zet de waarde van nadere bijhoudingsaard.
     *
     * @param nadereBijhoudingsaard
     *            nadere bijhoudingsaard
     */
    public void setNadereBijhoudingsaard(final NadereBijhoudingsaard nadereBijhoudingsaard) {
        ValidationUtils.controleerOpNullWaarden("nadereBijhoudingsaard mag niet null zijn", nadereBijhoudingsaard);
        nadereBijhoudingsaardId = nadereBijhoudingsaard.getId();
    }

    /**
     * Geef de waarde van indicatie onverwerkt document aanwezig.
     *
     * @return indicatie onverwerkt document aanwezig
     */
    public Boolean getIndicatieOnverwerktDocumentAanwezig() {
        return indicatieOnverwerktDocumentAanwezig;
    }

    /**
     * Zet de waarde van indicatie onverwerkt document aanwezig.
     *
     * @param indicatieOnverwerktDocumentAanwezig
     *            indicatie onverwerkt document aanwezig
     */
    public void setIndicatieOnverwerktDocumentAanwezig(final Boolean indicatieOnverwerktDocumentAanwezig) {
        this.indicatieOnverwerktDocumentAanwezig = indicatieOnverwerktDocumentAanwezig;
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
        ValidationUtils.controleerOpNullWaarden(PARTIJ_MAG_NIET_NULL_ZIJN, partij);
        this.partij = partij;
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
        ValidationUtils.controleerOpNullWaarden(PERSOON_MAG_NIET_NULL_ZIJN, persoon);
        this.persoon = persoon;
    }
}

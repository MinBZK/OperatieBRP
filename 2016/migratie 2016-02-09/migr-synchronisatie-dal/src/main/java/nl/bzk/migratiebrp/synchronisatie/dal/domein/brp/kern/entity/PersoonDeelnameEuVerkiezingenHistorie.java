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
 * The persistent class for the his_perseuverkiezingen database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_persdeelneuverkiezingen", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"pers", "tsreg" }))
@SuppressWarnings("checkstyle:designforextension")
public class PersoonDeelnameEuVerkiezingenHistorie extends AbstractFormeleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_perseuverkiezingen_id_generator", sequenceName = "kern.seq_his_persdeelneuverkiezingen", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_perseuverkiezingen_id_generator")
    @Column(nullable = false)
    /**
     * Het veld zou Integer moeten zijn maar is Long i.v.m. de link naar GegevenInOnderzoek.
     */
    private Long id;

    @Column(name = "dataanlaanpdeelneuverkiezing")
    private Integer datumAanleidingAanpassingDeelnameEuVerkiezingen;

    @Column(name = "datvoorzeindeuitsleuverkiezi")
    private Integer datumVoorzienEindeUitsluitingEuVerkiezingen;

    @Column(name = "inddeelneuverkiezingen", nullable = false)
    private boolean indicatieDeelnameEuVerkiezingen;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    /**
     * JPA default constructor.
     */
    protected PersoonDeelnameEuVerkiezingenHistorie() {
    }

    /**
     * Maak een nieuwe persoon deelname eu verkiezingen historie.
     *
     * @param persoon
     *            persoon
     * @param indicatieDeelnameEuVerkiezingen
     *            indicatie deelname eu verkiezingen
     */
    public PersoonDeelnameEuVerkiezingenHistorie(final Persoon persoon, final boolean indicatieDeelnameEuVerkiezingen) {
        setPersoon(persoon);
        setIndicatieDeelnameEuVerkiezingen(indicatieDeelnameEuVerkiezingen);
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
     * Geef de waarde van datum aanleiding aanpassing deelname eu verkiezingen.
     *
     * @return datum aanleiding aanpassing deelname eu verkiezingen
     */
    public Integer getDatumAanleidingAanpassingDeelnameEuVerkiezingen() {
        return datumAanleidingAanpassingDeelnameEuVerkiezingen;
    }

    /**
     * Zet de waarde van datum aanleiding aanpassing deelname eu verkiezingen.
     *
     * @param datumAanleidingAanpassingDeelnameEuVerkiezingen
     *            datum aanleiding aanpassing deelname eu verkiezingen
     */
    public void setDatumAanleidingAanpassingDeelnameEuVerkiezingen(final Integer datumAanleidingAanpassingDeelnameEuVerkiezingen) {
        this.datumAanleidingAanpassingDeelnameEuVerkiezingen = datumAanleidingAanpassingDeelnameEuVerkiezingen;
    }

    /**
     * Geef de waarde van datum voorzien einde uitsluiting eu verkiezingen.
     *
     * @return datum voorzien einde uitsluiting eu verkiezingen
     */
    public Integer getDatumVoorzienEindeUitsluitingEuVerkiezingen() {
        return datumVoorzienEindeUitsluitingEuVerkiezingen;
    }

    /**
     * Zet de waarde van datum voorzien einde uitsluiting eu verkiezingen.
     *
     * @param datumVoorzienEindeUitsluitingEuVerkiezingen
     *            datum voorzien einde uitsluiting eu verkiezingen
     */
    public void setDatumVoorzienEindeUitsluitingEuVerkiezingen(final Integer datumVoorzienEindeUitsluitingEuVerkiezingen) {
        this.datumVoorzienEindeUitsluitingEuVerkiezingen = datumVoorzienEindeUitsluitingEuVerkiezingen;
    }

    /**
     * Geef de waarde van indicatie deelname eu verkiezingen.
     *
     * @return indicatie deelname eu verkiezingen
     */
    public Boolean getIndicatieDeelnameEuVerkiezingen() {
        return indicatieDeelnameEuVerkiezingen;
    }

    /**
     * Zet de waarde van indicatie deelname eu verkiezingen.
     *
     * @param indicatieDeelnameEuVerkiezingen
     *            indicatie deelname eu verkiezingen
     */
    public void setIndicatieDeelnameEuVerkiezingen(final Boolean indicatieDeelnameEuVerkiezingen) {
        this.indicatieDeelnameEuVerkiezingen = indicatieDeelnameEuVerkiezingen;
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
}

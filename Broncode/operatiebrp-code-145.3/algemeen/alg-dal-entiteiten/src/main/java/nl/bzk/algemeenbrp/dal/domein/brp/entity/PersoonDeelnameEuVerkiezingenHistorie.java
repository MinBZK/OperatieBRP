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
 * The persistent class for the his_perseuverkiezingen database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_persdeelneuverkiezingen", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"pers", "tsreg"}))
public class PersoonDeelnameEuVerkiezingenHistorie extends AbstractFormeleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_perseuverkiezingen_id_generator", sequenceName = "kern.seq_his_persdeelneuverkiezingen", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_perseuverkiezingen_id_generator")
    @Column(nullable = false)
    private Long id;

    @Column(name = "dataanlaanpdeelneuverkiezing")
    private Integer datumAanleidingAanpassingDeelnameEuVerkiezingen;

    @Column(name = "datvoorzeindeuitsleuverkiezi")
    private Integer datumVoorzienEindeUitsluitingEuVerkiezingen;

    @Column(name = "inddeelneuverkiezingen", nullable = false)
    private boolean indicatieDeelnameEuVerkiezingen;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    /**
     * JPA default constructor.
     */
    protected PersoonDeelnameEuVerkiezingenHistorie() {}

    /**
     * Maak een nieuwe persoon deelname eu verkiezingen historie.
     *
     * @param persoon persoon
     * @param indicatieDeelnameEuVerkiezingen indicatie deelname eu verkiezingen
     */
    public PersoonDeelnameEuVerkiezingenHistorie(final Persoon persoon, final boolean indicatieDeelnameEuVerkiezingen) {
        setPersoon(persoon);
        setIndicatieDeelnameEuVerkiezingen(indicatieDeelnameEuVerkiezingen);
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
     * Zet de waarden voor id van PersoonDeelnameEuVerkiezingenHistorie.
     *
     * @param id de nieuwe waarde voor id van PersoonDeelnameEuVerkiezingenHistorie
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van datum aanleiding aanpassing deelname eu verkiezingen van
     * PersoonDeelnameEuVerkiezingenHistorie.
     *
     * @return de waarde van datum aanleiding aanpassing deelname eu verkiezingen van
     *         PersoonDeelnameEuVerkiezingenHistorie
     */
    public Integer getDatumAanleidingAanpassingDeelnameEuVerkiezingen() {
        return datumAanleidingAanpassingDeelnameEuVerkiezingen;
    }

    /**
     * Zet de waarden voor datum aanleiding aanpassing deelname eu verkiezingen van
     * PersoonDeelnameEuVerkiezingenHistorie.
     *
     * @param datumAanleidingAanpassingDeelnameEuVerkiezingen de nieuwe waarde voor datum aanleiding
     *        aanpassing deelname eu verkiezingen van PersoonDeelnameEuVerkiezingenHistorie
     */
    public void setDatumAanleidingAanpassingDeelnameEuVerkiezingen(final Integer datumAanleidingAanpassingDeelnameEuVerkiezingen) {
        this.datumAanleidingAanpassingDeelnameEuVerkiezingen = datumAanleidingAanpassingDeelnameEuVerkiezingen;
    }

    /**
     * Geef de waarde van datum voorzien einde uitsluiting eu verkiezingen van
     * PersoonDeelnameEuVerkiezingenHistorie.
     *
     * @return de waarde van datum voorzien einde uitsluiting eu verkiezingen van
     *         PersoonDeelnameEuVerkiezingenHistorie
     */
    public Integer getDatumVoorzienEindeUitsluitingEuVerkiezingen() {
        return datumVoorzienEindeUitsluitingEuVerkiezingen;
    }

    /**
     * Zet de waarden voor datum voorzien einde uitsluiting eu verkiezingen van
     * PersoonDeelnameEuVerkiezingenHistorie.
     *
     * @param datumVoorzienEindeUitsluitingEuVerkiezingen de nieuwe waarde voor datum voorzien einde
     *        uitsluiting eu verkiezingen van PersoonDeelnameEuVerkiezingenHistorie
     */
    public void setDatumVoorzienEindeUitsluitingEuVerkiezingen(final Integer datumVoorzienEindeUitsluitingEuVerkiezingen) {
        this.datumVoorzienEindeUitsluitingEuVerkiezingen = datumVoorzienEindeUitsluitingEuVerkiezingen;
    }

    /**
     * Geef de waarde van indicatie deelname eu verkiezingen van
     * PersoonDeelnameEuVerkiezingenHistorie.
     *
     * @return de waarde van indicatie deelname eu verkiezingen van
     *         PersoonDeelnameEuVerkiezingenHistorie
     */
    public Boolean getIndicatieDeelnameEuVerkiezingen() {
        return indicatieDeelnameEuVerkiezingen;
    }

    /**
     * Zet de waarden voor indicatie deelname eu verkiezingen van
     * PersoonDeelnameEuVerkiezingenHistorie.
     *
     * @param indicatieDeelnameEuVerkiezingen de nieuwe waarde voor indicatie deelname eu
     *        verkiezingen van PersoonDeelnameEuVerkiezingenHistorie
     */
    public void setIndicatieDeelnameEuVerkiezingen(final Boolean indicatieDeelnameEuVerkiezingen) {
        this.indicatieDeelnameEuVerkiezingen = indicatieDeelnameEuVerkiezingen;
    }

    /**
     * Geef de waarde van persoon van PersoonDeelnameEuVerkiezingenHistorie.
     *
     * @return de waarde van persoon van PersoonDeelnameEuVerkiezingenHistorie
     */
    public Persoon getPersoon() {
        return persoon;
    }

    /**
     * Zet de waarden voor persoon van PersoonDeelnameEuVerkiezingenHistorie.
     *
     * @param persoon de nieuwe waarde voor persoon van PersoonDeelnameEuVerkiezingenHistorie
     */
    public void setPersoon(final Persoon persoon) {
        ValidationUtils.controleerOpNullWaarden("persoon mag niet null zijn", persoon);
        this.persoon = persoon;
    }
}

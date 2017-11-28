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
 * The persistent class for the his_betrouderlijkgezag database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_ouderouderlijkGezag", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"betr", "tsreg", "dataanvgel"}))
public class BetrokkenheidOuderlijkGezagHistorie extends AbstractMaterieleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String BETROKKENHEID_MAG_NIET_NULL_ZIJN = "betrokkenheid mag niet null zijn";
    private static final String INDICATIE_OUDER_HEEFT_GEZAG_MAG_NIET_NULL_ZIJN = "indicatieOuderHeeftGezag mag niet null zijn";

    @Id
    @SequenceGenerator(name = "his_ouderouderlijkgezag_id_generator", sequenceName = "kern.seq_his_ouderouderlijkgezag", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_ouderouderlijkgezag_id_generator")
    @Column(nullable = false)
    private Long id;

    @Column(name = "indouderheeftgezag", nullable = false)
    private Boolean indicatieOuderHeeftGezag;

    // bi-directional many-to-one association to Betrokkenheid
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "betr", nullable = false, updatable = false)
    private Betrokkenheid betrokkenheid;

    /**
     * JPA default constructor.
     */
    protected BetrokkenheidOuderlijkGezagHistorie() {}

    /**
     * Maak een nieuwe betrokkenheid ouderlijk gezag historie.
     *
     * @param betrokkenheid betrokkenheid
     * @param indicatieOuderHeeftGezag indicatie ouder heeft gezag
     */
    public BetrokkenheidOuderlijkGezagHistorie(final Betrokkenheid betrokkenheid, final Boolean indicatieOuderHeeftGezag) {
        setBetrokkenheid(betrokkenheid);
        setIndicatieOuderHeeftGezag(indicatieOuderHeeftGezag);
    }

    /**
     * Kopie constructor. Maakt een nieuw object op basis van het gegeven bron object.
     *
     * @param ander het te kopieren object
     */
    public BetrokkenheidOuderlijkGezagHistorie(final BetrokkenheidOuderlijkGezagHistorie ander) {
        super(ander);
        indicatieOuderHeeftGezag = ander.getIndicatieOuderHeeftGezag();
        betrokkenheid = ander.getBetrokkenheid();
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
     * Zet de waarden voor id van BetrokkenheidOuderlijkGezagHistorie.
     *
     * @param id de nieuwe waarde voor id van BetrokkenheidOuderlijkGezagHistorie
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van indicatie ouder heeft gezag van BetrokkenheidOuderlijkGezagHistorie.
     *
     * @return de waarde van indicatie ouder heeft gezag van BetrokkenheidOuderlijkGezagHistorie
     */
    public Boolean getIndicatieOuderHeeftGezag() {
        return indicatieOuderHeeftGezag;
    }

    /**
     * Zet de waarden voor indicatie ouder heeft gezag van BetrokkenheidOuderlijkGezagHistorie.
     *
     * @param indicatieOuderHeeftGezag de nieuwe waarde voor indicatie ouder heeft gezag van
     *        BetrokkenheidOuderlijkGezagHistorie
     */
    public void setIndicatieOuderHeeftGezag(final Boolean indicatieOuderHeeftGezag) {
        ValidationUtils.controleerOpNullWaarden(INDICATIE_OUDER_HEEFT_GEZAG_MAG_NIET_NULL_ZIJN, indicatieOuderHeeftGezag);
        this.indicatieOuderHeeftGezag = indicatieOuderHeeftGezag;
    }

    /**
     * Geef de waarde van betrokkenheid van BetrokkenheidOuderlijkGezagHistorie.
     *
     * @return de waarde van betrokkenheid van BetrokkenheidOuderlijkGezagHistorie
     */
    public Betrokkenheid getBetrokkenheid() {
        return betrokkenheid;
    }

    /**
     * Zet de waarden voor betrokkenheid van BetrokkenheidOuderlijkGezagHistorie.
     *
     * @param betrokkenheid de nieuwe waarde voor betrokkenheid van
     *        BetrokkenheidOuderlijkGezagHistorie
     */
    public void setBetrokkenheid(final Betrokkenheid betrokkenheid) {
        ValidationUtils.controleerOpNullWaarden(BETROKKENHEID_MAG_NIET_NULL_ZIJN, betrokkenheid);
        this.betrokkenheid = betrokkenheid;
    }

    @Override
    public final BetrokkenheidOuderlijkGezagHistorie kopieer() {
        return new BetrokkenheidOuderlijkGezagHistorie(this);
    }

    @Override
    public Persoon getPersoon() {
        return getBetrokkenheid().getPersoon();
    }
}

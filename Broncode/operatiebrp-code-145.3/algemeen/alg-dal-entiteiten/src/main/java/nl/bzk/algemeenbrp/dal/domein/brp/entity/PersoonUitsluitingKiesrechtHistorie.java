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
 * The persistent class for the his_persuitslnlkiesr database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_persuitslkiesr", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"pers", "tsreg"}))
public class PersoonUitsluitingKiesrechtHistorie extends AbstractFormeleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_persuitslnlkiesr_id_generator", sequenceName = "kern.seq_his_persuitslkiesr", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_persuitslnlkiesr_id_generator")
    @Column(nullable = false)
    private Long id;

    @Column(name = "datvoorzeindeuitslkiesr")
    private Integer datumVoorzienEindeUitsluitingKiesrecht;

    @Column(name = "induitslkiesr", nullable = false)
    private boolean indicatieUitsluitingKiesrecht;

    // bi-directional many-to-one association to Persoon
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    /**
     * JPA default constructor.
     */
    protected PersoonUitsluitingKiesrechtHistorie() {}

    /**
     * Maak een nieuwe persoon uitsluiting kiesrecht historie.
     *
     * @param persoon persoon
     * @param indicatieUitsluitingKiesrecht indicatie uitsluiting kiesrecht
     */
    public PersoonUitsluitingKiesrechtHistorie(final Persoon persoon, final boolean indicatieUitsluitingKiesrecht) {
        setIndicatieUitsluitingKiesrecht(indicatieUitsluitingKiesrecht);
        setPersoon(persoon);
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
     * Zet de waarden voor id van PersoonUitsluitingKiesrechtHistorie.
     *
     * @param id de nieuwe waarde voor id van PersoonUitsluitingKiesrechtHistorie
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van datum voorzien einde uitsluiting kiesrecht van
     * PersoonUitsluitingKiesrechtHistorie.
     *
     * @return de waarde van datum voorzien einde uitsluiting kiesrecht van
     *         PersoonUitsluitingKiesrechtHistorie
     */
    public Integer getDatumVoorzienEindeUitsluitingKiesrecht() {
        return datumVoorzienEindeUitsluitingKiesrecht;
    }

    /**
     * Zet de waarden voor datum voorzien einde uitsluiting kiesrecht van
     * PersoonUitsluitingKiesrechtHistorie.
     *
     * @param datumVoorzienEindeUitsluitingKiesrecht de nieuwe waarde voor datum voorzien einde
     *        uitsluiting kiesrecht van PersoonUitsluitingKiesrechtHistorie
     */
    public void setDatumVoorzienEindeUitsluitingKiesrecht(final Integer datumVoorzienEindeUitsluitingKiesrecht) {
        this.datumVoorzienEindeUitsluitingKiesrecht = datumVoorzienEindeUitsluitingKiesrecht;
    }

    /**
     * Geef de waarde van indicatie uitsluiting kiesrecht van PersoonUitsluitingKiesrechtHistorie.
     *
     * @return de waarde van indicatie uitsluiting kiesrecht van PersoonUitsluitingKiesrechtHistorie
     */
    public boolean getIndicatieUitsluitingKiesrecht() {
        return indicatieUitsluitingKiesrecht;
    }

    /**
     * Zet de waarden voor indicatie uitsluiting kiesrecht van PersoonUitsluitingKiesrechtHistorie.
     *
     * @param indicatieUitsluitingKiesrecht de nieuwe waarde voor indicatie uitsluiting kiesrecht
     *        van PersoonUitsluitingKiesrechtHistorie
     */
    public void setIndicatieUitsluitingKiesrecht(final boolean indicatieUitsluitingKiesrecht) {
        this.indicatieUitsluitingKiesrecht = indicatieUitsluitingKiesrecht;
    }

    /**
     * Geef de waarde van persoon van PersoonUitsluitingKiesrechtHistorie.
     *
     * @return de waarde van persoon van PersoonUitsluitingKiesrechtHistorie
     */
    public Persoon getPersoon() {
        return persoon;
    }

    /**
     * Zet de waarden voor persoon van PersoonUitsluitingKiesrechtHistorie.
     *
     * @param persoon de nieuwe waarde voor persoon van PersoonUitsluitingKiesrechtHistorie
     */
    public void setPersoon(final Persoon persoon) {
        ValidationUtils.controleerOpNullWaarden("persoon mag niet null zijn", persoon);
        this.persoon = persoon;
    }
}

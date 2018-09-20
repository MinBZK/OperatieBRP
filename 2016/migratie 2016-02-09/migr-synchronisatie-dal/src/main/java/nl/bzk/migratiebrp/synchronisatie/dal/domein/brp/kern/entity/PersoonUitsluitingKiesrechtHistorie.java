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
 * The persistent class for the his_persuitslnlkiesr database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_persuitslkiesr", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"pers", "tsreg" }))
@SuppressWarnings("checkstyle:designforextension")
public class PersoonUitsluitingKiesrechtHistorie extends AbstractFormeleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_persuitslnlkiesr_id_generator", sequenceName = "kern.seq_his_persuitslkiesr", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_persuitslnlkiesr_id_generator")
    @Column(nullable = false)
    /**
     * Het veld zou Integer moeten zijn maar is Long i.v.m. de link naar GegevenInOnderzoek.
     */
    private Long id;

    @Column(name = "datvoorzeindeuitslkiesr")
    private Integer datumVoorzienEindeUitsluitingKiesrecht;

    @Column(name = "induitslkiesr", nullable = false)
    private boolean indicatieUitsluitingKiesrecht;

    // bi-directional many-to-one association to Persoon
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    /**
     * JPA default constructor.
     */
    protected PersoonUitsluitingKiesrechtHistorie() {
    }

    /**
     * Maak een nieuwe persoon uitsluiting kiesrecht historie.
     *
     * @param persoon
     *            persoon
     * @param indicatieUitsluitingKiesrecht
     *            indicatie uitsluiting kiesrecht
     */
    public PersoonUitsluitingKiesrechtHistorie(final Persoon persoon, final boolean indicatieUitsluitingKiesrecht) {
        setIndicatieUitsluitingKiesrecht(indicatieUitsluitingKiesrecht);
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
     * Geef de waarde van datum voorzien einde uitsluiting kiesrecht.
     *
     * @return datum voorzien einde uitsluiting kiesrecht
     */
    public Integer getDatumVoorzienEindeUitsluitingKiesrecht() {
        return datumVoorzienEindeUitsluitingKiesrecht;
    }

    /**
     * Zet de waarde van datum voorzien einde uitsluiting kiesrecht.
     *
     * @param datumVoorzienEindeUitsluitingKiesrecht
     *            datum voorzien einde uitsluiting kiesrecht
     */
    public void setDatumVoorzienEindeUitsluitingKiesrecht(final Integer datumVoorzienEindeUitsluitingKiesrecht) {
        this.datumVoorzienEindeUitsluitingKiesrecht = datumVoorzienEindeUitsluitingKiesrecht;
    }

    /**
     * Geef de waarde van indicatie uitsluiting kiesrecht.
     *
     * @return indicatie uitsluiting kiesrecht
     */
    public boolean getIndicatieUitsluitingKiesrecht() {
        return indicatieUitsluitingKiesrecht;
    }

    /**
     * Zet de waarde van indicatie uitsluiting kiesrecht.
     *
     * @param indicatieUitsluitingKiesrecht
     *            indicatie uitsluiting kiesrecht
     */
    public void setIndicatieUitsluitingKiesrecht(final boolean indicatieUitsluitingKiesrecht) {
        this.indicatieUitsluitingKiesrecht = indicatieUitsluitingKiesrecht;
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

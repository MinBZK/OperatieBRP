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
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;

/**
 * The persistent class for the gegeveninonderzoek database table.
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_gegeveninonderzoek", schema = "kern")
public class GegevenInOnderzoekHistorie extends AbstractFormeleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_gegeveninonderzoek_id_generator", sequenceName = "kern.seq_his_gegeveninonderzoek", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_gegeveninonderzoek_id_generator")
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "gegeveninonderzoek", nullable = false)
    private GegevenInOnderzoek gegevenInOnderzoek;


    /**
     * JPA default constructor.
     */
    protected GegevenInOnderzoekHistorie() {
    }

    /**
     * Maak een nieuwe gegeven in onderzoek Historie aan.
     * @param gegevenInOnderzoek onderzoek
     */
    public GegevenInOnderzoekHistorie(final GegevenInOnderzoek gegevenInOnderzoek) {
        setGegevenInOnderzoek(gegevenInOnderzoek);
    }

    /**
     * Maak een nieuwe gegeven in onderzoek Historie aan.
     * @param gegevenInOnderzoek onderzoek
     * @param actieInhoud actie inhoud
     */
    public GegevenInOnderzoekHistorie(final GegevenInOnderzoek gegevenInOnderzoek, final BRPActie actieInhoud) {
        setGegevenInOnderzoek(gegevenInOnderzoek);
        ValidationUtils.controleerOpNullWaarden("actieInhoud mag niet null zijn", actieInhoud);
        setDatumTijdRegistratie(actieInhoud.getDatumTijdRegistratie());
        setActieInhoud(actieInhoud);
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
     * Zet de waarden voor id van GegevenInOnderzoek.
     * @param id de nieuwe waarde voor id van GegevenInOnderzoek
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geeft het gerelateerde gegevenInOnderzoek.
     * @return GegevenInOnderzoek
     */
    public GegevenInOnderzoek getGegevenInOnderzoek() {
        return gegevenInOnderzoek;
    }

    /**
     * Zet de waarden voor onderzoek van GegevenInOnderzoek.
     * @param gegevenInOnderzoek {@link GegevenInOnderzoek}
     */
    @Override
    public void setGegevenInOnderzoek(final GegevenInOnderzoek gegevenInOnderzoek) {
        ValidationUtils.controleerOpNullWaarden("onderzoek mag niet null zijn", gegevenInOnderzoek);
        this.gegevenInOnderzoek = gegevenInOnderzoek;
    }
}

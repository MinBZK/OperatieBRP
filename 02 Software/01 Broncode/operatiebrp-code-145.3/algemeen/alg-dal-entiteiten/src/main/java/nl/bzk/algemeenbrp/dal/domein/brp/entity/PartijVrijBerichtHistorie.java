/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;

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
 * The persistent class for the his_partijbijhouding database table.
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_partijvrijber", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"id"}))
public class PartijVrijBerichtHistorie extends AbstractFormeleHistorieZonderVerantwoording implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_partijvrijber_id_generator", sequenceName = "kern.seq_his_partijvrijber", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_partijvrijber_id_generator")
    @Column(nullable = false)
    /**
     * Het veld zou Integer moeten zijn maar is Long i.v.m. de link naar GegevenInOnderzoek.
     */
    private Long id;

    // uni-directional many-to-one association to Partij
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partij", nullable = false, updatable = false)
    private Partij partij;

    // uni-directional many-to-one association to Partij
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ondertekenaarvrijber")
    private Partij ondertekenaarVrijBericht;

    // uni-directional many-to-one association to Partij
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transporteurvrijber")
    private Partij transporteurVrijBericht;

    @Column(name = "datingangvrijber", nullable = false)
    private Integer datumIngangVrijBericht;

    @Column(name = "dateindevrijber")
    private Integer datumEindeVrijBericht;

    @Column(name = "afleverpuntvrijber")
    private String afleverpuntVrijBericht;

    @Column(name = "indblokvrijber")
    private Boolean isVrijBerichtGeblokkeerd;

    /**
     * JPA default constructor.
     */
    protected PartijVrijBerichtHistorie() {
    }

    @Override
    public Number getId() {
        return id;
    }

    /**
     * Geef de waarde van partij van PartijVrijBerichtHistorie.
     *
     * @return de waarde van partij van PartijVrijBerichtHistorie
     */
    public Partij getPartij() {
        return partij;
    }

    /**
     * Zet de waarden voor partij van PartijVrijBerichtHistorie.
     *
     * @param partij de nieuwe waarde voor partij van PartijVrijBerichtHistorie
     */
    public void setPartij(final Partij partij) {
        ValidationUtils.controleerOpNullWaarden("partij mag niet null zijn", partij);
        this.partij = partij;
    }

    /**
     * Geef de ondertekenaar van het vrije bericht
     *
     * @return een partij
     */
    public Partij getOndertekenaarVrijBericht() {
        return ondertekenaarVrijBericht;
    }

    /**
     * Zet de ondertekenaar van het vrije bericht
     *
     * @param ondertekenaarVrijBericht een partij
     */
    public void setOndertekenaarVrijBericht(final Partij ondertekenaarVrijBericht) {
        this.ondertekenaarVrijBericht = ondertekenaarVrijBericht;
    }

    /**
     * Geef de transporteur van het vrije bericht
     *
     * @return een partij
     */
    public Partij getTransporteurVrijBericht() {
        return transporteurVrijBericht;
    }

    /**
     * Zet de transporteur van het vrije bericht
     *
     * @param transporteurVrijBericht een partij
     */
    public void setTransporteurVrijBericht(final Partij transporteurVrijBericht) {
        this.transporteurVrijBericht = transporteurVrijBericht;
    }

    /**
     * Geef de datum ingang voor het versturen van het vrije bericht
     *
     * @return een datum
     */
    public Integer getDatumIngangVrijBericht() {
        return datumIngangVrijBericht;
    }

    /**
     * Zet de datumIngang voor het versturen van een vrij bericht.
     *
     * @param datumIngangVrijBericht een datum
     */
    public void setDatumIngangVrijBericht(final Integer datumIngangVrijBericht) {
        this.datumIngangVrijBericht = datumIngangVrijBericht;
    }

    /**
     * Geef de datum einde voor het versturen van het vrije bericht
     *
     * @return een datum
     */
    public Integer getDatumEindeVrijBericht() {
        return datumEindeVrijBericht;
    }

    /**
     * Zet de datumEinde voor het versturen van een vrij bericht
     *
     * @param datumEindeVrijBericht een datum
     */
    public void setDatumEindeVrijBericht(final Integer datumEindeVrijBericht) {
        this.datumEindeVrijBericht = datumEindeVrijBericht;
    }

    /**
     * Geef het afleverpunt voor het versturen van het vrije bericht
     *
     * @return een datum
     */
    public String getAfleverpuntVrijBericht() {
        return afleverpuntVrijBericht;
    }

    /**
     * Zet het afleverpunt voor het versturen van een vrij bericht
     *
     * @param afleverpuntVrijBericht een afleverpunt (URI)
     */
    public void setAfleverpuntVrijBericht(final String afleverpuntVrijBericht) {
        this.afleverpuntVrijBericht = afleverpuntVrijBericht;
    }

    /**
     * Geeft indicatie of het versturen van een vrij bericht geblokkeerd is.
     *
     * @return een boolean indicatie
     */
    public Boolean isVrijBerichtGeblokkeerd() {
        return isVrijBerichtGeblokkeerd;
    }

    /**
     * Zet de indicatie of het versturen van een vrij bericht geblokkeerd is.
     *
     * @param isVrijBerichtGeblokkeerd een boolean indicatie
     */
    public void setVrijBerichtGeblokkeerd(final Boolean isVrijBerichtGeblokkeerd) {
        if (Boolean.FALSE.equals(isVrijBerichtGeblokkeerd)) {
            throw new IllegalArgumentException("Is vrij bericht geblokkeerd moet null of TRUE zijn");
        }
        this.isVrijBerichtGeblokkeerd = isVrijBerichtGeblokkeerd;
    }

}

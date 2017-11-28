/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.sql.Timestamp;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLInsert;

/**
 * levsaantekpers entiteit uit het prot-schema.
 */
@Entity
@Table(name = "levsaantekpers", schema = "prot")
@IdClass(LeveringsaantekeningPersoonSleutel.class)
@SQLInsert(sql = "insert into prot.levsaantekpers (dataanvmaterieleperiode, tsklaarzettenlev, tslaatstewijzpers, levsaantek, pers) values (?, ?, ?, ?, ?)",
check = ResultCheckStyle.NONE)
public class LeveringsaantekeningPersoon {

    @Id
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "levsaantek", nullable = false, updatable = false)
    private Leveringsaantekening leveringsaantekening;

    @Column(name = "tsklaarzettenlev", nullable = false)
    private Timestamp datumTijdKlaarzettenLevering;

    @Id
    @Column(name = "pers", nullable = false, updatable = false)
    private Long persoon;

    @Column(name = "tslaatstewijzpers", nullable = false)
    private Timestamp datumTijdLaatsteWijzigingPersoon;

    @Column(name = "dataanvmaterieleperiode")
    private Integer datumAanvangMaterielePeriode;

    /**
     * JPA Default constructor.
     */
    protected LeveringsaantekeningPersoon() {
    }

    /**
     * Maakt een nieuw LeveringsaantekeningPersoon object.
     * @param leveringsaantekening leveringsaantekening, mag niet null zijn
     * @param persoon persoon, mag niet null zijn
     * @param datumTijdLaatsteWijzigingPersoon datumTijdLaatsteWijzigingPersoon, mag niet null zijn
     * @param datumTijdKlaarzettenLevering datumTijdKlaarzettenLevering mag niet null zijn
     */
    public LeveringsaantekeningPersoon(final Leveringsaantekening leveringsaantekening, final Long persoon, final Timestamp datumTijdLaatsteWijzigingPersoon,
                                       final Timestamp datumTijdKlaarzettenLevering) {
        setLeveringsaantekening(leveringsaantekening);
        setPersoon(persoon);
        setDatumTijdLaatsteWijzigingPersoon(datumTijdLaatsteWijzigingPersoon);
        setDatumTijdKlaarzettenLevering(datumTijdKlaarzettenLevering);
    }

    /**
     * Geef de waarde van leveringsaantekening.
     * @return leveringsaantekening
     */
    public Leveringsaantekening getLeveringsaantekening() {
        return leveringsaantekening;
    }

    /**
     * Zet de waarde van leveringsaantekening.
     * @param leveringsaantekening leveringsaantekening, mag niet null zijn
     */
    public void setLeveringsaantekening(final Leveringsaantekening leveringsaantekening) {
        ValidationUtils.controleerOpNullWaarden("leveringsaantekening mag niet null zijn", leveringsaantekening);
        this.leveringsaantekening = leveringsaantekening;
    }

    /**
     * Geeft de waarde van datumTijdKlaarzettenLevering.
     * @return datumTijdKlaarzettenLevering
     */
    public Timestamp getDatumTijdKlaarzettenLevering() {
        return Entiteit.timestamp(datumTijdKlaarzettenLevering);
    }

    /**
     * Zet de waarde van datumTijdKlaarzettenLevering.
     * @param datumTijdKlaarzettenLevering datumTijdKlaarzettenLevering
     */
    public void setDatumTijdKlaarzettenLevering(final Timestamp datumTijdKlaarzettenLevering) {
        ValidationUtils.controleerOpNullWaarden("datumTijdKlaarzettenLevering mag niet null zijn", datumTijdKlaarzettenLevering);
        this.datumTijdKlaarzettenLevering = Entiteit.timestamp(datumTijdKlaarzettenLevering);
    }

    /**
     * Geef de waarde van persoon.
     * @return persoon
     */
    public Long getPersoon() {
        return persoon;
    }

    /**
     * Zet de waarde van persoon.
     * @param persoon persoon, mag niet null zijn
     */
    public void setPersoon(final Long persoon) {
        ValidationUtils.controleerOpNullWaarden("persoon", persoon);
        this.persoon = persoon;
    }

    /**
     * Geef de waarde van datumTijdLaatsteWijzigingPersoon.
     * @return datumTijdLaatsteWijzigingPersoon
     */
    public Timestamp getDatumTijdLaatsteWijzigingPersoon() {
        return Entiteit.timestamp(datumTijdLaatsteWijzigingPersoon);
    }

    /**
     * Zet de waarde van datumTijdLaatsteWijzigingPersoon.
     * @param datumTijdLaatsteWijzigingPersoon datumTijdLaatsteWijzigingPersoon, mag niet null zijn
     */
    public void setDatumTijdLaatsteWijzigingPersoon(final Timestamp datumTijdLaatsteWijzigingPersoon) {
        ValidationUtils.controleerOpNullWaarden("datumTijdLaatsteWijzigingPersoon", datumTijdLaatsteWijzigingPersoon);
        this.datumTijdLaatsteWijzigingPersoon = Entiteit.timestamp(datumTijdLaatsteWijzigingPersoon);
    }

    /**
     * Geef de waarde van datumAanvangMaterielePeriode.
     * @return datumAanvangMaterielePeriode
     */
    public Integer getDatumAanvangMaterielePeriode() {
        return datumAanvangMaterielePeriode;
    }

    /**
     * Zet de waarde van datumAanvangMaterielePeriode.
     * @param datumAanvangMaterielePeriode datumAanvangMaterielePeriode
     */
    public void setDatumAanvangMaterielePeriode(final Integer datumAanvangMaterielePeriode) {
        this.datumAanvangMaterielePeriode = datumAanvangMaterielePeriode;
    }
}

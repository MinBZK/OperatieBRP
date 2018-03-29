/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
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
 * De persistent class for the his_seltaakstatus database table.
 */
@Entity
@Table(name = "his_seltaakstatus", schema = "autaut")
public class SelectietaakStatusHistorie extends AbstractEntiteit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_seltaakstatus_id_generator", sequenceName = "autaut.seq_his_seltaakstatus", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_seltaakstatus_id_generator")
    @Column(updatable = false, nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seltaak")
    private Selectietaak selectietaak;

    @Column(name = "tsreg", nullable = false)
    private Timestamp datumTijdRegistratie;

    @Column(name = "tsverval")
    private Timestamp datumTijdVerval;

    @Column(name = "status", nullable = false)
    private Short status;

    @Column(name = "statusgewijzigddoor", nullable = false)
    private String statusGewijzigdDoor;

    @Column(name = "statustoelichting")
    private String statusToelichting;

    /**
     * JPA default constructor.
     */
    protected SelectietaakStatusHistorie() {
    }

    /**
     * Maakt een nieuw SelectietaakStatusHistorie object.
     * @param selectietaak selectietaak
     */
    public SelectietaakStatusHistorie(final Selectietaak selectietaak) {
        setSelectietaak(selectietaak);
    }


    /**
     * Kopie constructor. Maakt een nieuw object op basis van het gegeven bron object.
     * @param ander het te kopieren object
     */
    public SelectietaakStatusHistorie(final SelectietaakStatusHistorie ander) {
        this.selectietaak = ander.selectietaak;
        this.status = ander.status;
        this.statusGewijzigdDoor = ander.statusGewijzigdDoor;
        this.statusToelichting = ander.statusToelichting;
    }


    /**
     * @return het ID.
     */
    @Override
    public Number getId() {
        return id;
    }

    /**
     * Gets serial version uid.
     * @return the serial version uid
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * Sets id.
     * @param id the id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets selectietaak.
     * @return the selectietaak
     */
    public Selectietaak getSelectietaak() {
        return selectietaak;
    }

    /**
     * Sets selectietaak.
     * @param selectietaak the selectietaak
     */
    public void setSelectietaak(Selectietaak selectietaak) {
        ValidationUtils.controleerOpNullWaarden("selectietaak mag niet null zijn", selectietaak);
        this.selectietaak = selectietaak;
    }

    /**
     * Gets datum tijd registratie.
     * @return the datum tijd registratie
     */
    public Timestamp getDatumTijdRegistratie() {
        return datumTijdRegistratie;
    }

    /**
     * Sets datum tijd registratie.
     * @param datumTijdRegistratie the datum tijd registratie
     */
    public void setDatumTijdRegistratie(Timestamp datumTijdRegistratie) {
        ValidationUtils.controleerOpNullWaarden("datumTijdRegistratie mag niet null zijn", datumTijdRegistratie);
        this.datumTijdRegistratie = datumTijdRegistratie;
    }

    /**
     * Gets datum tijd verval.
     * @return the datum tijd verval
     */
    public Timestamp getDatumTijdVerval() {
        return datumTijdVerval;
    }

    /**
     * Sets datum tijd verval.
     * @param datumTijdVerval the datum tijd verval
     */
    public void setDatumTijdVerval(Timestamp datumTijdVerval) {
        this.datumTijdVerval = datumTijdVerval;
    }

    /**
     * Gets status.
     * @return the status
     */
    public Short getStatus() {
        return status;
    }

    /**
     * Sets status.
     * @param status the status
     */
    public void setStatus(Short status) {
        ValidationUtils.controleerOpNullWaarden("status mag niet null zijn", status);
        this.status = status;
    }

    /**
     * Gets status gewijzigd door.
     * @return the status gewijzigd door
     */
    public String getStatusGewijzigdDoor() {
        return statusGewijzigdDoor;
    }

    /**
     * Sets status gewijzigd door.
     * @param statusGewijzigdDoor the status gewijzigd door
     */
    public void setStatusGewijzigdDoor(String statusGewijzigdDoor) {
        ValidationUtils.controleerOpNullWaarden("statusGewijzigdDoor mag niet null zijn", statusGewijzigdDoor);
        this.statusGewijzigdDoor = statusGewijzigdDoor;
    }

    /**
     * Gets status toelichting.
     * @return the status toelichting
     */
    public String getStatusToelichting() {
        return statusToelichting;
    }

    /**
     * Sets status toelichting.
     * @param statusToelichting the status toelichting
     */
    public void setStatusToelichting(String statusToelichting) {
        this.statusToelichting = statusToelichting;
    }


}

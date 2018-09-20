/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nl.bzk.brp.model.basis.AbstractFormeleHistorieEntiteit;


/**
 * Database entiteit voor Kern.His_PersSamengesteldeNaam.
 */
@Entity
@Table(schema = "kern", name = "His_PersInschr")
@Access(AccessType.FIELD)
public class HisPersoonInschrijving extends AbstractFormeleHistorieEntiteit {

    @Id
    @SequenceGenerator(name = "HISPERSINSCHR", sequenceName = "Kern.seq_His_PersInschr")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HISPERSINSCHR")
    private Long              id;

    @ManyToOne
    @JoinColumn(name = "pers")
    private PersistentPersoon persoon;

    @Column(name = "actieinh")
    private Long              actieInh;

    @Column(name = "actieverval")
    private Integer           actieVerval;

    @Column(name = "DatInschr")
    private Integer           datInschr;

    @Column(name = "Versienr")
    private Integer           versienr;

    @ManyToOne
    @JoinColumn(name = "VorigePers")
    private PersistentPersoon vorigePers;

    @ManyToOne
    @JoinColumn(name = "VolgendePers")
    private PersistentPersoon volgendePers;

    public long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public PersistentPersoon getPersoon() {
        return persoon;
    }

    public void setPersoon(final PersistentPersoon persoon) {
        this.persoon = persoon;
    }

    public Long getActieInh() {
        return actieInh;
    }

    public void setActieInh(final Long actieInh) {
        this.actieInh = actieInh;
    }

    public Integer getActieVerval() {
        return actieVerval;
    }

    public void setActieVerval(final Integer actieVerval) {
        this.actieVerval = actieVerval;
    }

    public Integer getDatInschr() {
        return datInschr;
    }

    public void setDatInschr(final Integer datInschr) {
        this.datInschr = datInschr;
    }

    public Integer getVersienr() {
        return versienr;
    }

    public void setVersienr(final Integer versienr) {
        this.versienr = versienr;
    }

    public PersistentPersoon getVorigePers() {
        return vorigePers;
    }

    public void setVorigePers(final PersistentPersoon vorigePers) {
        this.vorigePers = vorigePers;
    }

    public PersistentPersoon getVolgendePers() {
        return volgendePers;
    }

    public void setVolgendePers(final PersistentPersoon volgendePers) {
        this.volgendePers = volgendePers;
    }
}

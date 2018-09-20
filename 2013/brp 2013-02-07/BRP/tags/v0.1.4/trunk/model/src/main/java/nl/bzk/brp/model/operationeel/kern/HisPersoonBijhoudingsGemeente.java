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

import nl.bzk.brp.model.basis.AbstractMaterieleEnFormeleHistorieEntiteit;
import nl.bzk.brp.model.gedeeld.Partij;

/**
 * Entity voor de historie van bijhoudingsgemeente.
 */
@Entity
@Table(schema = "kern", name = "his_persbijhgem")
@Access(AccessType.FIELD)
public class HisPersoonBijhoudingsGemeente extends AbstractMaterieleEnFormeleHistorieEntiteit {

    @Id
    @SequenceGenerator(name = "HISPERSOONBIJHGEM", sequenceName = "Kern.seq_His_PersBijhgem")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HISPERSOONBIJHGEM")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pers")
    private PersistentPersoon persoon;

    @ManyToOne
    @JoinColumn(name = "bijhgem")
    private Partij bijhoudingsGemeente;

    @Column(name = "datinschringem")
    private Integer datumInschrijvingInGemeente;

    @Column(name = "indonverwdocaanw")
    private Boolean indOnverwDocAanw;

    public Long getId() {
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

    public Partij getBijhoudingsGemeente() {
        return bijhoudingsGemeente;
    }

    public void setBijhoudingsGemeente(final Partij bijhoudingsGemeente) {
        this.bijhoudingsGemeente = bijhoudingsGemeente;
    }

    public Integer getDatumInschrijvingInGemeente() {
        return datumInschrijvingInGemeente;
    }

    public void setDatumInschrijvingInGemeente(final Integer datumInschrijvingInGemeente) {
        this.datumInschrijvingInGemeente = datumInschrijvingInGemeente;
    }

    public Boolean isIndOnverwDocAanw() {
        return indOnverwDocAanw;
    }

    public void setIndOnverwDocAanw(final Boolean indOnverwDocAanw) {
        this.indOnverwDocAanw = indOnverwDocAanw;
    }

    @Override
    public AbstractMaterieleEnFormeleHistorieEntiteit clone() throws CloneNotSupportedException {
        HisPersoonBijhoudingsGemeente kopie = (HisPersoonBijhoudingsGemeente) super.clone();
        kopie.id = null;
        kopie.setBijhoudingsGemeente(getBijhoudingsGemeente());
        kopie.setDatumInschrijvingInGemeente(getDatumInschrijvingInGemeente());
        kopie.setIndOnverwDocAanw(isIndOnverwDocAanw());
        kopie.setPersoon(getPersoon());

        return kopie;
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nl.bzk.brp.model.basis.AbstractMaterieleEnFormeleHistorieEntiteit;
import nl.bzk.brp.model.gedeeld.RedenVerkregenNLNationaliteit;
import nl.bzk.brp.model.gedeeld.RedenVerliesNLNationaliteit;

/**
 * Database entiteit voor Kern.His_PersNation.
 */
@Entity
@Table(schema = "kern", name = "his_persnation")
@Access(AccessType.FIELD)
public class HisPersoonNationaliteit extends AbstractMaterieleEnFormeleHistorieEntiteit {

    @Id
    @SequenceGenerator(name = "HISPERSNATION", sequenceName = "Kern.seq_His_PersNation")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HISPERSNATION")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "persnation")
    private PersistentPersoonNationaliteit persoonNationaliteit;
    @ManyToOne
    @JoinColumn(name = "rdnverlies")
    private RedenVerliesNLNationaliteit redenVerliesNLNationaliteit;
    @ManyToOne
    @JoinColumn(name = "rdnverk")
    private RedenVerkregenNLNationaliteit redenVerkregenNLNationaliteit;

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public PersistentPersoonNationaliteit getPersoonNationaliteit() {
        return persoonNationaliteit;
    }

    public void setPersoonNationaliteit(final PersistentPersoonNationaliteit persoonNationaliteit) {
        this.persoonNationaliteit = persoonNationaliteit;
    }

    public RedenVerliesNLNationaliteit getRedenVerliesNLNationaliteit() {
        return redenVerliesNLNationaliteit;
    }

    public void setRedenVerliesNLNationaliteit(final RedenVerliesNLNationaliteit redenVerliesNLNationaliteit) {
        this.redenVerliesNLNationaliteit = redenVerliesNLNationaliteit;
    }

    public RedenVerkregenNLNationaliteit getRedenVerkregenNLNationaliteit() {
        return redenVerkregenNLNationaliteit;
    }

    public void setRedenVerkregenNLNationaliteit(final RedenVerkregenNLNationaliteit redenVerkregenNLNationaliteit) {
        this.redenVerkregenNLNationaliteit = redenVerkregenNLNationaliteit;
    }

    @Override
    public HisPersoonNationaliteit clone() throws CloneNotSupportedException {
        HisPersoonNationaliteit clone = (HisPersoonNationaliteit) super.clone();
        clone.id = null;
        clone.setPersoonNationaliteit(persoonNationaliteit);
        clone.setRedenVerkregenNLNationaliteit(getRedenVerkregenNLNationaliteit());
        clone.setRedenVerliesNLNationaliteit(getRedenVerliesNLNationaliteit());
        return clone;
    }
}

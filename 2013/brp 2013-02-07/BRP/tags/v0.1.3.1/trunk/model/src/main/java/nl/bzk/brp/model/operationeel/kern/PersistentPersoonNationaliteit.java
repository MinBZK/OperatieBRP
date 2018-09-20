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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nl.bzk.brp.model.gedeeld.Nationaliteit;
import nl.bzk.brp.model.gedeeld.RedenVerkregenNLNationaliteit;
import nl.bzk.brp.model.gedeeld.RedenVerliesNLNationaliteit;
import nl.bzk.brp.model.operationeel.StatusHistorie;

/**
 * Database entiteit voor Kern.PersNation.
 */
@Entity
@Table(schema = "kern", name = "persnation")
@Access(AccessType.FIELD)
public class PersistentPersoonNationaliteit {

    @Id
    @SequenceGenerator(name = "PERSNATION", sequenceName = "Kern.seq_PersNation")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERSNATION")
    private long id;
    @ManyToOne
    @JoinColumn(name = "pers")
    private PersistentPersoon pers;
    @ManyToOne
    @JoinColumn(name = "nation")
    private Nationaliteit nationaliteit;
    @ManyToOne
    @JoinColumn(name = "rdnverlies")
    private RedenVerliesNLNationaliteit redenVerliesNLNationaliteit;
    @ManyToOne
    @JoinColumn(name = "rdnverk")
    private RedenVerkregenNLNationaliteit redenVerkregenNLNationaliteit;
    @Column(name = "persnationstatushis")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie nationaliteitStatusHistorie;

    public long getId() {
        return id;
    }

    public PersistentPersoon getPers() {
        return pers;
    }

    public void setPers(final PersistentPersoon pers) {
        this.pers = pers;
    }

    public Nationaliteit getNationaliteit() {
        return nationaliteit;
    }

    public void setNationaliteit(final Nationaliteit nationaliteit) {
        this.nationaliteit = nationaliteit;
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

    public StatusHistorie getNationaliteitStatusHistorie() {
        return nationaliteitStatusHistorie;
    }

    public void setNationaliteitStatusHistorie(final StatusHistorie nationaliteitStatusHistorie) {
        this.nationaliteitStatusHistorie = nationaliteitStatusHistorie;
    }
}

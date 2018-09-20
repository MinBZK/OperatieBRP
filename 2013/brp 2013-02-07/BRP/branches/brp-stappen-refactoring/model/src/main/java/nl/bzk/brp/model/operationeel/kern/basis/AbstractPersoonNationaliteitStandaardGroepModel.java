/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerkrijgingNLNationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerliesNLNationaliteit;
import nl.bzk.brp.model.logisch.kern.PersoonNationaliteitStandaardGroep;
import nl.bzk.brp.model.logisch.kern.basis.PersoonNationaliteitStandaardGroepBasis;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 * Vorm van historie: beiden. Motivatie: een persoon kan een bepaalde Nationaliteit verkrijgen, dan wel verliezen. Naast
 * een formele historie ('wat stond geregistreerd') is dus ook materi�le historie denkbaar ('over welke periode
 * beschikte hij over de Nederlandse Nationaliteit'). We leggen beide vast, ��k omdat dit van oudsher gebeurde vanuit de
 * GBA.
 * RvdP 17 jan 2012.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.OperationeelModelGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-21 13:38:20.
 * Gegenereerd op: Mon Jan 21 13:42:15 CET 2013.
 */
@MappedSuperclass
public abstract class AbstractPersoonNationaliteitStandaardGroepModel implements
        PersoonNationaliteitStandaardGroepBasis
{

    @ManyToOne
    @JoinColumn(name = "RdnVerk")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private RedenVerkrijgingNLNationaliteit redenVerkrijging;

    @ManyToOne
    @JoinColumn(name = "RdnVerlies")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private RedenVerliesNLNationaliteit     redenVerlies;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractPersoonNationaliteitStandaardGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param redenVerkrijging redenVerkrijging van Standaard.
     * @param redenVerlies redenVerlies van Standaard.
     */
    public AbstractPersoonNationaliteitStandaardGroepModel(final RedenVerkrijgingNLNationaliteit redenVerkrijging,
            final RedenVerliesNLNationaliteit redenVerlies)
    {
        this.redenVerkrijging = redenVerkrijging;
        this.redenVerlies = redenVerlies;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonNationaliteitStandaardGroep te kopieren groep.
     */
    public AbstractPersoonNationaliteitStandaardGroepModel(
            final PersoonNationaliteitStandaardGroep persoonNationaliteitStandaardGroep)
    {
        this.redenVerkrijging = persoonNationaliteitStandaardGroep.getRedenVerkrijging();
        this.redenVerlies = persoonNationaliteitStandaardGroep.getRedenVerlies();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedenVerkrijgingNLNationaliteit getRedenVerkrijging() {
        return redenVerkrijging;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedenVerliesNLNationaliteit getRedenVerlies() {
        return redenVerlies;
    }

}

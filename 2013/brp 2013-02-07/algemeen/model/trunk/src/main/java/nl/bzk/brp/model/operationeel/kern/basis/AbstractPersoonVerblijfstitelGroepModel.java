/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Verblijfstitel;
import nl.bzk.brp.model.logisch.kern.PersoonVerblijfstitelGroep;
import nl.bzk.brp.model.logisch.kern.basis.PersoonVerblijfstitelGroepBasis;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 * 1. Vorm van historie: beiden (zowel materi�le als formele historie).
 * Het historiepatroon bij verblijfsrecht is bijzonder. De datum aanvang verblijfsrecht wordt aangeleverd door de IND,
 * en komt logischerwijs overeen met datum aanvang geldigheid.
 * De datum VOORZIEN einde kan in de toekomst liggen, en wijkt derhalve af van een 'normale' datum einde geldigheid, die
 * meestal in het verleden zal liggen.
 * Vanwege aanlevering vanuit migratie (met een andere granulariteit voor historie) kan datum aanvang geldigheid
 * afwijken van de datum aanvang verblijfsrecht.
 *
 *
 *
 */
@MappedSuperclass
public abstract class AbstractPersoonVerblijfstitelGroepModel implements PersoonVerblijfstitelGroepBasis {

    @ManyToOne
    @JoinColumn(name = "Verblijfstitel")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Verblijfstitel verblijfstitel;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatAanvVerblijfstitel"))
    @JsonProperty
    private Datum          datumAanvangVerblijfstitel;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatVoorzEindeVerblijfstitel"))
    @JsonProperty
    private Datum          datumVoorzienEindeVerblijfstitel;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractPersoonVerblijfstitelGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param verblijfstitel verblijfstitel van Verblijfstitel.
     * @param datumAanvangVerblijfstitel datumAanvangVerblijfstitel van Verblijfstitel.
     * @param datumVoorzienEindeVerblijfstitel datumVoorzienEindeVerblijfstitel van Verblijfstitel.
     */
    public AbstractPersoonVerblijfstitelGroepModel(final Verblijfstitel verblijfstitel,
            final Datum datumAanvangVerblijfstitel, final Datum datumVoorzienEindeVerblijfstitel)
    {
        this.verblijfstitel = verblijfstitel;
        this.datumAanvangVerblijfstitel = datumAanvangVerblijfstitel;
        this.datumVoorzienEindeVerblijfstitel = datumVoorzienEindeVerblijfstitel;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonVerblijfstitelGroep te kopieren groep.
     */
    public AbstractPersoonVerblijfstitelGroepModel(final PersoonVerblijfstitelGroep persoonVerblijfstitelGroep) {
        this.verblijfstitel = persoonVerblijfstitelGroep.getVerblijfstitel();
        this.datumAanvangVerblijfstitel = persoonVerblijfstitelGroep.getDatumAanvangVerblijfstitel();
        this.datumVoorzienEindeVerblijfstitel = persoonVerblijfstitelGroep.getDatumVoorzienEindeVerblijfstitel();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Verblijfstitel getVerblijfstitel() {
        return verblijfstitel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Datum getDatumAanvangVerblijfstitel() {
        return datumAanvangVerblijfstitel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Datum getDatumVoorzienEindeVerblijfstitel() {
        return datumVoorzienEindeVerblijfstitel;
    }

}

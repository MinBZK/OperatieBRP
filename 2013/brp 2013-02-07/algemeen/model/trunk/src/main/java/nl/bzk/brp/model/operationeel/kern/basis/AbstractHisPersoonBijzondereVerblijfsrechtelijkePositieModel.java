/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.BijzondereVerblijfsrechtelijkePositie;
import nl.bzk.brp.model.basis.AbstractFormeleHistorieEntiteit;
import nl.bzk.brp.model.logisch.kern.basis.PersoonBijzondereVerblijfsrechtelijkePositieGroepBasis;
import nl.bzk.brp.model.operationeel.kern.PersoonBijzondereVerblijfsrechtelijkePositieGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractHisPersoonBijzondereVerblijfsrechtelijkePositieModel extends
        AbstractFormeleHistorieEntiteit implements PersoonBijzondereVerblijfsrechtelijkePositieGroepBasis
{

    @Id
    @SequenceGenerator(name = "HIS_PERSOONBIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE", sequenceName = "Kern.seq_His_PersBVP")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PERSOONBIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE")
    @JsonProperty
    private Integer                               iD;

    @ManyToOne
    @JoinColumn(name = "Pers")
    private PersoonModel                          persoon;

    @ManyToOne
    @JoinColumn(name = "BVP")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private BijzondereVerblijfsrechtelijkePositie bijzondereVerblijfsrechtelijkePositie;

    /**
     * Default constructor t.b.v. JPA
     *
     */
    protected AbstractHisPersoonBijzondereVerblijfsrechtelijkePositieModel() {
    }

    /**
     * Copy Constructor die op basis van een A-laag klasse en groep een C/D laag klasse construeert.
     *
     * @param persoonModel instantie van A-laag klasse.
     * @param groep Groep uit de A Laag.
     */
    public AbstractHisPersoonBijzondereVerblijfsrechtelijkePositieModel(final PersoonModel persoonModel,
            final PersoonBijzondereVerblijfsrechtelijkePositieGroepModel groep)
    {
        this.persoon = persoonModel;
        this.bijzondereVerblijfsrechtelijkePositie = groep.getBijzondereVerblijfsrechtelijkePositie();

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie instantie van A-laag klasse.
     */
    public AbstractHisPersoonBijzondereVerblijfsrechtelijkePositieModel(
            final AbstractHisPersoonBijzondereVerblijfsrechtelijkePositieModel kopie)
    {
        super(kopie);
        persoon = kopie.getPersoon();
        bijzondereVerblijfsrechtelijkePositie = kopie.getBijzondereVerblijfsrechtelijkePositie();

    }

    /**
     * Retourneert ID van His Persoon Bijzondere verblijfsrechtelijke positie.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van His Persoon Bijzondere verblijfsrechtelijke positie.
     *
     * @return Persoon.
     */
    public PersoonModel getPersoon() {
        return persoon;
    }

    /**
     * Retourneert Bijzondere verblijfsrechtelijke positie van His Persoon Bijzondere verblijfsrechtelijke positie.
     *
     * @return Bijzondere verblijfsrechtelijke positie.
     */
    public BijzondereVerblijfsrechtelijkePositie getBijzondereVerblijfsrechtelijkePositie() {
        return bijzondereVerblijfsrechtelijkePositie;
    }

}

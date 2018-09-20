/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.basis.AbstractFormeleHistorieEntiteit;
import nl.bzk.brp.model.logisch.kern.basis.ErkenningOngeborenVruchtStandaardGroepBasis;
import nl.bzk.brp.model.operationeel.kern.ErkenningOngeborenVruchtModel;
import nl.bzk.brp.model.operationeel.kern.ErkenningOngeborenVruchtStandaardGroepModel;


/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractHisErkenningOngeborenVruchtModel extends AbstractFormeleHistorieEntiteit implements
        ErkenningOngeborenVruchtStandaardGroepBasis
{

    @Id
    @SequenceGenerator(name = "HIS_ERKENNINGONGEBORENVRUCHT", sequenceName = "Kern.seq_His_ErkenningOngeborenVrucht")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_ERKENNINGONGEBORENVRUCHT")
    @JsonProperty
    private Integer                       iD;

    @ManyToOne
    @JoinColumn(name = "Relatie")
    @JsonProperty
    private ErkenningOngeborenVruchtModel relatie;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatErkenningOngeborenVrucht"))
    @JsonProperty
    private Datum                         datumErkenningOngeborenVrucht;

    /**
     * Default constructor t.b.v. JPA
     *
     */
    protected AbstractHisErkenningOngeborenVruchtModel() {
    }

    /**
     * Copy Constructor die op basis van een A-laag klasse en groep een C/D laag klasse construeert.
     *
     * @param erkenningOngeborenVruchtModel instantie van A-laag klasse.
     * @param groep Groep uit de A Laag.
     */
    public AbstractHisErkenningOngeborenVruchtModel(final ErkenningOngeborenVruchtModel erkenningOngeborenVruchtModel,
            final ErkenningOngeborenVruchtStandaardGroepModel groep)
    {
        this.relatie = erkenningOngeborenVruchtModel;
        this.datumErkenningOngeborenVrucht = groep.getDatumErkenningOngeborenVrucht();

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie instantie van A-laag klasse.
     */
    public AbstractHisErkenningOngeborenVruchtModel(final AbstractHisErkenningOngeborenVruchtModel kopie) {
        super(kopie);
        relatie = kopie.getRelatie();
        datumErkenningOngeborenVrucht = kopie.getDatumErkenningOngeborenVrucht();

    }

    /**
     * Retourneert ID van His Erkenning ongeboren vrucht.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Relatie van His Erkenning ongeboren vrucht.
     *
     * @return Relatie.
     */
    public ErkenningOngeborenVruchtModel getRelatie() {
        return relatie;
    }

    /**
     * Retourneert Datum erkenning ongeboren vrucht van His Erkenning ongeboren vrucht.
     *
     * @return Datum erkenning ongeboren vrucht.
     */
    public Datum getDatumErkenningOngeborenVrucht() {
        return datumErkenningOngeborenVrucht;
    }

}

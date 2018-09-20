/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.basis.AbstractMaterieleHistorieEntiteit;
import nl.bzk.brp.model.logisch.kern.basis.PersoonIndicatieStandaardGroepBasis;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieModel;
import nl.bzk.brp.model.operationeel.kern.PersoonIndicatieModel;
import nl.bzk.brp.model.operationeel.kern.PersoonIndicatieStandaardGroepModel;
import org.hibernate.annotations.Type;


/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractHisPersoonIndicatieModel extends AbstractMaterieleHistorieEntiteit implements
        PersoonIndicatieStandaardGroepBasis
{

    @Id
    @SequenceGenerator(name = "HIS_PERSOONINDICATIE", sequenceName = "Kern.seq_His_PersIndicatie")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PERSOONINDICATIE")
    @JsonProperty
    private Integer               iD;

    @ManyToOne
    @JoinColumn(name = "PersIndicatie")
    private PersoonIndicatieModel persoonIndicatie;

    @Type(type = "Ja")
    @Column(name = "Waarde")
    @JsonProperty
    private Ja                    waarde;

    /**
     * Default constructor t.b.v. JPA
     *
     */
    protected AbstractHisPersoonIndicatieModel() {
    }

    /**
     * Copy Constructor die op basis van een A-laag klasse en groep een C/D laag klasse construeert.
     *
     * @param persoonIndicatieModel instantie van A-laag klasse.
     * @param groep Groep uit de A Laag.
     */
    public AbstractHisPersoonIndicatieModel(final PersoonIndicatieModel persoonIndicatieModel,
            final PersoonIndicatieStandaardGroepModel groep)
    {
        this.persoonIndicatie = persoonIndicatieModel;
        this.waarde = groep.getWaarde();

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie instantie van A-laag klasse.
     */
    public AbstractHisPersoonIndicatieModel(final AbstractHisPersoonIndicatieModel kopie) {
        super(kopie);
        persoonIndicatie = kopie.getPersoonIndicatie();
        waarde = kopie.getWaarde();

    }

    /**
     * Retourneert ID van His Persoon \ Indicatie.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon \ Indicatie van His Persoon \ Indicatie.
     *
     * @return Persoon \ Indicatie.
     */
    public PersoonIndicatieModel getPersoonIndicatie() {
        return persoonIndicatie;
    }

    /**
     * Retourneert Waarde van His Persoon \ Indicatie.
     *
     * @return Waarde.
     */
    public Ja getWaarde() {
        return waarde;
    }

    /**
     * Deze functie maakt een kopie van het object dmv het aanroepen van de copy constructor met zichzelf als argument.
     *
     * @return de kopie
     */
    @Override
    public HisPersoonIndicatieModel kopieer() {
        return new HisPersoonIndicatieModel(this);
    }
}

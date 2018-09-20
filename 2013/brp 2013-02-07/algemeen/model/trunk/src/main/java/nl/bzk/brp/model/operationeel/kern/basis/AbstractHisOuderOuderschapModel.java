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
import javax.persistence.Transient;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.basis.AbstractMaterieleHistorieEntiteit;
import nl.bzk.brp.model.logisch.kern.basis.OuderOuderschapGroepBasis;
import nl.bzk.brp.model.operationeel.kern.HisOuderOuderschapModel;
import nl.bzk.brp.model.operationeel.kern.OuderModel;
import nl.bzk.brp.model.operationeel.kern.OuderOuderschapGroepModel;
import org.hibernate.annotations.Type;


/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractHisOuderOuderschapModel extends AbstractMaterieleHistorieEntiteit implements
        OuderOuderschapGroepBasis
{

    @Id
    @SequenceGenerator(name = "HIS_OUDEROUDERSCHAP", sequenceName = "Kern.seq_His_OuderOuderschap")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_OUDEROUDERSCHAP")
    @JsonProperty
    private Integer    iD;

    @ManyToOne
    @JoinColumn(name = "Betr")
    @JsonProperty
    private OuderModel betrokkenheid;

    @Type(type = "Ja")
    @Column(name = "IndOuder")
    @JsonProperty
    private Ja         indicatieOuder;

    @Transient
    @JsonProperty
    private Ja indicatieOuderUitWieKindIsVoortgekomen;

    /**
     * Default constructor t.b.v. JPA
     *
     */
    protected AbstractHisOuderOuderschapModel() {
    }

    /**
     * Copy Constructor die op basis van een A-laag klasse en groep een C/D laag klasse construeert.
     *
     * @param ouderModel instantie van A-laag klasse.
     * @param groep Groep uit de A Laag.
     */
    public AbstractHisOuderOuderschapModel(final OuderModel ouderModel, final OuderOuderschapGroepModel groep) {
        this.betrokkenheid = ouderModel;
        this.indicatieOuder = groep.getIndicatieOuder();

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie instantie van A-laag klasse.
     */
    public AbstractHisOuderOuderschapModel(final AbstractHisOuderOuderschapModel kopie) {
        super(kopie);
        betrokkenheid = kopie.getBetrokkenheid();
        indicatieOuder = kopie.getIndicatieOuder();

    }

    /**
     * Retourneert ID van His Ouder Ouderschap.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Betrokkenheid van His Ouder Ouderschap.
     *
     * @return Betrokkenheid.
     */
    public OuderModel getBetrokkenheid() {
        return betrokkenheid;
    }

    /**
     * Retourneert Ouder? van His Ouder Ouderschap.
     *
     * @return Ouder?.
     */
    @Override
    public Ja getIndicatieOuder() {
        return indicatieOuder;
    }

    @Override
    public Ja getIndicatieOuderUitWieKindIsVoortgekomen() {
        return indicatieOuderUitWieKindIsVoortgekomen;
    }

    /**
     * Deze functie maakt een kopie van het object dmv het aanroepen van de copy constructor met zichzelf als argument.
     *
     * @return de kopie
     */
    @Override
    public HisOuderOuderschapModel kopieer() {
        return new HisOuderOuderschapModel(this);
    }
}

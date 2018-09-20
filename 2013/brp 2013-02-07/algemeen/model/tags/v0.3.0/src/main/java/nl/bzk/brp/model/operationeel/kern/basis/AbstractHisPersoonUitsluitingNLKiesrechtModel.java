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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.basis.AbstractFormeleHistorieEntiteit;
import nl.bzk.brp.model.logisch.kern.basis.PersoonUitsluitingNLKiesrechtGroepBasis;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.operationeel.kern.PersoonUitsluitingNLKiesrechtGroepModel;
import org.hibernate.annotations.Type;


/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractHisPersoonUitsluitingNLKiesrechtModel extends AbstractFormeleHistorieEntiteit implements
        PersoonUitsluitingNLKiesrechtGroepBasis
{

    @Id
    @SequenceGenerator(name = "HIS_PERSOONUITSLUITINGNLKIESRECHT", sequenceName = "Kern.seq_His_PersUitslNLKiesr")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PERSOONUITSLUITINGNLKIESRECHT")
    @JsonProperty
    private Integer      iD;

    @ManyToOne
    @JoinColumn(name = "Pers")
    private PersoonModel persoon;

    @Type(type = "Ja")
    @Column(name = "IndUitslNLKiesr")
    @JsonProperty
    private Ja           indicatieUitsluitingNLKiesrecht;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatEindeUitslNLKiesr"))
    @JsonProperty
    private Datum        datumEindeUitsluitingNLKiesrecht;

    /**
     * Default constructor t.b.v. JPA
     *
     */
    protected AbstractHisPersoonUitsluitingNLKiesrechtModel() {
    }

    /**
     * Copy Constructor die op basis van een A-laag klasse en groep een C/D laag klasse construeert.
     *
     * @param persoonModel instantie van A-laag klasse.
     * @param groep Groep uit de A Laag.
     */
    public AbstractHisPersoonUitsluitingNLKiesrechtModel(final PersoonModel persoonModel,
            final PersoonUitsluitingNLKiesrechtGroepModel groep)
    {
        this.persoon = persoonModel;
        this.indicatieUitsluitingNLKiesrecht = groep.getIndicatieUitsluitingNLKiesrecht();
        this.datumEindeUitsluitingNLKiesrecht = groep.getDatumEindeUitsluitingNLKiesrecht();

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie instantie van A-laag klasse.
     */
    public AbstractHisPersoonUitsluitingNLKiesrechtModel(final AbstractHisPersoonUitsluitingNLKiesrechtModel kopie) {
        super(kopie);
        persoon = kopie.getPersoon();
        indicatieUitsluitingNLKiesrecht = kopie.getIndicatieUitsluitingNLKiesrecht();
        datumEindeUitsluitingNLKiesrecht = kopie.getDatumEindeUitsluitingNLKiesrecht();

    }

    /**
     * Retourneert ID van His Persoon Uitsluiting NL kiesrecht.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van His Persoon Uitsluiting NL kiesrecht.
     *
     * @return Persoon.
     */
    public PersoonModel getPersoon() {
        return persoon;
    }

    /**
     * Retourneert Uitsluiting NL kiesrecht? van His Persoon Uitsluiting NL kiesrecht.
     *
     * @return Uitsluiting NL kiesrecht?.
     */
    public Ja getIndicatieUitsluitingNLKiesrecht() {
        return indicatieUitsluitingNLKiesrecht;
    }

    /**
     * Retourneert Datum einde uitsluiting NL kiesrecht van His Persoon Uitsluiting NL kiesrecht.
     *
     * @return Datum einde uitsluiting NL kiesrecht.
     */
    public Datum getDatumEindeUitsluitingNLKiesrecht() {
        return datumEindeUitsluitingNLKiesrecht;
    }

}

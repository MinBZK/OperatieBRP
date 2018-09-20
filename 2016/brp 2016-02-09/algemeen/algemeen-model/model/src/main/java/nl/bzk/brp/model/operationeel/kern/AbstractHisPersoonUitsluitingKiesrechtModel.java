/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.annotaties.AttribuutAccessor;
import nl.bzk.brp.model.annotaties.GroepAccessor;
import nl.bzk.brp.model.basis.AbstractFormeelHistorischMetActieVerantwoording;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.kern.PersoonUitsluitingKiesrechtGroep;
import nl.bzk.brp.model.logisch.kern.PersoonUitsluitingKiesrechtGroepBasis;

/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@GroepAccessor(dbObjectId = 3519)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractHisPersoonUitsluitingKiesrechtModel extends AbstractFormeelHistorischMetActieVerantwoording implements
        PersoonUitsluitingKiesrechtGroepBasis, ModelIdentificeerbaar<Integer>, ElementIdentificeerbaar
{

    @Id
    @SequenceGenerator(name = "HIS_PERSOONUITSLUITINGKIESRECHT", sequenceName = "Kern.seq_His_PersUitslKiesr")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PERSOONUITSLUITINGKIESRECHT")
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = PersoonHisVolledigImpl.class)
    @JoinColumn(name = "Pers")
    @JsonBackReference
    private PersoonHisVolledig persoon;

    @Embedded
    @AttributeOverride(name = JaAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndUitslKiesr"))
    @JsonProperty
    private JaAttribuut indicatieUitsluitingKiesrecht;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatVoorzEindeUitslKiesr"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datumVoorzienEindeUitsluitingKiesrecht;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractHisPersoonUitsluitingKiesrechtModel() {
    }

    /**
     * Constructor voor het initialiseren van alle attributen.
     *
     * @param persoon persoon van HisPersoonUitsluitingKiesrechtModel
     * @param indicatieUitsluitingKiesrecht indicatieUitsluitingKiesrecht van HisPersoonUitsluitingKiesrechtModel
     * @param datumVoorzienEindeUitsluitingKiesrecht datumVoorzienEindeUitsluitingKiesrecht van
     *            HisPersoonUitsluitingKiesrechtModel
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     */
    public AbstractHisPersoonUitsluitingKiesrechtModel(
        final PersoonHisVolledig persoon,
        final JaAttribuut indicatieUitsluitingKiesrecht,
        final DatumEvtDeelsOnbekendAttribuut datumVoorzienEindeUitsluitingKiesrecht,
        final ActieModel actieInhoud)
    {
        this.persoon = persoon;
        this.indicatieUitsluitingKiesrecht = indicatieUitsluitingKiesrecht;
        this.datumVoorzienEindeUitsluitingKiesrecht = datumVoorzienEindeUitsluitingKiesrecht;
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public AbstractHisPersoonUitsluitingKiesrechtModel(final AbstractHisPersoonUitsluitingKiesrechtModel kopie) {
        super(kopie);
        persoon = kopie.getPersoon();
        indicatieUitsluitingKiesrecht = kopie.getIndicatieUitsluitingKiesrecht();
        datumVoorzienEindeUitsluitingKiesrecht = kopie.getDatumVoorzienEindeUitsluitingKiesrecht();

    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param persoonHisVolledig instantie van A-laag klasse.
     * @param groep groep
     * @param actieInhoud Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public AbstractHisPersoonUitsluitingKiesrechtModel(
        final PersoonHisVolledig persoonHisVolledig,
        final PersoonUitsluitingKiesrechtGroep groep,
        final ActieModel actieInhoud)
    {
        this.persoon = persoonHisVolledig;
        this.indicatieUitsluitingKiesrecht = groep.getIndicatieUitsluitingKiesrecht();
        this.datumVoorzienEindeUitsluitingKiesrecht = groep.getDatumVoorzienEindeUitsluitingKiesrecht();
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Retourneert ID van His Persoon Uitsluiting kiesrecht.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van His Persoon Uitsluiting kiesrecht.
     *
     * @return Persoon.
     */
    public PersoonHisVolledig getPersoon() {
        return persoon;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9764)
    public JaAttribuut getIndicatieUitsluitingKiesrecht() {
        return indicatieUitsluitingKiesrecht;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9765)
    public DatumEvtDeelsOnbekendAttribuut getDatumVoorzienEindeUitsluitingKiesrecht() {
        return datumVoorzienEindeUitsluitingKiesrecht;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (indicatieUitsluitingKiesrecht != null) {
            attributen.add(indicatieUitsluitingKiesrecht);
        }
        if (datumVoorzienEindeUitsluitingKiesrecht != null) {
            attributen.add(datumVoorzienEindeUitsluitingKiesrecht);
        }
        return attributen;
    }

    /**
     * Retourneert het Element behorende bij deze groep.
     *
     * @return Element enum instantie behorende bij deze groep.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PERSOON_UITSLUITINGKIESRECHT;
    }

}

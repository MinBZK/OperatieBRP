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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.annotaties.AttribuutAccessor;
import nl.bzk.brp.model.annotaties.GroepAccessor;
import nl.bzk.brp.model.basis.AbstractFormeelHistorischMetActieVerantwoording;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonVerificatieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVerificatieHisVolledig;
import nl.bzk.brp.model.logisch.kern.PersoonVerificatieStandaardGroep;
import nl.bzk.brp.model.logisch.kern.PersoonVerificatieStandaardGroepBasis;

/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@GroepAccessor(dbObjectId = 3783)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractHisPersoonVerificatieModel extends AbstractFormeelHistorischMetActieVerantwoording implements
        PersoonVerificatieStandaardGroepBasis, ModelIdentificeerbaar<Long>, ElementIdentificeerbaar
{

    @Id
    @SequenceGenerator(name = "HIS_PERSOONVERIFICATIE", sequenceName = "Kern.seq_His_PersVerificatie")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PERSOONVERIFICATIE")
    @JsonProperty
    private Long iD;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = PersoonVerificatieHisVolledigImpl.class)
    @JoinColumn(name = "PersVerificatie")
    @JsonBackReference
    private PersoonVerificatieHisVolledig persoonVerificatie;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Dat"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datum;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractHisPersoonVerificatieModel() {
    }

    /**
     * Constructor voor het initialiseren van alle attributen.
     *
     * @param persoonVerificatie persoonVerificatie van HisPersoonVerificatieModel
     * @param datum datum van HisPersoonVerificatieModel
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     */
    public AbstractHisPersoonVerificatieModel(
        final PersoonVerificatieHisVolledig persoonVerificatie,
        final DatumEvtDeelsOnbekendAttribuut datum,
        final ActieModel actieInhoud)
    {
        this.persoonVerificatie = persoonVerificatie;
        this.datum = datum;
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public AbstractHisPersoonVerificatieModel(final AbstractHisPersoonVerificatieModel kopie) {
        super(kopie);
        persoonVerificatie = kopie.getPersoonVerificatie();
        datum = kopie.getDatum();

    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param persoonVerificatieHisVolledig instantie van A-laag klasse.
     * @param groep groep
     * @param actieInhoud Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public AbstractHisPersoonVerificatieModel(
        final PersoonVerificatieHisVolledig persoonVerificatieHisVolledig,
        final PersoonVerificatieStandaardGroep groep,
        final ActieModel actieInhoud)
    {
        this.persoonVerificatie = persoonVerificatieHisVolledig;
        this.datum = groep.getDatum();
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Retourneert ID van His Persoon \ Verificatie.
     *
     * @return ID.
     */
    public Long getID() {
        return iD;
    }

    /**
     * Retourneert Persoon \ Verificatie van His Persoon \ Verificatie.
     *
     * @return Persoon \ Verificatie.
     */
    public PersoonVerificatieHisVolledig getPersoonVerificatie() {
        return persoonVerificatie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9813)
    public DatumEvtDeelsOnbekendAttribuut getDatum() {
        return datum;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (datum != null) {
            attributen.add(datum);
        }
        return attributen;
    }

    /**
     * Retourneert het Element behorende bij deze groep.
     *
     * @return Element enum instantie behorende bij deze groep.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PERSOON_VERIFICATIE_STANDAARD;
    }

}

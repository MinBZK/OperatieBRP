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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VersienummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.annotaties.AttribuutAccessor;
import nl.bzk.brp.model.annotaties.GroepAccessor;
import nl.bzk.brp.model.basis.AbstractFormeelHistorischMetActieVerantwoording;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.kern.PersoonInschrijvingGroep;
import nl.bzk.brp.model.logisch.kern.PersoonInschrijvingGroepBasis;

/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@GroepAccessor(dbObjectId = 3521)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractHisPersoonInschrijvingModel extends AbstractFormeelHistorischMetActieVerantwoording implements
        PersoonInschrijvingGroepBasis, ModelIdentificeerbaar<Integer>, ElementIdentificeerbaar
{

    @Id
    @SequenceGenerator(name = "HIS_PERSOONINSCHRIJVING", sequenceName = "Kern.seq_His_PersInschr")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PERSOONINSCHRIJVING")
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = PersoonHisVolledigImpl.class)
    @JoinColumn(name = "Pers")
    @JsonBackReference
    private PersoonHisVolledig persoon;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatInschr"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datumInschrijving;

    @Embedded
    @AttributeOverride(name = VersienummerAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Versienr"))
    @JsonProperty
    private VersienummerAttribuut versienummer;

    @Embedded
    @AttributeOverride(name = DatumTijdAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Dattijdstempel"))
    @JsonProperty
    private DatumTijdAttribuut datumtijdstempel;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractHisPersoonInschrijvingModel() {
    }

    /**
     * Constructor voor het initialiseren van alle attributen.
     *
     * @param persoon persoon van HisPersoonInschrijvingModel
     * @param datumInschrijving datumInschrijving van HisPersoonInschrijvingModel
     * @param versienummer versienummer van HisPersoonInschrijvingModel
     * @param datumtijdstempel datumtijdstempel van HisPersoonInschrijvingModel
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     */
    public AbstractHisPersoonInschrijvingModel(
        final PersoonHisVolledig persoon,
        final DatumEvtDeelsOnbekendAttribuut datumInschrijving,
        final VersienummerAttribuut versienummer,
        final DatumTijdAttribuut datumtijdstempel,
        final ActieModel actieInhoud)
    {
        this.persoon = persoon;
        this.datumInschrijving = datumInschrijving;
        this.versienummer = versienummer;
        this.datumtijdstempel = datumtijdstempel;
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public AbstractHisPersoonInschrijvingModel(final AbstractHisPersoonInschrijvingModel kopie) {
        super(kopie);
        persoon = kopie.getPersoon();
        datumInschrijving = kopie.getDatumInschrijving();
        versienummer = kopie.getVersienummer();
        datumtijdstempel = kopie.getDatumtijdstempel();

    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param persoonHisVolledig instantie van A-laag klasse.
     * @param groep groep
     * @param actieInhoud Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public AbstractHisPersoonInschrijvingModel(
        final PersoonHisVolledig persoonHisVolledig,
        final PersoonInschrijvingGroep groep,
        final ActieModel actieInhoud)
    {
        this.persoon = persoonHisVolledig;
        this.datumInschrijving = groep.getDatumInschrijving();
        this.versienummer = groep.getVersienummer();
        this.datumtijdstempel = groep.getDatumtijdstempel();
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Retourneert ID van His Persoon Inschrijving.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van His Persoon Inschrijving.
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
    @AttribuutAccessor(dbObjectId = 9733)
    public DatumEvtDeelsOnbekendAttribuut getDatumInschrijving() {
        return datumInschrijving;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9734)
    public VersienummerAttribuut getVersienummer() {
        return versienummer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 11238)
    public DatumTijdAttribuut getDatumtijdstempel() {
        return datumtijdstempel;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (datumInschrijving != null) {
            attributen.add(datumInschrijving);
        }
        if (versienummer != null) {
            attributen.add(versienummer);
        }
        if (datumtijdstempel != null) {
            attributen.add(datumtijdstempel);
        }
        return attributen;
    }

    /**
     * Retourneert het Element behorende bij deze groep.
     *
     * @return Element enum instantie behorende bij deze groep.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PERSOON_INSCHRIJVING;
    }

}

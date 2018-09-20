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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OnderzoekOmschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.StatusOnderzoekAttribuut;
import nl.bzk.brp.model.annotaties.AttribuutAccessor;
import nl.bzk.brp.model.annotaties.GroepAccessor;
import nl.bzk.brp.model.basis.AbstractFormeelHistorischMetActieVerantwoording;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.impl.kern.OnderzoekHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.OnderzoekHisVolledig;
import nl.bzk.brp.model.logisch.kern.OnderzoekStandaardGroep;
import nl.bzk.brp.model.logisch.kern.OnderzoekStandaardGroepBasis;

/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@GroepAccessor(dbObjectId = 3774)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractHisOnderzoekModel extends AbstractFormeelHistorischMetActieVerantwoording implements OnderzoekStandaardGroepBasis,
        ModelIdentificeerbaar<Integer>, ElementIdentificeerbaar
{

    @Id
    @SequenceGenerator(name = "HIS_ONDERZOEK", sequenceName = "Kern.seq_His_Onderzoek")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_ONDERZOEK")
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = OnderzoekHisVolledigImpl.class)
    @JoinColumn(name = "Onderzoek")
    @JsonBackReference
    private OnderzoekHisVolledig onderzoek;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatAanv"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datumAanvang;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "VerwachteAfhandeldat"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut verwachteAfhandeldatum;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatEinde"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datumEinde;

    @Embedded
    @AttributeOverride(name = OnderzoekOmschrijvingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Oms"))
    @JsonProperty
    private OnderzoekOmschrijvingAttribuut omschrijving;

    @Embedded
    @AttributeOverride(name = StatusOnderzoekAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Status"))
    @JsonProperty
    private StatusOnderzoekAttribuut status;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractHisOnderzoekModel() {
    }

    /**
     * Constructor voor het initialiseren van alle attributen.
     *
     * @param onderzoek onderzoek van HisOnderzoekModel
     * @param datumAanvang datumAanvang van HisOnderzoekModel
     * @param verwachteAfhandeldatum verwachteAfhandeldatum van HisOnderzoekModel
     * @param datumEinde datumEinde van HisOnderzoekModel
     * @param omschrijving omschrijving van HisOnderzoekModel
     * @param status status van HisOnderzoekModel
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     */
    public AbstractHisOnderzoekModel(
        final OnderzoekHisVolledig onderzoek,
        final DatumEvtDeelsOnbekendAttribuut datumAanvang,
        final DatumEvtDeelsOnbekendAttribuut verwachteAfhandeldatum,
        final DatumEvtDeelsOnbekendAttribuut datumEinde,
        final OnderzoekOmschrijvingAttribuut omschrijving,
        final StatusOnderzoekAttribuut status,
        final ActieModel actieInhoud)
    {
        this.onderzoek = onderzoek;
        this.datumAanvang = datumAanvang;
        this.verwachteAfhandeldatum = verwachteAfhandeldatum;
        this.datumEinde = datumEinde;
        this.omschrijving = omschrijving;
        this.status = status;
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public AbstractHisOnderzoekModel(final AbstractHisOnderzoekModel kopie) {
        super(kopie);
        onderzoek = kopie.getOnderzoek();
        datumAanvang = kopie.getDatumAanvang();
        verwachteAfhandeldatum = kopie.getVerwachteAfhandeldatum();
        datumEinde = kopie.getDatumEinde();
        omschrijving = kopie.getOmschrijving();
        status = kopie.getStatus();

    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param onderzoekHisVolledig instantie van A-laag klasse.
     * @param groep groep
     * @param actieInhoud Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public AbstractHisOnderzoekModel(final OnderzoekHisVolledig onderzoekHisVolledig, final OnderzoekStandaardGroep groep, final ActieModel actieInhoud) {
        this.onderzoek = onderzoekHisVolledig;
        this.datumAanvang = groep.getDatumAanvang();
        this.verwachteAfhandeldatum = groep.getVerwachteAfhandeldatum();
        this.datumEinde = groep.getDatumEinde();
        this.omschrijving = groep.getOmschrijving();
        this.status = groep.getStatus();
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Retourneert ID van His Onderzoek.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Onderzoek van His Onderzoek.
     *
     * @return Onderzoek.
     */
    public OnderzoekHisVolledig getOnderzoek() {
        return onderzoek;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9709)
    public DatumEvtDeelsOnbekendAttribuut getDatumAanvang() {
        return datumAanvang;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 10921)
    public DatumEvtDeelsOnbekendAttribuut getVerwachteAfhandeldatum() {
        return verwachteAfhandeldatum;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9710)
    public DatumEvtDeelsOnbekendAttribuut getDatumEinde() {
        return datumEinde;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9711)
    public OnderzoekOmschrijvingAttribuut getOmschrijving() {
        return omschrijving;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 10922)
    public StatusOnderzoekAttribuut getStatus() {
        return status;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (datumAanvang != null) {
            attributen.add(datumAanvang);
        }
        if (verwachteAfhandeldatum != null) {
            attributen.add(verwachteAfhandeldatum);
        }
        if (datumEinde != null) {
            attributen.add(datumEinde);
        }
        if (omschrijving != null) {
            attributen.add(omschrijving);
        }
        if (status != null) {
            attributen.add(status);
        }
        return attributen;
    }

    /**
     * Retourneert het Element behorende bij deze groep.
     *
     * @return Element enum instantie behorende bij deze groep.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.ONDERZOEK_STANDAARD;
    }

}

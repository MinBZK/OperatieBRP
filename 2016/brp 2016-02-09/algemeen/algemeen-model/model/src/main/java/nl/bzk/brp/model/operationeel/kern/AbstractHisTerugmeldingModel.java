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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.KenmerkTerugmeldingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.TekstTerugmeldingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.StatusTerugmeldingAttribuut;
import nl.bzk.brp.model.annotaties.AttribuutAccessor;
import nl.bzk.brp.model.annotaties.GroepAccessor;
import nl.bzk.brp.model.basis.AbstractFormeelHistorischMetActieVerantwoording;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.impl.kern.TerugmeldingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.TerugmeldingHisVolledig;
import nl.bzk.brp.model.logisch.kern.TerugmeldingStandaardGroep;
import nl.bzk.brp.model.logisch.kern.TerugmeldingStandaardGroepBasis;

/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@GroepAccessor(dbObjectId = 10739)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractHisTerugmeldingModel extends AbstractFormeelHistorischMetActieVerantwoording implements TerugmeldingStandaardGroepBasis,
        ModelIdentificeerbaar<Integer>, ElementIdentificeerbaar
{

    @Id
    @SequenceGenerator(name = "HIS_TERUGMELDING", sequenceName = "Kern.seq_His_Terugmelding")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_TERUGMELDING")
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = TerugmeldingHisVolledigImpl.class)
    @JoinColumn(name = "Terugmelding")
    @JsonBackReference
    private TerugmeldingHisVolledig terugmelding;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Onderzoek")
    @JsonProperty
    private OnderzoekModel onderzoek;

    @Embedded
    @AttributeOverride(name = StatusTerugmeldingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Status"))
    @JsonProperty
    private StatusTerugmeldingAttribuut status;

    @Embedded
    @AttributeOverride(name = TekstTerugmeldingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Toelichting"))
    @JsonProperty
    private TekstTerugmeldingAttribuut toelichting;

    @Embedded
    @AttributeOverride(name = KenmerkTerugmeldingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "KenmerkMeldendePartij"))
    @JsonProperty
    private KenmerkTerugmeldingAttribuut kenmerkMeldendePartij;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractHisTerugmeldingModel() {
    }

    /**
     * Constructor voor het initialiseren van alle attributen.
     *
     * @param terugmelding terugmelding van HisTerugmeldingModel
     * @param onderzoek onderzoek van HisTerugmeldingModel
     * @param status status van HisTerugmeldingModel
     * @param toelichting toelichting van HisTerugmeldingModel
     * @param kenmerkMeldendePartij kenmerkMeldendePartij van HisTerugmeldingModel
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     */
    public AbstractHisTerugmeldingModel(
        final TerugmeldingHisVolledig terugmelding,
        final OnderzoekModel onderzoek,
        final StatusTerugmeldingAttribuut status,
        final TekstTerugmeldingAttribuut toelichting,
        final KenmerkTerugmeldingAttribuut kenmerkMeldendePartij,
        final ActieModel actieInhoud)
    {
        this.terugmelding = terugmelding;
        this.onderzoek = onderzoek;
        this.status = status;
        this.toelichting = toelichting;
        this.kenmerkMeldendePartij = kenmerkMeldendePartij;
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public AbstractHisTerugmeldingModel(final AbstractHisTerugmeldingModel kopie) {
        super(kopie);
        terugmelding = kopie.getTerugmelding();
        onderzoek = kopie.getOnderzoek();
        status = kopie.getStatus();
        toelichting = kopie.getToelichting();
        kenmerkMeldendePartij = kopie.getKenmerkMeldendePartij();

    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param terugmeldingHisVolledig instantie van A-laag klasse.
     * @param groep groep
     * @param actieInhoud Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public AbstractHisTerugmeldingModel(
        final TerugmeldingHisVolledig terugmeldingHisVolledig,
        final TerugmeldingStandaardGroep groep,
        final ActieModel actieInhoud)
    {
        this.terugmelding = terugmeldingHisVolledig;
        if (groep.getOnderzoek() != null) {
            this.onderzoek = new OnderzoekModel(groep.getOnderzoek());
        }
        this.status = groep.getStatus();
        this.toelichting = groep.getToelichting();
        this.kenmerkMeldendePartij = groep.getKenmerkMeldendePartij();
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Retourneert ID van His Terugmelding.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Terugmelding van His Terugmelding.
     *
     * @return Terugmelding.
     */
    public TerugmeldingHisVolledig getTerugmelding() {
        return terugmelding;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 10816)
    public OnderzoekModel getOnderzoek() {
        return onderzoek;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 10818)
    public StatusTerugmeldingAttribuut getStatus() {
        return status;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 11157)
    public TekstTerugmeldingAttribuut getToelichting() {
        return toelichting;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 10817)
    public KenmerkTerugmeldingAttribuut getKenmerkMeldendePartij() {
        return kenmerkMeldendePartij;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (status != null) {
            attributen.add(status);
        }
        if (toelichting != null) {
            attributen.add(toelichting);
        }
        if (kenmerkMeldendePartij != null) {
            attributen.add(kenmerkMeldendePartij);
        }
        return attributen;
    }

    /**
     * Retourneert het Element behorende bij deze groep.
     *
     * @return Element enum instantie behorende bij deze groep.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.TERUGMELDING_STANDAARD;
    }

}

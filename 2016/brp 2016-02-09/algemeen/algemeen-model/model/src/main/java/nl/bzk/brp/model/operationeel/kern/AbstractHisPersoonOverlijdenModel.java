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
import javax.persistence.AssociationOverride;
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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandsePlaatsAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandseRegioAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieomschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.annotaties.AttribuutAccessor;
import nl.bzk.brp.model.annotaties.GroepAccessor;
import nl.bzk.brp.model.basis.AbstractFormeelHistorischMetActieVerantwoording;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.kern.PersoonOverlijdenGroep;
import nl.bzk.brp.model.logisch.kern.PersoonOverlijdenGroepBasis;

/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@GroepAccessor(dbObjectId = 3515)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractHisPersoonOverlijdenModel extends AbstractFormeelHistorischMetActieVerantwoording implements PersoonOverlijdenGroepBasis,
        ModelIdentificeerbaar<Integer>, ElementIdentificeerbaar
{

    @Id
    @SequenceGenerator(name = "HIS_PERSOONOVERLIJDEN", sequenceName = "Kern.seq_His_PersOverlijden")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PERSOONOVERLIJDEN")
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = PersoonHisVolledigImpl.class)
    @JoinColumn(name = "Pers")
    @JsonBackReference
    private PersoonHisVolledig persoon;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatOverlijden"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datumOverlijden;

    @Embedded
    @AssociationOverride(name = GemeenteAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "GemOverlijden"))
    @JsonProperty
    private GemeenteAttribuut gemeenteOverlijden;

    @Embedded
    @AttributeOverride(name = NaamEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "WplnaamOverlijden"))
    @JsonProperty
    private NaamEnumeratiewaardeAttribuut woonplaatsnaamOverlijden;

    @Embedded
    @AttributeOverride(name = BuitenlandsePlaatsAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "BLPlaatsOverlijden"))
    @JsonProperty
    private BuitenlandsePlaatsAttribuut buitenlandsePlaatsOverlijden;

    @Embedded
    @AttributeOverride(name = BuitenlandseRegioAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "BLRegioOverlijden"))
    @JsonProperty
    private BuitenlandseRegioAttribuut buitenlandseRegioOverlijden;

    @Embedded
    @AttributeOverride(name = LocatieomschrijvingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "OmsLocOverlijden"))
    @JsonProperty
    private LocatieomschrijvingAttribuut omschrijvingLocatieOverlijden;

    @Embedded
    @AssociationOverride(name = LandGebiedAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "LandGebiedOverlijden"))
    @JsonProperty
    private LandGebiedAttribuut landGebiedOverlijden;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractHisPersoonOverlijdenModel() {
    }

    /**
     * Constructor voor het initialiseren van alle attributen. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param persoon persoon van HisPersoonOverlijdenModel
     * @param datumOverlijden datumOverlijden van HisPersoonOverlijdenModel
     * @param gemeenteOverlijden gemeenteOverlijden van HisPersoonOverlijdenModel
     * @param woonplaatsnaamOverlijden woonplaatsnaamOverlijden van HisPersoonOverlijdenModel
     * @param buitenlandsePlaatsOverlijden buitenlandsePlaatsOverlijden van HisPersoonOverlijdenModel
     * @param buitenlandseRegioOverlijden buitenlandseRegioOverlijden van HisPersoonOverlijdenModel
     * @param omschrijvingLocatieOverlijden omschrijvingLocatieOverlijden van HisPersoonOverlijdenModel
     * @param landGebiedOverlijden landGebiedOverlijden van HisPersoonOverlijdenModel
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     */
    public AbstractHisPersoonOverlijdenModel(
        final PersoonHisVolledig persoon,
        final DatumEvtDeelsOnbekendAttribuut datumOverlijden,
        final GemeenteAttribuut gemeenteOverlijden,
        final NaamEnumeratiewaardeAttribuut woonplaatsnaamOverlijden,
        final BuitenlandsePlaatsAttribuut buitenlandsePlaatsOverlijden,
        final BuitenlandseRegioAttribuut buitenlandseRegioOverlijden,
        final LocatieomschrijvingAttribuut omschrijvingLocatieOverlijden,
        final LandGebiedAttribuut landGebiedOverlijden,
        final ActieModel actieInhoud)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        this.persoon = persoon;
        this.datumOverlijden = datumOverlijden;
        this.gemeenteOverlijden = gemeenteOverlijden;
        this.woonplaatsnaamOverlijden = woonplaatsnaamOverlijden;
        this.buitenlandsePlaatsOverlijden = buitenlandsePlaatsOverlijden;
        this.buitenlandseRegioOverlijden = buitenlandseRegioOverlijden;
        this.omschrijvingLocatieOverlijden = omschrijvingLocatieOverlijden;
        this.landGebiedOverlijden = landGebiedOverlijden;
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public AbstractHisPersoonOverlijdenModel(final AbstractHisPersoonOverlijdenModel kopie) {
        super(kopie);
        persoon = kopie.getPersoon();
        datumOverlijden = kopie.getDatumOverlijden();
        gemeenteOverlijden = kopie.getGemeenteOverlijden();
        woonplaatsnaamOverlijden = kopie.getWoonplaatsnaamOverlijden();
        buitenlandsePlaatsOverlijden = kopie.getBuitenlandsePlaatsOverlijden();
        buitenlandseRegioOverlijden = kopie.getBuitenlandseRegioOverlijden();
        omschrijvingLocatieOverlijden = kopie.getOmschrijvingLocatieOverlijden();
        landGebiedOverlijden = kopie.getLandGebiedOverlijden();

    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param persoonHisVolledig instantie van A-laag klasse.
     * @param groep groep
     * @param actieInhoud Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public AbstractHisPersoonOverlijdenModel(final PersoonHisVolledig persoonHisVolledig, final PersoonOverlijdenGroep groep, final ActieModel actieInhoud)
    {
        this.persoon = persoonHisVolledig;
        this.datumOverlijden = groep.getDatumOverlijden();
        this.gemeenteOverlijden = groep.getGemeenteOverlijden();
        this.woonplaatsnaamOverlijden = groep.getWoonplaatsnaamOverlijden();
        this.buitenlandsePlaatsOverlijden = groep.getBuitenlandsePlaatsOverlijden();
        this.buitenlandseRegioOverlijden = groep.getBuitenlandseRegioOverlijden();
        this.omschrijvingLocatieOverlijden = groep.getOmschrijvingLocatieOverlijden();
        this.landGebiedOverlijden = groep.getLandGebiedOverlijden();
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Retourneert ID van His Persoon Overlijden.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van His Persoon Overlijden.
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
    @AttribuutAccessor(dbObjectId = 9742)
    public DatumEvtDeelsOnbekendAttribuut getDatumOverlijden() {
        return datumOverlijden;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9743)
    public GemeenteAttribuut getGemeenteOverlijden() {
        return gemeenteOverlijden;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9744)
    public NaamEnumeratiewaardeAttribuut getWoonplaatsnaamOverlijden() {
        return woonplaatsnaamOverlijden;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9745)
    public BuitenlandsePlaatsAttribuut getBuitenlandsePlaatsOverlijden() {
        return buitenlandsePlaatsOverlijden;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9746)
    public BuitenlandseRegioAttribuut getBuitenlandseRegioOverlijden() {
        return buitenlandseRegioOverlijden;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9747)
    public LocatieomschrijvingAttribuut getOmschrijvingLocatieOverlijden() {
        return omschrijvingLocatieOverlijden;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9748)
    public LandGebiedAttribuut getLandGebiedOverlijden() {
        return landGebiedOverlijden;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (datumOverlijden != null) {
            attributen.add(datumOverlijden);
        }
        if (gemeenteOverlijden != null) {
            attributen.add(gemeenteOverlijden);
        }
        if (woonplaatsnaamOverlijden != null) {
            attributen.add(woonplaatsnaamOverlijden);
        }
        if (buitenlandsePlaatsOverlijden != null) {
            attributen.add(buitenlandsePlaatsOverlijden);
        }
        if (buitenlandseRegioOverlijden != null) {
            attributen.add(buitenlandseRegioOverlijden);
        }
        if (omschrijvingLocatieOverlijden != null) {
            attributen.add(omschrijvingLocatieOverlijden);
        }
        if (landGebiedOverlijden != null) {
            attributen.add(landGebiedOverlijden);
        }
        return attributen;
    }

    /**
     * Retourneert het Element behorende bij deze groep.
     *
     * @return Element enum instantie behorende bij deze groep.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PERSOON_OVERLIJDEN;
    }

}

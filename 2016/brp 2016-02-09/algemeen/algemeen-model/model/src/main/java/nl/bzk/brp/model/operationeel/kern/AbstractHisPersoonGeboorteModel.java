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
import nl.bzk.brp.model.logisch.kern.PersoonGeboorteGroep;
import nl.bzk.brp.model.logisch.kern.PersoonGeboorteGroepBasis;

/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@GroepAccessor(dbObjectId = 3514)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractHisPersoonGeboorteModel extends AbstractFormeelHistorischMetActieVerantwoording implements PersoonGeboorteGroepBasis,
        ModelIdentificeerbaar<Integer>, ElementIdentificeerbaar
{

    @Id
    @SequenceGenerator(name = "HIS_PERSOONGEBOORTE", sequenceName = "Kern.seq_His_PersGeboorte")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PERSOONGEBOORTE")
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = PersoonHisVolledigImpl.class)
    @JoinColumn(name = "Pers")
    @JsonBackReference
    private PersoonHisVolledig persoon;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatGeboorte"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datumGeboorte;

    @Embedded
    @AssociationOverride(name = GemeenteAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "GemGeboorte"))
    @JsonProperty
    private GemeenteAttribuut gemeenteGeboorte;

    @Embedded
    @AttributeOverride(name = NaamEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "WplnaamGeboorte"))
    @JsonProperty
    private NaamEnumeratiewaardeAttribuut woonplaatsnaamGeboorte;

    @Embedded
    @AttributeOverride(name = BuitenlandsePlaatsAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "BLPlaatsGeboorte"))
    @JsonProperty
    private BuitenlandsePlaatsAttribuut buitenlandsePlaatsGeboorte;

    @Embedded
    @AttributeOverride(name = BuitenlandseRegioAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "BLRegioGeboorte"))
    @JsonProperty
    private BuitenlandseRegioAttribuut buitenlandseRegioGeboorte;

    @Embedded
    @AttributeOverride(name = LocatieomschrijvingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "OmsLocGeboorte"))
    @JsonProperty
    private LocatieomschrijvingAttribuut omschrijvingLocatieGeboorte;

    @Embedded
    @AssociationOverride(name = LandGebiedAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "LandGebiedGeboorte"))
    @JsonProperty
    private LandGebiedAttribuut landGebiedGeboorte;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractHisPersoonGeboorteModel() {
    }

    /**
     * Constructor voor het initialiseren van alle attributen. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param persoon persoon van HisPersoonGeboorteModel
     * @param datumGeboorte datumGeboorte van HisPersoonGeboorteModel
     * @param gemeenteGeboorte gemeenteGeboorte van HisPersoonGeboorteModel
     * @param woonplaatsnaamGeboorte woonplaatsnaamGeboorte van HisPersoonGeboorteModel
     * @param buitenlandsePlaatsGeboorte buitenlandsePlaatsGeboorte van HisPersoonGeboorteModel
     * @param buitenlandseRegioGeboorte buitenlandseRegioGeboorte van HisPersoonGeboorteModel
     * @param omschrijvingLocatieGeboorte omschrijvingLocatieGeboorte van HisPersoonGeboorteModel
     * @param landGebiedGeboorte landGebiedGeboorte van HisPersoonGeboorteModel
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     */
    public AbstractHisPersoonGeboorteModel(
        final PersoonHisVolledig persoon,
        final DatumEvtDeelsOnbekendAttribuut datumGeboorte,
        final GemeenteAttribuut gemeenteGeboorte,
        final NaamEnumeratiewaardeAttribuut woonplaatsnaamGeboorte,
        final BuitenlandsePlaatsAttribuut buitenlandsePlaatsGeboorte,
        final BuitenlandseRegioAttribuut buitenlandseRegioGeboorte,
        final LocatieomschrijvingAttribuut omschrijvingLocatieGeboorte,
        final LandGebiedAttribuut landGebiedGeboorte,
        final ActieModel actieInhoud)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        this.persoon = persoon;
        this.datumGeboorte = datumGeboorte;
        this.gemeenteGeboorte = gemeenteGeboorte;
        this.woonplaatsnaamGeboorte = woonplaatsnaamGeboorte;
        this.buitenlandsePlaatsGeboorte = buitenlandsePlaatsGeboorte;
        this.buitenlandseRegioGeboorte = buitenlandseRegioGeboorte;
        this.omschrijvingLocatieGeboorte = omschrijvingLocatieGeboorte;
        this.landGebiedGeboorte = landGebiedGeboorte;
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public AbstractHisPersoonGeboorteModel(final AbstractHisPersoonGeboorteModel kopie) {
        super(kopie);
        persoon = kopie.getPersoon();
        datumGeboorte = kopie.getDatumGeboorte();
        gemeenteGeboorte = kopie.getGemeenteGeboorte();
        woonplaatsnaamGeboorte = kopie.getWoonplaatsnaamGeboorte();
        buitenlandsePlaatsGeboorte = kopie.getBuitenlandsePlaatsGeboorte();
        buitenlandseRegioGeboorte = kopie.getBuitenlandseRegioGeboorte();
        omschrijvingLocatieGeboorte = kopie.getOmschrijvingLocatieGeboorte();
        landGebiedGeboorte = kopie.getLandGebiedGeboorte();

    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param persoonHisVolledig instantie van A-laag klasse.
     * @param groep groep
     * @param actieInhoud Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public AbstractHisPersoonGeboorteModel(final PersoonHisVolledig persoonHisVolledig, final PersoonGeboorteGroep groep, final ActieModel actieInhoud) {
        this.persoon = persoonHisVolledig;
        this.datumGeboorte = groep.getDatumGeboorte();
        this.gemeenteGeboorte = groep.getGemeenteGeboorte();
        this.woonplaatsnaamGeboorte = groep.getWoonplaatsnaamGeboorte();
        this.buitenlandsePlaatsGeboorte = groep.getBuitenlandsePlaatsGeboorte();
        this.buitenlandseRegioGeboorte = groep.getBuitenlandseRegioGeboorte();
        this.omschrijvingLocatieGeboorte = groep.getOmschrijvingLocatieGeboorte();
        this.landGebiedGeboorte = groep.getLandGebiedGeboorte();
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Retourneert ID van His Persoon Geboorte.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van His Persoon Geboorte.
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
    @AttribuutAccessor(dbObjectId = 9725)
    public DatumEvtDeelsOnbekendAttribuut getDatumGeboorte() {
        return datumGeboorte;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9726)
    public GemeenteAttribuut getGemeenteGeboorte() {
        return gemeenteGeboorte;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9727)
    public NaamEnumeratiewaardeAttribuut getWoonplaatsnaamGeboorte() {
        return woonplaatsnaamGeboorte;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9728)
    public BuitenlandsePlaatsAttribuut getBuitenlandsePlaatsGeboorte() {
        return buitenlandsePlaatsGeboorte;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9729)
    public BuitenlandseRegioAttribuut getBuitenlandseRegioGeboorte() {
        return buitenlandseRegioGeboorte;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9730)
    public LocatieomschrijvingAttribuut getOmschrijvingLocatieGeboorte() {
        return omschrijvingLocatieGeboorte;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9731)
    public LandGebiedAttribuut getLandGebiedGeboorte() {
        return landGebiedGeboorte;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (datumGeboorte != null) {
            attributen.add(datumGeboorte);
        }
        if (gemeenteGeboorte != null) {
            attributen.add(gemeenteGeboorte);
        }
        if (woonplaatsnaamGeboorte != null) {
            attributen.add(woonplaatsnaamGeboorte);
        }
        if (buitenlandsePlaatsGeboorte != null) {
            attributen.add(buitenlandsePlaatsGeboorte);
        }
        if (buitenlandseRegioGeboorte != null) {
            attributen.add(buitenlandseRegioGeboorte);
        }
        if (omschrijvingLocatieGeboorte != null) {
            attributen.add(omschrijvingLocatieGeboorte);
        }
        if (landGebiedGeboorte != null) {
            attributen.add(landGebiedGeboorte);
        }
        return attributen;
    }

    /**
     * Retourneert het Element behorende bij deze groep.
     *
     * @return Element enum instantie behorende bij deze groep.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PERSOON_GEBOORTE;
    }

}

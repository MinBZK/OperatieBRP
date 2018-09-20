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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenEindeRelatieAttribuut;
import nl.bzk.brp.model.annotaties.AttribuutAccessor;
import nl.bzk.brp.model.annotaties.GroepAccessor;
import nl.bzk.brp.model.basis.AbstractFormeelHistorischMetActieVerantwoording;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.BestaansperiodeFormeelImplicietMaterieel;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.impl.kern.RelatieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.RelatieHisVolledig;
import nl.bzk.brp.model.logisch.kern.RelatieStandaardGroep;
import nl.bzk.brp.model.logisch.kern.RelatieStandaardGroepBasis;

/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@GroepAccessor(dbObjectId = 3599)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractHisRelatieModel extends AbstractFormeelHistorischMetActieVerantwoording implements RelatieStandaardGroepBasis,
        ModelIdentificeerbaar<Integer>, BestaansperiodeFormeelImplicietMaterieel, ElementIdentificeerbaar
{

    @Id
    @SequenceGenerator(name = "HIS_RELATIE", sequenceName = "Kern.seq_His_Relatie")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_RELATIE")
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = RelatieHisVolledigImpl.class)
    @JoinColumn(name = "Relatie")
    @JsonBackReference
    private RelatieHisVolledig relatie;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatAanv"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datumAanvang;

    @Embedded
    @AssociationOverride(name = GemeenteAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "GemAanv"))
    @JsonProperty
    private GemeenteAttribuut gemeenteAanvang;

    @Embedded
    @AttributeOverride(name = NaamEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "WplnaamAanv"))
    @JsonProperty
    private NaamEnumeratiewaardeAttribuut woonplaatsnaamAanvang;

    @Embedded
    @AttributeOverride(name = BuitenlandsePlaatsAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "BLPlaatsAanv"))
    @JsonProperty
    private BuitenlandsePlaatsAttribuut buitenlandsePlaatsAanvang;

    @Embedded
    @AttributeOverride(name = BuitenlandseRegioAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "BLRegioAanv"))
    @JsonProperty
    private BuitenlandseRegioAttribuut buitenlandseRegioAanvang;

    @Embedded
    @AttributeOverride(name = LocatieomschrijvingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "OmsLocAanv"))
    @JsonProperty
    private LocatieomschrijvingAttribuut omschrijvingLocatieAanvang;

    @Embedded
    @AssociationOverride(name = LandGebiedAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "LandGebiedAanv"))
    @JsonProperty
    private LandGebiedAttribuut landGebiedAanvang;

    @Embedded
    @AssociationOverride(name = RedenEindeRelatieAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "RdnEinde"))
    @JsonProperty
    private RedenEindeRelatieAttribuut redenEinde;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatEinde"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datumEinde;

    @Embedded
    @AssociationOverride(name = GemeenteAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "GemEinde"))
    @JsonProperty
    private GemeenteAttribuut gemeenteEinde;

    @Embedded
    @AttributeOverride(name = NaamEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "WplnaamEinde"))
    @JsonProperty
    private NaamEnumeratiewaardeAttribuut woonplaatsnaamEinde;

    @Embedded
    @AttributeOverride(name = BuitenlandsePlaatsAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "BLPlaatsEinde"))
    @JsonProperty
    private BuitenlandsePlaatsAttribuut buitenlandsePlaatsEinde;

    @Embedded
    @AttributeOverride(name = BuitenlandseRegioAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "BLRegioEinde"))
    @JsonProperty
    private BuitenlandseRegioAttribuut buitenlandseRegioEinde;

    @Embedded
    @AttributeOverride(name = LocatieomschrijvingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "OmsLocEinde"))
    @JsonProperty
    private LocatieomschrijvingAttribuut omschrijvingLocatieEinde;

    @Embedded
    @AssociationOverride(name = LandGebiedAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "LandGebiedEinde"))
    @JsonProperty
    private LandGebiedAttribuut landGebiedEinde;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractHisRelatieModel() {
    }

    /**
     * Constructor voor het initialiseren van alle attributen. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param relatie relatie van HisRelatieModel
     * @param datumAanvang datumAanvang van HisRelatieModel
     * @param gemeenteAanvang gemeenteAanvang van HisRelatieModel
     * @param woonplaatsnaamAanvang woonplaatsnaamAanvang van HisRelatieModel
     * @param buitenlandsePlaatsAanvang buitenlandsePlaatsAanvang van HisRelatieModel
     * @param buitenlandseRegioAanvang buitenlandseRegioAanvang van HisRelatieModel
     * @param omschrijvingLocatieAanvang omschrijvingLocatieAanvang van HisRelatieModel
     * @param landGebiedAanvang landGebiedAanvang van HisRelatieModel
     * @param redenEinde redenEinde van HisRelatieModel
     * @param datumEinde datumEinde van HisRelatieModel
     * @param gemeenteEinde gemeenteEinde van HisRelatieModel
     * @param woonplaatsnaamEinde woonplaatsnaamEinde van HisRelatieModel
     * @param buitenlandsePlaatsEinde buitenlandsePlaatsEinde van HisRelatieModel
     * @param buitenlandseRegioEinde buitenlandseRegioEinde van HisRelatieModel
     * @param omschrijvingLocatieEinde omschrijvingLocatieEinde van HisRelatieModel
     * @param landGebiedEinde landGebiedEinde van HisRelatieModel
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     */
    public AbstractHisRelatieModel(
        final RelatieHisVolledig relatie,
        final DatumEvtDeelsOnbekendAttribuut datumAanvang,
        final GemeenteAttribuut gemeenteAanvang,
        final NaamEnumeratiewaardeAttribuut woonplaatsnaamAanvang,
        final BuitenlandsePlaatsAttribuut buitenlandsePlaatsAanvang,
        final BuitenlandseRegioAttribuut buitenlandseRegioAanvang,
        final LocatieomschrijvingAttribuut omschrijvingLocatieAanvang,
        final LandGebiedAttribuut landGebiedAanvang,
        final RedenEindeRelatieAttribuut redenEinde,
        final DatumEvtDeelsOnbekendAttribuut datumEinde,
        final GemeenteAttribuut gemeenteEinde,
        final NaamEnumeratiewaardeAttribuut woonplaatsnaamEinde,
        final BuitenlandsePlaatsAttribuut buitenlandsePlaatsEinde,
        final BuitenlandseRegioAttribuut buitenlandseRegioEinde,
        final LocatieomschrijvingAttribuut omschrijvingLocatieEinde,
        final LandGebiedAttribuut landGebiedEinde,
        final ActieModel actieInhoud)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        this.relatie = relatie;
        this.datumAanvang = datumAanvang;
        this.gemeenteAanvang = gemeenteAanvang;
        this.woonplaatsnaamAanvang = woonplaatsnaamAanvang;
        this.buitenlandsePlaatsAanvang = buitenlandsePlaatsAanvang;
        this.buitenlandseRegioAanvang = buitenlandseRegioAanvang;
        this.omschrijvingLocatieAanvang = omschrijvingLocatieAanvang;
        this.landGebiedAanvang = landGebiedAanvang;
        this.redenEinde = redenEinde;
        this.datumEinde = datumEinde;
        this.gemeenteEinde = gemeenteEinde;
        this.woonplaatsnaamEinde = woonplaatsnaamEinde;
        this.buitenlandsePlaatsEinde = buitenlandsePlaatsEinde;
        this.buitenlandseRegioEinde = buitenlandseRegioEinde;
        this.omschrijvingLocatieEinde = omschrijvingLocatieEinde;
        this.landGebiedEinde = landGebiedEinde;
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public AbstractHisRelatieModel(final AbstractHisRelatieModel kopie) {
        super(kopie);
        relatie = kopie.getRelatie();
        datumAanvang = kopie.getDatumAanvang();
        gemeenteAanvang = kopie.getGemeenteAanvang();
        woonplaatsnaamAanvang = kopie.getWoonplaatsnaamAanvang();
        buitenlandsePlaatsAanvang = kopie.getBuitenlandsePlaatsAanvang();
        buitenlandseRegioAanvang = kopie.getBuitenlandseRegioAanvang();
        omschrijvingLocatieAanvang = kopie.getOmschrijvingLocatieAanvang();
        landGebiedAanvang = kopie.getLandGebiedAanvang();
        redenEinde = kopie.getRedenEinde();
        datumEinde = kopie.getDatumEinde();
        gemeenteEinde = kopie.getGemeenteEinde();
        woonplaatsnaamEinde = kopie.getWoonplaatsnaamEinde();
        buitenlandsePlaatsEinde = kopie.getBuitenlandsePlaatsEinde();
        buitenlandseRegioEinde = kopie.getBuitenlandseRegioEinde();
        omschrijvingLocatieEinde = kopie.getOmschrijvingLocatieEinde();
        landGebiedEinde = kopie.getLandGebiedEinde();

    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param relatieHisVolledig instantie van A-laag klasse.
     * @param groep groep
     * @param actieInhoud Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public AbstractHisRelatieModel(final RelatieHisVolledig relatieHisVolledig, final RelatieStandaardGroep groep, final ActieModel actieInhoud) {
        this.relatie = relatieHisVolledig;
        if (groep != null) {
            this.datumAanvang = groep.getDatumAanvang();
        }
        if (groep != null) {
            this.gemeenteAanvang = groep.getGemeenteAanvang();
        }
        if (groep != null) {
            this.woonplaatsnaamAanvang = groep.getWoonplaatsnaamAanvang();
        }
        if (groep != null) {
            this.buitenlandsePlaatsAanvang = groep.getBuitenlandsePlaatsAanvang();
        }
        if (groep != null) {
            this.buitenlandseRegioAanvang = groep.getBuitenlandseRegioAanvang();
        }
        if (groep != null) {
            this.omschrijvingLocatieAanvang = groep.getOmschrijvingLocatieAanvang();
        }
        if (groep != null) {
            this.landGebiedAanvang = groep.getLandGebiedAanvang();
        }
        if (groep != null) {
            this.redenEinde = groep.getRedenEinde();
        }
        if (groep != null) {
            this.datumEinde = groep.getDatumEinde();
        }
        if (groep != null) {
            this.gemeenteEinde = groep.getGemeenteEinde();
        }
        if (groep != null) {
            this.woonplaatsnaamEinde = groep.getWoonplaatsnaamEinde();
        }
        if (groep != null) {
            this.buitenlandsePlaatsEinde = groep.getBuitenlandsePlaatsEinde();
        }
        if (groep != null) {
            this.buitenlandseRegioEinde = groep.getBuitenlandseRegioEinde();
        }
        if (groep != null) {
            this.omschrijvingLocatieEinde = groep.getOmschrijvingLocatieEinde();
        }
        if (groep != null) {
            this.landGebiedEinde = groep.getLandGebiedEinde();
        }
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Retourneert ID van His Relatie.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Relatie van His Relatie.
     *
     * @return Relatie.
     */
    public RelatieHisVolledig getRelatie() {
        return relatie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9820)
    public DatumEvtDeelsOnbekendAttribuut getDatumAanvang() {
        return datumAanvang;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9821)
    public GemeenteAttribuut getGemeenteAanvang() {
        return gemeenteAanvang;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9822)
    public NaamEnumeratiewaardeAttribuut getWoonplaatsnaamAanvang() {
        return woonplaatsnaamAanvang;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9823)
    public BuitenlandsePlaatsAttribuut getBuitenlandsePlaatsAanvang() {
        return buitenlandsePlaatsAanvang;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9824)
    public BuitenlandseRegioAttribuut getBuitenlandseRegioAanvang() {
        return buitenlandseRegioAanvang;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9825)
    public LocatieomschrijvingAttribuut getOmschrijvingLocatieAanvang() {
        return omschrijvingLocatieAanvang;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9826)
    public LandGebiedAttribuut getLandGebiedAanvang() {
        return landGebiedAanvang;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9827)
    public RedenEindeRelatieAttribuut getRedenEinde() {
        return redenEinde;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9828)
    public DatumEvtDeelsOnbekendAttribuut getDatumEinde() {
        return datumEinde;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9829)
    public GemeenteAttribuut getGemeenteEinde() {
        return gemeenteEinde;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9830)
    public NaamEnumeratiewaardeAttribuut getWoonplaatsnaamEinde() {
        return woonplaatsnaamEinde;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9831)
    public BuitenlandsePlaatsAttribuut getBuitenlandsePlaatsEinde() {
        return buitenlandsePlaatsEinde;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9832)
    public BuitenlandseRegioAttribuut getBuitenlandseRegioEinde() {
        return buitenlandseRegioEinde;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9833)
    public LocatieomschrijvingAttribuut getOmschrijvingLocatieEinde() {
        return omschrijvingLocatieEinde;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9834)
    public LandGebiedAttribuut getLandGebiedEinde() {
        return landGebiedEinde;
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
        if (gemeenteAanvang != null) {
            attributen.add(gemeenteAanvang);
        }
        if (woonplaatsnaamAanvang != null) {
            attributen.add(woonplaatsnaamAanvang);
        }
        if (buitenlandsePlaatsAanvang != null) {
            attributen.add(buitenlandsePlaatsAanvang);
        }
        if (buitenlandseRegioAanvang != null) {
            attributen.add(buitenlandseRegioAanvang);
        }
        if (omschrijvingLocatieAanvang != null) {
            attributen.add(omschrijvingLocatieAanvang);
        }
        if (landGebiedAanvang != null) {
            attributen.add(landGebiedAanvang);
        }
        if (redenEinde != null) {
            attributen.add(redenEinde);
        }
        if (datumEinde != null) {
            attributen.add(datumEinde);
        }
        if (gemeenteEinde != null) {
            attributen.add(gemeenteEinde);
        }
        if (woonplaatsnaamEinde != null) {
            attributen.add(woonplaatsnaamEinde);
        }
        if (buitenlandsePlaatsEinde != null) {
            attributen.add(buitenlandsePlaatsEinde);
        }
        if (buitenlandseRegioEinde != null) {
            attributen.add(buitenlandseRegioEinde);
        }
        if (omschrijvingLocatieEinde != null) {
            attributen.add(omschrijvingLocatieEinde);
        }
        if (landGebiedEinde != null) {
            attributen.add(landGebiedEinde);
        }
        return attributen;
    }

    /**
     * Retourneert het Element behorende bij deze groep.
     *
     * @return Element enum instantie behorende bij deze groep.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.RELATIE_STANDAARD;
    }

}

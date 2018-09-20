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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GeslachtsnaamstamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ScheidingstekenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornamenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoorvoegselAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitelAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NaamgebruikAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PredicaatAttribuut;
import nl.bzk.brp.model.annotaties.AttribuutAccessor;
import nl.bzk.brp.model.annotaties.GroepAccessor;
import nl.bzk.brp.model.basis.AbstractFormeelHistorischMetActieVerantwoording;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.kern.PersoonNaamgebruikGroep;
import nl.bzk.brp.model.logisch.kern.PersoonNaamgebruikGroepBasis;

/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@GroepAccessor(dbObjectId = 3487)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractHisPersoonNaamgebruikModel extends AbstractFormeelHistorischMetActieVerantwoording implements PersoonNaamgebruikGroepBasis,
        ModelIdentificeerbaar<Integer>, ElementIdentificeerbaar
{

    @Id
    @SequenceGenerator(name = "HIS_PERSOONNAAMGEBRUIK", sequenceName = "Kern.seq_His_PersNaamgebruik")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PERSOONNAAMGEBRUIK")
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = PersoonHisVolledigImpl.class)
    @JoinColumn(name = "Pers")
    @JsonBackReference
    private PersoonHisVolledig persoon;

    @Embedded
    @AttributeOverride(name = NaamgebruikAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Naamgebruik"))
    @JsonProperty
    private NaamgebruikAttribuut naamgebruik;

    @Embedded
    @AttributeOverride(name = JaNeeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndNaamgebruikAfgeleid"))
    @JsonProperty
    private JaNeeAttribuut indicatieNaamgebruikAfgeleid;

    @Embedded
    @AssociationOverride(name = PredicaatAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "PredicaatNaamgebruik"))
    @JsonProperty
    private PredicaatAttribuut predicaatNaamgebruik;

    @Embedded
    @AttributeOverride(name = VoornamenAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "VoornamenNaamgebruik"))
    @JsonProperty
    private VoornamenAttribuut voornamenNaamgebruik;

    @Embedded
    @AssociationOverride(name = AdellijkeTitelAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "AdellijkeTitelNaamgebruik"))
    @JsonProperty
    private AdellijkeTitelAttribuut adellijkeTitelNaamgebruik;

    @Embedded
    @AttributeOverride(name = VoorvoegselAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "VoorvoegselNaamgebruik"))
    @JsonProperty
    private VoorvoegselAttribuut voorvoegselNaamgebruik;

    @Embedded
    @AttributeOverride(name = ScheidingstekenAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "ScheidingstekenNaamgebruik"))
    @JsonProperty
    private ScheidingstekenAttribuut scheidingstekenNaamgebruik;

    @Embedded
    @AttributeOverride(name = GeslachtsnaamstamAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "GeslnaamstamNaamgebruik"))
    @JsonProperty
    private GeslachtsnaamstamAttribuut geslachtsnaamstamNaamgebruik;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractHisPersoonNaamgebruikModel() {
    }

    /**
     * Constructor voor het initialiseren van alle attributen. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param persoon persoon van HisPersoonNaamgebruikModel
     * @param naamgebruik naamgebruik van HisPersoonNaamgebruikModel
     * @param indicatieNaamgebruikAfgeleid indicatieNaamgebruikAfgeleid van HisPersoonNaamgebruikModel
     * @param predicaatNaamgebruik predicaatNaamgebruik van HisPersoonNaamgebruikModel
     * @param voornamenNaamgebruik voornamenNaamgebruik van HisPersoonNaamgebruikModel
     * @param adellijkeTitelNaamgebruik adellijkeTitelNaamgebruik van HisPersoonNaamgebruikModel
     * @param voorvoegselNaamgebruik voorvoegselNaamgebruik van HisPersoonNaamgebruikModel
     * @param scheidingstekenNaamgebruik scheidingstekenNaamgebruik van HisPersoonNaamgebruikModel
     * @param geslachtsnaamstamNaamgebruik geslachtsnaamstamNaamgebruik van HisPersoonNaamgebruikModel
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     */
    public AbstractHisPersoonNaamgebruikModel(
        final PersoonHisVolledig persoon,
        final NaamgebruikAttribuut naamgebruik,
        final JaNeeAttribuut indicatieNaamgebruikAfgeleid,
        final PredicaatAttribuut predicaatNaamgebruik,
        final VoornamenAttribuut voornamenNaamgebruik,
        final AdellijkeTitelAttribuut adellijkeTitelNaamgebruik,
        final VoorvoegselAttribuut voorvoegselNaamgebruik,
        final ScheidingstekenAttribuut scheidingstekenNaamgebruik,
        final GeslachtsnaamstamAttribuut geslachtsnaamstamNaamgebruik,
        final ActieModel actieInhoud)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        this.persoon = persoon;
        this.naamgebruik = naamgebruik;
        this.indicatieNaamgebruikAfgeleid = indicatieNaamgebruikAfgeleid;
        this.predicaatNaamgebruik = predicaatNaamgebruik;
        this.voornamenNaamgebruik = voornamenNaamgebruik;
        this.adellijkeTitelNaamgebruik = adellijkeTitelNaamgebruik;
        this.voorvoegselNaamgebruik = voorvoegselNaamgebruik;
        this.scheidingstekenNaamgebruik = scheidingstekenNaamgebruik;
        this.geslachtsnaamstamNaamgebruik = geslachtsnaamstamNaamgebruik;
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public AbstractHisPersoonNaamgebruikModel(final AbstractHisPersoonNaamgebruikModel kopie) {
        super(kopie);
        persoon = kopie.getPersoon();
        naamgebruik = kopie.getNaamgebruik();
        indicatieNaamgebruikAfgeleid = kopie.getIndicatieNaamgebruikAfgeleid();
        predicaatNaamgebruik = kopie.getPredicaatNaamgebruik();
        voornamenNaamgebruik = kopie.getVoornamenNaamgebruik();
        adellijkeTitelNaamgebruik = kopie.getAdellijkeTitelNaamgebruik();
        voorvoegselNaamgebruik = kopie.getVoorvoegselNaamgebruik();
        scheidingstekenNaamgebruik = kopie.getScheidingstekenNaamgebruik();
        geslachtsnaamstamNaamgebruik = kopie.getGeslachtsnaamstamNaamgebruik();

    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param persoonHisVolledig instantie van A-laag klasse.
     * @param groep groep
     * @param actieInhoud Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public AbstractHisPersoonNaamgebruikModel(
        final PersoonHisVolledig persoonHisVolledig,
        final PersoonNaamgebruikGroep groep,
        final ActieModel actieInhoud)
    {
        this.persoon = persoonHisVolledig;
        this.naamgebruik = groep.getNaamgebruik();
        this.indicatieNaamgebruikAfgeleid = groep.getIndicatieNaamgebruikAfgeleid();
        this.predicaatNaamgebruik = groep.getPredicaatNaamgebruik();
        this.voornamenNaamgebruik = groep.getVoornamenNaamgebruik();
        this.adellijkeTitelNaamgebruik = groep.getAdellijkeTitelNaamgebruik();
        this.voorvoegselNaamgebruik = groep.getVoorvoegselNaamgebruik();
        this.scheidingstekenNaamgebruik = groep.getScheidingstekenNaamgebruik();
        this.geslachtsnaamstamNaamgebruik = groep.getGeslachtsnaamstamNaamgebruik();
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Retourneert ID van His Persoon Naamgebruik.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van His Persoon Naamgebruik.
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
    @AttribuutAccessor(dbObjectId = 9749)
    public NaamgebruikAttribuut getNaamgebruik() {
        return naamgebruik;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9751)
    public JaNeeAttribuut getIndicatieNaamgebruikAfgeleid() {
        return indicatieNaamgebruikAfgeleid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9752)
    public PredicaatAttribuut getPredicaatNaamgebruik() {
        return predicaatNaamgebruik;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9753)
    public VoornamenAttribuut getVoornamenNaamgebruik() {
        return voornamenNaamgebruik;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9754)
    public AdellijkeTitelAttribuut getAdellijkeTitelNaamgebruik() {
        return adellijkeTitelNaamgebruik;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9755)
    public VoorvoegselAttribuut getVoorvoegselNaamgebruik() {
        return voorvoegselNaamgebruik;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9756)
    public ScheidingstekenAttribuut getScheidingstekenNaamgebruik() {
        return scheidingstekenNaamgebruik;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9757)
    public GeslachtsnaamstamAttribuut getGeslachtsnaamstamNaamgebruik() {
        return geslachtsnaamstamNaamgebruik;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (naamgebruik != null) {
            attributen.add(naamgebruik);
        }
        if (indicatieNaamgebruikAfgeleid != null) {
            attributen.add(indicatieNaamgebruikAfgeleid);
        }
        if (predicaatNaamgebruik != null) {
            attributen.add(predicaatNaamgebruik);
        }
        if (voornamenNaamgebruik != null) {
            attributen.add(voornamenNaamgebruik);
        }
        if (adellijkeTitelNaamgebruik != null) {
            attributen.add(adellijkeTitelNaamgebruik);
        }
        if (voorvoegselNaamgebruik != null) {
            attributen.add(voorvoegselNaamgebruik);
        }
        if (scheidingstekenNaamgebruik != null) {
            attributen.add(scheidingstekenNaamgebruik);
        }
        if (geslachtsnaamstamNaamgebruik != null) {
            attributen.add(geslachtsnaamstamNaamgebruik);
        }
        return attributen;
    }

    /**
     * Retourneert het Element behorende bij deze groep.
     *
     * @return Element enum instantie behorende bij deze groep.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PERSOON_NAAMGEBRUIK;
    }

}

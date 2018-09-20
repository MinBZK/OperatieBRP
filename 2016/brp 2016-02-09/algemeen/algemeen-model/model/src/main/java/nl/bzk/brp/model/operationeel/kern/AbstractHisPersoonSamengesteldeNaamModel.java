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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PredicaatAttribuut;
import nl.bzk.brp.model.annotaties.AttribuutAccessor;
import nl.bzk.brp.model.annotaties.GroepAccessor;
import nl.bzk.brp.model.basis.AbstractMaterieelHistorischMetActieVerantwoording;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.MaterieleHistorie;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.kern.PersoonSamengesteldeNaamGroep;
import nl.bzk.brp.model.logisch.kern.PersoonSamengesteldeNaamGroepBasis;

/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@GroepAccessor(dbObjectId = 3557)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractHisPersoonSamengesteldeNaamModel extends AbstractMaterieelHistorischMetActieVerantwoording implements
        PersoonSamengesteldeNaamGroepBasis, ModelIdentificeerbaar<Integer>, ElementIdentificeerbaar
{

    @Id
    @SequenceGenerator(name = "HIS_PERSOONSAMENGESTELDENAAM", sequenceName = "Kern.seq_His_PersSamengesteldeNaam")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PERSOONSAMENGESTELDENAAM")
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = PersoonHisVolledigImpl.class)
    @JoinColumn(name = "Pers")
    @JsonBackReference
    private PersoonHisVolledig persoon;

    @Embedded
    @AttributeOverride(name = JaNeeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndAfgeleid"))
    @JsonProperty
    private JaNeeAttribuut indicatieAfgeleid;

    @Embedded
    @AttributeOverride(name = JaNeeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndNreeks"))
    @JsonProperty
    private JaNeeAttribuut indicatieNamenreeks;

    @Embedded
    @AssociationOverride(name = PredicaatAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "Predicaat"))
    @JsonProperty
    private PredicaatAttribuut predicaat;

    @Embedded
    @AttributeOverride(name = VoornamenAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Voornamen"))
    @JsonProperty
    private VoornamenAttribuut voornamen;

    @Embedded
    @AssociationOverride(name = AdellijkeTitelAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "AdellijkeTitel"))
    @JsonProperty
    private AdellijkeTitelAttribuut adellijkeTitel;

    @Embedded
    @AttributeOverride(name = VoorvoegselAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Voorvoegsel"))
    @JsonProperty
    private VoorvoegselAttribuut voorvoegsel;

    @Embedded
    @AttributeOverride(name = ScheidingstekenAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Scheidingsteken"))
    @JsonProperty
    private ScheidingstekenAttribuut scheidingsteken;

    @Embedded
    @AttributeOverride(name = GeslachtsnaamstamAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Geslnaamstam"))
    @JsonProperty
    private GeslachtsnaamstamAttribuut geslachtsnaamstam;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractHisPersoonSamengesteldeNaamModel() {
    }

    /**
     * Constructor voor het initialiseren van alle attributen. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param persoon persoon van HisPersoonSamengesteldeNaamModel
     * @param indicatieAfgeleid indicatieAfgeleid van HisPersoonSamengesteldeNaamModel
     * @param indicatieNamenreeks indicatieNamenreeks van HisPersoonSamengesteldeNaamModel
     * @param predicaat predicaat van HisPersoonSamengesteldeNaamModel
     * @param voornamen voornamen van HisPersoonSamengesteldeNaamModel
     * @param adellijkeTitel adellijkeTitel van HisPersoonSamengesteldeNaamModel
     * @param voorvoegsel voorvoegsel van HisPersoonSamengesteldeNaamModel
     * @param scheidingsteken scheidingsteken van HisPersoonSamengesteldeNaamModel
     * @param geslachtsnaamstam geslachtsnaamstam van HisPersoonSamengesteldeNaamModel
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     * @param historie De groep uit een bericht
     */
    public AbstractHisPersoonSamengesteldeNaamModel(
        final PersoonHisVolledig persoon,
        final JaNeeAttribuut indicatieAfgeleid,
        final JaNeeAttribuut indicatieNamenreeks,
        final PredicaatAttribuut predicaat,
        final VoornamenAttribuut voornamen,
        final AdellijkeTitelAttribuut adellijkeTitel,
        final VoorvoegselAttribuut voorvoegsel,
        final ScheidingstekenAttribuut scheidingsteken,
        final GeslachtsnaamstamAttribuut geslachtsnaamstam,
        final ActieModel actieInhoud,
        final MaterieleHistorie historie)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        this.persoon = persoon;
        this.indicatieAfgeleid = indicatieAfgeleid;
        this.indicatieNamenreeks = indicatieNamenreeks;
        this.predicaat = predicaat;
        this.voornamen = voornamen;
        this.adellijkeTitel = adellijkeTitel;
        this.voorvoegsel = voorvoegsel;
        this.scheidingsteken = scheidingsteken;
        this.geslachtsnaamstam = geslachtsnaamstam;
        setVerantwoordingInhoud(actieInhoud);
        getMaterieleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());
        getMaterieleHistorie().setDatumAanvangGeldigheid(historie.getDatumAanvangGeldigheid());
        getMaterieleHistorie().setDatumEindeGeldigheid(historie.getDatumEindeGeldigheid());

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public AbstractHisPersoonSamengesteldeNaamModel(final AbstractHisPersoonSamengesteldeNaamModel kopie) {
        super(kopie);
        persoon = kopie.getPersoon();
        indicatieAfgeleid = kopie.getIndicatieAfgeleid();
        indicatieNamenreeks = kopie.getIndicatieNamenreeks();
        predicaat = kopie.getPredicaat();
        voornamen = kopie.getVoornamen();
        adellijkeTitel = kopie.getAdellijkeTitel();
        voorvoegsel = kopie.getVoorvoegsel();
        scheidingsteken = kopie.getScheidingsteken();
        geslachtsnaamstam = kopie.getGeslachtsnaamstam();

    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param persoonHisVolledig instantie van A-laag klasse.
     * @param groep groep
     * @param historie historie
     * @param actieInhoud Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public AbstractHisPersoonSamengesteldeNaamModel(
        final PersoonHisVolledig persoonHisVolledig,
        final PersoonSamengesteldeNaamGroep groep,
        final MaterieleHistorie historie,
        final ActieModel actieInhoud)
    {
        this.persoon = persoonHisVolledig;
        this.indicatieAfgeleid = groep.getIndicatieAfgeleid();
        this.indicatieNamenreeks = groep.getIndicatieNamenreeks();
        this.predicaat = groep.getPredicaat();
        this.voornamen = groep.getVoornamen();
        this.adellijkeTitel = groep.getAdellijkeTitel();
        this.voorvoegsel = groep.getVoorvoegsel();
        this.scheidingsteken = groep.getScheidingsteken();
        this.geslachtsnaamstam = groep.getGeslachtsnaamstam();
        getMaterieleHistorie().setDatumAanvangGeldigheid(historie.getDatumAanvangGeldigheid());
        getMaterieleHistorie().setDatumEindeGeldigheid(historie.getDatumEindeGeldigheid());
        setVerantwoordingInhoud(actieInhoud);
        getMaterieleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Retourneert ID van His Persoon Samengestelde naam.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van His Persoon Samengestelde naam.
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
    @AttribuutAccessor(dbObjectId = 9717)
    public JaNeeAttribuut getIndicatieAfgeleid() {
        return indicatieAfgeleid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9718)
    public JaNeeAttribuut getIndicatieNamenreeks() {
        return indicatieNamenreeks;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9719)
    public PredicaatAttribuut getPredicaat() {
        return predicaat;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9720)
    public VoornamenAttribuut getVoornamen() {
        return voornamen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9721)
    public AdellijkeTitelAttribuut getAdellijkeTitel() {
        return adellijkeTitel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9722)
    public VoorvoegselAttribuut getVoorvoegsel() {
        return voorvoegsel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9723)
    public ScheidingstekenAttribuut getScheidingsteken() {
        return scheidingsteken;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9724)
    public GeslachtsnaamstamAttribuut getGeslachtsnaamstam() {
        return geslachtsnaamstam;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (indicatieAfgeleid != null) {
            attributen.add(indicatieAfgeleid);
        }
        if (indicatieNamenreeks != null) {
            attributen.add(indicatieNamenreeks);
        }
        if (predicaat != null) {
            attributen.add(predicaat);
        }
        if (voornamen != null) {
            attributen.add(voornamen);
        }
        if (adellijkeTitel != null) {
            attributen.add(adellijkeTitel);
        }
        if (voorvoegsel != null) {
            attributen.add(voorvoegsel);
        }
        if (scheidingsteken != null) {
            attributen.add(scheidingsteken);
        }
        if (geslachtsnaamstam != null) {
            attributen.add(geslachtsnaamstam);
        }
        return attributen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HisPersoonSamengesteldeNaamModel kopieer() {
        return new HisPersoonSamengesteldeNaamModel(this);
    }

    /**
     * Retourneert het Element behorende bij deze groep.
     *
     * @return Element enum instantie behorende bij deze groep.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PERSOON_SAMENGESTELDENAAM;
    }

}

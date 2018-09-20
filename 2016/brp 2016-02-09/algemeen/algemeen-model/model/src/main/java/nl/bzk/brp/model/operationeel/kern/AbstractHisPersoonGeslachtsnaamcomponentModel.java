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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ScheidingstekenAttribuut;
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
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonGeslachtsnaamcomponentHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonGeslachtsnaamcomponentHisVolledig;
import nl.bzk.brp.model.logisch.kern.PersoonGeslachtsnaamcomponentStandaardGroep;
import nl.bzk.brp.model.logisch.kern.PersoonGeslachtsnaamcomponentStandaardGroepBasis;

/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@GroepAccessor(dbObjectId = 3598)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractHisPersoonGeslachtsnaamcomponentModel extends AbstractMaterieelHistorischMetActieVerantwoording implements
        PersoonGeslachtsnaamcomponentStandaardGroepBasis, ModelIdentificeerbaar<Integer>, ElementIdentificeerbaar
{

    @Id
    @SequenceGenerator(name = "HIS_PERSOONGESLACHTSNAAMCOMPONENT", sequenceName = "Kern.seq_His_PersGeslnaamcomp")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PERSOONGESLACHTSNAAMCOMPONENT")
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = PersoonGeslachtsnaamcomponentHisVolledigImpl.class)
    @JoinColumn(name = "PersGeslnaamcomp")
    @JsonBackReference
    private PersoonGeslachtsnaamcomponentHisVolledig persoonGeslachtsnaamcomponent;

    @Embedded
    @AssociationOverride(name = PredicaatAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "Predicaat"))
    @JsonProperty
    private PredicaatAttribuut predicaat;

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
    @AttributeOverride(name = GeslachtsnaamstamAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Stam"))
    @JsonProperty
    private GeslachtsnaamstamAttribuut stam;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractHisPersoonGeslachtsnaamcomponentModel() {
    }

    /**
     * Constructor voor het initialiseren van alle attributen. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param persoonGeslachtsnaamcomponent persoonGeslachtsnaamcomponent van HisPersoonGeslachtsnaamcomponentModel
     * @param predicaat predicaat van HisPersoonGeslachtsnaamcomponentModel
     * @param adellijkeTitel adellijkeTitel van HisPersoonGeslachtsnaamcomponentModel
     * @param voorvoegsel voorvoegsel van HisPersoonGeslachtsnaamcomponentModel
     * @param scheidingsteken scheidingsteken van HisPersoonGeslachtsnaamcomponentModel
     * @param stam stam van HisPersoonGeslachtsnaamcomponentModel
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     * @param historie De groep uit een bericht
     */
    public AbstractHisPersoonGeslachtsnaamcomponentModel(
        final PersoonGeslachtsnaamcomponentHisVolledig persoonGeslachtsnaamcomponent,
        final PredicaatAttribuut predicaat,
        final AdellijkeTitelAttribuut adellijkeTitel,
        final VoorvoegselAttribuut voorvoegsel,
        final ScheidingstekenAttribuut scheidingsteken,
        final GeslachtsnaamstamAttribuut stam,
        final ActieModel actieInhoud,
        final MaterieleHistorie historie)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        this.persoonGeslachtsnaamcomponent = persoonGeslachtsnaamcomponent;
        this.predicaat = predicaat;
        this.adellijkeTitel = adellijkeTitel;
        this.voorvoegsel = voorvoegsel;
        this.scheidingsteken = scheidingsteken;
        this.stam = stam;
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
    public AbstractHisPersoonGeslachtsnaamcomponentModel(final AbstractHisPersoonGeslachtsnaamcomponentModel kopie) {
        super(kopie);
        persoonGeslachtsnaamcomponent = kopie.getPersoonGeslachtsnaamcomponent();
        predicaat = kopie.getPredicaat();
        adellijkeTitel = kopie.getAdellijkeTitel();
        voorvoegsel = kopie.getVoorvoegsel();
        scheidingsteken = kopie.getScheidingsteken();
        stam = kopie.getStam();

    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param persoonGeslachtsnaamcomponentHisVolledig instantie van A-laag klasse.
     * @param groep groep
     * @param historie historie
     * @param actieInhoud Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public AbstractHisPersoonGeslachtsnaamcomponentModel(
        final PersoonGeslachtsnaamcomponentHisVolledig persoonGeslachtsnaamcomponentHisVolledig,
        final PersoonGeslachtsnaamcomponentStandaardGroep groep,
        final MaterieleHistorie historie,
        final ActieModel actieInhoud)
    {
        this.persoonGeslachtsnaamcomponent = persoonGeslachtsnaamcomponentHisVolledig;
        this.predicaat = groep.getPredicaat();
        this.adellijkeTitel = groep.getAdellijkeTitel();
        this.voorvoegsel = groep.getVoorvoegsel();
        this.scheidingsteken = groep.getScheidingsteken();
        this.stam = groep.getStam();
        getMaterieleHistorie().setDatumAanvangGeldigheid(historie.getDatumAanvangGeldigheid());
        getMaterieleHistorie().setDatumEindeGeldigheid(historie.getDatumEindeGeldigheid());
        setVerantwoordingInhoud(actieInhoud);
        getMaterieleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Retourneert ID van His Persoon \ Geslachtsnaamcomponent.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon \ Geslachtsnaamcomponent van His Persoon \ Geslachtsnaamcomponent.
     *
     * @return Persoon \ Geslachtsnaamcomponent.
     */
    public PersoonGeslachtsnaamcomponentHisVolledig getPersoonGeslachtsnaamcomponent() {
        return persoonGeslachtsnaamcomponent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9797)
    public PredicaatAttribuut getPredicaat() {
        return predicaat;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9798)
    public AdellijkeTitelAttribuut getAdellijkeTitel() {
        return adellijkeTitel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9799)
    public VoorvoegselAttribuut getVoorvoegsel() {
        return voorvoegsel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9800)
    public ScheidingstekenAttribuut getScheidingsteken() {
        return scheidingsteken;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9801)
    public GeslachtsnaamstamAttribuut getStam() {
        return stam;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (predicaat != null) {
            attributen.add(predicaat);
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
        if (stam != null) {
            attributen.add(stam);
        }
        return attributen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HisPersoonGeslachtsnaamcomponentModel kopieer() {
        return new HisPersoonGeslachtsnaamcomponentModel(this);
    }

    /**
     * Retourneert het Element behorende bij deze groep.
     *
     * @return Element enum instantie behorende bij deze groep.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD;
    }

}

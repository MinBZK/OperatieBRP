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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdresregelAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AangeverAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingVerblijfAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortMigratieAttribuut;
import nl.bzk.brp.model.annotaties.AttribuutAccessor;
import nl.bzk.brp.model.annotaties.GroepAccessor;
import nl.bzk.brp.model.basis.AbstractMaterieelHistorischMetActieVerantwoording;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.MaterieleHistorie;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.kern.PersoonMigratieGroep;
import nl.bzk.brp.model.logisch.kern.PersoonMigratieGroepBasis;

/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@GroepAccessor(dbObjectId = 3790)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractHisPersoonMigratieModel extends AbstractMaterieelHistorischMetActieVerantwoording implements PersoonMigratieGroepBasis,
        ModelIdentificeerbaar<Integer>, ElementIdentificeerbaar
{

    @Id
    @SequenceGenerator(name = "HIS_PERSOONMIGRATIE", sequenceName = "Kern.seq_His_PersMigratie")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PERSOONMIGRATIE")
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = PersoonHisVolledigImpl.class)
    @JoinColumn(name = "Pers")
    @JsonBackReference
    private PersoonHisVolledig persoon;

    @Embedded
    @AttributeOverride(name = SoortMigratieAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "SrtMigratie"))
    @JsonProperty
    private SoortMigratieAttribuut soortMigratie;

    @Embedded
    @AssociationOverride(name = RedenWijzigingVerblijfAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "RdnWijzMigratie"))
    @JsonProperty
    private RedenWijzigingVerblijfAttribuut redenWijzigingMigratie;

    @Embedded
    @AssociationOverride(name = AangeverAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "AangMigratie"))
    @JsonProperty
    private AangeverAttribuut aangeverMigratie;

    @Embedded
    @AssociationOverride(name = LandGebiedAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "LandGebiedMigratie"))
    @JsonProperty
    private LandGebiedAttribuut landGebiedMigratie;

    @Embedded
    @AttributeOverride(name = AdresregelAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "BLAdresRegel1Migratie"))
    @JsonProperty
    private AdresregelAttribuut buitenlandsAdresRegel1Migratie;

    @Embedded
    @AttributeOverride(name = AdresregelAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "BLAdresRegel2Migratie"))
    @JsonProperty
    private AdresregelAttribuut buitenlandsAdresRegel2Migratie;

    @Embedded
    @AttributeOverride(name = AdresregelAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "BLAdresRegel3Migratie"))
    @JsonProperty
    private AdresregelAttribuut buitenlandsAdresRegel3Migratie;

    @Embedded
    @AttributeOverride(name = AdresregelAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "BLAdresRegel4Migratie"))
    @JsonProperty
    private AdresregelAttribuut buitenlandsAdresRegel4Migratie;

    @Embedded
    @AttributeOverride(name = AdresregelAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "BLAdresRegel5Migratie"))
    @JsonProperty
    private AdresregelAttribuut buitenlandsAdresRegel5Migratie;

    @Embedded
    @AttributeOverride(name = AdresregelAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "BLAdresRegel6Migratie"))
    @JsonProperty
    private AdresregelAttribuut buitenlandsAdresRegel6Migratie;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractHisPersoonMigratieModel() {
    }

    /**
     * Constructor voor het initialiseren van alle attributen. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param persoon persoon van HisPersoonMigratieModel
     * @param soortMigratie soortMigratie van HisPersoonMigratieModel
     * @param redenWijzigingMigratie redenWijzigingMigratie van HisPersoonMigratieModel
     * @param aangeverMigratie aangeverMigratie van HisPersoonMigratieModel
     * @param landGebiedMigratie landGebiedMigratie van HisPersoonMigratieModel
     * @param buitenlandsAdresRegel1Migratie buitenlandsAdresRegel1Migratie van HisPersoonMigratieModel
     * @param buitenlandsAdresRegel2Migratie buitenlandsAdresRegel2Migratie van HisPersoonMigratieModel
     * @param buitenlandsAdresRegel3Migratie buitenlandsAdresRegel3Migratie van HisPersoonMigratieModel
     * @param buitenlandsAdresRegel4Migratie buitenlandsAdresRegel4Migratie van HisPersoonMigratieModel
     * @param buitenlandsAdresRegel5Migratie buitenlandsAdresRegel5Migratie van HisPersoonMigratieModel
     * @param buitenlandsAdresRegel6Migratie buitenlandsAdresRegel6Migratie van HisPersoonMigratieModel
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     * @param historie De groep uit een bericht
     */
    public AbstractHisPersoonMigratieModel(
        final PersoonHisVolledig persoon,
        final SoortMigratieAttribuut soortMigratie,
        final RedenWijzigingVerblijfAttribuut redenWijzigingMigratie,
        final AangeverAttribuut aangeverMigratie,
        final LandGebiedAttribuut landGebiedMigratie,
        final AdresregelAttribuut buitenlandsAdresRegel1Migratie,
        final AdresregelAttribuut buitenlandsAdresRegel2Migratie,
        final AdresregelAttribuut buitenlandsAdresRegel3Migratie,
        final AdresregelAttribuut buitenlandsAdresRegel4Migratie,
        final AdresregelAttribuut buitenlandsAdresRegel5Migratie,
        final AdresregelAttribuut buitenlandsAdresRegel6Migratie,
        final ActieModel actieInhoud,
        final MaterieleHistorie historie)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        this.persoon = persoon;
        this.soortMigratie = soortMigratie;
        this.redenWijzigingMigratie = redenWijzigingMigratie;
        this.aangeverMigratie = aangeverMigratie;
        this.landGebiedMigratie = landGebiedMigratie;
        this.buitenlandsAdresRegel1Migratie = buitenlandsAdresRegel1Migratie;
        this.buitenlandsAdresRegel2Migratie = buitenlandsAdresRegel2Migratie;
        this.buitenlandsAdresRegel3Migratie = buitenlandsAdresRegel3Migratie;
        this.buitenlandsAdresRegel4Migratie = buitenlandsAdresRegel4Migratie;
        this.buitenlandsAdresRegel5Migratie = buitenlandsAdresRegel5Migratie;
        this.buitenlandsAdresRegel6Migratie = buitenlandsAdresRegel6Migratie;
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
    public AbstractHisPersoonMigratieModel(final AbstractHisPersoonMigratieModel kopie) {
        super(kopie);
        persoon = kopie.getPersoon();
        soortMigratie = kopie.getSoortMigratie();
        redenWijzigingMigratie = kopie.getRedenWijzigingMigratie();
        aangeverMigratie = kopie.getAangeverMigratie();
        landGebiedMigratie = kopie.getLandGebiedMigratie();
        buitenlandsAdresRegel1Migratie = kopie.getBuitenlandsAdresRegel1Migratie();
        buitenlandsAdresRegel2Migratie = kopie.getBuitenlandsAdresRegel2Migratie();
        buitenlandsAdresRegel3Migratie = kopie.getBuitenlandsAdresRegel3Migratie();
        buitenlandsAdresRegel4Migratie = kopie.getBuitenlandsAdresRegel4Migratie();
        buitenlandsAdresRegel5Migratie = kopie.getBuitenlandsAdresRegel5Migratie();
        buitenlandsAdresRegel6Migratie = kopie.getBuitenlandsAdresRegel6Migratie();

    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param persoonHisVolledig instantie van A-laag klasse.
     * @param groep groep
     * @param historie historie
     * @param actieInhoud Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public AbstractHisPersoonMigratieModel(
        final PersoonHisVolledig persoonHisVolledig,
        final PersoonMigratieGroep groep,
        final MaterieleHistorie historie,
        final ActieModel actieInhoud)
    {
        this.persoon = persoonHisVolledig;
        this.soortMigratie = groep.getSoortMigratie();
        this.redenWijzigingMigratie = groep.getRedenWijzigingMigratie();
        this.aangeverMigratie = groep.getAangeverMigratie();
        this.landGebiedMigratie = groep.getLandGebiedMigratie();
        this.buitenlandsAdresRegel1Migratie = groep.getBuitenlandsAdresRegel1Migratie();
        this.buitenlandsAdresRegel2Migratie = groep.getBuitenlandsAdresRegel2Migratie();
        this.buitenlandsAdresRegel3Migratie = groep.getBuitenlandsAdresRegel3Migratie();
        this.buitenlandsAdresRegel4Migratie = groep.getBuitenlandsAdresRegel4Migratie();
        this.buitenlandsAdresRegel5Migratie = groep.getBuitenlandsAdresRegel5Migratie();
        this.buitenlandsAdresRegel6Migratie = groep.getBuitenlandsAdresRegel6Migratie();
        getMaterieleHistorie().setDatumAanvangGeldigheid(historie.getDatumAanvangGeldigheid());
        getMaterieleHistorie().setDatumEindeGeldigheid(historie.getDatumEindeGeldigheid());
        setVerantwoordingInhoud(actieInhoud);
        getMaterieleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Retourneert ID van His Persoon Migratie.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van His Persoon Migratie.
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
    @AttribuutAccessor(dbObjectId = 10947)
    public SoortMigratieAttribuut getSoortMigratie() {
        return soortMigratie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 11279)
    public RedenWijzigingVerblijfAttribuut getRedenWijzigingMigratie() {
        return redenWijzigingMigratie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 11280)
    public AangeverAttribuut getAangeverMigratie() {
        return aangeverMigratie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9758)
    public LandGebiedAttribuut getLandGebiedMigratie() {
        return landGebiedMigratie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 10948)
    public AdresregelAttribuut getBuitenlandsAdresRegel1Migratie() {
        return buitenlandsAdresRegel1Migratie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 10949)
    public AdresregelAttribuut getBuitenlandsAdresRegel2Migratie() {
        return buitenlandsAdresRegel2Migratie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 10950)
    public AdresregelAttribuut getBuitenlandsAdresRegel3Migratie() {
        return buitenlandsAdresRegel3Migratie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 10951)
    public AdresregelAttribuut getBuitenlandsAdresRegel4Migratie() {
        return buitenlandsAdresRegel4Migratie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 10952)
    public AdresregelAttribuut getBuitenlandsAdresRegel5Migratie() {
        return buitenlandsAdresRegel5Migratie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 10953)
    public AdresregelAttribuut getBuitenlandsAdresRegel6Migratie() {
        return buitenlandsAdresRegel6Migratie;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (soortMigratie != null) {
            attributen.add(soortMigratie);
        }
        if (redenWijzigingMigratie != null) {
            attributen.add(redenWijzigingMigratie);
        }
        if (aangeverMigratie != null) {
            attributen.add(aangeverMigratie);
        }
        if (landGebiedMigratie != null) {
            attributen.add(landGebiedMigratie);
        }
        if (buitenlandsAdresRegel1Migratie != null) {
            attributen.add(buitenlandsAdresRegel1Migratie);
        }
        if (buitenlandsAdresRegel2Migratie != null) {
            attributen.add(buitenlandsAdresRegel2Migratie);
        }
        if (buitenlandsAdresRegel3Migratie != null) {
            attributen.add(buitenlandsAdresRegel3Migratie);
        }
        if (buitenlandsAdresRegel4Migratie != null) {
            attributen.add(buitenlandsAdresRegel4Migratie);
        }
        if (buitenlandsAdresRegel5Migratie != null) {
            attributen.add(buitenlandsAdresRegel5Migratie);
        }
        if (buitenlandsAdresRegel6Migratie != null) {
            attributen.add(buitenlandsAdresRegel6Migratie);
        }
        return attributen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HisPersoonMigratieModel kopieer() {
        return new HisPersoonMigratieModel(this);
    }

    /**
     * Retourneert het Element behorende bij deze groep.
     *
     * @return Element enum instantie behorende bij deze groep.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PERSOON_MIGRATIE;
    }

}

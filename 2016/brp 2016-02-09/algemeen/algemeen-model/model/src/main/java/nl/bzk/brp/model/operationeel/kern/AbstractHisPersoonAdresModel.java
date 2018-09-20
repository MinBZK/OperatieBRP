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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AfgekorteNaamOpenbareRuimteAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeentedeelAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisletterAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisnummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisnummertoevoegingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.IdentificatiecodeAdresseerbaarObjectAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.IdentificatiecodeNummeraanduidingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieTenOpzichteVanAdresAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieomschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamOpenbareRuimteAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PostcodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AangeverAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdresAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingVerblijfAttribuut;
import nl.bzk.brp.model.annotaties.AttribuutAccessor;
import nl.bzk.brp.model.annotaties.GroepAccessor;
import nl.bzk.brp.model.basis.AbstractMaterieelHistorischMetActieVerantwoording;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.MaterieleHistorie;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonAdresHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonAdresHisVolledig;
import nl.bzk.brp.model.logisch.kern.PersoonAdresStandaardGroep;
import nl.bzk.brp.model.logisch.kern.PersoonAdresStandaardGroepBasis;

/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@GroepAccessor(dbObjectId = 6063)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractHisPersoonAdresModel extends AbstractMaterieelHistorischMetActieVerantwoording implements PersoonAdresStandaardGroepBasis,
        ModelIdentificeerbaar<Integer>, ElementIdentificeerbaar
{

    @Id
    @SequenceGenerator(name = "HIS_PERSOONADRES", sequenceName = "Kern.seq_His_PersAdres")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PERSOONADRES")
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = PersoonAdresHisVolledigImpl.class)
    @JoinColumn(name = "PersAdres")
    @JsonBackReference
    private PersoonAdresHisVolledig persoonAdres;

    @Embedded
    @AttributeOverride(name = FunctieAdresAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Srt"))
    @JsonProperty
    private FunctieAdresAttribuut soort;

    @Embedded
    @AssociationOverride(name = RedenWijzigingVerblijfAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "RdnWijz"))
    @JsonProperty
    private RedenWijzigingVerblijfAttribuut redenWijziging;

    @Embedded
    @AssociationOverride(name = AangeverAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "AangAdresh"))
    @JsonProperty
    private AangeverAttribuut aangeverAdreshouding;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatAanvAdresh"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datumAanvangAdreshouding;

    @Embedded
    @AttributeOverride(name = IdentificatiecodeAdresseerbaarObjectAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IdentcodeAdresseerbaarObject"))
    @JsonProperty
    private IdentificatiecodeAdresseerbaarObjectAttribuut identificatiecodeAdresseerbaarObject;

    @Embedded
    @AttributeOverride(name = IdentificatiecodeNummeraanduidingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IdentcodeNraand"))
    @JsonProperty
    private IdentificatiecodeNummeraanduidingAttribuut identificatiecodeNummeraanduiding;

    @Embedded
    @AssociationOverride(name = GemeenteAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "Gem"))
    @JsonProperty
    private GemeenteAttribuut gemeente;

    @Embedded
    @AttributeOverride(name = NaamOpenbareRuimteAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "NOR"))
    @JsonProperty
    private NaamOpenbareRuimteAttribuut naamOpenbareRuimte;

    @Embedded
    @AttributeOverride(name = AfgekorteNaamOpenbareRuimteAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "AfgekorteNOR"))
    @JsonProperty
    private AfgekorteNaamOpenbareRuimteAttribuut afgekorteNaamOpenbareRuimte;

    @Embedded
    @AttributeOverride(name = GemeentedeelAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Gemdeel"))
    @JsonProperty
    private GemeentedeelAttribuut gemeentedeel;

    @Embedded
    @AttributeOverride(name = HuisnummerAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Huisnr"))
    @JsonProperty
    private HuisnummerAttribuut huisnummer;

    @Embedded
    @AttributeOverride(name = HuisletterAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Huisletter"))
    @JsonProperty
    private HuisletterAttribuut huisletter;

    @Embedded
    @AttributeOverride(name = HuisnummertoevoegingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Huisnrtoevoeging"))
    @JsonProperty
    private HuisnummertoevoegingAttribuut huisnummertoevoeging;

    @Embedded
    @AttributeOverride(name = PostcodeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Postcode"))
    @JsonProperty
    private PostcodeAttribuut postcode;

    @Embedded
    @AttributeOverride(name = NaamEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Wplnaam"))
    @JsonProperty
    private NaamEnumeratiewaardeAttribuut woonplaatsnaam;

    @Embedded
    @AttributeOverride(name = LocatieTenOpzichteVanAdresAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "LocTenOpzichteVanAdres"))
    @JsonProperty
    private LocatieTenOpzichteVanAdresAttribuut locatieTenOpzichteVanAdres;

    @Embedded
    @AttributeOverride(name = LocatieomschrijvingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Locoms"))
    @JsonProperty
    private LocatieomschrijvingAttribuut locatieomschrijving;

    @Embedded
    @AttributeOverride(name = AdresregelAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "BLAdresRegel1"))
    @JsonProperty
    private AdresregelAttribuut buitenlandsAdresRegel1;

    @Embedded
    @AttributeOverride(name = AdresregelAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "BLAdresRegel2"))
    @JsonProperty
    private AdresregelAttribuut buitenlandsAdresRegel2;

    @Embedded
    @AttributeOverride(name = AdresregelAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "BLAdresRegel3"))
    @JsonProperty
    private AdresregelAttribuut buitenlandsAdresRegel3;

    @Embedded
    @AttributeOverride(name = AdresregelAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "BLAdresRegel4"))
    @JsonProperty
    private AdresregelAttribuut buitenlandsAdresRegel4;

    @Embedded
    @AttributeOverride(name = AdresregelAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "BLAdresRegel5"))
    @JsonProperty
    private AdresregelAttribuut buitenlandsAdresRegel5;

    @Embedded
    @AttributeOverride(name = AdresregelAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "BLAdresRegel6"))
    @JsonProperty
    private AdresregelAttribuut buitenlandsAdresRegel6;

    @Embedded
    @AssociationOverride(name = LandGebiedAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "LandGebied"))
    @JsonProperty
    private LandGebiedAttribuut landGebied;

    @Embedded
    @AttributeOverride(name = NeeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndPersAangetroffenOpAdres"))
    @JsonProperty
    private NeeAttribuut indicatiePersoonAangetroffenOpAdres;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractHisPersoonAdresModel() {
    }

    /**
     * Constructor voor het initialiseren van alle attributen. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param persoonAdres persoonAdres van HisPersoonAdresModel
     * @param soort soort van HisPersoonAdresModel
     * @param redenWijziging redenWijziging van HisPersoonAdresModel
     * @param aangeverAdreshouding aangeverAdreshouding van HisPersoonAdresModel
     * @param datumAanvangAdreshouding datumAanvangAdreshouding van HisPersoonAdresModel
     * @param identificatiecodeAdresseerbaarObject identificatiecodeAdresseerbaarObject van HisPersoonAdresModel
     * @param identificatiecodeNummeraanduiding identificatiecodeNummeraanduiding van HisPersoonAdresModel
     * @param gemeente gemeente van HisPersoonAdresModel
     * @param naamOpenbareRuimte naamOpenbareRuimte van HisPersoonAdresModel
     * @param afgekorteNaamOpenbareRuimte afgekorteNaamOpenbareRuimte van HisPersoonAdresModel
     * @param gemeentedeel gemeentedeel van HisPersoonAdresModel
     * @param huisnummer huisnummer van HisPersoonAdresModel
     * @param huisletter huisletter van HisPersoonAdresModel
     * @param huisnummertoevoeging huisnummertoevoeging van HisPersoonAdresModel
     * @param postcode postcode van HisPersoonAdresModel
     * @param woonplaatsnaam woonplaatsnaam van HisPersoonAdresModel
     * @param locatieTenOpzichteVanAdres locatieTenOpzichteVanAdres van HisPersoonAdresModel
     * @param locatieomschrijving locatieomschrijving van HisPersoonAdresModel
     * @param buitenlandsAdresRegel1 buitenlandsAdresRegel1 van HisPersoonAdresModel
     * @param buitenlandsAdresRegel2 buitenlandsAdresRegel2 van HisPersoonAdresModel
     * @param buitenlandsAdresRegel3 buitenlandsAdresRegel3 van HisPersoonAdresModel
     * @param buitenlandsAdresRegel4 buitenlandsAdresRegel4 van HisPersoonAdresModel
     * @param buitenlandsAdresRegel5 buitenlandsAdresRegel5 van HisPersoonAdresModel
     * @param buitenlandsAdresRegel6 buitenlandsAdresRegel6 van HisPersoonAdresModel
     * @param landGebied landGebied van HisPersoonAdresModel
     * @param indicatiePersoonAangetroffenOpAdres indicatiePersoonAangetroffenOpAdres van HisPersoonAdresModel
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     * @param historie De groep uit een bericht
     */
    public AbstractHisPersoonAdresModel(
        final PersoonAdresHisVolledig persoonAdres,
        final FunctieAdresAttribuut soort,
        final RedenWijzigingVerblijfAttribuut redenWijziging,
        final AangeverAttribuut aangeverAdreshouding,
        final DatumEvtDeelsOnbekendAttribuut datumAanvangAdreshouding,
        final IdentificatiecodeAdresseerbaarObjectAttribuut identificatiecodeAdresseerbaarObject,
        final IdentificatiecodeNummeraanduidingAttribuut identificatiecodeNummeraanduiding,
        final GemeenteAttribuut gemeente,
        final NaamOpenbareRuimteAttribuut naamOpenbareRuimte,
        final AfgekorteNaamOpenbareRuimteAttribuut afgekorteNaamOpenbareRuimte,
        final GemeentedeelAttribuut gemeentedeel,
        final HuisnummerAttribuut huisnummer,
        final HuisletterAttribuut huisletter,
        final HuisnummertoevoegingAttribuut huisnummertoevoeging,
        final PostcodeAttribuut postcode,
        final NaamEnumeratiewaardeAttribuut woonplaatsnaam,
        final LocatieTenOpzichteVanAdresAttribuut locatieTenOpzichteVanAdres,
        final LocatieomschrijvingAttribuut locatieomschrijving,
        final AdresregelAttribuut buitenlandsAdresRegel1,
        final AdresregelAttribuut buitenlandsAdresRegel2,
        final AdresregelAttribuut buitenlandsAdresRegel3,
        final AdresregelAttribuut buitenlandsAdresRegel4,
        final AdresregelAttribuut buitenlandsAdresRegel5,
        final AdresregelAttribuut buitenlandsAdresRegel6,
        final LandGebiedAttribuut landGebied,
        final NeeAttribuut indicatiePersoonAangetroffenOpAdres,
        final ActieModel actieInhoud,
        final MaterieleHistorie historie)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        this.persoonAdres = persoonAdres;
        this.soort = soort;
        this.redenWijziging = redenWijziging;
        this.aangeverAdreshouding = aangeverAdreshouding;
        this.datumAanvangAdreshouding = datumAanvangAdreshouding;
        this.identificatiecodeAdresseerbaarObject = identificatiecodeAdresseerbaarObject;
        this.identificatiecodeNummeraanduiding = identificatiecodeNummeraanduiding;
        this.gemeente = gemeente;
        this.naamOpenbareRuimte = naamOpenbareRuimte;
        this.afgekorteNaamOpenbareRuimte = afgekorteNaamOpenbareRuimte;
        this.gemeentedeel = gemeentedeel;
        this.huisnummer = huisnummer;
        this.huisletter = huisletter;
        this.huisnummertoevoeging = huisnummertoevoeging;
        this.postcode = postcode;
        this.woonplaatsnaam = woonplaatsnaam;
        this.locatieTenOpzichteVanAdres = locatieTenOpzichteVanAdres;
        this.locatieomschrijving = locatieomschrijving;
        this.buitenlandsAdresRegel1 = buitenlandsAdresRegel1;
        this.buitenlandsAdresRegel2 = buitenlandsAdresRegel2;
        this.buitenlandsAdresRegel3 = buitenlandsAdresRegel3;
        this.buitenlandsAdresRegel4 = buitenlandsAdresRegel4;
        this.buitenlandsAdresRegel5 = buitenlandsAdresRegel5;
        this.buitenlandsAdresRegel6 = buitenlandsAdresRegel6;
        this.landGebied = landGebied;
        this.indicatiePersoonAangetroffenOpAdres = indicatiePersoonAangetroffenOpAdres;
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
    public AbstractHisPersoonAdresModel(final AbstractHisPersoonAdresModel kopie) {
        super(kopie);
        persoonAdres = kopie.getPersoonAdres();
        soort = kopie.getSoort();
        redenWijziging = kopie.getRedenWijziging();
        aangeverAdreshouding = kopie.getAangeverAdreshouding();
        datumAanvangAdreshouding = kopie.getDatumAanvangAdreshouding();
        identificatiecodeAdresseerbaarObject = kopie.getIdentificatiecodeAdresseerbaarObject();
        identificatiecodeNummeraanduiding = kopie.getIdentificatiecodeNummeraanduiding();
        gemeente = kopie.getGemeente();
        naamOpenbareRuimte = kopie.getNaamOpenbareRuimte();
        afgekorteNaamOpenbareRuimte = kopie.getAfgekorteNaamOpenbareRuimte();
        gemeentedeel = kopie.getGemeentedeel();
        huisnummer = kopie.getHuisnummer();
        huisletter = kopie.getHuisletter();
        huisnummertoevoeging = kopie.getHuisnummertoevoeging();
        postcode = kopie.getPostcode();
        woonplaatsnaam = kopie.getWoonplaatsnaam();
        locatieTenOpzichteVanAdres = kopie.getLocatieTenOpzichteVanAdres();
        locatieomschrijving = kopie.getLocatieomschrijving();
        buitenlandsAdresRegel1 = kopie.getBuitenlandsAdresRegel1();
        buitenlandsAdresRegel2 = kopie.getBuitenlandsAdresRegel2();
        buitenlandsAdresRegel3 = kopie.getBuitenlandsAdresRegel3();
        buitenlandsAdresRegel4 = kopie.getBuitenlandsAdresRegel4();
        buitenlandsAdresRegel5 = kopie.getBuitenlandsAdresRegel5();
        buitenlandsAdresRegel6 = kopie.getBuitenlandsAdresRegel6();
        landGebied = kopie.getLandGebied();
        indicatiePersoonAangetroffenOpAdres = kopie.getIndicatiePersoonAangetroffenOpAdres();

    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param persoonAdresHisVolledig instantie van A-laag klasse.
     * @param groep groep
     * @param historie historie
     * @param actieInhoud Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public AbstractHisPersoonAdresModel(
        final PersoonAdresHisVolledig persoonAdresHisVolledig,
        final PersoonAdresStandaardGroep groep,
        final MaterieleHistorie historie,
        final ActieModel actieInhoud)
    {
        this.persoonAdres = persoonAdresHisVolledig;
        this.soort = groep.getSoort();
        this.redenWijziging = groep.getRedenWijziging();
        this.aangeverAdreshouding = groep.getAangeverAdreshouding();
        this.datumAanvangAdreshouding = groep.getDatumAanvangAdreshouding();
        this.identificatiecodeAdresseerbaarObject = groep.getIdentificatiecodeAdresseerbaarObject();
        this.identificatiecodeNummeraanduiding = groep.getIdentificatiecodeNummeraanduiding();
        this.gemeente = groep.getGemeente();
        this.naamOpenbareRuimte = groep.getNaamOpenbareRuimte();
        this.afgekorteNaamOpenbareRuimte = groep.getAfgekorteNaamOpenbareRuimte();
        this.gemeentedeel = groep.getGemeentedeel();
        this.huisnummer = groep.getHuisnummer();
        this.huisletter = groep.getHuisletter();
        this.huisnummertoevoeging = groep.getHuisnummertoevoeging();
        this.postcode = groep.getPostcode();
        this.woonplaatsnaam = groep.getWoonplaatsnaam();
        this.locatieTenOpzichteVanAdres = groep.getLocatieTenOpzichteVanAdres();
        this.locatieomschrijving = groep.getLocatieomschrijving();
        this.buitenlandsAdresRegel1 = groep.getBuitenlandsAdresRegel1();
        this.buitenlandsAdresRegel2 = groep.getBuitenlandsAdresRegel2();
        this.buitenlandsAdresRegel3 = groep.getBuitenlandsAdresRegel3();
        this.buitenlandsAdresRegel4 = groep.getBuitenlandsAdresRegel4();
        this.buitenlandsAdresRegel5 = groep.getBuitenlandsAdresRegel5();
        this.buitenlandsAdresRegel6 = groep.getBuitenlandsAdresRegel6();
        this.landGebied = groep.getLandGebied();
        this.indicatiePersoonAangetroffenOpAdres = groep.getIndicatiePersoonAangetroffenOpAdres();
        getMaterieleHistorie().setDatumAanvangGeldigheid(historie.getDatumAanvangGeldigheid());
        getMaterieleHistorie().setDatumEindeGeldigheid(historie.getDatumEindeGeldigheid());
        setVerantwoordingInhoud(actieInhoud);
        getMaterieleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Retourneert ID van His Persoon \ Adres.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon \ Adres van His Persoon \ Adres.
     *
     * @return Persoon \ Adres.
     */
    public PersoonAdresHisVolledig getPersoonAdres() {
        return persoonAdres;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9771)
    public FunctieAdresAttribuut getSoort() {
        return soort;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9772)
    public RedenWijzigingVerblijfAttribuut getRedenWijziging() {
        return redenWijziging;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9773)
    public AangeverAttribuut getAangeverAdreshouding() {
        return aangeverAdreshouding;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9774)
    public DatumEvtDeelsOnbekendAttribuut getDatumAanvangAdreshouding() {
        return datumAanvangAdreshouding;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9775)
    public IdentificatiecodeAdresseerbaarObjectAttribuut getIdentificatiecodeAdresseerbaarObject() {
        return identificatiecodeAdresseerbaarObject;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9776)
    public IdentificatiecodeNummeraanduidingAttribuut getIdentificatiecodeNummeraanduiding() {
        return identificatiecodeNummeraanduiding;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9777)
    public GemeenteAttribuut getGemeente() {
        return gemeente;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9778)
    public NaamOpenbareRuimteAttribuut getNaamOpenbareRuimte() {
        return naamOpenbareRuimte;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9779)
    public AfgekorteNaamOpenbareRuimteAttribuut getAfgekorteNaamOpenbareRuimte() {
        return afgekorteNaamOpenbareRuimte;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9780)
    public GemeentedeelAttribuut getGemeentedeel() {
        return gemeentedeel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9781)
    public HuisnummerAttribuut getHuisnummer() {
        return huisnummer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9782)
    public HuisletterAttribuut getHuisletter() {
        return huisletter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9783)
    public HuisnummertoevoegingAttribuut getHuisnummertoevoeging() {
        return huisnummertoevoeging;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9784)
    public PostcodeAttribuut getPostcode() {
        return postcode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9785)
    public NaamEnumeratiewaardeAttribuut getWoonplaatsnaam() {
        return woonplaatsnaam;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9786)
    public LocatieTenOpzichteVanAdresAttribuut getLocatieTenOpzichteVanAdres() {
        return locatieTenOpzichteVanAdres;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9787)
    public LocatieomschrijvingAttribuut getLocatieomschrijving() {
        return locatieomschrijving;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9789)
    public AdresregelAttribuut getBuitenlandsAdresRegel1() {
        return buitenlandsAdresRegel1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9790)
    public AdresregelAttribuut getBuitenlandsAdresRegel2() {
        return buitenlandsAdresRegel2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9791)
    public AdresregelAttribuut getBuitenlandsAdresRegel3() {
        return buitenlandsAdresRegel3;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9792)
    public AdresregelAttribuut getBuitenlandsAdresRegel4() {
        return buitenlandsAdresRegel4;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9793)
    public AdresregelAttribuut getBuitenlandsAdresRegel5() {
        return buitenlandsAdresRegel5;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9794)
    public AdresregelAttribuut getBuitenlandsAdresRegel6() {
        return buitenlandsAdresRegel6;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9795)
    public LandGebiedAttribuut getLandGebied() {
        return landGebied;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9796)
    public NeeAttribuut getIndicatiePersoonAangetroffenOpAdres() {
        return indicatiePersoonAangetroffenOpAdres;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (soort != null) {
            attributen.add(soort);
        }
        if (redenWijziging != null) {
            attributen.add(redenWijziging);
        }
        if (aangeverAdreshouding != null) {
            attributen.add(aangeverAdreshouding);
        }
        if (datumAanvangAdreshouding != null) {
            attributen.add(datumAanvangAdreshouding);
        }
        if (identificatiecodeAdresseerbaarObject != null) {
            attributen.add(identificatiecodeAdresseerbaarObject);
        }
        if (identificatiecodeNummeraanduiding != null) {
            attributen.add(identificatiecodeNummeraanduiding);
        }
        if (gemeente != null) {
            attributen.add(gemeente);
        }
        if (naamOpenbareRuimte != null) {
            attributen.add(naamOpenbareRuimte);
        }
        if (afgekorteNaamOpenbareRuimte != null) {
            attributen.add(afgekorteNaamOpenbareRuimte);
        }
        if (gemeentedeel != null) {
            attributen.add(gemeentedeel);
        }
        if (huisnummer != null) {
            attributen.add(huisnummer);
        }
        if (huisletter != null) {
            attributen.add(huisletter);
        }
        if (huisnummertoevoeging != null) {
            attributen.add(huisnummertoevoeging);
        }
        if (postcode != null) {
            attributen.add(postcode);
        }
        if (woonplaatsnaam != null) {
            attributen.add(woonplaatsnaam);
        }
        if (locatieTenOpzichteVanAdres != null) {
            attributen.add(locatieTenOpzichteVanAdres);
        }
        if (locatieomschrijving != null) {
            attributen.add(locatieomschrijving);
        }
        if (buitenlandsAdresRegel1 != null) {
            attributen.add(buitenlandsAdresRegel1);
        }
        if (buitenlandsAdresRegel2 != null) {
            attributen.add(buitenlandsAdresRegel2);
        }
        if (buitenlandsAdresRegel3 != null) {
            attributen.add(buitenlandsAdresRegel3);
        }
        if (buitenlandsAdresRegel4 != null) {
            attributen.add(buitenlandsAdresRegel4);
        }
        if (buitenlandsAdresRegel5 != null) {
            attributen.add(buitenlandsAdresRegel5);
        }
        if (buitenlandsAdresRegel6 != null) {
            attributen.add(buitenlandsAdresRegel6);
        }
        if (landGebied != null) {
            attributen.add(landGebied);
        }
        if (indicatiePersoonAangetroffenOpAdres != null) {
            attributen.add(indicatiePersoonAangetroffenOpAdres);
        }
        return attributen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HisPersoonAdresModel kopieer() {
        return new HisPersoonAdresModel(this);
    }

    /**
     * Retourneert het Element behorende bij deze groep.
     *
     * @return Element enum instantie behorende bij deze groep.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PERSOON_ADRES_STANDAARD;
    }

}

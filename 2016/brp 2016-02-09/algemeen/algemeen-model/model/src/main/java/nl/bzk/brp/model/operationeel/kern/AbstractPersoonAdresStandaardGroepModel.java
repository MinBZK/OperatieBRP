/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.AssociationOverride;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdresAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingVerblijfAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.logisch.kern.PersoonAdresStandaardGroep;
import nl.bzk.brp.model.logisch.kern.PersoonAdresStandaardGroepBasis;

/**
 * Voor de modellering van buitenlands adres waren enkele opties: - Adres in een attribuut met 'regelovergang' tekens
 * Nadeel: Regelovergangtekens zijn niet platformonafhankelijk en het maximale aantal regels is niet goed af te dwingen.
 * - Adres uitsplitsen volgens een of andere norm (wordt naar gezocht) RNI heeft een actie gestart om te kijken of
 * binnen Europa een werkbare standaard te vinden is. Wereldwijd gaat niet lukken. (Voorlopig) nog geen optie. - Adres
 * per regel opnemen. - Adresregels in een aparte tabel. Is ook mogelijk mits aantal regels beperkt wordt. Uiteindelijk
 * is gekozen voor opname per regel. Dat lijkt minder flexibel dan een vrij veld waarin meerdere regels geplaatst kunnen
 * worden. Het geeft de afnemer echter wel duidelijkheid over het maximale aantal regels en het maximale aantal
 * karakters per regel dat deze kan verwachten. Het aantal zes is afkomstig uit onderzoek door de RNI inzake de maximale
 * grootte van internationale adressen.
 *
 * 2. De groep standaard is NIET verplicht: in geval van VOW wordt een actueel adres beëindigd, en is er dus géén sprake
 * van een 'actueel' record in de C/D laag.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractPersoonAdresStandaardGroepModel implements PersoonAdresStandaardGroepBasis {

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
    protected AbstractPersoonAdresStandaardGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param soort soort van Standaard.
     * @param redenWijziging redenWijziging van Standaard.
     * @param aangeverAdreshouding aangeverAdreshouding van Standaard.
     * @param datumAanvangAdreshouding datumAanvangAdreshouding van Standaard.
     * @param identificatiecodeAdresseerbaarObject identificatiecodeAdresseerbaarObject van Standaard.
     * @param identificatiecodeNummeraanduiding identificatiecodeNummeraanduiding van Standaard.
     * @param gemeente gemeente van Standaard.
     * @param naamOpenbareRuimte naamOpenbareRuimte van Standaard.
     * @param afgekorteNaamOpenbareRuimte afgekorteNaamOpenbareRuimte van Standaard.
     * @param gemeentedeel gemeentedeel van Standaard.
     * @param huisnummer huisnummer van Standaard.
     * @param huisletter huisletter van Standaard.
     * @param huisnummertoevoeging huisnummertoevoeging van Standaard.
     * @param postcode postcode van Standaard.
     * @param woonplaatsnaam woonplaatsnaam van Standaard.
     * @param locatieTenOpzichteVanAdres locatieTenOpzichteVanAdres van Standaard.
     * @param locatieomschrijving locatieomschrijving van Standaard.
     * @param buitenlandsAdresRegel1 buitenlandsAdresRegel1 van Standaard.
     * @param buitenlandsAdresRegel2 buitenlandsAdresRegel2 van Standaard.
     * @param buitenlandsAdresRegel3 buitenlandsAdresRegel3 van Standaard.
     * @param buitenlandsAdresRegel4 buitenlandsAdresRegel4 van Standaard.
     * @param buitenlandsAdresRegel5 buitenlandsAdresRegel5 van Standaard.
     * @param buitenlandsAdresRegel6 buitenlandsAdresRegel6 van Standaard.
     * @param landGebied landGebied van Standaard.
     * @param indicatiePersoonAangetroffenOpAdres indicatiePersoonAangetroffenOpAdres van Standaard.
     */
    public AbstractPersoonAdresStandaardGroepModel(
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
        final NeeAttribuut indicatiePersoonAangetroffenOpAdres)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
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

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonAdresStandaardGroep te kopieren groep.
     */
    public AbstractPersoonAdresStandaardGroepModel(final PersoonAdresStandaardGroep persoonAdresStandaardGroep) {
        this.soort = persoonAdresStandaardGroep.getSoort();
        this.redenWijziging = persoonAdresStandaardGroep.getRedenWijziging();
        this.aangeverAdreshouding = persoonAdresStandaardGroep.getAangeverAdreshouding();
        this.datumAanvangAdreshouding = persoonAdresStandaardGroep.getDatumAanvangAdreshouding();
        this.identificatiecodeAdresseerbaarObject = persoonAdresStandaardGroep.getIdentificatiecodeAdresseerbaarObject();
        this.identificatiecodeNummeraanduiding = persoonAdresStandaardGroep.getIdentificatiecodeNummeraanduiding();
        this.gemeente = persoonAdresStandaardGroep.getGemeente();
        this.naamOpenbareRuimte = persoonAdresStandaardGroep.getNaamOpenbareRuimte();
        this.afgekorteNaamOpenbareRuimte = persoonAdresStandaardGroep.getAfgekorteNaamOpenbareRuimte();
        this.gemeentedeel = persoonAdresStandaardGroep.getGemeentedeel();
        this.huisnummer = persoonAdresStandaardGroep.getHuisnummer();
        this.huisletter = persoonAdresStandaardGroep.getHuisletter();
        this.huisnummertoevoeging = persoonAdresStandaardGroep.getHuisnummertoevoeging();
        this.postcode = persoonAdresStandaardGroep.getPostcode();
        this.woonplaatsnaam = persoonAdresStandaardGroep.getWoonplaatsnaam();
        this.locatieTenOpzichteVanAdres = persoonAdresStandaardGroep.getLocatieTenOpzichteVanAdres();
        this.locatieomschrijving = persoonAdresStandaardGroep.getLocatieomschrijving();
        this.buitenlandsAdresRegel1 = persoonAdresStandaardGroep.getBuitenlandsAdresRegel1();
        this.buitenlandsAdresRegel2 = persoonAdresStandaardGroep.getBuitenlandsAdresRegel2();
        this.buitenlandsAdresRegel3 = persoonAdresStandaardGroep.getBuitenlandsAdresRegel3();
        this.buitenlandsAdresRegel4 = persoonAdresStandaardGroep.getBuitenlandsAdresRegel4();
        this.buitenlandsAdresRegel5 = persoonAdresStandaardGroep.getBuitenlandsAdresRegel5();
        this.buitenlandsAdresRegel6 = persoonAdresStandaardGroep.getBuitenlandsAdresRegel6();
        this.landGebied = persoonAdresStandaardGroep.getLandGebied();
        this.indicatiePersoonAangetroffenOpAdres = persoonAdresStandaardGroep.getIndicatiePersoonAangetroffenOpAdres();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FunctieAdresAttribuut getSoort() {
        return soort;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedenWijzigingVerblijfAttribuut getRedenWijziging() {
        return redenWijziging;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AangeverAttribuut getAangeverAdreshouding() {
        return aangeverAdreshouding;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumAanvangAdreshouding() {
        return datumAanvangAdreshouding;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IdentificatiecodeAdresseerbaarObjectAttribuut getIdentificatiecodeAdresseerbaarObject() {
        return identificatiecodeAdresseerbaarObject;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IdentificatiecodeNummeraanduidingAttribuut getIdentificatiecodeNummeraanduiding() {
        return identificatiecodeNummeraanduiding;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GemeenteAttribuut getGemeente() {
        return gemeente;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NaamOpenbareRuimteAttribuut getNaamOpenbareRuimte() {
        return naamOpenbareRuimte;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AfgekorteNaamOpenbareRuimteAttribuut getAfgekorteNaamOpenbareRuimte() {
        return afgekorteNaamOpenbareRuimte;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GemeentedeelAttribuut getGemeentedeel() {
        return gemeentedeel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HuisnummerAttribuut getHuisnummer() {
        return huisnummer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HuisletterAttribuut getHuisletter() {
        return huisletter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HuisnummertoevoegingAttribuut getHuisnummertoevoeging() {
        return huisnummertoevoeging;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PostcodeAttribuut getPostcode() {
        return postcode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NaamEnumeratiewaardeAttribuut getWoonplaatsnaam() {
        return woonplaatsnaam;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocatieTenOpzichteVanAdresAttribuut getLocatieTenOpzichteVanAdres() {
        return locatieTenOpzichteVanAdres;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocatieomschrijvingAttribuut getLocatieomschrijving() {
        return locatieomschrijving;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdresregelAttribuut getBuitenlandsAdresRegel1() {
        return buitenlandsAdresRegel1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdresregelAttribuut getBuitenlandsAdresRegel2() {
        return buitenlandsAdresRegel2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdresregelAttribuut getBuitenlandsAdresRegel3() {
        return buitenlandsAdresRegel3;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdresregelAttribuut getBuitenlandsAdresRegel4() {
        return buitenlandsAdresRegel4;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdresregelAttribuut getBuitenlandsAdresRegel5() {
        return buitenlandsAdresRegel5;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdresregelAttribuut getBuitenlandsAdresRegel6() {
        return buitenlandsAdresRegel6;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LandGebiedAttribuut getLandGebied() {
        return landGebied;
    }

    /**
     * {@inheritDoc}
     */
    @Override
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

}

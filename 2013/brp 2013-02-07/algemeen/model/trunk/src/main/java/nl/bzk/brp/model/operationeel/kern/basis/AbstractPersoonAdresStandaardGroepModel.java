/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.AanduidingAdresseerbaarObject;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AanduidingBijHuisnummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Adresregel;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AfgekorteNaamOpenbareRuimte;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Gemeentedeel;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Huisletter;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Huisnummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Huisnummertoevoeging;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.IdentificatiecodeNummeraanduiding;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieOmschrijving;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamOpenbareRuimte;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Postcode;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AangeverAdreshouding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdres;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Land;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Plaats;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingAdres;
import nl.bzk.brp.model.logisch.kern.PersoonAdresStandaardGroep;
import nl.bzk.brp.model.logisch.kern.basis.PersoonAdresStandaardGroepBasis;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;


/**
 * Voor de modellering van buitenlands adres waren enkele opties:
 * - Adres in een attribuut met 'regelovergang' tekens
 * Nadeel:
 * Regelovergangtekens zijn niet platformonafhankelijk en het maximale aantal regels is niet goed af te dwingen.
 * - Adres uitsplitsen volgens een of andere norm (wordt naar gezocht)
 * RNI heeft een actie gestart om te kijken of binnen Europa een werkbare standaard te vinden is. Wereldwijd gaat niet
 * lukken. (Voorlopig) nog geen optie.
 * - Adres per regel opnemen.
 * - Adresregels in een aparte tabel.
 * Is ook mogelijk mits aantal regels beperkt wordt.
 * Uiteindelijk is gekozen voor opname per regel. Dat lijkt minder flexibel dan een vrij veld waarin meerdere regels
 * geplaatst kunnen worden. Het geeft de afnemer echter wel duidelijkheid over het maximale aantal regels en het
 * maximale aantal karakters per regel dat deze kan verwachten. Het aantal zes is afkomstig uit onderzoek door de RNI
 * inzake de maximale grootte van internationale adressen.
 * RvdP 5 september 2011, verplaatst naar nieuwe groep standaard op 13 jan 2012.
 *
 *
 *
 */
@MappedSuperclass
public abstract class AbstractPersoonAdresStandaardGroepModel implements PersoonAdresStandaardGroepBasis {

    @Enumerated
    @Column(name = "Srt")
    @JsonProperty
    private FunctieAdres                      soort;

    @ManyToOne
    @JoinColumn(name = "RdnWijz")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private RedenWijzigingAdres               redenWijziging;

    @ManyToOne
    @JoinColumn(name = "AangAdresh")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private AangeverAdreshouding              aangeverAdreshouding;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatAanvAdresh"))
    @JsonProperty
    private Datum                             datumAanvangAdreshouding;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "AdresseerbaarObject"))
    @JsonProperty
    private AanduidingAdresseerbaarObject     adresseerbaarObject;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "IdentcodeNraand"))
    @JsonProperty
    private IdentificatiecodeNummeraanduiding identificatiecodeNummeraanduiding;

    @ManyToOne
    @JoinColumn(name = "Gem")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Partij                            gemeente;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "NOR"))
    @JsonProperty
    private NaamOpenbareRuimte                naamOpenbareRuimte;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "AfgekorteNOR"))
    @JsonProperty
    private AfgekorteNaamOpenbareRuimte       afgekorteNaamOpenbareRuimte;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Gemdeel"))
    @JsonProperty
    private Gemeentedeel                      gemeentedeel;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Huisnr"))
    @JsonProperty
    private Huisnummer                        huisnummer;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Huisletter"))
    @JsonProperty
    private Huisletter                        huisletter;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Huisnrtoevoeging"))
    @JsonProperty
    private Huisnummertoevoeging              huisnummertoevoeging;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Postcode"))
    @JsonProperty
    private Postcode                          postcode;

    @ManyToOne
    @JoinColumn(name = "Wpl")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Plaats                            woonplaats;

    @Type(type = "AanduidingBijHuisnummer")
    @Column(name = "LoctovAdres")
    @JsonProperty
    private AanduidingBijHuisnummer           locatietovAdres;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "LocOms"))
    @JsonProperty
    private LocatieOmschrijving               locatieOmschrijving;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatVertrekUitNederland"))
    @JsonProperty
    private Datum                             datumVertrekUitNederland;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "BLAdresRegel1"))
    @JsonProperty
    private Adresregel                        buitenlandsAdresRegel1;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "BLAdresRegel2"))
    @JsonProperty
    private Adresregel                        buitenlandsAdresRegel2;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "BLAdresRegel3"))
    @JsonProperty
    private Adresregel                        buitenlandsAdresRegel3;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "BLAdresRegel4"))
    @JsonProperty
    private Adresregel                        buitenlandsAdresRegel4;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "BLAdresRegel5"))
    @JsonProperty
    private Adresregel                        buitenlandsAdresRegel5;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "BLAdresRegel6"))
    @JsonProperty
    private Adresregel                        buitenlandsAdresRegel6;

    @ManyToOne
    @JoinColumn(name = "Land")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Land                              land;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "IndPersNietAangetroffenOpAdr"))
    @JsonProperty
    private JaNee                             indicatiePersoonNietAangetroffenOpAdres;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractPersoonAdresStandaardGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     * CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param soort soort van Standaard.
     * @param redenWijziging redenWijziging van Standaard.
     * @param aangeverAdreshouding aangeverAdreshouding van Standaard.
     * @param datumAanvangAdreshouding datumAanvangAdreshouding van Standaard.
     * @param adresseerbaarObject adresseerbaarObject van Standaard.
     * @param identificatiecodeNummeraanduiding identificatiecodeNummeraanduiding van Standaard.
     * @param gemeente gemeente van Standaard.
     * @param naamOpenbareRuimte naamOpenbareRuimte van Standaard.
     * @param afgekorteNaamOpenbareRuimte afgekorteNaamOpenbareRuimte van Standaard.
     * @param gemeentedeel gemeentedeel van Standaard.
     * @param huisnummer huisnummer van Standaard.
     * @param huisletter huisletter van Standaard.
     * @param huisnummertoevoeging huisnummertoevoeging van Standaard.
     * @param postcode postcode van Standaard.
     * @param woonplaats woonplaats van Standaard.
     * @param locatietovAdres locatietovAdres van Standaard.
     * @param locatieOmschrijving locatieOmschrijving van Standaard.
     * @param datumVertrekUitNederland datumVertrekUitNederland van Standaard.
     * @param buitenlandsAdresRegel1 buitenlandsAdresRegel1 van Standaard.
     * @param buitenlandsAdresRegel2 buitenlandsAdresRegel2 van Standaard.
     * @param buitenlandsAdresRegel3 buitenlandsAdresRegel3 van Standaard.
     * @param buitenlandsAdresRegel4 buitenlandsAdresRegel4 van Standaard.
     * @param buitenlandsAdresRegel5 buitenlandsAdresRegel5 van Standaard.
     * @param buitenlandsAdresRegel6 buitenlandsAdresRegel6 van Standaard.
     * @param land land van Standaard.
     * @param indicatiePersoonNietAangetroffenOpAdres indicatiePersoonNietAangetroffenOpAdres van Standaard.
     */
    public AbstractPersoonAdresStandaardGroepModel(final FunctieAdres soort, final RedenWijzigingAdres redenWijziging,
            final AangeverAdreshouding aangeverAdreshouding, final Datum datumAanvangAdreshouding,
            final AanduidingAdresseerbaarObject adresseerbaarObject,
            final IdentificatiecodeNummeraanduiding identificatiecodeNummeraanduiding, final Partij gemeente,
            final NaamOpenbareRuimte naamOpenbareRuimte, final AfgekorteNaamOpenbareRuimte afgekorteNaamOpenbareRuimte,
            final Gemeentedeel gemeentedeel, final Huisnummer huisnummer, final Huisletter huisletter,
            final Huisnummertoevoeging huisnummertoevoeging, final Postcode postcode, final Plaats woonplaats,
            final AanduidingBijHuisnummer locatietovAdres, final LocatieOmschrijving locatieOmschrijving,
            final Datum datumVertrekUitNederland, final Adresregel buitenlandsAdresRegel1,
            final Adresregel buitenlandsAdresRegel2, final Adresregel buitenlandsAdresRegel3,
            final Adresregel buitenlandsAdresRegel4, final Adresregel buitenlandsAdresRegel5,
            final Adresregel buitenlandsAdresRegel6, final Land land,
            final JaNee indicatiePersoonNietAangetroffenOpAdres)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        this.soort = soort;
        this.redenWijziging = redenWijziging;
        this.aangeverAdreshouding = aangeverAdreshouding;
        this.datumAanvangAdreshouding = datumAanvangAdreshouding;
        this.adresseerbaarObject = adresseerbaarObject;
        this.identificatiecodeNummeraanduiding = identificatiecodeNummeraanduiding;
        this.gemeente = gemeente;
        this.naamOpenbareRuimte = naamOpenbareRuimte;
        this.afgekorteNaamOpenbareRuimte = afgekorteNaamOpenbareRuimte;
        this.gemeentedeel = gemeentedeel;
        this.huisnummer = huisnummer;
        this.huisletter = huisletter;
        this.huisnummertoevoeging = huisnummertoevoeging;
        this.postcode = postcode;
        this.woonplaats = woonplaats;
        this.locatietovAdres = locatietovAdres;
        this.locatieOmschrijving = locatieOmschrijving;
        this.datumVertrekUitNederland = datumVertrekUitNederland;
        this.buitenlandsAdresRegel1 = buitenlandsAdresRegel1;
        this.buitenlandsAdresRegel2 = buitenlandsAdresRegel2;
        this.buitenlandsAdresRegel3 = buitenlandsAdresRegel3;
        this.buitenlandsAdresRegel4 = buitenlandsAdresRegel4;
        this.buitenlandsAdresRegel5 = buitenlandsAdresRegel5;
        this.buitenlandsAdresRegel6 = buitenlandsAdresRegel6;
        this.land = land;
        this.indicatiePersoonNietAangetroffenOpAdres = indicatiePersoonNietAangetroffenOpAdres;

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
        this.adresseerbaarObject = persoonAdresStandaardGroep.getAdresseerbaarObject();
        this.identificatiecodeNummeraanduiding = persoonAdresStandaardGroep.getIdentificatiecodeNummeraanduiding();
        this.gemeente = persoonAdresStandaardGroep.getGemeente();
        this.naamOpenbareRuimte = persoonAdresStandaardGroep.getNaamOpenbareRuimte();
        this.afgekorteNaamOpenbareRuimte = persoonAdresStandaardGroep.getAfgekorteNaamOpenbareRuimte();
        this.gemeentedeel = persoonAdresStandaardGroep.getGemeentedeel();
        this.huisnummer = persoonAdresStandaardGroep.getHuisnummer();
        this.huisletter = persoonAdresStandaardGroep.getHuisletter();
        this.huisnummertoevoeging = persoonAdresStandaardGroep.getHuisnummertoevoeging();
        this.postcode = persoonAdresStandaardGroep.getPostcode();
        this.woonplaats = persoonAdresStandaardGroep.getWoonplaats();
        this.locatietovAdres = persoonAdresStandaardGroep.getLocatietovAdres();
        this.locatieOmschrijving = persoonAdresStandaardGroep.getLocatieOmschrijving();
        this.datumVertrekUitNederland = persoonAdresStandaardGroep.getDatumVertrekUitNederland();
        this.buitenlandsAdresRegel1 = persoonAdresStandaardGroep.getBuitenlandsAdresRegel1();
        this.buitenlandsAdresRegel2 = persoonAdresStandaardGroep.getBuitenlandsAdresRegel2();
        this.buitenlandsAdresRegel3 = persoonAdresStandaardGroep.getBuitenlandsAdresRegel3();
        this.buitenlandsAdresRegel4 = persoonAdresStandaardGroep.getBuitenlandsAdresRegel4();
        this.buitenlandsAdresRegel5 = persoonAdresStandaardGroep.getBuitenlandsAdresRegel5();
        this.buitenlandsAdresRegel6 = persoonAdresStandaardGroep.getBuitenlandsAdresRegel6();
        this.land = persoonAdresStandaardGroep.getLand();
        this.indicatiePersoonNietAangetroffenOpAdres =
            persoonAdresStandaardGroep.getIndicatiePersoonNietAangetroffenOpAdres();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FunctieAdres getSoort() {
        return soort;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedenWijzigingAdres getRedenWijziging() {
        return redenWijziging;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AangeverAdreshouding getAangeverAdreshouding() {
        return aangeverAdreshouding;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Datum getDatumAanvangAdreshouding() {
        return datumAanvangAdreshouding;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AanduidingAdresseerbaarObject getAdresseerbaarObject() {
        return adresseerbaarObject;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IdentificatiecodeNummeraanduiding getIdentificatiecodeNummeraanduiding() {
        return identificatiecodeNummeraanduiding;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Partij getGemeente() {
        return gemeente;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NaamOpenbareRuimte getNaamOpenbareRuimte() {
        return naamOpenbareRuimte;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AfgekorteNaamOpenbareRuimte getAfgekorteNaamOpenbareRuimte() {
        return afgekorteNaamOpenbareRuimte;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Gemeentedeel getGemeentedeel() {
        return gemeentedeel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Huisnummer getHuisnummer() {
        return huisnummer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Huisletter getHuisletter() {
        return huisletter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Huisnummertoevoeging getHuisnummertoevoeging() {
        return huisnummertoevoeging;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Postcode getPostcode() {
        return postcode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Plaats getWoonplaats() {
        return woonplaats;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AanduidingBijHuisnummer getLocatietovAdres() {
        return locatietovAdres;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocatieOmschrijving getLocatieOmschrijving() {
        return locatieOmschrijving;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Datum getDatumVertrekUitNederland() {
        return datumVertrekUitNederland;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Adresregel getBuitenlandsAdresRegel1() {
        return buitenlandsAdresRegel1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Adresregel getBuitenlandsAdresRegel2() {
        return buitenlandsAdresRegel2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Adresregel getBuitenlandsAdresRegel3() {
        return buitenlandsAdresRegel3;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Adresregel getBuitenlandsAdresRegel4() {
        return buitenlandsAdresRegel4;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Adresregel getBuitenlandsAdresRegel5() {
        return buitenlandsAdresRegel5;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Adresregel getBuitenlandsAdresRegel6() {
        return buitenlandsAdresRegel6;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Land getLand() {
        return land;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JaNee getIndicatiePersoonNietAangetroffenOpAdres() {
        return indicatiePersoonNietAangetroffenOpAdres;
    }

}

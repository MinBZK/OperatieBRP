/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

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
import nl.bzk.brp.model.basis.AbstractMaterieleHistorieEntiteit;
import nl.bzk.brp.model.logisch.kern.basis.PersoonAdresStandaardGroepBasis;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAdresModel;
import nl.bzk.brp.model.operationeel.kern.PersoonAdresModel;
import nl.bzk.brp.model.operationeel.kern.PersoonAdresStandaardGroepModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;


/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractHisPersoonAdresModel extends AbstractMaterieleHistorieEntiteit implements
        PersoonAdresStandaardGroepBasis
{

    @Id
    @SequenceGenerator(name = "HIS_PERSOONADRES", sequenceName = "Kern.seq_His_PersAdres")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PERSOONADRES")
    @JsonProperty
    private Integer                           iD;

    @ManyToOne
    @JoinColumn(name = "PersAdres")
    private PersoonAdresModel                 persoonAdres;

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
     * Default constructor t.b.v. JPA
     *
     */
    protected AbstractHisPersoonAdresModel() {
    }

    /**
     * Copy Constructor die op basis van een A-laag klasse en groep een C/D laag klasse construeert.
     *
     * @param persoonAdresModel instantie van A-laag klasse.
     * @param groep Groep uit de A Laag.
     */
    public AbstractHisPersoonAdresModel(final PersoonAdresModel persoonAdresModel,
            final PersoonAdresStandaardGroepModel groep)
    {
        this.persoonAdres = persoonAdresModel;
        this.soort = groep.getSoort();
        this.redenWijziging = groep.getRedenWijziging();
        this.aangeverAdreshouding = groep.getAangeverAdreshouding();
        this.datumAanvangAdreshouding = groep.getDatumAanvangAdreshouding();
        this.adresseerbaarObject = groep.getAdresseerbaarObject();
        this.identificatiecodeNummeraanduiding = groep.getIdentificatiecodeNummeraanduiding();
        this.gemeente = groep.getGemeente();
        this.naamOpenbareRuimte = groep.getNaamOpenbareRuimte();
        this.afgekorteNaamOpenbareRuimte = groep.getAfgekorteNaamOpenbareRuimte();
        this.gemeentedeel = groep.getGemeentedeel();
        this.huisnummer = groep.getHuisnummer();
        this.huisletter = groep.getHuisletter();
        this.huisnummertoevoeging = groep.getHuisnummertoevoeging();
        this.postcode = groep.getPostcode();
        this.woonplaats = groep.getWoonplaats();
        this.locatietovAdres = groep.getLocatietovAdres();
        this.locatieOmschrijving = groep.getLocatieOmschrijving();
        this.datumVertrekUitNederland = groep.getDatumVertrekUitNederland();
        this.buitenlandsAdresRegel1 = groep.getBuitenlandsAdresRegel1();
        this.buitenlandsAdresRegel2 = groep.getBuitenlandsAdresRegel2();
        this.buitenlandsAdresRegel3 = groep.getBuitenlandsAdresRegel3();
        this.buitenlandsAdresRegel4 = groep.getBuitenlandsAdresRegel4();
        this.buitenlandsAdresRegel5 = groep.getBuitenlandsAdresRegel5();
        this.buitenlandsAdresRegel6 = groep.getBuitenlandsAdresRegel6();
        this.land = groep.getLand();
        this.indicatiePersoonNietAangetroffenOpAdres = groep.getIndicatiePersoonNietAangetroffenOpAdres();

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie instantie van A-laag klasse.
     */
    public AbstractHisPersoonAdresModel(final AbstractHisPersoonAdresModel kopie) {
        super(kopie);
        persoonAdres = kopie.getPersoonAdres();
        soort = kopie.getSoort();
        redenWijziging = kopie.getRedenWijziging();
        aangeverAdreshouding = kopie.getAangeverAdreshouding();
        datumAanvangAdreshouding = kopie.getDatumAanvangAdreshouding();
        adresseerbaarObject = kopie.getAdresseerbaarObject();
        identificatiecodeNummeraanduiding = kopie.getIdentificatiecodeNummeraanduiding();
        gemeente = kopie.getGemeente();
        naamOpenbareRuimte = kopie.getNaamOpenbareRuimte();
        afgekorteNaamOpenbareRuimte = kopie.getAfgekorteNaamOpenbareRuimte();
        gemeentedeel = kopie.getGemeentedeel();
        huisnummer = kopie.getHuisnummer();
        huisletter = kopie.getHuisletter();
        huisnummertoevoeging = kopie.getHuisnummertoevoeging();
        postcode = kopie.getPostcode();
        woonplaats = kopie.getWoonplaats();
        locatietovAdres = kopie.getLocatietovAdres();
        locatieOmschrijving = kopie.getLocatieOmschrijving();
        datumVertrekUitNederland = kopie.getDatumVertrekUitNederland();
        buitenlandsAdresRegel1 = kopie.getBuitenlandsAdresRegel1();
        buitenlandsAdresRegel2 = kopie.getBuitenlandsAdresRegel2();
        buitenlandsAdresRegel3 = kopie.getBuitenlandsAdresRegel3();
        buitenlandsAdresRegel4 = kopie.getBuitenlandsAdresRegel4();
        buitenlandsAdresRegel5 = kopie.getBuitenlandsAdresRegel5();
        buitenlandsAdresRegel6 = kopie.getBuitenlandsAdresRegel6();
        land = kopie.getLand();
        indicatiePersoonNietAangetroffenOpAdres = kopie.getIndicatiePersoonNietAangetroffenOpAdres();

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
    public PersoonAdresModel getPersoonAdres() {
        return persoonAdres;
    }

    /**
     * Retourneert Soort van His Persoon \ Adres.
     *
     * @return Soort.
     */
    public FunctieAdres getSoort() {
        return soort;
    }

    /**
     * Retourneert Reden wijziging van His Persoon \ Adres.
     *
     * @return Reden wijziging.
     */
    public RedenWijzigingAdres getRedenWijziging() {
        return redenWijziging;
    }

    /**
     * Retourneert Aangever adreshouding van His Persoon \ Adres.
     *
     * @return Aangever adreshouding.
     */
    public AangeverAdreshouding getAangeverAdreshouding() {
        return aangeverAdreshouding;
    }

    /**
     * Retourneert Datum aanvang adreshouding van His Persoon \ Adres.
     *
     * @return Datum aanvang adreshouding.
     */
    public Datum getDatumAanvangAdreshouding() {
        return datumAanvangAdreshouding;
    }

    /**
     * Retourneert Adresseerbaar object van His Persoon \ Adres.
     *
     * @return Adresseerbaar object.
     */
    public AanduidingAdresseerbaarObject getAdresseerbaarObject() {
        return adresseerbaarObject;
    }

    /**
     * Retourneert Identificatiecode nummeraanduiding van His Persoon \ Adres.
     *
     * @return Identificatiecode nummeraanduiding.
     */
    public IdentificatiecodeNummeraanduiding getIdentificatiecodeNummeraanduiding() {
        return identificatiecodeNummeraanduiding;
    }

    /**
     * Retourneert Gemeente van His Persoon \ Adres.
     *
     * @return Gemeente.
     */
    public Partij getGemeente() {
        return gemeente;
    }

    /**
     * Retourneert Naam openbare ruimte van His Persoon \ Adres.
     *
     * @return Naam openbare ruimte.
     */
    public NaamOpenbareRuimte getNaamOpenbareRuimte() {
        return naamOpenbareRuimte;
    }

    /**
     * Retourneert Afgekorte Naam Openbare Ruimte van His Persoon \ Adres.
     *
     * @return Afgekorte Naam Openbare Ruimte.
     */
    public AfgekorteNaamOpenbareRuimte getAfgekorteNaamOpenbareRuimte() {
        return afgekorteNaamOpenbareRuimte;
    }

    /**
     * Retourneert Gemeentedeel van His Persoon \ Adres.
     *
     * @return Gemeentedeel.
     */
    public Gemeentedeel getGemeentedeel() {
        return gemeentedeel;
    }

    /**
     * Retourneert Huisnummer van His Persoon \ Adres.
     *
     * @return Huisnummer.
     */
    public Huisnummer getHuisnummer() {
        return huisnummer;
    }

    /**
     * Retourneert Huisletter van His Persoon \ Adres.
     *
     * @return Huisletter.
     */
    public Huisletter getHuisletter() {
        return huisletter;
    }

    /**
     * Retourneert Huisnummertoevoeging van His Persoon \ Adres.
     *
     * @return Huisnummertoevoeging.
     */
    public Huisnummertoevoeging getHuisnummertoevoeging() {
        return huisnummertoevoeging;
    }

    /**
     * Retourneert Postcode van His Persoon \ Adres.
     *
     * @return Postcode.
     */
    public Postcode getPostcode() {
        return postcode;
    }

    /**
     * Retourneert Woonplaats van His Persoon \ Adres.
     *
     * @return Woonplaats.
     */
    public Plaats getWoonplaats() {
        return woonplaats;
    }

    /**
     * Retourneert Locatie t.o.v. adres van His Persoon \ Adres.
     *
     * @return Locatie t.o.v. adres.
     */
    public AanduidingBijHuisnummer getLocatietovAdres() {
        return locatietovAdres;
    }

    /**
     * Retourneert Locatie omschrijving van His Persoon \ Adres.
     *
     * @return Locatie omschrijving.
     */
    public LocatieOmschrijving getLocatieOmschrijving() {
        return locatieOmschrijving;
    }

    /**
     * Retourneert Datum vertrek uit Nederland van His Persoon \ Adres.
     *
     * @return Datum vertrek uit Nederland.
     */
    public Datum getDatumVertrekUitNederland() {
        return datumVertrekUitNederland;
    }

    /**
     * Retourneert Buitenlands adres regel 1 van His Persoon \ Adres.
     *
     * @return Buitenlands adres regel 1.
     */
    public Adresregel getBuitenlandsAdresRegel1() {
        return buitenlandsAdresRegel1;
    }

    /**
     * Retourneert Buitenlands adres regel 2 van His Persoon \ Adres.
     *
     * @return Buitenlands adres regel 2.
     */
    public Adresregel getBuitenlandsAdresRegel2() {
        return buitenlandsAdresRegel2;
    }

    /**
     * Retourneert Buitenlands adres regel 3 van His Persoon \ Adres.
     *
     * @return Buitenlands adres regel 3.
     */
    public Adresregel getBuitenlandsAdresRegel3() {
        return buitenlandsAdresRegel3;
    }

    /**
     * Retourneert Buitenlands adres regel 4 van His Persoon \ Adres.
     *
     * @return Buitenlands adres regel 4.
     */
    public Adresregel getBuitenlandsAdresRegel4() {
        return buitenlandsAdresRegel4;
    }

    /**
     * Retourneert Buitenlands adres regel 5 van His Persoon \ Adres.
     *
     * @return Buitenlands adres regel 5.
     */
    public Adresregel getBuitenlandsAdresRegel5() {
        return buitenlandsAdresRegel5;
    }

    /**
     * Retourneert Buitenlands adres regel 6 van His Persoon \ Adres.
     *
     * @return Buitenlands adres regel 6.
     */
    public Adresregel getBuitenlandsAdresRegel6() {
        return buitenlandsAdresRegel6;
    }

    /**
     * Retourneert Land van His Persoon \ Adres.
     *
     * @return Land.
     */
    public Land getLand() {
        return land;
    }

    /**
     * Retourneert Persoon niet aangetroffen op adres? van His Persoon \ Adres.
     *
     * @return Persoon niet aangetroffen op adres?.
     */
    public JaNee getIndicatiePersoonNietAangetroffenOpAdres() {
        return indicatiePersoonNietAangetroffenOpAdres;
    }

    /**
     * Deze functie maakt een kopie van het object dmv het aanroepen van de copy constructor met zichzelf als argument.
     *
     * @return de kopie
     */
    @Override
    public HisPersoonAdresModel kopieer() {
        return new HisPersoonAdresModel(this);
    }
}

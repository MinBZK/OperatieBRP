package nl.bzk.brp.model.groep.operationeel;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.attribuuttype.AanduidingAdresseerbaarObject;
import nl.bzk.brp.model.attribuuttype.AanduidingBijHuisnummer;
import nl.bzk.brp.model.attribuuttype.Adresregel;
import nl.bzk.brp.model.attribuuttype.AfgekorteNaamOpenbareRuimte;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Gemeentedeel;
import nl.bzk.brp.model.attribuuttype.Huisletter;
import nl.bzk.brp.model.attribuuttype.Huisnummer;
import nl.bzk.brp.model.attribuuttype.Huisnummertoevoeging;
import nl.bzk.brp.model.attribuuttype.IdentificatiecodeNummeraanduiding;
import nl.bzk.brp.model.attribuuttype.LocatieOmschrijving;
import nl.bzk.brp.model.attribuuttype.NaamOpenbareRuimte;
import nl.bzk.brp.model.attribuuttype.Postcode;
import nl.bzk.brp.model.basis.AbstractGroep;
import nl.bzk.brp.model.groep.logisch.basis.PersoonAdresStandaardGroepBasis;
import nl.bzk.brp.model.objecttype.operationeel.statisch.AangeverAdreshouding;
import nl.bzk.brp.model.objecttype.operationeel.statisch.FunctieAdres;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Land;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Plaats;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenWijzigingAdres;


/**
 * Implementatie voor groep "Standaard" van objecttype "Persoon \ Adres".
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonAdresStandaardGroep extends AbstractGroep implements
        PersoonAdresStandaardGroepBasis
{

    @Column(name = "Srt")
    @Enumerated
    private FunctieAdres soort;

    @ManyToOne
    @JoinColumn(name = "RdnWijz")
    private RedenWijzigingAdres redenWijziging;

    @ManyToOne
    @JoinColumn(name = "AangAdresh")
    private AangeverAdreshouding aangeverAdreshouding;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatAanvAdresh"))
    private Datum datumAanvangAdreshouding;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "AdresseerbaarObject"))
    private AanduidingAdresseerbaarObject adresseerbaarObject;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "IdentcodeNraand"))
    private IdentificatiecodeNummeraanduiding identificatiecodeNummeraanduiding;

    @ManyToOne
    @JoinColumn(name = "Gem")
    private Partij gemeente;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "NOR"))
    private NaamOpenbareRuimte naamOpenbareRuimte;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "AfgekorteNOR"))
    private AfgekorteNaamOpenbareRuimte afgekorteNaamOpenbareRuimte;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Gemdeel"))
    private Gemeentedeel gemeentedeel;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Huisnr"))
    private Huisnummer huisnummer;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Huisletter"))
    private Huisletter huisletter;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Huisnrtoevoeging"))
    private Huisnummertoevoeging huisnummertoevoeging;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Postcode"))
    private Postcode postcode;

    @ManyToOne
    @JoinColumn(name = "Wpl")
    private Plaats woonplaats;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "LoctovAdres"))
    private AanduidingBijHuisnummer locatietovAdres;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "LocOms"))
    private LocatieOmschrijving locatieOmschrijving;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "BLAdresRegel1"))
    private Adresregel buitenlandsAdresRegel1;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "BLAdresRegel2"))
    private Adresregel buitenlandsAdresRegel2;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "BLAdresRegel3"))
    private Adresregel buitenlandsAdresRegel3;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "BLAdresRegel4"))
    private Adresregel buitenlandsAdresRegel4;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "BLAdresRegel5"))
    private Adresregel buitenlandsAdresRegel5;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "BLAdresRegel6"))
    private Adresregel buitenlandsAdresRegel6;

    @ManyToOne
    @JoinColumn(name = "Land")
    private Land land;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatVertrekUitNederland"))
    private Datum datumVertrekUitNederland;

    /**
     * Default constructor tbv hibernate.
     */
    protected AbstractPersoonAdresStandaardGroep() {
        super();
    }

    /**
     * Copy constructor.
     *
     * @param groep De te kopieren groep
     */
    protected AbstractPersoonAdresStandaardGroep(
            final PersoonAdresStandaardGroepBasis groep)
    {
        soort = groep.getSoort();
        redenWijziging = groep.getRedenWijziging();
        aangeverAdreshouding = groep.getAangeverAdreshouding();
        datumAanvangAdreshouding = groep.getDatumAanvangAdreshouding();
        adresseerbaarObject = groep.getAdresseerbaarObject();
        identificatiecodeNummeraanduiding = groep.getIdentificatiecodeNummeraanduiding();
        gemeente = groep.getGemeente();
        naamOpenbareRuimte = groep.getNaamOpenbareRuimte();
        afgekorteNaamOpenbareRuimte = groep.getAfgekorteNaamOpenbareRuimte();
        gemeentedeel = groep.getGemeentedeel();
        huisnummer = groep.getHuisnummer();
        huisletter = groep.getHuisletter();
        huisnummertoevoeging = groep.getHuisnummertoevoeging();
        postcode = groep.getPostcode();
        woonplaats = groep.getWoonplaats();
        locatietovAdres = groep.getLocatietovAdres();
        locatieOmschrijving = groep.getLocatieOmschrijving();
        buitenlandsAdresRegel1 = groep.getBuitenlandsAdresRegel1();
        buitenlandsAdresRegel2 = groep.getBuitenlandsAdresRegel2();
        buitenlandsAdresRegel3 = groep.getBuitenlandsAdresRegel3();
        buitenlandsAdresRegel4 = groep.getBuitenlandsAdresRegel4();
        buitenlandsAdresRegel5 = groep.getBuitenlandsAdresRegel5();
        buitenlandsAdresRegel6 = groep.getBuitenlandsAdresRegel6();
        land = groep.getLand();
        datumVertrekUitNederland = groep.getDatumVertrekUitNederland();
    }

    @Override
    public FunctieAdres getSoort() {
        return soort;
    }

    @Override
    public RedenWijzigingAdres getRedenWijziging() {
        return redenWijziging;
    }

    @Override
    public AangeverAdreshouding getAangeverAdreshouding() {
        return aangeverAdreshouding;
    }

    @Override
    public Datum getDatumAanvangAdreshouding() {
        return datumAanvangAdreshouding;
    }

    @Override
    public AanduidingAdresseerbaarObject getAdresseerbaarObject() {
        return adresseerbaarObject;
    }

    @Override
    public IdentificatiecodeNummeraanduiding getIdentificatiecodeNummeraanduiding() {
        return identificatiecodeNummeraanduiding;
    }

    @Override
    public Partij getGemeente() {
        return gemeente;
    }

    @Override
    public NaamOpenbareRuimte getNaamOpenbareRuimte() {
        return naamOpenbareRuimte;
    }

    @Override
    public AfgekorteNaamOpenbareRuimte getAfgekorteNaamOpenbareRuimte() {
        return afgekorteNaamOpenbareRuimte;
    }

    @Override
    public Gemeentedeel getGemeentedeel() {
        return gemeentedeel;
    }

    @Override
    public Huisnummer getHuisnummer() {
        return huisnummer;
    }

    @Override
    public Huisletter getHuisletter() {
        return huisletter;
    }

    @Override
    public Huisnummertoevoeging getHuisnummertoevoeging() {
        return huisnummertoevoeging;
    }

    @Override
    public Postcode getPostcode() {
        return postcode;
    }

    @Override
    public Plaats getWoonplaats() {
        return woonplaats;
    }

    @Override
    public AanduidingBijHuisnummer getLocatietovAdres() {
        return locatietovAdres;
    }

    @Override
    public LocatieOmschrijving getLocatieOmschrijving() {
        return locatieOmschrijving;
    }

    @Override
    public Adresregel getBuitenlandsAdresRegel1() {
        return buitenlandsAdresRegel1;
    }

    @Override
    public Adresregel getBuitenlandsAdresRegel2() {
        return buitenlandsAdresRegel2;
    }

    @Override
    public Adresregel getBuitenlandsAdresRegel3() {
        return buitenlandsAdresRegel3;
    }

    @Override
    public Adresregel getBuitenlandsAdresRegel4() {
        return buitenlandsAdresRegel4;
    }

    @Override
    public Adresregel getBuitenlandsAdresRegel5() {
        return buitenlandsAdresRegel5;
    }

    @Override
    public Adresregel getBuitenlandsAdresRegel6() {
        return buitenlandsAdresRegel6;
    }

    @Override
    public Land getLand() {
        return land;
    }

    @Override
    public Datum getDatumVertrekUitNederland() {
        return datumVertrekUitNederland;
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.groep.operationeel;

import javax.persistence.*;

import nl.bzk.copy.model.attribuuttype.*;
import nl.bzk.copy.model.basis.AbstractGroep;
import nl.bzk.copy.model.groep.logisch.basis.PersoonAdresStandaardGroepBasis;
import nl.bzk.copy.model.objecttype.operationeel.statisch.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 * Implementatie voor standaard groep van persoon adres.
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

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatAanvAdresh"))
    private Datum datumAanvangAdreshouding;

    @ManyToOne
    @JoinColumn(name = "RdnWijz")
    @Fetch(FetchMode.JOIN)
    private RedenWijzigingAdres redenwijziging;

    @Enumerated
    @Column(name = "AangAdresh")
    private AangeverAdreshoudingIdentiteit aangeverAdreshouding;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "adresseerbaarObject"))
    private Adresseerbaarobject adresseerbaarObject;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "IdentcodeNraand"))
    private IdentificatiecodeNummeraanduiding identificatiecodeNummeraanduiding;

    @ManyToOne
    @JoinColumn(name = "Gem")
    @Fetch(FetchMode.JOIN)
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
    @AttributeOverride(name = "waarde", column = @Column(name = "huisletter"))
    private Huisletter huisletter;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "postcode"))
    @nl.bzk.copy.model.validatie.constraint.Postcode
    private Postcode postcode;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Huisnrtoevoeging"))
    private Huisnummertoevoeging huisnummertoevoeging;

    @ManyToOne
    @JoinColumn(name = "Wpl")
    @Fetch(FetchMode.JOIN)
    private Plaats woonplaats;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "LoctovAdres"))
    private LocatieTovAdres locatietovAdres;

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
    @Fetch(FetchMode.JOIN)
    private Land land;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatVertrekUitNederland"))
    private Datum datumVertrekUitNederland;

    /**
     * Default constructor tbv hibernate.
     */
    protected AbstractPersoonAdresStandaardGroep() {

    }

    /**
     * .
     *
     * @param persoonAdresStandaardGroepBasis
     *         PersoonAdresStandaardGroepBasis
     */
    protected AbstractPersoonAdresStandaardGroep(final PersoonAdresStandaardGroepBasis persoonAdresStandaardGroepBasis) {
        soort = persoonAdresStandaardGroepBasis.getSoort();
        datumAanvangAdreshouding = persoonAdresStandaardGroepBasis.getDatumAanvangAdreshouding();
        redenwijziging = persoonAdresStandaardGroepBasis.getRedenWijziging();
        aangeverAdreshouding = persoonAdresStandaardGroepBasis.getAangeverAdresHouding();
        adresseerbaarObject = persoonAdresStandaardGroepBasis.getAdresseerbaarObject();
        identificatiecodeNummeraanduiding = persoonAdresStandaardGroepBasis.getIdentificatiecodeNummeraanduiding();
        gemeente = persoonAdresStandaardGroepBasis.getGemeente();
        naamOpenbareRuimte = persoonAdresStandaardGroepBasis.getNaamOpenbareRuimte();
        afgekorteNaamOpenbareRuimte = persoonAdresStandaardGroepBasis.getAfgekorteNaamOpenbareRuimte();
        gemeentedeel = persoonAdresStandaardGroepBasis.getGemeentedeel();
        huisnummer = persoonAdresStandaardGroepBasis.getHuisnummer();
        huisletter = persoonAdresStandaardGroepBasis.getHuisletter();
        postcode = persoonAdresStandaardGroepBasis.getPostcode();
        huisnummertoevoeging = persoonAdresStandaardGroepBasis.getHuisnummertoevoeging();
        woonplaats = persoonAdresStandaardGroepBasis.getWoonplaats();
        locatietovAdres = persoonAdresStandaardGroepBasis.getLocatietovAdres();
        locatieOmschrijving = persoonAdresStandaardGroepBasis.getLocatieOmschrijving();
        buitenlandsAdresRegel1 = persoonAdresStandaardGroepBasis.getBuitenlandsAdresRegel1();
        buitenlandsAdresRegel2 = persoonAdresStandaardGroepBasis.getBuitenlandsAdresRegel2();
        buitenlandsAdresRegel3 = persoonAdresStandaardGroepBasis.getBuitenlandsAdresRegel3();
        buitenlandsAdresRegel4 = persoonAdresStandaardGroepBasis.getBuitenlandsAdresRegel4();
        buitenlandsAdresRegel5 = persoonAdresStandaardGroepBasis.getBuitenlandsAdresRegel5();
        buitenlandsAdresRegel6 = persoonAdresStandaardGroepBasis.getBuitenlandsAdresRegel6();
        land = persoonAdresStandaardGroepBasis.getLand();
        datumVertrekUitNederland = persoonAdresStandaardGroepBasis.getDatumVertrekUitNederland();
    }

    @Override
    public FunctieAdres getSoort() {
        return soort;
    }

    @Override
    public Datum getDatumAanvangAdreshouding() {
        return datumAanvangAdreshouding;
    }

    @Override
    public RedenWijzigingAdres getRedenWijziging() {
        return redenwijziging;
    }

    @Override
    public AangeverAdreshoudingIdentiteit getAangeverAdresHouding() {
        return aangeverAdreshouding;
    }

    @Override
    public Adresseerbaarobject getAdresseerbaarObject() {
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
    public Postcode getPostcode() {
        return postcode;
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
    public Plaats getWoonplaats() {
        return woonplaats;
    }

    @Override
    public LocatieTovAdres getLocatietovAdres() {
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

    // Added
    public void setSoort(final FunctieAdres soort) {
        this.soort = soort;
    }

    public void setDatumAanvangAdreshouding(final Datum datumAanvangAdreshouding) {
        this.datumAanvangAdreshouding = datumAanvangAdreshouding;
    }

    public void setAangeverAdreshouding(final AangeverAdreshoudingIdentiteit aangeverAdreshouding) {
        this.aangeverAdreshouding = aangeverAdreshouding;
    }

    public void setAdresseerbaarObject(final Adresseerbaarobject adresseerbaarObject) {
        this.adresseerbaarObject = adresseerbaarObject;
    }

    public void setIdentificatiecodeNummeraanduiding(
            final IdentificatiecodeNummeraanduiding identificatiecodeNummeraanduiding)
    {
        this.identificatiecodeNummeraanduiding = identificatiecodeNummeraanduiding;
    }

    public void setNaamOpenbareRuimte(final NaamOpenbareRuimte naamOpenbareRuimte) {
        this.naamOpenbareRuimte = naamOpenbareRuimte;
    }

    public void setAfgekorteNaamOpenbareRuimte(final AfgekorteNaamOpenbareRuimte afgekorteNaamOpenbareRuimte) {
        this.afgekorteNaamOpenbareRuimte = afgekorteNaamOpenbareRuimte;
    }

    public void setGemeentedeel(final Gemeentedeel gemeentedeel) {
        this.gemeentedeel = gemeentedeel;
    }

    public void setHuisnummer(final Huisnummer huisnummer) {
        this.huisnummer = huisnummer;
    }

    public void setHuisletter(final Huisletter huisletter) {
        this.huisletter = huisletter;
    }

    public void setPostcode(final Postcode postcode) {
        this.postcode = postcode;
    }

    public void setHuisnummertoevoeging(final Huisnummertoevoeging huisnummertoevoeging) {
        this.huisnummertoevoeging = huisnummertoevoeging;
    }

    public void setLocatietovAdres(final LocatieTovAdres locatietovAdres) {
        this.locatietovAdres = locatietovAdres;
    }

    public void setLocatieOmschrijving(final LocatieOmschrijving locatieOmschrijving) {
        this.locatieOmschrijving = locatieOmschrijving;
    }

    public void setBuitenlandsAdresRegel1(final Adresregel buitenlandsAdresRegel1) {
        this.buitenlandsAdresRegel1 = buitenlandsAdresRegel1;
    }

    public void setBuitenlandsAdresRegel2(final Adresregel buitenlandsAdresRegel2) {
        this.buitenlandsAdresRegel2 = buitenlandsAdresRegel2;
    }

    public void setBuitenlandsAdresRegel3(final Adresregel buitenlandsAdresRegel3) {
        this.buitenlandsAdresRegel3 = buitenlandsAdresRegel3;
    }

    public void setBuitenlandsAdresRegel4(final Adresregel buitenlandsAdresRegel4) {
        this.buitenlandsAdresRegel4 = buitenlandsAdresRegel4;
    }

    public void setBuitenlandsAdresRegel5(final Adresregel buitenlandsAdresRegel5) {
        this.buitenlandsAdresRegel5 = buitenlandsAdresRegel5;
    }

    public void setBuitenlandsAdresRegel6(final Adresregel buitenlandsAdresRegel6) {
        this.buitenlandsAdresRegel6 = buitenlandsAdresRegel6;
    }

    public void setDatumVertrekUitNederland(final Datum datumVertrekUitNederland) {
        this.datumVertrekUitNederland = datumVertrekUitNederland;
    }

    public void setRedenwijziging(final RedenWijzigingAdres redenwijziging) {
        this.redenwijziging = redenwijziging;
    }

    public void setGemeente(final Partij gemeente) {
        this.gemeente = gemeente;
    }

    public void setWoonplaats(final Plaats woonplaats) {
        this.woonplaats = woonplaats;
    }

    public void setLand(final Land land) {
        this.land = land;
    }
}

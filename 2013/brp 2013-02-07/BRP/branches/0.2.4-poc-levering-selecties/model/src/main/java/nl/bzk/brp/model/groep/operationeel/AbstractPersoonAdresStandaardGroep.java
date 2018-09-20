/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.operationeel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import nl.bzk.brp.model.attribuuttype.*;
import nl.bzk.brp.model.basis.AbstractGegevensAttribuutType;
import nl.bzk.brp.model.basis.AbstractGroep;
import nl.bzk.brp.model.groep.logisch.basis.PersoonAdresStandaardGroepBasis;
import nl.bzk.brp.model.objecttype.operationeel.statisch.*;
import nl.bzk.brp.util.ExternalReaderService;
import nl.bzk.brp.util.ExternalWriterService;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 * Implementatie voor standaard groep van persoon adres.
 *
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonAdresStandaardGroep extends AbstractGroep implements
        PersoonAdresStandaardGroepBasis, Externalizable, Serializable
{

    @Column(name = "Srt")
    @Enumerated
    @JsonProperty
    private FunctieAdres                      soort;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatAanvAdresh"))
    @JsonProperty
    private Datum                             datumAanvangAdreshouding;

    @ManyToOne
    @JoinColumn(name = "RdnWijz")
    @JsonProperty
    @Fetch(FetchMode.JOIN)
    private RedenWijzigingAdres               redenwijziging;

    @Enumerated
    @Column(name = "AangAdresh")
    @JsonProperty
    private AangeverAdreshoudingIdentiteit    aangeverAdreshouding;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "adresseerbaarObject"))
    @JsonProperty
    private Adresseerbaarobject               adresseerbaarObject;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "IdentcodeNraand"))
    @JsonProperty
    private IdentificatiecodeNummeraanduiding identificatiecodeNummeraanduiding;

    @ManyToOne
    @JoinColumn(name = "Gem")
    @JsonProperty
    @Fetch(FetchMode.JOIN)
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
    @AttributeOverride(name = "waarde", column = @Column(name = "huisletter"))
    @JsonProperty
    private Huisletter                        huisletter;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "postcode"))
    @nl.bzk.brp.model.validatie.constraint.Postcode
    @JsonProperty
    private Postcode                          postcode;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Huisnrtoevoeging"))
    @JsonProperty
    private Huisnummertoevoeging              huisnummertoevoeging;

    @ManyToOne
    @JoinColumn(name = "Wpl")
    @JsonProperty
    @Fetch(FetchMode.JOIN)
    private Plaats                            woonplaats;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "LoctovAdres"))
    @JsonProperty
    private LocatieTovAdres                   locatietovAdres;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "LocOms"))
    @JsonProperty
    private LocatieOmschrijving               locatieOmschrijving;

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
    @JsonProperty
    @Fetch(FetchMode.JOIN)
    private Land                              land;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatVertrekUitNederland"))
    @JsonProperty
    private Datum                             datumVertrekUitNederland;

    /**
     * Default constructor tbv hibernate.
     */
    protected AbstractPersoonAdresStandaardGroep() {

    }

    /**
     * .
     *
     * @param persoonAdresStandaardGroepBasis PersoonAdresStandaardGroepBasis
     */
    protected AbstractPersoonAdresStandaardGroep(final PersoonAdresStandaardGroepBasis persoonAdresStandaardGroepBasis)
    {
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

    // TODO: generatie van setters of copy-constructors


    public void setSoort(final FunctieAdres soort) {
        this.soort = soort;
    }

    public void setDatumAanvangAdreshouding(final Datum datumAanvangAdreshouding) {
        this.datumAanvangAdreshouding = datumAanvangAdreshouding;
    }

    public void setRedenwijziging(final RedenWijzigingAdres redenwijziging) {
        this.redenwijziging = redenwijziging;
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

    public void setGemeente(final Partij gemeente) {
        this.gemeente = gemeente;
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

    public void setWoonplaats(final Plaats woonplaats) {
        this.woonplaats = woonplaats;
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

    public void setLand(final Land land) {
        this.land = land;
    }

    public void setDatumVertrekUitNederland(final Datum datumVertrekUitNederland) {
        this.datumVertrekUitNederland = datumVertrekUitNederland;
    }

    @Override
    public void writeExternal(final ObjectOutput out) throws IOException {
        ExternalWriterService.schrijfEnum(out, soort);
        ExternalWriterService.schrijfWaarde(out, datumAanvangAdreshouding);
        ExternalWriterService.schrijfNullableObject(out, this.redenwijziging);
        ExternalWriterService.schrijfEnum(out, aangeverAdreshouding);
        ExternalWriterService.schrijfWaarde(out, adresseerbaarObject);
        ExternalWriterService.schrijfWaarde(out, identificatiecodeNummeraanduiding);
        ExternalWriterService.schrijfNullableObject(out, this.gemeente);
        ExternalWriterService.schrijfWaarde(out, naamOpenbareRuimte);
        ExternalWriterService.schrijfWaarde(out, afgekorteNaamOpenbareRuimte);
        ExternalWriterService.schrijfWaarde(out, gemeentedeel);
        ExternalWriterService.schrijfWaarde(out, huisnummer);
        ExternalWriterService.schrijfWaarde(out, huisletter);
        ExternalWriterService.schrijfWaarde(out, postcode);
        ExternalWriterService.schrijfWaarde(out, huisnummertoevoeging);
        ExternalWriterService.schrijfNullableObject(out, this.woonplaats);
        ExternalWriterService.schrijfWaarde(out, locatietovAdres);
        ExternalWriterService.schrijfWaarde(out, locatieOmschrijving);
        ExternalWriterService.schrijfWaarde(out, buitenlandsAdresRegel1);
        ExternalWriterService.schrijfWaarde(out, buitenlandsAdresRegel2);
        ExternalWriterService.schrijfWaarde(out, buitenlandsAdresRegel3);
        ExternalWriterService.schrijfWaarde(out, buitenlandsAdresRegel4);
        ExternalWriterService.schrijfWaarde(out, buitenlandsAdresRegel5);
        ExternalWriterService.schrijfWaarde(out, buitenlandsAdresRegel6);
        ExternalWriterService.schrijfNullableObject(out, this.land);
        ExternalWriterService.schrijfWaarde(out, this.datumVertrekUitNederland);
    }

    @Override
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        this.soort = (FunctieAdres) ExternalReaderService.leesEnum(in, FunctieAdres.class);
        this.datumAanvangAdreshouding = (Datum) ExternalReaderService.leesWaarde(in, Datum.class);
        this.redenwijziging = (RedenWijzigingAdres) ExternalReaderService.leesNullableObject(in,
                RedenWijzigingAdres.class);
        this.aangeverAdreshouding =
                (AangeverAdreshoudingIdentiteit) ExternalReaderService
                        .leesEnum(in, AangeverAdreshoudingIdentiteit.class);
        this.adresseerbaarObject =
                (Adresseerbaarobject) ExternalReaderService.leesWaarde(in, Adresseerbaarobject.class);
        this.identificatiecodeNummeraanduiding = (IdentificatiecodeNummeraanduiding) ExternalReaderService
                .leesWaarde(in, IdentificatiecodeNummeraanduiding.class);
        this.gemeente = (Partij) ExternalReaderService.leesNullableObject(in, Partij.class);
        this.naamOpenbareRuimte = (NaamOpenbareRuimte) ExternalReaderService.leesWaarde(in, NaamOpenbareRuimte.class);
        this.afgekorteNaamOpenbareRuimte =
                (AfgekorteNaamOpenbareRuimte) ExternalReaderService.leesWaarde(in, AfgekorteNaamOpenbareRuimte.class);
        this.gemeentedeel = (Gemeentedeel) ExternalReaderService.leesWaarde(in, Gemeentedeel.class);
        this.huisnummer = (Huisnummer) ExternalReaderService.leesWaarde(in, Huisnummer.class);
        this.huisletter = (Huisletter) ExternalReaderService.leesWaarde(in, Huisletter.class);
        this.postcode = (Postcode) ExternalReaderService.leesWaarde(in, Postcode.class);
        this.huisnummertoevoeging =
                (Huisnummertoevoeging) ExternalReaderService.leesWaarde(in, Huisnummertoevoeging.class);
        this.woonplaats = (Plaats) ExternalReaderService.leesNullableObject(in, Plaats.class);
        this.locatietovAdres = (LocatieTovAdres) ExternalReaderService.leesWaarde(in, LocatieTovAdres.class);
        this.locatieOmschrijving =
                (LocatieOmschrijving) ExternalReaderService.leesWaarde(in, LocatieOmschrijving.class);
        this.buitenlandsAdresRegel1 = (Adresregel) ExternalReaderService.leesWaarde(in, Adresregel.class);
        this.buitenlandsAdresRegel2 = (Adresregel) ExternalReaderService.leesWaarde(in, Adresregel.class);
        this.buitenlandsAdresRegel3 = (Adresregel) ExternalReaderService.leesWaarde(in, Adresregel.class);
        this.buitenlandsAdresRegel4 = (Adresregel) ExternalReaderService.leesWaarde(in, Adresregel.class);
        this.buitenlandsAdresRegel5 = (Adresregel) ExternalReaderService.leesWaarde(in, Adresregel.class);
        this.buitenlandsAdresRegel6 = (Adresregel) ExternalReaderService.leesWaarde(in, Adresregel.class);
        this.land = (Land) ExternalReaderService.leesNullableObject(in, Land.class);
        this.datumVertrekUitNederland = (Datum) ExternalReaderService.leesWaarde(in, Datum.class);
    }

}

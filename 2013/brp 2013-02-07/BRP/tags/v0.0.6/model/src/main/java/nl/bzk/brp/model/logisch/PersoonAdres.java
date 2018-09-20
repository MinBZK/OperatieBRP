/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch;

import javax.validation.Valid;

import nl.bzk.brp.binding.MeldingCode;
import nl.bzk.brp.model.gedeeld.FunctieAdres;
import nl.bzk.brp.model.gedeeld.Land;
import nl.bzk.brp.model.gedeeld.Partij;
import nl.bzk.brp.model.gedeeld.Plaats;
import nl.bzk.brp.model.logisch.groep.AangeverAdreshoudingIdentiteit;
import nl.bzk.brp.model.validatie.constraint.ConditioneelVerplichtVeld;
import nl.bzk.brp.model.validatie.constraint.ConditioneelVerplichteVelden;
import nl.bzk.brp.model.validatie.constraint.Datum;


/**
 * Persoon \ Adres.
 *
 * @brp.bedrijfsregel BRAL2032
 */
@ConditioneelVerplichteVelden({
    //soort moet verplicht zijn wanneer land NL (landcode 6030) is
    @ConditioneelVerplichtVeld(naamVerplichtVeld = "soort", naamAfhankelijkVeld = "land.landcode", waardeAfhankelijkVeld = "6030", code = MeldingCode.BRAL2032) })
public class PersoonAdres {

    private Long                           id;

    @Valid
    private Persoon                        persoon;

    private FunctieAdres                   soort;

    private RedenWijzigingAdres            redenWijziging;

    private AangeverAdreshoudingIdentiteit aangeverAdreshouding;

    @Datum
    private Integer                        datumAanvangAdreshouding;

    private String                         adresseerbaarObject;

    private String                         identificatiecodeNummeraanduiding;

    private Partij                         gemeente;

    private String                         naamOpenbareRuimte;

    private String                         afgekorteNaamOpenbareRuimte;

    private String                         locatieOmschrijving;

    private String                         locatieTovAdres;

    private String                         gemeentedeel;

    private String                         huisnummer;

    private String                         huisletter;

    private String                         huisnummertoevoeging;

    private String                         postcode;

    private Plaats                         woonplaats;

    private Land                           land;

    private String                         persoonAdresStatusHis;

    public Long getId() {
        return id;
    }

    public void setId(final Long value) {
        id = value;
    }

    public Persoon getPersoon() {
        return persoon;
    }

    public void setPersoon(final Persoon persoon) {
        this.persoon = persoon;
    }

    public FunctieAdres getSoort() {
        return soort;
    }

    public void setSoort(final FunctieAdres soort) {
        this.soort = soort;
    }

    public RedenWijzigingAdres getRedenWijziging() {
        return redenWijziging;
    }

    public void setRedenWijziging(final RedenWijzigingAdres redenWijziging) {
        this.redenWijziging = redenWijziging;
    }

    public AangeverAdreshoudingIdentiteit getAangeverAdreshouding() {
        return aangeverAdreshouding;
    }

    public void setAangeverAdreshouding(final AangeverAdreshoudingIdentiteit aangeverAdreshouding) {
        this.aangeverAdreshouding = aangeverAdreshouding;
    }

    public Integer getDatumAanvangAdreshouding() {
        return datumAanvangAdreshouding;
    }

    public void setDatumAanvangAdreshouding(final Integer datumAanvangAdreshouding) {
        this.datumAanvangAdreshouding = datumAanvangAdreshouding;
    }

    public String getAdresseerbaarObject() {
        return adresseerbaarObject;
    }

    public void setAdresseerbaarObject(final String adresseerbaarObject) {
        this.adresseerbaarObject = adresseerbaarObject;
    }

    public String getIdentificatiecodeNummeraanduiding() {
        return identificatiecodeNummeraanduiding;
    }

    public void setIdentificatiecodeNummeraanduiding(final String identificatiecodeNummeraanduiding) {
        this.identificatiecodeNummeraanduiding = identificatiecodeNummeraanduiding;
    }

    public Partij getGemeente() {
        return gemeente;
    }

    public void setGemeente(final Partij gemeente) {
        this.gemeente = gemeente;
    }

    public String getNaamOpenbareRuimte() {
        return naamOpenbareRuimte;
    }

    public void setNaamOpenbareRuimte(final String naamOpenbareRuimte) {
        this.naamOpenbareRuimte = naamOpenbareRuimte;
    }

    public String getAfgekorteNaamOpenbareRuimte() {
        return afgekorteNaamOpenbareRuimte;
    }

    public void setAfgekorteNaamOpenbareRuimte(final String afgekorteNaamOpenbareRuimte) {
        this.afgekorteNaamOpenbareRuimte = afgekorteNaamOpenbareRuimte;
    }

    public String getLocatieOmschrijving() {
        return locatieOmschrijving;
    }

    public void setLocatieOmschrijving(final String locatieOmschrijving) {
        this.locatieOmschrijving = locatieOmschrijving;
    }

    public String getLocatieTovAdres() {
        return locatieTovAdres;
    }

    public void setLocatieTovAdres(final String locatieTovAdres) {
        this.locatieTovAdres = locatieTovAdres;
    }

    public String getGemeentedeel() {
        return gemeentedeel;
    }

    public void setGemeentedeel(final String gemeentedeel) {
        this.gemeentedeel = gemeentedeel;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(final String huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getHuisletter() {
        return huisletter;
    }

    public void setHuisletter(final String huisletter) {
        this.huisletter = huisletter;
    }

    public String getHuisnummertoevoeging() {
        return huisnummertoevoeging;
    }

    public void setHuisnummertoevoeging(final String huisnummertoevoeging) {
        this.huisnummertoevoeging = huisnummertoevoeging;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(final String postcode) {
        this.postcode = postcode;
    }

    public Plaats getWoonplaats() {
        return woonplaats;
    }

    public void setWoonplaats(final Plaats woonplaats) {
        this.woonplaats = woonplaats;
    }

    public Land getLand() {
        return land;
    }

    public void setLand(final Land land) {
        this.land = land;
    }

    public String getPersoonAdresStatusHis() {
        return persoonAdresStatusHis;
    }

    public void setPersoonAdresStatusHis(final String persoonAdresStatusHis) {
        this.persoonAdresStatusHis = persoonAdresStatusHis;
    }
}

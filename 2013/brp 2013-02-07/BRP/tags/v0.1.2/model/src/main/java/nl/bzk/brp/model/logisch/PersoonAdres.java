/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch;

import javax.validation.Valid;

import nl.bzk.brp.model.gedeeld.AangeverAdreshoudingIdentiteit;
import nl.bzk.brp.model.gedeeld.FunctieAdres;
import nl.bzk.brp.model.gedeeld.Land;
import nl.bzk.brp.model.gedeeld.Partij;
import nl.bzk.brp.model.gedeeld.Plaats;
import nl.bzk.brp.model.gedeeld.RedenWijzigingAdres;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.constraint.ConditioneelVerplichtVeld;
import nl.bzk.brp.model.validatie.constraint.ConditioneelVerplichteVelden;
import nl.bzk.brp.model.validatie.constraint.Datum;


/**
 * Persoon \ Adres.
 *
 * @brp.bedrijfsregel BRAL2032
 * @brp.bedrijfsregel BRAL2033
 */
@ConditioneelVerplichteVelden({
    // Verplicht zijn wanneer land NL (landcode 6030) is
    // Note: de message attribute is hier nodig anders kan de validatie framework geen onderscheidt maken tussen de validatie errors en ontstaat er maar 1 error uitkomst.
    @ConditioneelVerplichtVeld(naamVerplichtVeld = "soort", naamAfhankelijkVeld = "land.landcode", waardeAfhankelijkVeld = "6030", code = MeldingCode.BRAL2032, message = "BRAL0232"),
    @ConditioneelVerplichtVeld(naamVerplichtVeld = "datumAanvangAdreshouding", naamAfhankelijkVeld = "land.landcode", waardeAfhankelijkVeld = "6030", code = MeldingCode.BRAL2033, message = "BRAL2033"),
    @ConditioneelVerplichtVeld(naamVerplichtVeld = "gemeente", naamAfhankelijkVeld = "land.landcode", waardeAfhankelijkVeld = "6030", code = MeldingCode.BRAL2033, message = "BRAL2033"),
    @ConditioneelVerplichtVeld(naamVerplichtVeld = "redenWijziging", naamAfhankelijkVeld = "land.landcode", waardeAfhankelijkVeld = "6030", code = MeldingCode.BRAL2033, message = "BRAL2033")
})
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

    // @Postcode
    private String                         postcode;

    private Plaats                         woonplaats;

    private Land                           land;

    private String                         persoonAdresStatusHis;

    private String                         buitenlandsAdresRegel1;

    private String                         buitenlandsAdresRegel2;

    private String                         buitenlandsAdresRegel3;

    private String                         buitenlandsAdresRegel4;

    private String                         buitenlandsAdresRegel5;

    private String                         buitenlandsAdresRegel6;

    @Datum
    private Integer                        datumVertrekUitNederland;

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

    /**
     * @return the datumVertrekUitNederland
     */
    public Integer getDatumVertrekUitNederland() {
        return datumVertrekUitNederland;
    }

    /**
     * @param datumVertrekUitNederland the datumVertrekUitNederland to set
     */
    public void setDatumVertrekUitNederland(final Integer datumVertrekUitNederland) {
        this.datumVertrekUitNederland = datumVertrekUitNederland;
    }

    /**
     * @return the buitenlandsAdresRegel1
     */
    public String getBuitenlandsAdresRegel1() {
        return buitenlandsAdresRegel1;
    }

    /**
     * @param buitenlandsAdresRegel1 the buitenlandsAdresRegel1 to set
     */
    public void setBuitenlandsAdresRegel1(final String buitenlandsAdresRegel1) {
        this.buitenlandsAdresRegel1 = buitenlandsAdresRegel1;
    }

    /**
     * @return the buitenlandsAdresRegel2
     */
    public String getBuitenlandsAdresRegel2() {
        return buitenlandsAdresRegel2;
    }

    /**
     * @param buitenlandsAdresRegel2 the buitenlandsAdresRegel2 to set
     */
    public void setBuitenlandsAdresRegel2(final String buitenlandsAdresRegel2) {
        this.buitenlandsAdresRegel2 = buitenlandsAdresRegel2;
    }

    /**
     * @return the buitenlandsAdresRegel3
     */
    public String getBuitenlandsAdresRegel3() {
        return buitenlandsAdresRegel3;
    }

    /**
     * @param buitenlandsAdresRegel3 the buitenlandsAdresRegel3 to set
     */
    public void setBuitenlandsAdresRegel3(final String buitenlandsAdresRegel3) {
        this.buitenlandsAdresRegel3 = buitenlandsAdresRegel3;
    }

    /**
     * @return the buitenlandsAdresRegel4
     */
    public String getBuitenlandsAdresRegel4() {
        return buitenlandsAdresRegel4;
    }

    /**
     * @param buitenlandsAdresRegel4 the buitenlandsAdresRegel4 to set
     */
    public void setBuitenlandsAdresRegel4(final String buitenlandsAdresRegel4) {
        this.buitenlandsAdresRegel4 = buitenlandsAdresRegel4;
    }

    /**
     * @return the buitenlandsAdresRegel5
     */
    public String getBuitenlandsAdresRegel5() {
        return buitenlandsAdresRegel5;
    }

    /**
     * @param buitenlandsAdresRegel5 the buitenlandsAdresRegel5 to set
     */
    public void setBuitenlandsAdresRegel5(final String buitenlandsAdresRegel5) {
        this.buitenlandsAdresRegel5 = buitenlandsAdresRegel5;
    }

    /**
     * @return the buitenlandsAdresRegel6
     */
    public String getBuitenlandsAdresRegel6() {
        return buitenlandsAdresRegel6;
    }

    /**
     * @param buitenlandsAdresRegel6 the buitenlandsAdresRegel6 to set
     */
    public void setBuitenlandsAdresRegel6(final String buitenlandsAdresRegel6) {
        this.buitenlandsAdresRegel6 = buitenlandsAdresRegel6;
    }
}

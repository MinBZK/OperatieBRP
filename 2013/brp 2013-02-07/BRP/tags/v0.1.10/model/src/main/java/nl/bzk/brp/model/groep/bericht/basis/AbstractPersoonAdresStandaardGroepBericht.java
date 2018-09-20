/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.bericht.basis;

import nl.bzk.brp.constanten.BrpConstanten;
import nl.bzk.brp.model.attribuuttype.Adresregel;
import nl.bzk.brp.model.attribuuttype.Adresseerbaarobject;
import nl.bzk.brp.model.attribuuttype.AfgekorteNaamOpenbareRuimte;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Gemeentecode;
import nl.bzk.brp.model.attribuuttype.Gemeentedeel;
import nl.bzk.brp.model.attribuuttype.Huisletter;
import nl.bzk.brp.model.attribuuttype.Huisnummer;
import nl.bzk.brp.model.attribuuttype.Huisnummertoevoeging;
import nl.bzk.brp.model.attribuuttype.IdentificatiecodeNummeraanduiding;
import nl.bzk.brp.model.attribuuttype.Landcode;
import nl.bzk.brp.model.attribuuttype.LocatieOmschrijving;
import nl.bzk.brp.model.attribuuttype.LocatieTovAdres;
import nl.bzk.brp.model.attribuuttype.NaamOpenbareRuimte;
import nl.bzk.brp.model.attribuuttype.PlaatsCode;
import nl.bzk.brp.model.attribuuttype.Postcode;
import nl.bzk.brp.model.attribuuttype.RedenWijzigingAdresCode;
import nl.bzk.brp.model.basis.AbstractGroepBericht;
import nl.bzk.brp.model.groep.logisch.basis.PersoonAdresStandaardGroepBasis;
import nl.bzk.brp.model.objecttype.operationeel.statisch.AangeverAdreshoudingIdentiteit;
import nl.bzk.brp.model.objecttype.operationeel.statisch.FunctieAdres;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Land;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Plaats;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenWijzigingAdres;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.constraint.ConditioneelVerplichtVeld;
import nl.bzk.brp.model.validatie.constraint.ConditioneelVerplichteVelden;


/**
 * Implementatie voor standaard groep van persoon adres.
 *
 */
@ConditioneelVerplichteVelden({
    // Verplicht zijn wanneer land NL (landcode 6030) is
    // Note: de message attribute is hier nodig anders kan de validatie framework geen onderscheidt maken tussen de validatie errors en ontstaat er maar 1 error uitkomst.
    @ConditioneelVerplichtVeld(naamVerplichtVeld = "soort", naamAfhankelijkVeld = "land.code", waardeAfhankelijkVeld = BrpConstanten.NL_LAND_CODE_STRING, code = MeldingCode.BRAL2032, message = "BRAL2032"),
    @ConditioneelVerplichtVeld(naamVerplichtVeld = "datumAanvangAdreshouding", naamAfhankelijkVeld = "land.code", waardeAfhankelijkVeld = BrpConstanten.NL_LAND_CODE_STRING, code = MeldingCode.BRAL2033, message = "BRAL2033"),
    @ConditioneelVerplichtVeld(naamVerplichtVeld = "gemeente.gemeentecode", naamAfhankelijkVeld = "land.code", waardeAfhankelijkVeld = BrpConstanten.NL_LAND_CODE_STRING, code = MeldingCode.BRAL2033, message = "BRAL2033"),
    @ConditioneelVerplichtVeld(naamVerplichtVeld = "redenwijziging.redenWijzigingAdresCode", naamAfhankelijkVeld = "land.code", waardeAfhankelijkVeld = BrpConstanten.NL_LAND_CODE_STRING, code = MeldingCode.BRAL2033, message = "BRAL2033"),
    @ConditioneelVerplichtVeld(naamVerplichtVeld = "aangeverAdreshouding", naamAfhankelijkVeld = "redenwijziging.redenWijzigingAdresCode", waardeAfhankelijkVeld = BrpConstanten.PERSOON_REDEN_WIJZIGING_ADRES_CODE_STRING, code = MeldingCode.BRAL1118, message = "BRAL1118")
})
@SuppressWarnings("serial")
public abstract class AbstractPersoonAdresStandaardGroepBericht extends AbstractGroepBericht implements
        PersoonAdresStandaardGroepBasis
{

    private FunctieAdres                      soort;
    @nl.bzk.brp.model.validatie.constraint.Datum
    private Datum                             datumAanvangAdreshouding;
    private RedenWijzigingAdres               redenwijziging;
    private RedenWijzigingAdresCode           redenwijzigingCode;
    private AangeverAdreshoudingIdentiteit    aangeverAdreshouding;
    private Adresseerbaarobject               adresseerbaarObject;
    private IdentificatiecodeNummeraanduiding identificatiecodeNummeraanduiding;
    private Partij                            gemeente;
    private Gemeentecode                      gemeentecode;
    private NaamOpenbareRuimte                naamOpenbareRuimte;
    private AfgekorteNaamOpenbareRuimte       afgekorteNaamOpenbareRuimte;
    private Gemeentedeel                      gemeentedeel;
    private Huisnummer                        huisnummer;
    private Huisletter                        huisletter;
    private Postcode                          postcode;
    private Huisnummertoevoeging              huisnummertoevoeging;
    private Plaats                            woonplaats;
    private PlaatsCode                        woonplaatsCode;
    private LocatieTovAdres                   locatietovAdres;
    private LocatieOmschrijving               locatieOmschrijving;
    private Adresregel                        buitenlandsAdresRegel1;
    private Adresregel                        buitenlandsAdresRegel2;
    private Adresregel                        buitenlandsAdresRegel3;
    private Adresregel                        buitenlandsAdresRegel4;
    private Adresregel                        buitenlandsAdresRegel5;
    private Adresregel                        buitenlandsAdresRegel6;
    private Land                              land;
    private Landcode                          landcode;
    @nl.bzk.brp.model.validatie.constraint.Datum
    private Datum                             datumVertrekUitNederland;

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

    public RedenWijzigingAdres getRedenwijziging() {
        return redenwijziging;
    }

    public void setRedenwijziging(final RedenWijzigingAdres redenwijziging) {
        this.redenwijziging = redenwijziging;
    }

    public AangeverAdreshoudingIdentiteit getAangeverAdreshouding() {
        return aangeverAdreshouding;
    }

    public void setAangeverAdreshouding(final AangeverAdreshoudingIdentiteit aangeverAdreshouding) {
        this.aangeverAdreshouding = aangeverAdreshouding;
    }

    public void setSoort(final FunctieAdres soort) {
        this.soort = soort;
    }

    public void setDatumAanvangAdreshouding(final Datum datumAanvangAdreshouding) {
        this.datumAanvangAdreshouding = datumAanvangAdreshouding;
    }

    public void setAdresseerbaarObject(final Adresseerbaarobject adresseerbaarObject) {
        this.adresseerbaarObject = adresseerbaarObject;
    }

    public void setIdentificatiecodeNummeraanduiding(final IdentificatiecodeNummeraanduiding identificatiecodeNummeraanduiding)
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

    public RedenWijzigingAdresCode getRedenWijzigingAdresCode() {
        return redenwijzigingCode;
    }

    public Gemeentecode getGemeentecode() {
        return gemeentecode;
    }

    public PlaatsCode getCode() {
        return woonplaatsCode;
    }

    public Landcode getLandcode() {
        return landcode;
    }

    public void setRedenwijzigingCode(final RedenWijzigingAdresCode redenwijzigingCode) {
        this.redenwijzigingCode = redenwijzigingCode;
    }

    public void setGemeentecode(final Gemeentecode gemeentecode) {
        this.gemeentecode = gemeentecode;
    }

    public void setWoonplaatsCode(final PlaatsCode woonplaatsCode) {
        this.woonplaatsCode = woonplaatsCode;
    }

    public void setLandcode(final Landcode landcode) {
        this.landcode = landcode;
    }
}

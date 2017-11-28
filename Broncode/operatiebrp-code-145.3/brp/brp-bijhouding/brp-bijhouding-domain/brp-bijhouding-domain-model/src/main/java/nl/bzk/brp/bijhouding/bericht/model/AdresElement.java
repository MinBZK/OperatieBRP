/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Aangever;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Plaats;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenWijzigingVerblijf;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;

/**
 * Het element dat een adres BMR groep representeert.
 */
@XmlElement("adres")
public class AdresElement extends AbstractBmrGroep {

    private static final int LOCATIE_OMS_MAX_LENGTE = 35;
    private static final char REDEN_WIJZIGING_AMBTSHALVE = 'A';
    private static final char REDEN_WIJZIGING_DOOR_PERSOON = 'P';
    private final StringElement soortCode;
    private final CharacterElement redenWijzigingCode;
    private final CharacterElement aangeverAdreshoudingCode;
    private final DatumElement datumAanvangAdreshouding;
    private final StringElement identificatiecodeAdresseerbaarObject;
    private final StringElement identificatiecodeNummeraanduiding;
    private final StringElement gemeenteCode;
    private final StringElement naamOpenbareRuimte;
    private final StringElement afgekorteNaamOpenbareRuimte;
    private final StringElement huisnummer;
    private final CharacterElement huisletter;
    private final StringElement huisnummertoevoeging;
    private final StringElement postcode;
    private final StringElement woonplaatsnaam;
    private final StringElement locatieTenOpzichteVanAdres;
    private final StringElement locatieomschrijving;


    /**
     * Maakt een nieuw AdresElement object.
     * @param attributen attributen
     * @param soortCode soortCode
     * @param redenWijzigingCode redenWijzigingCode
     * @param aangeverAdreshoudingCode aangeverAdreshoudingCode
     * @param datumAanvangAdreshouding datumAanvangAdreshouding
     * @param identificatiecodeAdresseerbaarObject identificatiecodeAdresseerbaarObject
     * @param identificatiecodeNummeraanduiding identificatiecodeNummeraanduiding
     * @param gemeenteCode gemeenteCode
     * @param naamOpenbareRuimte naamOpenbareRuimte
     * @param afgekorteNaamOpenbareRuimte afgekorteNaamOpenbareRuimte
     * @param huisnummer huisnummer
     * @param huisletter huisletter
     * @param huisnummertoevoeging huisnummertoevoeging
     * @param postcode postcode
     * @param woonplaatsnaam woonplaatsnaam
     * @param locatieTenOpzichteVanAdres locatieTenOpzichteVanAdres
     * @param locatieomschrijving locatieomschrijving
     */
    //
    public AdresElement(final Map<String, String> attributen,
                        final StringElement soortCode,
                        final CharacterElement redenWijzigingCode,
                        final CharacterElement aangeverAdreshoudingCode,
                        final DatumElement datumAanvangAdreshouding,
                        final StringElement identificatiecodeAdresseerbaarObject,
                        final StringElement identificatiecodeNummeraanduiding,
                        final StringElement gemeenteCode,
                        final StringElement naamOpenbareRuimte,
                        final StringElement afgekorteNaamOpenbareRuimte,
                        final StringElement huisnummer,
                        final CharacterElement huisletter,
                        final StringElement huisnummertoevoeging,
                        final StringElement postcode,
                        final StringElement woonplaatsnaam,
                        final StringElement locatieTenOpzichteVanAdres,
                        final StringElement locatieomschrijving) {
        super(attributen);
        this.soortCode = soortCode;
        this.redenWijzigingCode = redenWijzigingCode;
        this.aangeverAdreshoudingCode = aangeverAdreshoudingCode;
        this.datumAanvangAdreshouding = datumAanvangAdreshouding;
        this.identificatiecodeAdresseerbaarObject = identificatiecodeAdresseerbaarObject;
        this.identificatiecodeNummeraanduiding = identificatiecodeNummeraanduiding;
        this.gemeenteCode = gemeenteCode;
        this.naamOpenbareRuimte = naamOpenbareRuimte;
        this.afgekorteNaamOpenbareRuimte = afgekorteNaamOpenbareRuimte;
        this.huisnummer = huisnummer;
        this.huisletter = huisletter;
        this.huisnummertoevoeging = huisnummertoevoeging;
        this.postcode = postcode;
        this.woonplaatsnaam = woonplaatsnaam;
        this.locatieTenOpzichteVanAdres = locatieTenOpzichteVanAdres;
        this.locatieomschrijving = locatieomschrijving;
    }

    /**
     * geeft soort code terug.
     * @return soort code
     */
    public StringElement getSoortCode() {
        return soortCode;
    }

    /**
     * geeft reden wijziging code terug.
     * @return reden wijziging code
     */
    public CharacterElement getRedenWijzigingCode() {
        return redenWijzigingCode;
    }

    /**
     * geeft aangever adreshouding code terug.
     * @return aangever adreshouding code
     */
    public CharacterElement getAangeverAdreshoudingCode() {
        return aangeverAdreshoudingCode;
    }

    /**
     * geeft datum aanvang adres houding terug.
     * @return datum aanvang adres houding
     */
    public DatumElement getDatumAanvangAdreshouding() {
        return datumAanvangAdreshouding;
    }

    /**
     * geeft identificatiecode adresseerbaar object terug.
     * @return identificatiecode adresseerbaar object
     */
    public StringElement getIdentificatiecodeAdresseerbaarObject() {
        return identificatiecodeAdresseerbaarObject;
    }

    /**
     * geeft identificatiecode nummer aanduiding terug.
     * @return identificatiecode nummer aanduiding
     */
    public StringElement getIdentificatiecodeNummeraanduiding() {
        return identificatiecodeNummeraanduiding;
    }

    /**
     * geeft gemeente code terug.
     * @return gemeente code
     */
    public StringElement getGemeenteCode() {
        return gemeenteCode;
    }

    /**
     * geeft naam openbare ruimte terug.
     * @return naam openbare ruimte
     */
    public StringElement getNaamOpenbareRuimte() {
        return naamOpenbareRuimte;
    }

    /**
     * geeft afgekorte naam openbare ruimte terug.
     * @return afgekorte naam openbare ruimte
     */
    public StringElement getAfgekorteNaamOpenbareRuimte() {
        return afgekorteNaamOpenbareRuimte;
    }

    /**
     * geeft huisnummer terug.
     * @return huisnummer
     */
    public StringElement getHuisnummer() {
        return huisnummer;
    }

    /**
     * geeft huisletter terug.
     * @return huisletter
     */
    public CharacterElement getHuisletter() {
        return huisletter;
    }

    /**
     * geeft huisnummer toevoeging terug.
     * @return huisnummer toevoeging
     */
    public StringElement getHuisnummertoevoeging() {
        return huisnummertoevoeging;
    }

    /**
     * geeft postcode terug.
     * @return postcode
     */
    public StringElement getPostcode() {
        return postcode;
    }

    /**
     * geeft Wiinplaats naam terug.
     * @return woonplaats naam
     */
    public StringElement getWoonplaatsnaam() {
        return woonplaatsnaam;
    }

    /**
     * geeft Locatie ten opzichte van adres terug.
     * @return Locatie ten opzichte van adres
     */
    public StringElement getLocatieTenOpzichteVanAdres() {
        return locatieTenOpzichteVanAdres;
    }

    /**
     * geeft locatie omschrijving terug.
     * @return locatieOmschrijving
     */
    public StringElement getLocatieomschrijving() {
        return locatieomschrijving;
    }

    @Override
    protected List<MeldingElement> valideerInhoud() {
        final List<MeldingElement> meldingen = new ArrayList<>();
        controleerAfgekorteNaamOpenbareRuimte(meldingen);
        controleerGemeente(meldingen);
        controleerDatumAanvangAdreshouding(meldingen);
        controleerLocatieomschrijving(meldingen);
        controleerLocatieTenOpzichtVanAdres(meldingen);
        controleerRedenWijziging(meldingen);
        controleerAangever(meldingen);
        controleerHuisnummer(meldingen);
        controleerHuisletter(meldingen);
        controleerHuisnummerToevoeging(meldingen);
        controleerIdentificatiecodeNummerAanduiding(meldingen);
        controleerIdentificatiecodeAdresseerbaarObject(meldingen);
        controleerPostcode(meldingen);
        controleerNaamOpenbareRuimte(meldingen);
        controleerWoonplaatsnaam(meldingen);
        return meldingen;
    }

    @Bedrijfsregel(Regel.R2314)
    @Bedrijfsregel(Regel.R2316)
    private void controleerWoonplaatsnaam(final List<MeldingElement> meldingen) {
        if (isBAGAdres() && woonplaatsnaam == null) {
            meldingen.add(MeldingElement.getInstance(Regel.R2314, this));
        }
        if (woonplaatsnaam != null) {
            final Plaats plaats = getDynamischeStamtabelRepository().getPlaatsByPlaatsNaam(woonplaatsnaam.getWaarde());
            if (plaats == null) {
                meldingen.add(MeldingElement.getInstance(Regel.R2316, this));
            }
        }
    }

    @Bedrijfsregel(Regel.R2313)
    private void controleerNaamOpenbareRuimte(final List<MeldingElement> meldingen) {
        if (isBAGAdres() && naamOpenbareRuimte == null) {
            meldingen.add(MeldingElement.getInstance(Regel.R2313, this));
        }
    }

    @Bedrijfsregel(Regel.R1357)
    @Bedrijfsregel(Regel.R1375)
    private void controleerLocatieomschrijving(final List<MeldingElement> meldingen) {
        if (locatieomschrijving != null) {
            if (isBAGAdres()) {
                meldingen.add(MeldingElement.getInstance(Regel.R1357, this));
            }
            if (locatieomschrijving.getWaarde().length() > LOCATIE_OMS_MAX_LENGTE) {
                meldingen.add(MeldingElement.getInstance(Regel.R1375, this));
            }
        }
    }

    @Bedrijfsregel(Regel.R2317)
    @Bedrijfsregel(Regel.R1370)
    private void controleerLocatieTenOpzichtVanAdres(final List<MeldingElement> meldingen) {
        if (locatieTenOpzichteVanAdres != null) {
            if (huisnummertoevoeging != null) {
                meldingen.add(MeldingElement.getInstance(Regel.R2317, this));
            } else if (!(isBAGAdres() || isNietBAGAdresOpenbareRuimte())) {
                meldingen.add(MeldingElement.getInstance(Regel.R1370, this));
            }
        }

    }

    @Bedrijfsregel(Regel.R1358)
    @Bedrijfsregel(Regel.R2416)
    private void controleerAfgekorteNaamOpenbareRuimte(final List<MeldingElement> meldingen) {
        if (isBAGAdres() || isNietBAGAdresOpenbareRuimte()) {
            if (afgekorteNaamOpenbareRuimte == null) {
                meldingen.add(MeldingElement.getInstance(Regel.R1358, this));
            }
        } else {
            if (afgekorteNaamOpenbareRuimte != null) {
                meldingen.add(MeldingElement.getInstance(Regel.R2416, this));
            }
        }
    }

    @Bedrijfsregel(Regel.R1367)
    @Bedrijfsregel(Regel.R2417)
    private void controleerHuisnummer(final List<MeldingElement> meldingen) {
        if (isBAGAdres() || isNietBAGAdresOpenbareRuimte()) {
            if (huisnummer == null) {
                meldingen.add(MeldingElement.getInstance(Regel.R1367, this));
            }
        } else {
            if (huisnummer != null) {
                meldingen.add(MeldingElement.getInstance(Regel.R2417, this));
            }
        }
    }

    @Bedrijfsregel(Regel.R1368)
    private void controleerHuisletter(final List<MeldingElement> meldingen) {
        if (huisletter != null && isNietBAGAdres()) {
            meldingen.add(MeldingElement.getInstance(Regel.R1368, this));
        }
    }

    @Bedrijfsregel(Regel.R1369)
    private void controleerHuisnummerToevoeging(final List<MeldingElement> meldingen) {
        if (huisnummertoevoeging != null && isNietBAGAdres()) {
            meldingen.add(MeldingElement.getInstance(Regel.R1369, this));
        }
    }

    @Bedrijfsregel(Regel.R1371)
    private void controleerPostcode(final List<MeldingElement> meldingen) {
        if (postcode != null && isNietBAGAdres()) {
            meldingen.add(MeldingElement.getInstance(Regel.R1371, this));
        }
    }


    @Bedrijfsregel(Regel.R1374)
    @Bedrijfsregel(Regel.R2355)
    private void controleerDatumAanvangAdreshouding(final List<MeldingElement> meldingen) {
        if (datumAanvangAdreshouding != null) {
            if (!datumAanvangAdreshouding.isVolledigBekendeDatum()) {
                meldingen.add(MeldingElement.getInstance(Regel.R1374, this));
            }
            if (datumAanvangAdreshouding.getWaarde() > DatumUtil.vandaag()) {
                meldingen.add(MeldingElement.getInstance(Regel.R2355, this));
            }
        }
    }

    @Bedrijfsregel(Regel.R1650)
    @Bedrijfsregel(Regel.R1379)
    private void controleerGemeente(final List<MeldingElement> meldingen) {
        if (gemeenteCode != null) {
            final Gemeente gemeente = getDynamischeStamtabelRepository().getGemeenteByGemeentecode(gemeenteCode.getWaarde());
            if (gemeente == null) {
                meldingen.add(MeldingElement.getInstance(Regel.R1650, this));
            } else if (datumAanvangAdreshouding != null && !DatumUtil
                    .valtDatumBinnenPeriode(datumAanvangAdreshouding.getWaarde(), gemeente.getDatumAanvangGeldigheid(), gemeente.getDatumEindeGeldigheid())) {
                meldingen.add(MeldingElement.getInstance(Regel.R1379, this));
            }
        }
    }

    @Bedrijfsregel(Regel.R1924)
    @Bedrijfsregel(Regel.R2420)
    @Bedrijfsregel(Regel.R1926)
    private void controleerRedenWijziging(final List<MeldingElement> meldingen) {
        if (redenWijzigingCode != null) {
            final RedenWijzigingVerblijf redenWijzigingVerblijf = getDynamischeStamtabelRepository().getRedenWijzigingVerblijf(redenWijzigingCode.getWaarde());
            if (redenWijzigingVerblijf == null) {
                meldingen.add(MeldingElement.getInstance(Regel.R1924, this));
            } else if (controleerWaardenRedenWijziging(meldingen)) {
                if (isRedenWijzigingDoorPersoon() && aangeverAdreshoudingCode == null) {
                    meldingen.add(MeldingElement.getInstance(Regel.R1926, this));
                }
                if (!isRedenWijzigingDoorPersoon() && aangeverAdreshoudingCode != null) {
                    meldingen.add(MeldingElement.getInstance(Regel.R2420, this));
                }
            }

        }
    }


    @Bedrijfsregel(Regel.R1925)
    private void controleerAangever(final List<MeldingElement> meldingen) {
        if (aangeverAdreshoudingCode != null) {
            final Aangever aangever = getDynamischeStamtabelRepository().getAangeverByCode(aangeverAdreshoudingCode.getWaarde());
            if (aangever == null) {
                meldingen.add(MeldingElement.getInstance(Regel.R1925, this));
            }
        }

    }

    @Bedrijfsregel(Regel.R2364)
    private boolean controleerWaardenRedenWijziging(final List<MeldingElement> meldingen) {
        if (!isRedenWijzigingDoorPersoon() && !isRedenWijzigingAmbtshalve()) {
            meldingen.add(MeldingElement.getInstance(Regel.R2364, this));
            return false;
        }
        return true;
    }

    @Bedrijfsregel(Regel.R2309)
    private void controleerIdentificatiecodeNummerAanduiding(final List<MeldingElement> meldingen) {
        if (isBAGAdres() && identificatiecodeNummeraanduiding == null) {
            meldingen.add(MeldingElement.getInstance(Regel.R2309, this));
        }
    }

    @Bedrijfsregel(Regel.R2329)
    private void controleerIdentificatiecodeAdresseerbaarObject(final List<MeldingElement> meldingen) {
        if (isBAGAdres() && identificatiecodeAdresseerbaarObject == null) {
            meldingen.add(MeldingElement.getInstance(Regel.R2329, this));
        }
    }

    private boolean isRedenWijzigingAmbtshalve() {
        return redenWijzigingCode != null && REDEN_WIJZIGING_AMBTSHALVE == redenWijzigingCode.getWaarde();
    }

    private boolean isRedenWijzigingDoorPersoon() {
        return redenWijzigingCode != null && REDEN_WIJZIGING_DOOR_PERSOON == redenWijzigingCode.getWaarde();
    }


    private boolean isBAGAdres() {
        return getIdentificatiecodeAdresseerbaarObject() != null
                || getIdentificatiecodeNummeraanduiding() != null
                || getNaamOpenbareRuimte() != null
                || getWoonplaatsnaam() != null;
    }


    private boolean isNietBAGAdresOpenbareRuimte() {
        return !isBAGAdres() && getLocatieomschrijving() == null;
    }

    private boolean isNietBAGAdres() {
        return !isBAGAdres() && getLocatieomschrijving() != null;
    }


}

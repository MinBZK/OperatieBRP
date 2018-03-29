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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Plaats;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenBeeindigingRelatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;

/**
 * Een relatie uit het bijhoudingsbericht. NB: In het bericht mag bij registratie aanvang of einde in nederland geen
 * Landcode worden meegegeven. We gaan er bij de regels dan ook vanuit als landgebied(aanvang/einde)Code null is dat het
 * dan Nederland betreft.
 */
@XmlElement("relatie")
public final class RelatieGroepElement extends AbstractBmrGroep {

    private final DatumElement datumAanvang;
    private final StringElement gemeenteAanvangCode;
    private final StringElement woonplaatsnaamAanvang;
    private final CharacterElement redenEindeCode;
    private final DatumElement datumEinde;
    private final StringElement gemeenteEindeCode;
    private final StringElement woonplaatsnaamEinde;
    private final StringElement buitenlandsePlaatsAanvang;
    private final StringElement buitenlandseRegioAanvang;
    private final StringElement omschrijvingLocatieAanvang;
    private final StringElement landGebiedAanvangCode;
    private final StringElement buitenlandsePlaatsEinde;
    private final StringElement buitenlandseRegioEinde;
    private final StringElement omschrijvingLocatieEinde;
    private final StringElement landGebiedEindeCode;

    /**
     * Maakt een RelatieGroepElement object.
     * @param attributen attributen, mag niet null zijn
     * @param datumAanvang datum aanvang, mag niet null zijn
     * @param gemeenteAanvangCode gemeente aanvang code, mag niet null zijn
     * @param woonplaatsnaamAanvang woonplaatsnaam aanvang
     * @param redenEindeCode reden einde code
     * @param datumEinde datum einde
     * @param gemeenteEindeCode gemeente einde code
     * @param woonplaatsnaamEinde woonplaatsnaam einde
     * @param buitenlandsePlaatsAanvang buitenlandsePlaatsAanvang
     * @param buitenlandseRegioAanvang buitenlandseRegioAanvang
     * @param omschrijvingLocatieAanvang omschrijvingLocatieAanvang
     * @param landGebiedAanvangCode landGebiedAanvangCode
     * @param buitenlandsePlaatsEinde buitenlandsePlaatsEinde
     * @param buitenlandseRegioEinde buitenlandseRegioEinde
     * @param omschrijvingLocatieEinde omschrijvingLocatieEinde
     * @param landGebiedEindeCode landGebiedEindeCode
     */
    //
    public RelatieGroepElement(
            final Map<String, String> attributen,
            final DatumElement datumAanvang,
            final StringElement gemeenteAanvangCode,
            final StringElement woonplaatsnaamAanvang,
            final CharacterElement redenEindeCode,
            final DatumElement datumEinde,
            final StringElement gemeenteEindeCode,
            final StringElement woonplaatsnaamEinde,
            final StringElement buitenlandsePlaatsAanvang,
            final StringElement buitenlandseRegioAanvang,
            final StringElement omschrijvingLocatieAanvang,
            final StringElement landGebiedAanvangCode,
            final StringElement buitenlandsePlaatsEinde,
            final StringElement buitenlandseRegioEinde,
            final StringElement omschrijvingLocatieEinde,
            final StringElement landGebiedEindeCode) {
        super(attributen);
        this.redenEindeCode = redenEindeCode;
        this.datumEinde = datumEinde;
        this.gemeenteEindeCode = gemeenteEindeCode;
        this.woonplaatsnaamEinde = woonplaatsnaamEinde;
        this.datumAanvang = datumAanvang;
        this.gemeenteAanvangCode = gemeenteAanvangCode;
        this.woonplaatsnaamAanvang = woonplaatsnaamAanvang;
        this.buitenlandsePlaatsAanvang = buitenlandsePlaatsAanvang;
        this.buitenlandseRegioAanvang = buitenlandseRegioAanvang;
        this.omschrijvingLocatieAanvang = omschrijvingLocatieAanvang;
        this.landGebiedAanvangCode = landGebiedAanvangCode;
        this.buitenlandsePlaatsEinde = buitenlandsePlaatsEinde;
        this.buitenlandseRegioEinde = buitenlandseRegioEinde;
        this.omschrijvingLocatieEinde = omschrijvingLocatieEinde;
        this.landGebiedEindeCode = landGebiedEindeCode;
    }

    /**
     * Geef de waarde van datumAanvang.
     * @return datumAanvang datum aanvang
     */
    public DatumElement getDatumAanvang() {
        return datumAanvang;
    }

    /**
     * Geef de waarde van gemeenteAanvangCode.
     * @return gemeenteAanvangCode gemeente aanvang code
     */
    public StringElement getGemeenteAanvangCode() {
        return gemeenteAanvangCode;
    }

    /**
     * Geef de waarde van woonplaatsnaamAanvang.
     * @return woonplaatsnaamAanvang woonplaatsnaam aanvang
     */
    public StringElement getWoonplaatsnaamAanvang() {
        return woonplaatsnaamAanvang;
    }

    /**
     * Geef de waarde van redenEindeCode.
     * @return redenEindeCode reden einde code
     */
    public CharacterElement getRedenEindeCode() {
        return redenEindeCode;
    }

    /**
     * Geef de waarde van datumEinde.
     * @return datumEinde datum einde
     */
    public DatumElement getDatumEinde() {
        return datumEinde;
    }

    /**
     * Geef de waarde van gemeenteEindeCode.
     * @return gemeenteEindeCode gemeente einde code
     */
    public StringElement getGemeenteEindeCode() {
        return gemeenteEindeCode;
    }

    /**
     * Geef de waarde van woonplaatsnaamEinde.
     * @return woonplaatsnaamEinde woonplaatsnaam einde
     */
    public StringElement getWoonplaatsnaamEinde() {
        return woonplaatsnaamEinde;
    }

    /**
     * Geeft buitenlandse plaats aanvang.
     * @return the buitenlandse plaats aanvang
     */
    public StringElement getBuitenlandsePlaatsAanvang() {
        return buitenlandsePlaatsAanvang;
    }

    /**
     * Geeft buitenlandse regio aanvang.
     * @return the buitenlandse regio aanvang
     */
    public StringElement getBuitenlandseRegioAanvang() {
        return buitenlandseRegioAanvang;
    }

    /**
     * Geeft omschrijving locatie aanvang.
     * @return the omschrijving locatie aanvang
     */
    public StringElement getOmschrijvingLocatieAanvang() {
        return omschrijvingLocatieAanvang;
    }

    /**
     * Geeft land gebied aanvang code.
     * @return the land gebied aanvang code
     */
    public StringElement getLandGebiedAanvangCode() {
        return landGebiedAanvangCode;
    }

    /**
     * Geeft buitenlandse plaats einde.
     * @return the buitenlandse plaats einde
     */
    public StringElement getBuitenlandsePlaatsEinde() {
        return buitenlandsePlaatsEinde;
    }

    /**
     * Geeft buitenlandse regio einde.
     * @return the buitenlandse regio einde
     */
    public StringElement getBuitenlandseRegioEinde() {
        return buitenlandseRegioEinde;
    }

    /**
     * Geeft omschrijving locatie einde.
     * @return the omschrijving locatie einde
     */
    public StringElement getOmschrijvingLocatieEinde() {
        return omschrijvingLocatieEinde;
    }

    /**
     * Geeft land gebied einde code.
     * @return the land gebied einde code
     */
    public StringElement getLandGebiedEindeCode() {
        return landGebiedEindeCode;
    }

    @Bedrijfsregel(Regel.R1859)
    @Bedrijfsregel(Regel.R1860)
    @Override
    protected List<MeldingElement> valideerInhoud() {
        final List<MeldingElement> meldingen = new ArrayList<>();

        if (!getVerzoekBericht().isPrevalidatie()) {
            if (getDatumAanvang() != null && !getDatumAanvang().isVolledigBekendeDatum()) {
                meldingen.add(MeldingElement.getInstance(Regel.R1859, this));
            }
            if (getDatumEinde() != null && !getDatumEinde().isVolledigBekendeDatum()) {
                meldingen.add(MeldingElement.getInstance(Regel.R1860, this));
            }
            controleerDatumAanvangNietInToekomst(meldingen);
        }
        controleerWoonplaatsAanvang(meldingen);
        controleerWoonplaatsEinde(meldingen);
        valideerGemeenteAanvang(meldingen);
        valideerGemeenteEinde(meldingen);
        controleerDatumEinde(meldingen);
        controleerRedenEinde(meldingen);
        controleerLandGebiedEinde(meldingen);
        controleerLandGebiedAanvang(meldingen);
        controleerBuitenlandsePlaatsRegioEinde(meldingen);
        controleerOmschrijvingLocatieAanvang(meldingen);
        controleerAanvangRelatieBuitenland(meldingen);
        controleerBuitenlandsePlaatsEnRegio(meldingen);
        controleerGemeenteEnWoonplaatsnaamAanvangBijAanvangBuitenland(meldingen);
        controleerGemeenteEnWoonplaatsnaamEindeBijEindeBuitenland(meldingen);
        return meldingen;
    }

    @Bedrijfsregel(Regel.R2031)
    private void controleerGemeenteEnWoonplaatsnaamEindeBijEindeBuitenland(final List<MeldingElement> meldingen) {
        if (landGebiedEindeCode != null && !isEindeLandOfGebiedDefaultWaardeOfNederland() && (gemeenteEindeCode != null || woonplaatsnaamEinde != null)) {
            meldingen.add(MeldingElement.getInstance(Regel.R2031, this));
        }
    }

    @Bedrijfsregel(Regel.R2030)
    private void controleerGemeenteEnWoonplaatsnaamAanvangBijAanvangBuitenland(final List<MeldingElement> meldingen) {
        if (landGebiedAanvangCode != null && !isAanvangLandOfGebiedNederland() && (gemeenteAanvangCode != null || woonplaatsnaamAanvang != null)) {
            meldingen.add(MeldingElement.getInstance(Regel.R2030, this));
        }
    }

    @Bedrijfsregel(Regel.R1872)
    void controleerDatumAanvangNietInToekomst(final List<MeldingElement> meldingen) {
        if (getDatumAanvang() != null && getDatumAanvang().getWaarde() > DatumUtil.vandaag()) {
            meldingen.add(MeldingElement.getInstance(Regel.R1872, this));
        }
    }

    @Bedrijfsregel(Regel.R2048)
    private void controleerOmschrijvingLocatieAanvang(final List<MeldingElement> meldingen) {
        if (landGebiedAanvangCode != null && (isAanvangLandOfGebiedOnbekendOfInternationaal()) && getOmschrijvingLocatieAanvang() == null) {
            meldingen.add(MeldingElement.getInstance(Regel.R2048, this));
        }

    }

    @Bedrijfsregel(Regel.R1871)
    @Bedrijfsregel(Regel.R1873)
    @Bedrijfsregel(Regel.R2165)
    private void controleerLandGebiedAanvang(final List<MeldingElement> meldingen) {
        if (landGebiedAanvangCode != null && !getVerzoekBericht().getAdministratieveHandeling().getSoort().isCorrectie()) {
            if (isAanvangLandOfGebiedNederland()) {
                /*
                 * Regel R1873 heeft alleen betrekking op aangaan HGP in buitenland
                 * en alleen in dat geval is landGebiedAanvangCode gevuld. Dus in het geval
                 * dat de AH het aangaan van een HGP in NL betreft is landGebiedAanvangCode null
                 * en zal deze melding dus niet geretourneerd worden.
                 */
                meldingen.add(MeldingElement.getInstance(Regel.R1873, this));
            } else {
                final LandOfGebied landOfGebiedByCode = getDynamischeStamtabelRepository().getLandOfGebiedByCode(landGebiedAanvangCode.getWaarde());
                if (landOfGebiedByCode == null) {
                    meldingen.add(MeldingElement.getInstance(Regel.R2165, this));
                } else if (datumAanvang != null && !DatumUtil.valtDatumBinnenPeriode(
                        datumAanvang.getWaarde(),
                        landOfGebiedByCode.getDatumAanvangGeldigheid(),
                        landOfGebiedByCode.getDatumEindeGeldigheid())) {
                    meldingen.add(MeldingElement.getInstance(Regel.R1871, this));
                }
            }
        }
    }

    @Bedrijfsregel(Regel.R2180)
    @Bedrijfsregel(Regel.R1881)
    @Bedrijfsregel(Regel.R1877)
    private void controleerLandGebiedEinde(final List<MeldingElement> meldingen) {
        if (landGebiedEindeCode != null) {
            if (isEindeLandOfGebiedDefaultWaardeOfNederland() && !getVerzoekBericht().getAdministratieveHandeling().getSoort().isCorrectie()) {
                meldingen.add(MeldingElement.getInstance(Regel.R1877, this));
            } else {
                final LandOfGebied landOfGebiedByCode = getDynamischeStamtabelRepository().getLandOfGebiedByCode(landGebiedEindeCode.getWaarde());
                if (landOfGebiedByCode == null) {
                    meldingen.add(MeldingElement.getInstance(Regel.R2180, this));
                } else if (datumEinde != null && !DatumUtil.valtDatumBinnenPeriode(
                        datumEinde.getWaarde(),
                        landOfGebiedByCode.getDatumAanvangGeldigheid(),
                        landOfGebiedByCode.getDatumEindeGeldigheid())) {
                    meldingen.add(MeldingElement.getInstance(Regel.R1881, this));
                }
            }
        }
    }

    /**
     * Methode die controleerd of de redenEinde zit in de lijst met toegestane redenen voor een actie. Indien
     * redenEindeCode null is dan hebben we het niet over een relatie die bij een beeindiging hoort. Bij een beeindiging
     * is de redenEinde verplicht in de XSD.
     * @param toegestaneRedenen List
     * @return boolean true of false
     */
    boolean isRedenBeeindigingGeldig(final List<Character> toegestaneRedenen) {
        return redenEindeCode == null || toegestaneRedenen.contains(redenEindeCode.getWaarde());
    }

    @Bedrijfsregel(Regel.R1875)
    private void controleerDatumEinde(final List<MeldingElement> meldingen) {
        if (getDatumEinde() != null && getDatumEinde().getWaarde() > DatumUtil.vandaag()) {
            meldingen.add(MeldingElement.getInstance(Regel.R1875, this));
        }
    }

    @Bedrijfsregel(Regel.R2039)
    @Bedrijfsregel(Regel.R2151)
    private void controleerWoonplaatsAanvang(final List<MeldingElement> meldingen) {
        if (getGemeenteAanvangCode() == null && getWoonplaatsnaamAanvang() != null) {
            meldingen.add(MeldingElement.getInstance(Regel.R2039, this));
        }
        if (getWoonplaatsnaamAanvang() != null) {
            final Plaats plaats = getDynamischeStamtabelRepository().getPlaatsByPlaatsNaam(getWoonplaatsnaamAanvang().getWaarde());
            if (plaats == null) {
                meldingen.add(MeldingElement.getInstance(Regel.R2151, this));
            }
        }
    }

    @Bedrijfsregel(Regel.R2040)
    @Bedrijfsregel(Regel.R2178)
    private void controleerWoonplaatsEinde(final List<MeldingElement> meldingen) {
        if (getGemeenteEindeCode() == null && getWoonplaatsnaamEinde() != null) {
            meldingen.add(MeldingElement.getInstance(Regel.R2040, this));
        }
        if (getWoonplaatsnaamEinde() != null) {
            final Plaats plaats = getDynamischeStamtabelRepository().getPlaatsByPlaatsNaam(getWoonplaatsnaamEinde().getWaarde());
            if (plaats == null) {
                meldingen.add(MeldingElement.getInstance(Regel.R2178, this));
            }
        }
    }

    @Bedrijfsregel(Regel.R1870)
    @Bedrijfsregel(Regel.R2150)
    private void valideerGemeenteAanvang(final List<MeldingElement> meldingen) {
        if (getGemeenteAanvangCode() != null) {
            final Gemeente gemeente = getDynamischeStamtabelRepository().getGemeenteByGemeentecode(getGemeenteAanvangCode().getWaarde());

            if (gemeente == null) {
                meldingen.add(MeldingElement.getInstance(Regel.R2150, this));
            } else if (datumAanvang != null
                    && datumAanvang.isGeldigeKalenderDatum()
                    && !DatumUtil.valtDatumBinnenPeriode(
                    datumAanvang.getWaarde(),
                    gemeente.getDatumAanvangGeldigheid(),
                    gemeente.getDatumEindeGeldigheid())) {
                meldingen.add(MeldingElement.getInstance(Regel.R1870, this));
            }
        }
    }

    @Bedrijfsregel(Regel.R1880)
    @Bedrijfsregel(Regel.R2179)
    private void valideerGemeenteEinde(final List<MeldingElement> meldingen) {
        if (getGemeenteEindeCode() != null) {
            final Gemeente gemeente = getDynamischeStamtabelRepository().getGemeenteByGemeentecode(getGemeenteEindeCode().getWaarde());

            if (gemeente == null) {
                meldingen.add(MeldingElement.getInstance(Regel.R2179, this));
            } else if (datumEinde != null
                    && datumEinde.isGeldigeKalenderDatum()
                    && !DatumUtil.valtDatumBinnenPeriodeStreng(
                    datumEinde.getWaarde(),
                    gemeente.getDatumAanvangGeldigheid(),
                    gemeente.getDatumEindeGeldigheid())) {
                meldingen.add(MeldingElement.getInstance(Regel.R1880, this));
            }
        }
    }

    @Bedrijfsregel(Regel.R1853)
    private void controleerRedenEinde(final List<MeldingElement> meldingen) {
        if (redenEindeCode != null) {
            final RedenBeeindigingRelatie reden = getDynamischeStamtabelRepository().getRedenBeeindigingRelatieByCode(redenEindeCode.getWaarde());
            if (reden == null) {
                meldingen.add(MeldingElement.getInstance(Regel.R1853, this));
            }
        }
    }

    @Bedrijfsregel(Regel.R2020)
    @Bedrijfsregel(Regel.R2029)
    private void controleerBuitenlandsePlaatsRegioEinde(final List<MeldingElement> meldingen) {
        if (landGebiedEindeCode != null) {
            if (isEindeLandOfGebiedOnbekendOfInternationaalOfNederland()
                    && !ValidationUtils.zijnParametersAllemaalNull(getBuitenlandsePlaatsEinde(), getBuitenlandseRegioEinde())) {
                meldingen.add(MeldingElement.getInstance(Regel.R2029, this));
            }
            if (!isEindeLandOfGebiedOnbekendOfInternationaalOfNederland()
                    && !StringElement.heeftElementWaarde(getBuitenlandsePlaatsEinde())
                    && !StringElement.heeftElementWaarde(getBuitenlandseRegioEinde())) {
                meldingen.add(MeldingElement.getInstance(Regel.R2020, this));
            }
        }
    }

    @Bedrijfsregel(Regel.R1648)
    private void controleerAanvangRelatieBuitenland(final List<MeldingElement> meldingen) {
        // is er sprake van aanvang in buitenland?
        //en zijn zowel buitenlandseplaats en -regio aanvang niet gevuld?
        if (landGebiedAanvangCode != null && isAanvangLandOfGebiedBuitenland()
                && !StringElement.heeftElementWaarde(buitenlandsePlaatsAanvang) && !StringElement.heeftElementWaarde(buitenlandseRegioAanvang)) {
            meldingen.add(MeldingElement.getInstance(Regel.R1648, this));
        }
    }

    @Bedrijfsregel(Regel.R2028)
    private void controleerBuitenlandsePlaatsEnRegio(final List<MeldingElement> meldingen) {
        // is er GEEN sprake van aanvang in buitenland?
        //en is buitenlandseplaats of -regio aanvang gevuld?
        if (landGebiedAanvangCode != null && !isAanvangLandOfGebiedBuitenland()
                && (StringElement.heeftElementWaarde(buitenlandsePlaatsAanvang) || StringElement.heeftElementWaarde(buitenlandseRegioAanvang))) {
            meldingen.add(MeldingElement.getInstance(Regel.R2028, this));
        }
    }

    private boolean isEindeLandOfGebiedOnbekendOfInternationaalOfNederland() {
        return isEindeLandOfGebiedDefaultWaardeOfNederland() || isEindeLandOfGebiedOnbekend() || isEindeLandOfGebiedInternationaal();
    }

    /**
     * Is einde land of gebied onbekend of internationaal.
     * @return the boolean
     */
    boolean isEindeLandOfGebiedOnbekendOfInternationaal() {
        return isEindeLandOfGebiedOnbekend() || isEindeLandOfGebiedInternationaal();
    }

    /**
     * Is einde land of gebied de standaard waarde (nederland) of nederland.
     * @return true als het land/gebied de standaard waarde (null) heeft is nederland is.
     */
    private boolean isEindeLandOfGebiedDefaultWaardeOfNederland() {
        return landGebiedEindeCode == null || isEindeLandOfGebiedNederland();
    }

    private boolean isEindeLandOfGebiedOnbekend() {
        return LandOfGebied.CODE_ONBEKEND.equals(landGebiedEindeCode.getWaarde());
    }

    private boolean isEindeLandOfGebiedInternationaal() {
        return LandOfGebied.CODE_INTERNATIONAAL.equals(landGebiedEindeCode.getWaarde());
    }

    private boolean isAanvangLandOfGebiedOnbekendOfInternationaal() {
        return isAanvangLandOfGebiedOnbekend() || isAanvangLandOfGebiedInternationaal();
    }

    /**
     * Is Land/gebied aanvang relatie Nederland?
     * @return true als het land/gebied aanvang relatie Nederland is
     */
    boolean isAanvangLandOfGebiedNederland() {
        return LandOfGebied.CODE_NEDERLAND.equals(landGebiedAanvangCode.getWaarde());
    }

    /**
     * Is Land/gebied einde relatie Nederland?
     * @return true als het land/gebied einde relatie Nederland is
     */
    boolean isEindeLandOfGebiedNederland() {
        return LandOfGebied.CODE_NEDERLAND.equals(landGebiedEindeCode.getWaarde());
    }

    private boolean isAanvangLandOfGebiedOnbekend() {
        return LandOfGebied.CODE_ONBEKEND.equals(landGebiedAanvangCode.getWaarde());
    }

    private boolean isAanvangLandOfGebiedInternationaal() {
        return LandOfGebied.CODE_INTERNATIONAAL.equals(landGebiedAanvangCode.getWaarde());
    }

    private boolean isAanvangLandOfGebiedBuitenland() {
        return !isAanvangLandOfGebiedNederland() && !isAanvangLandOfGebiedOnbekend() && !isAanvangLandOfGebiedInternationaal();
    }

}

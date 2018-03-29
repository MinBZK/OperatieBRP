/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeboorteHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Plaats;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * De GeboorteElement voor een bijhoudingsbericht.
 */
@XmlElement("geboorte")
public final class GeboorteElement extends AbstractBmrGroep {

    private final DatumElement datum;
    private final StringElement gemeenteCode;
    private final StringElement woonplaatsnaam;
    private final StringElement buitenlandsePlaats;
    private final StringElement buitenlandseRegio;
    private final StringElement omschrijvingLocatie;
    private final StringElement landGebiedCode;

    /**
     * Maakt een GeboorteElement object.
     * @param attributen attributen
     * @param datum datum
     * @param gemeenteCode gemeenteCode
     * @param woonplaatsnaam woonplaatsnaam
     * @param buitenlandsePlaats buitenlandsePlaats
     * @param buitenlandseRegio buitenlandseRegio
     * @param omschrijvingLocatie omschrijvingLocatie
     * @param landGebiedCode landGebiedCode
     */
    //
    public GeboorteElement(
            final Map<String, String> attributen,
            final DatumElement datum,
            final StringElement gemeenteCode,
            final StringElement woonplaatsnaam,
            final StringElement buitenlandsePlaats,
            final StringElement buitenlandseRegio,
            final StringElement omschrijvingLocatie,
            final StringElement landGebiedCode) {
        super(attributen);
        this.datum = datum;
        this.gemeenteCode = gemeenteCode;
        this.woonplaatsnaam = woonplaatsnaam;
        this.buitenlandsePlaats = buitenlandsePlaats;
        this.buitenlandseRegio = buitenlandseRegio;
        this.omschrijvingLocatie = omschrijvingLocatie;
        this.landGebiedCode = landGebiedCode;
    }

    /**
     * Geef de waarde van datum.
     * @return datum
     */
    public DatumElement getDatum() {
        return datum;
    }

    /**
     * Geef de waarde van gemeenteCode.
     * @return gemeenteCode
     */
    public StringElement getGemeenteCode() {
        return gemeenteCode;
    }

    /**
     * Geef de waarde van woonplaatsnaam.
     * @return woonplaatsnaam
     */
    public StringElement getWoonplaatsnaam() {
        return woonplaatsnaam;
    }

    /**
     * Geef de waarde van buitenlandsePlaats.
     * @return buitenlandsePlaats
     */
    public StringElement getBuitenlandsePlaats() {
        return buitenlandsePlaats;
    }

    /**
     * Geef de waarde van buitenlandseRegio.
     * @return buitenlandseRegio
     */
    public StringElement getBuitenlandseRegio() {
        return buitenlandseRegio;
    }

    /**
     * Geef de waarde van omschrijvingLocatie.
     * @return omschrijvingLocatie
     */
    public StringElement getOmschrijvingLocatie() {
        return omschrijvingLocatie;
    }

    /**
     * Geef de waarde van landGebiedCode.
     * @return landGebiedCode
     */
    public StringElement getLandGebiedCode() {
        return landGebiedCode;
    }

    @Bedrijfsregel(Regel.R1469)
    @Bedrijfsregel(Regel.R2177)
    @Override
    protected List<MeldingElement> valideerInhoud() {
        if (getVoorkomenSleutel() != null) {
            // De enige keer dat er een voorkomen sleutel wordt gebruikt is bij correctie verval. Dan moet er niet gecontroleerd worden
            return VALIDATIE_OK;
        }

        final List<MeldingElement> meldingen = new ArrayList<>();
        if (isLandOfGebiedNederland() && !getDatum().isVolledigBekendeDatum()) {
            meldingen.add(MeldingElement.getInstance(Regel.R1469, this));
        }
        if (gemeenteCode != null) {
            controleerGemeenteBijGeboorte(meldingen);
        }
        controleerLandOfGebiedBijGeboorte(meldingen);
        controleerBuitenlandsePlaatsEnRegio(meldingen);
        controleerGemeenteWoonplaatsGeboorte(meldingen);
        controleerOmschrijvingLocatie(meldingen);
        if (woonplaatsnaam != null) {
            controleerPlaatsBijGeboorte(meldingen);
        }

        if (datum.getWaarde() > DatumUtil.vandaag()) {
            meldingen.add(MeldingElement.getInstance(Regel.R2177, this));
        }

        return meldingen;
    }

    @Bedrijfsregel(Regel.R2155)
    private void controleerPlaatsBijGeboorte(final List<MeldingElement> meldingen) {
        final Plaats plaatsNaam = getDynamischeStamtabelRepository().getPlaatsByPlaatsNaam(woonplaatsnaam.getWaarde());
        if (plaatsNaam == null) {
            meldingen.add(MeldingElement.getInstance(Regel.R2155, this));
        }
    }

    @Bedrijfsregel(Regel.R1466)
    @Bedrijfsregel(Regel.R2164)
    private void controleerGemeenteBijGeboorte(final List<MeldingElement> meldingen) {
        final Gemeente gemeente = getDynamischeStamtabelRepository().getGemeenteByGemeentecode(gemeenteCode.getWaarde());
        if (gemeente == null) {
            meldingen.add(MeldingElement.getInstance(Regel.R2164, this));
        } else if (!DatumUtil.valtDatumBinnenPeriodeStreng(
                getDatum().getWaarde(),
                gemeente.getDatumAanvangGeldigheid(),
                gemeente.getDatumEindeGeldigheid())) {
            meldingen.add(MeldingElement.getInstance(Regel.R1466, this));
        }
    }

    @Bedrijfsregel(Regel.R1467)
    @Bedrijfsregel(Regel.R1649)
    private void controleerLandOfGebiedBijGeboorte(final List<MeldingElement> meldingen) {
        if (landGebiedCode != null) {
            final LandOfGebied landOfGebied = getDynamischeStamtabelRepository().getLandOfGebiedByCode(landGebiedCode.getWaarde());
            if (landOfGebied == null) {
                meldingen.add(MeldingElement.getInstance(Regel.R1649, this));
            } else if (!DatumUtil.valtDatumBinnenPeriodeStreng(
                    getDatum().getWaarde(),
                    landOfGebied.getDatumAanvangGeldigheid(),
                    landOfGebied.getDatumEindeGeldigheid())) {
                meldingen.add(MeldingElement.getInstance(Regel.R1467, this));
            }
        }
    }

    @Bedrijfsregel(Regel.R2021)
    @Bedrijfsregel(Regel.R2032)
    private void controleerBuitenlandsePlaatsEnRegio(final List<MeldingElement> meldingen) {
        if (landGebiedCode != null) {
            if (buitenlandsePlaatsOfRegioOnGeldig()) {
                meldingen.add(MeldingElement.getInstance(Regel.R2021, this));
            }
            final boolean nietBuitenland = isLandOfGebiedNederland() || isLandOfGebiedOnbekend() || isLandOfGebiedInternationaal();
            if (nietBuitenland && (buitenlandsePlaats != null || buitenlandseRegio != null)) {
                meldingen.add(MeldingElement.getInstance(Regel.R2032, this));
            }
        }
    }

    @Bedrijfsregel(Regel.R2034)
    @Bedrijfsregel(Regel.R2036)
    @Bedrijfsregel(Regel.R2041)
    private void controleerGemeenteWoonplaatsGeboorte(final List<MeldingElement> meldingen) {
        final LandOfGebied
                landOfGebied =
                landGebiedCode == null ? null : getDynamischeStamtabelRepository().getLandOfGebiedByCode(landGebiedCode.getWaarde());

        if (landOfGebied != null) {
            if (!isLandOfGebiedNederland() && (gemeenteCode != null || woonplaatsnaam != null)) {
                meldingen.add(MeldingElement.getInstance(Regel.R2034, this));
            }
            if (isLandOfGebiedNederland() && gemeenteCode == null) {
                meldingen.add(MeldingElement.getInstance(Regel.R2041, this));
            }
        }
        if (gemeenteCode == null && woonplaatsnaam != null) {
            meldingen.add(MeldingElement.getInstance(Regel.R2036, this));
        }
    }

    @Bedrijfsregel(Regel.R2046)
    private void controleerOmschrijvingLocatie(final List<MeldingElement> meldingen) {
        if (controleOmschrijvingLocatieOnbekendOfInternationaal() || (isLandOfGebiedNederland() && omschrijvingLocatie != null)) {
            meldingen.add(MeldingElement.getInstance(Regel.R2046, this));
        }
    }

    private boolean controleOmschrijvingLocatieOnbekendOfInternationaal() {
        return (isLandOfGebiedOnbekend() || isLandOfGebiedInternationaal()) && omschrijvingLocatie == null;
    }

    private boolean buitenlandsePlaatsOfRegioOnGeldig() {
        boolean result = false;
        if (!isLandOfGebiedNederland() && !isLandOfGebiedOnbekend() && !isLandOfGebiedInternationaal()) {
            result = buitenlandsePlaats == null && buitenlandseRegio == null;
        }
        return result;
    }

    private boolean isLandOfGebiedNederland() {
        return landGebiedCode == null || LandOfGebied.CODE_NEDERLAND.equals(landGebiedCode.getWaarde());
    }

    private boolean isLandOfGebiedOnbekend() {
        return landGebiedCode != null && LandOfGebied.CODE_ONBEKEND.equals(landGebiedCode.getWaarde());
    }

    private boolean isLandOfGebiedInternationaal() {
        return landGebiedCode != null && LandOfGebied.CODE_INTERNATIONAAL.equals(landGebiedCode.getWaarde());
    }

    /**
     * Maakt een GeboorteElement o.b.v. een {@link PersoonGeboorteHistorie} voorkomen, of null als dit voorkomen null is.
     * @param voorkomen het voorkomen waarvan de gegevens gebruikt moeten worden om dit element te maken, mag null zijn
     * @param verzoekBericht het verzoek bericht waar dit element deel vanuit moet gaan maken
     * @return het nieuwe element
     */
    public static GeboorteElement getInstance(final PersoonGeboorteHistorie voorkomen, final BijhoudingVerzoekBericht verzoekBericht) {
        if (voorkomen == null) {
            return null;
        } else {
            final DatumElement datumElement;
            final StringElement landGebiedCodeElement;
            final StringElement gemeenteCodeElement;
            final StringElement woonplaatsnaamElement;
            final StringElement buitenlandsePlaatsElement;
            final StringElement buitenlandseRegioElement;
            final StringElement omschrijvingLocatieElement;

            //verplichte elementen
            datumElement = new DatumElement(voorkomen.getDatumGeboorte());
            landGebiedCodeElement = new StringElement(String.valueOf(voorkomen.getLandOfGebied().getCode()));
            //optionele elementen
            gemeenteCodeElement = voorkomen.getGemeente() == null ? null : new StringElement(String.valueOf(voorkomen.getGemeente().getCode()));
            woonplaatsnaamElement = voorkomen.getWoonplaatsnaamGeboorte() == null ? null : new StringElement(voorkomen.getWoonplaatsnaamGeboorte());
            buitenlandsePlaatsElement = voorkomen.getBuitenlandsePlaatsGeboorte() == null ? null : new StringElement(voorkomen.getBuitenlandsePlaatsGeboorte());
            buitenlandseRegioElement = voorkomen.getBuitenlandseRegioGeboorte() == null ? null : new StringElement(voorkomen.getBuitenlandseRegioGeboorte());
            omschrijvingLocatieElement =
                    voorkomen.getOmschrijvingGeboortelocatie() == null ? null : new StringElement(voorkomen.getOmschrijvingGeboortelocatie());

            final GeboorteElement
                    result =
                    new GeboorteElement(new AttributenBuilder().build(), datumElement, gemeenteCodeElement, woonplaatsnaamElement, buitenlandsePlaatsElement,
                            buitenlandseRegioElement, omschrijvingLocatieElement, landGebiedCodeElement);
            result.setVerzoekBericht(verzoekBericht);
            return result;
        }
    }
}

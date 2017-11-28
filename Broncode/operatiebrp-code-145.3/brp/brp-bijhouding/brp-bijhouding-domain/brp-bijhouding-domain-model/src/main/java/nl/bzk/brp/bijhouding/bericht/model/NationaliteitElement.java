/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Nationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;
import nl.bzk.brp.bijhouding.bericht.util.ValidatieHelper;

/**
 * Het nationaliteit element voor een bijhoudingsbericht.
 */
@XmlElement("nationaliteit")
public class NationaliteitElement extends AbstractBmrGroepReferentie<NationaliteitElement> implements BmrEntiteit<BijhoudingPersoonNationaliteit> {
    private static final Logger LOGGER = LoggerFactory.getLogger();
    private final StringElement nationaliteitCode;
    private final StringElement redenVerkrijgingCode;
    private final StringElement redenVerliesCode;
    private Integer datumAanvangGeldigheidRegistratieNationaliteitActie;
    private Integer datumEindeGeldigheidRegistratieNationaliteitActie;
    private BijhoudingPersoonNationaliteit bijhoudingPersoonNationaliteitEntiteit;

    /**
     * Maakt een NationaliteitElement object.
     * @param attributen de lijst met attributen
     * @param nationaliteitCode de code van de nationaliteit
     * @param redenVerkrijgingCode de code van reden verkrijging
     * @param redenVerliesCode de code van de reden verlies nationaliteit
     */
    public NationaliteitElement(final Map<String, String> attributen, final StringElement nationaliteitCode, final StringElement redenVerkrijgingCode,
                                final StringElement redenVerliesCode) {
        super(attributen);
        this.nationaliteitCode = nationaliteitCode;
        this.redenVerkrijgingCode = redenVerkrijgingCode;
        this.redenVerliesCode = redenVerliesCode;
    }

    @Override
    public Class<BijhoudingPersoonNationaliteit> getEntiteitType() {
        return BijhoudingPersoonNationaliteit.class;
    }

    @Override
    protected List<MeldingElement> valideerInhoud() {
        final List<MeldingElement> meldingen = new LinkedList<>();
        if (getReferentie() == null) {
            controleerNationaliteitCode(meldingen);
            controleerRedenVerkrijging(meldingen);
        }
        controleerRedenVerliesCode(meldingen);
        return meldingen;
    }

    @Bedrijfsregel(Regel.R1687)
    private void controleerRedenVerliesCode(final List<MeldingElement> meldingen) {
        if (getRedenVerliesCode() != null
                && getDynamischeStamtabelRepository().getRedenVerliesNLNationaliteitByCode(getRedenVerliesCode().getWaarde()) == null) {
            meldingen.add(MeldingElement.getInstance(Regel.R1687, this));
        }
    }

    @Bedrijfsregel(Regel.R1686)
    @Bedrijfsregel(Regel.R1688)
    private void controleerRedenVerkrijging(final List<MeldingElement> meldingen) {
        if (getRedenVerkrijgingCode() != null) {
            if (getDynamischeStamtabelRepository().getRedenVerkrijgingNLNationaliteitByCode(getRedenVerkrijgingCode().getWaarde()) == null) {
                meldingen.add(MeldingElement.getInstance(Regel.R1686, this));
            }
        } else {
            if (Nationaliteit.NEDERLANDSE.equals(BmrAttribuut.getWaardeOfNull(getNationaliteitCode()))) {
                meldingen.add(MeldingElement.getInstance(Regel.R1688, this));
            }
        }
    }

    @Bedrijfsregel(Regel.R1685)
    private void controleerNationaliteitCode(final List<MeldingElement> meldingen) {
        if (getNationaliteitCode() != null
                && getDynamischeStamtabelRepository().getNationaliteitByNationaliteitcode(BmrAttribuut.getWaardeOfNull(getNationaliteitCode())) == null) {
            meldingen.add(MeldingElement.getInstance(Regel.R1685, this));
        }
    }

    /**
     * Geeft de waarde van de nationaliteit code
     * @return de nationaliteit code
     */
    public StringElement getNationaliteitCode() {
        return getElement().nationaliteitCode;
    }

    /**
     * Geeft de nationaliteit code terug als {@link String}. Als dit element een objectsleutel heeft, dan wordt de code uit de bestaande nationaliteit gehaald,
     * anders uit dit element of het element waar naar toe wordt gerefereerd.
     * @return de nationaliteit code
     */
    String getNationaliteitCodeAsString() {
        final String code;
        if (heeftObjectSleutel()) {
            code = getEntiteit() == null ? null : getEntiteit().getNationaliteit().getCode();
        } else {
            code = getNationaliteitCode().getWaarde();
        }
        return code;
    }

    /**
     * Geeft de reden verkrijging terug
     * @return de code reden verkrijging
     */
    public StringElement getRedenVerkrijgingCode() {
        return getElement().redenVerkrijgingCode;
    }

    /**
     * geef redenVerliesCode terug.
     * @return StringElement met waarde redenVerliesCode
     */
    public StringElement getRedenVerliesCode() {
        // Reden verlies kan op de referentie zitten. Dit eerst controleren
        return redenVerliesCode != null ? redenVerliesCode : getElement().redenVerliesCode;
    }

    public boolean isNederlandse() {
        if (getEntiteit() != null) {
            return getEntiteit().getNationaliteit().isNederlandse();
        } else {
            return Nationaliteit.NEDERLANDSE.equals(BmrAttribuut.getWaardeOfNull(getNationaliteitCode()));
        }
    }

    @Override
    public NationaliteitElement getReferentie() {
        return (NationaliteitElement) getGroep();
    }

    @Override
    public boolean verwijstNaarBestaandEnJuisteType() {
        return getReferentieId() == null || getGroep() instanceof NationaliteitElement;
    }

    private NationaliteitElement getElement() {
        return getReferentie() != null ? getReferentie() : this;
    }

    /**
     * geeft de datum aanvang geldigheid terug. wordt gebruikt als dit element onderdeel is van de actie registratieNationaliteit
     * @return datum aanvang geldigheid gezet in de actie
     */
    public Integer getDatumAanvangGeldigheidRegistratieNationaliteitActie() {
        return getElement().datumAanvangGeldigheidRegistratieNationaliteitActie;
    }

    /**
     * geeft de datum einde geldigheid terug.
     * wordt gebruikt als dit element onderdeel is van de actie beeindigingNationaliteit
     * @return datum einde geldigheid gezet in de actie
     */
    public Integer getDatumEindeGeldigheidRegistratieNationaliteitActie() {
        return datumEindeGeldigheidRegistratieNationaliteitActie;
    }

    /**
     * set de datum aanvang geldigheid van de bovenliggende actie.
     * @param datum datum aanvang geldigheid
     */
    public void setDatumAanvangGeldigheidRegistratieNationaliteitActie(int datum) {
        datumAanvangGeldigheidRegistratieNationaliteitActie = datum;
    }

    /**
     * set de datum einde geldigheid van de bovenliggende actie.
     * @param datum datum einde geldigheid
     */
    public void setDatumEindeGeldigheidRegistratieNationaliteitActie(int datum) {
        datumEindeGeldigheidRegistratieNationaliteitActie = datum;
    }

    /**
     * Geeft de {@link BijhoudingPersoonNationaliteit} terug die bij de meegegeven object sleutel hoort. Null als dit element geen object sleutel heeft.
     * @return een {@link BijhoudingPersoonNationaliteit} of null als er geen object sleutel is.
     */
    @Override
    public BijhoudingPersoonNationaliteit getEntiteit() {
        return bijhoudingPersoonNationaliteitEntiteit;
    }

    /**
     * Bepaalt de {@link BijhoudingPersoonNationaliteit} bij de meegeven {@link BijhoudingPersoon} adhv de object sleutel.
     */
    void bepaalBijhoudingPersoonNationaliteitEntiteit(final BijhoudingPersoon bijhoudingPersoonEntiteit) {
        ValidatieHelper.controleerOpNullWaarde(bijhoudingPersoonEntiteit, "bijhoudingspersoonEntiteit");

        if (heeftObjectSleutel()) {
            try {
                final long teZoekenId = Long.parseLong(getObjectSleutel());
                bijhoudingPersoonNationaliteitEntiteit = BijhoudingPersoonNationaliteit.decorate(
                        bijhoudingPersoonEntiteit.getPersoonNationaliteitSet().stream()
                                .filter(nationaliteit -> Objects.equals(nationaliteit.getId(), teZoekenId))
                                .findFirst().orElse(null));
            } catch (final NumberFormatException nfe) {
                LOGGER.info("Ongeldige objectsleutel", nfe);
            }
        }
    }

    /**
     * Geeft aan of dit element een beeindiging is van een nationaliteit.
     * @return true als het een beeindiging is van een nationaliteit
     */
    public boolean isBeeindiging() {
        return datumEindeGeldigheidRegistratieNationaliteitActie != null;
    }

    /**
     * Geeft aan of dit element een registratie is van een nationaliteit.
     * @return true als het een registratie is van een nationaliteit
     */
    public boolean isRegistratie() {
        return datumAanvangGeldigheidRegistratieNationaliteitActie != null;
    }
}

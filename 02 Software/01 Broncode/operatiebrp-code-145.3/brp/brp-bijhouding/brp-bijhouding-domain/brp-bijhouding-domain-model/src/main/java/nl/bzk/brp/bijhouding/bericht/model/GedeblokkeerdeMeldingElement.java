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
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMelding;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;
import nl.bzk.brp.bijhouding.bericht.util.ValidatieHelper;

/**
 * Het GedeblokkeerdeMeldingElement uit het bijhouding berichtenmodel.
 */
@XmlElement("gedeblokkeerdeMelding")
public final class GedeblokkeerdeMeldingElement extends AbstractBmrGroepReferentie<BmrGroep> {
    /**
     * Standaard tekst voor een regel melding indien de regelcode niet bestaat.
     */
    private static final String NVT = "Niet van toepassing.";

    private final StringElement regelCode;
    private final StringElement melding;

    /**
     * Maakt een GedeblokkeerdeMeldingElement object.
     * @param basisAttribuutGroep de basis attribuutgroep
     * @param regelCode regelCode
     * @param melding melding
     */
    public GedeblokkeerdeMeldingElement(final Map<String, String> basisAttribuutGroep, final StringElement regelCode, final StringElement melding) {
        super(basisAttribuutGroep);
        ValidatieHelper.controleerOpNullWaarde(regelCode, "regelCode");
        this.regelCode = regelCode;
        this.melding = melding;
    }

    /**
     * Geef de waarde van regelCode.
     * @return regelCode
     */
    public StringElement getRegelCode() {
        return regelCode;
    }

    /**
     * Geef de waarde van melding.
     * @return melding
     */
    public StringElement getMelding() {
        return melding;
    }

    @Override
    public BmrGroep getReferentie() {
        return getGroep();
    }

    @Override
    public boolean verwijstNaarBestaandEnJuisteType() {
        return getGroep() != null;
    }

    @Bedrijfsregel(Regel.R2428)
    @Override
    protected List<MeldingElement> valideerInhoud() {
        final List<MeldingElement> meldingen = new ArrayList<>();
        final Regel regel = getRegel(regelCode.getWaarde());
        if (regel == null || !SoortMelding.DEBLOKKEERBAAR.equals(regel.getSoortMelding())) {
            meldingen.add(MeldingElement.getInstance(Regel.R2428, this));
        }
        return meldingen;
    }

    /**
     * Geeft een {@link GedeblokkeerdeMeldingElement} terug die geschikt is voor een {@link BijhoudingAntwoordBericht}.
     * @param element een {@link GedeblokkeerdeMeldingElement} wat als basis dient voor de nieuwe instantie.
     * @return een {@link GedeblokkeerdeMeldingElement} voor een {@link BijhoudingAntwoordBericht}
     */
    public static GedeblokkeerdeMeldingElement getInstanceVoorAntwoord(final GedeblokkeerdeMeldingElement element) {
        final Map<String, String> attributen = new AttributenBuilder().objecttype("GedeblokkeerdeMelding").referentieId(element.getCommunicatieId()).build();
        final Regel regel = getRegel(element.regelCode.getWaarde());
        return new GedeblokkeerdeMeldingElement(attributen, element.regelCode, StringElement.getInstance(regel != null ? regel.getMelding() : NVT));
    }

    private static Regel getRegel(final String waarde) {
        if (Regel.bestaatCode(waarde)) {
            return Regel.parseCode(waarde);
        }
        return null;
    }
}

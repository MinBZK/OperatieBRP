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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIDHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;

/**
 * De IdentificatienummersElement voor een bijhoudingsbericht.
 */
@XmlElement("identificatienummers")
@Bedrijfsregel(Regel.R1585)
@Bedrijfsregel(Regel.R1587)
public final class IdentificatienummersElement extends AbstractBmrGroep {

    private static final int BSN_LENGTE = 9;
    private static final int ELF = 11;
    private static final int VIJF = 5;
    public static final int ANR_FACTOR = 2;

    private final StringElement burgerservicenummer;
    private final StringElement administratienummer;

    /**
     * Maakt een IdentificatienummersElement object.
     * @param attributen attributen
     * @param burgerservicenummer burgerservicenummer
     * @param administratienummer administratienummer
     */
    public IdentificatienummersElement(
            final Map<String, String> attributen,
            final StringElement burgerservicenummer,
            final StringElement administratienummer) {
        super(attributen);
        this.burgerservicenummer = burgerservicenummer;
        this.administratienummer = administratienummer;
    }

    /**
     * Geef de waarde van burgerservicenummer.
     * @return burgerservicenummer
     */
    public StringElement getBurgerservicenummer() {
        return burgerservicenummer;
    }

    /**
     * Geef de waarde van administratienummer.
     * @return administratienummer
     */
    public StringElement getAdministratienummer() {
        return administratienummer;
    }

    @Bedrijfsregel(Regel.R2458)
    @Override
    protected List<MeldingElement> valideerInhoud() {
        final List<MeldingElement> meldingen = new ArrayList<>();
        if (!getVerzoekBericht().getAdministratieveHandeling().getSoort().isCorrectie() && getAdministratienummer() == null
                && getBurgerservicenummer() == null) {
            meldingen.add(MeldingElement.getInstance(Regel.R2458, this));
        }
        if (getAdministratienummer() != null) {
            controleerANummer(meldingen);
        }
        if (getBurgerservicenummer() != null) {
            controleerBSN(meldingen);
        }
        return meldingen;
    }

    /**
     * Maakt een nieuw IdentificatienummersElement object.
     * @param burgerservicenummer burgerservicenummer
     * @param administratienummer administratienummer
     * @return IdentificatienummersElement
     */
    public static IdentificatienummersElement getInstance(final String burgerservicenummer, final String administratienummer) {
        return new IdentificatienummersElement(
                new AttributenBuilder().build(),
                burgerservicenummer == null ? null : new StringElement(burgerservicenummer),
                administratienummer == null ? null : new StringElement(administratienummer));
    }

    /**
     * Maakt een nieuw IdentificatienummersElement object of null als voorkomen null is.
     * @param voorkomen het persoon id voorkomen waarvan de gegevens moeten worden overgenomen in dit element, mag null zijn
     * @param verzoekBericht het verzoekbericht waar dit element deel vanuit moet maken
     * @return een nieuw element
     */
    public static IdentificatienummersElement getInstance(final PersoonIDHistorie voorkomen, final BijhoudingVerzoekBericht verzoekBericht) {
        if (voorkomen == null) {
            return null;
        } else {
            final IdentificatienummersElement
                    result =
                    IdentificatienummersElement.getInstance(voorkomen.getBurgerservicenummer(), voorkomen.getAdministratienummer());
            result.setVerzoekBericht(verzoekBericht);
            return result;
        }
    }

    private void controleerBSN(final List<MeldingElement> meldingen) {
        final String bsn = getBurgerservicenummer().getWaarde();
        if (BSN_LENGTE != bsn.length()) {
            meldingen.add(MeldingElement.getInstance(Regel.R1587, this));
        } else {
            int som = 0;
            for (int i = 0; i < bsn.length() - 1; i++) {
                som += Character.getNumericValue(bsn.charAt(i)) * (BSN_LENGTE - i);
            }
            som += Character.getNumericValue(bsn.charAt(bsn.length() - 1)) * -1;
            if (som % ELF != 0) {
                meldingen.add(MeldingElement.getInstance(Regel.R1587, this));
            }
        }
    }

    private void controleerANummer(final List<MeldingElement> meldingen) {
        final String aNummer = getAdministratienummer().getWaarde();
        int simpeleOptelling = 0;
        int complexeOptelling = 0;
        int factor = 1;
        if (aNummer.startsWith("0")) {
            meldingen.add(MeldingElement.getInstance(Regel.R1585, this));
        } else {
            for (int i = 0; i < aNummer.length(); i++) {
                final int num = Character.getNumericValue(aNummer.charAt(i));
                if (i < aNummer.length() - 1 && aNummer.charAt(i) == aNummer.charAt(i + 1)) {
                    meldingen.add(MeldingElement.getInstance(Regel.R1585, this));
                    break;
                }
                simpeleOptelling += num;
                complexeOptelling += factor * num;
                factor *= ANR_FACTOR;
            }
            if (meldingen.isEmpty()) {
                final int smod11 = simpeleOptelling % ELF;
                final int cmod11 = complexeOptelling % ELF;
                if ((smod11 != 0 && smod11 != VIJF) || cmod11 != 0) {
                    meldingen.add(MeldingElement.getInstance(Regel.R1585, this));
                }
            }
        }

    }
}

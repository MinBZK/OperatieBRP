/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;
import nl.bzk.brp.bijhouding.bericht.util.ValidatieHelper;

/**
 * Het VoornaamElement voor een bijhoudingsbericht.
 */
@XmlElement("voornaam")
public final class VoornaamElement extends AbstractBmrGroep {

    /**
     * Sorteert de lijst van Voornamen o.b.v. het volgnummer.
     */
    static final Comparator<VoornaamElement> COMPARATOR = Comparator.comparingInt(persoonVoornaam -> persoonVoornaam.getVolgnummer().getWaarde());

    private final IntegerElement volgnummer;
    private final StringElement naam;

    /**
     * Maakt een VoornaamElement object.
     * @param attributen attributen
     * @param volgnummer volgnummer
     * @param naam naam
     */
    public VoornaamElement(
            final Map<String, String> attributen,
            final IntegerElement volgnummer,
            final StringElement naam) {
        super(attributen);
        ValidatieHelper.controleerOpNullWaarde(volgnummer, "volgnummer");
        ValidatieHelper.controleerOpNullWaarde(naam, "naam");
        this.volgnummer = volgnummer;
        this.naam = naam;
    }

    /**
     * Geef de waarde van volgnummer.
     * @return volgnummer
     */
    public IntegerElement getVolgnummer() {
        return volgnummer;
    }

    /**
     * Geef de waarde van naam.
     * @return naam
     */
    public StringElement getNaam() {
        return naam;
    }

    @Override
    protected List<MeldingElement> valideerInhoud() {
        return VALIDATIE_OK;
    }

}

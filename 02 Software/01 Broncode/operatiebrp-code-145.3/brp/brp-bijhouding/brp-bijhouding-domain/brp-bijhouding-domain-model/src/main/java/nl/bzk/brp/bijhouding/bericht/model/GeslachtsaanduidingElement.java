/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsaanduidingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Geslachtsaanduiding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * De GeslachtsaanduidingCodeElement voor een bijhoudingsbericht.
 */
@XmlElement("geslachtsaanduiding")
public final class GeslachtsaanduidingElement extends AbstractBmrGroep {

    private final StringElement code;

    /**
     * Maakt een GeslachtsaanduidingCodeElement object.
     * @param attributen attributen
     * @param code code
     */
    public GeslachtsaanduidingElement(final Map<String, String> attributen, final StringElement code) {
        super(attributen);
        this.code = code;
    }

    /**
     * Geef de waarde van code.
     * @return code
     */
    public StringElement getCode() {
        return code;
    }

    @Override
    @Bedrijfsregel(Regel.R1569)
    protected List<MeldingElement> valideerInhoud() {
        if (getVoorkomenSleutel() != null) {
            // De enige keer dat er een voorkomen sleutel wordt gebruikt is bij correctie verval. Dan moet er niet gecontroleerd worden
            return VALIDATIE_OK;
        }
        final List<MeldingElement> meldingen = new ArrayList<>();
        if (!bestaatGeslachtsaanduiding()) {
            meldingen.add(MeldingElement.getInstance(Regel.R1569, this));
        }
        return meldingen;
    }

    private boolean bestaatGeslachtsaanduiding() {
        return Geslachtsaanduiding.bestaatCode(getCode().getWaarde());
    }

    /**
     * Maakt een GeslachtsaanduidingElement o.b.v. een {@link PersoonGeslachtsaanduidingHistorie} voorkomen, of null als dit voorkomen null is.
     * @param voorkomen het voorkomen waarvan de gegevens gebruikt moeten worden om dit element te maken, mag null zijn
     * @param verzoekBericht het verzoek bericht waar dit element deel vanuit moet gaan maken
     * @return het nieuwe element
     */
    public static GeslachtsaanduidingElement getInstance(final PersoonGeslachtsaanduidingHistorie voorkomen, final BijhoudingVerzoekBericht verzoekBericht) {
        if (voorkomen == null) {
            return null;
        } else {
            final GeslachtsaanduidingElement
                    result =
                    new GeslachtsaanduidingElement(new AttributenBuilder().build(), new StringElement(voorkomen.getGeslachtsaanduiding().getCode()));
            result.setVerzoekBericht(verzoekBericht);
            return result;
        }
    }
}

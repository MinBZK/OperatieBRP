/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;
import nl.bzk.brp.bijhouding.bericht.util.ValidatieHelper;

/**
 * Het MeldingElement uit het bijhouding berichtenmodel.
 */
@XmlElement("melding")
public final class MeldingElement extends AbstractBmrGroepReferentie<BmrGroep> {

    private static final String OBJECTTYPE = "Melding";

    private final StringElement regelCode;
    private final StringElement soortNaam;
    private final StringElement melding;

    // Onderstaand komt niet terug in de XML.
    private final Regel regel;

    /**
     * Maakt een MeldingElement object.
     * @param attributen de lijst met attributen
     * @param regelCode regelCode
     * @param soortNaam soortNaam
     * @param melding melding
     */
    public MeldingElement(
            final Map<String, String> attributen,
            final StringElement regelCode,
            final StringElement soortNaam,
            final StringElement melding) {
        this(attributen, regelCode, soortNaam, melding, null);
    }

    private MeldingElement(
            final Map<String, String> attributen,
            final StringElement regelCode,
            final StringElement soortNaam,
            final StringElement melding,
            final Regel regel) {
        super(attributen);
        ValidatieHelper.controleerOpNullWaarde(regelCode, "regelCode");
        ValidatieHelper.controleerOpNullWaarde(soortNaam, "soortNaam");
        ValidatieHelper.controleerOpNullWaarde(melding, "melding");
        this.regelCode = regelCode;
        this.soortNaam = soortNaam;
        this.melding = melding;
        this.regel = regel;
    }

    /**
     * Geef de waarde van regelCode.
     * @return regelCode
     */
    public StringElement getRegelCode() {
        return regelCode;
    }

    /**
     * Geef de waarde van soortNaam.
     * @return soortNaam
     */
    public StringElement getSoortNaam() {
        return soortNaam;
    }

    /**
     * Geef de waarde van melding.
     * @return melding
     */
    public StringElement getMelding() {
        return melding;
    }

    @Override
    protected List<MeldingElement> valideerInhoud() {
        return VALIDATIE_OK;
    }

    @Override
    public BmrGroep getReferentie() {
        return getGroep();
    }

    @Override
    public boolean verwijstNaarBestaandEnJuisteType() {
        // Groep kan nooit null zijn vanwege de builder, dus deze methode kan nooit false geven.
        return true;
    }


    /**
     * Geeft de {@link Regel} terug waarop deze MeldingElement is gemaakt.
     * @return de regel
     */
    public Regel getRegel() {
        return regel;
    }

    /**
     * Maakt een nieuw MeldingElement. Als de groep gevuld is moet ook het communicatieId gevuld zijn. Deze wordt dan
     * als referentieId gebruikt in de melding. Als de groep null is wordt het referentieId leeg gelaten.
     * @param regel de regel
     * @param groep de groep
     * @return een nieuw melding element
     */
    public static MeldingElement getInstance(final Regel regel, final BmrGroep groep) {
        ValidatieHelper.controleerOpNullWaarde(groep, "groep");
        ValidatieHelper.controleerOpNullWaarde(groep.getCommunicatieId(), "groep.communicatieId");
        final MeldingElement result =
                new MeldingElement(
                        new AttributenBuilder().objecttype(OBJECTTYPE).referentieId(groep.getCommunicatieId()).referentieId(groep.getCommunicatieId()).build(),
                        new StringElement(regel.getCode()),
                        new StringElement(regel.getSoortMelding().getNaam()),
                        new StringElement(regel.getMelding()),
                        regel);
        result.initialiseer(Collections.singletonMap(groep.getCommunicatieId(), groep));
        return result;
    }
}

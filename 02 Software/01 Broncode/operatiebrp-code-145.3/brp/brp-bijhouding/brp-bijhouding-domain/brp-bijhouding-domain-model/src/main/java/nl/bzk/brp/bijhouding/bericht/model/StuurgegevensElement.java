/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;
import nl.bzk.brp.bijhouding.bericht.util.ValidatieHelper;

/**
 * De StuurgegevensElement voor een bijhoudingsbericht.
 */
@XmlElement("stuurgegevens")
public final class StuurgegevensElement extends AbstractBmrGroep {

    private final StringElement zendendePartij;
    private final StringElement zendendeSysteem;
    private final StringElement ontvangendePartij;
    private final StringElement referentienummer;
    private final StringElement crossReferentienummer;
    private final DatumTijdElement tijdstipVerzending;

    /**
     * Maakt een StuurgegevensElement object.
     * @param attributen attributen
     * @param zendendePartij zendendePartij
     * @param zendendeSysteem zendendeSysteem
     * @param ontvangendePartij ontvangendePartij
     * @param referentienummer referentienummer
     * @param crossReferentienummer crossReferentienummer
     * @param tijdstipVerzending tijdstipVerzending
     */
    //
    public StuurgegevensElement(
            final Map<String, String> attributen,
            final StringElement zendendePartij,
            final StringElement zendendeSysteem,
            final StringElement ontvangendePartij,
            final StringElement referentienummer,
            final StringElement crossReferentienummer,
            final DatumTijdElement tijdstipVerzending) {
        super(attributen);
        ValidatieHelper.controleerOpNullWaarde(zendendePartij, "zendendePartij");
        ValidatieHelper.controleerOpNullWaarde(zendendeSysteem, "zendendeSysteem");
        ValidatieHelper.controleerOpNullWaarde(referentienummer, "referentienummer");
        ValidatieHelper.controleerOpNullWaarde(tijdstipVerzending, "tijdstipVerzending");
        this.zendendePartij = zendendePartij;
        this.zendendeSysteem = zendendeSysteem;
        this.ontvangendePartij = ontvangendePartij;
        this.referentienummer = referentienummer;
        this.crossReferentienummer = crossReferentienummer;
        this.tijdstipVerzending = tijdstipVerzending;
    }

    /**
     * Geef de waarde van zendendePartij.
     * @return zendendePartij
     */
    public StringElement getZendendePartij() {
        return zendendePartij;
    }

    /**
     * Geef de waarde van zendendeSysteem.
     * @return zendendeSysteem
     */
    public StringElement getZendendeSysteem() {
        return zendendeSysteem;
    }

    /**
     * Geef de waarde van ontvangendePartij.
     * @return ontvangendePartij
     */
    public StringElement getOntvangendePartij() {
        return ontvangendePartij;
    }

    /**
     * Geef de waarde van referentienummer.
     * @return referentienummer
     */
    public StringElement getReferentienummer() {
        return referentienummer;
    }

    /**
     * Geef de waarde van crossReferentienummer.
     * @return crossReferentienummer
     */
    public StringElement getCrossReferentienummer() {
        return crossReferentienummer;
    }

    /**
     * Geef de waarde van tijdstipVerzending.
     * @return tijdstipVerzending
     */
    public DatumTijdElement getTijdstipVerzending() {
        return tijdstipVerzending;
    }

    @Override
    protected List<MeldingElement> valideerInhoud() {
        return VALIDATIE_OK;
    }

    /**
     * Maakt een StuurgegevensElement object.
     * @param zendendePartij zendendePartij
     * @param zendendeSysteem zendendeSysteem
     * @param ontvangendePartij ontvangendePartij
     * @param crossReferentienummer crossReferentienummer
     * @param tijdstipVerzending tijdstipVerzending
     * @return StuurgegevensElement
     */
    public static StuurgegevensElement getInstance(
            final String zendendePartij,
            final String zendendeSysteem,
            final String ontvangendePartij,
            final String crossReferentienummer,
            final ZonedDateTime tijdstipVerzending) {
        return new StuurgegevensElement(
                new AttributenBuilder().build(),
                StringElement.getInstance(zendendePartij),
                StringElement.getInstance(zendendeSysteem),
                StringElement.getInstance(ontvangendePartij),
                StringElement.getInstance(UUID.randomUUID().toString()),
                StringElement.getInstance(crossReferentienummer),
                tijdstipVerzending == null ? null : new DatumTijdElement(tijdstipVerzending));
    }
}

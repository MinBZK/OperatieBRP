/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;
import nl.bzk.brp.bijhouding.bericht.util.ValidatieHelper;

/**
 * Het AfgeleidAdministratiefElement.
 */
@XmlElement("afgeleidAdministratief")
public final class AfgeleidAdministratiefElement extends AbstractBmrGroep {

    private final DatumTijdElement tijdstipLaatsteWijziging;

    /**
     * Maakt een {@link AfgeleidAdministratiefElement} object.
     *
     * @param attributen attributen, mag niet null zijn
     * @param tijdstipLaatsteWijziging tijdstipLaatsteWijziging, mag niet null zijn
     */
    public AfgeleidAdministratiefElement(final Map<String, String> attributen, final DatumTijdElement tijdstipLaatsteWijziging) {
        super(attributen);
        ValidatieHelper.controleerOpNullWaarde(tijdstipLaatsteWijziging, "tijdstipLaatsteWijziging");
        this.tijdstipLaatsteWijziging = tijdstipLaatsteWijziging;
    }

    /**
     * Geeft het tijdstip laatste wijziging terug.
     * 
     * @return tijdstip laatste wijziging
     */
    public DatumTijdElement getTijdstipLaatsteWijziging() {
        return tijdstipLaatsteWijziging;
    }

    @Override
    protected List<MeldingElement> valideerInhoud() {
        return VALIDATIE_OK;
    }

    /**
     * Maakt een nieuw AfgeleidAdministratiefElement object.
     *
     * @param tijdstipLaatsteWijziging tijdstipLaatsteWijziging
     * @return AfgeleidAdministratiefElement
     */
    public static AfgeleidAdministratiefElement getInstance(final ZonedDateTime tijdstipLaatsteWijziging) {
        return new AfgeleidAdministratiefElement(new AttributenBuilder().build(), new DatumTijdElement(tijdstipLaatsteWijziging));
    }
}

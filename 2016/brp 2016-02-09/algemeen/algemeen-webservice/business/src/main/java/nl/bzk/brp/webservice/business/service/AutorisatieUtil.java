/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.business.service;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.util.AutorisatieOffloadGegevens;

/**
 * Utiliteitsklasse ten behoeve van autorisatie.
 */
class AutorisatieUtil {

    private AutorisatieUtil() {
    }

    private static boolean isPartijGeautoriseerd(final Partij teAutoriserenPartij, final Partij zendendePartij,
        final boolean isZelfGeautoriseerdePartij)
    {
        return (zendendePartij != null && zendendePartij.getOIN().getWaarde().equals(teAutoriserenPartij
            .getOIN().getWaarde())) || (isZelfGeautoriseerdePartij && zendendePartij == null);
    }

    static void verifieerAutorisatie(final Object gevondenAutorisatie, final boolean isOndertekenaarGeautoriseerd, final boolean
        isTransporteurGeautoriseerd)
        throws AutorisatieException
    {
        if (gevondenAutorisatie == null && isOndertekenaarGeautoriseerd && isTransporteurGeautoriseerd) {
            throw new AutorisatieException(Regel.R1257);
        } else if (!isOndertekenaarGeautoriseerd) {
            throw new AutorisatieException(Regel.R2121);
        } else if (!isTransporteurGeautoriseerd) {
            throw new AutorisatieException(Regel.R2122);
        }
    }

    static AutorisatieResultaat bepaalAutorisatieResultaat(final Partij ondertekenaar, final Partij transporteur,
        final AutorisatieOffloadGegevens offloadGegevens,
        boolean isZelfOndertekenaar,
        boolean isZelfTransporteur)
    {
        if (ondertekenaar == null && transporteur == null) {
            if (isZelfOndertekenaar && isZelfTransporteur) {
                return AutorisatieResultaat.geautoriseerdResultaat();
            }
        }

        AutorisatieResultaat autorisatieResultaat;
        final boolean isOndertekenaarGeautoriseerd = isPartijGeautoriseerd(offloadGegevens.getOndertekenaar(), ondertekenaar, isZelfOndertekenaar);
        final boolean isTransporteurGeautoriseerd = isPartijGeautoriseerd(offloadGegevens.getTransporteur(), transporteur, isZelfTransporteur);
        if (isOndertekenaarGeautoriseerd && isTransporteurGeautoriseerd) {
            autorisatieResultaat = AutorisatieResultaat.geautoriseerdResultaat();
        } else {
            autorisatieResultaat = new AutorisatieResultaat(isOndertekenaarGeautoriseerd, isTransporteurGeautoriseerd);
        }
        return autorisatieResultaat;
    }

    static class AutorisatieResultaat {
        private final boolean isOndertekenaarGeautoriseerd;
        private final boolean isTransporteurGeautoriseerd;

        private AutorisatieResultaat(final boolean isOndertekenaarGeautoriseerd, final boolean isTransporteurGeautoriseerd) {
            this.isOndertekenaarGeautoriseerd = isOndertekenaarGeautoriseerd;
            this.isTransporteurGeautoriseerd = isTransporteurGeautoriseerd;
        }

        boolean isGeautoriseerd() {
            return isOndertekenaarGeautoriseerd && isTransporteurGeautoriseerd;
        }

        boolean isOndertekenaarGeautoriseerd() {
            return isOndertekenaarGeautoriseerd;
        }

        boolean isTransporteurGeautoriseerd() {
            return isTransporteurGeautoriseerd;
        }

        private static AutorisatieResultaat geautoriseerdResultaat() {
            return new AutorisatieResultaat(true, true);
        }
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.proces;

import java.util.Iterator;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AdministratiefGegeven;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.DeltaEntiteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.FormeleHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.GegevenInOnderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Onderzoek;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;

/**
 * Container voor mappen van bestaande aan nieuw Onderzoek.
 */
final class OnderzoekPaar {
    private Onderzoek bestaandOnderzoek;
    private Onderzoek nieuwOnderzoek;
    private OnderzoekDeltaStatus status;

    /**
     * Constructor waarbij zowel bestaand als nieuw onderzoek aan meegegeven worden. Nieuw onderzoek mag null zijn.
     *
     * @param bestaandOnderzoek
     *            bestaand onderzoek
     * @param nieuwOnderzoek
     *            nieuw onderzoek
     */
    OnderzoekPaar(final Onderzoek bestaandOnderzoek, final Onderzoek nieuwOnderzoek) {
        this.bestaandOnderzoek = bestaandOnderzoek;
        this.nieuwOnderzoek = nieuwOnderzoek;
        if (bestaandOnderzoek == null) {
            status = OnderzoekDeltaStatus.NIEUW;
        } else {
            if (nieuwOnderzoek != null) {
                if (bestaandOnderzoek.isInhoudelijkGelijk(nieuwOnderzoek)) {
                    status = OnderzoekDeltaStatus.ONGEWIJZIGD;
                } else {
                    status = OnderzoekDeltaStatus.GEWIJZIGD;
                }
            } else {
                status = OnderzoekDeltaStatus.VERWIJDERD;
            }
        }
    }

    public Onderzoek getBestaandOnderzoek() {
        return bestaandOnderzoek;
    }

    public Onderzoek getNieuwOnderzoek() {
        return nieuwOnderzoek;
    }

    public OnderzoekDeltaStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Onderzoek " + status;
    }

    private String getOmschrijving() {
        return bestaandOnderzoek != null ? bestaandOnderzoek.getOmschrijving() : nieuwOnderzoek.getOmschrijving();
    }

    /**
     * Bepaalt of het onderzoek een
     * {@link nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortAdministratieveHandeling#GBA_BIJHOUDING_ACTUEEL}
     * of een
     * {@link nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortAdministratieveHandeling#GBA_BIJHOUDING_OVERIG}
     * is. Het kan alleen
     * {@link nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortAdministratieveHandeling#GBA_BIJHOUDING_ACTUEEL}
     * worden als het voorkomen in BRP dat in onderzoek staat een actueel historisch voorkomen is of een A-laag
     * voorkomen en geen onderdeel uit maakt van {@link AdministratiefGegeven}. De bepaling of de bijhouding actueel is,
     * vindt plaats op het nieuwe onderzoek. Als er geen nieuw onderzoek is (bv onderzoek is verwijderd), dan wordt er
     * naar het bestaande onderzoek gekeken.
     *
     * Indien het Onderzoek voortkomt uit een historisch Lo3 voorkomen betreft de bijhouding ok niet actueel
     * 
     * @return true als bijhouding actueel is.
     */
    public boolean isBijhoudingActueel() {
        final Onderzoek onderzoek = nieuwOnderzoek != null ? nieuwOnderzoek : bestaandOnderzoek;
        final String onderzoekOmschrijving = getOmschrijving();
        boolean isActueel = true;
        String melding = "";
        final Iterator<GegevenInOnderzoek> gegevenInOnderzoekIterator = onderzoek.getGegevenInOnderzoekSet().iterator();
        while (isActueel && gegevenInOnderzoekIterator.hasNext()) {
            final GegevenInOnderzoek gegevenInOnderzoek = gegevenInOnderzoekIterator.next();
            final DeltaEntiteit entiteit = gegevenInOnderzoek.getObjectOfVoorkomen();

            if (entiteit instanceof AdministratiefGegeven) {
                isActueel = false;
                melding = "onderzoek is op administratieve BRP gegevens";
            } else if (entiteit instanceof FormeleHistorie && !((FormeleHistorie) entiteit).isActueel()) {
                isActueel = false;
                melding = "onderzoek is op niet actuele BRP gegevens";
            } else if (onderzoek.isVoortgekomenUitNietActueelVoorkomen()) {
                isActueel = false;
                melding = "onderzoek is afkomstig van historisch LO3 vookomen";
            }
        }

        if (!OnderzoekDeltaStatus.ONGEWIJZIGD.equals(status)) {
            final String volledigeMelding =
                    String.format("Onderzoek: %s; is bijhouding actueel? %b (reden: %s)", onderzoekOmschrijving, isActueel, melding);
            SynchronisatieLogging.addMelding(volledigeMelding);
        }
        return isActueel;
    }
}

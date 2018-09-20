/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.synchronisatie.dto.synchronisatie;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import nl.bzk.brp.model.basis.SynchroniseerbaarStamgegeven;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.webservice.business.stappen.BerichtVerwerkingsResultaatImpl;

/**
 * Model klasse voor een antwoord op een synchronisatie verzoek.
 */
public class SynchronisatieResultaat extends BerichtVerwerkingsResultaatImpl {

    /**
     * Stamgegeven voor synchronisatie stamgegevens verzoeken.
     */
    private List<SynchroniseerbaarStamgegeven> stamgegeven;

    /**
     * Persoon id's van te archiveren personen voor synchronisatie persoon verzoeken.
     */
    private Set<Integer> teArchiverenPersoonIdsInkomendBericht = new HashSet<>();

    /**
     * Constructor.
     *
     * @param meldingen Een lijst om meldingen op te kunnen slaan.
     */
    public SynchronisatieResultaat(final List<Melding> meldingen) {
        super(meldingen);
    }

    /**
     * Retourneert de synchronisatie stam gegevens.
     *
     * @return Lijst met de waarden (records) voor het stamgegeven.
     */
    public final List<SynchroniseerbaarStamgegeven> getStamgegeven() {
        return stamgegeven;
    }

    /**
     * Zet de synchronisatie stamgegeven.
     *
     * @param stamgegeven waarden van het stamgegeven.
     */
    public final void setStamgegeven(final List<SynchroniseerbaarStamgegeven> stamgegeven) {
        this.stamgegeven = stamgegeven;
    }

    @Override
    public final Set<Integer> getTeArchiverenPersonenIngaandBericht() {
        return teArchiverenPersoonIdsInkomendBericht;
    }
}

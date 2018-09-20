/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.afnemerindicaties;

public class AbonnementMetPartij {
    int abonnementId;
    int partijId;

    public AbonnementMetPartij(final int abonnementId, final int partijId) {
        this.partijId = partijId;
        this.abonnementId = abonnementId;
    }

    public int getAbonnementId() {
        return abonnementId;
    }

    public int getPartijId() {
        return partijId;
    }
}

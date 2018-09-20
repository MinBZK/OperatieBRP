/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.webservice.business.stappen.BerichtVerwerkingsResultaatImpl;


/**
 * Resultaat klasse die de meldingen bevat die worden teruggeven op het verzoek om een afnemerindicatie te plaatsen of verwijderen via de webservice
 * onderhoud afnemerindicaties.
 */
public class OnderhoudAfnemerindicatiesResultaat extends BerichtVerwerkingsResultaatImpl {

    /**
     * Persoon id's van te archiveren personen voor synchronisatie persoon verzoeken.
     */
    private Set<Integer> teArchiverenPersoonIdsInkomendBericht = new HashSet<>();

    private Date tijdstipRegistratie;

    /**
     * Constructor.
     *
     * @param meldingen Een lijst om meldingen op te kunnen slaan.
     */
    public OnderhoudAfnemerindicatiesResultaat(final List<Melding> meldingen) {
        super(meldingen);
    }

    @Override
    public final Set<Integer> getTeArchiverenPersonenIngaandBericht() {
        return teArchiverenPersoonIdsInkomendBericht;
    }

    /**
     * Retourneert het tijdstip van registratie.
     *
     * @return het tijdstip van registratie.
     */
    public final Date getTijdstipRegistratie() {
        // Retourneer een "defensive copy", daar Date een mutable object is.
        if (tijdstipRegistratie == null) {
            return null;
        } else {
            return new Date(tijdstipRegistratie.getTime());
        }
    }

    /**
     * Zet het tijdstip van registratie.
     *
     * @param tijdstipRegistratie het tijdstip van registratie.
     */
    public final void setTijdstipRegistratie(final Date tijdstipRegistratie) {
        // Gebruik een "defensive copy", daar Date een mutable object is.
        if (tijdstipRegistratie == null) {
            this.tijdstipRegistratie = null;
        } else {
            this.tijdstipRegistratie = new Date(tijdstipRegistratie.getTime());
        }
    }
}

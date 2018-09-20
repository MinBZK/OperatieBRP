/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.migratie.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import nl.moderniseringgba.isc.migratie.domein.entities.Gemeente;
import nl.moderniseringgba.isc.migratie.repository.GemeenteRepository;

import org.springframework.stereotype.Component;

/**
 * Gemeente service implementatie.
 */
@Component
public final class GemeenteServiceImpl implements GemeenteService {

    private final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

    @Inject
    private GemeenteRepository repository;

    @Override
    public Stelsel geefStelselVoorGemeente(final int gemeenteCode) {
        final Gemeente gemeente = repository.findGemeente(gemeenteCode);

        if (gemeente == null || gemeente.getDatumBrp() == null) {
            return Stelsel.GBA;
        } else {
            final int datumTijd = Integer.parseInt(format.format(new Date()));
            return datumTijd > gemeente.getDatumBrp() ? Stelsel.BRP : Stelsel.GBA;
        }
    }
}

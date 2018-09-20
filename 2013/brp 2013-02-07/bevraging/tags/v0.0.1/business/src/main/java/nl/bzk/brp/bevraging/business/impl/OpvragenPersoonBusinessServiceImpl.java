/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.impl;

import java.math.BigDecimal;

import javax.inject.Inject;

import nl.bzk.brp.bevraging.business.OpvragenPersoonBusinessService;
import nl.bzk.brp.bevraging.business.antwoord.Antwoord;
import nl.bzk.brp.bevraging.business.vraag.Vraag;
import nl.bzk.brp.bevraging.domein.Persoon;
import nl.bzk.brp.bevraging.domein.repository.PersoonRepository;

import org.springframework.stereotype.Service;


/**
 * Standaard implementatie van de {@link OpvragenPersoonBusinessService} interface, welke middels de data access laag
 * persoonsgegevens ophaalt op basis van verschillende zoek criteria. (PUC500)
 */
@Service
public class OpvragenPersoonBusinessServiceImpl implements OpvragenPersoonBusinessService {

    @Inject
    private PersoonRepository persoonRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public final Antwoord<Persoon> opvragenPersoonOpBasisVanBsn(final Vraag<BigDecimal> vraag) {
        Antwoord<Persoon> antwoord = new Antwoord<Persoon>();

        if (vraag.getVraag() != null) {
            Persoon persoon = persoonRepository.zoekOpBSN(vraag.getVraag().intValue());
            antwoord.setResultaat(persoon);
        }

        return antwoord;
    }

}

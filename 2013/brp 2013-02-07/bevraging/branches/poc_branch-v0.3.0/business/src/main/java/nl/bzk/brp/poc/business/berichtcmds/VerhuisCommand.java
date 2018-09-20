/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.business.berichtcmds;

import javax.inject.Inject;

import nl.bzk.brp.bevraging.business.berichtcmds.AbstractBerichtCommand;
import nl.bzk.brp.bevraging.business.dto.BerichtContext;
import nl.bzk.brp.poc.business.dto.antwoord.BijhoudingResultaat;
import nl.bzk.brp.poc.business.dto.verzoek.VerhuisVerzoek;
import nl.bzk.brp.poc.dal.PersoonAdresRepository;
import nl.bzk.brp.poc.domein.PocPersoonAdres;

/**
 * Het verhuis command dat overeenkomt met een verhuisbericht. In dit command wordt de verhuizing van de in het
 * bericht aanwezige persoon naar het tevens in het bericht aanwezige adres, doorgevoerd. Hiervoor wordt het nieuwe
 * adres opgeslagen, waarna de Data Access Laag de rest (zoals beeindigen huidig adres) voor zijn rekening neemt.
 */
public class VerhuisCommand extends AbstractBerichtCommand<VerhuisVerzoek, BijhoudingResultaat> {

    @Inject
    private PersoonAdresRepository persoonAdresRepository;

    /**
     * Standaard constructor die het verzoek en de context zet.
     */
    public VerhuisCommand(final VerhuisVerzoek verzoek, final BerichtContext context) {
        super(verzoek, context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void voerUit(final BijhoudingResultaat antwoord) {
        PocPersoonAdres adres = this.getVerzoek().getNieuweSituatie().getAdressen().iterator().next();
        persoonAdresRepository.opslaanNieuwPersoonAdres(this.getVerzoek().getBijhoudingContext(), adres);
    }

}

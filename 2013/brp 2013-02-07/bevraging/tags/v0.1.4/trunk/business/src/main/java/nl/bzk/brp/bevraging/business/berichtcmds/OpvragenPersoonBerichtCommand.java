/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.berichtcmds;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Named;

import nl.bzk.brp.bevraging.business.dto.BrpBerichtContext;
import nl.bzk.brp.bevraging.business.dto.antwoord.PersoonZoekCriteriaAntwoord;
import nl.bzk.brp.bevraging.business.dto.verzoek.PersoonZoekCriteria;
import nl.bzk.brp.bevraging.domein.Persoon;
import nl.bzk.brp.bevraging.domein.ber.SoortBericht;
import nl.bzk.brp.bevraging.domein.repository.PersoonRepository;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * Bericht command voor het ophalen van persoonsgegevens op basis van verschillende zoek criteria. (PUC500)
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Named("OpvragenPersoonBerichtCommand")
public class OpvragenPersoonBerichtCommand extends
        AbstractBrpBerichtCommand<PersoonZoekCriteria, PersoonZoekCriteriaAntwoord>
{

    @Inject
    private PersoonRepository persoonRepository;

    /**
     * Standaard constructor waarbij het verzoek en de context van het bericht direct worden geinitialiseerd.
     *
     * @param verzoek het verzoek object van het bericht.
     * @param context de context van het bericht.
     */
    public OpvragenPersoonBerichtCommand(final PersoonZoekCriteria verzoek, final BrpBerichtContext context) {
        super(verzoek, context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void voerUit() {
        assert getVerzoek() != null;

        if (getVerzoek().getBsn() != null) {
            Collection<Persoon> personen = persoonRepository.findByBurgerservicenummer(getVerzoek().getBsn());

            PersoonZoekCriteriaAntwoord persoonZoekCriteriaAntwoord = new PersoonZoekCriteriaAntwoord(personen);

            setAntwoord(persoonZoekCriteriaAntwoord);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SoortBericht getSoortBericht() {
        return SoortBericht.OPVRAGEN_PERSOON_VRAAG;
    }
}

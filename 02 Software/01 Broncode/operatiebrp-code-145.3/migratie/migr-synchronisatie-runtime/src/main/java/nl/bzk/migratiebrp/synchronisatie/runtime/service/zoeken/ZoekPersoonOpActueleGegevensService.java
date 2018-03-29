/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.zoeken;

import java.util.List;

import javax.inject.Inject;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ZoekPersoonAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ZoekPersoonOpActueleGegevensVerzoekBericht;
import nl.bzk.migratiebrp.synchronisatie.dal.service.PersoonService;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.SynchronisatieBerichtService;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Zoek persoon op actuele gegevens.
 */
public final class ZoekPersoonOpActueleGegevensService
        implements SynchronisatieBerichtService<ZoekPersoonOpActueleGegevensVerzoekBericht, ZoekPersoonAntwoordBericht> {

    private final PersoonService persoonDalService;
    private final ZoekPersoonFilter filter;

    /**
     * Constructor.
     * @param persoonDalService persoon dal service
     * @param filter filter
     */
    @Inject
    public ZoekPersoonOpActueleGegevensService(final PersoonService persoonDalService, final ZoekPersoonFilter filter) {
        this.persoonDalService = persoonDalService;
        this.filter = filter;
    }

    /*
         * (non-Javadoc)
         *
         * @see nl.bzk.migratiebrp.synchronisatie.runtime.service.SynchronisatieBerichtService#getVerzoekType()
         */
    @Override
    public Class<ZoekPersoonOpActueleGegevensVerzoekBericht> getVerzoekType() {
        return ZoekPersoonOpActueleGegevensVerzoekBericht.class;
    }

    @Override
    @Transactional(value = "syncDalTransactionManager", propagation = Propagation.REQUIRED, readOnly = true,
            noRollbackFor = IllegalArgumentException.class)
    public ZoekPersoonAntwoordBericht verwerkBericht(final ZoekPersoonOpActueleGegevensVerzoekBericht verzoek) throws BerichtSyntaxException {
        final List<Persoon> personen =
                persoonDalService.zoekPersonenOpActueleGegevens(verzoek.getANummer(), verzoek.getBsn(), verzoek.getGeslachtsnaam(), verzoek.getPostcode());

        final List<GevondenPersoon> gevondenPersonen = filter.filter(personen, verzoek.getAanvullendeZoekcriteria());

        return ZoekPersoonServiceUtil.maakZoekPersoonAntwoord(verzoek, gevondenPersonen);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.synchronisatie.runtime.service.SynchronisatieBerichtService#getServiceNaam()
     */
    @Override
    public String getServiceNaam() {
        return "ZoekPersoonOpActueleGegevensService";
    }
}

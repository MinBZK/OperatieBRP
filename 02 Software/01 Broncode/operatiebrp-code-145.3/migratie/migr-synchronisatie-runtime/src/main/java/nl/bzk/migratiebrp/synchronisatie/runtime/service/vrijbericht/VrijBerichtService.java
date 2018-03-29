/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.vrijbericht;

import javax.inject.Inject;
import nl.bzk.brp.gba.domain.vrijbericht.VrijBerichtOpdracht;
import nl.bzk.brp.gba.domain.vrijbericht.VrijBerichtQueue;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VrijBerichtAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VrijBerichtVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigePersoonslijstException;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.SynchronisatieBerichtService;

/**
 * Service voor Vrij Bericht.
 */
public class VrijBerichtService implements SynchronisatieBerichtService<VrijBerichtVerzoekBericht, VrijBerichtAntwoordBericht> {

    private final VrijBerichtNaarBrpVerzender verzender;

    /**
     * Constructor.
     * @param verzender verzender welke bericht naar BRP verstuurd
     */
    @Inject
    public VrijBerichtService(VrijBerichtNaarBrpVerzender verzender) {
        this.verzender = verzender;
    }

    @Override
    public Class<VrijBerichtVerzoekBericht> getVerzoekType() {
        return VrijBerichtVerzoekBericht.class;
    }

    @Override
    public VrijBerichtAntwoordBericht verwerkBericht(final VrijBerichtVerzoekBericht verzoek) throws OngeldigePersoonslijstException, BerichtSyntaxException {
        Logging.initContext();
        try {
            VrijBerichtOpdracht opdracht = new VrijBerichtOpdracht();
            opdracht.setVerzendendePartijCode(verzoek.getVerzendendePartij());
            opdracht.setOntvangendePartijCode(verzoek.getOntvangendePartij());
            opdracht.setReferentienummer(verzoek.getReferentienummer());
            opdracht.setBericht(verzoek.getBericht());
            verzender.verstuurVrijBericht(opdracht, VrijBerichtQueue.VERZOEK.getQueueNaam(), verzoek.getMessageId());
        } finally {
            Logging.destroyContext();
        }
        return null;
    }

    @Override
    public String getServiceNaam() {
        return this.getClass().getSimpleName();
    }
}

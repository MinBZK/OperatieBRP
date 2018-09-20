/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.monitoring;

import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtVerwerkingsFoutZwaarte;
import nl.bzk.brp.bevraging.ws.service.model.Antwoord;
import nl.bzk.brp.bevraging.ws.service.model.OpvragenPersoonAntwoord;
import nl.bzk.brp.bevraging.ws.service.model.OpvragenPersoonAntwoordFout;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

/**
 * Interceptor om berichten met fouten te tellen.
 *
 */
public class ErrorAntwoordOutInterceptor extends AbstractPhaseInterceptor<Message> {

    @Inject
    private BerichtenMBean berichtenLijst;

    /**
     * Constructor.
     */
    public ErrorAntwoordOutInterceptor() {
        super(Phase.PRE_LOGICAL);
    }

    @Override
    public void handleMessage(final Message message) {
        @SuppressWarnings("unchecked")
        List<Antwoord> antwoorden = message.getContent(List.class);

        for (Antwoord antwoord : antwoorden) {
            if (antwoord instanceof OpvragenPersoonAntwoord) {
                if (((OpvragenPersoonAntwoord) antwoord).getAantalFouten() > 0) {
                    for (OpvragenPersoonAntwoordFout fout : ((OpvragenPersoonAntwoord) antwoord)
                            .getOpvragenPersoonAntwoordFout())
                    {
                        if (BerichtVerwerkingsFoutZwaarte.INFO.toString().equals(fout.getToelichting())) {
                            berichtenLijst.telOpFoutenInfo();
                        } else if (BerichtVerwerkingsFoutZwaarte.WAARSCHUWING.toString().equals(fout.getToelichting()))
                        {
                            berichtenLijst.telOpFoutenWaarschuwing();
                        } else if (BerichtVerwerkingsFoutZwaarte.FOUT.toString().equals(fout.getToelichting())) {
                            berichtenLijst.telOpFoutenFout();
                        } else if (BerichtVerwerkingsFoutZwaarte.SYSTEEM.toString().equals(fout.getToelichting())) {
                            berichtenLijst.telOpFoutenSysteem();
                        }
                    }
                }
            }
        }

    }
}

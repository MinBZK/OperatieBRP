/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen;

import javax.inject.Inject;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.Resultaat;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.ResultaatMelding;
import nl.bzk.brp.configuratie.BrpBusinessConfiguratie;
import nl.bzk.brp.locking.BrpLocker;
import nl.bzk.brp.locking.BrpLockerExceptie;
import org.springframework.stereotype.Component;


/**
 * Kan een logisch lock op persoonId's verkrijgen en releasen.
 */
@Component
public class LockStap {

    private static final String FOUTMELDING = "De personen in bericht met ID %d worden gelijktijdig door"
                                                + " een ander bericht geraadpleegd of gemuteerd";
    @Inject
    private BrpBusinessConfiguratie brpBusinessConfiguratie;

    @Inject
    private BrpLocker           brpLocker;

    /**
     * <b>Let op:</b> Deze stap plaatst de locks en voegt de betreffende connectie toe aan de ThreadLocal om verder in
     * het systeem nog gebruikt te worden en om in de naverwerking van deze stap netjes deze connectie te kunnen
     * sluiten. In de naverwerking dient de connectie dan ook gesloten te worden en van de ThreadLocal verwijderd te
     * worden.
     * @param context BijhoudingBerichtContext
     * @return Resultaat
     */
    public Resultaat vergrendel(final BijhoudingBerichtContext context) {
        Resultaat resultaat = Resultaat.LEEG;

        try {
            brpLocker.lock(context.getLockingIds(), context.getLockingElement(), context.getLockingMode(),
                    getLockingTimeout());
        } catch (BrpLockerExceptie e) {
            resultaat = resultaat.voegToe(ResultaatMelding.builder()
                .withMeldingTekst(String.format(FOUTMELDING, context.getIngaandBerichtId()))
                .build());
        }

        return resultaat;
    }

    /**
     * De naverwerking van deze stap dient er voor te zorgen dat de connectie (en de locks) die tijdens de verwerking
     * van de stap zijn aangemaakt, netjes worden opgeruimd. Hiervoor roept het de {@link BrpLocker#unLock()} methode
     * aan, daar deze correct de connectie uit de ThreadLocal haalt en netjes afsluit (inclusief commit of rollback in
     * het geval van fouten).
     * <p>
     * Deze methode wordt na uitvoering van deze stap (en alle volgende stappen in het proces) uitgevoerd.
     * </p>
     */
    public void ontgrendel() {
        brpLocker.unLock();
    }

    /**
     * Geeft de max. wachttijd in sec. aan hoe lang er gewacht moet worden om het lock te verkrijgen
     *
     * @return de maximale locking timeout tijd
     */
    private int getLockingTimeout() {
        return brpBusinessConfiguratie.getLockingTimeOutProperty();
    }
}

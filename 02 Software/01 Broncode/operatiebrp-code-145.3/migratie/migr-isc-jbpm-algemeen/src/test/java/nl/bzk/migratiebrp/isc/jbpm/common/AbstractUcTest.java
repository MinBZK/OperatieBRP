/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common;

import java.util.UUID;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Lq01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.OngeldigeSyntaxBericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Pf01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Pf02Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Pf03Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesPartijRegisterVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.OngeldigBericht;

public abstract class AbstractUcTest extends AbstractJbpmTest {

    public AbstractUcTest(final String processDefinitionXml) {
        super(processDefinitionXml);
    }

    protected String generateMessageId() {
        return UUID.randomUUID().toString();
    }

    /* *** ONGELDIG ************************************************************************************** */

    protected SyncBericht maakOngeldigBericht(final SyncBericht verzoek) {
        final SyncBericht result = new OngeldigBericht("<ongeldig/>", "Ongeldig bericht");
        result.setMessageId(generateMessageId());
        result.setCorrelationId(verzoek.getMessageId());

        return result;
    }

    protected Lo3Bericht maakOngeldigeSyntaxBericht(final Lo3Bericht verzoek) {
        final Lo3Bericht result = new OngeldigeSyntaxBericht("<ongeldig/>", "Ongeldig bericht");
        result.setMessageId(generateMessageId());
        result.setCorrelationId(verzoek.getMessageId());

        return result;
    }

    /* *** ONVERWACHT ************************************************************************************** */

    /**
     * Huidige implementatie: LeesPartijRegisterVerzoek. Dat verwacht (behalve het voisc control proces) een ISC
     * proces nooit als input of antwoord bericht.
     */
    protected SyncBericht maakOnverwachtBericht(final SyncBericht verzoek) {
        final SyncBericht result = new LeesPartijRegisterVerzoekBericht();
        result.setMessageId(generateMessageId());
        result.setCorrelationId(verzoek.getMessageId());

        return result;
    }

    /**
     * Huidige implementatie: Lq01. Dat verwacht een ISC proces nooit als input of antwoord bericht.
     */
    protected Lo3Bericht maakOnverwachtBericht(final Lo3Bericht verzoek) {
        final Lq01Bericht result = new Lq01Bericht();
        result.setANummer("1231231232");
        result.setMessageId(generateMessageId());
        result.setCorrelationId(verzoek.getMessageId());
        result.setBronPartijCode(verzoek.getDoelPartijCode());
        result.setDoelPartijCode(verzoek.getBronPartijCode());

        return result;
    }

    /*
     * *** Pf01 *****************************************************************************************
     */

    protected Pf01Bericht maakPf01Bericht(final Lo3Bericht verzoek) {
        final Pf01Bericht result = new Pf01Bericht();
        result.setMessageId(generateMessageId());
        result.setCorrelationId(verzoek.getMessageId());
        result.setBronPartijCode(verzoek.getDoelPartijCode());
        result.setDoelPartijCode(verzoek.getBronPartijCode());

        return result;
    }

    /*
     * *** Pf02 *****************************************************************************************
     */

    protected Pf02Bericht maakPf02Bericht(final Lo3Bericht verzoek) {
        final Pf02Bericht result = new Pf02Bericht();
        result.setMessageId(generateMessageId());
        result.setCorrelationId(verzoek.getMessageId());
        result.setBronPartijCode(verzoek.getDoelPartijCode());
        result.setDoelPartijCode(verzoek.getBronPartijCode());

        return result;

    }

    /*
     * *** Pf03 *****************************************************************************************
     */

    protected Pf03Bericht maakPf03Bericht(final Lo3Bericht verzoek) {
        final Pf03Bericht result = new Pf03Bericht();
        result.setMessageId(generateMessageId());
        result.setCorrelationId(verzoek.getMessageId());
        result.setBronPartijCode(verzoek.getDoelPartijCode());
        result.setDoelPartijCode(verzoek.getBronPartijCode());

        return result;

    }
}

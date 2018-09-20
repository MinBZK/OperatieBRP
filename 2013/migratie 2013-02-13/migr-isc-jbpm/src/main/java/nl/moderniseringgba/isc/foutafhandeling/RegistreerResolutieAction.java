/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.foutafhandeling;

import java.util.Map;

import nl.moderniseringgba.isc.jbpm.spring.SpringAction;

import org.springframework.stereotype.Component;

/**
 * Registreer de oplossing.
 */
@Component("foutafhandelingRegistreerResolutieAction")
public final class RegistreerResolutieAction implements SpringAction {

    private FoutenDao foutenDao = new JbpmFoutenDao();

    public void setFoutenDao(final FoutenDao foutenDao) {
        this.foutenDao = foutenDao;
    }

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        final String resolutie = (String) parameters.get(FoutafhandelingConstants.RESTART);
        final Long id = (Long) parameters.get(FoutafhandelingConstants.REGISTRATIE_ID);

        foutenDao.voegResolutieToe(id, resolutie);

        return null;
    }
}

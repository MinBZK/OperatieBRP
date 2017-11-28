/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * JSON Configuratie.
 */
@Configuration
@ComponentScan(basePackages = {"nl.bzk.brp.beheer.webapp.configuratie.json", "nl.bzk.brp.beheer.webapp.configuratie.json.modules" })
public class JsonConfiguratie {

}

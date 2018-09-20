/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.component;

import nl.bzk.brp.testrunner.component.services.datatoegang.DatabaseVerzoek;
import nl.bzk.brp.testrunner.component.services.dsl.DslVerzoekFactory;
import nl.bzk.brp.testrunner.omgeving.Component;
import org.springframework.core.io.Resource;

/**
 * Interface voor het BRP database componenten
 */
public interface BrpDatabase extends Component {

    /**
     * Voert een verzoek uit op de database, zonder transactie.
     * @param verzoek een DatabaseVerzoek
     */
    void voerUit(DatabaseVerzoek verzoek);

    /**
     * Voert een verzoek uit op de database, met transactie
     * @param verzoek een DatabaseVerzoek
     */
    void voerUitMetTransactie(DatabaseVerzoek verzoek);

    /**
     * Leegt de database (abonnementen en personen)
     */
    void leegDatabase();

    /**
     * Vult de database middels een SQL resource.
     * @param sqlResource een resource dat SQL bevat
     */
    void sqlScript(Resource sqlResource);

    /**
     * Verwijdert alle personen uit de database. Let op,
     * dit werkt alleen goed als de cascade delete scripts
     * op de database zijn uitgevoerd.
     */
    void verwijderAllePersonen();

    DslVerzoekFactory geefDslVerzoekFactory();
}

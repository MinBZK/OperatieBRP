/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.service.gbasync;

import nl.bzk.brp.dockertest.component.DatabaseDocker;
import nl.bzk.brp.dockertest.service.AutorisatieDataSqlVerzoek;
import nl.bzk.brp.test.common.TestclientExceptie;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

/**
 */
public final class PersoonUitExcelHelper {
    private final DatabaseDocker databaseDocker;

    private ClassPathXmlApplicationContext ctx;

    /**
     * Constructor waarin de omgeving wordt meegegeven waarmee de GBA-sync opgestart kan worden.
     */
    public PersoonUitExcelHelper(final DatabaseDocker databaseDocker) {
        this.databaseDocker = databaseDocker;
    }

    /**
     * Converteert een persoonslijst als initiele vulling naar BRP.
     * @param persoonFile het bestand waarin de persoon is gedefinieerd.
     * @return het resultaat van de conversie
     */
    public void converteerExcel(final Resource ... persoonFile) {
        lazyLaadConfiguratie();
        databaseDocker.template().readonly(new AutorisatieDataSqlVerzoek());
        try {
            for (Resource resource : persoonFile) {
                ctx.getBean(GbaSyncService.class).converteer(resource);
            }
        } catch (Exception e) {
            throw new TestclientExceptie(e);
        }
    }

    /**
     * Converteert een persoonslijst als initiele vulling naar BRP.
     * @param persoonFile het bestand waarin de persoon is gedefinieerd.
     * @return het resultaat van de conversie
     */
    public void sync(final Resource ... persoonFile) {
        lazyLaadConfiguratie();
        databaseDocker.template().readonly(new AutorisatieDataSqlVerzoek());
        try {
            for (Resource resource : persoonFile) {
                ctx.getBean(GbaSyncService.class).synchroniseer(resource);
            }
        } catch (Exception e) {
            throw new TestclientExceptie(e);
        }
    }

    public void close() {
        if (ctx != null) {
            ctx.close();
            ctx = null;
        }
    }

    private void lazyLaadConfiguratie() {
        if (ctx != null) {
            return;
        }
        ctx = new ClassPathXmlApplicationContext(databaseDocker.getApplicationContext());
        ctx.setConfigLocation("classpath:/database/lo3-brp.xml");
        ctx.refresh();
    }

}

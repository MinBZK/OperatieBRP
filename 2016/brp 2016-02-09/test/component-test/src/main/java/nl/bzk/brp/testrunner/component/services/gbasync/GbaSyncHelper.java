/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.component.services.gbasync;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;
import nl.bzk.brp.testrunner.component.BrpOmgeving;
import nl.bzk.brp.testrunner.component.Poorten;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigePersoonslijstException;
import nl.bzk.migratiebrp.synchronisatie.dal.service.PersoonslijstPersisteerResultaat;
import nl.bzk.migratiebrp.util.common.UniqueName;
import nl.bzk.migratiebrp.util.excel.ExcelAdapterException;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

/**
 */
public final class GbaSyncHelper {
    private static final UniqueName UNIQUE_NAME = new UniqueName();
    private final BrpOmgeving omgeving;
    private final AtomicInteger uniekeNaam = new AtomicInteger(5555);

    static {
        UNIQUE_NAME.setBaseName("syncDalDatabase");
    }

    private GbaSyncService gbaSyncService;

    /**
     * Constructor waarin de omgeving wordt meegegeven waarmee de GBA-sync opgestart kan worden.
     *
     * @param omgeving de omgeving waar binnen GBA-sync gaat draaien
     */
    public GbaSyncHelper(final BrpOmgeving omgeving) {
        this.omgeving = omgeving;
    }

    /**
     * Converteert een persoonslijst als initiele vulling naar BRP.
     *
     * @param persoonFile het bestand waarin de persoon is gedefinieerd.
     * @return het resultaat van de conversie
     * @throws IOException                     als het bestand niet bestaat of gelezen kan worden
     * @throws ExcelAdapterException           als het bestand geen Excel bestand is of niet goed gelezen kan worden
     * @throws Lo3SyntaxException              als de persoon een LO3 syntax fout bevat (bv geen datum in een datumveld)
     * @throws OngeldigePersoonslijstException als de persoon niet voldoet aan de precondities die gesteld zijn
     */
    public PersoonslijstPersisteerResultaat converteerInitieleVullingPL(final Resource persoonFile)
        throws IOException, ExcelAdapterException, Lo3SyntaxException, OngeldigePersoonslijstException
    {
        lazyLaadConfiguratie();
        return gbaSyncService.converteerInitieleVullingPL(persoonFile);
    }

    /**
     * Converteert een persoonslijst als synchronisatie naar BRP.
     *
     * @param persoonFile het bestand waarin de persoon is gedefinieerd.
     * @return het resultaat van de conversie
     * @throws IOException als het bestand niet bestaat of gelezen kan worden
     * @throws ExcelAdapterException als het bestand geen Excel bestand is of niet goed gelezen kan worden
     * @throws Lo3SyntaxException als de persoon een LO3 syntax fout bevat (bv geen datum in een datumveld)
     * @throws OngeldigePersoonslijstException als de persoon niet voldoet aan de precondities die gesteld zijn
     */
    public PersoonslijstPersisteerResultaat plMetSync(final Resource persoonFile)
        throws IOException, ExcelAdapterException, Lo3SyntaxException, OngeldigePersoonslijstException
    {
        lazyLaadConfiguratie();
        return gbaSyncService.converteerSyncPL(persoonFile);
    }

    /**
     * Mapt de BRP persoon terug naar het conversie model.
     *
     * @param anummer het anummer van de persoon die terug gemapped moet worden.
     * @return de persoonslijst in het conversie model formaat.
     */
    public BrpPersoonslijst mapTerug(final long anummer) {
        lazyLaadConfiguratie();
        return gbaSyncService.terugMappenNaarConversieModel(anummer);
    }

    private void lazyLaadConfiguratie() {
        if (gbaSyncService != null) {
            return;
        }
        final ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext();
        final PropertyPlaceholderConfigurer propConfig = new PropertyPlaceholderConfigurer();
        final Properties properties = new Properties();
        properties.put("uniekeNaam", "syncDatabase" + ((int) (Math.random() * Integer.MAX_VALUE)) + uniekeNaam.incrementAndGet());
        properties.put("db.ServerName", omgeving.geefOmgevingHost());
        properties.put("db.PortNumber", String.valueOf(omgeving.database().getPoortMap().get(Poorten.DB_POORT_5432)));
        propConfig.setProperties(properties);
        ctx.addBeanFactoryPostProcessor(propConfig);
        ctx.setConfigLocation("classpath:/database/lo3-brp.xml");
        ctx.refresh();
        this.gbaSyncService = ctx.getBean(GbaSyncService.class);
    }

}

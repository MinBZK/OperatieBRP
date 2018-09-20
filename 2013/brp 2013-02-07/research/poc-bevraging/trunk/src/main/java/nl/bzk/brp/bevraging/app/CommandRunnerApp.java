/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.app;

import nl.bzk.brp.bevraging.exceptions.CommandExecutionException;
import nl.bzk.brp.bevraging.exceptions.CommandNotFoundException;
import org.apache.commons.chain.Catalog;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Applicatie die de een {@link Command} uitvoert.
 */
public class CommandRunnerApp implements App {

    private Catalog catalog;
    private String chain;

    /**
     * Constructor met de chain om uit te voeren.
     * @param chain de chain om uit te voeren
     */
    public CommandRunnerApp(final String chain) {
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("datasource-context.xml", "bevraging-beans.xml");
        this.catalog = applicationContext.getBean("catalog", Catalog.class);

        this.chain = chain;
    }

    @Override
    public void run(final Context context) {
        Command command = this.catalog.getCommand(this.chain);
        if (command == null) {
            throw new CommandNotFoundException(this.chain);
        }

        try {
            command.execute(context);
        } catch (Exception e) {
            throw new CommandExecutionException(e);
        }
    }
}

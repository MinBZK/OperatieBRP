/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.selenium;

import jp.vmi.selenium.selenese.Context;
import jp.vmi.selenium.selenese.command.AbstractCommand;
import jp.vmi.selenium.selenese.command.ArgumentType;
import jp.vmi.selenium.selenese.result.Result;
import jp.vmi.selenium.selenese.result.Success;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

/**
 * Klasse voor het uitvoeren van Asynchrone scripts via Selenium.
 */
public final class ExecuteAsyncScriptCommand extends AbstractCommand {

    /**
     * Voer seleniumscript async uit.
     * 
     * @param index
     *            command index
     * @param name
     *            command name
     * @param args
     *            command arguments
     */
    ExecuteAsyncScriptCommand(final int index, final String name, final String... args) {
        super(index, name, args, ArgumentType.VALUE);
    }

    @Override
    protected Result executeImpl(final Context context, final String... curArgs) {
        final WebDriver driver = context.getWrappedDriver();

        if (driver instanceof JavascriptExecutor) {
            ((JavascriptExecutor) driver).executeAsyncScript(curArgs[0]);
        } else {
            throw new IllegalArgumentException("Gegeven driver ondersteund geen javascript");
        }

        return Success.SUCCESS;
    }
}

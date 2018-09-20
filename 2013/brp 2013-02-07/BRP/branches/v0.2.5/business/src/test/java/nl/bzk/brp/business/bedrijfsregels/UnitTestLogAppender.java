/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels;

import java.util.ArrayList;

import org.apache.log4j.Appender;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.ErrorHandler;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;


public class UnitTestLogAppender extends ArrayList<String> implements Appender {
    private final Class clazz;

    public UnitTestLogAppender(final Class clazz) {
        super();
        this.clazz = clazz;
    }

    @Override
    public void addFilter(final Filter newFilter) {
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    @Override
    public void clearFilters() {
    }

    @Override
    public void close() {
    }

    @Override
    public void doAppend(final LoggingEvent event) {
        add(event.getRenderedMessage());
    }

    @Override
    public String getName() {
        return "TestAppender for " + clazz.getSimpleName();
    }

    @Override
    public void setErrorHandler(final ErrorHandler errorHandler) {
    }

    @Override
    public ErrorHandler getErrorHandler() {
        return null;
    }

    @Override
    public void setLayout(final Layout layout) {
    }

    @Override
    public Layout getLayout() {
        return null;
    }

    @Override
    public void setName(final String name) {
    }

    @Override
    public boolean requiresLayout() {
        return false;
    }
}
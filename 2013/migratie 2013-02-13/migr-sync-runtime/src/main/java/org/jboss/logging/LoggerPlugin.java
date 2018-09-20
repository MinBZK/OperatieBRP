/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

//CHECKSTYLE:OFF - Externe code
/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.logging;

/**
 * Defines a "pluggable" login module. In fact, this is only used to split between log4j and /dev/null. Choice is made
 * in org.jboss.logging.Logger
 * 
 * @see org.jboss.logging.Logger
 * @see org.jboss.logging.NullLoggerPlugin
 * 
 * @author <a href="mailto:sacha.labourey@cogito-info.ch">Sacha Labourey</a>.
 * @version $Revision: 2081 $
 */
public interface LoggerPlugin {
    /**
     * Initialise the logger with the given name
     * 
     * @param name
     *            the name
     */
    void init(String name);

    /**
     * Check to see if the TRACE level is enabled for this logger.
     * 
     * @return true if a {@link #trace(Object)} method invocation would pass the msg to the configured appenders, false
     *         otherwise.
     */
    boolean isTraceEnabled();

    /**
     * Issue a log msg with a level of TRACE.
     * 
     * @param message
     *            the message
     */
    void trace(Object message);

    /**
     * Issue a log msg and throwable with a level of TRACE.
     * 
     * @param message
     *            the message
     * @param t
     *            the throwable
     */
    void trace(Object message, Throwable t);

    /**
     * Check to see if the DEBUG level is enabled for this logger.
     * 
     * @deprecated DEBUG is for low volume logging, you don't need this
     * @return true if a {@link #trace(Object)} method invocation would pass the msg to the configured appenders, false
     *         otherwise.
     */
    @Deprecated
    boolean isDebugEnabled();

    /**
     * Issue a log msg with a level of DEBUG.
     * 
     * @param message
     *            the message
     */
    void debug(Object message);

    /**
     * Issue a log msg and throwable with a level of DEBUG.
     * 
     * @param message
     *            the message
     * @param t
     *            the throwable
     */
    void debug(Object message, Throwable t);

    /**
     * Check to see if the INFO level is enabled for this logger.
     * 
     * @deprecated INFO is for low volume information, you don't need this
     * @return true if a {@link #info(Object)} method invocation would pass the msg to the configured appenders, false
     *         otherwise.
     */
    @Deprecated
    boolean isInfoEnabled();

    /**
     * Issue a log msg with a level of INFO.
     * 
     * @param message
     *            the message
     */
    void info(Object message);

    /**
     * Issue a log msg and throwable with a level of INFO.
     * 
     * @param message
     *            the message
     * @param t
     *            the throwable
     */
    void info(Object message, Throwable t);

    /**
     * Issue a log msg with a level of WARN.
     * 
     * @param message
     *            the message
     */
    void warn(Object message);

    /**
     * Issue a log msg and throwable with a level of WARN.
     * 
     * @param message
     *            the message
     * @param t
     *            the throwable
     */
    void warn(Object message, Throwable t);

    /**
     * Issue a log msg with a level of ERROR.
     * 
     * @param message
     *            the message
     */
    void error(Object message);

    /**
     * Issue a log msg and throwable with a level of ERROR.
     * 
     * @param message
     *            the message
     * @param t
     *            the throwable
     */
    void error(Object message, Throwable t);

    /**
     * Issue a log msg with a level of FATAL.
     * 
     * @param message
     *            the message
     */
    void fatal(Object message);

    /**
     * Issue a log msg and throwable with a level of FATAL.
     * 
     * @param message
     *            the message
     * @param t
     *            the throwable
     */
    void fatal(Object message, Throwable t);
}

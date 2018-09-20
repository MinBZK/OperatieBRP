/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

//CHECKSTYLE:OFF - Externe code
/*
 * JBoss, Home of Professional Open Source.
 *
 * Copyright 2010 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jboss.logging;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Assigns a message string to a resource method. The method arguments are used to supply the positional parameter
 * values for the method.
 * 
 * @author <a href="mailto:david.lloyd@redhat.com">David M. Lloyd</a>
 */
@Target(METHOD)
@Retention(CLASS)
@Documented
@Deprecated
public @interface Message {

    /**
     * Indicates that this message has no ID.
     */
    int NONE = 0;
    /**
     * Indicates that this message should inherit the ID from another message with the same name.
     */
    int INHERIT = -1;

    /**
     * The message ID number. Only one message with a given name may specify an ID other than {@link #INHERIT}.
     * 
     * @return the message ID number
     */
    int id() default INHERIT;

    /**
     * The default format string of this message.
     * 
     * @return the format string
     */
    String value();

    /**
     * The format type of this method (defaults to {@link Format#PRINTF}).
     * 
     * @return the format type
     */
    Format format() default Format.PRINTF;

    /**
     * The possible format types.
     */
    enum Format {

        /**
         * A {@link java.util.Formatter}-type format string.
         */
        PRINTF,
        /**
         * A {@link java.text.MessageFormat}-type format string.
         */
        MESSAGE_FORMAT,

        /**
         * Indicates the message should not be formatted.
         */
        NO_FORMAT,
    }

}

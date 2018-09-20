/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

//CHECKSTYLE:OFF - Externe code
/*
 * JBoss, Home of Professional Open Source.
 *
 * Copyright 2011 Red Hat, Inc., and individual contributors
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

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Identifies a parameter is to be used for constructing an exception and excluded from the formatting of the message.
 * <p/>
 * Parameters will be order-matched first, then type-matched to resolve ambiguity. If a match fails an error should
 * occur.
 * <p/>
 * The {@link #value()} option will allow an optional class to be specified which will have to match the exact type of
 * the parameter in question, to enable unambiguous resolution. The value must be the fully qualified class name.
 * 
 * @author <a href="mailto:jperkins@redhat.com">James R. Perkins</a>
 */
@Target(PARAMETER)
@Retention(CLASS)
@Documented
@Deprecated
public @interface Param {

    /**
     * Defines an exact class the parameter must match for unambiguous resolution.
     * 
     * @return the class the parameter must match.
     */
    Class<?> value() default Object.class;
}

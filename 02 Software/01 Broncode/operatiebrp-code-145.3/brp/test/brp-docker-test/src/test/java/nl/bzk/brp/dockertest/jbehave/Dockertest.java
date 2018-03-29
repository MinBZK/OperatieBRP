/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.jbehave;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import nl.bzk.brp.dockertest.component.DockerMountingInfo;
import nl.bzk.brp.dockertest.component.DockerNaam;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE_USE})
@Inherited
@Documented
public @interface Dockertest {

    String naam();

    DockerNaam[] componenten() default {};

    String[] autorisaties() default {};

    DockerMountingInfo[] mount() default {};
}

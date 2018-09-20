/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.dataaccess;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class FileSystemBestandRepositoryTest {

    private FileSystemBestandRepository repository;

    @Before
    public void setup() {
        System.setProperty("app.home", "target");
        repository = new FileSystemBestandRepository();
    }

    @Test
    public void kanSchrijven() {
        repository.schrijfRegels("foo", Arrays.asList("dumbar"));
    }

}

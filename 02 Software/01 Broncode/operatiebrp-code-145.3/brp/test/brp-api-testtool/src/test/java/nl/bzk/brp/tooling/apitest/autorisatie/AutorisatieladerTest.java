/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

//package nl.bzk.brp.tooling.apitest.autorisatie;
//
//import com.google.common.collect.Lists;
//import java.io.File;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.Parameterized;
//import org.springframework.core.io.FileSystemResource;
//
///**
// * Test die alle autorisaties laadt.
// */
//@RunWith(Parameterized.class)
//public class AutorisatieladerTest {
//
//    private final File file;
//
//    public AutorisatieladerTest(final File file) {
//        this.file = file;
//    }
//
//    @Parameterized.Parameters(name = "{index}: {0} = {1}")
//    public static File[] data() {
//        return AlleAutorisaties.AUTORISATIES.toArray(new File[AlleAutorisaties.AUTORISATIES.size()]);
//    }
//
//    @Test
//    public void test() {
//        Autorisatielader.laadAutorisatie(Lists.newArrayList(new FileSystemResource(file)));
//    }
//}

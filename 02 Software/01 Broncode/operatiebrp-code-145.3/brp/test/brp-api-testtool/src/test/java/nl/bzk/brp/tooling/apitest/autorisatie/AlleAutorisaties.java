/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

//package nl.bzk.brp.tooling.apitest.autorisatie;
//
//import java.io.File;
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.List;
//import org.apache.commons.io.FileUtils;
//import org.apache.commons.io.filefilter.FileFileFilter;
//import org.apache.commons.io.filefilter.TrueFileFilter;
//
///**
// */
//public class AlleAutorisaties {
//
//
//    static final Collection<File> AUTORISATIES;
//
//    static {
//
//        final FileFileFilter fileFileFilter = new FileFileFilter() {
//            @Override
//            public boolean accept(final File file) {
//                return "autorisatie".equals(file.getParentFile().getName());
//            }
//        };
//        final Collection<File> files = FileUtils.listFiles(new File("src/test/resources/testcases"), fileFileFilter, TrueFileFilter.INSTANCE);
//
//        final File generiekeAutorisatieDir = new File("src/test/resources/levering_autorisaties");
//        files.addAll(Arrays.asList(generiekeAutorisatieDir.listFiles(pathname -> !pathname.isDirectory())));
//
//
//        AUTORISATIES = Collections.unmodifiableCollection(files);
//    }
//}

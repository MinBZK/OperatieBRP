/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.deploy.check;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Analysor {

    private final File directory;
    private Map<String, List<String>> contents = new HashMap<>();
    private Map<String, Set<String>> duplicates = new HashMap<>();

    public Analysor(String directoryName) {
        directory = new File(directoryName);
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directoryName + " is not a valid directory.");
        }
    }

    public void analyse() throws IOException {
        for (File file : directory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File directory, String name) {
                return name.endsWith(".jar");
            }
        })) {

            System.out.println("Looking in: " + file.getName());

            String thisContentKey = file.getName();
            List<String> thisContentList = new ArrayList<>();

            try (JarFile jarFile = new JarFile(file)) {
                Enumeration<JarEntry> entries = jarFile.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    String thisContent = entry.getName();

                    if (!thisContent.endsWith(".class")) {
                        continue;
                    }

                    if (!entry.isDirectory()) {
                        thisContentList.add(thisContent);

                        for (Map.Entry<String, List<String>> otherContents : contents.entrySet()) {
                            if (otherContents.getValue().contains(thisContent)) {
                                duplicates.get(otherContents.getKey()).add(thisContentKey);
                            }
                        }
                    }
                }
            }

            contents.put(thisContentKey, thisContentList);
            duplicates.put(thisContentKey, new HashSet<String>());
        }
    }

    public void printDuplicates() {
        for (Map.Entry<String, Set<String>> thisDuplicate : duplicates.entrySet()) {
            for (String thatDuplicate : thisDuplicate.getValue()) {
                System.out.println(thisDuplicate.getKey() + " contains classes that are also present in " + thatDuplicate);
            }
        }
    }
}

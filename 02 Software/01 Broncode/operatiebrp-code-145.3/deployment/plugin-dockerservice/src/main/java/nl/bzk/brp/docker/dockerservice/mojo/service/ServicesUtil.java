package nl.bzk.brp.docker.dockerservice.mojo.service;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.stream.Collectors;

/**
 * Services.
 */
public class ServicesUtil {

    public static List<String> bepaalAlleServices() throws IOException {
        List<String> services = new ArrayList<>();
        try {
            final Enumeration<URL> servicePaden = ServicesUtil.class.getClassLoader().getResources("serviceconf");
            while (servicePaden.hasMoreElements()) {
                final URL serviceUrl = servicePaden.nextElement();
                if (serviceUrl.toString().startsWith("jar:file")) {
                    services = ((JarURLConnection) serviceUrl.openConnection()).getJarFile()
                            .stream()
                            .filter(ServicesUtil::filterBestandsnamenVanService)
                            .map(ServicesUtil::mapJarEntryNaarServiceNaam)
                            .collect(Collectors.toList());
                } else {
                    File file = new File(serviceUrl.toURI());
                    if (file.isDirectory()) {
                        services = Arrays.stream(file.list((dir, name) -> name.endsWith(".properties")))
                                .map(ServicesUtil::mapBestandsNaamNaarServiceNaam)
                                .collect(Collectors.toList());
                    }
                }
            }
        } catch(URISyntaxException e) {
            throw new IOException("Kan bestand niet bepalen", e);
        }
        return services;
    }


    private static String mapBestandsNaamNaarServiceNaam(final String filename) {
        return filename.replace(".properties", "");
    }

    private static String mapJarEntryNaarServiceNaam(final JarEntry jarEntry) {
          return mapBestandsNaamNaarServiceNaam(jarEntry.getName().replace("serviceconf/", ""));
    }

    private static boolean filterBestandsnamenVanService(final JarEntry jarEntry) {
        return jarEntry.getName().matches("serviceconf.*properties");
    }

}

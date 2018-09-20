/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.regels;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.context.ApplicationContext;


/**
 * De abstracte bedrijfsregelmanager die de basis vormt voor bedrijfsregelmanagers.
 */
public abstract class AbstractBedrijfsregelManager implements BedrijfsregelManager {

    @Inject
    private ApplicationContext applicationContext;

    /**
     * Initialiseert op basis van een properties bestand de regels voor een bericht.
     *
     * @param <R>               Het type regel.
     * @param propertiesBestand de naam van het properties bestand.
     * @param map               De map waarin de regels geinitialiseerd moeten worden.
     * @param enumClass         de specifieke enum class
     * @throws org.apache.commons.configuration.ConfigurationException indien er een fout optreedt bij het lezen van
     *                                                                 de configuratie bestanden.
     */
    protected <R> void initialiseerRegelsEnum(final String propertiesBestand,
            final Map<Enum, List<R>> map,
            final Class<? extends Enum> enumClass)
        throws ConfigurationException
    {
        final PropertiesConfiguration configuration = new PropertiesConfiguration(propertiesBestand);
        final Iterator<String> keys = configuration.getKeys();
        // Initialiseer de map voor alle soorten berichten. Mocht een bepaald soort bericht helemaal geen
        // bericht regels kennen dan krijgt deze in ieder geval de wildcard regels toegekend.
        for (final Enum enumObject : enumClass.getEnumConstants()) {
            map.put(enumObject, new ArrayList<R>());
        }

        while (keys.hasNext()) {
            final String key = keys.next();
            final List<R> regelsVoorKey = bepaalRegelsVoorKey(key, configuration);
            if ("*".equals(key)) {
                for (final List<R> regels : map.values()) {
                    regels.addAll(regelsVoorKey);
                }
            } else {
                final Enum enumObject = Enum.valueOf(enumClass, key);
                map.get(enumObject).addAll(regelsVoorKey);
            }
        }
    }

    /**
     * Haalt de bedrijfsregels op via Spring voor een bepaalde key in een properties configuratie.
     *
     * @param key           de key string
     * @param configuration de properties configuratie
     * @param <R>           Regel parameter
     * @return lijst van regels geconfigureerd voor de key.
     */
    @SuppressWarnings("unchecked")
    protected <R> List<R> bepaalRegelsVoorKey(final String key, final PropertiesConfiguration configuration) {
        final List<R> regelsVoorKey = new ArrayList<>();
        final List<Object> regelComponentNamen = configuration.getList(key);
        for (final Object regelComponentNaam : regelComponentNamen) {
            if (applicationContext.containsBean((String) regelComponentNaam)) {
                final R regel = (R) applicationContext.getBean((String) regelComponentNaam);
                regelsVoorKey.add(regel);
            } else {
                throw new IllegalStateException("Geen Regel component gevonden voor de regel '"
                        + regelComponentNaam + "'. Controleer de regel configuratie.");
            }
        }
        return regelsVoorKey;
    }

    /**
     * Retourneert de meegegeven lijst als die niet null is,
     * anders een lege lijst van het juiste type.
     *
     * @param lijst de lijst
     * @param <R>   het type element in de lijst
     * @return dezelfde lijst of een lege lijst als de oorspronkelijke null was
     */
    protected <R> List<R> dezeOfLegeLijst(final List<R> lijst) {
        List<R> dezeOfLegeLijst = lijst;
        if (dezeOfLegeLijst == null) {
            dezeOfLegeLijst = new ArrayList<>();
        }
        return dezeOfLegeLijst;
    }

}

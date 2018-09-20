package nl.bzk.brp.funqmachine.mapper

import com.google.common.collect.HashMultimap
import org.yaml.snakeyaml.Yaml

/**
 * Mapper die voor een administratieve handeling, of ander verzoek, weet welk bericht erbij hoort.
 */
class BerichtenMapper {
    private static HashMultimap<String, String> berichtHandelingMap = HashMultimap.create()
    private static Map<String, String>          berichtEndpointMap  = new HashMap<>()
    private static Map<String, String>          berichtNamespaceMap = new HashMap<>()

    static {
        Yaml yaml = new Yaml()
        def data = yaml.load(new InputStreamReader(getClass().getResourceAsStream('/nl/bzk/brp/funqmachine/mapper/endpoints-berichten.yml')))

        data.each {ber ->
            berichtHandelingMap.putAll(ber.bericht, ber.handelingen)
            berichtEndpointMap.put(ber.bericht, ber.endpoint)
            berichtNamespaceMap.put(ber.bericht, ber.namespace)
        }
    }

    /**
     * Geeft het soort bericht voor een soort verzoek.
     *
     * @param soortVerzoek soort verzoek
     * @return soort bericht
     */
    static String geefBerichtVoorSoortVerzoek(String soortVerzoek) {

        final berichtSoorten = berichtHandelingMap.entries().findAll { Map.Entry entry ->
            entry.value.equals(soortVerzoek)
        }

        if (berichtSoorten.size() == 0) {
            throw new IllegalArgumentException('Soort verzoek wordt niet ondersteund: ' + soortVerzoek)
        } else if (berichtSoorten.size() > 1) {
            throw new IllegalArgumentException('Soort verzoek [' + soortVerzoek + '] komt in meerdere berichten voor: ' + berichtSoorten.collectEntries().keySet()
                    + '. Je dient de step te gebruiken waarin je het specifieke bericht meegeeft.')
        } else {
            return berichtSoorten.first().key
        }
    }

    /**
     * Geeft het endpoint voor een specifiek verzoek.
     * @param soortVerzoek soort verzoek
     * @return endpoint
     */
    static String geefEndpointVoorVerzoek(String soortVerzoek) {
        def berichtNaam = geefBerichtVoorSoortVerzoek(soortVerzoek)
        geefEndpointVoorBericht(berichtNaam);
    }

    /**
     * Geeft het endpoint voor een specifiek bericht.
     * @param soortBericht soort bericht
     * @return endpoint
     */
    static String geefEndpointVoorBericht(String soortBericht) {
        berichtEndpointMap.get(soortBericht)
    }

    /**
     * Geeft namespace voor een specifiek verzoek.
     * @param soortVerzoek soort verzoek
     * @return namespace
     */
    static String geefNamespaceVoorVerzoek(String soortVerzoek) {
        def berichtNaam = geefBerichtVoorSoortVerzoek(soortVerzoek)
        geefNamespaceVoorBericht(berichtNaam)
    }

    /**
     * Geeft namespace voor een specifiek bericht.
     * @param soortBericht soort bericht
     * @return namespace
     */
    static String geefNamespaceVoorBericht(String soortBericht) {
        berichtNamespaceMap.get(soortBericht)
    }

}

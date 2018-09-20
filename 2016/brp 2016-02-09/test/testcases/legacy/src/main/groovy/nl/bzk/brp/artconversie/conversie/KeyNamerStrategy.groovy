package nl.bzk.brp.artconversie.conversie

/**
 * Strategie voor het hernoemen van keys van SoapUI formaat naar Freemarker formaat.
 */
class KeyNamerStrategy {

    static String rename(String key) {
        return  key.replace('.', '$').replace('-', '$').replace('|', '_')
    }
}

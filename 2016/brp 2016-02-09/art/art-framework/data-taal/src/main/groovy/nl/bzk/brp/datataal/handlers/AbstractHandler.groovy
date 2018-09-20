package nl.bzk.brp.datataal.handlers
import groovy.text.GStringTemplateEngine
import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie
import nl.bzk.brp.dataaccess.repository.jpa.ReferentieDataJpaRepository
import nl.bzk.brp.datataal.dataaccess.SpringBeanProvider
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LandGebiedCodeAttribuut
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Gemeente
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebied
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij
/**
 * Abstracte handler, die voor alle implementaties een aantal basale dingen biedt:
 * - toegang tot de referentieData
 * - mogelijkheid om string of number waardes te gebruiken bij het beschrijven van o.a. partij,
 *   land, gemeente en datums.
 */
abstract class AbstractHandler {
    protected ReferentieDataJpaRepository referentieData = SpringBeanProvider.getBean(ReferentieDataJpaRepository)

    /**
     * Valideer de keys die in een map voorkomen.
     * De Map mag alleen sleutels hebben die in de gegeven lijst voorkomen
     *
     * @param m de te valideren map
     * @param keys de mogelijke opties voor sleutels
     * @param message foutmelding bericht, mag {@code $keys} als variabele bevatten
     */
    static void valideerMapKeys(Map m, List keys, String message) {
        try {
            assert m.keySet().intersect(keys).sort() == m.keySet().sort()
        } catch (AssertionError e) {
            def melding = new GStringTemplateEngine().createTemplate(message).make([keys: keys]).toString() + "${m.keySet().sort()} -/- ${keys.sort()}"
            throw new IllegalArgumentException(melding, e)
        }
    }

    /**
     * Probeert van de gegeven waarde een datum, integer met jjjjMMdd, te maken.
     *
     * @param datum
     *          een datum notatie, mogelijk een {@link Number} of een String met opmaak 'jjjj/MM/dd'.
     * @return integer die de datum representeert
     * @throws IllegalArgumentException als de opgegeven datum geen integer of datum met goede opmaak is
     */
    protected Integer bepaalDatum(def datum) {
        def dat
        if (datum instanceof String) {
            // Sta bijde formaten toe als datum: yyyy/MM/dd en yyyy-MM-dd
            dat = datum.replaceAll('/', '').replaceAll('-', '') as int
        } else if (datum instanceof Number) {
            dat = datum as Integer
        } else {
            throw new IllegalArgumentException('datum moet een datum string "yyyy/mm/dd", "yyyy-MM-dd" of getal van 8 cijfers zijn')
        }
        dat
    }

    /**
     * Probeert van de gegeven waarde een {@link Partij} op te halen uit de database.
     *
     * @param partij een partij naam of code. De code mag een String of Number zijn
     * @return de gevonden partij
     * @throws OnbekendeReferentieExceptie als de naam of code geen bestaande partij beschrijft
     */
    protected Partij bepaalPartij(def partij) {
        if (partij instanceof Number) {
            return referentieData.vindPartijOpCode(partij as Integer)
        } else if (partij instanceof String) {
            if (partij.isNumber()) {
                return referentieData.vindPartijOpCode(partij as Integer)
            } else {
                return referentieData.vindPartijOpNaam(partij as String)
            }
        }

        throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.PARTIJCODE, partij, null)
    }

    /**
     * Probeert van de gegeven waarde een {@link Gemeente} op te halen uit de database.
     *
     * @param gem een gemeente naam of code. De code mag een String of Number zijn
     * @return de gevonden gemeente
     * @throws OnbekendeReferentieExceptie als de naam of code geen bestaande gemeente beschrijft
     */
    protected Gemeente bepaalGemeente(def gem) {
        if (gem instanceof Number) {
            return referentieData.vindGemeenteOpCode(gem as Short)
        } else if (gem instanceof String) {
            if (gem.isNumber()) {
                return referentieData.vindGemeenteOpCode(gem as Short)
            } else {
                return referentieData.vindGemeenteOpNaam(gem as String)
            }
        }

        throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.GEMEENTECODE, gem, null)
    }

    /**
     * Probeert van de gegeven waarde een {@link LandGebied} op te halen uit de database.
     *
     * @param land een land naam of code. De code mag een String of Number zijn
     * @return het gevonden landgebied
     * @throws OnbekendeReferentieExceptie als de naam of code geen bestaand landgebied beschrijft
     */
    protected LandGebied bepaalLand(def land) {
        if (land instanceof Number) {
            return referentieData.vindLandOpCode(new LandGebiedCodeAttribuut(land as Short))
        } else if (land instanceof String) {
            if (land.isNumber()) {
                return referentieData.vindLandOpCode(new LandGebiedCodeAttribuut(land as Short))
            } else {
                return referentieData.vindLandOpNaam(land as String)
            }
        }

        throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.LANDGEBIEDCODE, land, null)
    }
}

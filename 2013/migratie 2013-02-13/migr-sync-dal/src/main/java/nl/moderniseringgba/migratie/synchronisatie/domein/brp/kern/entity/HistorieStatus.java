/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity;

import java.util.Set;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroepInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpHistorie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;

/**
 * Historiestatus voor de redundante meta-gegevens op de A-laag van het BRP-model.
 * <p>
 * Historische groepen komen in een aantal patronen voor. Voor de patronen “materieel en formeel” en “formeel” geldt dat
 * er sprake kan zijn van (formeel) historische waarden in de D laag zónder dat er een actuele waarde in de A laag zit.
 * <p>
 * Kijkend in de A laag bij het attribuut “Voornamen”, en een “lege” waarde ontdekkend, is het daarom niet meteen
 * duidelijk of deze leeg is omdat… Er een actueel record is in de C laag voor Samengestelde naam, maar “Voornaam” is
 * toevallig leeg, of omdat … er geen actueel record is. Dit onderscheid wordt gemaakt door een (op elk moment
 * afleidbaar!) attribuut “Status historie”. Dit attribuut is aanwezig bij elke normale groep die het patroon ‘materieel
 * en formeel’ of het patroon ‘formeel’ heeft. Het waardebereik is als volgt:
 * 
 * <ul>
 * <li>
 * <b>A)</b> Er is een actuele waarde is in de C laag. Bij patroon “formeel” betekent dit dat er een record is in de C/D
 * tabel waarvan ‘datumtijd verval’ leeg is. Bij patroon “materieel en formeel” betekent dit tevens dat hiervoor geldt
 * dat ‘datum einde geldigheid’ &lt;= ‘vandaag’ &lt; ‘datum einde geldigheid’. <b>(*)</b></li>
 * 
 * <li><b>M)</b> Géén actuele waarde is in de C laag, maar wel materieel historische. (Alleen mogelijk bij patroon
 * ‘materieel én formeel’; niet bij ‘formeel’). Dit betekent: er is een record in de C laag met ‘datum einde geldigheid’
 * &lt; ‘datum einde geldigheid’ &lt;= ‘vandaag’.</li>
 * 
 * <li><b>F)</b> Geen record is in de C laag, maar wel in de D laag; alle relevante records in de C | D tabel hebben een
 * gevulde datumtijd verval. Kan zowel bij patroon “Materieel en formeel” als bij “Formeel”.</li>
 * <li><b>X)</b> Er is géén record in de C/D tabel (dus zelfs geen record met gevulde ‘datumtijd verval’).</li>
 * </ul>
 * 
 * <p>
 * <b>(*)</b> = Waarbij een lege waarde voor datum einde geldigheid gelezen kan worden als ‘ver in de toekomst’.
 * <p>
 * <b>(**)</b> = Normaal kan een mutatie in de C/D tabel leiden tot een update van het statushistorie veld: dit kan niet
 * voor toekomstmutaties...
 */
public enum HistorieStatus {
    /**
     * * <b>A)</b> Er is een actuele waarde is in de C laag. Bij patroon “formeel” betekent dit dat er een record is in
     * de C/D tabel waarvan ‘datumtijd verval’ leeg is. Bij patroon “materieel en formeel” betekent dit tevens dat
     * ‘datum einde geldigheid’ leeg is.
     */
    A,
    /**
     * <b>F)</b> Geen record is in de C laag, maar wel in de D laag; alle relevante records in de C | D tabel hebben een
     * gevulde datumtijd verval. Kan zowel bij patroon “Materieel en formeel” als bij “Formeel”.
     */
    F,
    /**
     * <b>M)</b> Géén actuele waarde is in de C laag, maar wel materieel historische. (Alleen mogelijk bij patroon
     * ‘materieel én formeel’; niet bij ‘formeel’). Dit betekent: er is een record in de C laag met ‘datum einde
     * geldigheid’ en ‘datumtijd verval’ is dus leeg.
     */
    M,
    /**
     * <b>X)</b> Er is géén record in de C/D tabel (dus zelfs geen record met gevulde ‘datumtijd verval’).
     */
    X;

    /**
     * Bepaal de status van de historie van de meegegeven stapel van BRP groepen. Deze implementatie gaat ervan uit dat
     * datum geldigheid altijd in het verleden ligt. Er wordt dus alleen gekeken of de datum einde geldigheid is gevuld
     * of niet.
     * 
     * @param brpStapel
     *            de brp stapel, mag null zijn
     * @param <T>
     *            Type van de BrpInhoud
     * @return de status van de historie zoals deze op actueel niveau wordt geregistreerd in de BRP.
     */
    public static <T extends BrpGroepInhoud> HistorieStatus bepaalHistorieStatus(final BrpStapel<T> brpStapel) {
        boolean bevatActueleGroepen = false;
        boolean bevatVervallenGroepen = false;
        boolean bevatMaterieelHistorischeGroepen = false;
        if (brpStapel != null) {
            // controle op brpStapel.isEmpty() is niet nodig omdat een stapel niet leeg kan zijn (invariant van Stapel)
            BrpGroep<T> groep = brpStapel.getMeestRecenteElement();
            do {
                final BrpHistorie historie = groep.getHistorie();
                if (historie.getDatumTijdVerval() == null && historie.getDatumEindeGeldigheid() == null) {
                    bevatActueleGroepen = true;
                } else if (historie.getDatumTijdVerval() == null) {
                    bevatMaterieelHistorischeGroepen = true;
                } else {
                    bevatVervallenGroepen = true;
                }
                groep = brpStapel.getVorigElement(groep);
            } while (groep != null);
        }

        HistorieStatus result = X;
        if (bevatActueleGroepen) {
            result = A;
        } else if (bevatMaterieelHistorischeGroepen) {
            result = M;
        } else if (bevatVervallenGroepen) {
            result = F;
        }
        return result;
    }

    /**
     * Bepaal de status van de historie van de meegegeven stapel van BRP groepen. Deze implementatie gaat ervan uit dat
     * datum geldigheid altijd in het verleden ligt. Er wordt dus alleen gekeken of de datum einde geldigheid is gevuld
     * of niet.
     * 
     * @param historieSet
     *            die historie
     * @param <T>
     *            het type historie
     * @return de status van de meegegeven historie
     */
    public static <T extends FormeleHistorie> HistorieStatus bepaalHistorieStatusVoorBrp(final Set<T> historieSet) {
        HistorieStatus result = X;
        if (historieSet != null) {
            boolean bevatActueleGroepen = false;
            boolean bevatVervallenGroepen = false;
            boolean bevatMaterieelHistorischeGroepen = false;
            for (final T formeleHistorie : historieSet) {
                if (formeleHistorie.getDatumTijdVerval() == null && formeleHistorie instanceof MaterieleHistorie) {
                    final MaterieleHistorie materieleHistorie = (MaterieleHistorie) formeleHistorie;
                    if (materieleHistorie.getDatumEindeGeldigheid() == null) {
                        bevatActueleGroepen = true;
                    } else {
                        bevatMaterieelHistorischeGroepen = true;
                    }
                } else if (formeleHistorie.getDatumTijdVerval() == null) {
                    bevatActueleGroepen = true;
                } else {
                    bevatVervallenGroepen = true;
                }
            }
            if (bevatActueleGroepen) {
                result = A;
            } else if (bevatMaterieelHistorischeGroepen) {
                result = M;
            } else if (bevatVervallenGroepen) {
                result = F;
            }
        }
        return result;
    }
}

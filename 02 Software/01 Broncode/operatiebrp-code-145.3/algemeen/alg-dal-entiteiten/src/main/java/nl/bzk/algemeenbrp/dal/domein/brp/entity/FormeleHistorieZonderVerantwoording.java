/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Interface voor rijen in BRP met formele historie (datum/tijd registratie/verval) zonder de
 * verantwoordings attributen.
 */
public interface FormeleHistorieZonderVerantwoording extends Entiteit {

    /**
     * Geef de waarde van datum tijd registratie van FormeleHistorieZonderVerantwoording.
     * 
     * @return de waarde van datum tijd registratie van FormeleHistorieZonderVerantwoording
     */
    Timestamp getDatumTijdRegistratie();

    /**
     * Zet de waarden voor datum tijd registratie van FormeleHistorieZonderVerantwoording.
     * 
     * @param datumTijdRegistratie de nieuwe waarde voor datum tijd registratie van
     *        FormeleHistorieZonderVerantwoording
     */
    void setDatumTijdRegistratie(Timestamp datumTijdRegistratie);

    /**
     * Geef de waarde van datum tijd verval van FormeleHistorieZonderVerantwoording.
     * 
     * @return de waarde van datum tijd verval van FormeleHistorieZonderVerantwoording
     */
    Timestamp getDatumTijdVerval();

    /**
     * Zet de waarden voor datum tijd verval van FormeleHistorieZonderVerantwoording.
     * 
     * @param datumTijdVerval de nieuwe waarde voor datum tijd verval van
     *        FormeleHistorieZonderVerantwoording
     */
    void setDatumTijdVerval(Timestamp datumTijdVerval);

    /**
     * Checks if is actueel.
     * 
     * @return true, if is actueel
     */
    boolean isActueel();

    /**
     * Checks if is vervallen.
     * 
     * @return true, if is vervallen
     */
    boolean isVervallen();

    /**
     * Voegt een nieuwVoorkomen met formele historie toe aan de gegeven lijst met voorkomens. Dit
     * nieuwVoorkomen dient aan de voorwaarden te voldoen die worden gecontroleerd door
     * {@link FormeleHistorie#valideerActueelVoorkomen}.
     * 
     * @param <E> het type historie
     * @param nieuwVoorkomen het nieuwVoorkomen dat moet worden toegevoegd
     * @param voorkomens de lijst met voorkomens dat moet worden uitgebreid met het gegeven
     *        nieuwVoorkomen
     * @throws IllegalArgumentException wanneer het nieuwe voorkomen materiele historie bevat
     * @see MaterieleHistorie#voegNieuweActueleToe(MaterieleHistorie, Set)
     */
    static <E extends FormeleHistorieZonderVerantwoording> void voegToe(final E nieuwVoorkomen, final Set<E> voorkomens) {

        if (nieuwVoorkomen instanceof MaterieleHistorie) {
            throw new IllegalArgumentException("Deze validatie mag alleen op FormeleHistorieZonderVerantwoording worden uitgevoerd.");
        }

        final E bestaandActueelVoorkomen = FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(voorkomens);
        if (bestaandActueelVoorkomen != null) {
            if (nieuwVoorkomen == null) {
                // Enkel actueel laten vervallen
                bestaandActueelVoorkomen.setDatumTijdVerval(Timestamp.valueOf(LocalDateTime.now()));
            } else {
                bestaandActueelVoorkomen.setDatumTijdVerval(nieuwVoorkomen.getDatumTijdRegistratie());
            }
        }

        if (nieuwVoorkomen != null) {
            voorkomens.add(nieuwVoorkomen);
        }
    }

    /**
     * Sluit het actuele voorkomen (zet datum verval).
     * 
     * @param voorkomens historie
     * @param <E> het type historie
     */
    static <E extends FormeleHistorieZonderVerantwoording> void sluit(final Set<E> voorkomens) {
        final E bestaandActueelVoorkomen = FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(voorkomens);
        if (bestaandActueelVoorkomen != null) {
            bestaandActueelVoorkomen.setDatumTijdVerval(Timestamp.valueOf(LocalDateTime.now()));
        }
    }

    /**
     * Geeft een intersectie terug van de lijst van (private) velden in de historie en entiteit
     * (behalve 'id').
     * 
     * @param historieClass historie class
     * @param entiteitClass entiteit class
     * @param <E> historie type
     * @return lijst van velden
     */
    static <E extends FormeleHistorieZonderVerantwoording> List<Field> historieVelden(final Class<? extends E> historieClass, final Class<?> entiteitClass) {
        final List<String> entiteitFieldNames = Arrays.stream(entiteitClass.getDeclaredFields()).map(Field::getName).collect(Collectors.toList());
        return Arrays.stream(historieClass.getDeclaredFields()).filter(field -> !"id".equals(field.getName()))
                .filter(field -> field.getAnnotations().length > 0).filter(field -> entiteitFieldNames.contains(field.getName())).map(field -> {
                    field.setAccessible(true);
                    return field;
                }).collect(Collectors.toList());
    }

    /**
     * Geef het actuele historie voorkomen.
     * 
     * @param <T> het type historie
     * @param historieSet de historie set
     * @return het actuele (niet-vervallen) historische voorkomen van deze set historie, of null als
     *         deze niet bestaat
     */
    static <T extends FormeleHistorieZonderVerantwoording> T getActueelHistorieVoorkomen(final Set<T> historieSet) {
        return historieSet.stream().filter(FormeleHistorieZonderVerantwoording::isActueel).findFirst().orElse(null);
    }

    /**
     * Geeft aan of de meegegven historie set een actueel voorkomen bevat.
     * @param historieSet de historie set
     * @param <T> het type historie
     * @return true als de set een actueel (niet vervallen/geen datum einde geldigheid) voorkomen bevat, anders false.
     */
    static <T extends FormeleHistorieZonderVerantwoording> boolean heeftActueelVoorkomen(final Set<T> historieSet) {
        return getActueelHistorieVoorkomen(historieSet) != null;
    }

    /**
     * Geef de set van niet vervallen voorkomens binnen de gegeven set van voorkomens.
     *
     * @param <T> het type historie
     * @param historieSet de historie set
     * @return de set van niet-vervallen historische voorkomens van deze set historie, of een lege set als deze niet bestaan
     */
    static <T extends FormeleHistorieZonderVerantwoording> Set<T> getNietVervallenVoorkomens(final Set<T> historieSet) {
        final Set<T> results = new LinkedHashSet<>();
        for (final T voorkomen : historieSet) {
            if (voorkomen.getDatumTijdVerval() == null) {
                results.add(voorkomen);
            }
        }
        return results;
    }

    /**
     * Geeft de (private) velden van de historie terug die gelden voor de meegegeven entiteit class.
     * 
     * @param entiteitClass de class van de entiteit
     * @param historieClass de class van de historie waar de velden van opgezocht moeten worden
     * @return de lijst van historie velden die van toepassing zijn op de entiteit
     */
    static List<Field> getDeclaredEntityHistoryFields(final Class<?> entiteitClass, final Class<? extends FormeleHistorieZonderVerantwoording> historieClass) {
        if (historieClass.isAssignableFrom(entiteitClass)) {
            return Arrays.stream(historieClass.getDeclaredFields()).filter(field -> !Entiteit.isFieldConstant(field)).filter(field -> !field.isSynthetic())
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.alaag;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import nl.bzk.algemeenbrp.dal.domein.brp.annotation.IndicatieActueelEnGeldig;
import nl.bzk.algemeenbrp.dal.domein.brp.annotation.NegerenVoorALaagAfleiding;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Afleidbaar;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Entiteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RelatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.OneToMany;

/**
 * Util-class om de A-laag te kunnen afleiden voor {@link Afleidbaar}.
 */
public final class ALaagAfleidingsUtil {

    private ALaagAfleidingsUtil() {
        throw new AssertionError("ALaagAfleidingsUtil mag niet geinstantieerd worden.");
    }

    /**
     * Muteert de A-laag voor de meegegeven entiteit adhv de meegegeven historie.
     * @param entiteit de entiteit waarvan de A-laag gevuld/geleegd moet worden
     * @param indicatieActueelEnGeldigSetter setter om de indicatie actueel en geldig voor deze groep mee te zetten
     * @param historie de historie waarmee de A-laag gevuld/geleegd wordt
     * @param vullenOfLegen true als de A-laag gevuld moet worden, false voor legen.
     * @throws IllegalStateException als het gegeven op de A-laag niet gevuld kan worden
     */
    static void muteerALaag(
            final Object entiteit,
            final IndicatieActueelEnGeldigSetter indicatieActueelEnGeldigSetter,
            final FormeleHistorieZonderVerantwoording historie,
            final boolean vullenOfLegen,
            final Class<? extends FormeleHistorieZonderVerantwoording> historieType) {
        if (historieType == null) {
            return;
        }
        final Object pojoEntiteit = Entiteit.convertToPojo(entiteit);
        final FormeleHistorieZonderVerantwoording pojoHistorie;
        if (historie != null) {
            pojoHistorie = Entiteit.convertToPojo(historie);
        } else {
            pojoHistorie = null;
        }

        muteerALaagVelden(vullenOfLegen, historieType, pojoEntiteit, pojoHistorie);

        if (indicatieActueelEnGeldigSetter != null) {
            indicatieActueelEnGeldigSetter.set(vullenOfLegen && pojoHistorie != null);
        }
    }

    private static void muteerALaagVelden(final boolean vullenOfLegen, final Class<? extends FormeleHistorieZonderVerantwoording> historieType,
                                          final Object pojoEntiteit,
                                          final FormeleHistorieZonderVerantwoording pojoHistorie) {
        String huidigeFieldNaam = "";
        try {
            for (final Field historieField : FormeleHistorieZonderVerantwoording.historieVelden(historieType, pojoEntiteit.getClass())) {

                huidigeFieldNaam = historieField.getName();
                final Field entiteitField = pojoEntiteit.getClass().getDeclaredField(historieField.getName());
                entiteitField.setAccessible(true);

                if (vullenOfLegen && pojoHistorie != null) {
                    entiteitField.set(pojoEntiteit, historieField.get(pojoHistorie));
                } else if (isVeldNullable(entiteitField)) {
                    // Veld moet worden geleegd, maar verplicht velden mogen niet leeg worden (@Column nullable = false)
                    entiteitField.set(pojoEntiteit, null);
                }
            }
        } catch (final ReflectiveOperationException e) {
            throw new IllegalStateException(
                    String.format(
                            "Kan veld %s niet Kopieren van historie %s naar A-laag %s",
                            huidigeFieldNaam,
                            historieType.getSimpleName(),
                            pojoEntiteit.getClass().getSimpleName()),
                    e);
        }
    }

    private static boolean isVeldNullable(final Field rootEntiteitField) {
        final Column[] columnAnnotation = rootEntiteitField.getAnnotationsByType(Column.class);
        final JoinColumn[] joinColumnAnnotation = rootEntiteitField.getAnnotationsByType(JoinColumn.class);
        return columnAnnotation.length > 0 && columnAnnotation[0].nullable() || joinColumnAnnotation.length > 0 && joinColumnAnnotation[0].nullable();
    }

    /**
     * Vult de A-laag adhv de actuele historie (C/D laag). Als de entiteit een {@link Persoon} is,
     * dan wordt er ook gezocht naar alle relaties om deze te vullen.
     * @param entiteit de A-laag die gevuld moet worden
     * @throws IllegalStateException als er tijdens de kopie een fout ontstaat, bv de veldnamen komen niet overeen tussen de {@link Afleidbaar} en de {@link
     * FormeleHistorieZonderVerantwoording} implementatie.
     */
    public static void vulALaag(final Afleidbaar entiteit) {
        muteerALaag(entiteit, true);
    }

    /**
     * Leegt de A-laag adhv de actuele historie (C/D laag). Als de entiteit een {@link Persoon} is,
     * dan wordt er ook gezocht naar alle relaties om deze te vullen.
     * @param entiteit de A-laag die geleegd moet worden
     * @throws IllegalStateException als er tijdens de kopie een fout ontstaat, bv de veldnamen komen niet overeen tussen de {@link Afleidbaar} en de {@link
     * FormeleHistorieZonderVerantwoording} implementatie.
     */
    public static void leegALaag(final Afleidbaar entiteit) {
        muteerALaag(entiteit, false);
    }

    /**
     * Muteert de A-Laag
     * @param afleidbareEntiteit de entiteit waar de A-laag van gevuld of geleegd wordt.
     * @param vullenOfLegen true als de A-laag gevuld moet; false als de A-laag leeg gemaakt moet worden
     */
    private static void muteerALaag(final Afleidbaar afleidbareEntiteit, final boolean vullenOfLegen) {
        final Afleidbaar pojoEntiteit = Entiteit.convertToPojo(afleidbareEntiteit);

        Arrays.stream(pojoEntiteit.getClass().getDeclaredFields())
                .filter(field -> field.getAnnotationsByType(OneToMany.class).length > 0)
                .filter(field -> field.getAnnotationsByType(NegerenVoorALaagAfleiding.class).length == 0)
                .forEach(field -> muteerAssociatie(field, pojoEntiteit, vullenOfLegen));

        for (final Relatie relatie : verzamelRelaties(pojoEntiteit)) {
            muteerRelatieALaag(relatie, vullenOfLegen);
        }
    }

    private static void muteerAssociatie(final Field field, final Afleidbaar pojoEntiteit, final boolean vullenOfLegen) {
        field.setAccessible(true);
        try {
            final Set<?> fieldValue = (Set<?>) field.get(pojoEntiteit);

            if (!fieldValue.isEmpty()) {
                final Object setEntry = fieldValue.iterator().next();
                if (setEntry instanceof Afleidbaar) {
                    fieldValue.forEach(entiteit -> muteerALaag((Afleidbaar) entiteit, vullenOfLegen));
                } else if (setEntry instanceof FormeleHistorieZonderVerantwoording) {
                    @SuppressWarnings("unchecked")
                    final Set<FormeleHistorieZonderVerantwoording> formeleHistorieSet = (Set<FormeleHistorieZonderVerantwoording>) fieldValue;
                    final Class<? extends FormeleHistorieZonderVerantwoording> historieType = bepaalTypeHistorie(formeleHistorieSet);
                    final FormeleHistorieZonderVerantwoording actueelHistorieVoorkomen = getHistorieRecord(vullenOfLegen, formeleHistorieSet);
                    final IndicatieActueelEnGeldigSetter indicatieActueelEnGeldigSetter =
                            bepaalIndicatieActueelEnGeldigSetter(pojoEntiteit, field);
                    //maak velden leeg
                    muteerALaag(pojoEntiteit, indicatieActueelEnGeldigSetter, actueelHistorieVoorkomen, vullenOfLegen, historieType);
                }
            }
        } catch (final ReflectiveOperationException e) {
            throw new IllegalStateException("Er is iets mis gegaan tijdens afleiden A-laag", e);
        }
    }

    private static Class<? extends FormeleHistorieZonderVerantwoording> bepaalTypeHistorie(Set<FormeleHistorieZonderVerantwoording> formeleHistorieSet) {
        final Class<? extends FormeleHistorieZonderVerantwoording> result;
        if (formeleHistorieSet.isEmpty()) {
            result = null;
        } else {
            result = Entiteit.convertToPojo(formeleHistorieSet.iterator().next()).getClass();
        }
        return result;
    }

    private static IndicatieActueelEnGeldigSetter bepaalIndicatieActueelEnGeldigSetter(final Afleidbaar entiteit, final Field historieSetField)
            throws ReflectiveOperationException {
        final IndicatieActueelEnGeldig[] annotationsByType = historieSetField.getAnnotationsByType(IndicatieActueelEnGeldig.class);
        if (annotationsByType.length > 0) {
            final Field indicatieAgField = entiteit.getClass().getDeclaredField(annotationsByType[0].naam());
            indicatieAgField.setAccessible(true);
            return indicatieActueelEnGeldig -> {
                try {
                    indicatieAgField.set(entiteit, indicatieActueelEnGeldig);
                } catch (final ReflectiveOperationException e) {
                    throw new IllegalStateException("Er is iets mis gegaan tijdens afleiden A-laag (bepalen veld indicatie actueel en geldig)", e);
                }
            };
        }
        return null;
    }

    private static Set<Relatie> verzamelRelaties(final Afleidbaar entiteit) {
        final Set<Relatie> relaties = new LinkedHashSet<>();
        if (entiteit instanceof Persoon && SoortPersoon.INGESCHREVENE.equals(((Persoon) entiteit).getSoortPersoon())) {
            relaties.addAll(((Persoon) entiteit).getBetrokkenheidSet().stream().map(Betrokkenheid::getRelatie).collect(Collectors.toList()));
        }
        return relaties;
    }

    private static <T extends FormeleHistorieZonderVerantwoording> FormeleHistorieZonderVerantwoording getHistorieRecord(
            final boolean vullenOfLegen,
            final Set<T> formeleHistorieSet) {
        return vullenOfLegen ? FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(formeleHistorieSet) : formeleHistorieSet.iterator().next();
    }

    private static void muteerRelatieALaag(final Relatie relatie, final boolean vullenOfLegen) {
        final RelatieHistorie actueleRelatieHistorie = FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(relatie.getRelatieHistorieSet());
        muteerALaag(relatie, relatie::setActueelEnGeldig, actueleRelatieHistorie, vullenOfLegen, RelatieHistorie.class);

        for (final Betrokkenheid betrokkenheid : relatie.getBetrokkenheidSet()) {
            muteerALaag(betrokkenheid, vullenOfLegen);

            final Persoon persoon = betrokkenheid.getPersoon();
            if (persoon != null && !persoon.getSoortPersoon().equals(SoortPersoon.INGESCHREVENE)) {
                muteerALaag(persoon, vullenOfLegen);
            }
        }
    }

    /**
     * Setter voor de indicatie actueel en geldig.
     */
    @FunctionalInterface
    public interface IndicatieActueelEnGeldigSetter {
        /**
         * Zet de indicatie actueel en geldig.
         * @param indicatieActueelEnGeldig true, als actueel en geldig, anders false
         */
        void set(boolean indicatieActueelEnGeldig);
    }
}

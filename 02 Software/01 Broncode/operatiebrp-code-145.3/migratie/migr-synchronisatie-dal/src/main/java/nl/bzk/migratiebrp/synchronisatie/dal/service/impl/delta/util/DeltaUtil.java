/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Version;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ALaagHistorieVerzameling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AbstractFormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DynamischeStamtabel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Entiteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Bericht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Voorkomen;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Onderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfgeleidAdministratiefHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNaamgebruikHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.EntiteitSleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.Verschil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilType;

/**
 * Util class voor delta verwerking.
 */
public final class DeltaUtil {

    private DeltaUtil() {
    }

    /**
     * Veld kan overgeslagen worden als het veld aan 1 van de volgende situaties voldoet:<br>
     * <OL>
     * <LI>Het veld van hetzelfde type is als de eigenaar. Veld is waarschijnlijk de eigenaar en dit voorkomt loops</LI>
     * <LI>Veld een @Id annotatie heeft</LI>
     * <LI>Het veld van het type is dat voor komt in de lijst van over-te-slaan-klasses (bv Relatie/Betrokkenheden)</LI>
     * </OL>
     * @param veld veld waarvan bepaalt wordt of het over geslagen moet worden
     * @param oud object waarvan het veld is
     * @param eigenaar eigenaar van het object.
     * @param skippableClasses een lijst van classes die overgeslagen mogen worden
     * @return true als het veld overgeslagen kan worden.
     * @throws IllegalAccessException als er een fout ontstaat tijdens reflectie
     */
    public static boolean isSkipableVeld(final Field veld, final Object oud, final Object eigenaar, final List<Class<?>> skippableClasses)
            throws IllegalAccessException {
        boolean result = eigenaar != null;
        result = result && DeltaUtil.isEigenaarVeld(veld, oud, eigenaar.getClass());
        result = result || isIdVeld(veld);
        result = result || isVersionVeld(veld);
        result = result || isTypeSkippable(skippableClasses, veld.getType());
        if (!result) {
            result = isSkipableVeld(veld, oud, skippableClasses);
        }

        return result;
    }

    private static boolean isSkipableVeld(final Field veld, final Object oud, final List<Class<?>> skippableClasses) throws IllegalAccessException {
        boolean result = false;
        if (PersoonNaamgebruikHistorie.class.isAssignableFrom(oud.getClass())) {
            final String veldnaam = veld.getName();
            result = veldnaam.equals(PersoonNaamgebruikHistorie.GESLACHTSNAAMSTAM)
                    || veldnaam.equals(PersoonNaamgebruikHistorie.SCHEIDINGSTEKEN)
                    || veldnaam.equals(PersoonNaamgebruikHistorie.VOORVOEGSEL);
        } else if (DeltaUtil.isEntiteitVerzamelingVeld(veld)) {
            final Type veldType = veld.getGenericType();
            if (veldType instanceof ParameterizedType) {
                final ParameterizedType type = (ParameterizedType) veldType;
                result = type.getActualTypeArguments().length == 1 && isTypeSkippable(skippableClasses, type.getActualTypeArguments()[0]);
            } else {
                final Collection<?> veldWaarde = (Collection<?>) veld.get(oud);
                result = controleerLijstRegelSkippable(skippableClasses, veldWaarde);
            }
        }
        return result;
    }

    private static boolean controleerLijstRegelSkippable(final List<Class<?>> skippableClasses, final Collection<?> veldWaarde) {
        boolean result = false;
        if (!veldWaarde.isEmpty()) {
            final Class<?> collectionInhoudClass = veldWaarde.iterator().next().getClass();
            if (DeltaUtil.isTypeSkippable(skippableClasses, collectionInhoudClass)) {
                result = true;
            }
        }
        return result;
    }

    /**
     * Controleert of het meegegeven objecttype overgeslagen kan worden bij vergelijken/verwerken van verschillen.
     * @param skippableClasses een lijst van classes die overgeslagen mogen worden
     * @param type objecttype dat evt overgeslagen kan worden
     * @return true als de class van het objecttype voorkomt op de lijst van classes die overgeslagen kunnen worden.
     */
    private static boolean isTypeSkippable(final List<Class<?>> skippableClasses, final Type type) {
        boolean result = false;
        if (type instanceof Class) {
            for (final Class<?> skippableClass : skippableClasses) {
                if (skippableClass.isAssignableFrom((Class<?>) type)) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Controleert of het meegegeven veld een ID veld is.
     * @param veld het te controleren veld
     * @return true als het veld een annotatie @Id heeft.
     */
    public static boolean isIdVeld(final Field veld) {
        return veld.getAnnotation(Id.class) != null;
    }

    /**
     * Controleert of het meegegeven veld een VERSION veld is.
     * @param veld het te controleren veld
     * @return true als het veld een annotatie @Version heeft.
     */
    private static boolean isVersionVeld(final Field veld) {
        return veld.getAnnotation(Version.class) != null;
    }

    /**
     * Test of een veld een verzameling is van entiteiten.
     * @param veld veld dat getest moet worden
     * @return true als het een veld betreft is dat een verzameling is van entiteiten.
     */
    public static boolean isEntiteitVerzamelingVeld(final Field veld) {
        return veld.getAnnotation(OneToMany.class) != null && Collection.class.isAssignableFrom(veld.getType());
    }

    /**
     * Controleert of het meegegeven veld een waarde veld is.
     * @param veld het te controleren veld
     * @return true als het veld een annotatie @Coumn heeft.
     */
    public static boolean isWaardeVeld(final Field veld) {
        return veld.getAnnotation(Column.class) != null;
    }

    /**
     * Controleert of het meegegeven veld een referentie is naar andere entiteiten.
     * @param eigenaarKlasse klasse van de eigenaar
     * @param veld het te controleren veld
     * @return true als het veld een referentie naar een andere entiteit is, niet zijnde het eigenaar veld.
     */
    public static boolean isEntiteitReferentieVeld(final Object eigenaarKlasse, final Field veld) {
        final boolean isJoinColumn = veld.getAnnotation(JoinColumn.class) != null;
        final boolean isToOne = veld.getAnnotation(ManyToOne.class) != null || veld.getAnnotation(OneToOne.class) != null;
        final boolean isEigenaar = eigenaarKlasse != null && eigenaarKlasse.equals(veld.getType());

        return isJoinColumn && isToOne && !isEigenaar;
    }

    /**
     * @param entiteitClass de class van de betreffende entiteit
     * @return de lijst met {@link Class#getDeclaredFields()} van de meegegeven class en optioneel de velden van {@link
     * nl.bzk.algemeenbrp.dal.domein.brp.entity.AbstractMaterieleHistorie} en {@link nl.bzk.algemeenbrp.dal.domein.brp.entity.AbstractFormeleHistorie} als de
     * meegeven class een subtype van deze classes is
     */
    public static Field[] getDeclaredEntityFields(final Class<?> entiteitClass) {
        final List<Field> result = new ArrayList<>();

        for (final Field veld : entiteitClass.getDeclaredFields()) {
            if (!Entiteit.isFieldConstant(veld)) {
                result.add(veld);
            }
        }
        Collections.addAll(result, Entiteit.verzamelAlleHistorieAttributen(entiteitClass));
        return result.toArray(new Field[result.size()]);
    }

    /**
     * Controleert of het meegegeven veld een dynamisch stamtabel veld is.
     * @param veld het te controleren veld
     * @return true als het veld de annotaties @JoinColumn en @ManyToOne heeft en daarnaast assignable is van {@link
     * nl.bzk.algemeenbrp.dal.domein.brp.entity.DynamischeStamtabel}.
     */
    public static boolean isDynamischeStamtabelVeld(final Field veld) {
        return veld.getAnnotation(JoinColumn.class) != null
                && veld.getAnnotation(ManyToOne.class) != null
                && DynamischeStamtabel.class.isAssignableFrom(veld.getType());
    }

    /**
     * Controleert of het meegegeven veld het veld van de eigenaar klasse is. Bv. als Persoon een nationaliteit heeft,
     * dan heeft nationaliteit een referentie terug naar persoon. Om te voorkomen dat de vergelijker de persoon gaat
     * vergelijken, kan er gecontroleerd worden of persoon de eigenaar is van nationaliteit.
     * @param veld het te controleren veld
     * @param entiteit het object waar het veld uit komt
     * @param eigenaarClass eigenaar van het object o
     * @return true als het veld het eigenaarveld is of een lijst van eigenaren is.
     * @throws IllegalAccessException als het object o niet als Collection kan worden gemaakt
     */
    static boolean isEigenaarVeld(final Field veld, final Object entiteit, final Class<?> eigenaarClass) throws IllegalAccessException {
        boolean result = false;
        if (veld.getAnnotation(JoinColumn.class) != null && veld.getAnnotation(ManyToOne.class) != null && veld.getType() == eigenaarClass) {
            result = true;
        } else if (DeltaUtil.isEntiteitVerzamelingVeld(veld)) {
            veld.setAccessible(true);

            // noinspection unchecked
            final Collection<?> oudeVeldWaarde = (Collection<?>) veld.get(entiteit);
            if (!oudeVeldWaarde.isEmpty() && eigenaarClass.isAssignableFrom(oudeVeldWaarde.iterator().next().getClass())) {
                result = true;
            }
        }
        return result;
    }

    /**
     * Controleert of het veld van het type {@link nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling}
     * is.
     * @param veld het te controleren veld
     * @return true als het veld van het type {@link nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling} is
     */
    public static boolean isVeldAdministratieveHandelingVeld(final Field veld) {
        return AdministratieveHandeling.class.isAssignableFrom(veld.getType());
    }

    /**
     * Controleert of het veld een veld is ten behoeve van levering mutaties, en daarom moet worden uitgesloten van
     * verschillen bepalen.
     * @param veld het te controleren veld
     * @return true als het veld uitgesloten moet worden
     */
    public static boolean isVeldTbvLeveringMutaties(final Field veld) {
        return AbstractFormeleHistorie.ACTIE_VERVAL_TBV_LEVERING_MUTATIES.equals(veld.getName())
                || AbstractFormeleHistorie.INDICATIE_VOORKOMEN_TBV_LEVERING_MUTATIES.equals(veld.getName());
    }

    /**
     * Bepaal of het gegeven object een historie 'M'-rij is.
     * @param rij het te testen object
     * @return true als het object een historie 'M'-rij is.
     */
    public static boolean isMRij(final Entiteit rij) {
        return rij instanceof FormeleHistorie && ((FormeleHistorie) rij).getIndicatieVoorkomenTbvLeveringMutaties() != null;
    }

    /**
     * Geeft een lijst van entiteiten terug die overgeslagen moeten worden bij bepalen en verwerken van verschillen op
     * {@link nl.bzk.algemeenbrp.dal.domein.brp.entity.RootEntiteit}.
     * @param modus Geeft aan voor welke {@link nl.bzk.algemeenbrp.dal.domein.brp.entity.RootEntiteit} de entiteiten geskipped moeten worden
     * @param isVerwerking geeft aan of de terug te geven lijst voor een verschil bepaler of een verschil verwerker is.
     * @return de lijst met entiteiten die overgeslagen moeten worden
     */
    public static List<Class<?>> getSkippableEntiteiten(final DeltaRootEntiteitModus modus, final boolean isVerwerking) {
        List<Class<?>> skippableClasses = new ArrayList<>();
        switch (modus) {
            case PERSOON:
                addSkipablePersoon(isVerwerking, skippableClasses);
                break;
            case RELATIE:
                addSkipableRelatie(isVerwerking, skippableClasses);
                break;
            case BETROKKENHEID:
                addSkipableBetrokkenheid(isVerwerking, skippableClasses);
                break;
            default:
                skippableClasses = Collections.emptyList();
        }
        return Collections.unmodifiableList(skippableClasses);
    }

    private static void addSkipableBetrokkenheid(final boolean isVerwerking, final List<Class<?>> skippableClasses) {
        if (!isVerwerking) {
            skippableClasses.add(Persoon.class);
            skippableClasses.add(BetrokkenheidHistorie.class);
        }
        skippableClasses.add(Relatie.class);
    }

    private static void addSkipableRelatie(final boolean isVerwerking, final List<Class<?>> skippableClasses) {
        if (!isVerwerking) {
            skippableClasses.add(Betrokkenheid.class);
        }
        skippableClasses.add(Stapel.class);
    }

    private static void addSkipablePersoon(final boolean isVerwerking, final List<Class<?>> skippableClasses) {
        if (!isVerwerking) {
            skippableClasses.add(Betrokkenheid.class);
        }
        skippableClasses.add(Relatie.class);
        skippableClasses.add(Lo3Bericht.class);
        skippableClasses.add(Lo3Voorkomen.class);
        skippableClasses.add(Onderzoek.class);
        skippableClasses.add(Stapel.class);
        skippableClasses.add(PersoonAfgeleidAdministratiefHistorie.class);
    }

    /**
     * Maakt de verschillen voor de historie rijen van een A-laag object zodat deze gemarkeerd kunnen worden voor
     * omzetting naar M-rijen.
     * @param aLaagEntiteit DeltaEntiteit welke de {@link nl.bzk.algemeenbrp.dal.domein.brp.entity.ALaagHistorieVerzameling} interface implementeerd
     * @param oudeEntiteitSleutel de sleutel van de oude entiteit
     * @param historieContext context waarin de historie rijen voor deze a-laag opgeslagen kan worden
     * @return een set van verschillen voor het verwijderen van de historie van een A-laag entiteit
     */
    public static Set<Verschil> bepaalVerschillenVoorVerwijderdeALaagEntiteit(
            final ALaagHistorieVerzameling aLaagEntiteit,
            final EntiteitSleutel oudeEntiteitSleutel,
            final HistorieContext historieContext) {
        final Set<Verschil> verschillen = new HashSet<>();
        try {
            final Map<String, Collection<FormeleHistorie>> verzameldeHistorie = aLaagEntiteit.verzamelHistorie();
            for (final Map.Entry<String, Collection<FormeleHistorie>> veldHistorieVerzamelingEntry : verzameldeHistorie.entrySet()) {
                final String veldnaam = veldHistorieVerzamelingEntry.getKey();

                for (final FormeleHistorie historie : veldHistorieVerzamelingEntry.getValue()) {
                    final FormeleHistorie pojoHistorie = Entiteit.convertToPojo(historie);
                    final EntiteitSleutel sleutel = SleutelUtil.maakRijSleutel(aLaagEntiteit, oudeEntiteitSleutel, pojoHistorie, veldnaam);
                    final HistorieContext rijVerwijderdContext = HistorieContext.bepaalNieuweHistorieContext(historieContext, pojoHistorie, null);
                    verschillen.add(
                            new Verschil(
                                    sleutel,
                                    pojoHistorie,
                                    null,
                                    VerschilType.RIJ_VERWIJDERD,
                                    rijVerwijderdContext.getBestaandeHistorieRij(),
                                    rijVerwijderdContext.getNieuweHistorieRij()));
                }
            }
        } catch (final ReflectiveOperationException roe) {
            throw new IllegalStateException(roe);
        }
        return verschillen;
    }
}

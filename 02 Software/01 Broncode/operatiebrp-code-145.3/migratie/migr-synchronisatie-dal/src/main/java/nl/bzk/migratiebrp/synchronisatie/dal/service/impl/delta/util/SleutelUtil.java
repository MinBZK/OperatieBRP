/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DynamischeStamtabel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Entiteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Voorkomen;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonDeelnameEuVerkiezingenHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonPersoonskaartHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonUitsluitingKiesrechtHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerificatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerificatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.Sleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.EntiteitSleutel;

/**
 * Utility class voor het gebruik van (Identificatie)Sleutels in Delta functionaliteit.
 */
public final class SleutelUtil {

    private static final String CATEGORIE_07 = "07";

    private SleutelUtil() {
        throw new AssertionError("Er mag geen instantie van de SleutelUtil class gemaakt worden.");
    }

    /**
     * Maakt een sleutel aan de hand van de opgegeven attributen. Als het meegegeven Object o een DeltaEntiteit is, dan
     * wordt de sleutel verder aangevuld met de unique constraint die op de deltaEntiteit zit.
     * @param deltaEntiteit object waar de sleutel voor gemaakt wordt.
     * @param eigenaarSleutel sleutel van de eigenaar van het object.
     * @param veld veld waar deze sleutel voor gemaakt wordt.
     * @return een {@link EntiteitSleutel}
     */
    public static EntiteitSleutel maakSleutel(final Entiteit deltaEntiteit, final EntiteitSleutel eigenaarSleutel, final Field veld){
        return maakSleutel(deltaEntiteit, eigenaarSleutel, veld.getName());
    }

    /**
     * Maakt een sleutel aan de hand van de opgegeven attributen. Als het meegegeven Object oo een DeltaEntiteit is, dan
     * wordt de sleutel verder aangevuld met de unique constraint die op de deltaEntiteit zit.
     * @param deltaEntiteit object waar de sleutel voor gemaakt wordt.
     * @param eigenaarSleutel sleutel van de eigenaar van het object.
     * @param veldnaam veldnaam waar deze sleutel voor gemaakt wordt.
     * @return een {@link EntiteitSleutel}
     */
    public static EntiteitSleutel maakSleutel(final Entiteit deltaEntiteit, final EntiteitSleutel eigenaarSleutel, final String veldnaam) {
        final Class<?> entiteitClass = deltaEntiteit.getClass();
        return new EntiteitSleutel(entiteitClass, veldnaam, eigenaarSleutel);
    }

    /**
     * Vult de meegegeven sleutel aan met de unique contraint eigenschappen van de meegegeven entiteit. Als er geen
     * unique constraint eigenschappen zijn, dan wordt de sleutel niet aangevuld.
     * @param sleutel sleutel die aangevuld moet worden.
     * @param entiteit entiteit die voor de aanvullende gegevens moet zorgen.
     * @param eigenaarSleutel sleutel die gebruikt wordt om het eigenaar veld terug te vinden. Dit veld zal niet gebruikt worden om de sleutel aan te vullen.
     * @throws ReflectiveOperationException als de velden van entiteit niet benaderd kunnen worden dmv reflection
     */
    private static void vulSleutelAan(final Sleutel sleutel, final Object entiteit, final EntiteitSleutel eigenaarSleutel) throws ReflectiveOperationException {
        if (entiteit != null) {
            final Class<?> entiteitClass = entiteit.getClass();
            final Field eigenaarVeld = eigenaarSleutel != null ? SleutelUtil.bepaalEigenaarVeld(entiteit, eigenaarSleutel) : null;

            final Table tableAnnotation = entiteitClass.getAnnotation(Table.class);
            final UniqueConstraint[] uniqueConstraints = tableAnnotation.uniqueConstraints();
            if (uniqueConstraints.length == 0) {
                return;
            }
            final UniqueConstraint uniqueConstraint = uniqueConstraints[0];
            final List<String> sleutelKolommen = new LinkedList<>();
            sleutelKolommen.addAll(Arrays.asList(uniqueConstraint.columnNames()));
            sleutelKolommen.addAll(SleutelUtil.bepaalExtraSleutelKolommen(entiteitClass));

            /*
             * Verwijder kolommen die afkomstig zijn uit datumtijdstempel, zodat deze rijen zonder historie inhoudelijk
             * vergeleken kunnen worden ipv rij verwijderd - rij toegevoegd.
             */
            sleutelKolommen.removeAll(SleutelUtil.bepaalKolommenDieAfkomstigZijnUitDatumTijdStempel(entiteit));

            for (final Field objectVeld : DeltaUtil.getDeclaredEntityFields(entiteitClass)) {
                SleutelUtil.verwerkVeldInEntiteitSleutel(sleutel, entiteit, objectVeld, eigenaarVeld, sleutelKolommen);
            }
        }
    }

    private static void verwerkVeldInEntiteitSleutel(
            final Sleutel sleutel,
            final Object entiteit,
            final Field objectVeld,
            final Field eigenaarVeld,
            final List<String> sleutelKolommen) throws ReflectiveOperationException {
        final Id idColumn = objectVeld.getAnnotation(Id.class);
        final boolean isBrpActieEntiteit = BRPActie.class.isAssignableFrom(entiteit.getClass());

        if (objectVeld.equals(eigenaarVeld) || idColumn != null && !isBrpActieEntiteit) {
            return;
        }

        final Column column = objectVeld.getAnnotation(Column.class);
        objectVeld.setAccessible(true);

        if (column != null) {
            verwerkDatabaseVeld(sleutel, entiteit, objectVeld, sleutelKolommen, idColumn, column);
        } else if (DeltaUtil.isDynamischeStamtabelVeld(objectVeld)) {
            verwerkDynamischStamtabelVeld(sleutel, entiteit, objectVeld, sleutelKolommen);
        }
    }

    private static void verwerkDatabaseVeld(
            final Sleutel sleutel,
            final Object entiteit,
            final Field objectVeld,
            final List<String> sleutelKolommen,
            final Id idColumn,
            final Column column) throws IllegalAccessException {
        final Object waarde = objectVeld.get(entiteit);
        if (idColumn != null) {
            sleutel.setId(waarde != null ? (Long) waarde : null);
        } else {
            final String kolomnaam = column.name();
            if (!"pers".equals(kolomnaam) && sleutelKolommen.contains(kolomnaam) && waarde != null) {
                sleutel.addSleuteldeel(kolomnaam, waarde);
            }
        }
    }

    private static void verwerkDynamischStamtabelVeld(final Sleutel sleutel, final Object entiteit, final Field objectVeld, final List<String> sleutelKolommen)
            throws IllegalAccessException {
        final JoinColumn joinColumn = objectVeld.getAnnotation(JoinColumn.class);
        final String kolomnaam = SleutelUtil.bepaalKolomnaam(joinColumn.name(), objectVeld);
        if (sleutelKolommen.contains(kolomnaam)) {
            final DynamischeStamtabel veldAttribuut = (DynamischeStamtabel) objectVeld.get(entiteit);
            Number id = null;
            if (veldAttribuut != null) {
                id = veldAttribuut.getId();
            }
            sleutel.addSleuteldeel(kolomnaam, id);
        }
    }

    private static String bepaalKolomnaam(final String annotatieNaam, final Field veld) {
        final String naam;
        if (annotatieNaam == null || annotatieNaam.length() == 0) {
            naam = veld.getName();
        } else {
            naam = annotatieNaam;
        }
        return naam;
    }

    private static List<String> bepaalExtraSleutelKolommen(final Class<?> entiteitClass) {
        final List<String> extraKolommen = new ArrayList<>();
        if (PersoonVerificatie.class.isAssignableFrom(entiteitClass)) {
            extraKolommen.addAll(Arrays.asList("dat", "partij", "srt"));
        }
        return extraKolommen;
    }

    private static List<String> bepaalKolommenDieAfkomstigZijnUitDatumTijdStempel(final Object entiteit) {
        if (isBrpGroepMetTsRegAfkomstigUitDatumTijdStempel(entiteit)) {
            return Collections.singletonList("tsreg");
        }

        return Collections.emptyList();
    }

    /**
     * Bepaal of de gegeven BRP groep een tsreg heeft die afkomstig is uit een datumtijdstempel.
     * @param entiteit de entiteit
     * @return true als dit een groep is met uit Cat07 afgeleide historie.
     */
    public static boolean isBrpGroepMetTsRegAfkomstigUitDatumTijdStempel(final Object entiteit) {
        final Object entiteitPojo = Entiteit.convertToPojo(entiteit);
        final Class<?> entiteitClass = entiteitPojo.getClass();
        final boolean isCat07Groep =
                PersoonPersoonskaartHistorie.class.isAssignableFrom(entiteitClass) || PersoonVerificatieHistorie.class.isAssignableFrom(entiteitClass);
        final boolean isCat13Groep =
                PersoonDeelnameEuVerkiezingenHistorie.class.isAssignableFrom(entiteitClass)
                        || PersoonUitsluitingKiesrechtHistorie.class.isAssignableFrom(entiteitClass);
        final boolean isCat07Indicatie =
                PersoonIndicatieHistorie.class.isAssignableFrom(entiteitClass) && isCat07HistorieIndicatieGroep((PersoonIndicatieHistorie) entiteit);

        final boolean isCat07 = isCat07Groep || isCat07Indicatie || isBijhoudingGroepAfkomstigUitCategorie07(entiteitPojo);
        return isCat07 || isCat13Groep;
    }

    private static boolean isBijhoudingGroepAfkomstigUitCategorie07(final Object entiteit) {
        boolean result = false;
        if ((entiteit instanceof PersoonBijhoudingHistorie) && ((PersoonBijhoudingHistorie) entiteit).getActieInhoud() != null) {
            final Lo3Voorkomen lo3Voorkomen = ((PersoonBijhoudingHistorie) entiteit).getActieInhoud().getLo3Voorkomen();
            result = lo3Voorkomen != null && CATEGORIE_07.equals(lo3Voorkomen.getCategorie());
        }
        return result;
    }

    private static boolean isCat07HistorieIndicatieGroep(final PersoonIndicatieHistorie indicatie) {
        final SoortIndicatie soortIndicatie = indicatie.getPersoonIndicatie().getSoortIndicatie();
        return SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE.equals(soortIndicatie)
                || SoortIndicatie.VOLLEDIGE_VERSTREKKINGSBEPERKING.equals(soortIndicatie);
    }

    /**
     * Bepaal welk veld in een object de referentie naar de eigenaar van het object bevat. Gaat ervan uit dat er
     * maximaal 1 veld in het object is met hetzelfde type als de eigenaar.
     * @param entiteit Het object wn het eigenaar veld moet worden bepaald
     * @param eigenaarSleutel De sleutel van de eigenaar van het object
     * @return Het eigenaar veld, of null als dit niet bepaald kon worden.
     * @throws java.lang.IllegalAccessException als het object o niet als Collection kan worden gemaakt
     */
    private static Field bepaalEigenaarVeld(final Object entiteit, final EntiteitSleutel eigenaarSleutel) throws IllegalAccessException {
        final Class<?> eigenaarClass = eigenaarSleutel.getEntiteit();
        final Class<?> entiteitClass = Entiteit.bepaalEntiteitClass(entiteit.getClass());

        Field eigenaarVeld = null;
        for (final Field veld : DeltaUtil.getDeclaredEntityFields(entiteitClass)) {
            if (DeltaUtil.isEigenaarVeld(veld, entiteit, eigenaarClass)) {
                eigenaarVeld = veld;
                break;
            }
        }

        return eigenaarVeld;
    }

    /**
     * Maakt een sleutel aan voor een rij in een collectie.
     * @param eigenaar eigenaar van de collectie.
     * @param eigenaarSleutel de sleutel van de eigenaar van de collectie.
     * @param lijstInhoudObject inhoud object van de collectie
     * @param veldnaam veld waar de collectie zit.
     * @return de sleutel voor de rij van de lijst
     * @throws ReflectiveOperationException als de velden van o niet benaderd kunnen worden dmv reflection
     */
    public static EntiteitSleutel maakRijSleutel(
            final Entiteit eigenaar,
            final EntiteitSleutel eigenaarSleutel,
            final Entiteit lijstInhoudObject,
            final String veldnaam) throws ReflectiveOperationException {
        final EntiteitSleutel sleutel = new EntiteitSleutel(eigenaar.getClass(), veldnaam, eigenaarSleutel);

        final Entiteit lijstInhoudPojo = Entiteit.convertToPojo(lijstInhoudObject);
        final Class<?> lijstInhoudPojoClass = lijstInhoudPojo.getClass();
        if (lijstInhoudPojoClass.getAnnotation(Entity.class) != null) {
            vulSleutelAan(sleutel, lijstInhoudPojo, eigenaarSleutel);
        }

        if (PersoonBijhoudingHistorie.class.isAssignableFrom(lijstInhoudObject.getClass())) {
            final Lo3Voorkomen voorkomen = ((PersoonBijhoudingHistorie) lijstInhoudObject).getActieInhoud().getLo3Voorkomen();
            if (voorkomen != null && voorkomen.getCategorie().equals(SleutelUtil.CATEGORIE_07)) {
                sleutel.addSleuteldeel(EntiteitSleutel.SLEUTELDEEL_BIJHOUDING_CATEGORIE, voorkomen.getCategorie());
            }
        }

        return sleutel;
    }
}

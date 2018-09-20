/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.Sleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AbstractFormeleHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.EntiteitSleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.FormeleHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.GegevenInOnderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonAdres;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonAdresHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonBijhoudingHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonGeboorteHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonGeslachtsaanduidingHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonGeslachtsnaamcomponentHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonIDHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonInschrijvingHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonMigratieHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonNaamgebruikHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonNummerverwijzingHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonSamengesteldeNaamHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonVoornaamHistorie;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Object dat een verschil in een BRP-PL lijst bevat. Onderscheid tussen de verschillende Verschil-objecten wordt gedaan
 * dmv een {@link nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.EntiteitSleutel}. Verder kan er aan dit
 * object worden gezien of de wijziging een toevoeging dan wel verwijdering is van een BRP-rij of dat er een element
 * binnen een rij veranderd is.
 */
public final class Verschil {
    private static final Set<Class> CLASSES_TOEGESTAAN_VOOR_A_NUMMER_WIJZIGING;
    private static final Set<String> PERSOON_ATTRIBUTEN_TOEGESTAAN_VOOR_A_NUMMER_WIJZIGING;
    private static final Set<String> M_RIJ_ATTRIBUTEN_TOEGESTAAN_VOOR_WIJZIGING;
    private static final Set<Class> CLASSES_TOEGESTAAN_VOOR_INFRA_WIJZIGING;
    private static final Set<String> PERSOON_ATTRIBUTEN_TOEGSTAAN_VOOR_INFRA_WIJZIGING;

    private final Sleutel sleutel;
    private final Object oudeWaarde;
    private final Object nieuweWaarde;
    private final VerschilType verschilType;
    private final FormeleHistorie bestaandeHistorieRij;
    private final FormeleHistorie nieuweHistorieRij;
    /**
     * Boolean om aan te geven of dit verschil tijdens de consolidatie van acties is aangemaakt of niet.
     */
    private boolean isConsolidatieVerschil;

    static {
        M_RIJ_ATTRIBUTEN_TOEGESTAAN_VOOR_WIJZIGING =
                new HashSet<>(Arrays.asList("indicatieVoorkomenTbvLeveringMutaties", "datumTijdVerval", "actieVerval", "actieVervalTbvLeveringMutaties"));

        CLASSES_TOEGESTAAN_VOOR_A_NUMMER_WIJZIGING = new HashSet<>();
        CLASSES_TOEGESTAAN_VOOR_A_NUMMER_WIJZIGING.add(PersoonSamengesteldeNaamHistorie.class);
        CLASSES_TOEGESTAAN_VOOR_A_NUMMER_WIJZIGING.add(PersoonGeslachtsnaamcomponentHistorie.class);
        CLASSES_TOEGESTAAN_VOOR_A_NUMMER_WIJZIGING.add(PersoonGeslachtsaanduidingHistorie.class);
        CLASSES_TOEGESTAAN_VOOR_A_NUMMER_WIJZIGING.add(PersoonVoornaamHistorie.class);
        CLASSES_TOEGESTAAN_VOOR_A_NUMMER_WIJZIGING.add(PersoonNaamgebruikHistorie.class);
        CLASSES_TOEGESTAAN_VOOR_A_NUMMER_WIJZIGING.add(PersoonGeboorteHistorie.class);
        CLASSES_TOEGESTAAN_VOOR_A_NUMMER_WIJZIGING.add(PersoonIDHistorie.class);
        CLASSES_TOEGESTAAN_VOOR_A_NUMMER_WIJZIGING.add(PersoonNummerverwijzingHistorie.class);
        CLASSES_TOEGESTAAN_VOOR_A_NUMMER_WIJZIGING.add(PersoonInschrijvingHistorie.class);

        PERSOON_ATTRIBUTEN_TOEGESTAAN_VOOR_A_NUMMER_WIJZIGING =
                new HashSet<>(Arrays.asList(
                    "datumtijdstempel",
                    "versienummer",
                    "vorigeAdministratienummer",
                    "tijdstipLaatsteWijzigingGbaSystematiek",
                    "administratienummer",
                    "tijdstipLaatsteWijziging"));

        CLASSES_TOEGESTAAN_VOOR_INFRA_WIJZIGING = new HashSet<>();
        CLASSES_TOEGESTAAN_VOOR_INFRA_WIJZIGING.add(PersoonAdres.class);
        CLASSES_TOEGESTAAN_VOOR_INFRA_WIJZIGING.add(PersoonInschrijvingHistorie.class);
        CLASSES_TOEGESTAAN_VOOR_INFRA_WIJZIGING.add(PersoonAdresHistorie.class);
        CLASSES_TOEGESTAAN_VOOR_INFRA_WIJZIGING.add(PersoonBijhoudingHistorie.class);
        CLASSES_TOEGESTAAN_VOOR_INFRA_WIJZIGING.add(PersoonMigratieHistorie.class);

        PERSOON_ATTRIBUTEN_TOEGSTAAN_VOOR_INFRA_WIJZIGING =
                new HashSet<>(Arrays.asList(
                    Persoon.PERSOONMIGRATIEHISTORIESET,
                    Persoon.PERSOONBIJHOUDINGHISTORIESET,
                    Persoon.PERSOONADRESSET,
                    Persoon.PERSOONINSCHRIJVINGHISTORIESET,
                    Persoon.TIJDSTIPLAATSTEWIJZIGING,
                    Persoon.VERSIENUMMER,
                    Persoon.DATUMTIJDSTEMPEL,
                    Persoon.SOORTMIGRATIEID,
                    Persoon.BUITENLANDSADRESREGEL1MIGRATIE,
                    Persoon.BUITENLANDSADRESREGEL2MIGRATIE,
                    Persoon.BUITENLANDSADRESREGEL3MIGRATIE,
                    Persoon.BUITENLANDSADRESREGEL4MIGRATIE,
                    Persoon.BUITENLANDSADRESREGEL5MIGRATIE,
                    Persoon.BUITENLANDSADRESREGEL6MIGRATIE,
                    Persoon.TIJDSTIPLAATSTEWIJZIGINGGBASYSTEMATIEK,
                    Persoon.BIJHOUDINGSPARTIJ,
                    AbstractFormeleHistorie.DATUM_TIJD_VERVAL,
                    Persoon.REDENWIJZIGINGMIGRATIE,
                    Persoon.LANDOFGEBIEDMIGRATIE,
                    Persoon.AANGEVERMIGRATIE));
    }

    /**
     * Constructor voor een Verschil van een element. Gebruik liever de factory-methode
     * {@link #maakVerschil(Sleutel, Object, Object, FormeleHistorie, FormeleHistorie)}.
     *
     * @param sleutel
     *            De sleutel die dit verschil uniek identificeert, mag niet null zijn
     * @param oudeWaarde
     *            Dit kan de oude waarde zijn van een element in een rij of een complete rij als deze verwijderd is.
     * @param nieuweWaarde
     *            Dit kan de nieuwe waarde zijn van een element in een rij of een complete rij als deze toegevoegd is.
     * @param bestaandeHistorieRij
     *            De bestaande (opgeslagen) historie rij waar dit verschil bij hoort
     * @param nieuweHistorieRij
     *            De nieuwe (kluizenaar) historie rij waar dit verschil bij hoort
     */
    public Verschil(
        final Sleutel sleutel,
        final Object oudeWaarde,
        final Object nieuweWaarde,
        final FormeleHistorie bestaandeHistorieRij,
        final FormeleHistorie nieuweHistorieRij)
    {
        this(sleutel, oudeWaarde, nieuweWaarde, bepaalVerschilType(oudeWaarde, nieuweWaarde), bestaandeHistorieRij, nieuweHistorieRij);
    }

    /**
     * Constructor voor een Verschil van een rij uit een verzameling.
     *
     * @param sleutel
     *            De sleutel die dit verschil uniek identificeert, mag niet null zijn
     * @param oudeWaarde
     *            Dit kan de oude waarde zijn van een element in een rij of een complete rij als deze verwijderd is.
     * @param nieuweWaarde
     *            Dit kan de nieuwe waarde zijn van een element in een rij of een complete rij als deze toegevoegd is.
     * @param verschilType
     *            type van dit verschil
     * @param bestaandeHistorieRij
     *            De bestaande (opgeslagen) historie rij waar dit verschil bij hoort
     * @param nieuweHistorieRij
     *            De nieuwe (kluizenaar) historie rij waar dit verschil bij hoort
     */
    public Verschil(
        final Sleutel sleutel,
        final Object oudeWaarde,
        final Object nieuweWaarde,
        final VerschilType verschilType,
        final FormeleHistorie bestaandeHistorieRij,
        final FormeleHistorie nieuweHistorieRij)
    {
        ValidationUtils.controleerOpNullWaarden("sleutel mag niet null zijn", sleutel);
        this.sleutel = sleutel;
        this.oudeWaarde = oudeWaarde;
        this.nieuweWaarde = nieuweWaarde;
        this.verschilType = verschilType;
        this.bestaandeHistorieRij = bestaandeHistorieRij;
        this.nieuweHistorieRij = nieuweHistorieRij;
    }

    /**
     * Factory method om een consolidatie verschil te maken waarbij een nieuwe rij wordt toegevoegd.
     *
     * @param sleutel
     *            de sleutel voor het verschil
     * @param nieuweRij
     *            de rij die toegevoegd moet worden.
     * @return het gevraagde verschil
     */
    public static Verschil maakConsolidatieVerschilRijToevoegen(final Sleutel sleutel, final FormeleHistorie nieuweRij) {
        final Verschil verschil = new Verschil(sleutel, null, nieuweRij, VerschilType.RIJ_TOEGEVOEGD, null, nieuweRij);
        verschil.isConsolidatieVerschil = true;
        return verschil;
    }

    /**
     * Factory method om een consolidatie verschil te maken waarbij de historie context alleen wordt ingevuld.
     *
     * @param sleutel
     *            de sleutel voor het verschil
     * @param historie
     *            de rij die toegevoegd moet worden.
     * @return het gevraagde verschil
     */
    public static Verschil maakConsolidatieHistorieContextVerschil(final Sleutel sleutel, final FormeleHistorie historie) {
        final Verschil verschil = new Verschil(sleutel, null, null, null, historie, null);
        verschil.isConsolidatieVerschil = true;
        return verschil;
    }

    /**
     * Factory method om een consolidatie verschil te maken.
     *
     * @param sleutel
     *            de sleutel voor het verschil
     * @param bestaandeWaarde
     *            de bestaande waarde
     * @param nieuweWaarde
     *            de nieuwe waarde
     * @param bestaandeHistorieRij
     *            de bestaande historie rij
     * @param nieuweHistorieRij
     *            de nieuwe historie rijd.
     * @return het gevraagde verschil
     */
    public static Verschil maakConsolidatieVerschil(
        final Sleutel sleutel,
        final Object bestaandeWaarde,
        final Object nieuweWaarde,
        final FormeleHistorie bestaandeHistorieRij,
        final FormeleHistorie nieuweHistorieRij)
    {
        final Verschil verschil = new Verschil(sleutel, bestaandeWaarde, nieuweWaarde, bestaandeHistorieRij, nieuweHistorieRij);
        verschil.isConsolidatieVerschil = true;
        return verschil;
    }

    /**
     * Factory method om een verschil te maken.
     *
     * @param sleutel
     *            de sleutel voor het verschil
     * @param bestaandeWaarde
     *            de bestaande waarde
     * @param nieuweWaarde
     *            de nieuwe waarde
     * @param bestaandeHistorieRij
     *            de bestaande historie rij
     * @param nieuweHistorieRij
     *            de nieuwe historie rijd.
     * @return het gevraagde verschil
     */
    public static Verschil maakVerschil(
        final Sleutel sleutel,
        final Object bestaandeWaarde,
        final Object nieuweWaarde,
        final FormeleHistorie bestaandeHistorieRij,
        final FormeleHistorie nieuweHistorieRij)
    {
        return new Verschil(sleutel, bestaandeWaarde, nieuweWaarde, bestaandeHistorieRij, nieuweHistorieRij);
    }

    /**
     * @return Geeft aan of dit verschil tijdens een consolidatie proces is ontstaan of niet.
     */
    public boolean isConsolidatieVerschil() {
        return isConsolidatieVerschil;
    }

    /**
     * Geef de waarde van sleutel.
     *
     * @return de sleutel
     */
    public Sleutel getSleutel() {
        return sleutel;
    }

    /**
     * Geef de waarde van oude waarde.
     *
     * @return oude waarde
     */
    public Object getOudeWaarde() {
        return oudeWaarde;
    }

    /**
     * Geef de waarde van nieuwe waarde.
     *
     * @return nieuwe waarde
     */
    public Object getNieuweWaarde() {
        return nieuweWaarde;
    }

    /**
     * Geef de waarde van verschil type.
     *
     * @return verschil type
     */
    public VerschilType getVerschilType() {
        return verschilType;
    }

    /**
     * Geef de bestaande historie rij waar dit verschil bij hoort, of null als dit verschil niet bij een historie rij
     * hoort.
     *
     * @return de bestaande historie rij
     */
    public FormeleHistorie getBestaandeHistorieRij() {
        return bestaandeHistorieRij;
    }

    /**
     * Geef de nieuwe historie rij waar dit verschil bij hoort, of null als dit verschil niet bij een historie rij
     * hoort.
     *
     * @return de nieuwe historie rij
     */
    public FormeleHistorie getNieuweHistorieRij() {
        return nieuweHistorieRij;
    }

    /**
     * Maakt een kopie van dit verschil en vult de sleutel van de kopie met de nieuwe sleutel.
     *
     * @param nieuweSleutel
     *            de nieuwe sleutel
     * @return een nieuw verschil object
     */
    public Verschil maakKopieMetNieuweSleutel(final Sleutel nieuweSleutel) {
        return new Verschil(nieuweSleutel, oudeWaarde, nieuweWaarde, verschilType, bestaandeHistorieRij, nieuweHistorieRij);
    }

    /**
     * @return Geeft aan of dit verschil een verschil op onderzoek (gegeven in onderzoek) betreft.
     */
    public boolean isOnderzoekVerschil() {
        return GegevenInOnderzoek.GEGEVEN_IN_ONDERZOEK.equals(sleutel.getVeld());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("verschiltype", verschilType).toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Verschil verschil = (Verschil) o;

        return new EqualsBuilder().append(sleutel, verschil.sleutel).append(verschilType, verschil.verschilType).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(sleutel).append(verschilType).toHashCode();
    }

    private static VerschilType bepaalVerschilType(final Object oudeWaardeParam, final Object nieuweWaardeParam) {
        final VerschilType result;
        if (oudeWaardeParam == null) {
            result = VerschilType.ELEMENT_NIEUW;
        } else if (nieuweWaardeParam == null) {
            result = VerschilType.ELEMENT_VERWIJDERD;
        } else {
            result = VerschilType.ELEMENT_AANGEPAST;
        }
        return result;
    }

    /**
     * Controleert of het verschil toegestaan is voor een a-nummer wijziging.
     * 
     * @return true als het verschil is toegestaan bij een a-nummer wijziging.
     */
    public boolean isToegestaanVoorAnummerWijziging() {
        if (!(sleutel instanceof EntiteitSleutel)) {
            return false;
        }
        final boolean result;
        switch (verschilType) {
            case RIJ_TOEGEVOEGD:
                result = CLASSES_TOEGESTAAN_VOOR_A_NUMMER_WIJZIGING.contains(nieuweWaarde.getClass());
                break;
            case ELEMENT_AANGEPAST:
            case ELEMENT_NIEUW:
                final Class<?> entiteitClass = ((EntiteitSleutel) sleutel).getEntiteit();
                if (Persoon.class.isAssignableFrom(entiteitClass)) {
                    result = PERSOON_ATTRIBUTEN_TOEGESTAAN_VOOR_A_NUMMER_WIJZIGING.contains(sleutel.getVeld());
                } else {
                    result =
                            FormeleHistorie.class.isAssignableFrom(entiteitClass)
                                    && M_RIJ_ATTRIBUTEN_TOEGESTAAN_VOOR_WIJZIGING.contains(sleutel.getVeld());
                }
                break;
            case NIEUWE_RIJ_ELEMENT_AANGEPAST:
                result = CLASSES_TOEGESTAAN_VOOR_A_NUMMER_WIJZIGING.contains(((EntiteitSleutel) sleutel).getEntiteit());
                break;
            default:
                result = false;
        }
        return result;
    }

    /**
     * @return true als het verschil is toegestaan voor een infrastructurele wijziging
     */
    public boolean isToegestaanVoorInfrastructureleWijziging() {
        if (!(sleutel instanceof EntiteitSleutel)) {
            return false;
        }
        final Class<?> sleutelEntiteit = ((EntiteitSleutel) sleutel).getEntiteit();
        final boolean isSleutelEntiteitToegestaneClass = CLASSES_TOEGESTAAN_VOOR_INFRA_WIJZIGING.contains(sleutelEntiteit);
        final boolean result;
        switch (verschilType) {
            case RIJ_TOEGEVOEGD:
                result = CLASSES_TOEGESTAAN_VOOR_INFRA_WIJZIGING.contains(nieuweWaarde.getClass());
                break;
            case ELEMENT_AANGEPAST:
            case ELEMENT_NIEUW:
            case ELEMENT_VERWIJDERD:
                final String veldnaam = sleutel.getVeld();
                result =
                        isSleutelEntiteitToegestaneClass
                                || (sleutelEntiteit.isAssignableFrom(Persoon.class) && PERSOON_ATTRIBUTEN_TOEGSTAAN_VOOR_INFRA_WIJZIGING.contains(veldnaam));
                break;
            case NIEUWE_RIJ_ELEMENT_AANGEPAST:
                result = isSleutelEntiteitToegestaneClass;
                break;
            default:
                result = false;
        }
        return result;
    }
}

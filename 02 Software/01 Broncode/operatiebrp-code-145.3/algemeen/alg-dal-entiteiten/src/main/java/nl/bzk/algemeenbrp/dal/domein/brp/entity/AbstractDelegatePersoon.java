/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AdellijkeTitel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Geslachtsaanduiding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Naamgebruik;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Predicaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMigratie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;

/**
 * Deze abstract class biedt delegeer functionaliteit voor de Persoon class. Er kunnen meerdere
 * delegates worden opgegeven, in dat geval zal een setter aanroep resulteren in het aanroepen van
 * de de setters van alle delegates. Een getter aanroep resulteert in het aanroepen van de getter
 * van de eerste delegate.
 */
public abstract class AbstractDelegatePersoon extends Persoon implements DelegateEntiteit<Persoon> {

    private final List<Persoon> delegates;
    private final boolean isReadOnly;

    /**
     * Initialiseert AbstractDelegatePersoon.
     * @param isReadOnly true als de setter methodes niet mogen delegeren, anders false
     * @param delegates de delegates waar de getter en setter methodes naar moeten delegeren, moet minimaal 1 delegate zijn
     */
    protected AbstractDelegatePersoon(final boolean isReadOnly, final Persoon... delegates) {
        if (delegates.length < 1) {
            throw new IllegalArgumentException("Minimaal 1 delegate object verwacht.");
        }
        this.isReadOnly = isReadOnly;
        this.delegates = new ArrayList<>(Arrays.asList(delegates));
    }

    @Override
    public final List<Persoon> getDelegates() {
        return Collections.unmodifiableList(delegates);
    }

    @Override
    public final Persoon getDelegate() {
        throw new UnsupportedOperationException("Deze methode is niet geimplementeerd voor de DelegatePersoon. Gebruik getDeletages");
    }

    /**
     * Voegt een delegate toe, vanaf dat moment worden alle bewerkingen op alle delegates toegepast.
     * @param delegate de toe te voegen delegate
     */
    public final void addDelegate(final Persoon delegate) {
        delegates.add(delegate);
    }

    @Override
    public boolean isReadOnly() {
        return isReadOnly;
    }

    private void delegateToSetter(final Method setterMethode, final Object waarde) {
        controleerMagWordenGewijzigd();
        for (final Persoon delegate : delegates) {
            try {
                setterMethode.invoke(delegate, waarde);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new IllegalArgumentException("Setter methode kan niet worden aangeroepen.", e);
            }
        }
    }

    private Object delegateToGetter(final Method getterMethode) {
        try {
            return getterMethode.invoke(delegates.get(0));
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException("Getter methode kan niet worden aangeroepen.", e);
        }
    }

    private Method getDelegateMethod(final String naam, final Class<?>... parameterTypes) {
        try {
            return Persoon.class.getMethod(naam, parameterTypes);
        } catch (final NoSuchMethodException e) {
            throw new IllegalArgumentException("Delegate method kan niet gevonden worden.", e);
        }
    }

    private void controleerEenDelegate() {
        if (delegates.size() > 1) {
            throw new UnsupportedOperationException("Deze methodes kan niet worden gedelegeerd naar meerdere delegates.");
        }
    }

    private void controleerMagWordenGewijzigd() {
        if (isReadOnly) {
            throw new UnsupportedOperationException("Een readonly persoon kan niet worden gewijzigd.");
        }
    }

    /******* Delegate methodes. *******/

    @Override
    public void setGegevenInOnderzoek(final GegevenInOnderzoek gegevenInOnderzoek) {
        delegateToSetter(getDelegateMethod("setGegevenInOnderzoek", GegevenInOnderzoek.class), gegevenInOnderzoek);
    }

    @Override
    public Collection<Element> getElementenInOnderzoek() {
        return (Collection<Element>) delegateToGetter(getDelegateMethod("getElementenInOnderzoek"));
    }

    @Override
    public Map<Element, GegevenInOnderzoek> getGegevenInOnderzoekPerElementMap() {
        return (Map<Element, GegevenInOnderzoek>) delegateToGetter(getDelegateMethod("getGegevenInOnderzoekPerElementMap"));
    }

    @Override
    public void verwijderGegevenInOnderzoek(final Element gegevenInOnderzoek) {
        delegateToSetter(getDelegateMethod("verwijderGegevenInOnderzoek", Element.class), gegevenInOnderzoek);
    }

    @Override
    public String toString() {
        return (String) delegateToGetter(getDelegateMethod("toString"));
    }

    @Override
    public Long getId() {
        return (Long) delegateToGetter(getDelegateMethod("getId"));
    }

    @Override
    public void setId(final Long id) {
        delegateToSetter(getDelegateMethod("setId", Long.class), id);
    }

    @Override
    public Long getLockVersie() {
        return (Long) delegateToGetter(getDelegateMethod("getLockVersie"));
    }

    @Override
    public String getAdministratienummer() {
        return (String) delegateToGetter(getDelegateMethod("getAdministratienummer"));
    }

    @Override
    public void setAdministratienummer(final String administratienummer) {
        delegateToSetter(getDelegateMethod("setAdministratienummer", String.class), administratienummer);
    }

    @Override
    public AdministratieveHandeling getAdministratieveHandeling() {
        return (AdministratieveHandeling) delegateToGetter(getDelegateMethod("getAdministratieveHandeling"));
    }

    @Override
    public void setAdministratieveHandeling(final AdministratieveHandeling administratieveHandeling) {
        delegateToSetter(getDelegateMethod("setAdministratieveHandeling", AdministratieveHandeling.class), administratieveHandeling);
    }

    @Override
    public Short getSorteervolgorde() {
        return (Short) delegateToGetter(getDelegateMethod("getSorteervolgorde"));
    }

    @Override
    public void setSorteervolgorde(final Short sorteervolgorde) {
        delegateToSetter(getDelegateMethod("setSorteervolgorde", Short.class), sorteervolgorde);
    }

    @Override
    public String getBuitenlandsAdresRegel1Migratie() {
        return (String) delegateToGetter(getDelegateMethod("getBuitenlandsAdresRegel1Migratie"));
    }

    @Override
    public void setBuitenlandsAdresRegel1Migratie(final String buitenlandsAdresRegel1Migratie) {
        delegateToSetter(getDelegateMethod("setBuitenlandsAdresRegel1Migratie", String.class), buitenlandsAdresRegel1Migratie);
    }

    @Override
    public String getBuitenlandsAdresRegel2Migratie() {
        return (String) delegateToGetter(getDelegateMethod("getBuitenlandsAdresRegel2Migratie"));
    }

    @Override
    public void setBuitenlandsAdresRegel2Migratie(final String buitenlandsAdresRegel2Migratie) {
        delegateToSetter(getDelegateMethod("setBuitenlandsAdresRegel2Migratie", String.class), buitenlandsAdresRegel2Migratie);
    }

    @Override
    public String getBuitenlandsAdresRegel3Migratie() {
        return (String) delegateToGetter(getDelegateMethod("getBuitenlandsAdresRegel3Migratie"));
    }

    @Override
    public void setBuitenlandsAdresRegel3Migratie(final String buitenlandsAdresRegel3Migratie) {
        delegateToSetter(getDelegateMethod("setBuitenlandsAdresRegel3Migratie", String.class), buitenlandsAdresRegel3Migratie);
    }

    @Override
    public String getBuitenlandsAdresRegel4Migratie() {
        return (String) delegateToGetter(getDelegateMethod("getBuitenlandsAdresRegel4Migratie"));
    }

    @Override
    public void setBuitenlandsAdresRegel4Migratie(final String buitenlandsAdresRegel4Migratie) {
        delegateToSetter(getDelegateMethod("setBuitenlandsAdresRegel4Migratie", String.class), buitenlandsAdresRegel4Migratie);
    }

    @Override
    public String getBuitenlandsAdresRegel5Migratie() {
        return (String) delegateToGetter(getDelegateMethod("getBuitenlandsAdresRegel5Migratie"));
    }

    @Override
    public void setBuitenlandsAdresRegel5Migratie(final String buitenlandsAdresRegel5Migratie) {
        delegateToSetter(getDelegateMethod("setBuitenlandsAdresRegel5Migratie", String.class), buitenlandsAdresRegel5Migratie);
    }

    @Override
    public String getBuitenlandsAdresRegel6Migratie() {
        return (String) delegateToGetter(getDelegateMethod("getBuitenlandsAdresRegel6Migratie"));
    }

    @Override
    public void setBuitenlandsAdresRegel6Migratie(final String buitenlandsAdresRegel6Migratie) {
        delegateToSetter(getDelegateMethod("setBuitenlandsAdresRegel6Migratie", String.class), buitenlandsAdresRegel6Migratie);
    }

    @Override
    public String getBuitenlandsePlaatsGeboorte() {
        return (String) delegateToGetter(getDelegateMethod("getBuitenlandsePlaatsGeboorte"));
    }

    @Override
    public void setBuitenlandsePlaatsGeboorte(final String buitenlandsePlaatsGeboorte) {
        delegateToSetter(getDelegateMethod("setBuitenlandsePlaatsGeboorte", String.class), buitenlandsePlaatsGeboorte);
    }

    @Override
    public String getBuitenlandsePlaatsOverlijden() {
        return (String) delegateToGetter(getDelegateMethod("getBuitenlandsePlaatsOverlijden"));
    }

    @Override
    public void setBuitenlandsePlaatsOverlijden(final String buitenlandsePlaatsOverlijden) {
        delegateToSetter(getDelegateMethod("setBuitenlandsePlaatsOverlijden", String.class), buitenlandsePlaatsOverlijden);
    }

    @Override
    public String getBuitenlandseRegioGeboorte() {
        return (String) delegateToGetter(getDelegateMethod("getBuitenlandseRegioGeboorte"));
    }

    @Override
    public void setBuitenlandseRegioGeboorte(final String buitenlandseRegioGeboorte) {
        delegateToSetter(getDelegateMethod("setBuitenlandseRegioGeboorte", String.class), buitenlandseRegioGeboorte);
    }

    @Override
    public String getBuitenlandseRegioOverlijden() {
        return (String) delegateToGetter(getDelegateMethod("getBuitenlandseRegioOverlijden"));
    }

    @Override
    public void setBuitenlandseRegioOverlijden(final String buitenlandseRegioOverlijden) {
        delegateToSetter(getDelegateMethod("setBuitenlandseRegioOverlijden", String.class), buitenlandseRegioOverlijden);
    }

    @Override
    public String getBurgerservicenummer() {
        return (String) delegateToGetter(getDelegateMethod("getBurgerservicenummer"));
    }

    @Override
    public void setBurgerservicenummer(final String burgerservicenummer) {
        delegateToSetter(getDelegateMethod("setBurgerservicenummer", String.class), burgerservicenummer);
    }

    @Override
    public Integer getDatumAanleidingAanpassingDeelnameEuVerkiezing() {
        return (Integer) delegateToGetter(getDelegateMethod("getDatumAanleidingAanpassingDeelnameEuVerkiezing"));
    }

    @Override
    public void setDatumAanleidingAanpassingDeelnameEuVerkiezingen(final Integer datumAanleidingAanpassingDeelnameEUVerkiezing) {
        delegateToSetter(getDelegateMethod("setDatumAanleidingAanpassingDeelnameEuVerkiezingen", Integer.class), datumAanleidingAanpassingDeelnameEUVerkiezing);
    }

    @Override
    public Integer getDatumAanvangVerblijfsrecht() {
        return (Integer) delegateToGetter(getDelegateMethod("getDatumAanvangVerblijfsrecht"));
    }

    @Override
    public void setDatumAanvangVerblijfsrecht(final Integer datumAanvangVerblijfsrecht) {
        delegateToSetter(getDelegateMethod("setDatumAanvangVerblijfsrecht", Integer.class), datumAanvangVerblijfsrecht);
    }

    @Override
    public Integer getDatumMededelingVerblijfsrecht() {
        return (Integer) delegateToGetter(getDelegateMethod("getDatumMededelingVerblijfsrecht"));
    }

    @Override
    public void setDatumMededelingVerblijfsrecht(final Integer datumMededelingVerblijfsrecht) {
        delegateToSetter(getDelegateMethod("setDatumMededelingVerblijfsrecht", Integer.class), datumMededelingVerblijfsrecht);
    }

    @Override
    public Integer getDatumVoorzienEindeUitsluitingEuVerkiezingen() {
        return (Integer) delegateToGetter(getDelegateMethod("getDatumVoorzienEindeUitsluitingEuVerkiezingen"));
    }

    @Override
    public void setDatumVoorzienEindeUitsluitingEuVerkiezingen(final Integer datumEindeUitsluitingEUKiesrecht) {
        delegateToSetter(getDelegateMethod("setDatumVoorzienEindeUitsluitingEuVerkiezingen", Integer.class), datumEindeUitsluitingEUKiesrecht);
    }

    @Override
    public Integer getDatumVoorzienEindeUitsluitingKiesrecht() {
        return (Integer) delegateToGetter(getDelegateMethod("getDatumVoorzienEindeUitsluitingKiesrecht"));
    }

    @Override
    public void setDatumVoorzienEindeUitsluitingKiesrecht(final Integer datumEindeUitsluitingKiesrecht) {
        delegateToSetter(getDelegateMethod("setDatumVoorzienEindeUitsluitingKiesrecht", Integer.class), datumEindeUitsluitingKiesrecht);
    }

    @Override
    public Integer getDatumGeboorte() {
        return (Integer) delegateToGetter(getDelegateMethod("getDatumGeboorte"));
    }

    @Override
    public void setDatumGeboorte(final Integer datumGeboorte) {
        delegateToSetter(getDelegateMethod("setDatumGeboorte", Integer.class), datumGeboorte);
    }

    @Override
    public Integer getDatumInschrijving() {
        return (Integer) delegateToGetter(getDelegateMethod("getDatumInschrijving"));
    }

    @Override
    public void setDatumInschrijving(final Integer datumInschrijving) {
        delegateToSetter(getDelegateMethod("setDatumInschrijving", Integer.class), datumInschrijving);
    }

    @Override
    public Integer getDatumOverlijden() {
        return (Integer) delegateToGetter(getDelegateMethod("getDatumOverlijden"));
    }

    @Override
    public void setDatumOverlijden(final Integer datumOverlijden) {
        delegateToSetter(getDelegateMethod("setDatumOverlijden", Integer.class), datumOverlijden);
    }

    @Override
    public Integer getDatumVoorzienEindeVerblijfsrecht() {
        return (Integer) delegateToGetter(getDelegateMethod("getDatumVoorzienEindeVerblijfsrecht"));
    }

    @Override
    public void setDatumVoorzienEindeVerblijfsrecht(final Integer datumVoorzienEindeVerblijfsrecht) {
        delegateToSetter(getDelegateMethod("setDatumVoorzienEindeVerblijfsrecht", Integer.class), datumVoorzienEindeVerblijfsrecht);
    }

    @Override
    public String getGeslachtsnaamstam() {
        return (String) delegateToGetter(getDelegateMethod("getGeslachtsnaamstam"));
    }

    @Override
    public void setGeslachtsnaamstam(final String geslachtsnaamstam) {
        delegateToSetter(getDelegateMethod("setGeslachtsnaamstam", String.class), geslachtsnaamstam);
    }

    @Override
    public String getGeslachtsnaamstamNaamgebruik() {
        return (String) delegateToGetter(getDelegateMethod("getGeslachtsnaamstamNaamgebruik"));
    }

    @Override
    public void setGeslachtsnaamstamNaamgebruik(final String geslachtsnaamstamNaamgebruik) {
        delegateToSetter(getDelegateMethod("setGeslachtsnaamstamNaamgebruik", String.class), geslachtsnaamstamNaamgebruik);
    }

    @Override
    public Boolean getIndicatieNaamgebruikAfgeleid() {
        return (Boolean) delegateToGetter(getDelegateMethod("getIndicatieNaamgebruikAfgeleid"));
    }

    @Override
    public void setIndicatieNaamgebruikAfgeleid(final Boolean indicatieNaamgebruikAfgeleid) {
        delegateToSetter(getDelegateMethod("setIndicatieNaamgebruikAfgeleid", Boolean.class), indicatieNaamgebruikAfgeleid);
    }

    @Override
    public Boolean getIndicatieAfgeleid() {
        return (Boolean) delegateToGetter(getDelegateMethod("getIndicatieAfgeleid"));
    }

    @Override
    public void setIndicatieAfgeleid(final Boolean indicatieAfgeleid) {
        delegateToSetter(getDelegateMethod("setIndicatieAfgeleid", Boolean.class), indicatieAfgeleid);
    }

    @Override
    public Boolean getIndicatieDeelnameEuVerkiezingen() {
        return (Boolean) delegateToGetter(getDelegateMethod("getIndicatieDeelnameEuVerkiezingen"));
    }

    @Override
    public void setIndicatieDeelnameEuVerkiezingen(final Boolean indicatieDeelnameEuVerkiezingen) {
        delegateToSetter(getDelegateMethod("setIndicatieDeelnameEuVerkiezingen", Boolean.class), indicatieDeelnameEuVerkiezingen);
    }

    @Override
    public Boolean getIndicatieNamenreeks() {
        return (Boolean) delegateToGetter(getDelegateMethod("getIndicatieNamenreeks"));
    }

    @Override
    public void setIndicatieNamenreeks(final Boolean indicatieNamenreeks) {
        delegateToSetter(getDelegateMethod("setIndicatieNamenreeks", Boolean.class), indicatieNamenreeks);
    }

    @Override
    public Boolean getIndicatiePersoonskaartVolledigGeconverteerd() {
        return (Boolean) delegateToGetter(getDelegateMethod("getIndicatiePersoonskaartVolledigGeconverteerd"));
    }

    @Override
    public void setIndicatiePersoonskaartVolledigGeconverteerd(final Boolean indicatiePersoonskaartVolledigGeconverteerd) {
        delegateToSetter(getDelegateMethod("setIndicatiePersoonskaartVolledigGeconverteerd", Boolean.class), indicatiePersoonskaartVolledigGeconverteerd);
    }

    @Override
    public Boolean getIndicatieUitsluitingKiesrecht() {
        return (Boolean) delegateToGetter(getDelegateMethod("getIndicatieUitsluitingKiesrecht"));
    }

    @Override
    public void setIndicatieUitsluitingKiesrecht(final Boolean indicatieUitsluitingKiesrecht) {
        delegateToSetter(getDelegateMethod("setIndicatieUitsluitingKiesrecht", Boolean.class), indicatieUitsluitingKiesrecht);
    }

    @Override
    public String getOmschrijvingGeboortelocatie() {
        return (String) delegateToGetter(getDelegateMethod("getOmschrijvingGeboortelocatie"));
    }

    @Override
    public void setOmschrijvingGeboortelocatie(final String omschrijvingGeboortelocatie) {
        delegateToSetter(getDelegateMethod("setOmschrijvingGeboortelocatie", String.class), omschrijvingGeboortelocatie);
    }

    @Override
    public String getOmschrijvingLocatieOverlijden() {
        return (String) delegateToGetter(getDelegateMethod("getOmschrijvingLocatieOverlijden"));
    }

    @Override
    public void setOmschrijvingLocatieOverlijden(final String omschrijvingLocatieOverlijden) {
        delegateToSetter(getDelegateMethod("setOmschrijvingLocatieOverlijden", String.class), omschrijvingLocatieOverlijden);
    }

    @Override
    public Character getScheidingsteken() {
        return (Character) delegateToGetter(getDelegateMethod("getScheidingsteken"));
    }

    @Override
    public void setScheidingsteken(final Character scheidingsteken) {
        delegateToSetter(getDelegateMethod("setScheidingsteken", Character.class), scheidingsteken);
    }

    @Override
    public Character getScheidingstekenNaamgebruik() {
        return (Character) delegateToGetter(getDelegateMethod("getScheidingstekenNaamgebruik"));
    }

    @Override
    public void setScheidingstekenNaamgebruik(final Character scheidingstekenNaamgebruik) {
        delegateToSetter(getDelegateMethod("setScheidingstekenNaamgebruik", Character.class), scheidingstekenNaamgebruik);
    }

    @Override
    public Timestamp getTijdstipLaatsteWijziging() {
        return (Timestamp) delegateToGetter(getDelegateMethod("getTijdstipLaatsteWijziging"));
    }

    @Override
    public void setTijdstipLaatsteWijziging(final Timestamp tijdstipLaatsteWijziging) {
        delegateToSetter(getDelegateMethod("setTijdstipLaatsteWijziging", Timestamp.class), tijdstipLaatsteWijziging);
    }

    @Override
    public Timestamp getTijdstipLaatsteWijzigingGbaSystematiek() {
        return (Timestamp) delegateToGetter(getDelegateMethod("getTijdstipLaatsteWijzigingGbaSystematiek"));
    }

    @Override
    public void setTijdstipLaatsteWijzigingGbaSystematiek(final Timestamp tijdstipLaatsteWijzigingGbaSystematiek) {
        delegateToSetter(getDelegateMethod("setTijdstipLaatsteWijzigingGbaSystematiek", Timestamp.class), tijdstipLaatsteWijzigingGbaSystematiek);
    }

    @Override
    public Long getVersienummer() {
        return (Long) delegateToGetter(getDelegateMethod("getVersienummer"));
    }

    @Override
    public void setVersienummer(final Long versienummer) {
        delegateToSetter(getDelegateMethod("setVersienummer", Long.class), versienummer);
    }

    @Override
    public Timestamp getDatumtijdstempel() {
        return (Timestamp) delegateToGetter(getDelegateMethod("getDatumtijdstempel"));
    }

    @Override
    public void setDatumtijdstempel(final Timestamp datumtijdstempel) {
        delegateToSetter(getDelegateMethod("setDatumtijdstempel", Timestamp.class), datumtijdstempel);
    }

    @Override
    public String getVoornamen() {
        return (String) delegateToGetter(getDelegateMethod("getVoornamen"));
    }

    @Override
    public void setVoornamen(final String voornamen) {
        delegateToSetter(getDelegateMethod("setVoornamen", String.class), voornamen);
    }

    @Override
    public String getVoornamenNaamgebruik() {
        return (String) delegateToGetter(getDelegateMethod("getVoornamenNaamgebruik"));
    }

    @Override
    public void setVoornamenNaamgebruik(final String voornamenNaamgebruik) {
        delegateToSetter(getDelegateMethod("setVoornamenNaamgebruik", String.class), voornamenNaamgebruik);
    }

    @Override
    public AdellijkeTitel getAdellijkeTitelNaamgebruik() {
        return (AdellijkeTitel) delegateToGetter(getDelegateMethod("getAdellijkeTitelNaamgebruik"));
    }

    @Override
    public void setAdellijkeTitelNaamgebruik(final AdellijkeTitel naamgebruikTitel) {
        delegateToSetter(getDelegateMethod("setAdellijkeTitelNaamgebruik", AdellijkeTitel.class), naamgebruikTitel);
    }

    @Override
    public SoortMigratie getSoortMigratie() {
        return (SoortMigratie) delegateToGetter(getDelegateMethod("getSoortMigratie"));
    }

    @Override
    public void setSoortMigratie(final SoortMigratie soortMigratie) {
        delegateToSetter(getDelegateMethod("setSoortMigratie", SoortMigratie.class), soortMigratie);
    }

    @Override
    public RedenWijzigingVerblijf getRedenWijzigingMigratie() {
        return (RedenWijzigingVerblijf) delegateToGetter(getDelegateMethod("getRedenWijzigingMigratie"));
    }

    @Override
    public void setRedenWijzigingMigratie(final RedenWijzigingVerblijf redenWijzigingMigratie) {
        delegateToSetter(getDelegateMethod("setRedenWijzigingMigratie", RedenWijzigingVerblijf.class), redenWijzigingMigratie);
    }

    @Override
    public Aangever getAangeverMigratie() {
        return (Aangever) delegateToGetter(getDelegateMethod("getAangeverMigratie"));
    }

    @Override
    public void setAangeverMigratie(final Aangever aangeverMigratie) {
        delegateToSetter(getDelegateMethod("setAangeverMigratie", Aangever.class), aangeverMigratie);
    }

    @Override
    public String getVoorvoegsel() {
        return (String) delegateToGetter(getDelegateMethod("getVoorvoegsel"));
    }

    @Override
    public void setVoorvoegsel(final String voorvoegsel) {
        delegateToSetter(getDelegateMethod("setVoorvoegsel", String.class), voorvoegsel);
    }

    @Override
    public String getVoorvoegselNaamgebruik() {
        return (String) delegateToGetter(getDelegateMethod("getVoorvoegselNaamgebruik"));
    }

    @Override
    public void setVoorvoegselNaamgebruik(final String voorvoegselNaamgebruik) {
        delegateToSetter(getDelegateMethod("setVoorvoegselNaamgebruik", String.class), voorvoegselNaamgebruik);
    }

    @Override
    public Geslachtsaanduiding getGeslachtsaanduiding() {
        return (Geslachtsaanduiding) delegateToGetter(getDelegateMethod("getGeslachtsaanduiding"));
    }

    @Override
    public void setGeslachtsaanduiding(final Geslachtsaanduiding geslachtsaanduiding) {
        delegateToSetter(getDelegateMethod("setGeslachtsaanduiding", Geslachtsaanduiding.class), geslachtsaanduiding);
    }

    @Override
    public LandOfGebied getLandOfGebiedGeboorte() {
        return (LandOfGebied) delegateToGetter(getDelegateMethod("getLandOfGebiedGeboorte"));
    }

    @Override
    public void setLandOfGebiedGeboorte(final LandOfGebied landOfGebiedGeboorte) {
        delegateToSetter(getDelegateMethod("setLandOfGebiedGeboorte", LandOfGebied.class), landOfGebiedGeboorte);
    }

    @Override
    public LandOfGebied getLandOfGebiedMigratie() {
        return (LandOfGebied) delegateToGetter(getDelegateMethod("getLandOfGebiedMigratie"));
    }

    @Override
    public void setLandOfGebiedMigratie(final LandOfGebied landOfGebiedMigratie) {
        delegateToSetter(getDelegateMethod("setLandOfGebiedMigratie", LandOfGebied.class), landOfGebiedMigratie);
    }

    @Override
    public LandOfGebied getLandOfGebiedOverlijden() {
        return (LandOfGebied) delegateToGetter(getDelegateMethod("getLandOfGebiedOverlijden"));
    }

    @Override
    public void setLandOfGebiedOverlijden(final LandOfGebied landOfGebiedOverlijden) {
        delegateToSetter(getDelegateMethod("setLandOfGebiedOverlijden", LandOfGebied.class), landOfGebiedOverlijden);
    }

    @Override
    public Partij getGemeentePersoonskaart() {
        return (Partij) delegateToGetter(getDelegateMethod("getGemeentePersoonskaart"));
    }

    @Override
    public void setGemeentePersoonskaart(final Partij gemeentePersoonskaart) {
        delegateToSetter(getDelegateMethod("setGemeentePersoonskaart", Partij.class), gemeentePersoonskaart);
    }

    @Override
    public Gemeente getGemeenteOverlijden() {
        return (Gemeente) delegateToGetter(getDelegateMethod("getGemeenteOverlijden"));
    }

    @Override
    public void setGemeenteOverlijden(final Gemeente gemeenteOverlijden) {
        delegateToSetter(getDelegateMethod("setGemeenteOverlijden", Gemeente.class), gemeenteOverlijden);
    }

    @Override
    public Gemeente getGemeenteGeboorte() {
        return (Gemeente) delegateToGetter(getDelegateMethod("getGemeenteGeboorte"));
    }

    @Override
    public void setGemeenteGeboorte(final Gemeente gemeenteGeboorte) {
        delegateToSetter(getDelegateMethod("setGemeenteGeboorte", Gemeente.class), gemeenteGeboorte);
    }

    @Override
    public Partij getBijhoudingspartij() {
        return (Partij) delegateToGetter(getDelegateMethod("getBijhoudingspartij"));
    }

    @Override
    public void setBijhoudingspartij(final Partij bijhoudingspartij) {
        delegateToSetter(getDelegateMethod("setBijhoudingspartij", Partij.class), bijhoudingspartij);
    }

    @Override
    public String getVolgendeAdministratienummer() {
        return (String) delegateToGetter(getDelegateMethod("getVolgendeAdministratienummer"));
    }

    @Override
    public void setVolgendeAdministratienummer(final String volgendeAdministratienummer) {
        delegateToSetter(getDelegateMethod("setVolgendeAdministratienummer", String.class), volgendeAdministratienummer);
    }

    @Override
    public String getVorigeAdministratienummer() {
        return (String) delegateToGetter(getDelegateMethod("getVorigeAdministratienummer"));
    }

    @Override
    public void setVorigeAdministratienummer(final String vorigeAdministratienummer) {
        delegateToSetter(getDelegateMethod("setVorigeAdministratienummer", String.class), vorigeAdministratienummer);
    }

    @Override
    public String getVolgendeBurgerservicenummer() {
        return (String) delegateToGetter(getDelegateMethod("getVolgendeBurgerservicenummer"));
    }

    @Override
    public void setVolgendeBurgerservicenummer(final String volgendeBurgerservicenummer) {
        delegateToSetter(getDelegateMethod("setVolgendeBurgerservicenummer", String.class), volgendeBurgerservicenummer);
    }

    @Override
    public String getVorigeBurgerservicenummer() {
        return (String) delegateToGetter(getDelegateMethod("getVorigeBurgerservicenummer"));
    }

    @Override
    public void setVorigeBurgerservicenummer(final String vorigeBurgerservicenummer) {
        delegateToSetter(getDelegateMethod("setVorigeBurgerservicenummer", String.class), vorigeBurgerservicenummer);
    }

    @Override
    public String getWoonplaatsGeboorte() {
        return (String) delegateToGetter(getDelegateMethod("getWoonplaatsGeboorte"));
    }

    @Override
    public void setWoonplaatsGeboorte(final String woonplaatsGeboorte) {
        delegateToSetter(getDelegateMethod("setWoonplaatsGeboorte", String.class), woonplaatsGeboorte);
    }

    @Override
    public String getWoonplaatsOverlijden() {
        return (String) delegateToGetter(getDelegateMethod("getWoonplaatsOverlijden"));
    }

    @Override
    public void setWoonplaatsOverlijden(final String woonplaatsOverlijden) {
        delegateToSetter(getDelegateMethod("setWoonplaatsOverlijden", String.class), woonplaatsOverlijden);
    }

    @Override
    public Predicaat getPredicaat() {
        return (Predicaat) delegateToGetter(getDelegateMethod("getPredicaat"));
    }

    @Override
    public void setPredicaat(final Predicaat predicaat) {
        delegateToSetter(getDelegateMethod("setPredicaat", Predicaat.class), predicaat);
    }

    @Override
    public Predicaat getPredicaatNaamgebruik() {
        return (Predicaat) delegateToGetter(getDelegateMethod("getPredicaatNaamgebruik"));
    }

    @Override
    public void setPredicaatNaamgebruik(final Predicaat predicaatNaamgebruik) {
        delegateToSetter(getDelegateMethod("setPredicaatNaamgebruik", Predicaat.class), predicaatNaamgebruik);
    }

    @Override
    public NadereBijhoudingsaard getNadereBijhoudingsaard() {
        return (NadereBijhoudingsaard) delegateToGetter(getDelegateMethod("getNadereBijhoudingsaard"));
    }

    @Override
    public void setNadereBijhoudingsaard(final NadereBijhoudingsaard redenOpschorting) {
        delegateToSetter(getDelegateMethod("setNadereBijhoudingsaard", NadereBijhoudingsaard.class), redenOpschorting);
    }

    @Override
    public SoortPersoon getSoortPersoon() {
        return (SoortPersoon) delegateToGetter(getDelegateMethod("getSoortPersoon"));
    }

    @Override
    public void setSoortPersoon(final SoortPersoon soortPersoon) {
        delegateToSetter(getDelegateMethod("setSoortPersoon", SoortPersoon.class), soortPersoon);
    }

    @Override
    public Verblijfsrecht getVerblijfsrecht() {
        return (Verblijfsrecht) delegateToGetter(getDelegateMethod("getVerblijfsrecht"));
    }

    @Override
    public void setVerblijfsrecht(final Verblijfsrecht verblijfsrecht) {
        delegateToSetter(getDelegateMethod("setVerblijfsrecht", Verblijfsrecht.class), verblijfsrecht);
    }

    @Override
    public Naamgebruik getNaamgebruik() {
        return (Naamgebruik) delegateToGetter(getDelegateMethod("getNaamgebruik"));
    }

    @Override
    public void setNaamgebruik(final Naamgebruik naamgebruik) {
        delegateToSetter(getDelegateMethod("setNaamgebruik", Naamgebruik.class), naamgebruik);
    }

    @Override
    public Set<PersoonAdres> getPersoonAdresSet() {
        return (Set<PersoonAdres>) delegateToGetter(getDelegateMethod("getPersoonAdresSet"));
    }

    @Override
    public Set<PersoonGeslachtsnaamcomponent> getPersoonGeslachtsnaamcomponentSet() {
        return (Set<PersoonGeslachtsnaamcomponent>) delegateToGetter(getDelegateMethod("getPersoonGeslachtsnaamcomponentSet"));
    }

    @Override
    public Set<PersoonIndicatie> getPersoonIndicatieSet() {
        return (Set<PersoonIndicatie>) delegateToGetter(getDelegateMethod("getPersoonIndicatieSet"));
    }

    @Override
    public Set<PersoonNationaliteit> getPersoonNationaliteitSet() {
        return (Set<PersoonNationaliteit>) delegateToGetter(getDelegateMethod("getPersoonNationaliteitSet"));
    }

    @Override
    public Set<Onderzoek> getOnderzoeken() {
        return (Set<Onderzoek>) delegateToGetter(getDelegateMethod("getOnderzoeken"));
    }

    @Override
    public Set<PersoonReisdocument> getPersoonReisdocumentSet() {
        return (Set<PersoonReisdocument>) delegateToGetter(getDelegateMethod("getPersoonReisdocumentSet"));
    }

    @Override
    public Set<PersoonVerificatie> getPersoonVerificatieSet() {
        return (Set<PersoonVerificatie>) delegateToGetter(getDelegateMethod("getPersoonVerificatieSet"));
    }

    @Override
    public Set<PersoonVerstrekkingsbeperking> getPersoonVerstrekkingsbeperkingSet() {
        return (Set<PersoonVerstrekkingsbeperking>) delegateToGetter(getDelegateMethod("getPersoonVerstrekkingsbeperkingSet"));
    }

    @Override
    public Set<PersoonVoornaam> getPersoonVoornaamSet() {
        return (Set<PersoonVoornaam>) delegateToGetter(getDelegateMethod("getPersoonVoornaamSet"));
    }

    @Override
    public PersoonVoornaam getPersoonVoornaam(int volgnummer) {
        return delegates.get(0).getPersoonVoornaam(volgnummer);
    }

    @Override
    public Set<PersoonBuitenlandsPersoonsnummer> getPersoonBuitenlandsPersoonsnummerSet() {
        return (Set<PersoonBuitenlandsPersoonsnummer>) delegateToGetter(getDelegateMethod("getPersoonBuitenlandsPersoonsnummerSet"));
    }

    @Override
    public AdellijkeTitel getAdellijkeTitel() {
        return (AdellijkeTitel) delegateToGetter(getDelegateMethod("getAdellijkeTitel"));
    }

    @Override
    public void setAdellijkeTitel(final AdellijkeTitel titel) {
        delegateToSetter(getDelegateMethod("setAdellijkeTitel", AdellijkeTitel.class), titel);
    }

    @Override
    public Bijhoudingsaard getBijhoudingsaard() {
        return (Bijhoudingsaard) delegateToGetter(getDelegateMethod("getBijhoudingsaard"));
    }

    @Override
    public void setBijhoudingsaard(final Bijhoudingsaard bijhoudingsaard) {
        delegateToSetter(getDelegateMethod("setBijhoudingsaard", Bijhoudingsaard.class), bijhoudingsaard);
    }

    @Override
    public boolean isActueelVoorAfgeleidadministratief() {
        return (boolean) delegateToGetter(getDelegateMethod("isActueelVoorAfgeleidadministratief"));
    }

    @Override
    public void setActueelVoorAfgeleidadministratief(final boolean actueelVoorAfgeleidadministratief) {
        delegateToSetter(getDelegateMethod("setActueelVoorAfgeleidadministratief", boolean.class), actueelVoorAfgeleidadministratief);
    }

    @Override
    public boolean isActueelVoorIds() {
        return (boolean) delegateToGetter(getDelegateMethod("isActueelVoorIds"));
    }

    @Override
    public void setActueelVoorIds(final boolean actueelVoorIds) {
        delegateToSetter(getDelegateMethod("setActueelVoorIds", boolean.class), actueelVoorIds);
    }

    @Override
    public boolean isActueelVoorSamengesteldenaam() {
        return (boolean) delegateToGetter(getDelegateMethod("isActueelVoorSamengesteldenaam"));
    }

    @Override
    public void setActueelVoorSamengesteldenaam(final boolean actueelVoorSamengesteldenaam) {
        delegateToSetter(getDelegateMethod("setActueelVoorSamengesteldenaam", boolean.class), actueelVoorSamengesteldenaam);
    }

    @Override
    public boolean isActueelVoorGeboorte() {
        return (boolean) delegateToGetter(getDelegateMethod("isActueelVoorGeboorte"));
    }

    @Override
    public void setActueelVoorGeboorte(final boolean actueelVoorGeboorte) {
        delegateToSetter(getDelegateMethod("setActueelVoorGeboorte", boolean.class), actueelVoorGeboorte);
    }

    @Override
    public boolean isActueelVoorGeslachtsaanduiding() {
        return (boolean) delegateToGetter(getDelegateMethod("isActueelVoorGeslachtsaanduiding"));
    }

    @Override
    public void setActueelVoorGeslachtsaanduiding(final boolean actueelVoorGeslachtsaanduiding) {
        delegateToSetter(getDelegateMethod("setActueelVoorGeslachtsaanduiding", boolean.class), actueelVoorGeslachtsaanduiding);
    }

    @Override
    public boolean isActueelVoorInschrijving() {
        return (boolean) delegateToGetter(getDelegateMethod("isActueelVoorInschrijving"));
    }

    @Override
    public void setActueelVoorInschrijving(final boolean actueelVoorInschrijving) {
        delegateToSetter(getDelegateMethod("setActueelVoorInschrijving", boolean.class), actueelVoorInschrijving);
    }

    @Override
    public boolean isActueelVoorVerwijzing() {
        return (boolean) delegateToGetter(getDelegateMethod("isActueelVoorVerwijzing"));
    }

    @Override
    public void setActueelVoorVerwijzing(final boolean actueelVoorVerwijzing) {
        delegateToSetter(getDelegateMethod("setActueelVoorVerwijzing", boolean.class), actueelVoorVerwijzing);
    }

    @Override
    public boolean isActueelVoorBijhouding() {
        return (boolean) delegateToGetter(getDelegateMethod("isActueelVoorBijhouding"));
    }

    @Override
    public void setActueelVoorBijhouding(final boolean actueelVoorBijhouding) {
        delegateToSetter(getDelegateMethod("setActueelVoorBijhouding", boolean.class), actueelVoorBijhouding);
    }

    @Override
    public boolean isActueelVoorOverlijden() {
        return (boolean) delegateToGetter(getDelegateMethod("isActueelVoorOverlijden"));
    }

    @Override
    public void setActueelVoorOverlijden(final boolean actueelVoorOverlijden) {
        delegateToSetter(getDelegateMethod("setActueelVoorOverlijden", boolean.class), actueelVoorOverlijden);
    }

    @Override
    public boolean isActueelVoorNaamgebruik() {
        return (boolean) delegateToGetter(getDelegateMethod("isActueelVoorNaamgebruik"));
    }

    @Override
    public void setActueelVoorNaamgebruik(final boolean actueelVoorNaamgebruik) {
        delegateToSetter(getDelegateMethod("setActueelVoorNaamgebruik", boolean.class), actueelVoorNaamgebruik);
    }

    @Override
    public boolean isActueelVoorMigratie() {
        return (boolean) delegateToGetter(getDelegateMethod("isActueelVoorMigratie"));
    }

    @Override
    public void setActueelVoorMigratie(final boolean actueelVoorMigratie) {
        delegateToSetter(getDelegateMethod("setActueelVoorMigratie", boolean.class), actueelVoorMigratie);
    }

    @Override
    public boolean isActueelVoorVerblijfsrecht() {
        return (boolean) delegateToGetter(getDelegateMethod("isActueelVoorVerblijfsrecht"));
    }

    @Override
    public void setActueelVoorVerblijfsrecht(final boolean actueelVoorVerblijfsrecht) {
        delegateToSetter(getDelegateMethod("setActueelVoorVerblijfsrecht", boolean.class), actueelVoorVerblijfsrecht);
    }

    @Override
    public boolean isActueelVoorUitsluitingkiesrecht() {
        return (boolean) delegateToGetter(getDelegateMethod("isActueelVoorUitsluitingkiesrecht"));
    }

    @Override
    public void setActueelVoorUitsluitingkiesrecht(final boolean actueelVoorUitsluitingkiesrecht) {
        delegateToSetter(getDelegateMethod("setActueelVoorUitsluitingkiesrecht", boolean.class), actueelVoorUitsluitingkiesrecht);
    }

    @Override
    public boolean isActueelVoorDeelnameEuVerkiezingen() {
        return (boolean) delegateToGetter(getDelegateMethod("isActueelVoorDeelnameEuVerkiezingen"));
    }

    @Override
    public void setActueelVoorDeelnameEuVerkiezingen(final boolean actueelVoorDeelnameEuVerkiezingen) {
        delegateToSetter(getDelegateMethod("setActueelVoorDeelnameEuVerkiezingen", boolean.class), actueelVoorDeelnameEuVerkiezingen);
    }

    @Override
    public boolean isActueelVoorPersoonskaart() {
        return (boolean) delegateToGetter(getDelegateMethod("isActueelVoorPersoonskaart"));
    }

    @Override
    public void setActueelVoorPersoonskaart(final boolean actueelVoorPersoonskaart) {
        delegateToSetter(getDelegateMethod("setActueelVoorPersoonskaart", boolean.class), actueelVoorPersoonskaart);
    }

    @Override
    public Set<PersoonAfgeleidAdministratiefHistorie> getPersoonAfgeleidAdministratiefHistorieSet() {
        return (Set<PersoonAfgeleidAdministratiefHistorie>) delegateToGetter(getDelegateMethod("getPersoonAfgeleidAdministratiefHistorieSet"));
    }

    @Override
    public Set<PersoonSamengesteldeNaamHistorie> getPersoonSamengesteldeNaamHistorieSet() {
        return (Set<PersoonSamengesteldeNaamHistorie>) delegateToGetter(getDelegateMethod("getPersoonSamengesteldeNaamHistorieSet"));
    }

    @Override
    public Set<PersoonNaamgebruikHistorie> getPersoonNaamgebruikHistorieSet() {
        return (Set<PersoonNaamgebruikHistorie>) delegateToGetter(getDelegateMethod("getPersoonNaamgebruikHistorieSet"));
    }

    @Override
    public Set<PersoonDeelnameEuVerkiezingenHistorie> getPersoonDeelnameEuVerkiezingenHistorieSet() {
        return (Set<PersoonDeelnameEuVerkiezingenHistorie>) delegateToGetter(getDelegateMethod("getPersoonDeelnameEuVerkiezingenHistorieSet"));
    }

    @Override
    public Set<PersoonGeboorteHistorie> getPersoonGeboorteHistorieSet() {
        return (Set<PersoonGeboorteHistorie>) delegateToGetter(getDelegateMethod("getPersoonGeboorteHistorieSet"));
    }

    @Override
    public Set<PersoonGeslachtsaanduidingHistorie> getPersoonGeslachtsaanduidingHistorieSet() {
        return (Set<PersoonGeslachtsaanduidingHistorie>) delegateToGetter(getDelegateMethod("getPersoonGeslachtsaanduidingHistorieSet"));
    }

    @Override
    public Set<PersoonIDHistorie> getPersoonIDHistorieSet() {
        return (Set<PersoonIDHistorie>) delegateToGetter(getDelegateMethod("getPersoonIDHistorieSet"));
    }

    @Override
    public Set<PersoonVerblijfsrechtHistorie> getPersoonVerblijfsrechtHistorieSet() {
        return (Set<PersoonVerblijfsrechtHistorie>) delegateToGetter(getDelegateMethod("getPersoonVerblijfsrechtHistorieSet"));
    }

    @Override
    public Set<PersoonBijhoudingHistorie> getPersoonBijhoudingHistorieSet() {
        return (Set<PersoonBijhoudingHistorie>) delegateToGetter(getDelegateMethod("getPersoonBijhoudingHistorieSet"));
    }

    @Override
    public Set<PersoonMigratieHistorie> getPersoonMigratieHistorieSet() {
        return (Set<PersoonMigratieHistorie>) delegateToGetter(getDelegateMethod("getPersoonMigratieHistorieSet"));
    }

    @Override
    public Set<PersoonInschrijvingHistorie> getPersoonInschrijvingHistorieSet() {
        return (Set<PersoonInschrijvingHistorie>) delegateToGetter(getDelegateMethod("getPersoonInschrijvingHistorieSet"));
    }

    @Override
    public Set<PersoonNummerverwijzingHistorie> getPersoonNummerverwijzingHistorieSet() {
        return (Set<PersoonNummerverwijzingHistorie>) delegateToGetter(getDelegateMethod("getPersoonNummerverwijzingHistorieSet"));
    }

    @Override
    public Set<PersoonOverlijdenHistorie> getPersoonOverlijdenHistorieSet() {
        return (Set<PersoonOverlijdenHistorie>) delegateToGetter(getDelegateMethod("getPersoonOverlijdenHistorieSet"));
    }

    @Override
    public Set<PersoonPersoonskaartHistorie> getPersoonPersoonskaartHistorieSet() {
        return (Set<PersoonPersoonskaartHistorie>) delegateToGetter(getDelegateMethod("getPersoonPersoonskaartHistorieSet"));
    }

    @Override
    public Set<PersoonUitsluitingKiesrechtHistorie> getPersoonUitsluitingKiesrechtHistorieSet() {
        return (Set<PersoonUitsluitingKiesrechtHistorie>) delegateToGetter(getDelegateMethod("getPersoonUitsluitingKiesrechtHistorieSet"));
    }

    @Override
    public Set<Lo3Bericht> getLo3Berichten() {
        return (Set<Lo3Bericht>) delegateToGetter(getDelegateMethod("getLo3Berichten"));
    }

    @Override
    public Set<Betrokkenheid> getBetrokkenheidSet() {
        return (Set<Betrokkenheid>) delegateToGetter(getDelegateMethod("getBetrokkenheidSet"));
    }

    @Override
    public Set<Betrokkenheid> getBetrokkenheidSet(final SoortBetrokkenheid soortBetrokkenheid) {
        return delegates.get(0).getBetrokkenheidSet(soortBetrokkenheid);
    }

    @Override
    public Set<Betrokkenheid> getActueleBetrokkenheidSet(final SoortBetrokkenheid soortBetrokkenheid) {
        return delegates.get(0).getActueleBetrokkenheidSet(soortBetrokkenheid);
    }

    @Override
    public Set<Betrokkenheid> getActueleOuders() {
        return (Set<Betrokkenheid>) delegateToGetter(getDelegateMethod("getActueleOuders"));
    }

    @Override
    public Set<Persoon> getOuders(final int peildatum) {
        return delegates.get(0).getOuders(peildatum);
    }

    @Override
    public Set<Betrokkenheid> getActueleKinderen() {
        return (Set<Betrokkenheid>) delegateToGetter(getDelegateMethod("getActueleKinderen"));
    }

    @Override
    public Set<Betrokkenheid> getKinderen(final int peildatum) {
        return delegates.get(0).getKinderen(peildatum);
    }

    @Override
    public Set<Betrokkenheid> getActuelePartners() {
        return (Set<Betrokkenheid>) delegateToGetter(getDelegateMethod("getActuelePartners"));
    }

    @Override
    public Set<Relatie> getRelaties() {
        return (Set<Relatie>) delegateToGetter(getDelegateMethod("getRelaties"));
    }

    @Override
    public Set<Relatie> getRelaties(final SoortBetrokkenheid soortBetrokkenheid) {
        return delegates.get(0).getRelaties(soortBetrokkenheid);
    }

    @Override
    public Set<Stapel> getStapels() {
        return (Set<Stapel>) delegateToGetter(getDelegateMethod("getStapels"));
    }

    @Override
    public Map<String, Collection<FormeleHistorie>> verzamelHistorie() {
        return (Map<String, Collection<FormeleHistorie>>) delegateToGetter(getDelegateMethod("verzamelHistorie"));
    }

    @Override
    public boolean isPersoonIngeschrevene() {
        return (boolean) delegateToGetter(getDelegateMethod("isPersoonIngeschrevene"));
    }

    @Override
    public void addPersoonAdres(final PersoonAdres persoonAdres) {
        controleerEenDelegate();
        delegateToSetter(getDelegateMethod("addPersoonAdres", PersoonAdres.class), persoonAdres);
    }

    @Override
    public void addPersoonGeslachtsnaamcomponent(final PersoonGeslachtsnaamcomponent persoonGeslachtsnaamcomponent) {
        controleerEenDelegate();
        delegateToSetter(getDelegateMethod("addPersoonGeslachtsnaamcomponent", PersoonGeslachtsnaamcomponent.class), persoonGeslachtsnaamcomponent);
    }

    @Override
    public void addPersoonIndicatie(final PersoonIndicatie persoonIndicatie) {
        controleerEenDelegate();
        delegateToSetter(getDelegateMethod("addPersoonIndicatie", PersoonIndicatie.class), persoonIndicatie);
    }

    @Override
    public void addPersoonNationaliteit(final PersoonNationaliteit persoonNationaliteit) {
        controleerEenDelegate();
        delegateToSetter(getDelegateMethod("addPersoonNationaliteit", PersoonNationaliteit.class), persoonNationaliteit);
    }

    @Override
    public void addOnderzoek(final Onderzoek onderzoek) {
        controleerEenDelegate();
        delegateToSetter(getDelegateMethod("addOnderzoek", Onderzoek.class), onderzoek);
    }

    @Override
    public boolean removeOnderzoek(final Onderzoek onderzoek) {
        controleerEenDelegate();
        return delegates.get(0).removeOnderzoek(onderzoek);
    }

    @Override
    public void addPersoonReisdocument(final PersoonReisdocument persoonReisdocument) {
        controleerEenDelegate();
        delegateToSetter(getDelegateMethod("addPersoonReisdocument", PersoonReisdocument.class), persoonReisdocument);
    }

    @Override
    public void addPersoonVerificatie(final PersoonVerificatie persoonVerificatie) {
        controleerEenDelegate();
        delegateToSetter(getDelegateMethod("addPersoonVerificatie", PersoonVerificatie.class), persoonVerificatie);
    }

    @Override
    public void addPersoonVerstrekkingsbeperking(final PersoonVerstrekkingsbeperking persoonVerstrekkingsbeperking) {
        controleerEenDelegate();
        delegateToSetter(getDelegateMethod("addPersoonVerstrekkingsbeperking", PersoonVerstrekkingsbeperking.class), persoonVerstrekkingsbeperking);
    }

    @Override
    public void addPersoonVoornaam(final PersoonVoornaam persoonVoornaam) {
        controleerEenDelegate();
        delegateToSetter(getDelegateMethod("addPersoonVoornaam", PersoonVoornaam.class), persoonVoornaam);
    }

    @Override
    public void addPersoonBuitenlandsPersoonsnummer(final PersoonBuitenlandsPersoonsnummer persoonBuitenlandsPersoonsnummer) {
        controleerEenDelegate();
        delegateToSetter(getDelegateMethod("addPersoonBuitenlandsPersoonsnummer", PersoonBuitenlandsPersoonsnummer.class), persoonBuitenlandsPersoonsnummer);
    }

    @Override
    public void addPersoonAfgeleidAdministratiefHistorie(final PersoonAfgeleidAdministratiefHistorie persoonAfgeleidAdministratiefHistorie) {
        controleerEenDelegate();
        delegateToSetter(getDelegateMethod("addPersoonAfgeleidAdministratiefHistorie", PersoonAfgeleidAdministratiefHistorie.class),
                persoonAfgeleidAdministratiefHistorie);
    }

    @Override
    public void addPersoonSamengesteldeNaamHistorie(final PersoonSamengesteldeNaamHistorie persoonSamengesteldeNaamHistorie) {
        controleerEenDelegate();
        delegateToSetter(getDelegateMethod("addPersoonSamengesteldeNaamHistorie", PersoonSamengesteldeNaamHistorie.class), persoonSamengesteldeNaamHistorie);
    }

    @Override
    public void addPersoonNaamgebruikHistorie(final PersoonNaamgebruikHistorie persoonNaamgebruikHistorie) {
        controleerEenDelegate();
        delegateToSetter(getDelegateMethod("addPersoonNaamgebruikHistorie", PersoonNaamgebruikHistorie.class), persoonNaamgebruikHistorie);
    }

    @Override
    public void addPersoonDeelnameEuVerkiezingenHistorie(final PersoonDeelnameEuVerkiezingenHistorie persoonDeelnameEuVerkiezingenHistorie) {
        controleerEenDelegate();
        delegateToSetter(getDelegateMethod("addPersoonDeelnameEuVerkiezingenHistorie", PersoonDeelnameEuVerkiezingenHistorie.class),
                persoonDeelnameEuVerkiezingenHistorie);
    }

    @Override
    public void addPersoonGeboorteHistorie(final PersoonGeboorteHistorie persoonGeboorteHistorie) {
        controleerEenDelegate();
        delegateToSetter(getDelegateMethod("addPersoonGeboorteHistorie", PersoonGeboorteHistorie.class), persoonGeboorteHistorie);
    }

    @Override
    public void addPersoonGeslachtsaanduidingHistorie(final PersoonGeslachtsaanduidingHistorie persoonGeslachtsaanduidingHistorie) {
        controleerEenDelegate();
        delegateToSetter(getDelegateMethod("addPersoonGeslachtsaanduidingHistorie", PersoonGeslachtsaanduidingHistorie.class),
                persoonGeslachtsaanduidingHistorie);
    }

    @Override
    public void addPersoonIDHistorie(final PersoonIDHistorie persoonIDHistorie) {
        controleerEenDelegate();
        delegateToSetter(getDelegateMethod("addPersoonIDHistorie", PersoonIDHistorie.class), persoonIDHistorie);
    }

    @Override
    public void addPersoonVerblijfsrechtHistorie(final PersoonVerblijfsrechtHistorie persoonVerblijfsrechtHistorie) {
        controleerEenDelegate();
        delegateToSetter(getDelegateMethod("addPersoonVerblijfsrechtHistorie", PersoonVerblijfsrechtHistorie.class), persoonVerblijfsrechtHistorie);
    }

    @Override
    public void addPersoonBijhoudingHistorie(final PersoonBijhoudingHistorie persoonBijhoudingHistorie) {
        controleerEenDelegate();
        delegateToSetter(getDelegateMethod("addPersoonBijhoudingHistorie", PersoonBijhoudingHistorie.class), persoonBijhoudingHistorie);
    }

    @Override
    public void leidAf(final BRPActie actie, final int datumAanvangGeldigheid, final boolean isExplicietGewijzigd) {
        leidAf(actie, datumAanvangGeldigheid, isExplicietGewijzigd, null);
    }

    @Override
    public void leidAf(final BRPActie actie, final int datumAanvangGeldigheid, final boolean isExplicietGewijzigd, Boolean explicieteIndicatieNamenReeks) {
        controleerMagWordenGewijzigd();
        for (final Persoon delegate : delegates) {
            delegate.leidAf(actie, datumAanvangGeldigheid, isExplicietGewijzigd, explicieteIndicatieNamenReeks);
        }
    }

    @Override
    public void addPersoonMigratieHistorie(final PersoonMigratieHistorie persoonMigratieHistorie) {
        controleerEenDelegate();
        delegateToSetter(getDelegateMethod("addPersoonMigratieHistorie", PersoonMigratieHistorie.class), persoonMigratieHistorie);
    }

    @Override
    public void addPersoonInschrijvingHistorie(final PersoonInschrijvingHistorie persoonInschrijvingHistorie) {
        controleerEenDelegate();
        delegateToSetter(getDelegateMethod("addPersoonInschrijvingHistorie", PersoonInschrijvingHistorie.class), persoonInschrijvingHistorie);
    }

    @Override
    public void addPersoonNummerverwijzingHistorie(final PersoonNummerverwijzingHistorie persoonNummerverwijzingHistorie) {
        controleerEenDelegate();
        delegateToSetter(getDelegateMethod("addPersoonNummerverwijzingHistorie", PersoonNummerverwijzingHistorie.class), persoonNummerverwijzingHistorie);
    }

    @Override
    public void addPersoonOverlijdenHistorie(final PersoonOverlijdenHistorie persoonOverlijdenHistorie) {
        controleerEenDelegate();
        delegateToSetter(getDelegateMethod("addPersoonOverlijdenHistorie", PersoonOverlijdenHistorie.class), persoonOverlijdenHistorie);
    }

    @Override
    public void addPersoonPersoonskaartHistorie(final PersoonPersoonskaartHistorie persoonPersoonskaartHistorie) {
        controleerEenDelegate();
        delegateToSetter(getDelegateMethod("addPersoonPersoonskaartHistorie", PersoonPersoonskaartHistorie.class), persoonPersoonskaartHistorie);
    }

    @Override
    public void addPersoonUitsluitingKiesrechtHistorie(final PersoonUitsluitingKiesrechtHistorie persoonUitsluitingKiesrechtHistorie) {
        controleerEenDelegate();
        delegateToSetter(getDelegateMethod("addPersoonUitsluitingKiesrechtHistorie", PersoonUitsluitingKiesrechtHistorie.class),
                persoonUitsluitingKiesrechtHistorie);
    }

    @Override
    public void addLo3Bericht(final Lo3Bericht lo3Bericht) {
        controleerEenDelegate();
        delegateToSetter(getDelegateMethod("addLo3Bericht", Lo3Bericht.class), lo3Bericht);
    }

    @Override
    public void addBetrokkenheid(final Betrokkenheid betrokkenheid) {
        controleerEenDelegate();
        delegateToSetter(getDelegateMethod("addBetrokkenheid", Betrokkenheid.class), betrokkenheid);
    }

    @Override
    public boolean removeBetrokkenheid(final Betrokkenheid betrokkenheid) {
        controleerMagWordenGewijzigd();
        controleerEenDelegate();
        return delegates.get(0).removeBetrokkenheid(betrokkenheid);
    }

    @Override
    public void addStapel(final Stapel stapel) {
        controleerEenDelegate();
        delegateToSetter(getDelegateMethod("addStapel", Stapel.class), stapel);
    }

    @Override
    public boolean removeStapel(final Stapel stapel) {
        controleerMagWordenGewijzigd();
        controleerEenDelegate();
        return delegates.get(0).removeStapel(stapel);
    }

    @Override
    public void leidtNaamgebruikAf(final BRPActie actie, final boolean isExplicietGewijzigd) {
        controleerMagWordenGewijzigd();
        for (final Persoon delegate : delegates) {
            delegate.leidtNaamgebruikAf(actie, isExplicietGewijzigd);
        }
    }

    @Override
    public void leidtNaamgebruikAf(final Naamgebruik naamgebruik, final BRPActie actie, final boolean isExplicietGewijzigd) {
        controleerMagWordenGewijzigd();
        for (final Persoon delegate : delegates) {
            delegate.leidtNaamgebruikAf(naamgebruik, actie, isExplicietGewijzigd);
        }
    }

    @Override
    public Set<MaterieleHistorie> getNietVervallenMaterieleGroepen() {
        return delegates.get(0).getNietVervallenMaterieleGroepen();
    }

    @Override
    public int bepaalLeeftijd(int peildatum) {
        return delegates.get(0).bepaalLeeftijd(peildatum);
    }

    @Override
    public Persoon getActuelePartner() {
        return delegates.get(0).getActuelePartner();
    }
}

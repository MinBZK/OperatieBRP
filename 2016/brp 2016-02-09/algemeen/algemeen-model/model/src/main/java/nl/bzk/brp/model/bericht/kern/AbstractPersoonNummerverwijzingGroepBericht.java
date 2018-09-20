/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.basis.AbstractMaterieleHistorieGroepBericht;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.PersoonNummerverwijzingGroepBasis;

/**
 * Zowel het Burgerservicenummer als het administratienummer worden in de Wet BRP genoemd en aangemerkt als een Algemeen
 * gegeven. Het is na wijziging van één van de nummers, ongeacht de reden voor de wijziging, noodzakelijk de afnemers
 * die geautoriseerd zijn voor deze gegevens te informeren over deze wijziging. De BRP voorziet in het leveren van deze
 * informatie. Om die reden dienen deze attributen aangemerkt te worden als Administratief gegeven en kunnen, mits de
 * afnemer daarvoor is geautoriseerd, worden geleverd. Het Centraal Bureau voor de Statistiek (CBS) is bijvoorbeeld een
 * voor de wijziging van identificerende nummers geautoriseerde afnemer.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractPersoonNummerverwijzingGroepBericht extends AbstractMaterieleHistorieGroepBericht implements Groep,
        PersoonNummerverwijzingGroepBasis, MetaIdentificeerbaar
{

    private static final Integer META_ID = 10900;
    private static final List<Integer> ONDERLIGGENDE_ATTRIBUTEN = Arrays.asList(3134, 3136, 3247, 3248);
    private BurgerservicenummerAttribuut vorigeBurgerservicenummer;
    private BurgerservicenummerAttribuut volgendeBurgerservicenummer;
    private AdministratienummerAttribuut vorigeAdministratienummer;
    private AdministratienummerAttribuut volgendeAdministratienummer;

    /**
     * {@inheritDoc}
     */
    @Override
    public BurgerservicenummerAttribuut getVorigeBurgerservicenummer() {
        return vorigeBurgerservicenummer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BurgerservicenummerAttribuut getVolgendeBurgerservicenummer() {
        return volgendeBurgerservicenummer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdministratienummerAttribuut getVorigeAdministratienummer() {
        return vorigeAdministratienummer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdministratienummerAttribuut getVolgendeAdministratienummer() {
        return volgendeAdministratienummer;
    }

    /**
     * Zet Vorige burgerservicenummer van Nummerverwijzing.
     *
     * @param vorigeBurgerservicenummer Vorige burgerservicenummer.
     */
    public void setVorigeBurgerservicenummer(final BurgerservicenummerAttribuut vorigeBurgerservicenummer) {
        this.vorigeBurgerservicenummer = vorigeBurgerservicenummer;
    }

    /**
     * Zet Volgende burgerservicenummer van Nummerverwijzing.
     *
     * @param volgendeBurgerservicenummer Volgende burgerservicenummer.
     */
    public void setVolgendeBurgerservicenummer(final BurgerservicenummerAttribuut volgendeBurgerservicenummer) {
        this.volgendeBurgerservicenummer = volgendeBurgerservicenummer;
    }

    /**
     * Zet Vorige administratienummer van Nummerverwijzing.
     *
     * @param vorigeAdministratienummer Vorige administratienummer.
     */
    public void setVorigeAdministratienummer(final AdministratienummerAttribuut vorigeAdministratienummer) {
        this.vorigeAdministratienummer = vorigeAdministratienummer;
    }

    /**
     * Zet Volgende administratienummer van Nummerverwijzing.
     *
     * @param volgendeAdministratienummer Volgende administratienummer.
     */
    public void setVolgendeAdministratienummer(final AdministratienummerAttribuut volgendeAdministratienummer) {
        this.volgendeAdministratienummer = volgendeAdministratienummer;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (vorigeBurgerservicenummer != null) {
            attributen.add(vorigeBurgerservicenummer);
        }
        if (volgendeBurgerservicenummer != null) {
            attributen.add(volgendeBurgerservicenummer);
        }
        if (vorigeAdministratienummer != null) {
            attributen.add(vorigeAdministratienummer);
        }
        if (volgendeAdministratienummer != null) {
            attributen.add(volgendeAdministratienummer);
        }
        return attributen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getMetaId() {
        return META_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean bevatElementMetMetaId(final Integer metaId) {
        return ONDERLIGGENDE_ATTRIBUTEN.contains(metaId);
    }

}

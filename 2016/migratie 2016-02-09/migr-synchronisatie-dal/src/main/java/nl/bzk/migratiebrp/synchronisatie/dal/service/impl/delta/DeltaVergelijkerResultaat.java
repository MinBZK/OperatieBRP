/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.Sleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.DeltaEntiteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.DeltaEntiteitPaar;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.EntiteitSleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.FormeleHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.IstSleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Voorkomen;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;

/**
 * Immutable data class om de resultaten van de delta in te bewaren.
 */
public final class DeltaVergelijkerResultaat implements VergelijkerResultaat {

    private final Map<Verschil, VerschilGroep> interneVerschilNaarVerschilGroepMap = new LinkedHashMap<>();
    private final List<VerschilGroep> interneVerschilGroepen = new ArrayList<>();

    /**
     * De lijst met nieuwe LO3 voorkomens (LO3-herkomsten) om aan de bestaande acties te koppelen.
     */
    private final Map<BRPActie, Lo3Voorkomen> actieHerkomstMap = new IdentityHashMap<>();
    private final ActieConsolidatieData actieConsolidatieData = new ActieConsolidatieData();
    private final Set<DeltaEntiteitPaar> deltaEntiteitPaarSet = new LinkedHashSet<>();

    @Override
    public Set<Verschil> getVerschillen() {
        return Collections.unmodifiableSet(interneVerschilNaarVerschilGroepMap.keySet());
    }

    @Override
    public Set<Verschil> getVerschillen(final VerschilType verschilType, final String veldnaam) {
        return getVerschillen(verschilType, false, veldnaam);
    }

    @Override
    public Set<Verschil> getVerschillen(final VerschilType verschilType, final boolean isVoorkomenSleutel, final String veldnaam) {
        final Set<Verschil> resultaat = new HashSet<>();

        for (final Verschil verschil : interneVerschilNaarVerschilGroepMap.keySet()) {
            final Sleutel sleutel = verschil.getSleutel();
            final boolean isVeldnaamGelijk = Objects.equals(veldnaam, sleutel.getVeld());
            if (verschilType.equals(verschil.getVerschilType()) && isJuisteSleutelType(sleutel, isVoorkomenSleutel) && isVeldnaamGelijk) {
                resultaat.add(verschil);
            }
        }
        return resultaat;
    }

    @Override
    public void voegToeOfVervangVerschil(final Verschil verschil) {
        verwijderVerschil(verschil);
        groepeerVerschil(verschil);
    }

    @Override
    public void voegVerschillenToe(final Collection<Verschil> verschillenParam) {
        groepeerVerschillen(verschillenParam);
    }

    @Override
    public void vervangVerschillen(final List<VerschilGroep> nieuweVerschilGroepen) {
        interneVerschilGroepen.clear();
        interneVerschilNaarVerschilGroepMap.clear();

        for (final VerschilGroep verschilGroep : nieuweVerschilGroepen) {
            interneVerschilGroepen.add(verschilGroep);
            final List<Verschil> verschilGroepVerschillen = verschilGroep.getVerschillen();

            for (final Verschil verschil : verschilGroepVerschillen) {
                interneVerschilNaarVerschilGroepMap.put(verschil, verschilGroep);
            }
        }
    }

    @Override
    public void verwijderVerschillenVoorHistorie(final FormeleHistorie historie) {
        final Iterator<VerschilGroep> verschilGroepIterator = interneVerschilGroepen.iterator();
        while (verschilGroepIterator.hasNext()) {
            final VerschilGroep verschilGroep = verschilGroepIterator.next();
            if (verschilGroep.getFormeleHistorie() == historie) {
                verschilGroepIterator.remove();

                for (final Verschil verschil : verschilGroep) {
                    interneVerschilNaarVerschilGroepMap.remove(verschil);
                }
            }
        }
    }

    @Override
    public boolean verwijderVerschil(final Verschil teVerwijderenVerschil) {
        boolean result = false;
        final VerschilGroep verschilGroep = interneVerschilNaarVerschilGroepMap.remove(teVerwijderenVerschil);
        if (verschilGroep != null) {
            result = verschilGroep.verwijderVerschil(teVerwijderenVerschil);
        }
        return result;
    }

    @Override
    public List<VerschilGroep> getVerschilGroepen() {
        return Collections.unmodifiableList(interneVerschilGroepen);
    }

    @Override
    public void voegActieHerkomstMapInhoudToe(final Map<BRPActie, Lo3Voorkomen> actieHerkomstMapParam) {
        actieHerkomstMap.putAll(actieHerkomstMapParam);
    }

    @Override
    public boolean bevatSleutel(final Sleutel sleutel) {
        return zoekVerschil(sleutel) != null;
    }

    @Override
    public Verschil zoekVerschil(final Sleutel sleutel) {
        Verschil result = null;
        for (final Verschil verschil : interneVerschilNaarVerschilGroepMap.keySet()) {
            if (verschil.getSleutel().equals(sleutel)) {
                result = verschil;
                break;
            }
        }
        return result;
    }

    @Override
    public List<Verschil> zoekVerschillen(final EntiteitSleutel sleutel) {
        final List<Verschil> verschillen = new ArrayList<>();
        for (final Verschil verschil : interneVerschilNaarVerschilGroepMap.keySet()) {
            final Sleutel verschilSleutel = verschil.getSleutel();
            if (verschilSleutel instanceof EntiteitSleutel && ((EntiteitSleutel) verschilSleutel).equalsIgnoreDelen(sleutel)) {
                verschillen.add(verschil);
            }
        }
        return verschillen;
    }

    @Override
    public Verschil zoekVerschil(final Sleutel sleutel, final VerschilType verschilType) {
        Verschil result = null;
        for (final Verschil verschil : interneVerschilNaarVerschilGroepMap.keySet()) {
            if (sleutel.equals(verschil.getSleutel()) && verschilType.equals(verschil.getVerschilType())) {
                result = verschil;
                break;
            }
        }
        return result;
    }

    @Override
    public boolean isLeeg() {
        return interneVerschilNaarVerschilGroepMap.keySet().isEmpty();
    }

    @Override
    public String toString() {
        final String eol = String.format("%n");
        final StringBuilder sb = new StringBuilder();
        sb.append("Verschillen:").append(eol);
        for (final Verschil verschil : interneVerschilNaarVerschilGroepMap.keySet()) {
            sb.append("Sleutel      : ").append(verschil.getSleutel()).append(eol);
            sb.append("Oude waarde  : ").append(verschil.getOudeWaarde()).append(eol);
            sb.append("Nieuwe waarde: ").append(verschil.getNieuweWaarde()).append(eol);
            sb.append("Type         : ").append(verschil.getVerschilType()).append(eol);
            sb.append(eol);
        }
        return sb.toString();
    }

    @Override
    public Map<BRPActie, Lo3Voorkomen> getActieHerkomstMap() {
        return actieHerkomstMap;
    }

    @Override
    public void koppelHerkomstAanActie(final BRPActie oudeActie, final Lo3Voorkomen nieuweHerkomst) {
        // Bewaar een link tussen de oude actie en de nieuwe herkomst die daarbij hoort
        if (!actieHerkomstMap.containsKey(oudeActie) || actieHerkomstMap.get(oudeActie) == null) {
            actieHerkomstMap.put(oudeActie, nieuweHerkomst);
        }
    }

    @Override
    public void koppelNieuweActieMetBestaandeActie(final BRPActie nieuweActie, final BRPActie opgeslagenActie) {
        actieConsolidatieData.koppelNieuweActieMetOudeActie(nieuweActie, opgeslagenActie);
    }

    @Override
    public void koppelVoorkomenAanActie(final BRPActie actie, final FormeleHistorie voorkomen) {
        actieConsolidatieData.koppelActieMetVoorkomen(actie, voorkomen);
    }

    @Override
    public void koppelStapelMatchAanVoorkomen(final FormeleHistorie voorkomen, final DeltaStapelMatch stapels) {
        actieConsolidatieData.koppelVoorkomenMetStapel(voorkomen, stapels);
    }

    @Override
    public ActieConsolidatieData getActieConsolidatieData() {
        return actieConsolidatieData;
    }

    private boolean isJuisteSleutelType(final Sleutel sleutel, final boolean isVoorkomenSleutel) {
        boolean result = true;
        if (sleutel instanceof IstSleutel) {
            final IstSleutel istSleutel = (IstSleutel) sleutel;
            result = !(isVoorkomenSleutel ^ istSleutel.isVoorkomenSleutel());
        }
        return result;
    }

    @Override
    public Verschil getVerschil(final VerschilType verschilType) {
        Verschil resultaat = null;

        for (final Verschil verschil : getVerschillen()) {
            if (verschilType.equals(verschil.getVerschilType())) {
                resultaat = verschil;
                break;
            }
        }
        return resultaat;
    }

    /**
     * Groepeer verschillen tot VerschilGroepen. Alle verschillen die over 1 historie rij gaan worden gegroepeerd. Alle
     * andere verschillen worden groepen van 1 verschil.
     *
     * @param verschillenParam
     *            De te groeperen verschillen
     */
    private void groepeerVerschillen(final Collection<Verschil> verschillenParam) {
        for (final Verschil verschil : verschillenParam) {
            groepeerVerschil(verschil);
        }
    }

    private void groepeerVerschil(final Verschil verschil) {
        final VerschilGroep verschilGroep = getVerschilGroep(verschil);
        verschilGroep.addVerschil(verschil);
        interneVerschilNaarVerschilGroepMap.put(verschil, verschilGroep);
    }

    private VerschilGroep getVerschilGroep(final Verschil verschil) {
        final FormeleHistorie bestaandeHistorieRij = verschil.getBestaandeHistorieRij();
        final FormeleHistorie nieuweHistorieRij = verschil.getNieuweHistorieRij();

        VerschilGroep verschilGroep = null;
        FormeleHistorie historie = null;

        if (bestaandeHistorieRij != null) {
            verschilGroep = bepaalVerschilGroepVoorHistorie(bestaandeHistorieRij);
            historie = bestaandeHistorieRij;
        } else if (nieuweHistorieRij != null) {
            verschilGroep = bepaalVerschilGroepVoorHistorie(nieuweHistorieRij);
            historie = nieuweHistorieRij;
        }

        if (verschilGroep == null) {
            verschilGroep = VerschilGroep.maakVerschilGroepMetHistorie(historie);
            interneVerschilGroepen.add(verschilGroep);
        }

        return verschilGroep;
    }

    private VerschilGroep bepaalVerschilGroepVoorHistorie(final FormeleHistorie formeleHistorie) {
        VerschilGroep resultaat = null;
        for (final VerschilGroep verschilGroep : interneVerschilGroepen) {
            if (formeleHistorie == verschilGroep.getFormeleHistorie()) {
                resultaat = verschilGroep;
                break;
            }
        }

        return resultaat;
    }

    @Override
    public void addDeltaEntiteitPaar(final DeltaEntiteit bestaandEntiteit, final DeltaEntiteit nieuweEntiteit) {
        if (bestaandEntiteit != null && nieuweEntiteit != null) {
            deltaEntiteitPaarSet.add(new DeltaEntiteitPaar(bestaandEntiteit, nieuweEntiteit));
        }
    }

    /**
     * Geef de verzameling van delta entiteit paren.
     * 
     * @return de verzameling van delta entiteit paren
     */
    @Override
    public Set<DeltaEntiteitPaar> getDeltaEntiteitPaarSet() {
        return Collections.unmodifiableSet(deltaEntiteitPaarSet);
    }

    @Override
    public boolean bevatAlleenInfrastructureleWijzigingen() {
        for (final Verschil verschil : interneVerschilNaarVerschilGroepMap.keySet()) {
            if (!verschil.isToegestaanVoorInfrastructureleWijziging()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean bevatAlleenAnummerWijzigingen() {
        boolean result = false;
        if (bevatNummerverwijzingHistorieWijziging()) {
            for (final Verschil verschil : interneVerschilNaarVerschilGroepMap.keySet()) {
                if (!verschil.isToegestaanVoorAnummerWijziging()) {
                    result = false;
                    break;
                }
                result = true;
            }
        }
        return result;
    }

    private boolean bevatNummerverwijzingHistorieWijziging() {
        final List<Verschil> nummerverwijzingVerschil = zoekVerschillen(new EntiteitSleutel(Persoon.class, "persoonNummerverwijzingHistorieSet"));
        return !nummerverwijzingVerschil.isEmpty();

    }
}

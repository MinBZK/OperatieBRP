/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.Sleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.EntiteitSleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.IstSleutel;

/**
 * Dit object bevat de lijst van verschillen voor een bepaalde groep binnen BRP. Dit kan zowel een A-laag (
 * {@link nl.bzk.algemeenbrp.dal.domein.brp.entity.ALaagHistorieVerzameling}) als een Historie (
 * {@link FormeleHistorie}) object zijn.
 */
public final class VerschilGroep implements Iterable<Verschil> {

    private final List<Verschil> verschillen = new ArrayList<>();
    private final FormeleHistorie formeleHistorie;

    private VerschilGroep(final FormeleHistorie formeleHistorie) {
        this.formeleHistorie = formeleHistorie;
    }

    /**
     * Maakt een kopie op basis de mee gegeven {@link VerschilGroep}, maar kopieert niet de verschillen.
     * @param verschilGroep het object waar een kopie van wordt gemaakt.
     * @return een kopie zonder verschillen
     */
    public static VerschilGroep maakKopieZonderVerschillen(final VerschilGroep verschilGroep) {
        return new VerschilGroep(verschilGroep.formeleHistorie);
    }

    /**
     * Maakt een {@link VerschilGroep} met historie.
     * @param formeleHistorie de historie waar dit object voor gemaakt wordt
     * @return een nieuw {@link VerschilGroep} voor de meegegeven historie
     */
    public static VerschilGroep maakVerschilGroepMetHistorie(final FormeleHistorie formeleHistorie) {
        return new VerschilGroep(formeleHistorie);
    }

    /**
     * Maakt een {@link VerschilGroep} zonder historie.
     * @return een nieuw {@link VerschilGroep} zonder historie
     */
    public static VerschilGroep maakVerschilGroepZonderHistorie() {
        return new VerschilGroep(null);
    }

    /**
     * Returns the element at the specified position in this list.
     * @param index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size())
     */
    public Verschil get(final int index) {
        return verschillen.get(index);
    }

    /**
     * True als deze groep verschillen bij een historie rij hoort.
     * @return true als deze groep verschillen bij een historie rij hoort.
     */
    public boolean isHistorieVerschil() {
        return formeleHistorie != null;
    }

    /**
     * @return the number of elements in this list. If this list contains more than Integer.MAX_VALUE elements, returns Integer.MAX_VALUE.
     */
    public int size() {
        return verschillen.size();
    }

    /**
     * @return true als deze groep verschillen leeg is.
     */
    public boolean isEmpty() {
        return verschillen.isEmpty();
    }

    @Override
    public Iterator<Verschil> iterator() {
        return verschillen.iterator();
    }

    /**
     * @return een lijst met verschillen binnen deze groep die niet gewijzigd kan worden
     */
    public List<Verschil> getVerschillen() {
        return Collections.unmodifiableList(verschillen);
    }

    /**
     * Voegt een verschil toe aan de lijst van verschillen. Als het verschil al voorkomt, dan wordt het verschil niet
     * toegevoegd.
     * @param verschil het verschil dat mogelijk toegevoegd wordt aan de lijst.
     */
    public void addVerschil(final Verschil verschil) {
        verschillen.add(verschil);
    }

    /**
     * Voegt een lijst van verschillen toe aan de bestaande lijst van verschillen. Als het verschil al voorkomt, dan
     * wordt het verschil niet toegevoegd.
     * @param nieuweVerschillen de lijst met nieuwe verschillen die mogelijk worden toegevoegd aan de lijst.
     */
    public void addVerschillen(final List<Verschil> nieuweVerschillen) {
        verschillen.addAll(nieuweVerschillen);
    }

    /**
     * Verwijdert het meegegeven verschil uit deze verschilgroep.
     * @param verschil het verschil wat verwijderd moet worden
     * @return true als het verschil succesvol verwijderd is. (zie ook {@link List#remove(Object)}
     */
    public boolean verwijderVerschil(final Verschil verschil) {
        return verschillen.remove(verschil);
    }

    /**
     * @return geeft de {@link FormeleHistorie} object terug waarvoor deze groep is aangemaakt, null als het voor een {@link
     * nl.bzk.algemeenbrp.dal.domein.brp.entity.ALaagHistorieVerzameling} is gemaakt.
     */
    public FormeleHistorie getFormeleHistorie() {
        return formeleHistorie;
    }

    /**
     * Geef de class van de historie groep waarvoor deze verschillen gelden.
     * @return de naam van de historie groep, of null als deze verschilgroep niet over historie gaat.
     */
    public Class<? extends FormeleHistorie> getHistorieGroepClass() {
        final Class<? extends FormeleHistorie> result;

        if (isHistorieVerschil() && size() > 0) {
            final Verschil verschil = get(0);
            if (verschil.getBestaandeHistorieRij() != null) {
                result = verschil.getBestaandeHistorieRij().getClass();
            } else {
                result = verschil.getNieuweHistorieRij().getClass();
            }
        } else {
            result = null;
        }

        return result;
    }

    @Override
    public String toString() {
        String result = "";
        if (verschillen.isEmpty()) {
            result = "";
        } else {
            final Sleutel sleutel = verschillen.get(0).getSleutel();
            final Class<? extends Sleutel> sleutelClass = sleutel.getClass();
            if (sleutelClass.isAssignableFrom(EntiteitSleutel.class)) {
                final EntiteitSleutel entiteitSleutel = (EntiteitSleutel) sleutel;
                result = entiteitSleutel.getEntiteit().getName() + ", " + entiteitSleutel.getVeld();
            } else if (sleutelClass.isAssignableFrom(IstSleutel.class)) {
                final IstSleutel istSleutel = (IstSleutel) sleutel;
                final Integer catNr = Integer.valueOf(istSleutel.getCategorie());

                if (istSleutel.getVoorkomennummer() != null) {
                    result = String.format("IST-%02d-%02d-%02d", catNr, istSleutel.getStapelnummer(), istSleutel.getVoorkomennummer());
                } else {
                    result = String.format("IST-%02d-%02d", catNr, istSleutel.getStapelnummer());
                }
            }
        }
        return result;
    }

    /**
     * Filtert de verschillen mbt onderzoek uit de verschilgroep.
     * @return de lijst van verschillen mbt onderzoek.
     */
    public List<Verschil> filterOnderzoekVerschillen() {
        final List<Verschil> onderzoekVerschillen = new ArrayList<>();
        for (final Iterator<Verschil> verschilIterator = verschillen.iterator(); verschilIterator.hasNext(); ) {
            final Verschil verschil = verschilIterator.next();
            if (verschil.isOnderzoekVerschil()) {
                onderzoekVerschillen.add(verschil);
                verschilIterator.remove();
            }
        }
        return onderzoekVerschillen;
    }
}

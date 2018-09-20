/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp;

import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.Stapel;

import org.simpleframework.xml.ElementList;

/**
 * Deze class representeert een BRP stapel. Dit is een lijst met BRP groepen die samen de actuele en historische
 * gegevens van deze groep representeren.
 * 
 * 
 * 
 * @param <I>
 *            brp groep inhoud
 */
public final class BrpStapel<I extends BrpGroepInhoud> extends Stapel<BrpGroep<I>> {

    /**
     * Maakt een BRP-groep stapel aan.
     * 
     * @param groepen
     *            de lijst met BRP groepen. Deze mag niet leeg zijn.
     * @throws IllegalArgumentException
     *             als groepen een lege lijst is
     * @throws NullPointerException
     *             als groepen null is
     */
    public BrpStapel(
            @ElementList(name = "groepen", inline = true, type = BrpGroep.class, required = false) final List<BrpGroep<I>> groepen) {
        super(groepen);
    }

    /**
     * @return BRP groepen
     */
    @ElementList(name = "groepen", inline = true, type = BrpGroep.class, required = false)
    public List<BrpGroep<I>> getGroepen() {
        return super.getElementen();
    }

    /**
     * Valideer de inhoud van deze BRP stapel.
     */
    public void valideer() {
        for (final BrpGroep<I> brpGroep : getGroepen()) {
            brpGroep.getInhoud().valideer();
        }
    }

    /**
     * Controleer of de stapel een actuele groep bevat. Dit wil zeggen dat de groep niet is vervallen en geen einde
     * geldigheid heeft.
     * 
     * @return true als deze stapel een actuele groep bevat
     */
    public boolean bevatActueel() {
        for (final BrpGroep<I> brpGroep : getGroepen()) {
            final BrpHistorie hist = brpGroep.getHistorie();
            if (!hist.isVervallen() && hist.getDatumEindeGeldigheid() == null) {
                return true;
            }
        }
        return false;
    }
}

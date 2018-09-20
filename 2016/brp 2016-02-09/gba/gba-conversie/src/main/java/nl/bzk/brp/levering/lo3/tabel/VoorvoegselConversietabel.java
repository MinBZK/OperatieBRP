/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.tabel;

import java.util.List;

import nl.bzk.brp.model.algemeen.stamgegeven.conv.ConversieVoorvoegsel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.VoorvoegselScheidingstekenPaar;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch.AbstractVoorvoegselConversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;

/**
 * Conversie van 'LO3 Voorvoegsel geslachtsnaam' naar een unieke combinatie van 'BRP Voorvoegsel' en 'BRP
 * Scheidingsteken'.
 */
public final class VoorvoegselConversietabel extends AbstractVoorvoegselConversietabel {

    /**
     * Maakt een VoorvoegselConversietabel object.
     *
     * @param list de lijst met waarden uit de conversietabel.
     */
    public VoorvoegselConversietabel(final List<ConversieVoorvoegsel> list) {
        super(new Converter().converteer(list));
    }

    /**
     * Converteer de lijst van {@link ConversieVoorvoegsel} naar een conversie map van de LO3 waarde {@link String} en
     * de BRP waarde {@link VoorvoegselScheidingstekenPaar}.
     */
    private static final class Converter extends AbstractLijstConverter<ConversieVoorvoegsel, Lo3String, VoorvoegselScheidingstekenPaar> {
        @Override
        protected Lo3String maakLo3Waarde(final ConversieVoorvoegsel voorvoegsel) {
            return new Lo3String(voorvoegsel.getRubriek0231Voorvoegsel().getWaarde(), null);
        }

        @Override
        protected VoorvoegselScheidingstekenPaar maakBrpWaarde(final ConversieVoorvoegsel voorvoegsel) {
            final BrpString brpVoorvoegsel;
            if (voorvoegsel.getVoorvoegsel() == null) {
                brpVoorvoegsel = null;
            } else {
                brpVoorvoegsel = new BrpString(voorvoegsel.getVoorvoegsel().getWaarde(), null);
            }

            final BrpCharacter brpScheidingsteken;
            if (voorvoegsel.getScheidingsteken() == null) {
                brpScheidingsteken = null;
            } else {
                brpScheidingsteken = new BrpCharacter(voorvoegsel.getScheidingsteken().getWaarde().charAt(0), null);
            }

            return new VoorvoegselScheidingstekenPaar(brpVoorvoegsel, brpScheidingsteken);
        }
    }
}

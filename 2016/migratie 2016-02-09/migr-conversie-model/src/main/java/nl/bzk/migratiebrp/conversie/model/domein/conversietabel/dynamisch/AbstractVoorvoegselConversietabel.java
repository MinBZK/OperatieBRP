/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.AbstractConversietabel;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.VoorvoegselScheidingstekenPaar;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;

/**
 * Conversie van 'LO3 Voorvoegsel geslachtsnaam' naar een unieke combinatie van 'BRP Voorvoegsel' en 'BRP
 * Scheidingsteken'.
 * 
 */
public abstract class AbstractVoorvoegselConversietabel extends AbstractConversietabel<Lo3String, VoorvoegselScheidingstekenPaar> {

    /**
     * Maakt een VoorvoegselConversietabel object.
     * 
     * @param conversieLijst
     *            de lijst met voorvoegsel conversies
     */
    public AbstractVoorvoegselConversietabel(final List<Entry<Lo3String, VoorvoegselScheidingstekenPaar>> conversieLijst) {
        super(conversieLijst);
    }

    @Override
    protected final Lo3Onderzoek bepaalOnderzoekLo3(final Lo3String input) {
        if (input == null) {
            return null;
        } else {
            return input.getOnderzoek();
        }
    }

    @Override
    protected final Lo3Onderzoek bepaalOnderzoekBrp(final VoorvoegselScheidingstekenPaar input) {
        if (input == null) {
            return null;
        } else {
            final List<Lo3Onderzoek> onderzoeken = new ArrayList<>();
            if (input.getVoorvoegsel() != null && input.getVoorvoegsel().getOnderzoek() != null) {
                onderzoeken.add(input.getVoorvoegsel().getOnderzoek());
            }
            if (input.getScheidingsteken() != null && input.getScheidingsteken().getOnderzoek() != null) {
                onderzoeken.add(input.getScheidingsteken().getOnderzoek());
            }
            return Lo3Onderzoek.bepaalRelevantOnderzoek(onderzoeken);
        }
    }

    @Override
    protected final Lo3String voegOnderzoekToeLo3(final Lo3String input, final Lo3Onderzoek onderzoek) {
        final Lo3String resultaat;
        if (nl.bzk.migratiebrp.conversie.model.lo3.element.Validatie.isElementGevuld(input)) {
            resultaat = new Lo3String(input.getWaarde(), onderzoek);
        } else {
            if (onderzoek != null) {
                resultaat = new Lo3String(null, onderzoek);
            } else {
                resultaat = null;
            }
        }

        return resultaat;
    }

    @Override
    protected final VoorvoegselScheidingstekenPaar voegOnderzoekToeBrp(final VoorvoegselScheidingstekenPaar input, final Lo3Onderzoek onderzoek) {
        final VoorvoegselScheidingstekenPaar resultaat;
        if (input == null || !(Validatie.isAttribuutGevuld(input.getScheidingsteken()) || Validatie.isAttribuutGevuld(input.getVoorvoegsel()))) {
            if (onderzoek == null) {
                resultaat = null;
            } else {
                resultaat = new VoorvoegselScheidingstekenPaar(new BrpString(null, onderzoek), new BrpCharacter(null, onderzoek));
            }
        } else {
            final BrpString voorvoegsel = BrpString.wrap(BrpString.unwrap(input.getVoorvoegsel()), onderzoek);
            final BrpCharacter scheidingsteken = BrpCharacter.wrap(BrpCharacter.unwrap(input.getScheidingsteken()), onderzoek);
            return new VoorvoegselScheidingstekenPaar(voorvoegsel, scheidingsteken);
        }

        return resultaat;
    }
}

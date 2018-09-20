/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAangeverCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenWijzigingVerblijfCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.AangeverRedenWijzigingVerblijfPaar;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.AbstractConversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AangifteAdreshouding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Validatie;

/**
 * De Conversietabel voor aangever adreshouding. Deze mapt een Lo3AangifteAdreshouding op de unieke combinatie van
 * BrpAangeverCode en BrpRedenWijzigingVerblijfCode.
 */
public abstract class AbstractAangeverRedenWijzigingVerblijfConversietabel extends
        AbstractConversietabel<Lo3AangifteAdreshouding, AangeverRedenWijzigingVerblijfPaar>
{

    /**
     * Maakt een AangeverRedenWijzigingVerblijfConversietabel object.
     * 
     * @param conversieLijst
     *            de lijst met alle AangifteAdreshouding entries uit de conversietabel
     */
    protected AbstractAangeverRedenWijzigingVerblijfConversietabel(
        final List<Entry<Lo3AangifteAdreshouding, AangeverRedenWijzigingVerblijfPaar>> conversieLijst)
    {
        super(conversieLijst);
    }

    @Override
    protected final Lo3Onderzoek bepaalOnderzoekLo3(final Lo3AangifteAdreshouding input) {
        if (input == null) {
            return null;
        } else {
            return input.getOnderzoek();
        }
    }

    @Override
    protected final Lo3Onderzoek bepaalOnderzoekBrp(final AangeverRedenWijzigingVerblijfPaar input) {
        if (input == null) {
            return null;
        } else {
            final List<Lo3Onderzoek> onderzoeken = new ArrayList<>();
            if (input.getBrpAangeverCode() != null && input.getBrpAangeverCode().getOnderzoek() != null) {
                onderzoeken.add(input.getBrpAangeverCode().getOnderzoek());
            }
            if (input.getBrpRedenWijzigingVerblijfCode() != null && input.getBrpRedenWijzigingVerblijfCode().getOnderzoek() != null) {
                onderzoeken.add(input.getBrpRedenWijzigingVerblijfCode().getOnderzoek());
            }
            return Lo3Onderzoek.bepaalRelevantOnderzoek(onderzoeken);
        }
    }

    @Override
    protected final Lo3AangifteAdreshouding voegOnderzoekToeLo3(final Lo3AangifteAdreshouding input, final Lo3Onderzoek onderzoek) {
        final Lo3AangifteAdreshouding resultaat;
        if (!Validatie.isElementGevuld(input)) {
            if (onderzoek == null) {
                resultaat = null;
            } else {
                resultaat = new Lo3AangifteAdreshouding(null, onderzoek);
            }
        } else {
            resultaat = new Lo3AangifteAdreshouding(input.getWaarde(), onderzoek);
        }

        return resultaat;
    }

    @Override
    protected final AangeverRedenWijzigingVerblijfPaar voegOnderzoekToeBrp(final AangeverRedenWijzigingVerblijfPaar input, final Lo3Onderzoek onderzoek) {
        final AangeverRedenWijzigingVerblijfPaar resultaat;
        if (input == null) {
            if (onderzoek == null) {
                resultaat = null;
            } else {
                resultaat =
                        new AangeverRedenWijzigingVerblijfPaar(new BrpAangeverCode(null, onderzoek), new BrpRedenWijzigingVerblijfCode(null, onderzoek));
            }
        } else {
            final BrpAangeverCode aangeverCode =
                    input.getBrpAangeverCode() == null || input.getBrpAangeverCode().getWaarde() == null ? null : new BrpAangeverCode(
                        input.getBrpAangeverCode().getWaarde(),
                        onderzoek);
            final BrpRedenWijzigingVerblijfCode redenCode;
            if (input.getBrpRedenWijzigingVerblijfCode() == null || input.getBrpRedenWijzigingVerblijfCode().getWaarde() == null) {
                redenCode = null;
            } else {
                redenCode = new BrpRedenWijzigingVerblijfCode(input.getBrpRedenWijzigingVerblijfCode().getWaarde(), onderzoek);
            }
            resultaat = new AangeverRedenWijzigingVerblijfPaar(aangeverCode, redenCode);
        }

        return resultaat;
    }
}

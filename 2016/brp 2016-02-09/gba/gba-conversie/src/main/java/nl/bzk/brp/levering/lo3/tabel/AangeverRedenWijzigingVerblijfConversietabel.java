/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.tabel;

import java.util.List;

import nl.bzk.brp.model.algemeen.stamgegeven.conv.ConversieAangifteAdreshouding;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAangeverCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenWijzigingVerblijfCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.AangeverRedenWijzigingVerblijfPaar;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch.AbstractAangeverRedenWijzigingVerblijfConversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AangifteAdreshouding;

/**
 * De Conversietabel voor aangever adreshouding. Deze mapt een Lo3AangifteAdreshouding op de unieke combinatie van
 * BrpAangeverAdreshoudingCode en BrpRedenWijzigingAdresCode.
 */
public final class AangeverRedenWijzigingVerblijfConversietabel extends AbstractAangeverRedenWijzigingVerblijfConversietabel {

    /**
     * Maakt een AangeverRedenWijzigingVerblijfConversietabel object.
     *
     * @param list de lijst met waarden uit de conversietabel.
     */
    public AangeverRedenWijzigingVerblijfConversietabel(final List<ConversieAangifteAdreshouding> list) {
        super(new Converter().converteer(list));
    }

    /**
     * Converteer de lijst van {@link ConversieAangifteAdreshouding} naar een conversie map van de LO3 waarde
     * {@link Lo3AangifteAdreshouding} en de BRP waarde {@link AangeverRedenWijzigingVerblijfPaar}.
     */
    private static final class Converter extends
            AbstractLijstConverter<ConversieAangifteAdreshouding, Lo3AangifteAdreshouding, AangeverRedenWijzigingVerblijfPaar>
    {
        @Override
        protected Lo3AangifteAdreshouding maakLo3Waarde(final ConversieAangifteAdreshouding aangifteAdreshouding) {
            return new Lo3AangifteAdreshouding(String.valueOf(aangifteAdreshouding.getRubriek7210OmschrijvingVanDeAangifteAdreshouding()));
        }

        @Override
        protected AangeverRedenWijzigingVerblijfPaar maakBrpWaarde(final ConversieAangifteAdreshouding aangifteAdreshouding) {
            BrpRedenWijzigingVerblijfCode brpRedenWijzigingVerblijfCode = null;
            if (aangifteAdreshouding.getRedenWijzigingVerblijf() != null) {
                brpRedenWijzigingVerblijfCode =
                        new BrpRedenWijzigingVerblijfCode(aangifteAdreshouding.getRedenWijzigingVerblijf().getCode().getWaarde().charAt(0));
            }
            BrpAangeverCode brpAangeverCode = null;
            if (aangifteAdreshouding.getAangever() != null) {
                brpAangeverCode = new BrpAangeverCode(aangifteAdreshouding.getAangever().getCode().getWaarde().charAt(0));
            }

            return new AangeverRedenWijzigingVerblijfPaar(brpAangeverCode, brpRedenWijzigingVerblijfCode);
        }
    }
}

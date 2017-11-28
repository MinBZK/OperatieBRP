/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.tabel;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AangifteAdreshouding;
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
     * @param list de lijst met waarden uit de conversietabel.
     */
    public AangeverRedenWijzigingVerblijfConversietabel(final List<AangifteAdreshouding> list) {
        super(new Converter().converteer(list));
    }

    /**
     * Converteer de lijst van {@link AangifteAdreshouding} naar een conversie map van de LO3 waarde
     * {@link Lo3AangifteAdreshouding} en de BRP waarde {@link AangeverRedenWijzigingVerblijfPaar}.
     */
    private static final class Converter
            extends AbstractLijstConverter<AangifteAdreshouding, Lo3AangifteAdreshouding, AangeverRedenWijzigingVerblijfPaar> {
        @Override
        protected Lo3AangifteAdreshouding maakLo3Waarde(final AangifteAdreshouding aangifteAdreshouding) {
            return new Lo3AangifteAdreshouding(Character.toString(aangifteAdreshouding.getLo3OmschrijvingAangifteAdreshouding()));
        }

        @Override
        protected AangeverRedenWijzigingVerblijfPaar maakBrpWaarde(final AangifteAdreshouding aangifteAdreshouding) {
            BrpRedenWijzigingVerblijfCode brpRedenWijzigingVerblijfCode = null;
            if (aangifteAdreshouding.getRedenWijzigingVerblijf() != null) {
                brpRedenWijzigingVerblijfCode = new BrpRedenWijzigingVerblijfCode(aangifteAdreshouding.getRedenWijzigingVerblijf().getCode());
            }
            BrpAangeverCode brpAangeverCode = null;
            if (aangifteAdreshouding.getAangever() != null) {
                brpAangeverCode = new BrpAangeverCode(aangifteAdreshouding.getAangever().getCode());
            }

            return new AangeverRedenWijzigingVerblijfPaar(brpAangeverCode, brpRedenWijzigingVerblijfCode);
        }
    }
}

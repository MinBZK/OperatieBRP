/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import nl.bzk.brp.constanten.BrpConstanten;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Postcode;
import nl.bzk.brp.model.bericht.kern.basis.AbstractPersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.logisch.kern.PersoonAdresStandaardGroep;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.constraint.ConditioneelVerplichtVeld;
import nl.bzk.brp.model.validatie.constraint.ConditioneelVerplichteVelden;


/**
 * Voor de modellering van buitenlands adres waren enkele opties:
 * - Adres in een attribuut met 'regelovergang' tekens
 * Nadeel:
 * Regelovergangtekens zijn niet platformonafhankelijk en het maximale aantal regels is niet goed af te dwingen.
 * - Adres uitsplitsen volgens een of andere norm (wordt naar gezocht)
 * RNI heeft een actie gestart om te kijken of binnen Europa een werkbare standaard te vinden is. Wereldwijd gaat niet
 * lukken. (Voorlopig) nog geen optie.
 * - Adres per regel opnemen.
 * - Adresregels in een aparte tabel.
 * Is ook mogelijk mits aantal regels beperkt wordt.
 * Uiteindelijk is gekozen voor opname per regel. Dat lijkt minder flexibel dan een vrij veld waarin meerdere regels
 * geplaatst kunnen worden. Het geeft de afnemer echter wel duidelijkheid over het maximale aantal regels en het
 * maximale aantal karakters per regel dat deze kan verwachten. Het aantal zes is afkomstig uit onderzoek door de RNI
 * inzake de maximale grootte van internationale adressen.
 * RvdP 5 september 2011, verplaatst naar nieuwe groep standaard op 13 jan 2012.
 *
 *
 *
 */
@ConditioneelVerplichteVelden({
    // Verplicht zijn wanneer land NL (landcode 6030) is
    // Note: de message attribute is hier nodig anders kan de validatie framework geen onderscheidt maken tussen de
    // validatie errors en ontstaat er maar 1 error uitkomst.
    @ConditioneelVerplichtVeld(naamVerplichtVeld = "soort", naamAfhankelijkVeld = "land.code",
                               waardeAfhankelijkVeld = BrpConstanten.NL_LAND_CODE_STRING,
                               code = MeldingCode.BRAL2032, message = "BRAL2032"),
    @ConditioneelVerplichtVeld(naamVerplichtVeld = "datumAanvangAdreshouding", naamAfhankelijkVeld = "land.code",
                               waardeAfhankelijkVeld = BrpConstanten.NL_LAND_CODE_STRING, code = MeldingCode.BRAL2033,
                               message = "BRAL2033"),
    @ConditioneelVerplichtVeld(naamVerplichtVeld = "gemeente.code", naamAfhankelijkVeld = "land.code",
                               waardeAfhankelijkVeld = BrpConstanten.NL_LAND_CODE_STRING,
                               code = MeldingCode.BRAL2033, message = "BRAL2033"),
    @ConditioneelVerplichtVeld(naamVerplichtVeld = "redenWijziging.code", naamAfhankelijkVeld = "land.code",
                               waardeAfhankelijkVeld = BrpConstanten.NL_LAND_CODE_STRING,
                               code = MeldingCode.BRAL2033,
                               message = "BRAL2033"),
    @ConditioneelVerplichtVeld(naamVerplichtVeld = "aangeverAdreshouding",
                               naamAfhankelijkVeld = "redenWijziging.code",
                               waardeAfhankelijkVeld = BrpConstanten.PERSOON_REDEN_WIJZIGING_ADRES_CODE_STRING,
                               code = MeldingCode.BRAL1118, message = "BRAL1118")
})
public class PersoonAdresStandaardGroepBericht extends AbstractPersoonAdresStandaardGroepBericht implements
        PersoonAdresStandaardGroep
{
    @nl.bzk.brp.model.validatie.constraint.Datum
    @Override
    public Datum getDatumAanvangAdreshouding() {
        return super.getDatumAanvangAdreshouding();
    }

    @nl.bzk.brp.model.validatie.constraint.Postcode
    @Override
    public Postcode getPostcode() {
        return super.getPostcode();
    }

    @nl.bzk.brp.model.validatie.constraint.Datum
    @Override
    public Datum getDatumVertrekUitNederland() {
        return super.getDatumVertrekUitNederland();
    }
}

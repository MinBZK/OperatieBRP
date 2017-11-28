/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElementInterface;

/**
 * Een actie die deel uitmaakt van een administratieve handeling in een bijhoudingsbericht.
 *
 */
@XmlElementInterface(ActieRegister.class)
public interface ActieElement extends BmrObjecttype {

    /**
     * Geef de waarde van datumAanvangGeldigheid.
     *
     * @return datumAanvangGeldigheid
     */
    DatumElement getDatumAanvangGeldigheid();

    /**
     * Geef de waarde van datumAanvangGeldigheid.
     *
     * @return datumAanvangGeldigheid
     */
    DatumElement getDatumEindeGeldigheid();

    /**
     * Geef de waarde van bronnen.
     *
     * @return bronnen
     */
    List<BronReferentieElement> getBronReferenties();

    /**
     * Geef het soort actie.
     *
     * @return soort actie
     */
    SoortActie getSoortActie();

    /**
     * Maakt een actie entiteit o.b.v. de administratieve handeling entiteit en dit actie element.
     *
     * @param administratieveHandeling de administratieve handeling
     * @return een nieuwe actie entiteit
     */
    BRPActie maakActieEntiteit(AdministratieveHandeling administratieveHandeling);

    /**
     * Voert de verwerking uit.
     *
     * @param bericht het bericht waar deze actie deel vanuit maakt
     * @param administratieveHandeling de administratieve handeling waarvoor acties worden aangemaakt
     * @return die actie entiteit de gebruikt is voor de verwerking, of null als er geen verwerking heeft plaatsgevonden
     */
    BRPActie verwerk(BijhoudingVerzoekBericht bericht, AdministratieveHandeling administratieveHandeling);

    /**
     * Bepaal alle hoofdpersonen uit de actie.
     *
     * @return List {@link BijhoudingPersoon}
     */
    @Bedrijfsregel(Regel.R1287)
    List<BijhoudingPersoon> getHoofdPersonen();

    /**
     * Geef de persoon elementen die horen bij deze actie.
     *
     * @return List {@link PersoonElement}
     */
    List<PersoonElement> getPersoonElementen();

    /**
     * Geef persoon element terug.
     * by default wordt de eerste uit de lijst met persoonelementen terug gegeven.
     *
     * @return {@link PersoonElement}
     */
    default PersoonElement getPersoonElement(){
     if(getPersoonElementen().isEmpty()){
        return null;
     }
     return getPersoonElementen().get(0);
    }

    /**
     * Geef de waarde van peilDatum horende bij de actie. Ten behoeve van Regel.R1882.
     *
     * @return peilDatum DateElement.
     */
    DatumElement getPeilDatum();

    /**
     * @return true als de actie invloed heeft op de gerelateerden van de hoofdpersoon
     */
    default boolean heeftInvloedOpGerelateerden() {
        return false;
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Nationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;

/**
 * Interface om aan te geven dat deze actie gegevens bevat van ouders.
 */
public interface ActieMetOuderGegevens {

    /**
     * voeg ouder indicaties toe aan kind entiteit
     * @param actie actie
     * @param kindEntiteit kind
     * @param ouders ouder
     * @param verzoekBericht verzoek bericht
     */
    default void verwerkBVP(final BRPActie actie, final BijhoudingPersoon kindEntiteit, final List<PersoonElement> ouders,
                            final BijhoudingVerzoekBericht verzoekBericht) {
        final List<PersoonIndicatie> persoonIndicaties = verzamelBijzondereVerblijfrechtelijkePositieIndicatiesVanOuders(ouders);
        if (!persoonIndicaties.isEmpty()) {
            final ActieElement
                    registratieNationaliteit =
                    verzoekBericht.getAdministratieveHandeling().getActieBySoort(SoortActie.REGISTRATIE_NATIONALITEIT);
            if (registratieNationaliteit == null
                    || !Nationaliteit.NEDERLANDSE
                    .equals(registratieNationaliteit.getPersoonElementen().get(0).getNationaliteit().getNationaliteitCode().getWaarde())) {
                kindEntiteit.kopieerIndicatie(persoonIndicaties.get(0), actie);
            }
        }
    }

    /**
     * verzamel BijzondereVerblijfrechtelijkePositie indicaties van ouders.
     * @param ouders ouders
     * @return lijst met persoon indicaties
     */
    default List<PersoonIndicatie> verzamelBijzondereVerblijfrechtelijkePositieIndicatiesVanOuders(final List<PersoonElement> ouders) {
        final List<PersoonIndicatie> indicaties = new ArrayList<>();
        for (final PersoonElement ouder : ouders) {
            if (ouder.heeftPersoonEntiteit()) {
                final BijhoudingPersoon ouderPersoon = ouder.getPersoonEntiteit();
                final PersoonIndicatie persoonIndicatie = ouderPersoon.getPersoonIndicatie(SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE);
                if (persoonIndicatie != null
                        && FormeleHistorieZonderVerantwoording.heeftActueelVoorkomen(persoonIndicatie.getPersoonIndicatieHistorieSet())) {
                    indicaties.add(persoonIndicatie);
                }
            }
        }
        return indicaties;
    }

    /**
     * Verzamelt de ouders ({@link PersoonElement}) uit deze actie.
     * @return een lijst met de {@link PersoonElement} objecten van de ouders
     */
    List<PersoonElement> getOuders();

    /**
     * Geeft lijst met nouwkig's terug.
     * @return een lijst met nouwkig's
     */
    List<PersoonElement> getNouwkigs();

    /**
     * Geef de waarde van peilDatum horende bij de actie. Ten behoeve van Regel.R1882.
     *
     * @return peilDatum DateElement.
     */
    DatumElement getPeilDatum();

    /**
     * Geef het kind element terug.
     *
     * @return het kind {@link PersoonElement}
     */
    PersoonElement getKind();

}

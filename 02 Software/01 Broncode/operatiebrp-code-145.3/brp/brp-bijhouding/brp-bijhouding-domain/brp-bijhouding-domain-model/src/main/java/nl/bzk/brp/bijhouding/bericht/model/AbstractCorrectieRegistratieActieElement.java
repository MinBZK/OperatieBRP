/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.MaterieleHistorie;

/**
 * Dit is de basis class voor alle correctie registratie acties van de bijhouding.
 * @param <T> het type voorkomen dat door deze correctie actie geregistreerd wordt
 */
public abstract class AbstractCorrectieRegistratieActieElement<T extends FormeleHistorie> extends AbstractActieElement {
    /**
     * Maakt een AbstractCorrectieRegistratieActieElement object.
     * @param basisAttribuutGroep de basis attribuutgroep
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     * @param datumEindeGeldigheid datum einde geldigheid
     * @param bronReferenties bron referenties
     */
    public AbstractCorrectieRegistratieActieElement(final Map<String, String> basisAttribuutGroep,
                                                    final DatumElement datumAanvangGeldigheid,
                                                    final DatumElement datumEindeGeldigheid,
                                                    final List<BronReferentieElement> bronReferenties) {
        super(basisAttribuutGroep, datumAanvangGeldigheid, datumEindeGeldigheid, bronReferenties);
    }

    @Override
    public final BRPActie verwerk(final BijhoudingVerzoekBericht bericht, final AdministratieveHandeling administratieveHandeling) {
        final BRPActie actie;
        if (zijnAlleHoofdPersonenVerwerkbaar()) {
            actie = maakActieEntiteit(administratieveHandeling);

            final T nieuweHistorie = maakNieuwVoorkomen();
            if (nieuweHistorie instanceof MaterieleHistorie) {
                final MaterieleHistorie materieleHistorie = (MaterieleHistorie) nieuweHistorie;
                materieleHistorie.setActieInhoud(actie);
                materieleHistorie.setDatumAanvangGeldigheid(getDatumAanvangGeldigheid().getWaarde());
                materieleHistorie.setDatumEindeGeldigheid(BmrAttribuut.getWaardeOfNull(getDatumEindeGeldigheid()));
                materieleHistorie.setDatumTijdRegistratie(actie.getDatumTijdRegistratie());
                MaterieleHistorie.voegVoorkomenToe(materieleHistorie, (Set<MaterieleHistorie>) bepaalSetVoorNieuwVoorkomen());
            } else {
                BijhoudingEntiteit.voegFormeleHistorieToe(nieuweHistorie, actie, bepaalSetVoorNieuwVoorkomen());
            }
        } else {
            actie = null;
        }
        return actie;
    }

    /**
     * De methode bepaald de set van voorkomens waaraan het nieuw te maken voorkomen moet worden toegevoegd.
     * @return de set van voorkomens die moet worden uitgebreid met een nieuw voorkomen
     */
    protected abstract Set<T> bepaalSetVoorNieuwVoorkomen();


    /**
     * De methode maakt een nieuw voorkomen op basis van het correctie bericht en de gegeven actie.
     * @return het nieuwe voorkomen
     */
    protected abstract T maakNieuwVoorkomen();
}

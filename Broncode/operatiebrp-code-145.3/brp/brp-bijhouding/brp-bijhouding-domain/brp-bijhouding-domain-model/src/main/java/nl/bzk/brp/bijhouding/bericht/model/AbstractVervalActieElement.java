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

/**
 * Dit is de basis class voor alle verval acties van de bijhouding. Deze actie laat alle voorkomens vervallen
 * die horen bij de aangewezen groep.
 */
public abstract class AbstractVervalActieElement extends AbstractActieElement {

    /**
     * Maakt een AbstractVervalActieElement object.
     * @param basisAttribuutGroep de basis attribuutgroep
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     * @param datumEindeGeldigheid datum einde geldigheid
     * @param bronReferenties bron referenties
     */
    public AbstractVervalActieElement(final Map<String, String> basisAttribuutGroep,
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
            for (final FormeleHistorie teVervallenVoorkomen : bepaalTeVervallenVoorkomens()) {
                teVervallenVoorkomen.laatVervallen(actie, BmrAttribuut.getWaardeOfNull(getNadereAanduidingVerval()));
                verwijderIstGegevens();
            }
        } else {
            actie = null;
        }
        return actie;
    }

    /**
     * Bepaal aan de hand van de actie en ojectttype de voorkomens (mogelijke van meerdere groepen) die komen te vervallen.
     * @return het voorkomen dat moet komen te vervallen
     */
    protected abstract Set<FormeleHistorie> bepaalTeVervallenVoorkomens();

    /**
     * Geeft de nadere aanduiding verval terug die is meegegeven in het correctie bericht of null als deze niet is meegegeven.
     * @return de nadere aanduiding verval
     */
    protected abstract CharacterElement getNadereAanduidingVerval();

    /**
     * Implementaties van dit soort actie moeten eventueel de IST gegevens bijwerken n.a.v. het vervallen van relaties.
     */
    protected abstract void verwijderIstGegevens();
}

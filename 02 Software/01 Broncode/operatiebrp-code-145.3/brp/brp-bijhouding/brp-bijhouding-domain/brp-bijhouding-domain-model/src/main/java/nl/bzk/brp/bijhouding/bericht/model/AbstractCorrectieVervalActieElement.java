/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;

/**
 * Dit is de basis class voor alle correctie verval acties van de bijhouding.
 * @param <T> het type voorkomen dat door deze correctie vervallen wordt
 */
public abstract class AbstractCorrectieVervalActieElement<T extends FormeleHistorie> extends AbstractActieElement {

    private final CharacterElement nadereAanduidingVerval;

    /**
     * Maakt een AbstractCorrectieVervalActieElement object.
     * @param basisAttribuutGroep de basis attribuutgroep
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     * @param datumEindeGeldigheid datum einde geldigheid
     * @param bronReferenties bron referenties
     * @param nadereAanduidingVerval nadere aanduiding verval
     */
    public AbstractCorrectieVervalActieElement(final Map<String, String> basisAttribuutGroep,
                                               final DatumElement datumAanvangGeldigheid,
                                               final DatumElement datumEindeGeldigheid,
                                               final List<BronReferentieElement> bronReferenties,
                                               final CharacterElement nadereAanduidingVerval) {
        super(basisAttribuutGroep, datumAanvangGeldigheid, datumEindeGeldigheid, bronReferenties);
        this.nadereAanduidingVerval = nadereAanduidingVerval;
    }

    /**
     * Geeft nadere Aanduiding Verval.
     * @return nadereAanduidingVerval
     */
    public final CharacterElement getNadereAanduidingVerval() {
        return nadereAanduidingVerval;
    }

    @Override
    public final BRPActie verwerk(final BijhoudingVerzoekBericht bericht, final AdministratieveHandeling administratieveHandeling) {
        final BRPActie actie;
        if (zijnAlleHoofdPersonenVerwerkbaar()) {
            actie = maakActieEntiteit(administratieveHandeling);
            bepaalTeVervallenVoorkomen().laatVervallen(actie, BmrAttribuut.getWaardeOfNull(getNadereAanduidingVerval()));
            verwijderIstGegevens();
        } else {
            actie = null;
        }
        return actie;
    }

    @Override
    @Bedrijfsregel(Regel.R1845)
    @Bedrijfsregel(Regel.R2334)
    @Bedrijfsregel(Regel.R2432)
    @Bedrijfsregel(Regel.R2465)
    protected final List<MeldingElement> valideerSpecifiekeInhoud() {
        final List<MeldingElement> results = new ArrayList<>(valideerCorrectieInhoud());
        final BmrGroep ongeldigAangewezenObjectOfVoorkomen = getOngeldigAangewezenObjectOfVoorkomen();
        if (ongeldigAangewezenObjectOfVoorkomen != null) {
            results.add(MeldingElement.getInstance(Regel.R1845, ongeldigAangewezenObjectOfVoorkomen));
        }
        if (teVervallenVoorkomenReedsVervallen()) {
            results.add(MeldingElement.getInstance(Regel.R2334, this));
        }
        if (heeftOngeldigeRechtsgrondVoorNadereAanduidingVerval(getNadereAanduidingVerval())) {
            results.add(MeldingElement.getInstance(Regel.R2432, this));
        }
        if (moetenIstGegevensVerwijderdWorden()) {
            results.add(MeldingElement.getInstance(Regel.R2465, getIstIngang()));
        }

        return results;
    }

    /**
     * Geeft de {@link BmrEntiteit} terug welke als ingang gebruikt wordt om bij de IST-gegevens te komen.
     * @return een {@link BmrEntiteit}
     */
    abstract BmrEntiteit getIstIngang();

    private boolean teVervallenVoorkomenReedsVervallen() {
        final T teVervallenVoorkomen = bepaalTeVervallenVoorkomen();
        return teVervallenVoorkomen != null && teVervallenVoorkomen.getDatumTijdVerval() != null;
    }

    /**
     * Valideert de specifieke inhoud van de implementerende correctie acties.
     * @return lijst van meldingen of een lege lijst als er geen meldingen zijn
     */
    protected abstract List<MeldingElement> valideerCorrectieInhoud();

    /**
     * Zoek aan de hand van de objectsleutels en voorkomen sleutels het voorkomen dat voor deze actie moet komen te vervallen.
     * @return het voorkomen dat moet komen te vervallen
     */
    protected abstract T bepaalTeVervallenVoorkomen();

    /**
     * Deze methode controleert of de object hierarchy i.c.m. de voorkomen sleutel ook een daadwerkelijk
     * bestaande hierarchy is in de database. Dit kan doorgaans worden bereikt door het root object te verkrijgen
     * uit de ObjectSleutelIndex om vervolgens binnen dit object te zoeken met de overige objectsleutels / voorkomens sleutels
     * naar het correcte voorkomen.
     * @return de {@link BmrGroep} die een onjuiste objectsleutel of voorkomensleutel bevat, of null als dit niet het geval is
     */
    @Bedrijfsregel(Regel.R1845)
    protected abstract BmrGroep getOngeldigAangewezenObjectOfVoorkomen();

    /**
     * Implementaties van dit soort actie moeten eventueel de IST gegevens bijwerken n.a.v. het vervallen van relaties.
     */
    protected abstract void verwijderIstGegevens();

    /**
     * Bepaalt of er IST gegevens verwijderd worden door deze actie.
     * @return true als deze actie IST gegevens verwijderd, anders false
     */
    protected abstract boolean moetenIstGegevensVerwijderdWorden();
}

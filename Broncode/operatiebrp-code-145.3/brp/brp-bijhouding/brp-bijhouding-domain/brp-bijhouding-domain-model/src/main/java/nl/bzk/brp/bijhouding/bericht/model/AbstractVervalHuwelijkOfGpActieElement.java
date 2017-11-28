/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;

/**
 * De actie voor het laten vervallen van een huwelijk of geregistreerd partnerschap in de bijhouding.
 */
public abstract class AbstractVervalHuwelijkOfGpActieElement extends AbstractVervalActieElement {

    private final CharacterElement nadereAanduidingVerval;

    /**
     * Maakt een AbstractVervalHuwelijkOfGpActieElement object.
     * @param basisAttribuutGroep de basis attribuutgroep
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     * @param datumEindeGeldigheid datum einde geldigheid
     * @param bronReferenties bron referenties
     * @param nadereAanduidingVerval nadere aanduiding verval
     */
    public AbstractVervalHuwelijkOfGpActieElement(final Map<String, String> basisAttribuutGroep,
                                                  final DatumElement datumAanvangGeldigheid,
                                                  final DatumElement datumEindeGeldigheid,
                                                  final List<BronReferentieElement> bronReferenties,
                                                  final CharacterElement nadereAanduidingVerval) {
        super(basisAttribuutGroep, datumAanvangGeldigheid, datumEindeGeldigheid, bronReferenties);
        this.nadereAanduidingVerval = nadereAanduidingVerval;
    }

    @Override
    public final List<BijhoudingPersoon> getHoofdPersonen() {
        return getRelatieElement().getRelatieEntiteit().getHoofdPersonen(getVerzoekBericht());
    }

    @Override
    public final List<PersoonElement> getPersoonElementen() {
        return getRelatieElement().getPersoonElementen();
    }

    @Override
    public final DatumElement getPeilDatum() {
        return getVerzoekBericht().getDatumOntvangst();
    }

    @Override
    protected final Set<FormeleHistorie> bepaalTeVervallenVoorkomens() {
        final Set<FormeleHistorie> results = new LinkedHashSet<>();
        final BijhoudingRelatie relatieEntiteit = getRelatieElement().getRelatieEntiteit();
        //groep relatie.standaard
        results.add(FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(relatieEntiteit.getRelatieHistorieSet()));
        for (final Betrokkenheid betrokkenheid : relatieEntiteit.getBetrokkenheidSet()) {
            //groep betrokkenheid.identiteit
            results.add(FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(betrokkenheid.getBetrokkenheidHistorieSet()));
            if (betrokkenheid.getPersoon() != null && SoortPersoon.PSEUDO_PERSOON.equals(betrokkenheid.getPersoon().getSoortPersoon())) {
                //groep Persoon.Identificatienummers
                results.add(FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(betrokkenheid.getPersoon().getPersoonIDHistorieSet()));
                //groep Persoon.Geboorte
                results.add(FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(betrokkenheid.getPersoon().getPersoonGeboorteHistorieSet()));
                //groep Persoon.Geslachtsaanduiding
                results.add(
                        FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(betrokkenheid.getPersoon().getPersoonGeslachtsaanduidingHistorieSet()));
                //groep Persoon.Samengesteldenaam
                results.add(
                        FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(betrokkenheid.getPersoon().getPersoonSamengesteldeNaamHistorieSet()));
            }
        }
        // als er geen actueel voorkomen gevonden wordt, wordt er null toegevoegd, deze moeten nog worden opgeruimd
        results.remove(null);
        return results;
    }

    @Override
    public final CharacterElement getNadereAanduidingVerval() {
        return nadereAanduidingVerval;
    }

    @Override
    protected final List<MeldingElement> valideerSpecifiekeInhoud() {
        final List<MeldingElement> results = new ArrayList<>();
        if (!getRelatieElement().getRelatieEntiteit().getStapels().isEmpty()) {
            results.add(MeldingElement.getInstance(Regel.R2465, getRelatieElement()));
        }
        if (heeftOngeldigeRechtsgrondVoorNadereAanduidingVerval(getNadereAanduidingVerval())) {
            results.add(MeldingElement.getInstance(Regel.R2432, this));
        }
        return results;
    }

    @Override
    protected void verwijderIstGegevens() {
        for (final Stapel stapel : getRelatieElement().getRelatieEntiteit().getStapels()) {
            stapel.getPersoon().removeStapel(stapel);
            getRelatieElement().getRelatieEntiteit().removeStapel(stapel);
        }
    }

    /**
     * Geeft de relatie element van deze actie.
     * @return relatie element
     */
    protected abstract RelatieElement getRelatieElement();
}

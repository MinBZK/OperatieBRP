/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.bijhouding;

import nl.bzk.brp.bijhouding.business.regels.AbstractAfleidingsregel;
import nl.bzk.brp.bijhouding.business.regels.AfleidingResultaat;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Bijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.BijhoudingsaardAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaardAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortMigratie;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.ActieBronModel;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonBijhoudingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonMigratieModel;

/**
 * VR00015b: Afgeleide registratie Bijhouding door Emigratie.
 *
 * Als voor een Persoon een actuele groep Migratie wordt geregistreerd van de Soort "Emigratie", terwijl er een actuele
 * Bijhouding staat geregistreerd met Bijhoudingsaard="Ingezetene", dan wordt voor de Persoon de volgende Bijhouding
 * afgeleid geregistreerd:
 *
 * Bijhoudingsaard := "Niet ingezetene"
 *
 *  NadereBijhoudingsaard :=
 *  "Vertrokken onbekend waarheen" als Migratie.Land = Null, anders
 *  "Ministerieel besluit" als de inhoud van Migratie wordt verantwoord door een MinisterieelBesluit, anders
 *  "Emigratie"
 *
 * Bijhoudingspartij := "Minister"
 */
public class BijhoudingAfleidingDoorEmigratie extends AbstractAfleidingsregel<PersoonHisVolledig> {

    /**
     * Constructor.
     *
     * @param model het model
     * @param actie de actie
     */
    public BijhoudingAfleidingDoorEmigratie(final PersoonHisVolledig model,
                                            final ActieModel actie)
    {
        super(model, actie);
    }

    @Override
    public final Regel getRegel() {
        return Regel.VR00015b;
    }

    @Override
    public AfleidingResultaat leidAf() {
        final PersoonHisVolledig persoonHisVolledig = getModel();
        final HisPersoonBijhoudingModel actueleBijhouding =
                persoonHisVolledig.getPersoonBijhoudingHistorie().getActueleRecord();
        final HisPersoonMigratieModel actueleMigratie =
                persoonHisVolledig.getPersoonMigratieHistorie().getActueleRecord();

        // Als voor een Persoon een actuele groep Migratie wordt geregistreerd van de Soort "Emigratie", terwijl er een
        // actuele Bijhouding staat geregistreerd met Bijhoudingsaard="Ingezetene",
        if (actueleBijhouding.getBijhoudingsaard() != null
                && Bijhoudingsaard.INGEZETENE == actueleBijhouding.getBijhoudingsaard().getWaarde()
                && actueleMigratie.getSoortMigratie() != null
                && SoortMigratie.EMIGRATIE == actueleMigratie.getSoortMigratie().getWaarde())
        {
            final Partij minister = getReferentieDataRepository().vindPartijOpCode(PartijCodeAttribuut.MINISTER);
            NadereBijhoudingsaard nadereBijhoudingsaard;

            if (actueleMigratie.getLandGebiedMigratie() == null) {
                nadereBijhoudingsaard = NadereBijhoudingsaard.VERTROKKEN_ONBEKEND_WAARHEEN;
            } else if (actieWordtVerantwoordDoorMinisterieelBesluit()) {
                nadereBijhoudingsaard = NadereBijhoudingsaard.MINISTERIEEL_BESLUIT;
            } else {
                nadereBijhoudingsaard = NadereBijhoudingsaard.EMIGRATIE;
            }

            final HisPersoonBijhoudingModel afgeleideBijhoudingGroep =
                    new HisPersoonBijhoudingModel(persoonHisVolledig,
                                                  new PartijAttribuut(minister),
                                                  new BijhoudingsaardAttribuut(Bijhoudingsaard.NIET_INGEZETENE),
                                                  new NadereBijhoudingsaardAttribuut(nadereBijhoudingsaard),
                                                  actueleBijhouding.getIndicatieOnverwerktDocumentAanwezig(),
                                                  getActie(),
                                                  getActie());
            persoonHisVolledig.getPersoonBijhoudingHistorie().voegToe(afgeleideBijhoudingGroep);
        }
        return GEEN_VERDERE_AFLEIDINGEN;
    }

    /**
     * Controleert of de actie verantwoord een ministerieel besluit betreft.
     * @return true indien ministerieel besluit anders false
     */
    private boolean actieWordtVerantwoordDoorMinisterieelBesluit() {
        boolean resultaat = false;
        if (getActie().getBronnen() != null) {
            for (ActieBronModel actieBron : getActie().getBronnen()) {
                // Bron kan een rechtsgrond zijn!
                if (actieBron.getDocument() != null
                        && actieBron.getDocument().getSoort().getWaarde().getNaam()
                                    .equals(NaamEnumeratiewaardeAttribuut.DOCUMENT_NAAM_MINISTERIEEL_BESLUIT))
                {
                    resultaat = true;
                }
            }
        }
        return resultaat;
    }

}

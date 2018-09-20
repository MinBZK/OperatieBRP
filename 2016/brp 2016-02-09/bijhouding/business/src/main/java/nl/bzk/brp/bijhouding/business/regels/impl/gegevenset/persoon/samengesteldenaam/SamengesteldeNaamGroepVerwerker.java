/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.samengesteldenaam;

import nl.bzk.brp.bijhouding.business.regels.AbstractVerwerkingsregel;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonSamengesteldeNaamGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonVoornaamHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonSamengesteldeNaamModel;

/**
 * Verwerkingsregel van de samengestelde naam groep.
 * Verwerkingsregel VR00003
 */
public class SamengesteldeNaamGroepVerwerker extends AbstractVerwerkingsregel<PersoonBericht, PersoonHisVolledigImpl> {

    /**
     * Forwarding constructor.
     *
     * @param bericht het bericht object
     * @param model het model object
     * @param actie de actie
     */
    public SamengesteldeNaamGroepVerwerker(final PersoonBericht bericht, final PersoonHisVolledigImpl model,
                                           final ActieModel actie)
    {
        super(bericht, model, actie);
    }

    @Override
    public final Regel getRegel() {
        return Regel.VR00003;
    }

    @Override
    public final void neemBerichtDataOverInModel() {
        final boolean isIngeschrevene = getModel().getSoort().getWaarde() == SoortPersoon.INGESCHREVENE;

        final JaNeeAttribuut indicatieAlgoritmischAfgeleid = bepaalIndicatieAlgoritmischAfgeleid(isIngeschrevene);

        final PersoonSamengesteldeNaamGroepBericht samengesteldeNaamBericht = getBericht().getSamengesteldeNaam();

        final JaNeeAttribuut indicatieNamenReeks = bepaalIndicatieNamenReeks(samengesteldeNaamBericht);

        final boolean nieuweGroepAanmaken = moetNieuweGroepAanmaken(indicatieAlgoritmischAfgeleid.getWaarde(),
            samengesteldeNaamBericht, getModel().getPersoonSamengesteldeNaamHistorie().getActueleRecord());

        // ROMEO-639
        if (nieuweGroepAanmaken) {
            //Rest van de gegevens overnemen van bericht, die kunnen mogelijk null zijn!
            //De samengestelde naam afleiding zal hier rekening mee houden.
            final HisPersoonSamengesteldeNaamModel model = new HisPersoonSamengesteldeNaamModel(
                    getModel(),
                    indicatieAlgoritmischAfgeleid,
                    indicatieNamenReeks,
                    getBericht().getSamengesteldeNaam().getPredicaat(),
                    getBericht().getSamengesteldeNaam().getVoornamen(),
                    getBericht().getSamengesteldeNaam().getAdellijkeTitel(),
                    getBericht().getSamengesteldeNaam().getVoorvoegsel(),
                    getBericht().getSamengesteldeNaam().getScheidingsteken(),
                    getBericht().getSamengesteldeNaam().getGeslachtsnaamstam(),
                    getActie(),
                    getActie());
            getModel().getPersoonSamengesteldeNaamHistorie().voegToe(model);
        }

        // Als de Namenreeks van een Ingeschrevene wordt gewijzigd in "Ja",
        // dan wordt elke Voornaam van de Ingeschrevene zonodig afgeleid beëindigd.
        if (isIngeschrevene && JaNeeAttribuut.JA.equals(indicatieNamenReeks)) {
            for (final PersoonVoornaamHisVolledigImpl voornaamHisVolledig : getModel().getVoornamen()) {
                voornaamHisVolledig.getPersoonVoornaamHistorie().beeindig(getActie(), getActie());
            }
        }
    }

    private JaNeeAttribuut bepaalIndicatieNamenReeks(final PersoonSamengesteldeNaamGroepBericht samengesteldeNaamBericht) {
        JaNeeAttribuut indicatieNamenReeks = JaNeeAttribuut.NEE;
        if (samengesteldeNaamBericht != null && samengesteldeNaamBericht.getIndicatieNamenreeks() != null) {
            indicatieNamenReeks = samengesteldeNaamBericht.getIndicatieNamenreeks();
        }
        return indicatieNamenReeks;
    }

    private JaNeeAttribuut bepaalIndicatieAlgoritmischAfgeleid(final boolean isIngeschrevene) {
        final JaNeeAttribuut indicatieAlgoritmischAfgeleid;
        if (isIngeschrevene) {
            indicatieAlgoritmischAfgeleid = JaNeeAttribuut.JA;
        } else {
            indicatieAlgoritmischAfgeleid = JaNeeAttribuut.NEE;
        }
        return indicatieAlgoritmischAfgeleid;
    }

    @Override
    public final void verzamelAfleidingsregels() {
        this.voegAfleidingsregelToe(new SamengesteldeNaamAfleiding(getModel(), getActie()));
    }

    /**
     * Checkt of er een nieuwe groep aangemaakt dient te worden. (ROMEO-639)
     * Dat is standaard wel het geval, behalve als de indicatie namenreeks op 'N' staat in het bericht
     * en dat ook het enige is wat in het bericht wordt meegegeven (te checken door indicatie algoritmisch
     * afgeleid). Tevens moet de indicatie namenreeks in de database al op 'N' staan.
     * Dan valt er dus niks nieuws vast te leggen. Het is zelfs een probleem om wel een nieuwe groep aan te maken,
     * want dan vindt er geen afleiding plaats en krijg je dus een
     * 'lege' afleiding met alleen indicatie namenreeks erin.
     *
     * @param indicatieAlgoritmischAfgeleid indicatie algoritmisch afgeleid
     * @param bericht de samengestelde naam bericht groep
     * @param model de samengestelde naam model groep
     * @return wel of niet een nieuwe groep aanmaken
     */
    private boolean moetNieuweGroepAanmaken(final boolean indicatieAlgoritmischAfgeleid,
            final PersoonSamengesteldeNaamGroepBericht bericht,
            final HisPersoonSamengesteldeNaamModel model)
    {
        return !(indicatieAlgoritmischAfgeleid
            && berichtNamenreeksNee(bericht)
            && modelNamenreeksNee(model));
    }

    private boolean berichtNamenreeksNee(final PersoonSamengesteldeNaamGroepBericht bericht) {
        return bericht.getIndicatieNamenreeks() != null
            && !bericht.getIndicatieNamenreeks().getWaarde();
    }

    private boolean modelNamenreeksNee(final HisPersoonSamengesteldeNaamModel model) {
        return model != null
            && model.getIndicatieNamenreeks() != null
            && !model.getIndicatieNamenreeks().getWaarde();
    }

}

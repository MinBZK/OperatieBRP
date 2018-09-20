/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.voornaam;

import nl.bzk.brp.bijhouding.business.regels.AbstractVerwerkingsregel;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.samengesteldenaam.SamengesteldeNaamAfleiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVoornaamBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonVoornaamHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVoornaamModel;

/**
 * Verwerker voor alle voornamen van een persoon tegelijk.
 * We wijken hier bewust af van het patroon dat elke een 1-op-N
 * associatie zijn eigen verwerker instantie krijgt, omdat de voornamen van een persoon
 * altijd samen afgehandeld worden. Tevens moeten eventueel 'overgebleven' voornamen
 * beëindigd worden, voordat dat samengestelde naam afleiding plaatsvindt. De beste oplossing is
 * om dat meteen mee te nemen in deze verwerker.
 *
 * "Je zou de voornamen het beste kunnen zien als een array direct op persoon."
 *
 * <p>
 *     <b>Let op:</b> deze methode voegt eventueel voornamen toe aan de <code>persoonHisVolledig</code> voor elke
 *     wel in het bericht aanwezige voornaam die nog niet in de <code>persoonHisVolledig</code> aanwezig is.
 * </p>
 *
 * VR00001 en VR00001a.
 */
public class VoornamenVerwerker extends AbstractVerwerkingsregel<PersoonBericht, PersoonHisVolledigImpl> {

    /**
     * Forwarding constructor.
     *
     * @param bericht het bericht object
     * @param model het model object
     * @param actie de actie
     */
    public VoornamenVerwerker(final PersoonBericht bericht, final PersoonHisVolledigImpl model, final ActieModel actie) {
        super(bericht, model, actie);
    }

    @Override
    public final Regel getRegel() {
        // Tevens zit VR00001a in deze klasse verwerkt.
        return Regel.VR00001;
    }

    @Override
    public final void neemBerichtDataOverInModel() {
        verwerkNieuweVoornamen();
        beeindigOverigeVoornamen();
    }

    /**
     * Verwerk alle nieuwe voornamen in de his volledig.
     */
    private void verwerkNieuweVoornamen() {
        final PersoonHisVolledigImpl persoonHisVolledig = getModel();
        for (final PersoonVoornaamBericht voornaamBericht : getBericht().getVoornamen()) {
            PersoonVoornaamHisVolledigImpl voornaamModel = null;
            for (final PersoonVoornaamHisVolledigImpl voornaamHisVolledig : persoonHisVolledig.getVoornamen()) {
                if (voornaamBericht.getVolgnummer().equals(voornaamHisVolledig.getVolgnummer())) {
                    voornaamModel = voornaamHisVolledig;
                    break;
                }
            }

            // Als er nog geen voornaam in bij deze persoon bekend is met dit volgnummer,
            // maak daar dan een nieuwe persoon voornaam his volledig voor aan.
            if (voornaamModel == null) {
                voornaamModel = new PersoonVoornaamHisVolledigImpl(persoonHisVolledig, voornaamBericht.getVolgnummer());
                persoonHisVolledig.getVoornamen().add(voornaamModel);
            }

            verwerkNieuweVoornaam(voornaamBericht, voornaamModel);
        }
    }

    /**
     * Verwerk een nieuwe voornaam in de his volledig.
     *
     * @param voornaamBericht het voornaam bericht
     * @param voornaamModel het voornaam model
     */
    private void verwerkNieuweVoornaam(final PersoonVoornaamBericht voornaamBericht,
            final PersoonVoornaamHisVolledigImpl voornaamModel)
    {
        final HisPersoonVoornaamModel hisVoornaam = new HisPersoonVoornaamModel(voornaamModel,
                voornaamBericht.getStandaard(), voornaamBericht.getStandaard(), getActie());
        voornaamModel.getPersoonVoornaamHistorie().voegToe(hisVoornaam);
    }

    /**
     * Beëindig alle voornamen die niet in het bericht voorkomen, maar al wel op deze persoon
     * geregistreerd staan. Dit, aangezien in een bericht altijd alle voornamen van een persoon tegelijk
     * geregistreerd moeten worden.
     *
     * Dit is de implementatie van afleidingsregel VR00001a.
     */
    private void beeindigOverigeVoornamen() {
        for (final PersoonVoornaamHisVolledigImpl voornaamHisVolledig : getModel().getVoornamen()) {
            boolean gevondenInBericht = false;
            for (final PersoonVoornaamBericht voornaamBericht : getBericht().getVoornamen()) {
                if (voornaamBericht.getVolgnummer().equals(voornaamHisVolledig.getVolgnummer())) {
                    gevondenInBericht = true;
                    break;
                }
            }

            // Het volgnummer van de voornaam his volledig is niet gevonden in het bericht
            // en moet dus beeindigd worden voor de geldigheidsperiode van de actie.
            if (!gevondenInBericht) {
                voornaamHisVolledig.getPersoonVoornaamHistorie().beeindig(getActie(), getActie());
            }
        }
    }

    @Override
    public final void verzamelAfleidingsregels() {
        this.voegAfleidingsregelToe(new SamengesteldeNaamAfleiding(getModel(), getActie()));
    }

}

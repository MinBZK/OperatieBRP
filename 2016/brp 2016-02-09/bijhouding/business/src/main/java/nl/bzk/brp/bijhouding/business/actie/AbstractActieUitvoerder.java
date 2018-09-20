/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.actie;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.Afleidingsregel;
import nl.bzk.brp.bijhouding.business.regels.Verwerkingsregel;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.HisVolledigRootObject;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.basis.BerichtRootObject;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import org.springframework.beans.factory.annotation.Configurable;


/**
 * Abstracte implementatie van een actie uitvoerder die regelt dat de actie eerst wordt gevalideerd en daarna
 * uitgevoerd. Implementerende klasses moeten de implementatie voor de actie verwerking implementeren.
 *
 * @param <B> het type bericht root object
 * @param <M> het type his volledig root object
 */
@Configurable
public abstract class AbstractActieUitvoerder<B extends BerichtRootObject, M extends HisVolledigRootObject> implements
        ActieUitvoerder<ActieBericht>
{

    private static final String          MODEL_ROOTOBJECT_KON_NIET_WORDEN_GEVONDEN =
            "Model rootobject kon niet worden gevonden door de uitvoerder, controleer de datum aanvang geldigheid van de acties in het bericht!";

    /** De logger voor deze klasse. */
    private static final Logger          LOG                                       = LoggerFactory.getLogger();

    private BijhoudingBerichtContext     context;
    private ActieBericht                 actieBericht;
    private ActieModel                   actieModel;
    private B                            berichtRootObject;
    private M                            modelRootObject;
    private final List<Verwerkingsregel> verwerkingsregels;
    private final List<Afleidingsregel>  afleidingsregels;

    /**
     * Constructor, initialiseer collecties.
     */
    public AbstractActieUitvoerder() {
        this.verwerkingsregels = new ArrayList<>();
        this.afleidingsregels = new ArrayList<>();
    }

    /**
     * Voer de actie uit. Dat wil zeggen:
     * - Valideer de actie
     * - Haal het root object op of maak het nieuwe root object aan
     * - Roep de verwerkingsregels aan
     * - Verzamel alle afleidingsregels en geef die terug
     *
     * @return een lijst met afleidingsregels die nog uitgevoerd moeten worden
     */
    // We gaan er van uit dat in de uitvoerder het juiste soort root object is geconfigureerd.
    @Override
    @SuppressWarnings("unchecked")
    public final List<Afleidingsregel> voerActieUit() {
        this.berichtRootObject = (B) this.actieBericht.getRootObject();
        this.modelRootObject = this.bepaalRootObjectHisVolledig();

        if (modelRootObject == null) {
            LOG.error(MODEL_ROOTOBJECT_KON_NIET_WORDEN_GEVONDEN);
            throw new IllegalArgumentException(MODEL_ROOTOBJECT_KON_NIET_WORDEN_GEVONDEN);
        }

        this.verzamelVerwerkingsregels();
        for (final Verwerkingsregel verwerkingsregel : this.verwerkingsregels) {
            // Alle verwerkingsregels krijgen de kans het bericht te verrijken, indien nodig.
            verwerkingsregel.verrijkBericht();
            // Laat alle verwerkingsregels de bericht data overnemen, zodat het (al dan niet nieuwe)
            // his volledig root object compleet is qua bericht informatie.
            verwerkingsregel.neemBerichtDataOverInModel();
            // Verzamel van alle verwerkingsregels hun afleidingsregels.
            verwerkingsregel.verzamelAfleidingsregels();
            this.afleidingsregels.addAll(verwerkingsregel.getAfleidingsregels());
        }
        // Verzamel ook de uitvoerder algemene afleidingsregels.
        this.verzamelAfleidingsregels();
        return this.afleidingsregels;
    }

    /**
     * Haal het root object voor deze uitvoerder op. Kan een bestaand root object zijn
     * (is uit de context te halen) of een nieuw aan te maken root object.
     *
     * @return het root object
     */
    protected M bepaalRootObjectHisVolledig() {
        final M rootObject;

        if (getActieBericht().getRootObject() instanceof PersoonBericht
            && (((PersoonBericht) getActieBericht().getRootObject()).getIdentificerendeSleutel() != null || getActieBericht()
                    .getRootObject().getReferentieID() != null))
        {
            // Bestaande persoon
            rootObject = this.zoekRootObjectHisVolledigInContext();
        } else if (getActieBericht().getRootObject().getObjectSleutel() != null
            || getActieBericht().getRootObject().getReferentieID() != null)
        {
            // Bestaand rootObject
            rootObject = this.zoekRootObjectHisVolledigInContext();
        } else {
            // Maak het nieuwe root object aan.
            rootObject = this.maakNieuwRootObjectHisVolledig();
            // Zorg er tevens voor dat het op de context bekend wordt gemaakt.
            final String communicatieId = getBerichtRootObject().getCommunicatieID();
            getContext().voegAangemaaktBijhoudingsRootObjectToe(communicatieId, rootObject);
        }
        return rootObject;
    }

    /**
     * Maak een nieuwe root object aan.
     *
     * @return het nieuwe root object model
     */
    protected M maakNieuwRootObjectHisVolledig() {
        // Default leeg, override bij nieuw root object
        return null;
    }

    /**
     * Verzamel alle verwerkingsregels voor deze uitvoerder. Een verwerkingsregel representeert een te verwerken groep.
     * In de implementatie van deze methode kunnen ook nieuwe object type his volledig objecten aangemaakt worden,
     * indien nodig. Bijvoorbeeld bij het aanmaken van nieuwe betrokkenheden op een al-dan-niet nieuwe relatie,
     * het registreren van een nieuw 1-op-N object bij een bestaand root object, etc.
     * Elke regel instantie dient toegevoegd te worden door een aanroep van voegVerwerkingsregelToe.
     */
    protected abstract void verzamelVerwerkingsregels();

    /**
     * Verzamelt alle afleidingsregel instanties die van toepassing zijn om uit te voeren
     * en niet al in één van de groepen verwerkers aangemaakt worden. Dit kunnen afleidingen zijn,
     * die alleen te vinden zijn door overkoepelende data te combineren.
     * Elke regel instantie dient toegevoegd te worden door een aanroep van voegAfleidingsregelToe.
     * <p/>
     * NB: Deze methode wordt aangeroepen nadat alle verwerkingsregels zijn uitgevoerd.
     */
    protected void verzamelAfleidingsregels() {
        // Default implementatie: geen, override indien wel van toepassing.
    }

    /**
     * Voeg een verwerkingsregel toe.
     *
     * @param verwerkingsregel de regel
     */
    protected final void voegVerwerkingsregelToe(final Verwerkingsregel verwerkingsregel) {
        this.verwerkingsregels.add(verwerkingsregel);
    }

    /**
     * Voeg meerdere verwerkingsregels toe.
     *
     * @param nieuwVerwerkingsregels de nieuwe regels
     */
    protected final void voegVerwerkingsregelsToe(final List<Verwerkingsregel> nieuwVerwerkingsregels) {
        this.verwerkingsregels.addAll(nieuwVerwerkingsregels);
    }

    /**
     * Voeg een afleidingsregel toe.
     *
     * @param afleidingsregel de regel
     */
    protected void voegAfleidingsregelToe(final Afleidingsregel afleidingsregel) {
        this.afleidingsregels.add(afleidingsregel);
    }

    /**
     * Zoek het root object in de context, zie aangeroepen methode voor details.
     *
     * @return het root object
     */
    // We gaan er van uit dat in de uitvoerder het juiste soort root object is geconfigureerd.
    @SuppressWarnings("unchecked")
    protected M zoekRootObjectHisVolledigInContext() {
        return (M) context.zoekHisVolledigRootObject(berichtRootObject);
    }

    /**
     * Bekijkt of deze uitvoerder bezig is met een van de meegegeven
     * soorten administratieve handeling.
     *
     * @param soortenAdmHand de soorten administratieve handeling
     * @return true of false
     */
    protected boolean isSoortAdmHand(final SoortAdministratieveHandeling... soortenAdmHand) {
        boolean isSoortAdmHand = false;
        for (final SoortAdministratieveHandeling soortAdmHand : soortenAdmHand) {
            isSoortAdmHand |= soortAdmHand == getSoortAdmHand();
        }
        return isSoortAdmHand;
    }

    /**
     * Geef het soort administratieve handeling terug voor deze uitvoerder.
     *
     * @return het soort adm. hand.
     */
    public SoortAdministratieveHandeling getSoortAdmHand() {
        return this.actieModel.getAdministratieveHandeling().getSoort().getWaarde();
    }

    public BijhoudingBerichtContext getContext() {
        return context;
    }

    @Override
    public void setContext(final BijhoudingBerichtContext context) {
        this.context = context;
    }

    public ActieBericht getActieBericht() {
        return actieBericht;
    }

    @Override
    public void setActieBericht(final ActieBericht actieBericht) {
        this.actieBericht = actieBericht;
    }

    public ActieModel getActieModel() {
        return actieModel;
    }

    @Override
    public void setActieModel(final ActieModel actieModel) {
        this.actieModel = actieModel;
    }

    public B getBerichtRootObject() {
        return berichtRootObject;
    }

    public M getModelRootObject() {
        return modelRootObject;
    }

}

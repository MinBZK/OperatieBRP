/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.bericht.request;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import nl.bzk.brp.business.regels.VoorBerichtRegel;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.basis.CommunicatieIdMap;
import nl.bzk.brp.model.bericht.ber.AdministratieveHandelingBronBericht;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;
import nl.bzk.brp.model.bericht.kern.ActieBronBericht;
import nl.bzk.brp.model.bericht.kern.GedeblokkeerdeMeldingBericht;


/**
 * Dit is een business regel die geldt voor ELK bericht, gevalideerd moet worden dat het object waar hij naar toe
 * refereert van dezelfde entiteit type is.
 * <p/>
 * Een referentieID moet naar een element in het request verwijzen met hetzelfde entiteittype Het is verzekerd dat we
 * hiervoor de attribuut "entiteittype" kunnen gebruiken.
 *
 * @brp.bedrijfsregel BRBY9906
 */
@Named("BRBY9906")
public class BRBY9906 implements VoorBerichtRegel<BerichtBericht> {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    public final Regel getRegel() {
        return Regel.BRBY9906;
    }

    /**
     * Dit is een business regel die geldt voor ELK bericht, gevalideerd moet worden dat het object waar hij naar toe
     * refereert van dezelfde entiteit type is.
     *
     * @param bericht het bericht waarvoor de bedrijfsregel moet worden uitgevoerd.
     * @return een lijst met eventuele meldingen indien het bericht niet voldoet aan de bedrijfsregel.
     */
    @Override
    public final List<BerichtIdentificeerbaar> voerRegelUit(final BerichtBericht bericht) {
        final List<BerichtIdentificeerbaar> objectenDieDeRegelOvertreden = new ArrayList<>();
        final CommunicatieIdMap communicatieIdMap = bericht.getCommunicatieIdMap();

        if (communicatieIdMap != null) {
            for (final List<BerichtIdentificeerbaar> identificeerbaarObjecten : communicatieIdMap.values()) {
                for (final BerichtIdentificeerbaar identificeerbaarObject : identificeerbaarObjecten) {
                    if (identificeerbaarObject instanceof BerichtEntiteit
                        && ((BerichtEntiteit) identificeerbaarObject).getReferentieID() != null)
                    {
                        objectenDieDeRegelOvertreden.addAll(controleerBronEntiteitTypeInLijst(
                                (BerichtEntiteit) identificeerbaarObject,
                                communicatieIdMap.get(((BerichtEntiteit) identificeerbaarObject).getReferentieID())));
                    }
                }
            }
        }

        return objectenDieDeRegelOvertreden;
    }

    /**
     * Controleert of de lijst de entiteit type bevat van bronObject.
     *
     * @param bronObject object waarmee de entiteit bepaald moet worden.
     * @param doelObjecten lijst van objecten die van hetzelfde type zou moeten zijn als bronObject
     * @return lijst van communicatieId's van objecten in de lijst die niet van het zelfde entiteit type is als
     *         bronObject
     */
    private List<BerichtEntiteit> controleerBronEntiteitTypeInLijst(final BerichtEntiteit bronObject,
            final List<BerichtIdentificeerbaar> doelObjecten)
    {
        final List<BerichtEntiteit> objectOngelijkeEntiteitTypen = new ArrayList<>();

        if (doelObjecten != null) {
            for (final BerichtIdentificeerbaar target : doelObjecten) {
                if (null != target && !isBronGelijkAanDoelObject(bronObject, target)) {
                    LOGGER.error(String.format("Entiteit [%s]=[%s], Entiteit ref [%s]=[%s]",
                            bronObject.getReferentieID(), bronObject.getClass().getName(), target.getCommunicatieID(),
                            target.getClass().getName()));

                    objectOngelijkeEntiteitTypen.add(bronObject);
                }
            }
        } else {
            // Doe niks, een andere bedrijfsregel handelt dit scenario af; die zegt dat elke referentieID naar een
            // bestaande object moet wijzen.
            LOGGER.debug(String.format(
                    "Object met communicatieID %s verwijst naar niet bestaande objecten met communicatieID %s",
                    bronObject.getCommunicatieID(), bronObject.getReferentieID()));
        }

        return objectOngelijkeEntiteitTypen;
    }

    /**
     * Controleert of het opgegeven bronobject een gedeblokkeerd melding bericht is of dat de klasse van het bron
     * object een instantie is van het opgegeven doel object.
     *
     * @param bronObject het bron object.
     * @param doelObject het doel object.
     * @return of het bronobject gelijk is aan het doel object.
     */
    private boolean isBronGelijkAanDoelObject(final BerichtIdentificeerbaar bronObject,
            final BerichtIdentificeerbaar doelObject)
    {
        // Uitzondering bij meldingen, een melding mag wel verwijzen naar een object van een andere type.
        // Uitzondering bij bronnen: een actie bron mag wel verwijzen naar een administratieve handeling bron.
        return bronObject instanceof GedeblokkeerdeMeldingBericht || bronObject instanceof ActieBronBericht
                && doelObject instanceof AdministratieveHandelingBronBericht
                || bronObject.getClass().isInstance(doelObject);
    }
}

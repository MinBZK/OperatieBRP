/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.bericht;

import nl.bzk.brp.business.bedrijfsregels.BerichtBedrijfsRegel;
import nl.bzk.brp.model.administratie.CommunicatieIdMap;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;
import nl.bzk.brp.model.logisch.ber.AdministratieveHandelingGedeblokkeerdeMelding;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Dit is een business regel die geldt voor ELK bericht, gevalideerd moet worden dat het object waar hij naar toe
 * refereert van dezelfde entiteit type is.
 *
 * Een referentieID moet naar een element in het request verwijzen met hetzelfde entiteittype
 * Het is verzekerd dat we hiervoor de attribuut "entiteittype" kunnen gebruiken.
 *
 * @brp.bedrijfsregel BRBY9906
 */
public class BRBY9906 implements BerichtBedrijfsRegel<BerichtBericht> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BRBY9906.class);

    //    @Override
    public String getCode() {
        return "BRBY9906";
    }

    /**
     * Dit is een business regel die geldt voor ELK bericht, gevalideerd moet worden dat het object waar hij naar toe
     * refereert van dezelfde entiteit type is.
     *
     * @param bericht het bericht waarvoor de bedrijfsregel moet worden uitgevoerd.
     * @return een lijst met eventuele meldingen indien het bericht niet voldoet aan de bedrijfsregel.
     */
//    @Override
    public List<Melding> executeer(final BerichtBericht bericht) {
        List<Identificeerbaar> objectOngelijkeEntiteitTypen =
                controlleerAlleObjectenInMap(bericht.getIdentificeerbaarObjectIndex());

        return maakMeldingen(objectOngelijkeEntiteitTypen);
    }

    private List<Melding> maakMeldingen(List<Identificeerbaar> foutieveObjecten) {
        List<Melding> meldingen = new ArrayList<Melding>();
        for (Identificeerbaar object : foutieveObjecten) {
            meldingen.add(new Melding(SoortMelding.FOUT, MeldingCode.BRBY9906,
                    MeldingCode.BRBY9906.getOmschrijving(),
                    object, "referentieID"));
        }
        return meldingen;
    }

    /**
     * Controlleer alle objecten in de map die naar elkaar verwijzen of ze van hetzelfde entiteit typen zijn.
     *
     * @param communicatieIdMap een map met alle identificeerbare entiteiten
     * @return lijst van referentie id's die verwijzen naar een entiteit van een andere type
     */
    private List<Identificeerbaar> controlleerAlleObjectenInMap(CommunicatieIdMap communicatieIdMap) {
        List<Identificeerbaar> objectOngelijkeEntiteitTypen = new ArrayList<Identificeerbaar>();

        for (List<Identificeerbaar> identificeerbaarObjecten : communicatieIdMap.values()) {
            for (Identificeerbaar identificeerbaarObject : identificeerbaarObjecten) {
                objectOngelijkeEntiteitTypen.addAll(
                        controlleerBronEntiteitTypeInLijst(identificeerbaarObject,
                                communicatieIdMap.get(
                                        identificeerbaarObject.getReferentieID())));
            }
        }

        return objectOngelijkeEntiteitTypen;
    }


    /**
     * Controleert of de lijst de entiteit type bevat van bronObject.
     *
     * @param bronObject   object waarmee de entiteit bepaald moet worden.
     * @param doelObjecten lijst van objecten die van hetzelfde type zou moeten zijn als bronObject
     * @return lijst van communicatieId's van objecten in de lijst die niet van het zelfde entiteit type is als bronObject
     */
    private List<Identificeerbaar> controlleerBronEntiteitTypeInLijst(Identificeerbaar bronObject,
                                                                      List<Identificeerbaar> doelObjecten)
    {
        List<Identificeerbaar> objectOngelijkeEntiteitTypen = new ArrayList<Identificeerbaar>();

        if (doelObjecten != null) {
            for (Identificeerbaar target : doelObjecten) {
                if (null != target) {
                    // OK, test nu of deze naar de correcte type verwijst
                    // null == null !!

                    if (!isBronGelijkAanDoelObject(bronObject, target)) {
                        LOGGER.error(String.format("Entiteit [%s]=[%s], Entiteit ref [%s]=[%s]",
                                bronObject.getReferentieID(),
                                bronObject.getClass().getName(), target.getCommunicatieID(),
                                target.getClass().getName()));

                        objectOngelijkeEntiteitTypen.add(bronObject);
                    }
                }
            }
        } else {
            // Do niks, een andere bedrijfsregel handelt deze scenario af, die zegt dat elke referentieID naar een
            // bestaande object moet wijzen.
            LOGGER.info("Object met communicatieID %s verwijst naar niet bestaande objecten met communicatieID %s",
                    bronObject.getCommunicatieID(), bronObject.getReferentieID());
        }

        return objectOngelijkeEntiteitTypen;
    }

    private boolean isBronGelijkAanDoelObject(final Identificeerbaar bronObject, final Identificeerbaar doelObject) {
        // Uitzondering bij meldingen, een melding mag wel verwijzen naar een object van een andere type.
        return bronObject instanceof AdministratieveHandelingGedeblokkeerdeMelding ||
                bronObject.getClass().isInstance(doelObject);
    }
}

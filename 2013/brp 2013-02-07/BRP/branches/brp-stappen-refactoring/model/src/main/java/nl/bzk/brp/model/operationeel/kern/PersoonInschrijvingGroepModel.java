/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Embeddable;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Versienummer;
import nl.bzk.brp.model.logisch.kern.PersoonInschrijvingGroep;
import nl.bzk.brp.model.operationeel.kern.basis.AbstractPersoonInschrijvingGroepModel;


/**
 * Deze verzameling van gegevens geeft weer wanneer de gegevens van een persoon in de BRP (voorheen GBA) zijn
 * ingeschreven, het moment van de laatste actualisering en of er eventuele identificatienummerwijzigingen zijn.
 * <p/>
 * 1. Vorm van historie: Formeel.
 * Motivatie & uitleg:
 * De groep inschrijving behoeft een aparte beschrijving. Zie hiervoor ook een overkoepelend memo over Volgend-Vorig
 * BSN
 * en Anummer.
 * De gegevens uit de groep bestaan ook in LO3.x; daar is in de betreffende categorie zowel een datum opneming
 * (=formeel
 * tijdsaspect) als een datum geldigheid (=materieel tijdsaspect) gevuld. Echter, in geval van gebruik van
 * vorig/volgend
 * Anummer/BSN, is de datum geldigheid altijd gelijk aan de datum opneming. (Of te wel: eigenlijk is de materiele
 * tijdslijn niet van toepassing voor deze groep van gegevens).
 * De groep Inschrijving bevat in LO BRP:
 * - technisch gegeven tbv synchronisatie (versienr). Materiele historie is niet van toepassing, althans formele
 * historie volstaat.
 * - gegevens over 'volgend en vorig persoon'. In het Logisch model vervallen de attributen vorig/volgend BSN en
 * Anummer, ten faveure van vorig/volgend persoon.
 * Het LO GBA 3.x voorziet niet in het samenvoegen van drie of meer persoonslijsten in een keer. (In twee keer kan:
 * stel
 * A, B en C moeten samenworden gevoegd tot een C; dit kan door A te laten verwijzen naar B, en B naar C. Of dit in de
 * praktijk is voorgekomen is schrijver dezes onbekend.)
 * Het lijkt erop (toetsing: use case persoon-samenvoegen!) dat formele historie voldoende is: de verwijzingen
 * gemigreerd uit LO 3.x kunnen erin, en na een samenvoeging wordt de 'oude' opgeschort en zal er dus (normaliter)
 * nooit
 * meer iets wijzigen, c.q. is er geen sprake van een benodigde materiele historie.
 * RvdP 12 jan 2012.
 * <p/>
 * <p/>
 * Generator: nl.bzk.brp.generatoren.java.OperationeelModelGenerator.
 * Metaregister versie: 1.1.8.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2012-11-27 12:02:51.
 * Gegenereerd op: Tue Nov 27 14:55:36 CET 2012.
 */
@Embeddable
public class PersoonInschrijvingGroepModel extends AbstractPersoonInschrijvingGroepModel implements
    PersoonInschrijvingGroep
{

    /** Standaard constructor (t.b.v. Hibernate/JPA). */
    protected PersoonInschrijvingGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param datumInschrijving datumInschrijving van Inschrijving.
     * @param versienummer versienummer van Inschrijving.
     * @param vorigePersoon vorigePersoon van Inschrijving.
     * @param volgendePersoon volgendePersoon van Inschrijving.
     */
    public PersoonInschrijvingGroepModel(final Datum datumInschrijving, final Versienummer versienummer,
        final PersoonModel vorigePersoon, final PersoonModel volgendePersoon)
    {
        super(datumInschrijving, versienummer, vorigePersoon, volgendePersoon);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonInschrijvingGroep te kopieren groep.
     */
    public PersoonInschrijvingGroepModel(final PersoonInschrijvingGroep persoonInschrijvingGroep) {
        super(persoonInschrijvingGroep);
    }

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.GegevenInOnderzoek;


/**
 * Gegevens waar onderzoek naar gedaan wordt/naar gedaan is.
 * <p/>
 * 1. Bij de naam '(Gegevens)Element' is de vraag welke elementen worden bedoeld: zijn dit de LOGISCHE elementen, of de representanten hiervan in de
 * database, de DATABASE OBJECTEN. Voor een aantal objecttypen is de hypothese waaronder gewerkt wordt dat het de DATABASE OBJECTEN zijn. Om deze duidelijk
 * te kunnen scheiden van (andere) Elementen, hebben deze een aparte naam gekregen: Databaseobject. In de verwijzing van het attribuut gebruiken we echter
 * nog de naam 'Element': een Databaseobject zou immers kunnen worden beschouwd als een specialisatie van Element. Alleen is die specialisatie in het model
 * niet uitgemodelleerd. RvdP 16 november 2011. 2. Bij conversie tussen GBA-V en BRP formaat, maakt men graag gebruik gemaakt van bepaalde standaard ORM
 * software voor het schrijven en lezen van objecten uit de database, in dit geval specifiek voor het vastleggen van (tegelijk) een inhoudelijk gegeven EN
 * het registreren van een indicatie dat dit inhoudelijk gegeven in onderzoek is. Vooralsnog is het gebruik van deze ORM mapping functionaliteit nog niet
 * voorzien in de reguliere bijhouding van de BRP. Besloten is om vooralsnog de "do instead" functionaliteit van Postgres te gebruiken. Hierbij ontstaat
 * dus in essentie een verschil tussen het "op basis van patronen gegenereerde Operationeel model", en het fysieke model zoals gegenereerd met de sql DDL.
 * Mocht in de toekomst blijken dat er meer afwijkingen ontstaan tussen operationeel model en fysiek model, dan kan deze keuze herzien worden. (Met als
 * mogelijke oplossingsrichtingen bijvoorbeeld het opnemen van deze extra structuur in het logisch model, of bijvoorbeeld het expliciet onderkennen van een
 * fysiek model dat NIET alleen op basis van patronen wordt gegenereerd uit het logische model. De hiertoe gegeneerde sql code heeft (o.a.) het volgende
 * commentaar (c.q. had deze op 11 april 2014): ---------------------------------- BEGIN -------------------------------------- -- Door het gebruikt van
 * standaard ORM software om het opslaan en lezen van objecten uit de database te realiseren, -- is er voor GegevenInOnderzoek behoefte aan een extra kolom
 * �TblGegeven' die een 1-op-1 mapping representeert -- tussen entiteit classen in de software en een waarde in de TblGegeven kolom in de database. -- Deze
 * kolom voegt geen extra informatie toe ten opzichte van de SrtGegeven kolom, en wordt daarom gerealiseerd -- als een �writeable view� gebaseerd op de
 * tabellen GegevenInOnderzoek en DbObject. ---------------------------------- BEGIN -------------------------------------- RvdP, 11 april 2014.
 */
public final class GegevenInOnderzoekBericht extends AbstractGegevenInOnderzoekBericht implements BrpObject, BerichtEntiteit,
    MetaIdentificeerbaar, GegevenInOnderzoek
{

}

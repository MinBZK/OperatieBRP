/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

/**
 * <p>Overview Maakbericht</p>
 * <p>
 * De module Maakbericht heeft als doel om het persoonsbericht samen te stellen.
 * Het persoonsbericht wordt uiteindelijk opgenomen in een XML bericht
 * (synchronisatiebericht / bevragingresponse / zoekvraag etc).
 * Maakbericht wordt gekenmerkt door een groot aantal bedrijfsregels welke
 * het uiteindelijke bericht inbeperken. Mag een attribuut/groep/object/verantwoording
 * wel/niet getoond worden etc. De regels verschillen per dienst, per
 * autorisatie en met welke tijdsbeeld er naar de persoonsgegevens gekeken wordt.
 * Daarnaast worden twee typen berichten onderkend, het volledigbericht en het mutatiebericht.
 * Ook hiervoor gelden weer specifieke uitzonderingen.
 * <p>
 * Maakbericht is in essentie een serie van stappen (chain-of-responsibility),
 * waarbij elke vervolgstap voortborduurt op de bepalingen die eerder gedaan zijn. De volgorde
 * van stappen is daarom belangrijk. Op een aantal detailstappen na is de volgorde vast,
 * De stappen zijn implementaties van {@link nl.bzk.brp.service.maakbericht.filterstappen.MaakBerichtStap} en
 * zijn gedefinieerd in een stappenplan {@link nl.bzk.brp.service.maakbericht.filterstappen.StappenlijstFactoryBean}
 * Voor een aantal diensten bestaat specifieke stappen. Deze stappen maken altijd deel uit
 * van het stappenplan, ook als het niet relevant is voor een gegeven dienst. De stap implementatie is zelf verantwoordelijk
 * voor het wel of niet uitvoeren. Veel stappen bevatten zogenoemde short-circuits om alleen uitgevoerd
 * te worden indien relevant.
 * <p>
 * Voor elke dienst is er een specieke ingang tot de maakbericht module. De ingangen staan in de verschillende dienst
 * modules.
 * Deze factories zijn als het ware een facade voor de maakbericht module.
 * Maakbericht service wordt aangeroepen met een parameter object
 * {@link nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtParameters MaakBerichtParameters}.
 * Met deze parameters wordt de {@link nl.bzk.brp.service.maakbericht.VerwerkPersoonBerichtFactory BerichtgegevensFactory}
 * aangeroepen. Dit voert de stappen uit en retourneert uiteindelijk een
 * {@link nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens Berichtgegevens}
 * object. {@code nl.bzk.brp.domain.berichtmodel.Berichtgegevens} bevat het
 * resultaat van alle bepalingen die gedaan zijn en vormt
 * weer input voor de
 * {@link nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens.Builder Berichtbuilder} welke het uiteindelijke bericht construeert.
 **/
package nl.bzk.brp.service.maakbericht.filterstappen;

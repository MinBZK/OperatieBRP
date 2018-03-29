/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.opschoner.dao;

import java.sql.Timestamp;
import java.util.List;

/**
 * Interface klasse voor Jbpm DAO.
 */
public interface JbpmDao {

    /**
     * Selecteert de subprocessen van het meegegeven proces.
     * @param procesId Het proces waarvan we de subprocessen willen.
     * @return De lijst met de id's van de subprocessen.
     */
    List<Long> selecteerSubProcessenVoorProces(Long procesId);

    /**
     * Haalt voor een proces de einddatum van het proces op.
     * @param procesId Het id van het proces waarvan we de einddatum willen.
     * @return De einddatum van het opgegeven proces.
     */
    Timestamp haalEinddatumProcesOp(Long procesId);

    /**
     * Selecteer de task instances van een proces.
     * @param procesId Het Id van het proces.
     * @return Lijst met task instances van het meegegeven proces.
     */
    List<Long> bepaalTaskInstancesVoorProces(Long procesId);

    /**
     * Verwijder de task instances voor de meegegeven tokens.
     * @param procesId Het id van het proces waarvoor we de task instances verwijderen.
     */
    void verwijderTaskInstancesVoorProces(Long procesId);

    /**
     * Selecteer de variable instances voor de meegegeven tokens.
     * @param tokens Lijst van tokens waarvoor we de variable instances bepalen.
     * @return Lijst met variable instances voor het meegegeven token.
     */
    List<Long> bepaalVariableInstancesVoorTokens(List<Long> tokens);

    /**
     * Verwijdert de variable instances voor de meegegeven tokens.
     * @param tokens Lijst van tokens waarvoor we de variable instances verwijderen.
     */
    void verwijderVariableInstancesVoorTokens(List<Long> tokens);

    /**
     * Verwijder de proces instantie van het proces.
     * @param procesId Het Id van het te verwijderen proces.
     */
    void verwijderProcessInstancesVoorProces(Long procesId);

    /**
     * Selecteer de byte arrays behorend bij de variable instances.
     * @param variableInstances Lijst van ids van de variable instances.
     * @return Lijst met de id's van de byte arrays.
     */
    List<Long> bepaalByteArraysVoorVariableInstances(List<Long> variableInstances);

    /**
     * Verwijderd de byte arrays met de opgegeven id's.
     * @param byteArrays Lijst met id's van te verwijderen byte arrays.
     */
    void verwijderByteArrays(List<Long> byteArrays);

    /**
     * Verwijdert de referenties van log naar log voor de meegegeven tokens.
     * @param tokens De ids van de tokens waarvoor we de referenties willen verwijderen.
     */
    void elimineerReferentieLogNaarLogVoorTokens(List<Long> tokens);

    /**
     * Verwijdert de referenties van token naar token voor de meegegeven tokens.
     * @param tokens De ids van de tokens waarvoor we de referenties willen verwijderen.
     */
    void elimineerReferentieTokenNaarTokenVoorTokens(List<Long> tokens);

    /**
     * Verwijdert de logs voor de meegegeven tokens.
     * @param tokens De tokens waarvoor we de logs verwijderen.
     */
    void verwijderLogsVoorTokens(List<Long> tokens);

    /**
     * Verwijdert de jobs voor het meegegeven proces.
     * @param procesId Het id van het proces waarvoor we de jobs willen opruimen.
     */
    void verwijderJobsVoorProces(Long procesId);

    /**
     * Verwijdert de module instances voor een proces.
     * @param procesId Het id van het proces waarvoor de module instances worden verwijderd.
     */
    void verwijderModuleInstancesVoorProces(Long procesId);

    /**
     * Selecteer de tokens van een proces.
     * @param procesId Het Id van het proces.
     * @return Lijst met tokens van het meegegeven proces.
     */
    List<Long> bepaalTokensVoorProces(Long procesId);

    /**
     * Verwijder de tokens van een proces.
     * @param tokens De te verwijderen tokens.
     */
    void verwijderTokens(List<Long> tokens);

    /**
     * Haalt op basis van het token (superprocesstoken) de ids van de subprocessen op.
     * @param token Het token waarvoor we de subprocessen ophalen.
     * @return De ids van de processen.
     */
    List<Long> selecteerProcesInstantiesOpBasisVanSuperProcessToken(Long token);

    /**
     * Verwijder de referenties van tokens van een proces.
     * @param tokens De te verwijderen refereneties van tokens.
     */
    void elimineerReferentieTokenNaarProcesInstantie(List<Long> tokens);

    /**
     * Verwijdert de token variable map voor een token.
     * @param tokens De tokens waarvoor de token variable map worden verwijderd.
     */
    void verwijderTokenVariableMapVoorToken(List<Long> tokens);

    /**
     * Verwijder de byte blocks met de meegegeven byte array ids.
     * @param byteArrays Lijst met id's van te verwijderen byte arrays.
     */
    void verwijderByteBlocks(List<Long> byteArrays);

    /**
     * Verwijdert de comments voor de meegegeven tokens.
     * @param tokens De lijst met tokens waarvoor we de comments verwijderen.
     */
    void verwijderCommentsVoorTokens(List<Long> tokens);

    /**
     * Verwijdert de swimlane instances voor de meegegeven task instances.
     * @param taskInstances De id's van de task instances waarvoor we de swimlane instances verwijderen.
     */
    void verwijderSwimlaneInstancesVoorTaskInstances(List<Long> taskInstances);

    /**
     * Verwijdert de task actor pools voor de meegegeven task instances.
     * @param taskInstances De id's van de task instances waarvoor we de task actor pools verwijderen.
     */
    void verwijderTaskActorPoolsVoorTaskInstances(List<Long> taskInstances);

    /**
     * Verwijdert de runtime actions voor het meegegeven proces.
     * @param procesId Het id van het proces waarvoor we de runtime actions verwijderen.
     */
    void verwijderRuntimeActionsVoorProces(Long procesId);

}

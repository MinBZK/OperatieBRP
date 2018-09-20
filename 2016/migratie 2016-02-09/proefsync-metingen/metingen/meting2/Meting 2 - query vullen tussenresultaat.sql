Drop table if exists metingTussenResultaat;
drop schema if exists meting cascade;

CREATE SCHEMA meting;

CREATE TABLE meting.tussenResultaat (
    bericht_id Varchar(36) NOT NULL, /* */
    status varchar(60),
    indicatie_beheerder boolean NOT NULL);
    
insert into meting.tussenResultaat (bericht_id, status, indicatie_beheerder)

(WITH berichten_foutafhandeling as (SELECT 
            berichten_foutafhandeling.message_id as bericht_id
        FROM 
            jbpm_processinstance jbpm_pi
        JOIN    jbpm_processinstance jbpm_pi_root
            ON jbpm_pi_root.roottoken_ = jbpm_pi.superprocesstoken_
        JOIN    mig_berichten berichten_foutafhandeling
            ON berichten_foutafhandeling.process_instance_id = jbpm_pi_root.id_
                AND berichten_foutafhandeling.naam = 'Lg01'
        WHERE 
            (jbpm_pi_root.end_ is not null AND
            jbpm_pi_root.processdefinition_ in (
                SELECT 
                    id_ 
                FROM
                    jbpm_processdefinition jbpm_pd
                WHERE
                    jbpm_pd.name_ = 'foutafhandeling'
                ))
            OR
            (jbpm_pi.end_ is not null AND 
            jbpm_pi.processdefinition_ in (
                SELECT 
                    id_ 
                FROM
                    jbpm_processdefinition jbpm_pd
                WHERE
                    jbpm_pd.name_ = 'foutafhandeling'
                ))
        )
 ,
berichten_beheerdersindicatie AS (
    SELECT berichten_beheerdersindicatie.message_id as bericht_id
    FROM mig_berichten berichten_beheerdersindicatie
    WHERE berichten_beheerdersindicatie.correlation_id in (select bericht_id from berichten_foutafhandeling))
(SELECT
        bericht.message_id as bericht_id,
    CASE WHEN (select code from mig_fouten where jbpm_pi.id_ = mig_fouten.process_instance_id AND mig_fouten.code IS NOT NULL order by id LIMIT 1 OFFSET 0) IS NOT NULL
THEN (select code from mig_fouten where jbpm_pi.id_ = mig_fouten.process_instance_id AND mig_fouten.code IS NOT NULL order by id LIMIT 1 OFFSET 0)  
    WHEN (select code from mig_fouten, jbpm_processinstance where jbpm_processinstance.roottoken_ = jbpm_pi.superprocesstoken_ and jbpm_processinstance.id_ = mig_fouten.process_instance_id AND mig_fouten.code IS NOT NULL order by id LIMIT 1 OFFSET 0) IS NOT NULL
THEN (select code from mig_fouten, jbpm_processinstance where jbpm_processinstance.roottoken_ = jbpm_pi.superprocesstoken_ and jbpm_processinstance.id_ = mig_fouten.process_instance_id AND mig_fouten.code IS NOT NULL order by id LIMIT 1 OFFSET 0)
    ELSE
'Ok'
     END,
        CASE WHEN bericht.message_id in (select bericht_id from berichten_beheerdersindicatie) THEN true ELSE false END as isFoutbericht
    FROM mig_berichten bericht
    LEFT OUTER JOIN mig_berichten gecorreleerd_bericht ON gecorreleerd_bericht.correlation_id = bericht.message_id
    LEFT OUTER JOIN jbpm_processinstance jbpm_pi ON jbpm_pi.id_ = bericht.process_instance_id
    WHERE bericht.kanaal = 'VOSPG' AND bericht.genegeerd = 'f' AND (bericht.naam = 'Pf01' or bericht.naam = 'Pf02' or bericht.naam = 'Pf03' or bericht.naam = 'Lg01')
        AND bericht.message_id NOT in (select bericht_id from berichten_foutafhandeling)
    ORDER BY bericht_id
));


insert into meting.tussenResultaat (bericht_id, status, indicatie_beheerder)
(select message_id, 'NOT' as status, false from mig_berichten where process_instance_id IS NULL AND
kanaal = 'VOSPG' AND genegeerd = 'f' AND naam = 'Lg01')
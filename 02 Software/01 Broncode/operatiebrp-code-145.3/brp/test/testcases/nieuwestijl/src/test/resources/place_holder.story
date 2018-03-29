Meta:
@sprintnummer               
@epic               
@auteur             aapox
@usecase            usecase_1, usecase_2
@jiraIssue          
@status             Onderhanden
@regels
@sleutelwoorden     testaaron        

Narrative:
            Als
            wil ik
            zodat


Scenario:   1. scenario description
            LT: R1234_LT01, R4567_LT01, R8910_LT03
            Verwacht resultaat:
            1. Volledig bericht
            2. Groep met ...
            3. etc ...

Given a system state
When I do something
Then system is in a different state


!-- Status mogelijkheden: Bug, Onderhanden, Klaar, Uitgeschakeld
!-- Indien 1 scenario Onderhanden is of status Bug heeft geeft dit dan ook aan per scenario,
!-- Scenario:   1. scenario description
!--                LT: R1234_LT01, R4567_LT01, R8910_LT03
!--                Verwacht resultaat:
!--                1. Volledig bericht
!--                2. Groep met ...
!--                3. etc ...
!--                @jiraIssue = TEAM-BRP 1234- Er is een bevinding etc....
!--
!-- Meta:
!-- @status     bug
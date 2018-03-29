UPDATE kern.pers 
SET naderebijhaard=(SELECT id FROM kern.naderebijhaard WHERE code='W')
WHERE anr='9610257185';
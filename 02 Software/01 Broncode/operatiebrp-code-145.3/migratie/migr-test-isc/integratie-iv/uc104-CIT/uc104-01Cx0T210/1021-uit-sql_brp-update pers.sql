UPDATE kern.pers 
SET naderebijhaard=(SELECT id FROM kern.naderebijhaard WHERE code='F')
WHERE anr='9610257185';
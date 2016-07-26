ALTER TABLE EGW_ESTIMATE_PHOTOGRAPHS ADD COLUMN version bigint DEFAULT 0;

ALTER TABLE EGW_ESTIMATE_PHOTOGRAPHS RENAME COLUMN ABSTRACTESTIMATE_ID TO ABSTRACTESTIMATE;
ALTER TABLE EGW_ESTIMATE_PHOTOGRAPHS RENAME COLUMN CAPTURED_DATE TO DATEOFCAPTURE;

ALTER TABLE EGW_ESTIMATE_PHOTOGRAPHS RENAME COLUMN CREATED_BY TO CREATEDBY;
ALTER TABLE EGW_ESTIMATE_PHOTOGRAPHS RENAME COLUMN CREATED_DATE TO CREATEDDATE;
ALTER TABLE EGW_ESTIMATE_PHOTOGRAPHS RENAME COLUMN modified_by TO lastmodifiedby;
ALTER TABLE EGW_ESTIMATE_PHOTOGRAPHS RENAME COLUMN modified_date TO lastmodifieddate;

ALTER TABLE EGW_ESTIMATE_PHOTOGRAPHS ALTER COLUMN LONGITUDE TYPE double precision;
ALTER TABLE EGW_ESTIMATE_PHOTOGRAPHS ALTER COLUMN LATITUDE TYPE double precision;

ALTER TABLE EGW_ESTIMATE_PHOTOGRAPHS DROP COLUMN est_photo_index;


--rollback ALTER TABLE EGW_ESTIMATE_PHOTOGRAPHS ADD COLUMN est_photo_index bigint;
--rollback ALTER TABLE EGW_ESTIMATE_PHOTOGRAPHS ALTER COLUMN LATITUDE TYPE bigint;
--rollback ALTER TABLE EGW_ESTIMATE_PHOTOGRAPHS ALTER COLUMN LONGITUDE TYPE bigint;

--rollback ALTER TABLE EGW_ESTIMATE_PHOTOGRAPHS RENAME COLUMN lastmodifieddate TO modified_date;
--rollback ALTER TABLE EGW_ESTIMATE_PHOTOGRAPHS RENAME COLUMN lastmodifiedby TO modified_by;
--rollback ALTER TABLE EGW_ESTIMATE_PHOTOGRAPHS RENAME COLUMN CREATEDDATE TO CREATED_DATE;
--rollback ALTER TABLE EGW_ESTIMATE_PHOTOGRAPHS RENAME COLUMN CREATEDBY TO CREATED_BY;

--rollback ALTER TABLE EGW_ESTIMATE_PHOTOGRAPHS RENAME COLUMN DATEOFCAPTURE TO CAPTURED_DATE;
--rollback ALTER TABLE EGW_ESTIMATE_PHOTOGRAPHS RENAME COLUMN ABSTRACTESTIMATE TO ABSTRACTESTIMATE_ID;
--rollback ALTER TABLE EGW_ESTIMATE_PHOTOGRAPHS DROP COLUMN version;
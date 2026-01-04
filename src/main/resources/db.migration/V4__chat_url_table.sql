------------------------------------------------------------
-- CHAT_URL TABLE
------------------------------------------------------------
CREATE TABLE CHAT_URL (
                          ID              UUID PRIMARY KEY DEFAULT uuid_generate_v4(),   -- URL ID
                          MESSAGE_ID      UUID NOT NULL REFERENCES CHAT_MESSAGE(ID),     -- LINKED MESSAGE
                          URL             TEXT NOT NULL,                                 -- URL VALUE

    -- AUDIT FIELDS
                          CREATED_AT      TIMESTAMP NOT NULL DEFAULT NOW(),
                          CREATED_BY      UUID,
                          UPDATED_AT      TIMESTAMP,
                          UPDATED_BY      UUID,
                          DELETED_AT      TIMESTAMP,
                          DELETED_BY      UUID,
                          IS_DELETED      BOOLEAN NOT NULL DEFAULT FALSE,

    -- VERSIONING
                          VERSION         INT NOT NULL DEFAULT 1
);

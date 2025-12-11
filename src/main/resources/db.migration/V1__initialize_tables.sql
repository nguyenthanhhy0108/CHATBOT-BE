-- UUID GENERATOR
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

------------------------------------------------------------
-- APP_USER TABLE
------------------------------------------------------------
CREATE TABLE APP_USER (
                       ID              UUID PRIMARY KEY DEFAULT uuid_generate_v4(), -- UNIQUE ID
                       USERNAME        VARCHAR(100) NOT NULL UNIQUE,                -- USERNAME
                       EMAIL           VARCHAR(150),                                -- EMAIL
                       PASSWORD_HASH   TEXT NOT NULL,                               -- ENCRYPTED PASSWORD

    -- AUDIT FIELDS
                       CREATED_AT      TIMESTAMP NOT NULL DEFAULT NOW(),            -- WHEN CREATED
                       CREATED_BY      UUID,                                        -- WHO CREATED
                       UPDATED_AT      TIMESTAMP NOT NULL DEFAULT NOW(),            -- LAST UPDATE TIME
                       UPDATED_BY      UUID,                                        -- WHO UPDATED
                       DELETED_AT      TIMESTAMP,                                   -- SOFT DELETE TIMESTAMP
                       DELETED_BY      UUID,                                        -- WHO DELETED
                       IS_DELETED      BOOLEAN NOT NULL DEFAULT FALSE,              -- SOFT DELETE FLAG

    -- OPTIMISTIC LOCKING
                       VERSION         INT NOT NULL DEFAULT 1                       -- INCREMENT ON UPDATE
);

------------------------------------------------------------
-- CHAT_SESSION TABLE
------------------------------------------------------------
CREATE TABLE CHAT_SESSION (
                              ID              UUID PRIMARY KEY DEFAULT uuid_generate_v4(),     -- SESSION ID
                              USER_ID         UUID NOT NULL REFERENCES APP_USER(ID),              -- OWNER
                              TITLE           VARCHAR(255),                                    -- SESSION TITLE

    -- AUDIT FIELDS
                              CREATED_AT      TIMESTAMP NOT NULL DEFAULT NOW(),                -- CREATED TIMESTAMP
                              CREATED_BY      UUID,
                              UPDATED_AT      TIMESTAMP NOT NULL DEFAULT NOW(),                -- UPDATED TIMESTAMP
                              UPDATED_BY      UUID,
                              DELETED_AT      TIMESTAMP,
                              DELETED_BY      UUID,
                              IS_DELETED      BOOLEAN NOT NULL DEFAULT FALSE,

    -- VERSIONING
                              VERSION         INT NOT NULL DEFAULT 1
);

------------------------------------------------------------
-- CHAT_MESSAGE TABLE
------------------------------------------------------------
CREATE TABLE CHAT_MESSAGE (
                              ID              UUID PRIMARY KEY DEFAULT uuid_generate_v4(),       -- MESSAGE ID
                              SESSION_ID      UUID NOT NULL REFERENCES CHAT_SESSION(ID),         -- LINKED SESSION
                              SENDER          VARCHAR(20) NOT NULL,                              -- USER/ASSISTANT/SYSTEM
                              MESSAGE         TEXT NOT NULL,                                     -- MESSAGE CONTENT
                              METADATA        JSONB,                                             -- TOKEN INFO, MODEL, COST

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

------------------------------------------------------------
-- CHAT_ATTACHMENT TABLE
------------------------------------------------------------
CREATE TABLE CHAT_ATTACHMENT (
                                 ID              UUID PRIMARY KEY DEFAULT uuid_generate_v4(),       -- ATTACHMENT ID
                                 MESSAGE_ID      UUID NOT NULL REFERENCES CHAT_MESSAGE(ID),         -- MESSAGE LINKED
                                 FILE_URL        TEXT NOT NULL,                                     -- STORAGE URL
                                 FILE_TYPE       VARCHAR(50),                                       -- MIME TYPE

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

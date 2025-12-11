------------------------------------------------------------
-- INDEXES FOR CHAT_SESSION
------------------------------------------------------------

-- FAST LOOKUP OF ACTIVE SESSIONS BY USER
CREATE INDEX IDX_CHAT_SESSION_USER_ACTIVE
    ON CHAT_SESSION (USER_ID)
    WHERE IS_DELETED = FALSE;

------------------------------------------------------------
-- INDEXES FOR CHAT_MESSAGE
------------------------------------------------------------

-- FAST LOOKUP OF ACTIVE MESSAGES BY SESSION
CREATE INDEX IDX_CHAT_MESSAGE_SESSION_ACTIVE
    ON CHAT_MESSAGE (SESSION_ID)
    WHERE IS_DELETED = FALSE;

-- FULL-TEXT SEARCH ON MESSAGE CONTENT (ONLY ACTIVE)
CREATE INDEX IDX_CHAT_MESSAGE_TEXT_SEARCH
    ON CHAT_MESSAGE
    USING GIN (TO_TSVECTOR('simple', MESSAGE))
    WHERE IS_DELETED = FALSE;

------------------------------------------------------------
-- INDEXES FOR CHAT_ATTACHMENT
------------------------------------------------------------

-- FAST LOOKUP OF ACTIVE ATTACHMENTS BY MESSAGE
CREATE INDEX IDX_CHAT_ATTACHMENT_MESSAGE_ACTIVE
    ON CHAT_ATTACHMENT (MESSAGE_ID)
    WHERE IS_DELETED = FALSE;

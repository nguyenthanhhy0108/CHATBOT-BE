INSERT INTO APP_USER (
    USERNAME,
    EMAIL,
    PASSWORD_HASH,
    CREATED_AT,
    CREATED_BY,
    UPDATED_AT,
    UPDATED_BY,
    IS_DELETED,
    VERSION
)
VALUES (
           'admin',
           'admin@example.com',
           '$2a$12$Ob7fO1M/5MiVzI9FtA1BvuH1CBYSRcZnuohXdFfBCW7KkX8qNojNG',
           NOW(),
           NULL,
           NOW(),
           NULL,
           FALSE,
           1
       );

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE client_seq';
    EXECUTE IMMEDIATE 'DROP SEQUENCE account_seq';
    EXECUTE IMMEDIATE 'DROP SEQUENCE payment_seq';
    EXECUTE IMMEDIATE 'DROP SEQUENCE purchase_seq';
    EXECUTE IMMEDIATE 'DROP TABLE purchases CASCADE CONSTRAINTS';
    EXECUTE IMMEDIATE 'DROP TABLE payments CASCADE CONSTRAINTS';
    EXECUTE IMMEDIATE 'DROP TABLE accounts CASCADE CONSTRAINTS';
    EXECUTE IMMEDIATE 'DROP TABLE clients CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 AND SQLCODE != -2289 THEN
            RAISE;
        END IF;
END;
/

CREATE SEQUENCE client_seq START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE SEQUENCE account_seq START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE SEQUENCE payment_seq START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE SEQUENCE purchase_seq START WITH 1 INCREMENT BY 1 NOCACHE;

CREATE TABLE Clients (
                         id NUMBER PRIMARY KEY,
                         first_name VARCHAR2(50) NOT NULL,
                         last_name VARCHAR2(50) NOT NULL,
                         address VARCHAR2(100) NOT NULL,
                         postal_code VARCHAR2(10) NOT NULL
);

CREATE TABLE Accounts (
                          id NUMBER PRIMARY KEY,
                          account_number VARCHAR2(50) NOT NULL,
                          balance NUMBER(10, 2) NOT NULL,
                          client_id NUMBER NOT NULL,
                          CONSTRAINT fk_client FOREIGN KEY (client_id) REFERENCES Clients(id) ON DELETE CASCADE
);

CREATE TABLE Payments (
                          id NUMBER PRIMARY KEY,
                          account_id NUMBER NOT NULL,
                          transaction_date DATE NOT NULL,
                          amount NUMBER(10, 2) NOT NULL,
                          payment_type VARCHAR2(50) NOT NULL,
                          CONSTRAINT fk_account_payment FOREIGN KEY (account_id) REFERENCES Accounts(id) ON DELETE CASCADE
);

CREATE TABLE Purchases (
                           id NUMBER PRIMARY KEY,
                           account_id NUMBER NOT NULL,
                           transaction_date DATE NOT NULL,
                           amount NUMBER(10, 2) NOT NULL,
                           CONSTRAINT fk_account_purchase FOREIGN KEY (account_id) REFERENCES Accounts(id) ON DELETE CASCADE
);

CREATE OR REPLACE TRIGGER trg_client_id
    BEFORE INSERT ON Clients
    FOR EACH ROW
BEGIN
    SELECT client_seq.NEXTVAL INTO :NEW.id FROM dual;
END;
/

CREATE OR REPLACE TRIGGER trg_account_id
    BEFORE INSERT ON Accounts
    FOR EACH ROW
BEGIN
    SELECT account_seq.NEXTVAL INTO :NEW.id FROM dual;
END;
/

CREATE OR REPLACE TRIGGER trg_payment_id
    BEFORE INSERT ON Payments
    FOR EACH ROW
BEGIN
    SELECT payment_seq.NEXTVAL INTO :NEW.id FROM dual;
END;
/

CREATE OR REPLACE TRIGGER trg_purchase_id
    BEFORE INSERT ON Purchases
    FOR EACH ROW
BEGIN
    SELECT purchase_seq.NEXTVAL INTO :NEW.id FROM dual;
END;
/





SELECT * FROM CLIENTS


















    //
SELECT * FROM PURCHASES



SELECT * FROM Payments WHERE transaction_date BETWEEN '2024-06-01' AND '2024-06-31';



SELECT * FROM  Clients



SELECT * FROM  ACCOUNTS
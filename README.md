# CurrencyRateAggregationService

**Technical task**
1. The application should be able to download exchange rates from text files that banks provide. 
2. Each bank provides one file with all exchange rates
3. Files can be of three formats: CSV, XML, JSON 
4. The file name (before the extension) is the name of the bank and should be further used by the system in this form
5. The list of currencies may be specific to each bank
6. Return the purchase rate of the specified currency by all banks
7. It should be possible to sort the results in descending / ascending order
8. Return the selling rate of the specified currency by all banks
9. Provide the ability to update the purchase or sale rate of the specified currency for a single bank
10. In particular, it should be possible to establish / remove a ban on the purchase or sale of a certain currency
11. Delete all offers for the purchase / sale of currency for a particular bank
12. Create a report with the best offers on the purchase and sale of all currencies
13. The service must be implemented using JDK 8 or later
14. Code coverage by unit tests



**Instruction for start application**
1. You should to create DB. For this action go to:

     a. In the Database tool window (View | Tool Windows | Database), click the data source.
    
     b. Open F4 or create (Ctrl+Shift+F10 | New Console) a database console.
    
    c. Type or paste the statement that you want to execute depending on the base you are using.
     
    d. Input user, password for your DB and press 'Test Connection'
    
    e. Go to file createDBCurrencyScripts.sql       
    
    f. Press Ctrl+Enter. Alternatively, click the Execute icon 'Run' on the toolbar.

2. You should to create Table. For this action go to `/resources/dump_db/createTableCurrencyScripts.sql` and repeat command 'Execute'. Example item 'f'.
3. You should to create DB for test app.  For this action repeat items '1.e' and '1.f' for file `/resources/dump_db/createDBCurrencyTestScripts.sql`.
4. You should to create Table for test app. You need to choose your scheme '**currency_test.public**' before creating table. 
For this action go to `/resources/dump_db/createTableCurrencyScripts.sql` and repeat command 'Execute'. Example item 'f'.
5. For start this one go to Application.class and press Run


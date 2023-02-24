Create a spring boot application to get the currency exchange rate for a particular day for the given currencies
 EUR - USD ,
 INR - USD ,
 AED - USD ,
 CAD - USD ,
 JPY - USD

eg : I want to get the 
1) fetchAllExchangeRate REST api which fetches the price of all the above currencies for the given date , while fetching Rest calls to external API needs to be in parallel . impl - 5
- Input is a optional field ( Date )
- Add timeout for getting responses.
- Status: SENT_THE_REQ , RECEIVED_RESPONSE
    
    
2) Write it to an excel in a single sheet , in the below order  -5
- AED
- CAD
- EUR
- INR
- JPY

when i trigger the req. for the second time , it should append on the same order
    
    -- BASE CURRENCY  , CONVERSION_CURRENCY , RATE ,    CREATE_TS
        AED                USD                 XYZ      2023-02-18
        CAD                USD                 XYZ      2023-02-18
        ...
        
        AED                USD                 XYZ      2023-02-18
        CAD                USD                 XYZ      2023-02-18
        
3) Create Testcases  - 5

4) Store the Request and Response in the db as well - ( Db Connectivity - 2 , Impl - 5 )
- AUDIT_INFO

   REQUEST_ID , STATUS ,  REQUEST , RESPONSE , CREATE_TS , UPDATE_TS 

5) Coding Best Practices - 3

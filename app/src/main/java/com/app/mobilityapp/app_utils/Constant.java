package com.app.mobilityapp.app_utils;

/**
 * Created by zaptasH on 1/17/2017.
 */
public class Constant {


       public static final String BASE_API_URL="http://ws.onlinedelivery.in/wcfservicecustomer.svc/";
       public static final String BASE_API_URL_SERVICE="http://ws.onlinedelivery.in/serviceProduct.svc/";

// http://34.215.46.59/ServiceProduct.svc?wsdl

//  1__signin:		         http://34.208.118.103/wcfserviceCustomer.svc/ValidateUser/neha.3512@yahoo.com/nehanehatyagi
//  2__registration:        	 http://34.208.118.103/wcfserviceCustomer.svc/CustomerRegistration/neha%20tyagi/9210850207/neha.3512@yahoo.com/nehanehatyagi
//  3__my_details:          	 http://34.208.118.103/wcfserviceCustomer.svc/UpdateCustomerInfo/?CustomerID=1&38375&CustomerName=neha%20tyag&MobileNo=75757575757&EmailID=neha.3512@yahoo.com&Password=nehanehatyag
//  4__customer point:         http://34.208.118.103/wcfserviceCustomer.svc/CustomerPoint?CustomerID=1
//  5__login:                  http://34.208.118.103/wcfserviceCustomer.svc/ValidateUser/neha.3512@yahoo.com/a
//  6__customer reminder:     http://34.208.118.103/wcfserviceCustomer.svc/CustomerReminder?ReminderID=0&CustomerID=1&RowCount=10
//  7__customer order:             http://34.208.118.103/wcfserviceCustomer.svc/CustomerPoint?CustomerID=1
//  8__track order:                http://34.208.118.103/wcfserviceCustomer.svc/TrackOrder/?OrderID=63240&EmailID=ajmalshah94@gmail.com
//  9__GetDeliveryCity:             http://34.208.118.103/wcfserviceCustomer.svc/GetDeliveryCity/
//  10__drop down in website based on DropDownKey:  http://34.208.118.103/wcfserviceCustomer.svc/GetDropDownValue/DropDownKey=ReminderType
//  11__Delete Reminder method:     http://34.208.118.103/wcfserviceCustomer.svc/DeleteReminder/?ReminderID=1
//  12__Insert / Update Reminder Method     :  http://34.208.118.103/wcfserviceCustomer.svc/InsertUpdateReminder/?ReminderID=1&ReminderTypeID=32&CustomerID=1&ReminderDate=7/Jun/2017&ReminderMessage=Happy%20Birthday&RemindYouEveryYear=true
//  13__complete order details with product list :   http://34.208.118.103/wcfserviceCustomer.svc/GetOrderDetails/?OrderID=74544
//  14__activate/Deactivate Reminder :  http://34.208.118.103/wcfserviceCustomer.svc/ReminderAction/?ReminderID=5184
//     15__order tracking graphically   :	http://34.208.118.103/wcfserviceCustomer.svc/ordertracking/?OrderID=75324
//      ConversationModel	:	http://www.onlinedelivery.in/paidorderstatus.aspx?ID=75324&EmailID=sujju20choudhari@gmail.com

//    16__productLoad  :	http://34.208.118.103/wcfserviceCustomer.svc/productLoad/?pageurl=flowers-delivery-in-delhi.aspx
//    17__GetProductDetails	:	http://34.208.118.103/wcfserviceCustomer.svc/GetProductDetails/?ProductID=17


              //{"NewDataSet": {"Table1": {"CustomerCompanyName": null, "CustomerID": "38375", "CustomerName": "Anjum Zafar", "DeliveryCharge": "0", "DeliveryType": "Standard Delivery", "Email": "neha.3512@yahoo.com", "Message": "getwell soon...my dad", "MessageOnCake": "Happy Birthday", "Mobile": "9599800882", "PointRedemption": "0", "PreferredDate": "05\\\\/July\\\\/2017", "PreferredTime": "11 AM - 6 PM", "PromotionalCode": null, "PromotionalDiscount": "0", "RecipientAddress": "Ramesh Park, Laxmi Nagar", "RecipientCity": "Delhi", "RecipientCompanyName": null, "RecipientCountry": "India", "RecipientMobile": "9599800882", "RecipientName": "R.Sundaram", "RecipientPostCode": "110092", "RecipientState": "Delhi", "RecipientTelephone": "9599800882", "ship_country": "IND", "Telephone": null, "TotalAmount": "1090" } }}&{ "NewDataSet": { "Cart": [ {"PhotoToBePrintOnProduct": null, "Price": "395", "ProductID": "2", "ProductName": "Bunch of 12 Red Roses", "ProductQty": "1", "SmallImage": ".jpg" }, {"PhotoToBePrintOnProduct": null, "Price": "695.00", "ProductID": "29", "ProductName": "1\\\\/2 Kg Chocolate cake", "ProductQty": "1", "SmallImage": ".jpg" },{"PhotoToBePrintOnProduct": null, "Price": "395", "ProductID": "2", "ProductName": "Bunch of 12 Red Roses", "ProductQty": "1", "SmallImage": ".jpg" }, {"PhotoToBePrintOnProduct": null, "Price": "695.00", "ProductID": "29", "ProductName": "1\\\\/2 Kg Chocolate cake", "ProductQty": "1", "SmallImage": ".jpg" } ] }}


    //   http://34.208.118.103/wcfserviceCustomer.svc/EBSPayment/?
   //    ResponseCode=&ResponseMessage=&DateCreated=&PaymentID=&MerchantRefNo=&Amount=&Mode=&BillingName=&BillingAddress=&BillingCity=&BillingState=&BillingPostalCode=&BillingCountry=&BillingPhone=&BillingEmail=&DeliveryName=&DeliveryAddress=&DeliveryCity=&DeliveryState=&DeliveryPostalCode=&DeliveryCountry=&DeliveryPhone=&Description=&IsFlagged=&TransactionID=&PaymentMethod=&PaymentThrough=&RequestID=&LoginID=

// update order detail : http://34.208.118.103/serviceproduct.svc/UpdateOrderDetail/?OrderID=73289&CustomerName=Raj&RecepientName=Arvindh&Address=basantpur&City=delhi&State=delhi&PostalCode=110095&PhoneNo=9999888845&MobileNo=6489751230&DeliveryDate=1/4/2017&PrefferedTime=&MessageOnCard=&MessageOnCake=&MessageOnMug=
// Price : http://34.208.118.103/wcfserviceCustomer.svc/ProductLoadByPrice/?serachtext=&priceFrom=0&priceTo=500&CategoryID=0

//http://34.215.46.59/ServiceProduct.svc?wsdl

          //  Payumoney: 5123456789012346, 123, 05/20
         // paytm: card : 5426064000424979 ,exp : 12/224 ,cvv : 979 ,3d authentication : secret3
        // ebs : card details:  4111 1111 1111 1111 ,cvv : 123 ,date: 06 2022
       // paypal : nehakatoch.3512@yahoo.com nehapaypal     ,   neha.3512-buyer@yahoo.com nehakatoch
      // Credit Card Number:4032037534675125,Credit Card Type:VISA,Expiration Date:08/2022 ,Account Number:930026165


       // Live Payment Credential :  Flagged

       // https://finance.google.com/finance/converter?a=500&from=INR&to=USD

// http://34.215.46.59/wcfservicecustomer.svc/PayUmoneyPaymentGateway/?OrderID=83682&PaymentStatus=success&amount=200
}

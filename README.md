#MacBooks

An app that connects buyers and sellers of used books on Macalester College's campus.

## Architecture

This repository includes both the Android based client and the MySQL-based server engine.  Our server provides an interactive REST API using Google Cloud Endpoints which provides details of books being sold and user's books for sale.  Our server responds instantly to requests made by users selling books and people requesting the details of a book.  If a user requests that they would like to buy a book an email is sent to the seller of that book.

##Client Development

The Android client is targeted towards Android SDK Version 17, and the minimum version is 15.  To test the app we have added fake books for sale.  When launched on an emulator or a device the user is able to add books or notify the seller they would like to buy a book.  

##Server Development

The server engine uses MySQL.  You must download the Google App Engine SDK for Java.  You also must have a Google App Engine account if you wish to work on the back end.  You also must have Eclipse downloaded with the Google Plugin for Eclipse.  

# LiveData with Single Event

This reference from [architecture-live-data-api](https://github.com/kisahtegar/FundamentalAndroid/tree/architecture-live-data-api)
in this case app show snackbar after post. Basically LiveData will observe the data again when a 
configuration change occurs. So, the problem is that even though LiveData is a single event, it will 
always appear when the configuration changes. Even though functionally it shouldn't be like that. 
To solved that we using a wrapper class for data exposed via a LiveData, representing an event that 
should be handled only once.

| Nama Project                  | RestaurantReview               |
|-------------------------------|--------------------------------|
| Target & Minimum Target SDK   | Phone and Tablet, API level 24 |
| Tipe Activity                 | Empty Views Activity           | 
| Activity Name                 | MainActivity                   |
| Language                      | Kotlin                         |
| Build Configuration Language  | Kotlin DSL                     |

<img src="preview_1.png" alt="Preview 1" width="200" height="400">


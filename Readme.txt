
I determined the information to be synchronized and saved it to my local database. Methods running as cron jobs perform synchronization using this database. In addition, the service that shows the synchronization information also gets from this database.

2 services have been written for the synchronization of the Edushake platform.

Service 1:
CodeShakeController-> /sync

It contains two methods: Since I want the synchronization to be automatic all the time, these two methods run every 5 minutes as a cron job and synchronization takes place.

1) CodeShakeSynchronizationService-> checkForNewUpdateForEdushake()

In this method, user information is collected from all source projects.
In the same way, user information is obtained from the edushake platform.
The information of new users who are not in the Edushake platform are saved in the local db syncronization_result table. (Used PostgreSQL)
Written information: Username, email, role etc.
Also added fields:
OperationStatus: UNDONE/DONE
ProcessType: ADD/DELETE

2)CodeShakeSynchronizationService-> doSync()

UNDONE ones are taken from the syncronization_result table in the database. Each user is registered in the federation. Federation ids are updated in db.
Finally all users are registered on the Edushake platform

Service 2:
CodeShakeController-> /syncronization-results

Service that displays synchronization information. Used in Angular project. Displays what is marked as DONE in the Synchronization_results table
Information that will appear on the screen:

ID
Name
E-mail
roleType
federationId
operationType: ADD/DELETE
processTime 
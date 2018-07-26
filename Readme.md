
![gobiiproject.org](WebContent/imgs/gobiilogo.png)

# GOBii Data Timescope

## This is a web application intended to be a database management tool for GOBii's GDM data warehouse. 

This tool uses the ZK framework for all the web components and JOOQ for a thin data access layer.


## Main Features

This tool will allow system administrators and anyone with admin access to the system to be able to browse and permanently delete data.

### There are 5 modules:

1. User Authentication and Authorization
2. Dataset
3. Markers
4. Dnasample
5. Dnarun

### User Authentication and Authorization Module

This requires the usual login page as a landing page. A registration page, however, is not necessary, and that adding new administrators should only be possible via the use of a superAdmin account. This account should also be capable of deleting rows in the administrator table.

##### ROLES:

1=Super Admin, 2=Admin, 3=User (reserved for foreseen functionality requests, ex. users of type 3 can only update data and not delete them)


This form will connect directly to the table *timescoper* in a GOBii database, which means *each GDM instance (crop) will have its own auth set –* ie. to be able to delete data from one crop, the user account needs to be set for that particular crop database.

 * The left content section will contain user information of the current logged in user
 ** An option to edit own's profile will be provided – which will open the same edit dialogue window as the edit option below
 * The right content section will show a list of Timescope users IF the logged in user is a super admin
 ** Options to create new user, edit existing user, and delete user will be available in the left content section as well

Levels of authorization per user type:

 ** Super Admin
 *** Ability to delete database entities
 *** Ability to update database entities
 *** Ability to manage users (create, edit, delete)
 * Admin
 ** Ability to delete database entities
 ** Ability to update database entities
 * User
 ** Ability to update database entities

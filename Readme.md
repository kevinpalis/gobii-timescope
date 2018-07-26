
![gobiiproject.org](WebContent/imgs/gobiilogo.png)

# GOBii Data Timescope

## This is a web application intended to be a database management tool for GOBii's GDM data warehouse. 

This tool uses the ZK framework for all the web components and JOOQ for a thin data access layer.


## Main Features
 

### There are 5 modules:

1. User Authentication and Authorization
2. Dataset
3. Markers
4. Dnasample
5. Dnarun

### User Authentication and Authorization Module

This requires the usual login page as a landing page. A registration page, however, is not necessary, and that adding new administrators should only be possible via the use of a superAdmin account. This account should also be capable of deleting rows in the administrator db table.

##### ROLES:

1=Super Admin, 2=Admin, 3=User (reserved for foreseen functionality requests, ex. users of type 3 can only update data and not delete them)
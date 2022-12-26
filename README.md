# Directory Controller

This is a sample case study of how we can read files in local machines and save their data to database.

## Directory Structure

1. Root Dir->src/main/java/org/example
2. core This directory contains helper classes
3. data This directory is responsible for connecting to database and read values in database.
4. domain This is interface between data and the main visible program. It mainly contains interface classes for
   connecting to the database.

### How to set up

1. First fork this repository by running the command
    ```
   git clone git@github.com:nipun2003/DirectoryControl.git
   cd DirectoryControl
   ```
### Pre-Requisite
1. Java-Jdk installed in your system
2. gradle 7.4 or above 
3. Postgresql installed on your system.
4. Go to src/main/java/org/example/data/database/PostgresDatabase 
   1. Change database name to your database in line no 37
   2. Change database user and password with your user and password on line 37

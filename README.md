# selenium
test assignment
On GitHub, navigate to the main page of the repository.

Above the list of files, click  Code. Copy the URL.

Open Git Bash

Change the current working directory to the location where you want the cloned directory.

Type git clone, and then paste the URL you copied earlier.

$ git clone https://github.com/YOUR-USERNAME/YOUR-REPOSITORY
Press Enter to create your local Clone.
Open Eclipse
Click on File>Import
Provide location of project from local
Click Finish

Right click on project->Build Path-> Configure Build Path. Add required libraries and external jars to be used in project

Class:

Main class: WeatherReportingTest

Class "APItesting" extract the API response

Class "FrontEnd" performs selenium actions on web browser (Google chrome v 85). calls the APItesting class and performs variance logic

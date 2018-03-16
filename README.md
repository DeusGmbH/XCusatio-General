# XCusatio-Developers Guide

## Github

### ZenHub
To use the issues feature of GitHub more effectively and better plan and track or progress, we are going to use the ZenHub plugin.
This allows us to organize all the issues in pipelines and create an overview on all our tasks.

To use this, everyone of us has to download the [Browser extension for either Chrome or Firefox](https://www.zenhub.com/choose-platform). As soon as you have the plugin installed you can switch to the [ZenHub Board of XCusation-General](https://github.com/DeusGmbH/XCusatio-General#boards).

### Setting up the GitHub Desktop Application
GitHub provides the *GitHub Desktop* App that helps you use GitHub without the command line.
Here is the [Link](https://help.github.com/desktop/guides/getting-started/installing-github-desktop/) for the installing guide.

### How we use GitHub

#### Structure of our projects
We'll only have one main repository containg the complete application. 

#### The Git pull request workflow
The git pull request workflow is one way of collaborating with git.

The workflow is as follows:
1. You want to fix a bug or add a feature to a repository
2. You either already have the repository cloned on your computer or you have to clone it to your computer
3. You create a new branch with a name like "fix/<bugname>" or "feature/<featurename>" (usually the master branch should be the parent branch)
4. You checkout your new branch
5. You add your code to your branch by committing small chunks of it that each have a meandingful description of what has been changed
6. You push your commits into the remote repositoy
7. You open a pull request and ask for a code review by another person
8. This person then reviews your code. This includes
    - requesting improvements
    - requesting clarification
    - requesting discussion (or another review by someone else)
    - and after all issues have been taken care of, the approval of your pull request
9. Merging your branch into the master (or parent) branch


## Ecplise
As IDE I would advise you to use [Eclipse Oxygen](http://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/oxygenr) because it already contains most of the features we need for our development (EGit, Maven, etc.).

### Code Formatting
To make sure we all follow the same code formatting conventions, you should import this [EPF-File](https://github.com/DeusGmbH/XCusatio-General/blob/master/eclipse-format-settings.epf) into [eclipse](https://help.eclipse.org/neon/index.jsp?topic=%2Forg.eclipse.platform.doc.user%2Ftasks%2Ftimpandexp.htm).
After you imported the new code formatting preferences, you should also activate the save action for code formatting.
To do this go to *Window* -> *Preferences* -> *Java* -> *Editor* -> *Save Actions*. There you need to activate "Format source code" and "Organzie imports".

### Github plugin (EGit)
This plugin is already included in *Ecplipse Oxygen*. Unfortunatly importing gradle projects via the EGit plugin seems to be broken in some way. You may try, but for now I recommend you either use Git from the CommandLine or from the Git Desktop Application.

#### Workaround
After some trial and error I found out, that you can do the following to make EGit work properly:
1. Clone the repository you want to collaborate via [GitHub Desktop Application](https://github.com/NoxAG/Developer-Guide#setting-up-the-github-desktop-application) or the Git Commandline
2. Open Eclipse
3. Go to *File* -> *Import...* and than search for *Existing Gradle Project*. Then you can choose the project path of the repository you cloned and import that project.
4. Now you can use all features provided by EGit

### Sonarqube Plugin
SonarQube is an open source platform for continuous inspection of code quality to perform automatic reviews with static analysis of code to detect bugs, code smells and security vulnerabilities. It offers reports on duplicated code, coding standards, unit tests, code coverage, code complexity, comments, bugs, and security vulnerabilities.

To use this plugin, you need to install it via the Ecplise Marketplace. To do that, open Ecplise and go to *Help* -> *Eclipse Marketpalce*.
Than use the searchbar to find "*SonarLint 3.2.0*" and click *install*. As soon as the installation has finished you need to restart eclipse. After that you can use the features of sonarqube by right clicking on you project and choosing *SonarLint* -> *Analyze*

### Gradle
We use Gradle as dependency manager. So to if there are any libraries we need/want to include, for example to interact with the Google Calendar API more easily, we need to add these libraries as gradle dependencies to the build.gradle file.

## Java
You should also make sure to have [Java SE Development Kit 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) installed. 

### Eclipse Project Configuration
Using Java 8 can be a little bit more tricky and did not work for us at the beginning, beause eclipse had been using an older Java version as default. To fix this for a specific project you need to righ click the project in the *Project Explorer* and then choose *Properties*. There you go to *Java Build Path* -> *Libraries* and select * JRE System Library*. Then click *edit* and either choose *Alternate RJE* or *Workspace default JRE*. Having "Execution envoirnment" selectet can lead to errors if Java 7 is also installed on the same operating system.

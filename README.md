#Mathematical Simulation - The Call Center Project
## How to install
Maven is used for dependency management. Incase you don't have maven installed I have included the binary in this project.
Please make sure you have a valid java jdk installed on your computer.

If not you can find the java jdk here:
https://www.oracle.com/java/technologies/javase-jdk14-downloads.html


###Install:
`maven\bin\mvn install`

## Project
#####Configuration
The project was made with configurability in mind. 
Under the directory `src\main\java\configs` you will find all the configuration files. ````

#####Schedule
In the scheduleconfig you can set the amount of agents for each shift per agent type.

#####Strategies
There are 4 type of strategies that can be used in a simulation.
`NoStrategy`
`CorporateQueueSwarmStrategy` 
`TimedConsumerQueueTimeStrategy`
`TimedCorporateAgentsIdleStrategy`

You can change strategies by change the strategy variable in the simulationconfig.

You can easily create your own strategy by extending the AbstractStrategy Class and implementing the execute method.
#####Note
This project uses 2 external libraries to plot the graphs (JFreeChart) and perform unit testing (JUnit). 
The dependencies can be found in pom.xml.
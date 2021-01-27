# CommandItem  
  

#### Create and run commands from an Item

###### IntelliJ Idea:   
1. Open IDEA, and import project.  
2. Select build.gradle and have it import.     
3. Run the following command: "gradlew genIntellijRuns" (./gradlew genIntellijRuns if you are on Mac/Linux)    
4. Refresh the Gradle Project in IDEA if required.    
5. Run gradlew build    
6. Files will be located in build/libs    

In game, run `/summon commanditem[tab]`  
Right click to set command, left click to run it.   

Debug: `/data get entity @s SelectedItem` to view NBT information
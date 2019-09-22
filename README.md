# Train station operating system project
![](http://www.railtechnologymagazine.com/write/MediaUploads/iStock-1145773618.jpg)
 ## Project aim 
The aim of this project is to apply the theoretical concepts of threads, synchronization and scheduling, that we learned during CS322 course into real-world application. 

---
##  Problem description 
A train station decides to reduce the human resource (HR) by automating trains scheduling, i.e. an order in which trains go through the common line. The train station receives trains that have different levels of importance, i.e. importance of how they should quickly arrive to their destinations. Firstly, trains must enter to the check point. In this train station, there is one check point serving trains. As a result, only one train can enter to the check point at a time even if several trains arrived at the same time to the station. At the check point, the station's workers determine the priority of the entered train. Moreover, they estimate the time that the train will take it in the common line. Usually the priority of the train is given depending on their types [passenger train, train of goods (full), and train of goods (empty)]. Obviously, if there are trains with same type, the one with earlier arrival time is assigned a higher priority. And in the case of tie, the higher priority is given to one train in random. Similarly, the time a train takes it in the common line is decided based on the train's type. For instance, a passenger train is given a time ranging from 20-30 minutes, a train of goods (full) from 40-50 minutes, and a train of goods (empty) from 30-40 minutes. Design a system for the train station which automates the operation of admitting the trains to the station and scheduling them to the common line. In order to completely simulate the train station system, read the trains' information from the input file. However, their information cannot be stored in the program simultaneously. Particularly, you need to ensure that registering/storing the data of one train only is done at time. After you finish storing all the trains' information using the appropriate data structures and methods, you will find the order in which the trains will be assigned to go through the common line. In other words, you will find the schedule of treating the trains using the priorities given by the workers in the check point earlier. Systems At the end, calculate the average waiting time and average turnaround time for all trains coming to the train station. Assume the time is given in minutes. 

---
##  Problem solution 
To solve the trains scheduling problem, we assume that each train represents a single thread, first the trains(Threads) will go to the checkpoint to store their information which is the critical section by calling start() function in thread library which executes a call to run() function, run() function represents the checkpoint, but no train will enter the checkpoint unless it has a permission and if a train enters the checkpoint no other train will enter, therefore we used a binary semaphore to do this, each train has a boolean variable named lock, lock is initialized to false, the lock can be accessed via pleaseWait() function and release() function, if a train attempt to enter the checkpoint, pleaseWait() will check if the checkpoint is free(lock=true) the train will enter otherwise(lock=false) it will wait until the checkpoint is free. release() function will permit a train to enter the checkpoint ensuring that they will enter according to their order (the way they arrived) in the ready list, if one train leaves the checkpoint release() function will permit the next train to enter, it will set lock of a train to true to let it enter to the checkpoint, if the train enters the checkpoint, we will read and store the train's information, which are arrival time, number, type and we will determine the priority for each train which is depends on train type as it is illustrated in the problem description above, and we should estimate the time that the train will take it in the common line to ensure that no train will starve while waiting and no train will take more time than needed, therefore, we assume that the time will be given in random way through getDuration(from,to) function that takes the ranges specified in the problem description for each type and return a random value between that range, after storing all the trains' information, we will schedule the trains according to their priorities, if they tie, we will schedule them according to their arrival times, if they also tie we will choose the first train, therefore the priority scheduling algorithm will be used to schedule the trains along with FCFS(First Come First Serve) algorithm that we have learned during CS322 course, after that, we will put all trains in the waiting list to let them go through the common line one by one according to their order (the way they are scheduled), in the common line we will take the train information, the time when it entered, the time when it is going to leave and we will calculate the waiting time and the turnaround time. finally, when all trains enter the common line, we will print the scheduling method that they entered according to it, average waiting time and the average turnaround time. 

---
###  Conclusion 

In conclusion, we applied three concepts to solve the train scheduling problem which are Thread, synchronization and scheduling, at the beginning we create multiple threads that represent the trains, then we apply a synchronization solution which is a binary semaphore to synchronized the threads to enter the checkpoint to store their information, assigned the priority for each train and the time that the train will take in the common line, after that we scheduled the trains using priority scheduling algorithm and  FCFS(first come first served) algorithm, the priority depends on the train type [passenger train- priority 1, train of goods (full)-priority 2, and train of goods (empty)-priority 3], then all trains went to the common line, in the common line we store the time when the train enters and when it left, also we calculated waiting time, turnaround time, the average  waiting time and the average  turnaround time, Finally we print all trains information and the scheduling method. we learned a lot through this project, we learn how the threads work, how to synchronize them and how to schedule the threads. 

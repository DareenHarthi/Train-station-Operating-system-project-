/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication6;

/**
 *
 * @author hp
 */
import java.io.IOException;
 import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TrainStation {
     /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        Train.scanner = new Scanner(Paths.get("input.txt"));
        int num_of_trains = Train.scanner.nextInt();
        Train[] trains = new Train[num_of_trains];
        //read the train information one by one and store each train in the array as thread
        for (int i = 0; i < num_of_trains; i++) {
            Train train = new Train();
            trains[i] = train;
            //Go to check point to store trains information  only one train can enter to the check point at a time
            train.start();
        }
        Train.ready = Arrays.asList(trains);
        //permit the first train to enter the check point to store it information the other trains will be waiting.
        Train.ready.get(0).lock = true;
        
        }
   
    }
class Train extends Thread implements Comparable<Train> {
    int arrival_time;
    int train_number;
    static Scanner scanner;
    int duration;
    static int  total_time = 0;
    static double  turnaround_time = 0;
    static double  waiting_time = 0;
    static List<Train> waiting;
    static List<Train> ready;
    boolean lock = false;
    static String schedule = "The trains are scheduled as follow:\nIn the common line:\n";

    @Override
    //check point
    public void run() {
        pleaseWait();  //wait until the check point become free
        // welcome to the check point you can start execution
        train_number = scanner.nextInt();
        arrival_time = scanner.nextInt();
        String train_type = scanner.next();
        //set the priority for each train according to the train type
        switch (train_type) {
            case "passenger":
                setPriority(1);
                /*getDuration  estimate the time a train takes it in the common 
                    line in givin range based on the train's type in random way **/
                duration = getDuration(20, 30);
                break;
            case "fullgoods":
                setPriority(2);
                duration = getDuration(40, 50);
                break;
            case "emptygoods":
                setPriority(3);
                duration = getDuration(30, 40);
                break;
            default:
                break;
        }
        release();//let the next train enter to the check point 
    }

    public void release() {
        // allow the waiting train to enter the check point
        int nextTrain = ready.indexOf(this) + 1;
        if (nextTrain < ready.size()) {
            ready.get(nextTrain).lock = true;
        } 
        else {            //wait untill the check point store all trains informations

                    // set the time that will be taken for each train 
            Train.ready.get(0).duration = 20;
            Train.ready.get(1).duration = 50;
            Train.ready.get(2).duration = 30;
            Train.ready.get(3).duration = 40;
            Train.ready.get(4).duration = 45;
      
            //Print the trains information in the same form that is received
            ready.forEach((train) -> {
                System.out.printf("Thread id %d: %d %d %d %d %n", train.getId(), train.train_number, train.arrival_time, train.getPriority(), train.duration);
            });

            /* schedule the trains according to their priorities,
            if they tie, sort them according to their arrival times, if they also tie choose the first**/
            Collections.sort(ready);
            
            //put all trains in the waiting list to let them enter in the common lin one by one 
             waiting = ready;
            // all trains going to the common line in ordered way according to thier priority and arrival time 
            waiting.forEach((train) -> {
                train.commonLine();
            });
            //if all trains entered the common line print the scheduling method that they entered according to it
            printSchedule();
       
        }

    }

    public void commonLine() {
        // welcome to the common line you can start execution
        schedule += "Time " + total_time + ": train " + this.train_number + " enters the common line\n";
        waiting_time += total_time ;
        total_time += this.duration;
        schedule += "Time " + total_time + ": train " + this.train_number + " leaves the common line\n";
        turnaround_time += total_time;

    }

    public void pleaseWait() {
        //wait until the current train exit from the common line 
        while (!lock) {
            try {
                //sleep untill the common line become free
                sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(TrainStation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //after entering the common line set back the lock to false 
        this.lock = false;
    }

    public static void  printSchedule() {
        System.out.println("\n=========================================================\n");
        System.out.println(schedule);
        System.out.println("\n=========================================================\n");
        System.out.println("Avrage waiting time for all trains is: " + (waiting_time / waiting.size()) + " minutes");
        System.out.println("Avrage turnaround time for all trains is: " + (turnaround_time / waiting.size() + " minutes"));
    }

    @Override
    public int compareTo(Train t) {

        // if the second train has higher priority return 1
        if (this.getPriority() > t.getPriority()) {
            return 1;
        } //if the first train has higher priority return -1
        else if (t.getPriority() > this.getPriority()) {
            return -1;
        } //if the second train arrive before the first  return 1
        else if (this.arrival_time > t.arrival_time) {
            return 1;
        } //if the first train arrive before the second or if they tie return -1 
        //take the first train
        else {
            return -1;
        }
    }

    public int getDuration(int from, int to) {
        Random random = new Random();
        return random.nextInt((to - from) + 1) + from;
    }

}

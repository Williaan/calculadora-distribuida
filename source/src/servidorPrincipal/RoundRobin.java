package servidorPrincipal;

import java.util.Random;

public class RoundRobin {
	
	static class Process {
        public int number;
        public int arrivalTime;
        public int startTime;
        public int burstTime;
        public Process(int t, int name) { burstTime=t; number = name;}
    }
	
    static class Queue {
    	
        class QueueNode {
            Process  key;
            QueueNode next = null;
            QueueNode(Process p, QueueNode n) { key=p; next=n; }
            QueueNode(Process p) { key=p; }
        }
        
        QueueNode tail;
        
        public  Queue() {
            tail = null;
        }
        public  boolean  isEmpty() { return tail==null; }
        public  void  enqueue (Process x) {
            if ( tail==null ) { tail = new QueueNode(x); tail.next=tail; }
            else tail = (tail.next = new QueueNode(x,tail.next));
        }
        public Process dequeue() {
            if ( tail==null ) return null;
            else {  QueueNode p = tail.next;
            if ( p!=tail ) tail.next = p.next;
            else tail = null;
            return  p.key;
            }
        }
    }

    public static void main(String[]  args) {
        double avSimTime = 0;
        double avTurnaround = 0;
        double avDuration = 0;
        double avThroughput = 0;
        double avinQueue = 0;

        for( int z = 0; z < 10; ++z ) {
            int name = 1;
            int timeSlice = 3000; // 3 milliseconds
            int T = 0;                       // Simulation time (microsec)
            int N = 0;                       // Terminated processes
            int NumProcesses =  100000;         // Max terminated processes during simulation
            int QueuedProcesses = 0;         // Nr processes in the queue
            int totalProcessDuration = 0;
            int totalTurnaroundTime = 0;      //  sum(process.endTime-process.arrivalTime)
            Process  activeProcess = null;
            int  lastProcessArrival = 0;      // Arrival time (microsec) of the last process into the simulation environment
            Random rand0 = new Random( );     //  Random generator for the arrivals of the processes
            Random rand1 = new Random( );     //  Random generators for the burst times
            Random rand2 = new Random( );     //
            Queue q = new Queue( );
            while ( N < NumProcesses ) {
                int newProcessArrival = rand0.nextInt(1801); // generate arrival time: up to 1.8 millisec.
                double u, v, x;
                do {
                    u = 1.-rand1.nextDouble( );
                    v = 1.-rand2.nextDouble( );
                    x = Math.sqrt (-2.*Math.log(u))*Math.cos(2.*Math.PI*v) + 2.0;  //  normal distribution: avg. 2  millisec.
                } while ( x<0 );
                int newProcessDuration = (int) Math.round (x*1000);                //  process duration in microsec.
                Process p = new Process(newProcessDuration, name);
                name++;
                lastProcessArrival += newProcessArrival;
                if ( lastProcessArrival < T ) lastProcessArrival = T;
                p.arrivalTime = lastProcessArrival;
                if ( activeProcess==null ) {
                    activeProcess = p;
                    T = p.startTime = p.arrivalTime;
                }
                else {  q.enqueue(p);   QueuedProcesses++; }
                do {
                    if ( T + activeProcess.burstTime > lastProcessArrival ) break;

                    // ************************************************************************
                    // This is where the RoundRobin code is implemented
                    if(activeProcess.burstTime <= timeSlice) {
                        totalProcessDuration += activeProcess.burstTime;
                        T += activeProcess.burstTime;
                        int  turnaroundTime = T - activeProcess.arrivalTime;
                        totalTurnaroundTime  +=  turnaroundTime;
                        System.out.printf ("Process %d terminated. Turnaround time: %.2f milliseconds.\n",
                                activeProcess.number, (double) turnaroundTime/1000);
                        System.out.printf (" \t(arrival %.2f,  begin %.2f,  end %.2f,  duration %.2f)\n",
                                (double) activeProcess.arrivalTime/1000, (double) activeProcess.startTime/1000,
                                (double) (activeProcess.startTime+activeProcess.burstTime)/1000,
                                (double) activeProcess.burstTime/1000);
                        // Increase executed Processes
                        N++;
                        // Only go to specified number of processes
                        if ( N >= NumProcesses ) break;
                        // If the queue is empty and there are no active processes, stop
                        if ( q.isEmpty( ) ) {  activeProcess = null;   break; }
                        // else take the next process from the queue and make it active
                        else {
                            activeProcess = q.dequeue( );  QueuedProcesses--;
                            if ( activeProcess.arrivalTime <= T ) activeProcess.startTime = T;
                            else activeProcess.startTime = T = activeProcess.arrivalTime;
                        }
                    }
                    // If the process burstTime is longer than the timeSlice, put it at the back of the queue
                    else {
                        activeProcess.burstTime -= timeSlice;
                        System.err.printf("Process %d was prempted.  Process has %.2f milliseconds burstTime remaining\n",
                                activeProcess.number, (double) activeProcess.burstTime/1000);
                        q.enqueue(activeProcess);
                        QueuedProcesses++;
                        // Only go to specified number of processes
                        if ( N >= NumProcesses ) break;
                        // else take the next process from the queue and make it active
                        else {
                            activeProcess = q.dequeue( );  QueuedProcesses--;
                            if ( activeProcess.arrivalTime <= T ) activeProcess.startTime = T;
                            else activeProcess.startTime = T = activeProcess.arrivalTime;
                        }
                    }
                    // ************************************************************************
                } while(true);
            }   //  end while loop

            System.out.printf ("\nSimulation time: %.2f milliseconds\n", (double) T/1000);
            System.out.printf ("Average turnaround time: %.2f milliseconds\n",
                    (double) totalTurnaroundTime/NumProcesses/1000);
            System.out.printf ("Average process duration: %.2f milliseconds\n",
                    (double) totalProcessDuration/NumProcesses/1000);
            System.out.printf ("Throughput: %.2f processes per second\n", (double) NumProcesses/T * 1000000);
            System.out.println ("Processes in the queue at the end of the simulation: "  + QueuedProcesses);

            // Collect the statistics
            avSimTime += (double) T/1000;
            avTurnaround += (double) totalTurnaroundTime/NumProcesses/1000;
            avDuration += (double) totalProcessDuration/NumProcesses/1000;
            avThroughput += (double) NumProcesses/T * 1000000;
            avinQueue += QueuedProcesses;
        }
        // Print the statistics
        System.out.println("The averages: ");
        System.out.printf ("\nSimulation time: %.2f milliseconds\n", avSimTime/10);
        System.out.printf ("Average turnaround time: %.2f milliseconds\n",
                avTurnaround/10);
        System.out.printf ("Average process duration: %.2f milliseconds\n",
                avDuration/10);
        System.out.printf ("Throughput: %.2f processes per second\n", avThroughput/10);
        System.out.println ("Processes in the queue at the end of the simulation: "  + (avinQueue/10));
    }   //  end main
}
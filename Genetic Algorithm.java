//solve single-objective Multiprocessor Scheduling problem using Genetic Algorithm

import static java.lang.Math.random;
import static java.lang.StrictMath.random;
import java.util.Random;
import java.util.Scanner;


public class ThesisProject {
    static int numTasks;
    static pair[][] population;
    static int[][] taskGraph;
    static int[] executionTimes;
    static int[] numParentsArray;
        static int edgeNum;

    static final int NUM_PROCESSORS = 3; // Number of processors

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        numTasks = 19;
        
        taskGraph = new int[numTasks][numTasks];
        population = new pair[100][numTasks];
        executionTimes = new int[numTasks];

                for (int i = 0; i < numTasks; i++) {
        for (int j = 0; j < numTasks; j++) {
            taskGraph[i][j]=-1;
        }
        }

                for (int i = 0; i < numTasks; i++) {
        for (int j = 0; j < numTasks; j++) {
           if( taskGraph[i][j]!=-1){
               edgeNum++;
           }
        }
        }     

      boolean continueInput = true;
      /*  while (continueInput) {
            System.out.print("Enter node number1: ");
            int task1 = scanner.nextInt();
            System.out.print("Enter node number2: ");
            int task2 = scanner.nextInt();
            System.out.print("Enter the weight for the edge between task " + task1 + " and task " + task2 + ": ");
            int weight = scanner.nextInt();
            // Store the weight in the corresponding cell of the matrix
            taskGraph[task1 - 1][task2 - 1] = weight;

            System.out.print("Do you want to continue? (y/n): ");
            String choice = scanner.next();
            if (!choice.equalsIgnoreCase("y")) {
                continueInput = false;
            }
        }
        // Initialize execution times for each task
        for (int i = 0; i < numTasks; i++) {
            System.out.print("Enter execution time for task " + (i + 1) + ": ");
            executionTimes[i] = scanner.nextInt();
        }*/
taskGraph[1][5]=2;        
taskGraph[1][4]=5;
taskGraph[2][3]=4;        
taskGraph[2][4]=5;        
taskGraph[3][5]=1;        
taskGraph[4][6]=2;
taskGraph[4][13]=2;        
taskGraph[5][7]=2;
taskGraph[5][8]=2; 
taskGraph[5][9]=1;   
taskGraph[5][10]=2;
taskGraph[6][7]=1;        
taskGraph[6][8]=3;
taskGraph[6][9]=2;
taskGraph[6][10]=1;
taskGraph[6][11]=2;
taskGraph[6][12]=3;
taskGraph[7][14]=5;        
taskGraph[8][14]=1;        
taskGraph[9][14]=5;        
taskGraph[10][15]=2;        
taskGraph[11][15]=4;        
taskGraph[12][15]=2;
taskGraph[13][16]=3;
taskGraph[13][17]=4;
taskGraph[14][16]=3;
taskGraph[15][16]=5;
taskGraph[15][17]=1;        
executionTimes[0]=0;
executionTimes[1]=4;
executionTimes[2]=3;
executionTimes[3]=4;
executionTimes[4]=5;
executionTimes[5]=5;
executionTimes[6]=4;
executionTimes[7]=3;
executionTimes[8]=6;
executionTimes[9]=4;
executionTimes[10]=2;
executionTimes[11]=6;
executionTimes[12]=6;
executionTimes[13]=2;
executionTimes[14]=4;
executionTimes[15]=3;
executionTimes[16]=5;
executionTimes[17]=6;
executionTimes[18]=0;

        System.out.println("Task graph matrix:");
        for (int i = 0; i < numTasks; i++) {
            for (int j = 0; j < numTasks; j++) {
               

                System.out.print(taskGraph[i][j] + "\t");
            }
            System.out.println();
        }

        // Calculate the number of parents for each task
        numParentsArray = new int[numTasks];
        System.out.println("Number of parents for each task:");
        for (int j = 0; j < numTasks; j++) {
            int numParents = 0;
            for (int i = 0; i < numTasks; i++) {
                if (taskGraph[i][j] != -1) {
                    numParents++;
                }
            }
            numParentsArray[j] = numParents; // Store the number of parents for task j
            System.out.println("Task " + (j ) + ": " + numParents);
        }

        // Generate 100 populations
        for (int ii = 0; ii < 100; ii++) { // rows
            int taskIndex = 0; // Track the position in the population for the current task

            // Reset processed tasks and parent counts
            boolean[] processedTasks = new boolean[numTasks];
            int[] tempNumParentsArray = numParentsArray.clone(); // creates a copy

            // Repeat until all tasks are processed
            while (taskIndex < numTasks) {
                // Find tasks with zero parents
                int[] tasksWithZeroParent = new int[numTasks];
                int count = 0;
                for (int i = 0; i < numTasks; i++) {
                    if (tempNumParentsArray[i] == 0 && !processedTasks[i]) {
                        tasksWithZeroParent[count++] = i ;
                    }
                }

                // If no tasks with zero parents are found, break the loop
                if (count == 0) {
                    break;
                }

                // Shuffle the tasks with zero parents
                for (int i = count - 1; i >= 0; i--) {
                    int index = random.nextInt(i + 1);
                    int selectedTask = tasksWithZeroParent[index];

                    // Swap the selected task with the last task in the array
                    int temp = tasksWithZeroParent[index];
                    tasksWithZeroParent[index] = tasksWithZeroParent[i];
                    tasksWithZeroParent[i] = temp;

                    // Assign the selected task to the population
                    population[ii][taskIndex] = new pair();
                    population[ii][taskIndex].t = selectedTask;
                    // Generate a random processor number (1 to 3)
                    int processor = random.nextInt(NUM_PROCESSORS) ;
                    // Assign the task and processor to the population
                    population[ii][taskIndex].p = processor; // Task * 10 + Processor

                    // Update parent counts for the selected task's children
                    for (int r = 0; r < numTasks; r++) {
                        if (taskGraph[selectedTask][r] != -1) {
                            tempNumParentsArray[r]--;
                        }
                    }

                    // Mark the selected task as processed
                    processedTasks[selectedTask] = true;
                    tempNumParentsArray[selectedTask] = -1; // Mark this task as added to population

                    // Move to the next position in the population
                    taskIndex++;
                }
            }
        }
          double smallest = 0;
               int a=0;
int fitnessValue;
        // Display the population and their fitness values
        System.out.println("Population:");
        int[] fitnessValues = new int[100];
        for (int i = 0; i < 100; i++) { 
         System.out.print(i+": ");

            for (int j = 0; j < numTasks; j++) {
                if (population[i][j] != null && population[i][j].t != -1) {
                    int task = population[i][j].t;
                    int processor = population[i][j].p;
                    System.out.print(task + "," + processor + "\t");
                }
            }
             fitnessValue = fitness(population[i]);
            fitnessValues[i] = fitnessValue;
            System.out.println("\nFitness value: " + fitnessValue);
            System.out.println();
            
        }
 //choose the smallest value from 100 numbers
//while(a<1){
double[] f1 = new double[100];
double[] f2 = new double[100];

     for (int i = 0; i < 100; i++) { 
        f1[i]= fitness(population[i]);
        
     smallest = f1[0]; 
  for (int g = 1; g <100 ; g++) { 
    if ( fitnessValues[i] < smallest) { 
     smallest =  fitnessValues[i]; 
    }}

     }
       System.out.println("S: "+smallest);

     pair[][] parents=new pair[100][numTasks];

//copy f1 to f2
for(int t=0;t<100;t++){
    f2[t]=f1[t];
}

int [] ind=new int[100];
for(int i=0;i<100;i++){//index
    ind[i]=i;
}


// Initialize variables to track the indices of the two best parents
int bestParentIndex1 = 0;
int bestParentIndex2 = 1; // Start with the first two chromosomes

// Find the two chromosomes with the lowest fitness values
for (int i = 2; i < 100; i++) {
    if (fitnessValues[i] < fitnessValues[bestParentIndex1]) {
        bestParentIndex2 = bestParentIndex1;
        bestParentIndex1 = i;
    } else if (fitnessValues[i] < fitnessValues[bestParentIndex2]) {
        bestParentIndex2 = i;
    }
}


for (int j = 0; j < numTasks; j++) {
    parents[0][j] = new pair(population[bestParentIndex1][j].t, population[bestParentIndex1][j].p);
    parents[1][j] = new pair(population[bestParentIndex2][j].t, population[bestParentIndex2][j].p);
}


// Example output to verify the selection
System.out.println("Best Parent 1:");
for (int j = 0; j < numTasks; j++) {
    System.out.print(parents[0][j].t + "," + parents[0][j].p + "\t");
}
System.out.println("\nFitness value: " + fitnessValues[bestParentIndex1]);

System.out.println("\nBest Parent 2:");
for (int j = 0; j < numTasks; j++) {
    System.out.print(parents[1][j].t + "," + parents[1][j].p + "\t");
}
System.out.println("\nFitness value: " + fitnessValues[bestParentIndex2]);


//selection
 // Fill the rest of the parents array (from index 2 to 99) with randomly chosen best populations
        for (int index = 2; index < 100; index++) {
            // Select the best 5 populations
            int[] bestPop = new int[5]; // Array to store indices of best populations
            for (int i = 0; i < 5; i++) {
                bestPop[i] = i; // Initialize with initial indices
            }

            // Find the best 5 populations
            for (int i = 5; i < 100; i++) {
                for (int j = 0; j < 5; j++) {
                    if (fitnessValues[i] < fitnessValues[bestPop[j]]) {
                        // Shift indices and insert the new best index
                        for (int k = 4; k > j; k--) {
                            bestPop[k] = bestPop[k - 1];
                        }
                        bestPop[j] = i;
                        break;
                    }
                }
            }

            // Randomly choose one index from the best 5 that hasn't been chosen yet
            int chosenIndex;
            do {
                chosenIndex = bestPop[random.nextInt(5)];
            } while (chosenIndex == index); // Ensure it's not the current index

            // Copy chosen population to parents array
            for (int j = 0; j < numTasks; j++) {
                parents[index][j] = new pair(population[chosenIndex][j].t, population[chosenIndex][j].p);
            }
      
        }
        // Display the contents of the parents array
        for (int i = 0; i < 100; i++) {
            System.out.print("Parents " + i + ": ");
            for (int j = 0; j < numTasks; j++) {
                System.out.print("(" + parents[i][j].t + ", " + parents[i][j].p + ") ");
            }
            System.out.println();
        }
   
for (int i = 2; i < 50; i += 2) {
    for (int p = 0; p < numTasks; p++) {
        performCrossover(parents[i][p].p, parents[i+1][p].p);
    }
}
    performMutation(random);
              

}
/*
        // Display the population after crossover
        System.out.println("Population after crossover:");
        for (int i = 0; i < 100; i++) { 
         System.out.print(i+": ");

            for (int j = 0; j < numTasks; j++) {
                if (population[i][j] != null && population[i][j].t != -1) {
                    int task = population[i][j].t;
                    int processor = population[i][j].p;
                    System.out.print(task + "," + processor + "\t");
                }
            }
            System.out.println("\nFitness value: " + fitness(population[i]));
            System.out.println();
}
*/
   }

public static int fitness(pair[] chromosome) {
        int f = 0;
        int[] refCount = new int[numTasks];
        for (int i = 0; i < numTasks; i++) {
                    refCount[i]=numParentsArray[i];
                    //System.out.println(refCount[i]+" "); 
        }

        int[] si = new int[NUM_PROCESSORS];
        int[] tonp = new int[NUM_PROCESSORS];
        for (int i = 0; i < NUM_PROCESSORS; i++) {
            si[i] = 0;
        }

        int ts = 0;
        int s = 0;

        while (ts < numTasks) {
            for (int j = 0; j < NUM_PROCESSORS; j++) {
                if (s == si[j]) {
                    for (int k = 0; k < numTasks; k++) {
                        if (taskGraph[tonp[j]][k]!=-1) {
                            refCount[k]--;
                        }
                    }
                }
            }

            for (int i = 0; i < numTasks; i++) {
                if (refCount[chromosome[i].t] == 0&& 
                        s >= si[chromosome[i].p ]) {
                    tonp[chromosome[i].p] = chromosome[i].t;
                    si[chromosome[i].p] = s + executionTimes[chromosome[i].t];
                    refCount[chromosome[i].t] = -1;
                    ts++;

                    if (executionTimes[chromosome[i].t] == 0) {
                        for (int k = 0; k < numTasks; k++) {
                            if (taskGraph[chromosome[i].t][k] ==chromosome[i].t ) {
                                refCount[k]--;
                            }
                        }
                    }
                }
            }
            s++;
        }

        for (int i = 0; i < NUM_PROCESSORS; i++) {
            if (si[i] > f) {
                f = si[i];
            }
        }

        for (int i = 0; i < numTasks -1; i++) {
            for (int j = i + 1; j < numTasks; j++) {
                if (taskGraph[chromosome[i].t][chromosome[j].t] != -1 && chromosome[i].p != chromosome[j].p) {
                    f += taskGraph[chromosome[i].t ][chromosome[j].t ];
                }
            }
        }

        return f;
    }
    
        public static void performCrossover(int parent1Index, int parent2Index) {
       // int parent1Index = selection(random, fitnessValues);
        //int parent2Index;
        //do {
          //  parent2Index = selection(random, fitnessValues);
       // } while (parent1Index == parent2Index);

        // Choose a random cut point between 1 and numTasks-1
    int RandomCut;
       Random rng = new Random();
       RandomCut = rng.nextInt(10);//random cut point
       
        // Print the selected parents and cut point
        System.out.println("Selected Parents: " + parent1Index + ", " + parent2Index);
        System.out.println("Cut Point: " + RandomCut);

        // Print the parents before crossover
        System.out.println("Parent 1 before crossover:");
        for (int j = 0; j < numTasks; j++) {
            System.out.print(population[parent1Index][j].t + "," + population[parent1Index][j].p + "\t");
        }
        System.out.println();

        System.out.println("Parent 2 before crossover:");
        for (int j = 0; j < numTasks; j++) {
            System.out.print(population[parent2Index][j].t + "," + population[parent2Index][j].p + "\t");
        }
        System.out.println();

        // Swap the processors between the parents from the cut point onwards
        for (int i = RandomCut; i < numTasks; i++) {
            int tempProcessor = population[parent1Index][i].p;
            population[parent1Index][i].p = population[parent2Index][i].p;
            population[parent2Index][i].p = tempProcessor;
        }

        // Print the parents after crossover
        System.out.println("Parent 1 after crossover:");
        for (int j = 0; j < numTasks; j++) {
            System.out.print(population[parent1Index][j].t + "," + population[parent1Index][j].p + "\t");
        }
        System.out.println();

        System.out.println("Parent 2 after crossover:");
        for (int j = 0; j < numTasks; j++) {
            System.out.print(population[parent2Index][j].t + "," + population[parent2Index][j].p + "\t");
        }
        System.out.println();

}
    
public static void crossover(double[] par1,double[] par2){
       int RandomCut;
       Random rng = new Random();
       RandomCut = rng.nextInt(10);//random cut point

for(int i=RandomCut;i<10;i++){
    double temp;
   temp =par1[i];
    par1[i]=par2[i];
    par2[i]=temp;
}                                        
}
  public static int selection(Random random, int[] fitnessValues) {
        // Number of random picks
        int numPicks = 5;
        int[] selectedIndices = new int[numPicks];

        // Randomly pick 5 indices
        for (int i = 0; i < numPicks; i++) {
            selectedIndices[i] = random.nextInt(fitnessValues.length);
        }

        // Find the index with the lowest fitness among the selected indices
        int bestIndex = selectedIndices[0];
        int lowestFitness = fitnessValues[bestIndex];

        for (int i = 1; i < numPicks; i++) {
            int currentIndex = selectedIndices[i];
            if (fitnessValues[currentIndex] < lowestFitness) {
                bestIndex = currentIndex;
                lowestFitness = fitnessValues[currentIndex];
            }
        }
       // System.out.println("best: "+lowestFitness);

        return lowestFitness;
    }
    
  public static void performMutation(Random random) {
    for (int i = 0; i < 100; i++) {
        int task1Index = random.nextInt(numTasks);
        int task2Index = random.nextInt(numTasks);

        while (task1Index == task2Index) {
            task2Index = random.nextInt(numTasks);
        }

        int oldProcessor1 = population[i][task1Index].p;
        int oldProcessor2 = population[i][task2Index].p;

        int newProcessor1 = random.nextInt(NUM_PROCESSORS);
        int newProcessor2 = random.nextInt(NUM_PROCESSORS);

        population[i][task1Index].p = newProcessor1;
        population[i][task2Index].p = newProcessor2;

      //  System.out.println("Chromosome " + i + ": Task " + population[i][task1Index].t + " changed from Processor " + oldProcessor1 + " to " + newProcessor1);
        //System.out.println("Chromosome " + i + ": Task " + population[i][task2Index].t + " changed from Processor " + oldProcessor2 + " to " + newProcessor2);
    }
}

}

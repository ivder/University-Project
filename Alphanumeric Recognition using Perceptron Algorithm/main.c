/*Created by Ivan - 2014758139*/ 

#include <stdio.h>
#include <stdlib.h>
/* for rand() */
#include <time.h>
#include <math.h>
#include <unistd.h>
#include <stdbool.h>
/* for type bool */

#include "functions.h"
/* all function prototypes are here */

#define THRESHOLD 0
#define DATA_FILE "alphanumeric.txt"
#define LEARNING_RATE 1 

int main(void)
{
	int i;
    /* open alphanumeric.txt for reading */
    FILE * file_pointer = fopen(DATA_FILE, "r");

    /* make sure the file can be opened */
    if (file_pointer == NULL)
    {
        fprintf(stderr, "Cannot open training data file.\n");
        fprintf(stderr, "Check permissions for data file.\n");
        exit(1);
    }

    float input[65];  
    /* the inputs for the artifical neural network */
    input[64]=1;
    /*bias value=1*/

    float weight[65];
    /* the weights for the artifical neural network, 64 for input + 1 for bias */

    float threshold;
    /* used in Activation Function */
    /* if summation of weighted inputs > threshold,
     * Activation Function returns true. Otherwise,
     * Activation Function returns false. */

    /* get learning rate from macro */
    float learning_rate = LEARNING_RATE;

    float dot_product[36];
    /* dot product = (a1 * b1) + (a2 * b2) + ... + (an * bn) */
    /* This will be the summation of all the weighted inputs.
     * This value will be given to the activation function. */

    /* What actual_output is the object being classified in? (Y) */
    int actual_output = 0;

    /* error = expected output - actual ouput (Y-d) */
    /* error is used in the update weight formula */
    float error = 0;

    /* keep track of if there are any incorrect
     * classifications left by using a boolean value.
     * true means there are still incorrect classifications
     * false means all classifications are correct. */
    bool incorrectClassifications = true;

    printf("PERCEPTRON TRAINING ALGORITHM IMPLEMENTATION for ALPHANUMERIC\n");

    /* We need to seed the random number generator. 
     * Otherwise, it will produce the same number every time
     * the program is run. */
    srand(time(NULL));
    /* I am using the current time to seed rand(). */

    /* get threshold from macro */
    threshold = THRESHOLD;

    /* The weights will start off as random numbers in 
     * the range [-1, 1]. */
    for(i=0;i<65;i++){
    	weight[i] = ((float)rand())/RAND_MAX*2-1;
	}

    while (incorrectClassifications == true)
    {
        incorrectClassifications = false;

        /* Let's loop through all the data sets. */
        int i = 0;  //numeric counter
        char alphabet='A';  //alphabet counter
        int x;  //input counter
        int p=0; //pattern counter

        /* loop will break if input = 999 */
        while (1)
        {

            /* get input from the data set file */ 
            for(x=0;x<64;x++){
            	input[x] = getInput(file_pointer); 
			}
            
            if (input[0] == 999){
            	break;
			} 

            /* sum the weighted inputs */
            dot_product[p] = sumWeightedInputs(input, weight);

            /* apply activation function to sum of weighted inputs */
            actual_output = activationFunction(dot_product, threshold); 

            /* print which data set we are on */
            if(i<=9){
            	printf("Data Set %d\n", i); //print data Numeric (0-9)
        	}
        	if(i>9){
        		printf("Data Set %c\n", alphabet); //print data alphabet (A-Z)
        		alphabet++;
			}
        
            int z;

            /* print the inputs including bias */
            for(z=0;z<65;z++){
            printf("\n"); // new line
            printf("Input [%d] = %.2f  ", z+1,input[z]);

            /* print the weights */
            printf("\n"); // new line
            printf("Weight [%d] = %.2f  ", z+1,weight[z]);
			}

            /* print the summation of weighted inputs */
            printf("\n"); // new line
            printf("Summation of pattern number %d = %.2f\n",p+1, dot_product[p]);

            /* print the actual_output */
            printf("Object classified to class %d.\n", actual_output);

            /* check the output */
            error = checkOutput(file_pointer, actual_output);

            /* print the result */
            if (error == 0) printf("Ouput correct.\n");
            else
            {
                /* set incorrectClassifications to true 
                 * to loop through the data set once more */
                incorrectClassifications = true;

                printf("Output incorrect.\n");
                printf("Error = %.0f\n", error);

                /* we need to update the weights if *
                 * there is an error */
                int c;
                for(c=0;c<65;c++){
                	weight[c] = updateWeights(weight[c], learning_rate, input[c], error);
				}
				
                /* print the new weights */
                printf("\n"); // new line;
                printf("NEW WEIGHTS: \n");
                for(c=0;c<65;c++){
                	printf("*** New weight [%d]: %.2f  ",c, weight[c]);
				}

            } // ends else

            printf("\n");
            printf("-----------------------------------------------\n");
            printf("-----------------------------------------------\n\n");

            i++; //increment i
            p++; //increment pattern counter
        } // ends while (1)

        /* set the file pointer back to beginning of file */
        rewind(file_pointer);

    } // ends while(incorrectClassifications == true)

    /* Print the final weights */
    printf("\n");
    printf("Final Weights: \n");
    int m;
    for(m=0;m<65;m++){
    	printf("Weight [%d]: %.2f\n",m+1, weight[m]); 
	}
	
	float newinput[65];  //input any pattern for pattern recognition
	newinput[64]=1; //bias automatically set to 1
	float dot_product_input;  //new summation of inputted pattern
	printf("Please input 64 bits of alphanumeric (8x8 matrix 1 & -1) : ");
	for(m=0;m<64;m++){
		scanf("%f",&newinput[m]);  //input bit of matrix
	}
	dot_product_input=sumWeightedInputs(newinput, weight);  //sum the weighted inputs (input*final weight)
	printf("Summation of Weighted new Input = %.2f\n",dot_product_input);  //print the summation
	
	int p,location;
	float diff[36]; //summation value difference
	for(p=0;p<36;p++){
		diff[p]=fabs(dot_product[p]-dot_product_input); //difference between 36 pattern's dot product - new inputted dot product
	}
	float minimum=diff[0];  //set the minimum value, which means the closest value, to find the closest pattern
	for(p=0;p<36;p++){
		if(diff[p]<minimum){
			minimum=diff[p];
			location=p;  //location of the closest value
		}
	}
	printf("The MINIMUM DIFFERENCE of inputted pattern and alphanumeric pattern is= %.2f\n",minimum);
	printf("The LOCATION of minimum difference of inputted pattern and alphanumeric pattern is at pattern number %d\n",location+1);
	char alphabet='A'; //alphabet counter
	printf("*************************************\n");
	if(location<=9){
		printf("PATTERN RECOGNITION RESULT = %d\n",location);  //print the final result which is numeric
	}
	if(location>9){
		alphabet=alphabet+(location-9);
		printf("PATTERN RECOGNITION RESULT = %c\n",alphabet);  //print the final result which is alphabet
	}
	printf("*************************************\n");
	
    /* close the input file */
    fclose(file_pointer);
    system("PAUSE");
    getchar();
    return 0;
} // ends main()


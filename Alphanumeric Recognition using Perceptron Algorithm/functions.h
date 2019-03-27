#include <stdio.h>
/* for FILE type */

/* get inputs for the perceptron from a data file */
float getInput(FILE * ftp);

/* A function for the dot product (summation) of weighted
 * inputs (i.e., (input1 * weight1) + (input2 * weight2) ... */
float sumWeightedInputs(float input[], float weight[]);

/* Activation Function -- sees if the weights is greater 
 * than a threshold, and returns 1, returns 0 if the same as threshold
 * and -1 otherwise */
int activationFunction(float dot_product[], float threshold);

/* Function checks the actual output of the perceptron's ouput 
 * against the training data set. */
float checkOutput(FILE * ftp, float actual_output);

/* a function to update weights */
float updateWeights(float weight, float learning_rate, float input, float error);


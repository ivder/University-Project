#include "functions.h"
#include <stdio.h>
#include <stdlib.h>

/* gets a input for the perceptron from a data file */
float getInput(FILE * ftp)
{
  float input;

  /* scan the input from the training data */
  fscanf(ftp, "%f ", &input);

  /* Return the input */
  return input;
}

/* A function for the dot product (summation) of weighted
 * inputs (i.e., (input1 * weight1) + (input2 * weight2) ... */
float sumWeightedInputs(float input[], float weight[])
{
  /* sum means dot product here */
  float sum = 0;
  int n;

  /* figure out the dot product here */
  for(n=0;n<64;n++){
  	sum =sum+(input[n] * weight[n]);
  }

  /* return sum */
  return sum;
}

/* This is the function that updates the weights 
 * if the neuron misclassified input. */
/* I am using this formual to update weights: 
 * new weight = old weight + (learning rate * current input * (error)
 * where error = expected output - actual output */
float updateWeights(float weight, float learning_rate, float input, float error)
{
    float new_weight = 0;

    /* use the formula Wn = Wn-1 + alpha(d-y)X */
    new_weight = weight + (learning_rate * error * input);

    return new_weight;
}

/* Activation Function -- sees if the weights is greater 
 * than a threshold, and returns 1, returns 0 if same as threshold,
 * and -1 otherwise. */
int activationFunction(float dot_product, float threshold)
{
  if (dot_product > threshold) return 1;
  /* object actual_outputified to class 1 */
  
  else if(dot_product < threshold) return -1;
   /* object actual_outputified to class -1 */

  else if(dot_product = threshold) return 0;
  /* object actual_outputified to class 0 */
}

/* Function checks the actual output of the perceptron's ouput against the expected output. */
float checkOutput(FILE * ftp, float actual_output)
{
  float expected_output = 0;

  /* error = expected_ouput - actual_output */
  float error = 0;
  /* the value of error is needed in the update
   * weight formula */

  /* get expected output from data file */
  fscanf(ftp, "%f ", &expected_output);
  printf("\n"); // new line

  printf("Expected Output: %.2f\n", expected_output);
  printf("Actual Output: %.2f\n", actual_output);

  /* calculate error */
  error = expected_output - actual_output;
  return error;
}


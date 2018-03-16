import numpy as np
import tensorflow as tf
import matplotlib
matplotlib.use('TkAgg')
import matplotlib.pyplot as plt


inputFile = open("total_data.txt")
dim = [3350,6]
inpRaw = []
c = 0
for line in inputFile:
	inpRaw.append(float(line.strip()))



def constructArray(rawInput, numDimensions, dimensionsArray):
	if(numDimensions==1):
		return rawInput
	ret = []
	for i in range(0,dimensionsArray[0]):
		lengthA = int(len(rawInput)/dimensionsArray[0]);
		ret.append(constructArray(rawInput[i*lengthA:(i+1)*lengthA],numDimensions-1,dimensionsArray[1:]))
	return ret;

def weight_variable(shape):
  initial = tf.truncated_normal(shape, stddev=0.1)
  return tf.Variable(initial)

inp = constructArray(inpRaw,len(dim),dim)

targetsFile = open("total_results.txt")
dim = [3350,2]
outRaw = []
c = 0
for line in targetsFile:
	outRaw.append(float(line.strip()))

targets = constructArray(outRaw,len(dim),dim)


sess = tf.InteractiveSession()
IndivHid = 10
CombHid = 20
insizenum=6

MINI_BATCH_SIZE = 10
EPOCHS = 60
PRINTA = 10
LEARNING_RATE = 5e-4

inputs = tf.placeholder(dtype = tf.float32, shape = [None,insizenum])
targetValues = tf.placeholder(dtype=tf.float32, shape = [None,2])
in_size = tf.size(inputs)
half = tf.cast(insizenum/2,dtype=tf.int32)
red_inputs = inputs[:,0:half]
blue_inputs = inputs[:,half:]

out1 = tf.matmul(red_inputs,[[1.0],[1.0],[1.0]])
out2 = tf.matmul(blue_inputs,[[1.0],[1.0],[1.0]])
out = tf.divide(tf.concat([out1,out2],1),tf.constant(3, dtype=tf.float32))
val1 = targetValues
val2 = out
correct_prediction = tf.equal(tf.argmax(targetValues,1), tf.argmax(out,1))
accuracy = tf.reduce_mean(tf.cast(correct_prediction, tf.float32))
sess.run(tf.global_variables_initializer())

arrOut = val1.eval(feed_dict={inputs:inp, targetValues:targets})
arrPred = val2.eval(feed_dict={inputs:inp, targetValues:targets})
summ = 0
counter = 0
for k in range(int(len(arrOut))-1):
#	print(arrOutInd[k])
	#print(arrOut[k])
	#print(arrOut[k][0])
	numOut = arrOut[k][0]
	numPred = arrPred[k][0]
	numOut1 = arrOut[k][1]
	numPred1 = arrPred[k][1]
	if numOut != 0:
		summ += (1-abs(numOut-numPred)/numOut)
	else:
		counter-=1
	if numOut1 != 0:
		summ += (1-abs(numOut1-numPred1)/numOut1)
	else:
		counter-=1

newAcc = summ/(2*len(arrOut)+counter)

a = newAcc #accuracy.eval(feed_dict={inputs: inp, targetValues: targets})
print(a)
